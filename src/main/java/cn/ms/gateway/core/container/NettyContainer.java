package cn.ms.gateway.core.container;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.util.concurrent.GenericFutureListener;

import org.apache.logging.log4j.ThreadContext;

import cn.ms.gateway.base.IGateway;
import cn.ms.gateway.base.container.AbstractContainer;
import cn.ms.gateway.common.TradeIdWorker;
import cn.ms.gateway.common.log.Logger;
import cn.ms.gateway.common.log.LoggerFactory;
import cn.ms.gateway.common.utils.NetUtils;
import cn.ms.gateway.entity.GatewayREQ;
import cn.ms.gateway.entity.GatewayRES;

/**
 * 基于Netty实现的网关容器
 * 
 * @author lry
 */
public class NettyContainer extends AbstractContainer<GatewayREQ, GatewayRES> {

	private static final Logger logger=LoggerFactory.getLogger(NettyContainer.class);
	
	NettyConf nettyConf;
	EventLoopGroup bossGroup = null;
	EventLoopGroup workerGroup = null;
	ServerBootstrap serverBootstrap = null;
	
	public NettyContainer(IGateway<GatewayREQ, GatewayRES> gateway, NettyConf nettyConf) {
		super(gateway);
		this.nettyConf = nettyConf;
	}

	@Override
	public void init() throws Exception {
		this.bossGroup = new NioEventLoopGroup(nettyConf.getBossGroupThread());
		this.workerGroup = new NioEventLoopGroup(nettyConf.getWorkerGroupThread());

		serverBootstrap = new ServerBootstrap();
		serverBootstrap.group(bossGroup, workerGroup)
				.channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					public void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline().addLast(new HttpResponseEncoder());
						ch.pipeline().addLast(new HttpRequestDecoder());
						ch.pipeline().addLast(new HttpServerInboundHandler());
						ch.pipeline().addLast(new HttpObjectAggregator(Integer.MAX_VALUE));
					}
				}).option(ChannelOption.SO_BACKLOG, 1024)
				.childOption(ChannelOption.SO_KEEPALIVE, true);

	}

	@Override
	public void start() throws Exception {
		ChannelFuture channelFuture = serverBootstrap.bind(nettyConf.getPort()).sync();
		channelFuture.addListener(new GenericFutureListener<ChannelFuture>() {
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				if(future.isSuccess()){
					logger.info("启动成功: http://%s:%s", NetUtils.getLocalIp(), nettyConf.getPort());
				}else{
					logger.error("启动失败");
				}
			}
		});
	}

	@Override
	public GatewayRES handler(GatewayREQ req, Object... args) throws Throwable {
		GatewayRES res = null;
		try {
			super.beforeInterceptor(req, args);
			res = super.sendGatewayHandler(req, args);
		} finally {
			super.afterInterceptor(req, res, args);
		}

		return res;
	}

	@Override
	public void destory() throws Exception {
		if (workerGroup != null) {
			workerGroup.shutdownGracefully();
		}

		if (bossGroup != null) {
			bossGroup.shutdownGracefully();
		}
		
		if (serverBootstrap != null) {
			serverBootstrap.clone();
		}
	}
	
	/**
	 * Netty 业务处理器
	 * 
	 * @author lry
	 */
	class HttpServerInboundHandler extends ChannelInboundHandlerAdapter {
	    
		private HttpRequest request;
		private TradeIdWorker tradeIdWorker=new TradeIdWorker(0, 0);
	    
		@Override
	    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
	        if (msg instanceof HttpRequest) {
	            request = (HttpRequest) msg;
	        }
	        if (msg instanceof HttpContent) {
	        	String tradeId=String.valueOf(tradeIdWorker.nextId());
	        	ThreadContext.put("tradeId", tradeId);
	        	
	            HttpContent httpContent = (HttpContent) msg;
	            ByteBuf buf = httpContent.content();
	            String content=buf.toString(io.netty.util.CharsetUtil.UTF_8);
	            buf.release();

	            GatewayREQ req=new GatewayREQ();
	            req.setContent(content);
	            req.setRequest(request);
	            req.setCtx(ctx);
	            req.setTradeId(String.valueOf(tradeId));
	            
	            try {
					handler(req, request);
				} catch (Throwable e) {
					e.printStackTrace();
				}
	        }
	    }

	    @Override
	    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
	        ctx.flush();
	    }

	    @Override
	    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
	    	cause.printStackTrace();
	        ctx.close();
	    }
	}

}
