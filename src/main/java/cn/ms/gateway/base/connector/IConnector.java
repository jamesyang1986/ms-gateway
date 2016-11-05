package cn.ms.gateway.base.connector;

import cn.ms.gateway.base.IAdapter;
import cn.ms.gateway.entity.GatewayREQ;

public interface IConnector extends IAdapter {

	void connect(GatewayREQ req, IProxyCallback callback, Object... args) throws Throwable;

}
