package cn.ms.gateway.core.filter.route;

import cn.ms.gateway.base.event.IEvent;
import cn.ms.gateway.base.filter.FilterType;
import cn.ms.gateway.base.filter.IFilter;
import cn.ms.gateway.common.annotation.Filter;
import cn.ms.gateway.common.log.Logger;
import cn.ms.gateway.common.log.LoggerFactory;
import cn.ms.gateway.entity.GatewayREQ;
import cn.ms.gateway.entity.GatewayRES;

@Filter(value = FilterType.ROUTE, order = 300)
public class HttpProxyRouteFilter implements IFilter<GatewayREQ, GatewayRES> {

	private static final Logger logger=LoggerFactory.getLogger(HttpProxyRouteFilter.class);
	
	IEvent event;

	public void setEvent(IEvent event) {
		this.event=event;
	}

	@Override
	public boolean check(GatewayREQ req, GatewayRES res, Object... args) {
		return true;
	}

	@Override
	public GatewayRES run(GatewayREQ req, GatewayRES res, Object... args) {
		try {
			event.publish(req);
		} catch (Throwable t) {
			logger.error(t, "发布事件异常: %s",t.getMessage());
		}
		
		return null;
	}

}
