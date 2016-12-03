package cn.ms.gateway.core;

import cn.ms.gateway.base.FilterContext;
import cn.ms.gateway.entity.Request;
import cn.ms.gateway.entity.Response;

public class GatewayFilterContext extends FilterContext<Request, Response> {

	public GatewayFilterContext() throws Exception {
		super();
	}
	
	@Override
	public void filterChain(Request req, Response res, Object... args) throws Throwable {
		try {
			super.filterChain(req, res, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
