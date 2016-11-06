package cn.ms.gateway.base.connector;

import cn.ms.gateway.IAdapter;
import cn.ms.gateway.entity.GatewayREQ;

public interface IConnector extends IAdapter {

	void connect(GatewayREQ req, IConnectorCallback callback, Object... args) throws Throwable;

}
