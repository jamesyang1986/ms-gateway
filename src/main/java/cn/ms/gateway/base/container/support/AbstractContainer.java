package cn.ms.gateway.base.container.support;

import cn.ms.gateway.base.Gateway;
import cn.ms.gateway.base.IGateway;
import cn.ms.gateway.base.container.IContainer;

/**
 * 容器抽象模块
 * 
 * @author lry
 */
public abstract class AbstractContainer<REQ, RES> implements IContainer<REQ, RES> {

	IGateway<REQ, RES> gateway = new Gateway<REQ, RES>();
	
	public RES sendGatewayHandler(REQ req, RES res, Object... args) throws Throwable {
		return gateway.handler(req, res, args);
	}
	
}
