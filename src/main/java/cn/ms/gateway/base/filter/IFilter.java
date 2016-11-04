package cn.ms.gateway.base.filter;

public interface IFilter<REQ, RES> {

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
