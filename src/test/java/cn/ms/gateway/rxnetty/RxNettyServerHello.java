package cn.ms.gateway.rxnetty;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.netty.RxNetty;
import io.reactivex.netty.protocol.http.server.HttpServer;

import java.nio.charset.Charset;

public class RxNettyServerHello {

	public static void main(String[] args) {
		try {
			HttpServer<ByteBuf, ByteBuf> server = RxNetty.createHttpServer(8080, (request, response) -> {
	            System.out.println("Server => Request: " + request.getPath());
	            try {
	                response.setStatus(HttpResponseStatus.OK);
	                response.writeString("Path Requested =>: " + request.getPath() + '\n');
	                return response.close();
	            } catch (Throwable e) {
	                System.err.println("Server => Error [" + request.getPath() + "] => " + e);
	                response.setStatus(HttpResponseStatus.BAD_REQUEST);
	                response.writeString("Error 500: Bad Request\n");
	                return response.close();
	            }
	        });
	        server.startAndWait();
	        
	        RxNetty.createHttpGet("http://localhost:8080/data")
	               .flatMap(response -> response.getContent())
	               .map(data -> "Client => " + data.toString(Charset.defaultCharset()))
	               .toBlocking().forEach(System.out::println);
//	        server.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
