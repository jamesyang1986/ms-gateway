package cn.ms.gateway.core.connector;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders.Names;
import io.netty.handler.codec.http.HttpHeaders.Values;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.AttributeKey;
import io.netty.util.CharsetUtil;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.ms.gateway.base.adapter.ICallback;
import cn.ms.gateway.base.connector.IConnector;
import cn.ms.gateway.common.Conf;
import cn.ms.gateway.common.log.Logger;
import cn.ms.gateway.common.log.LoggerFactory;
import cn.ms.gateway.common.thread.NamedThreadFactory;
import cn.ms.gateway.entity.GatewayREQ;
import cn.ms.gateway.entity.GatewayRES;

@SuppressWarnings("deprecation")
public class NettyConnector implements IConnector<GatewayREQ, GatewayRES> {

	private static final Logger logger=LoggerFactory.getLogger(NettyConnector.class);
	
	//$NON-NLS-通道回调函数绑定KEY$
	public static final AttributeKey<CallbackTransferObject> CHANNEL_CALLBACK_KEY = AttributeKey.valueOf("gateway_connector_callback");

	private Bootstrap bootstrap = null;
	private ConcurrentHashMap<String, ChannelFuture> channelFutureMap = new ConcurrentHashMap<String, ChannelFuture>();

	@Override
	public void init() throws Exception {
		EventLoopGroup workerGroup = new NioEventLoopGroup(Conf.CONF.getConnectorWorkerThreadNum(), new NamedThreadFactory("NettyConnectorWorker"));
		
		bootstrap = new Bootstrap();
		bootstrap.group(workerGroup);
		bootstrap.channel(NioSocketChannel.class);
		bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
	}

	@Override
	public void start() throws Exception {

	}
	
	@Override
	public void connector(GatewayREQ req, ICallback<GatewayREQ, GatewayRES> callback, Object... args) throws Exception {
		URI tempURI = new URI(req.getRemoteURI());
		int remotePort=tempURI.getPort()<=0?80:tempURI.getPort();
		String remoteAddress=tempURI.getHost()+":"+remotePort;
		
		//$NON-NLS-处理器和通道回收利用,一次创建N次使用$
		ChannelFuture channelFuture = channelFutureMap.get(remoteAddress);
		if (channelFuture == null) {
			bootstrap.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new IdleStateHandler(Conf.CONF.getReaderIdleTimeSeconds(), 
							Conf.CONF.getWriterIdleTimeSeconds(), Conf.CONF.getAllIdleTimeSeconds()));
					ch.pipeline().addLast(new HttpResponseDecoder());
					ch.pipeline().addLast(new HttpRequestEncoder());
					ch.pipeline().addLast(new HttpObjectAggregator(Integer.MAX_VALUE));
					ch.pipeline().addLast(new NettyConnectorHandler());
				}
			});

			channelFuture = bootstrap.connect(tempURI.getHost(), remotePort).sync();
			channelFutureMap.put(remoteAddress, channelFuture);
		}

		DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, 
				tempURI.getPath(), Unpooled.wrappedBuffer(req.getContent().getBytes(CharsetUtil.UTF_8)));

		// 构建http请求
		if(!(req.getClientHost()==null||req.getClientHost().length()<1)){
			request.headers().set("clientHost", req.getClientHost());// 客户端HOST	
		}
		request.headers().set(Names.HOST, req.getLocalHost());// 网关本机HOST
		request.headers().set(Names.CONNECTION, Values.KEEP_ALIVE);
		request.headers().set(Names.CONTENT_LENGTH, request.content().readableBytes());

		long routeStartTime = System.currentTimeMillis();
		logger.info("=====路由开始=====");
		// $NON-NLS-通过通道获取回调函数$
		channelFuture.channel().attr(CHANNEL_CALLBACK_KEY).set(
				new CallbackTransferObject(req.getTradeId(), req.getTradeStartTime(), routeStartTime, callback));
		
		// 发送http请求
		channelFuture.channel().writeAndFlush(request);
	}

	@Override
	public void shutdown() throws Exception {
		for (Map.Entry<String, ChannelFuture> entry : channelFutureMap.entrySet()) {
			ChannelFuture channelFuture = entry.getValue();
			channelFuture.channel().flush();
			channelFuture.channel().closeFuture().sync();
		}
	}

}
