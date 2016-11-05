package cn.ms.gateway.core.event.support;

import cn.ms.gateway.entity.GatewayREQ;

import com.lmax.disruptor.EventFactory;

public class GatewayEventFactory implements EventFactory<GatewayREQ> {

	@Override
	public GatewayREQ newInstance() {
		return new GatewayREQ();
	}

}
