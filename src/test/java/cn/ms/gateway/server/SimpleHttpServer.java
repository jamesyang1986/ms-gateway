package cn.ms.gateway.server;

import cn.ms.gateway.server.common.exception.ControllerRequestMappingException;
import cn.ms.gateway.server.core.NestyOptions;
import cn.ms.gateway.server.core.NestyServer;
import cn.ms.gateway.server.core.acceptor.AsyncServerProvider;
import cn.ms.gateway.server.core.protocol.NestyProtocol;

public class SimpleHttpServer {

    public static void main(String[] args) throws ControllerRequestMappingException, InterruptedException {

        // 1. create httpserver
        NestyServer server = AsyncServerProvider.builder().address("127.0.0.1").port(8080)
                                                                        .service(NestyProtocol.HTTP);

        // 2. choose http params. this is unnecessary
        server.option(NestyOptions.IO_THREADS, Runtime.getRuntime().availableProcessors())
                .option(NestyOptions.WORKER_THREADS, 128)
                .option(NestyOptions.TCP_BACKLOG, 1024)
                .option(NestyOptions.TCP_NODELAY, true);

        // 3. scan defined controller class with package name
        server.scanHttpController("com.nesty.test.neptune")
                .scanHttpController("com.nesty.test.billing")
                .scanHttpController("org.nesty.example.httpserver.handler");

        // 4. start http server
        if (!server.start())
            System.err.println("NestServer run failed");

        server.join();

        // would not to reach here as usual ......
    }
}

