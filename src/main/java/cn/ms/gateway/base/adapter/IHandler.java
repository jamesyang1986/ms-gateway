package cn.ms.gateway.base.adapter;


public interface IHandler<REQ, RES> extends IProcessor<REQ, RES> {

	RES handler(REQ req, ICallback<REQ, RES> callback, Object... args) throws Exception;
	
}
