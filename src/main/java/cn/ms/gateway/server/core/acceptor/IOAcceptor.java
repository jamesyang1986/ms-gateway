package cn.ms.gateway.server.core.acceptor;

public interface IOAcceptor {

    void eventLoop();

    void join() throws InterruptedException;

    void shutdown();
}
