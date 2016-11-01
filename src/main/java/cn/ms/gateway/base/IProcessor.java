package cn.ms.gateway.base;

/**
 * 网关核心处理接口
 * 
 * @author lry
 */
public interface IProcessor<REQ, RES> {

	/**
	 * 网关前处理
	 * 
	 * @param req
	 * @param args
	 * @return
	 * @throws Throwable
	 */
	RES before(REQ req, Object... args) throws Throwable;

	/**
	 * 网关处理
	 * 
	 * @param req
	 * @param args
	 * @return
	 * @throws Throwable
	 */
	RES processor(REQ req, Object... args) throws Throwable;

	/**
	 * 网关后处理
	 * 
	 * @param req
	 * @param args
	 * @return
	 * @throws Throwable
	 */
	RES after(REQ req, Object... args) throws Throwable;

}
