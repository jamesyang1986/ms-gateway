package cn.ms.gateway.core.processor;

import cn.ms.gateway.base.container.support.AbstractContainer;
import cn.ms.gateway.base.interceptor.Interceptor;
import cn.ms.gateway.core.entity.GatewayREQ;
import cn.ms.gateway.core.entity.GatewayRES;

public class GatewayInterceptor implements Interceptor<GatewayREQ, GatewayRES> {

	AbstractContainer<GatewayREQ, GatewayRES> container;

	public GatewayInterceptor(AbstractContainer<GatewayREQ, GatewayRES> container) {
		this.container = container;
	}

	@Override
	public GatewayREQ before(GatewayREQ req, Object... args) throws Throwable {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GatewayRES interceptor(GatewayREQ req, Object... args) throws Throwable {
		GatewayRES res = null;
		
		return container.sendGatewayHandler(req, res, args);
	}

	@Override
	public GatewayRES after(GatewayREQ req, GatewayRES res, Object... args)
			throws Throwable {
		// TODO Auto-generated method stub
		return null;
	}

}
