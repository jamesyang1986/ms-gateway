package cn.ms.gateway;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.netty.RxNetty;
import io.reactivex.netty.protocol.http.client.HttpClientResponse;
import io.reactivex.netty.protocol.http.server.HttpServer;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import io.reactivex.netty.protocol.http.server.RequestHandler;

import java.nio.charset.Charset;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public final class RxNettyExample {

	public static void main(String... args) throws InterruptedException {
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
		});

        server.start();

        for (int i = 0; i < 10; i++) {
        	final int k=i;
        	RxNetty.createHttpGet("http://localhost:8080/data")
            .flatMap(new Func1<HttpClientResponse<ByteBuf>, Observable<ByteBuf>>() {
            	@Override
            	public Observable<ByteBuf> call(HttpClientResponse<ByteBuf> arg0) {
            		return arg0.getContent();
            	}
    		}).forEach(new Action1<ByteBuf>() {
    			@Override
    			public void call(ByteBuf data) {
    				System.out.println(k+"----"+data.toString(Charset.defaultCharset()));
    			}
    		});
		}
		
		System.out.println("+++++++++");
        
		for (int i = 0; i < 1000; i++) {
			Thread.sleep(1000);
		}
		
        server.shutdown();
    }
}