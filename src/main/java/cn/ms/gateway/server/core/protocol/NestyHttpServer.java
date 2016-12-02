package cn.ms.gateway.server.core.protocol;

import cn.ms.gateway.server.core.NestyServer;
import cn.ms.gateway.server.core.acceptor.IOAcceptor;

public class NestyHttpServer extends NestyServer {

    /**
     * core io acceptor. implement http protocol
     */
    private IOAcceptor ioAcceptor;

    public NestyHttpServer ioAcceptor(IOAcceptor asyncAcceptor) {
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
