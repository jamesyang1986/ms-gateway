package cn.ms.gateway.base.processer;

import cn.ms.gateway.IAdapter;
import cn.ms.gateway.entity.GatewayREQ;

/**
 * 事件处理器
 * 
 * @author lry
 */
public interface IProcesser extends IAdapter {

	/**
	 * 发布事件
	 * 
	 * @param req
	 * @param args
	 * @throws Throwable
	 */
	void publish(GatewayREQ req, Object...args) throws Throwable;
	
}
