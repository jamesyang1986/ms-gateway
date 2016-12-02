package cn.ms.gateway.core.connector;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import me.shenfeng.http.HttpClient;
import me.shenfeng.http.HttpClientConfig;
import me.shenfeng.http.HttpResponseFuture;

import org.jboss.netty.handler.codec.http.HttpResponse;

public class Test {

	public static void main(String[] args) {
		try {
			// Http client sample usage
			HttpClientConfig config = new HttpClientConfig();
			Map<String, Object> header = new HashMap<String, Object>();
			HttpClient client = new HttpClient(config);
			for (int i = 0; i < 1; ++i) {
	            final URI uri = new URI("http://shenfeng.me");
	            final HttpResponseFuture get = client.execGet(uri, header);
	            get.addListener(new Runnable() {
	                public void run() {
	                    try {
	                        HttpResponse resp = get.get();
	                        System.out.println(resp);
	                        // System.out.println(Utils.bodyString(resp));
	                    } catch (Exception e) {
	                    }
	                }
	            });
	            get.get();
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
