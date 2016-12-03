package cn.ms.gateway.server.core.protocol;

import cn.ms.gateway.server.core.NestyServer;
import cn.ms.gateway.server.core.acceptor.IoAcceptor;

public class NestyHttpsServer extends NestyServer {

    /**
     * core io acceptor. implement http protocol
     */
	public IoAcceptor ioAcceptor;

    public NestyHttpsServer ioAcceptor(IoAcceptor asyncAcceptor) {
        this.ioAcceptor = asyncAcceptor;
        return this;
    }

    @Override
    public Boolean start() {
        return Boolean.FALSE;
    }

    @Override
    public void join() throws InterruptedException {
    }

    @Override
    public void shutdown() {
    }
}
