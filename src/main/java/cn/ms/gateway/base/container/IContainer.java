package cn.ms.gateway.base.container;

import cn.ms.gateway.base.IAdapter;

/**
 * 容器模块
 * 
 * @author lry
 */
public interface IContainer<REQ, RES> extends IAdapter {

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
