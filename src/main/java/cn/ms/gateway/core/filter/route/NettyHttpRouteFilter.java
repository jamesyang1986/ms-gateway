package cn.ms.gateway.core.filter.route;

import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpHeaders.Names;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.ms.gateway.base.filter.Filter;
import cn.ms.gateway.base.filter.FilterType;
import cn.ms.gateway.base.filter.IFilter;
import cn.ms.gateway.entity.Request;
import cn.ms.gateway.entity.Response;
import cn.ms.netty.client.NettyHttpClient;
import cn.ms.netty.client.NettyHttpRequest;
import cn.ms.netty.client.response.NettyHttpResponse;
import cn.ms.netty.client.response.NettyHttpResponseFuture;

/**
 * 基于Netty的HttpClient路由器
 * 
 * @author lry
 */
@SuppressWarnings("deprecation")
@Filter(value = FilterType.ROUTE, order = 300)
public class NettyHttpRouteFilter extends IFilter<Request, Response> {

	@Override
	public boolean doBefore(Request req, Response res, Object... args)
			throws Throwable {
		try {
			String postUrl = "http://127.0.0.1:8080/atbillquery";
			String postContent = "请求报文";// json format

			Map<String, Integer> maxPerRoute = new HashMap<String, Integer>();
			maxPerRoute.put("127.0.0.1:8844", 100);

			final NettyHttpClient client = new NettyHttpClient.ConfigBuilder()
					.maxIdleTimeInMilliSecondes(200 * 1000)
					.maxPerRoute(maxPerRoute)
					.connectTimeOutInMilliSecondes(30 * 1000).build();

			final NettyHttpRequest request = new NettyHttpRequest()
					.header(Names.HOST, "127.0.0.1")
					.header(HttpHeaders.Names.CONTENT_TYPE,
							"text/html; charset=UTF-8").uri(postUrl)
					.uri(postUrl)
					.content(postContent, Charset.forName("UTF-8"));

			NettyHttpResponseFuture responseFuture = client.doPost(request);
			NettyHttpResponse response = (NettyHttpResponse) responseFuture
					.get(1, TimeUnit.SECONDS);

			System.out.println("响应报文:" + response.getResponseBody());

			client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

}
