package cn.ms.gateway.core.connector;

import cn.ms.gateway.base.adapter.ICallback;
import cn.ms.gateway.base.adapter.IHandler;
import cn.ms.gateway.entity.GatewayREQ;
import cn.ms.gateway.entity.GatewayRES;

public class NettyConnectorHandler implements IHandler<GatewayREQ, GatewayRES> {

	@Override
	public void before(GatewayREQ req, Object... args) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public GatewayRES handler(GatewayREQ req,
			ICallback<GatewayREQ, GatewayRES> callback, Object... args)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void after(GatewayREQ req, Object... args) throws Exception {
		// TODO Auto-generated method stub

	}

}
