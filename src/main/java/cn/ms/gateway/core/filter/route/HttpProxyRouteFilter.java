package cn.ms.gateway.core.filter.route;

import cn.ms.gateway.base.annotation.Filter;
import cn.ms.gateway.base.filter.IFilter;
import cn.ms.gateway.base.type.FilterType;
import cn.ms.gateway.core.disruptor.IDisruptor;
import cn.ms.gateway.core.entity.GatewayREQ;
import cn.ms.gateway.core.entity.GatewayRES;

@Filter(value = FilterType.ROUTE, order = 300)
public class HttpProxyRouteFilter implements IFilter<GatewayREQ, GatewayRES> {

	IDisruptor disruptor = null;

	public HttpProxyRouteFilter() {
		
	}

	@Override
	public boolean check(GatewayREQ req, GatewayRES res, Object... args) {
		return true;
	}

	@Override
	public GatewayRES run(GatewayREQ req, GatewayRES res, Object... args) {
		try {
			disruptor.publish(req);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
