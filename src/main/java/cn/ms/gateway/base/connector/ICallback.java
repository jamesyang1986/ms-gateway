package cn.ms.gateway.base.connector;

public interface ICallback<REQ, RES, BEF> {

	void before(BEF bef, Object... args) throws Exception;

	RES callback(REQ req, Object... args) throws Exception;

}
