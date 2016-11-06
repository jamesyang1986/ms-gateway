package cn.ms.gateway.base.container;

import cn.ms.gateway.IAdapter;
import cn.ms.gateway.base.IGateway;

/**
 * 容器模块
 * 
 * @author lry
 */
public interface IContainer<REQ, RES> extends IAdapter {

	/**
	 * 设置网关
	 * 
	 * @param gateway
	 */
	void setGateway(IGateway<REQ, RES> gateway);

	/**
	 * 容器的请求入口
	 * 
	 * @param req
	 * @param args
	 * @return
	 * @throws Throwable
	 */
	RES handler(REQ req, Object... args) throws Throwable;

}
