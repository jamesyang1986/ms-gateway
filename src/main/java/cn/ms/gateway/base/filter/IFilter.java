package cn.ms.gateway.base.filter;

public interface IFilter<REQ, RES> {

	/**
	 * 初始化
	 * 
	 * @throws Exception
	 */
	void init() throws Exception;
	
	/**
	 * Whether to perform filtering
	 * 
	 * @param req
	 * @param res
	 * @return
	 */
	boolean check(REQ req, RES res, Object...args);

	/**
	 * Filtering logic processing
	 * 
	 * @param req
	 * @param res
	 * @return
	 */
	RES run(REQ req, RES res, Object...args);

}
