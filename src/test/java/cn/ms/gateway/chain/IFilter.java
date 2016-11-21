package cn.ms.gateway.chain;

public interface IFilter<REQ, RES> {

	//$NON-NLS-辅助类动作$

	/**
	 * 刷新<br>
	 * 用于动态变更过滤器中的全局变量
	 * 
	 * @throws Exception
	 */
	void refresh() throws Exception;

	/**
	 * 注入<br>
	 * 用于用户注入自定义的对象
	 * 
	 * @param cus
	 * @throws Exception
	 */
	<CUS> void inject(CUS cus) throws Exception;;

	/**
	 * 校验权限<br>
	 * 
	 * @param req
	 * @param res
	 * @param args
	 * @return
	 * @throws Exception
	 */
	boolean check(REQ req, RES res, Object... args) throws Exception;

	//$NON-NLS-执行顺序: before --> doFilter --> after $

	/**
	 * 请求执行前<br>
	 * 
	 * @param req
	 * @param res
	 * @throws Throwable
	 */
	void before(REQ req, RES res, Object... args) throws Throwable;

	/**
	 * 执行责任链调度<br>
	 * 
	 * @param chain
	 * @param req
	 * @param res
	 * @param args
	 * @throws Throwable
	 */
	void doFilter(FilterChain<REQ, RES> chain, REQ req, RES res, Object... args)
			throws Throwable;

	/**
	 * 请求响应前
	 * 
	 * @param req
	 * @param res
	 * @param args
	 * @throws Throwable
	 */
	void after(REQ req, RES res, Object... args) throws Throwable;

}
