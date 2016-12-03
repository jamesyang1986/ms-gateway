package cn.ms.gateway.core.filter.route;

import cn.ms.gateway.base.filter.Filter;
import cn.ms.gateway.base.filter.FilterType;
import cn.ms.gateway.base.filter.IFilter;
import cn.ms.gateway.entity.Request;
import cn.ms.gateway.entity.Response;

/**
 * 基于Motan的路由
 * 
 * @author lry
 */
@Filter(value = FilterType.ROUTE, order = 100)
public class MotanRouteFilter extends IFilter<Request, Response> {

	@Override
	public boolean doBefore(Request req, Response res, Object... args)
			throws Throwable {

		return true;
	}

}
