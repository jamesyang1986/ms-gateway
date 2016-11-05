package cn.ms.gateway.base.interceptor;

/**
 * 拦截器
 * 
 * @author lry
 */
public interface Interceptor<REQ, RES> {

	/**
	 * 拦截前处理
	 * 
	 * @param req
	 * @param args
	 * @throws Throwable
	 */
	void before(REQ req, Object... args) throws Throwable;

	/**
	 * 拦截后处理
	 * 
	 * @param req
	 * @param args
	 * @throws Throwable
	 */
	void after(REQ req, RES res, Object... args) throws Throwable;

}
