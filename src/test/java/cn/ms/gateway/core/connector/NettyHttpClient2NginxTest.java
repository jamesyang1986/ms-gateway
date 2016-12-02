package cn.ms.gateway.core.connector;

import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpHeaders.Names;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.ms.gateway.core.processor.connector.NettyHttpClient;
import cn.ms.gateway.core.processor.connector.NettyHttpRequest;
import cn.ms.gateway.core.processor.connector.response.NettyHttpResponse;
import cn.ms.gateway.core.processor.connector.response.NettyHttpResponseFuture;

@SuppressWarnings("deprecation")
public class NettyHttpClient2NginxTest {

	public static void main(String[] args) {
		try {
			final String postUrl = "http://127.0.0.1:8844";
			final String postContent = "请求报文";// json format
	        Map<String, Integer> maxPerRoute = new HashMap<String, Integer>();
	        maxPerRoute.put("127.0.0.1:8844", 10000);
	        final NettyHttpClient client = new NettyHttpClient.ConfigBuilder()
	            .maxIdleTimeInMilliSecondes(200 * 1000).maxPerRoute(maxPerRoute)
	            .connectTimeOutInMilliSecondes(30 * 1000).build();
	        
			Perf perf = new Perf(){ 
				public TaskInThread buildTaskInThread() {
					return new TaskInThread(){
						public void initTask() throws Exception {
						}
						
						public void doTask() throws Exception {
							final NettyHttpRequest request = new NettyHttpRequest()
					        .header(Names.HOST, "127.0.0.1")
					        .header(HttpHeaders.Names.CONTENT_TYPE, "text/html; charset=UTF-8").uri(postUrl)
					        .uri(postUrl).content(postContent, Charset.forName("UTF-8"));

					        NettyHttpResponseFuture responseFuture = client.doPost(request);
					        NettyHttpResponse response = (NettyHttpResponse) responseFuture.get(1,TimeUnit.SECONDS);
					        
							if(!response.getResponseBody().contains("I am OK")){
								throw new RuntimeException();
							}
						}
					};
				} 
			}; 
			perf.loopCount = 1000000;
			perf.threadCount = 16;
			perf.logInterval = 10000;
			perf.run();
			perf.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
