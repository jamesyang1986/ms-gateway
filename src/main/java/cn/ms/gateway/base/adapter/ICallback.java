package cn.ms.gateway.base.adapter;

/**
 * 回调函数
 * 
 * @author lry
 *
 * @param <REQ>
 * @param <RES>
 */
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

	/**
	 * 抛异常处理
	 * 
	 * @param t
	 */
	void exception(Throwable t);

}
