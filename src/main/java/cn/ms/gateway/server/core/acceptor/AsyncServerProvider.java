package cn.ms.gateway.server.core.acceptor;

import cn.ms.gateway.server.core.NestyServer;
import cn.ms.gateway.server.core.acceptor.http.HttpAsyncAcceptor;
import cn.ms.gateway.server.core.protocol.NestyHttpServer;
import cn.ms.gateway.server.core.protocol.NestyProtocol;

/**
 * Async http server in netty eventloop
 */
public class AsyncServerProvider {

    private AsyncServerProvider() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        // local address
        String address = "0.0.0.0";
        // local port
        int port = 8080;

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder port(int port) {
            this.port = port;
            return this;
        }

        public NestyServer service(NestyProtocol protocol) {
            switch (protocol) {
            case HTTP:
                NestyHttpServer httpServer = new NestyHttpServer();
                httpServer.ioAcceptor(new HttpAsyncAcceptor(httpServer, address, port));
                return httpServer;
            case HTTPS:
            case SPDY:
            case HTTP2:
            default:
                throw new IllegalArgumentException(String.format("protocol %s is not support now !!", protocol));
            }
        }
    }
}
