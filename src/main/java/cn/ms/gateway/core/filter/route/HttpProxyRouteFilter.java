package cn.ms.gateway.core.filter.route;

import io.netty.handler.codec.http.HttpResponse;
import cn.ms.gateway.base.filter.FilterType;
import cn.ms.gateway.base.filter.IFilter;
import cn.ms.gateway.base.processer.IProcesser;
import cn.ms.gateway.common.annotation.Filter;
import cn.ms.gateway.common.log.Logger;
import cn.ms.gateway.common.log.LoggerFactory;
import cn.ms.gateway.entity.GatewayREQ;
import cn.ms.gateway.entity.GatewayRES;

@Filter(value = FilterType.ROUTE, order = 300)
public class HttpProxyRouteFilter implements IFilter<GatewayREQ, GatewayRES> {

	private static final Logger logger=LoggerFactory.getLogger(HttpProxyRouteFilter.class);
	
	IProcesser<GatewayRES, GatewayRES, HttpResponse> processer;

	public void setEvent(IProcesser<GatewayRES, GatewayRES, HttpResponse> processer) {
		this.processer=processer;
	}
	
	@Override
	public void init() throws Exception {
		
	}

	@Override
	public boolean check(GatewayREQ req, GatewayRES res, Object... args) throws Exception {
		return true;
	}

	@Override
	public GatewayRES run(GatewayREQ req, GatewayRES res, Object... args) throws Exception {
		try {
			processer.publish(req);
		} catch (Throwable t) {
			logger.error(t, "发布事件异常: %s",t.getMessage());
		}
		
		return null;
	}

}
