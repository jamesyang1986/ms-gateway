package cn.ms.gateway.base.connector;

import cn.ms.gateway.IAdapter;
import cn.ms.gateway.entity.GatewayREQ;

public interface IConnector<REQ, RES, BEF> extends IAdapter {

	void connect(GatewayREQ req, ICallback<REQ, RES, BEF> callback, Object... args) throws Throwable;

}
