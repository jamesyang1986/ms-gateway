package cn.ms.gateway.core.filter.route;

import io.netty.handler.codec.http.HttpResponse;
import cn.ms.gateway.base.filter.FilterType;
import cn.ms.gateway.base.filter.MSFilter;
import cn.ms.gateway.base.processer.IProcesser;
import cn.ms.gateway.common.annotation.Filter;
import cn.ms.gateway.common.log.Logger;
import cn.ms.gateway.common.log.LoggerFactory;
import cn.ms.gateway.entity.GatewayREQ;
import cn.ms.gateway.entity.GatewayRES;

/**
 * 网关核心远程路由过滤器
 * 
 * @author lry
 */
@Filter(value = FilterType.ROUTE, order = 300)
public class HttpProxyRouteFilter extends MSFilter<GatewayREQ, GatewayRES> {

	private static final Logger logger=LoggerFactory.getLogger(HttpProxyRouteFilter.class);
	
	IProcesser<GatewayRES, GatewayRES, HttpResponse> processer;

	@SuppressWarnings("unchecked")
	@Override
	public <MOD> void inject(MOD mod) {
		this.processer=(IProcesser<GatewayRES, GatewayRES, HttpResponse>) mod;
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
			t.printStackTrace();
			logger.error(t, "发布事件异常: %s",t.getMessage());
		}
		
		return null;
	}

}
