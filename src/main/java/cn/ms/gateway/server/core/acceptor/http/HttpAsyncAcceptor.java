package cn.ms.gateway.server.core.acceptor.http;

import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.timeout.ReadTimeoutHandler;
import cn.ms.gateway.server.core.NestyOptionProvider;
import cn.ms.gateway.server.core.NestyOptions;
import cn.ms.gateway.server.core.NestyServer;
import cn.ms.gateway.server.core.acceptor.AsyncAcceptor;
import cn.ms.gateway.server.core.acceptor.AsyncRequestHandler;

import java.util.concurrent.TimeUnit;

public class HttpAsyncAcceptor extends AsyncAcceptor {

    public HttpAsyncAcceptor(NestyServer nestyServer, String address, int port) {
        super(nestyServer, address, port);
    }

    protected void protocolPipeline(ChannelPipeline pipeline, NestyOptionProvider options) {
        pipeline.addLast("nesty-timer", new ReadTimeoutHandler(options.option(NestyOptions.TCP_TIMEOUT), TimeUnit.MILLISECONDS))
                .addLast("nesty-http-decoder", new HttpRequestDecoder())
                .addLast("nesty-http-aggregator", new HttpObjectAggregator(options.option(NestyOptions.MAX_PACKET_SIZE)))
                .addLast("nesty-request-poster", AsyncRequestHandler.build())
                .addLast("nesty-http-encoder", new HttpResponseEncoder());
    }
}
