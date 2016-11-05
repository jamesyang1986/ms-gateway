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
import cn.ms.gateway.base.IGateway;
import cn.ms.gateway.base.container.AbstractContainer;
import cn.ms.gateway.base.interceptor.Interceptor;
import cn.ms.gateway.entity.GatewayREQ;
import cn.ms.gateway.entity.GatewayRES;

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
	Interceptor<GatewayREQ, GatewayRES> interceptor=null;
	
	public NettyContainer(IGateway<GatewayREQ, GatewayRES> gateway, NettyConf nettyConf, Interceptor<GatewayREQ, GatewayRES> interceptor) {
		super(gateway);
		
		this.nettyConf = nettyConf;
		this.interceptor=interceptor;
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
					System.out.println("启动成功: http://127.0.0.1:"+nettyConf.getPort());					
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
			GatewayRES res = null;
			interceptorGatewayRES = super.sendGatewayHandler(interceptorGatewayREQ, res, args);
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
