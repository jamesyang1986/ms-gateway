package cn.ms.gateway.core.container;

import cn.ms.gateway.base.Gateway;
import cn.ms.gateway.base.IGateway;
import cn.ms.gateway.base.IProcessor;
import cn.ms.gateway.base.container.IContainer;
import cn.ms.gateway.core.entity.GatewayREQ;
import cn.ms.gateway.core.entity.GatewayRES;

/**
 * 基于Netty实现的网关容器
 * 
 * @author lry
 */
public class NettyContainer implements IContainer<GatewayREQ, GatewayRES> {

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

		IProcessor<GatewayREQ, GatewayRES> processor = new IProcessor<GatewayREQ, GatewayRES>() {

			@Override
			public GatewayREQ before(GatewayREQ req, Object... args)
					throws Throwable {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public GatewayRES processor(GatewayREQ req, Object... args) throws Throwable {
				IGateway<GatewayREQ, GatewayRES> gateway = new Gateway<GatewayREQ, GatewayRES>();
				GatewayRES res=null;
				
				return gateway.handler(req, res, args);
			}

			@Override
			public GatewayRES after(GatewayREQ req, GatewayRES res,
					Object... args) throws Throwable {
				// TODO Auto-generated method stub
				return null;
			}
		};

		GatewayREQ beforeGatewayREQ = processor.before(req, args);
		if (beforeGatewayREQ != null) {
			req = beforeGatewayREQ;
		}
		GatewayRES processorGatewayRES = processor.processor(beforeGatewayREQ,
				args);
		GatewayRES afterGatewayRES = processor.after(beforeGatewayREQ,
				processorGatewayRES, args);
		if (afterGatewayRES != null) {
			return afterGatewayRES;
		} else {
			return processorGatewayRES;
		}
	}

	@Override
	public void destory() throws Exception {
		// TODO Auto-generated method stub

	}

}
