package cn.ms.gateway.core.filter.pre;

import cn.ms.gateway.base.filter.Filter;
import cn.ms.gateway.base.filter.FilterType;
import cn.ms.gateway.base.filter.IFilter;
import cn.ms.gateway.entity.Request;
import cn.ms.gateway.entity.Response;

/**
 * 服务ID校验
 * 
 * @author lry
 */
@Filter(value = FilterType.PRE, order = 110)
public class ServiceIdPreFilter extends IFilter<Request, Response> {

	@Override
	public boolean doBefore(Request req, Response res, Object... args) throws Throwable {
		return true;
	}

}
