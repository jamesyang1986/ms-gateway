package cn.ms.gateway.server.core.protocol;

import cn.ms.gateway.server.core.NestyServer;
import cn.ms.gateway.server.core.acceptor.IOAcceptor;

public class NestyHttpsServer extends NestyServer {

    /**
     * core io acceptor. implement http protocol
     */
	public IOAcceptor ioAcceptor;

    public NestyHttpsServer ioAcceptor(IOAcceptor asyncAcceptor) {
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
