package cn.ms.gateway.core.connector;

import cn.ms.gateway.base.adapter.IHandler;
import cn.ms.gateway.base.connector.IConnector;
import cn.ms.gateway.entity.GatewayREQ;
import cn.ms.gateway.entity.GatewayRES;

public class NettyConnector implements IConnector<GatewayREQ, GatewayRES> {

	IHandler<GatewayREQ, GatewayRES> handler=new NettyConnectorHandler();

	@Override
	public IHandler<GatewayREQ, GatewayRES> getConnectorHandler() {
		return handler;
	}
	
	@Override
	public void init() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void start() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void shutdown() throws Exception {
		// TODO Auto-generated method stub

	}

}
