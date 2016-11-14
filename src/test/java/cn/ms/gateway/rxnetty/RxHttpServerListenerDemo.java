package cn.ms.gateway.rxnetty;

import com.alibaba.fastjson.JSON;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.netty.RxNetty;
import io.reactivex.netty.protocol.http.server.HttpServer;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import io.reactivex.netty.protocol.http.server.RequestHandler;
import io.reactivex.netty.spectator.SpectatorEventsListenerFactory;
import io.reactivex.netty.spectator.http.HttpServerListener;
import rx.Observable;

public class RxHttpServerListenerDemo {

	public static void main(String[] args) {
		SpectatorEventsListenerFactory factory = new SpectatorEventsListenerFactory();
		RxNetty.useMetricListenersFactory(factory);
		HttpServer<ByteBuf, ByteBuf> server = RxNetty.createHttpServer(8080, new RequestHandler<ByteBuf, ByteBuf>() {
			@Override
			public Observable<Void> handle(HttpServerRequest<ByteBuf> request, HttpServerResponse<ByteBuf> response) {
				try {
					System.out.println("=======");
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
		HttpServerListener listener = factory.forHttpServer(server);
		while (true) {
			try {
				System.out.println(JSON.toJSONString(listener));
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
}
