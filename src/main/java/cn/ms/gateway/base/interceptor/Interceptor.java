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
	 * @return
	 * @throws Throwable
	 */
	REQ before(REQ req, Object... args) throws Throwable;

	/**
	 * 拦截后处理
	 * 
	 * @param req
	 * @param args
	 * @return
	 * @throws Throwable
	 */
	RES after(REQ req, RES res, Object... args) throws Throwable;

}
