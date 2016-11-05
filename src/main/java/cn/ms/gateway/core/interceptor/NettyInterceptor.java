package cn.ms.gateway.core.interceptor;

import cn.ms.gateway.base.interceptor.Interceptor;
import cn.ms.gateway.entity.GatewayREQ;
import cn.ms.gateway.entity.GatewayRES;

/**
 * Netty拦截器
 * 
 * @author lry
 */
public class NettyInterceptor implements Interceptor<GatewayREQ, GatewayRES> {

	@Override
	public void before(GatewayREQ req, Object... args) throws Throwable {
		
	}

	@Override
	public void after(GatewayREQ req, GatewayRES res, Object... args)
			throws Throwable {

	}

}
