package cn.ms.gateway.base;

public interface IFilter<REQ, RES> {

	/**
	 * Filter Name
	 * 
	 * @return
	 */
	String filterName();
	
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
