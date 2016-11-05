package cn.ms.gateway.core.interceptor;

import cn.ms.gateway.base.interceptor.IInterceptor;
import cn.ms.gateway.common.annotation.Interceptor;
import cn.ms.gateway.entity.GatewayREQ;
import cn.ms.gateway.entity.GatewayRES;

/**
 * 网关拦截器
 * 
 * @author lry
 */
@Interceptor
public class GatewayInterceptor implements IInterceptor<GatewayREQ, GatewayRES> {

	@Override
	public void before(GatewayREQ req, Object... args) throws Throwable {
		
	}

	@Override
	public void after(GatewayREQ req, GatewayRES res, Object... args)
			throws Throwable {
	}

}
