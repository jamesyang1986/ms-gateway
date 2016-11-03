package cn.ms.gateway.core.disruptor;

import cn.ms.gateway.base.IAdapter;
import cn.ms.gateway.core.entity.GatewayREQ;

/**
 * 事件处理
 * 
 * @author lry
 */
public interface IDisruptor extends IAdapter {

	/**
	 * 发布事件
	 * 
	 * @param req
	 * @param args
	 * @throws Throwable
	 */
	void publish(GatewayREQ req, Object...args) throws Throwable;
	
}
