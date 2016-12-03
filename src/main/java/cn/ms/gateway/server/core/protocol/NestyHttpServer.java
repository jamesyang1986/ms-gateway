package cn.ms.gateway.server.core.protocol;

import cn.ms.gateway.server.core.NestyServer;
import cn.ms.gateway.server.core.acceptor.IoAcceptor;

public class NestyHttpServer extends NestyServer {

    /**
     * core io acceptor. implement http protocol
     */
    private IoAcceptor ioAcceptor;

    public NestyHttpServer ioAcceptor(IoAcceptor asyncAcceptor) {
        this.ioAcceptor = asyncAcceptor;
        return this;
    }

    @Override
    public Boolean start() {
        try {
            // run event loop and joined
            ioAcceptor.eventLoop();

            return Boolean.TRUE;
        } catch (Exception e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }

    @Override
    public void join() throws InterruptedException {
        ioAcceptor.join();
    }

    @Override
    public void shutdown() {
        ioAcceptor.shutdown();
    }
}
