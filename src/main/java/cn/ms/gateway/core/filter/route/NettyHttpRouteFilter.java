package cn.ms.gateway.core.filter.route;

import cn.ms.gateway.base.filter.Filter;
import cn.ms.gateway.base.filter.FilterType;
import cn.ms.gateway.base.filter.IFilter;
import cn.ms.gateway.entity.Request;
import cn.ms.gateway.entity.Response;
import cn.ms.netty.client.NettyClient;
import cn.ms.netty.client.NettyClientFactory;
import cn.ms.netty.client.entity.CRequest;
import cn.ms.netty.client.entity.CResponse;
import cn.ms.netty.client.support.CURL;

/**
 * 基于Netty的HttpClient路由器
 * 
 * @author lry
 */
@Filter(value = FilterType.ROUTE, order = 300)
public class NettyHttpRouteFilter extends IFilter<Request, Response> {

	NettyClientFactory nettyClientFactory=new NettyClientFactory(); 
	
	@Override
	public boolean doBefore(Request req, Response res, Object... args) throws Throwable {
		try {
			String postUrl = "http://127.0.0.1:8080/atbillquery";
			CURL curl=CURL.valueOf(postUrl);
			NettyClient nettyClient = nettyClientFactory.getNettyClient(curl);
			CRequest crequest=new CRequest();
			crequest.setData(req.getHttpBody());
			CResponse respone = nettyClient.request(crequest);
			System.out.println("响应报文:"+respone.getData());
		} catch (Exception e) {
			throw new RuntimeException("远程路由异常");
		}
		
		return false;
	}

}
