package cn.ms.gateway.rxnetty;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.netty.RxNetty;
import io.reactivex.netty.pipeline.PipelineConfigurator;
import io.reactivex.netty.protocol.http.server.HttpServer;
import io.reactivex.netty.protocol.http.server.HttpServerPipelineConfigurator;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import io.reactivex.netty.protocol.http.server.RequestHandler;
import rx.Observable;

public final class RxNettyServerDemo {

	public static void main(String... args) throws InterruptedException {
		PipelineConfigurator<HttpServerRequest<ByteBuf>, HttpServerResponse<ByteBuf>> configurator=new HttpServerPipelineConfigurator<ByteBuf, ByteBuf>();
		HttpServer<ByteBuf, ByteBuf> server = RxNetty.createHttpServer(8080, new RequestHandler<ByteBuf, ByteBuf>() {
			@Override
			public Observable<Void> handle(HttpServerRequest<ByteBuf> request, HttpServerResponse<ByteBuf> response) {
				try {
	                response.setStatus(HttpResponseStatus.OK);
	                response.writeString("服务端响应报文");
	                return response.close();
	            } catch (Throwable e) {
	                response.setStatus(HttpResponseStatus.BAD_REQUEST);
	                response.writeString("Error 500: Bad Request\n");
	                return response.close();
	            }
			}
		}, configurator);

        server.start();
        //server.shutdown();
    }
}