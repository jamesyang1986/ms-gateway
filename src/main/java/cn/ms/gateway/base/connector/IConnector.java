package cn.ms.gateway.base.connector;

import cn.ms.gateway.base.adapter.IAdapter;
import cn.ms.gateway.base.adapter.IHandler;

public interface IConnector<REQ, RES> extends IAdapter {

	IHandler<REQ, RES> getConnectorHandler();

}
