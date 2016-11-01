package cn.ms.gateway.base;

public interface IGateway<REQ, RES> extends Adaptor {

	/**
	 * The Start of Gateway.
	 * 
	 * @throws Exception
	 */
	void start() throws Exception;

	/**
	 * The Gateway handler center.
	 * 
	 * @param req
	 * @param args
	 * @return
	 * @throws Throwable
	 */
	RES handler(REQ req, Object... args) throws Throwable;

}
