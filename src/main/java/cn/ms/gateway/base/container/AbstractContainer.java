package cn.ms.gateway.base.container;

import cn.ms.gateway.base.IGateway;

/**
 * 抽象容器
 * 
 * @author lry
 */
public abstract class AbstractContainer<REQ, RES> implements IContainer<REQ, RES> {

	protected IGateway<REQ, RES> gateway = null;

	@Override
	public void setGateway(IGateway<REQ, RES> gateway) {
		this.gateway = gateway;		
	}

	public RES sendGatewayHandler(REQ req, Object... args) throws Exception {
		return gateway.handler(req, args);
	}

}
