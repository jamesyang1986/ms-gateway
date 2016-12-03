package cn.ms.gateway.server.core.acceptor;

public interface IoAcceptor {

    void eventLoop();

    void join() throws InterruptedException;

    void shutdown();
}
