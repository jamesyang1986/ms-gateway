package cn.ms.gateway.server.core.acceptor;

public interface IoAcceptor1 {

    void eventLoop();

    void join() throws InterruptedException;

    void shutdown();
}
