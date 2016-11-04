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
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.util.concurrent.GenericFutureListener;
import cn.ms.gateway.base.container.support.AbstractContainer;
import cn.ms.gateway.base.interceptor.Interceptor;
import cn.ms.gateway.core.entity.GatewayREQ;
import cn.ms.gateway.core.entity.GatewayRES;
import cn.ms.gateway.core.interceptor.GatewayInterceptor;

/**
 * 基于Netty实现的网关容器
 * 
 * @author lry
 */
public class NettyContainer extends AbstractContainer<GatewayREQ, GatewayRES> {

	NettyConf nettyConf;
	EventLoopGroup bossGroup = null;
	EventLoopGroup workerGroup = null;
	ServerBootstrap serverBootstrap = null;
	Interceptor<GatewayREQ, GatewayRES> interceptor = new GatewayInterceptor(this);

	public NettyContainer(NettyConf nettyConf) {
		this.nettyConf = nettyConf;
	}

	@Override
	public void init() throws Exception {
		super.gateway.init();
		
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
					}
				}).option(ChannelOption.SO_BACKLOG, 1024)
				.childOption(ChannelOption.SO_KEEPALIVE, true);

	}

	@Override
	public void start() throws Exception {
		super.gateway.start();
		
		ChannelFuture channelFuture = serverBootstrap.bind(nettyConf.getPort()).sync();
		channelFuture.addListener(new GenericFutureListener<ChannelFuture>() {
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				if(future.isSuccess()){
					System.out.println("启动成功");					
				}else{
					System.out.println("启动失败");
				}
			}
		});
	}

	@Override
	public GatewayRES handler(GatewayREQ req, Object... args) throws Throwable {
		GatewayREQ interceptorGatewayREQ = req;
		GatewayRES interceptorGatewayRES = null;
		try {
			GatewayREQ beforeGatewayREQ = interceptor.before(interceptorGatewayREQ, args);
			if (beforeGatewayREQ != null) {
				interceptorGatewayREQ = beforeGatewayREQ;
			}
			interceptorGatewayRES = interceptor.interceptor(interceptorGatewayREQ, args);
		} finally {
			GatewayRES afterGatewayRES = interceptor.after(interceptorGatewayREQ, interceptorGatewayRES, args);
			if (afterGatewayRES != null) {
				interceptorGatewayRES = afterGatewayRES;
			}
		}

		return interceptorGatewayRES;
	}

	@Override
	public void destory() throws Exception {
		super.gateway.destory();
		
		if (serverBootstrap != null) {
			serverBootstrap.clone();
		}

		if (workerGroup != null) {
			workerGroup.shutdownGracefully();
		}

		if (bossGroup != null) {
			bossGroup.shutdownGracefully();
		}
	}
	
	/**
	 * Netty 业务处理器
	 * 
	 * @author lry
	 */
	class HttpServerInboundHandler extends ChannelInboundHandlerAdapter {
	    
		private HttpRequest request;
	    
		@Override
	    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
	        if (msg instanceof HttpRequest) {
	            request = (HttpRequest) msg;
	        }
	        if (msg instanceof HttpContent) {
	            HttpContent httpContent = (HttpContent) msg;
	            ByteBuf buf = httpContent.content();
	            String content=buf.toString(io.netty.util.CharsetUtil.UTF_8);
	            buf.release();

	            GatewayREQ req=new GatewayREQ();
	            req.setContent(content);
	            req.setRequest(request);
	            req.setCtx(ctx);
	            
	            try {
					handler(req);
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
