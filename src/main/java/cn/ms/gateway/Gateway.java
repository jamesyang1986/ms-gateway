package cn.ms.gateway;

import cn.ms.gateway.core.GatewayFilterContext;
import cn.ms.netty.server.core.NestyOptions;
import cn.ms.netty.server.core.NestyServer;
import cn.ms.netty.server.core.acceptor.AsyncServerProvider;
import cn.ms.netty.server.core.protocol.NestyProtocol;

public enum Gateway {

	INSTANCE;

	GatewayFilterContext gfc;

	/**
	 * 初始化
	 */
	public void init() {
		try {
			gfc = new GatewayFilterContext();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 启动
	 */
	public void startup() {
		try {
			// 1. create httpserver
			NestyServer server = AsyncServerProvider.builder()
					.address("127.0.0.1").port(8080)
					.service(NestyProtocol.HTTP);

			// 2. choose http params. this is unnecessary
			server.option(NestyOptions.IO_THREADS,
					Runtime.getRuntime().availableProcessors())
					.option(NestyOptions.WORKER_THREADS, 128)
					.option(NestyOptions.TCP_BACKLOG, 1024)
					.option(NestyOptions.TCP_NODELAY, true);

			// 3. scan defined controller class with package name
			server.scanHttpController("cn.ms.gateway");

			// 4. start http server
			if (server.start()) {
				System.err.println("GatewayServer run successed.");
			} else {
				System.out.println("GatewayServer run failed.");
			}

			server.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关闭
	 */
	public void shutdown() {

	}

	public static void main(String[] args) {
		INSTANCE.init();
		INSTANCE.startup();
	}

}
