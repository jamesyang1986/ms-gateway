package cn.ms.gateway.rxnetty;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.RxNetty;
import io.reactivex.netty.protocol.http.client.HttpClientResponse;

import java.nio.charset.Charset;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public final class RxNettyClientDemo {

	public static void main(String... args) throws InterruptedException {
        for (int i = 0; i < 1; i++) {
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
    }
}