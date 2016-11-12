package cn.ms.gateway.nettyproxy.target;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * Created with IntelliJ IDEA. User: ASUS Date: 14-6-24 Time: 下午3:33 To change
 * this template use File | Settings | File Templates.
 */
public class Target {

	private String targetHost;
	private int targetPort;

	public Target(String targetHost, int targetPort) {
		this.targetHost = targetHost;
		this.targetPort = targetPort;
	}

	public void run() throws Exception {
		System.err.println("Target host:" + targetHost + " targetPort:"
				+ targetPort);

		// Configure the bootstrap.
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch)
								throws Exception {
							// 注册handler
							ch.pipeline().addLast(
									new ObjectEncoder(),
									new ObjectDecoder(ClassResolvers
											.cacheDisabled(null)),
									new TargetHandler());
						}
					}).bind(targetPort).sync().channel().closeFuture().sync();
			// 监听本地的一个端口，当有客户端请求时，然后向目标服务器发送请求，获取消息，然后发送给客户端
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws Exception {
		new Target("127.0.0.1", 12358).run();
	}
}