package cn.ms.gateway.chain.impl;

import cn.ms.gateway.chain.FilterContext;
import cn.ms.gateway.chain.entity.Request;
import cn.ms.gateway.chain.entity.Response;

public class Test2Filter extends FilterContext<Request, Response> {

	@Override
	public boolean check(Request req, Response res, Object... args) throws Exception {
		return true;
	}
	
	@Override
	public void before(Request req, Response resp, Object... args) throws Throwable {
		req.reqStr += "---"+getClass().getSimpleName()+"(before)---";
	}
	
	@Override
	public void after(Request req, Response resp, Object... args) throws Throwable {
		resp.respStr += "---"+getClass().getSimpleName()+"(after)---";
	}
	
}
