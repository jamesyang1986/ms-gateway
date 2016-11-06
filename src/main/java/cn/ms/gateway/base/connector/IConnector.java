package cn.ms.gateway.base.connector;

import cn.ms.gateway.IAdapter;
import cn.ms.gateway.entity.GatewayREQ;

/**
 * 连接器
 * 
 * @author lry
 *
 * @param <REQ>
 * @param <RES>
 * @param <BEF>
 */
public interface IConnector<REQ, RES, BEF> extends IAdapter {

	/**
	 * 执行连接
	 * 
	 * @param req
	 * @param callback
	 * @param args
	 * @throws Throwable
	 */
	void connect(GatewayREQ req, ICallback<REQ, RES, BEF> callback,
			Object... args) throws Throwable;

}
