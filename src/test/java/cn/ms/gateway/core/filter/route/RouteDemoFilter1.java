package cn.ms.gateway.core.filter.route;

import cn.ms.gateway.base.filter.Filter;
import cn.ms.gateway.base.filter.FilterType;
import cn.ms.gateway.base.filter.IFilter;
import cn.ms.gateway.entity.Request;
import cn.ms.gateway.entity.Response;

@Filter(value=FilterType.ROUTE, order=200)
public class RouteDemoFilter1 extends IFilter<Request, Response>{

	@Override
	public boolean doBefore(Request req, Response res, Object... args) throws Throwable {
		req.setData(req.getData()+"--->(before)"+getClass().getSimpleName());
		return true;
	}

	@Override
	public void doAfter(Request req, Response res, Object... args) throws Throwable {
		req.setMsg(req.getMsg()+"--->(after)"+getClass().getSimpleName());
	}

}
