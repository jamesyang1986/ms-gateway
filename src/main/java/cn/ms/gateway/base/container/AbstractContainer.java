package cn.ms.gateway.base.container;

import cn.ms.gateway.base.IGateway;

/**
 * 容器抽象模块
 * 
 * @author lry
 */
public abstract class AbstractContainer<REQ, RES> implements IContainer<REQ, RES> {

	protected IGateway<REQ, RES> gateway = null;
	
	public AbstractContainer(IGateway<REQ, RES> gateway) {
		this.gateway=gateway;
	}
	
	public RES sendGatewayHandler(REQ req, RES res, Object... args) throws Throwable {
		return gateway.handler(req, res, args);
	}
	
}
