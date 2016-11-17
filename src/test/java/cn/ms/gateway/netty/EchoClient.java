package cn.ms.gateway.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lilinfeng
 * @date 2014年2月14日
 * @version 1.0
 */
public class EchoClient {

	EventLoopGroup group = new NioEventLoopGroup();
	Bootstrap b = new Bootstrap();
	ConcurrentHashMap<String, EchoClientHandler> handlerMap=new ConcurrentHashMap<String, EchoClientHandler>();
	
	
	public EchoClientHandler getHandlerAdapter(String host, int port) throws Exception {
		String id=host+":"+port;
		if(handlerMap.contains(id)){
			return handlerMap.get(id);
		}
		
		final EchoClientHandler adapter=new EchoClientHandler(); 
		try {
			handlerMap.put(id, adapter);
			b.group(group).channel(NioSocketChannel.class)
					.option(ChannelOption.TCP_NODELAY, true)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch)throws Exception {
							ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
							ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
							ch.pipeline().addLast(new StringDecoder());
							ch.pipeline().addLast(adapter);
						}
					});

			ChannelFuture f = b.connect(host, port).sync();
			for(int i=0;i<10;i++){
				f.channel().writeAndFlush(Unpooled.copiedBuffer("Hello world!".getBytes()));				
			}
			f.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully();
		}
		
		return adapter;
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		int port = 8080;
		if (args != null && args.length > 0) {
			try {
				port = Integer.valueOf(args[0]);
			} catch (NumberFormatException e) {
				// 采用默认值
			}
		}
		
		EchoClient echoClient=new EchoClient();
		for (int i = 0; i < 10; i++) {
			echoClient.getHandlerAdapter("127.0.0.1", port);
		}
	}
}