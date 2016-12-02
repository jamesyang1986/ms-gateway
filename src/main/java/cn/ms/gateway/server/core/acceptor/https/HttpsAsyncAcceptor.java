package cn.ms.gateway.server.core.acceptor.https;

import io.netty.channel.ChannelPipeline;
import cn.ms.gateway.server.core.NestyOptionProvider;
import cn.ms.gateway.server.core.NestyServer;
import cn.ms.gateway.server.core.acceptor.AsyncAcceptor;

public class HttpsAsyncAcceptor extends AsyncAcceptor {

    public HttpsAsyncAcceptor(NestyServer nestyServer, String address, int port) {
        super(nestyServer, address, port);
    }

    protected void protocolPipeline(ChannelPipeline pipeline, NestyOptionProvider options) {
        throw new RuntimeException("not implement");
    }
}
