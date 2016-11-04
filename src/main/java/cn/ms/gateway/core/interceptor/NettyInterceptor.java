package cn.ms.gateway.core.interceptor;

import cn.ms.gateway.base.interceptor.Interceptor;
import cn.ms.gateway.core.entity.GatewayREQ;
import cn.ms.gateway.core.entity.GatewayRES;

/**
 * Netty拦截器
 * 
 * @author lry
 */
public class NettyInterceptor implements Interceptor<GatewayREQ, GatewayRES> {

	@Override
	public GatewayREQ before(GatewayREQ req, Object... args) throws Throwable {
		return null;
	}

	@Override
	public GatewayRES after(GatewayREQ req, GatewayRES res, Object... args)
			throws Throwable {
		return null;
	}

}
