package cn.ms.gateway.demo;

import cn.ms.gateway.base.filter.FilterContext;
import cn.ms.gateway.entity.Request;
import cn.ms.gateway.entity.Response;

public class Test1Filter extends FilterContext<Request, Response> {

	@Override
	public void init() throws Exception {
	}
	
	
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
	
	@Override
	public void destroy() throws Exception {
		
	}
	
}
