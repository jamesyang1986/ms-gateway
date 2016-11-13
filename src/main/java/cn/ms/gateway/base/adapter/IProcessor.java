package cn.ms.gateway.base.adapter;

public interface IProcessor<REQ, RES> {

	/**
	 * 容器请求处理前
	 * 
	 * @param req
	 * @param args
	 * @throws Exception
	 */
	void before(REQ req, Object... args) throws Exception;

	/**
	 * 容器请求处理后
	 * 
	 * @param req
	 * @param args
	 * @throws Exception
	 */
	void after(REQ req, Object... args) throws Exception;

}
