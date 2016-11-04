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
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpVersion;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.ms.gateway.base.connector.IConnector;
import cn.ms.gateway.core.entity.GatewayREQ;

@SuppressWarnings("deprecation")
public class NettyClientConnector implements IConnector {

	ConnectorConf conf;
	Bootstrap bootstrap = new Bootstrap();
	private ConcurrentHashMap<String, ChannelFuture> channelFutureMap = new ConcurrentHashMap<String, ChannelFuture>();

	public NettyClientConnector(ConnectorConf conf) {
		this.conf=conf;
	}
	
	@Override
	public void init() throws Exception {
		EventLoopGroup workerGroup = new NioEventLoopGroup(conf.getConnectorWorkerThreadNum());
		bootstrap.group(workerGroup);
		bootstrap.channel(NioSocketChannel.class);
		bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
	}

	@Override
	public void start() throws Exception {

	}

	@Override
	public void connect(GatewayREQ req, final IConnectorCallback callback, Object... args) throws Throwable {
		URI tempURI = new URI(req.getOriginURI());
		String address=tempURI.getHost()+":"+(tempURI.getPort()<=0?80:tempURI.getPort());
		ChannelFuture channelFuture = channelFutureMap.get(address);
		if (channelFuture == null) {
			bootstrap.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new HttpResponseDecoder());
					ch.pipeline().addLast(new HttpRequestEncoder());
					ch.pipeline().addLast(new HttpClientInboundHandler(callback));
				}
			});

			channelFuture = bootstrap.connect(tempURI.getHost(), tempURI.getPort()).sync();
			channelFutureMap.put(address, channelFuture);
		}

		DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, 
				HttpMethod.GET, tempURI.toASCIIString(),Unpooled.wrappedBuffer(req.getContent().getBytes("UTF-8")));

		// 构建http请求
		request.headers().set(Names.HOST, tempURI.getHost());
		request.headers().set(Names.CONNECTION, Values.KEEP_ALIVE);
		request.headers().set(Names.CONTENT_LENGTH, request.content().readableBytes());
		
		// 发送http请求
		channelFuture.channel().writeAndFlush(request);
	}

	@Override
	public void destory() throws Exception {
		for (Map.Entry<String, ChannelFuture> entry : channelFutureMap.entrySet()) {
			ChannelFuture channelFuture = entry.getValue();
			channelFuture.channel().flush();
			channelFuture.channel().closeFuture().sync();
		}
	}

}
