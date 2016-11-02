package cn.ms.gateway.core.container;

import cn.ms.gateway.base.IProcessor;
import cn.ms.gateway.base.container.support.AbstractContainer;
import cn.ms.gateway.core.entity.GatewayREQ;
import cn.ms.gateway.core.entity.GatewayRES;
import cn.ms.gateway.core.processor.GatewayProcessor;

/**
 * 基于Netty实现的网关容器
 * 
 * @author lry
 */
public class NettyContainer extends AbstractContainer<GatewayREQ, GatewayRES> {

	IProcessor<GatewayREQ, GatewayRES> processor = new GatewayProcessor(this);
	
	@Override
	public void init() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void start() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public GatewayRES handler(GatewayREQ req, Object... args) throws Throwable {
		GatewayREQ processorGatewayREQ =req;
		GatewayRES processorGatewayRES =null;
		try {
			GatewayREQ beforeGatewayREQ = processor.before(processorGatewayREQ, args);
			if(beforeGatewayREQ!=null){
				processorGatewayREQ=beforeGatewayREQ;
			}
			processorGatewayRES = processor.processor(processorGatewayREQ, args);
		} finally {
			GatewayRES afterGatewayRES = processor.after(processorGatewayREQ, processorGatewayRES, args);
			if(afterGatewayRES!=null){
				processorGatewayRES=afterGatewayRES;
			}
		}
		
		return processorGatewayRES;
	}

	@Override
	public void destory() throws Exception {
		// TODO Auto-generated method stub

	}

}
