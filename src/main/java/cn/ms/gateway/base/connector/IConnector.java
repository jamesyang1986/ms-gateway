package cn.ms.gateway.base.connector;

import cn.ms.gateway.base.IAdapter;
import cn.ms.gateway.core.connector.IConnectorCallback;
import cn.ms.gateway.core.entity.GatewayREQ;

public interface IConnector extends IAdapter {

	void connect(GatewayREQ req, IConnectorCallback callback, Object... args) throws Throwable;

}
