package cn.ms.gateway.base.connector;

import cn.ms.gateway.base.adapter.IAdapter;

/**
 * 路由连接器 <br>
 * <br>
 * 负责向后端服务器进行通讯
 * 
 * @author lry
 *
 * @param <REQ>
 * @param <RES>
 */
public interface IConnector<REQ, RES> extends IAdapter {

	/**
	 * 发送请求
	 * 
	 * @param req
	 * @param callback
	 * @param args
	 * @throws Exception
	 */
	RES connector(REQ req, Object... args) throws Exception;

}
