package cn.ms.gateway.server.core;

/**
 * NestServer interface. global instance
 */
public interface Server {

    /**
     * startup the http service.
     *
     * @return true if listen service estanbulished or false on error
     */
    Boolean start();

    /**
     * keep servicing and block to waitting shutdown
     */
    void join() throws InterruptedException;

    /**
     * destroy the service
     */
    void shutdown();
}
