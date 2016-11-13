package cn.ms.gateway.base.adapter;

public interface ICallback<REQ, RES> {

	/**
	 * 容器请求处理前
	 * 
	 * @param req
	 * @param args
	 * @throws Exception
	 */
	void before(RES req, Object... args) throws Exception;

	/**
	 * 回调函数
	 * 
	 * @param res
	 * @param args
	 * @throws Exception
	 */
	RES callback(RES res, Object... args) throws Exception;
	
	/**
	 * 容器请求处理后
	 * 
	 * @param req
	 * @param args
	 * @throws Exception
	 */
	void after(RES req, Object... args) throws Exception;

}
