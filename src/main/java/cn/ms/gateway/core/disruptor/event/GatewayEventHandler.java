package cn.ms.gateway.core.disruptor.event;

import cn.ms.gateway.core.entity.GatewayREQ;

import com.lmax.disruptor.EventHandler;

public class GatewayEventHandler implements EventHandler<GatewayREQ> {
	
	@Override
	public void onEvent(GatewayREQ event, long sequence, boolean endOfBatch) {
		System.out.println("Event: " + event);
	}
	
}