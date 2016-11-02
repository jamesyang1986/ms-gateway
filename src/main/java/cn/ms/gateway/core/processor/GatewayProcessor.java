package cn.ms.gateway.core.processor;

import cn.ms.gateway.base.IProcessor;
import cn.ms.gateway.base.container.support.AbstractContainer;
import cn.ms.gateway.core.entity.GatewayREQ;
import cn.ms.gateway.core.entity.GatewayRES;

public class GatewayProcessor implements IProcessor<GatewayREQ, GatewayRES> {

	AbstractContainer<GatewayREQ, GatewayRES> container;

	public GatewayProcessor(AbstractContainer<GatewayREQ, GatewayRES> container) {
		this.container = container;
	}

	@Override
	public GatewayREQ before(GatewayREQ req, Object... args) throws Throwable {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GatewayRES processor(GatewayREQ req, Object... args)
			throws Throwable {
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
