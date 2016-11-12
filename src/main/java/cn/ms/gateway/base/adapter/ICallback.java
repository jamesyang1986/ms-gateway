package cn.ms.gateway.base.adapter;

public interface ICallback<REQ, RES> extends IProcessor<REQ, RES> {

	/**
	 * 回调函数
	 * 
	 * @param req
	 * @param res
	 * @param args
	 * @throws Exception
	 */
	RES callback(REQ req, RES res, Object... args) throws Exception;

}
