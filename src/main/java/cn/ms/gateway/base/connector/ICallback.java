package cn.ms.gateway.base.connector;

/**
 * 回调接口
 * 
 * @author lry
 *
 * @param <REQ>
 * @param <RES>
 * @param <BEF>
 */
public interface ICallback<REQ, RES, BEF> {

	/**
	 * 回调前
	 * 
	 * @param bef
	 * @param args
	 * @throws Exception
	 */
	void before(BEF bef, Object... args) throws Exception;

	/**
	 * 回调函数
	 * 
	 * @param req
	 * @param args
	 * @return
	 * @throws Exception
	 */
	RES callback(REQ req, Object... args) throws Exception;

}
