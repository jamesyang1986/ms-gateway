package cn.ms.gateway.core;

import cn.ms.gateway.base.FilterContext;
import cn.ms.gateway.core.entity.Request;
import cn.ms.gateway.core.entity.Response;
import cn.ms.gateway.core.filter.post.PostDemoFilter1;

public class FilterTest {

	public static void main(String[] args) throws Exception {
		FilterContext<Request, Response> chanChain = new FilterContext<Request, Response>();
		Request req = new Request();
		req.setData("");
		req.setMsg("");
		Response res = new Response();
		try {
			chanChain.filterChain(req, res);
		} catch (Throwable t) {
			t.printStackTrace();
		}
		
		//获取指定过滤器并刷新
		PostDemoFilter1 demoFilter1=chanChain.getFilter(PostDemoFilter1.class);
		System.out.println(demoFilter1);
		demoFilter1.ref("这是刷新时注入的对象");
	}

}
