package cn.ms.gateway.base.processer;

import cn.ms.gateway.IAdapter;
import cn.ms.gateway.base.connector.IConnector;
import cn.ms.gateway.entity.GatewayREQ;

/**
 * 事件处理器
 * 
 * @author lry
 */
public interface IProcesser<REQ, RES, BEF> extends IAdapter {

	void setConnector(IConnector<REQ, RES, BEF> connector);

	/**
	 * 发布事件
	 * 
	 * @param req
	 * @param args
	 * @throws Throwable
	 */
	void publish(GatewayREQ req, Object... args) throws Throwable;

}
