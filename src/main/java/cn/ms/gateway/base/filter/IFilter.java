package cn.ms.gateway.base.filter;

public interface IFilter<REQ, RES> {

	/**
	 * 初始化
	 * 
	 * @throws Exception
	 */
	void init() throws Exception;

	/**
	 * 过滤器是否执行校验
	 * 
	 * @param req
	 * @param res
	 * @param args
	 * @return
	 * @throws Exception
	 */
	boolean check(REQ req, RES res, Object... args) throws Exception;

	/**
	 * 过滤器执行器
	 * 
	 * @param req
	 * @param res
	 * @param args
	 * @return
	 * @throws Exception
	 */
	RES run(REQ req, RES res, Object... args) throws Exception;

}
