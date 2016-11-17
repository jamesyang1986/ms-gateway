package cn.ms.gateway.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class NettyClient {
	
	private Bootstrap bootstrap;
	private NioEventLoopGroup workGroup;
	private ConcurrentHashMap<String, ChannelFuture> channelFutureMap=new ConcurrentHashMap<String, ChannelFuture>();

	public void start() throws Exception {
		try {
			workGroup = new NioEventLoopGroup();
			bootstrap = new Bootstrap();
			bootstrap.group(workGroup)
				.channel(NioSocketChannel.class)
				.handler(new ChannelInitializer<SocketChannel>() {
					protected void initChannel(SocketChannel socketChannel) throws Exception {
						ChannelPipeline p = socketChannel.pipeline();
						p.addLast(new EchoClientHandler());
					}
				});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Channel connect(String host, int port) throws Exception {
		String address=host+":"+port;
		ChannelFuture channelFuture = channelFutureMap.get(address);
		if(channelFuture==null){
			doConnect(host, port);
			channelFuture = channelFutureMap.get(address);
		}
		
		return channelFuture.channel();
	}
	
	protected void doConnect(final String host, final int port) throws Exception {
		final ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
		channelFuture.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture futureListener) throws Exception {
				if (futureListener.isSuccess()) {
					System.out.println("Connect to server successfully!");
					channelFutureMap.put(host+":"+port, channelFuture);
				} else {
					System.out.println("Failed to connect to server, try connect after 1s.");
					futureListener.channel().eventLoop().schedule(new Runnable() {
						@Override
						public void run() {
							try {
								doConnect(host, port);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}, 1, TimeUnit.SECONDS);
				}
			}
		});
	}

}