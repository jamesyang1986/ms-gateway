package cn.ms.gateway.core.connector;

import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpHeaders.Names;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import cn.ms.gateway.core.processor.connector.NettyHttpClient;
import cn.ms.gateway.core.processor.connector.entity.NettyHttpRequest;
import cn.ms.gateway.core.processor.connector.entity.NettyHttpResponse;
import cn.ms.gateway.core.processor.connector.entity.NettyHttpResponseFuture;

@SuppressWarnings("deprecation")
public class NettyHttpClientTest {

	public static void main(String[] args) {
		try {
			String postUrl = "http://127.0.0.1:8844/atbillquery";
	        String postContent = "请求报文";// json format

	        Map<String, Integer> maxPerRoute = new HashMap<String, Integer>();
	        maxPerRoute.put("127.0.0.1:8844", 100);

	        final NettyHttpClient client = new NettyHttpClient.ConfigBuilder()
	            .maxIdleTimeInMilliSecondes(200 * 1000).maxPerRoute(maxPerRoute)
	            .connectTimeOutInMilliSecondes(30 * 1000).build();

	        for (int i = 0; i < 10; i++) {
	        	final NettyHttpRequest request = new NettyHttpRequest()
		        .header(Names.HOST, "127.0.0.1")
		        .header(HttpHeaders.Names.CONTENT_TYPE, "text/html; charset=UTF-8").uri(postUrl)
		        .uri(postUrl).content(postContent, Charset.forName("UTF-8"));

		        NettyHttpResponseFuture responseFuture = client.doPost(request);
		        NettyHttpResponse response = (NettyHttpResponse) responseFuture.get();
		        
		        System.out.println("响应报文:"+response.getResponseBody());
			}
	        
	        client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
