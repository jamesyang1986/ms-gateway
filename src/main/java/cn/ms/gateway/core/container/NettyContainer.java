package cn.ms.gateway.core.container;

import cn.ms.gateway.base.container.support.AbstractContainer;
import cn.ms.gateway.base.interceptor.Interceptor;
import cn.ms.gateway.core.entity.GatewayREQ;
import cn.ms.gateway.core.entity.GatewayRES;
import cn.ms.gateway.core.interceptor.GatewayInterceptor;

/**
 * 基于Netty实现的网关容器
 * 
 * @author lry
 */
public class NettyContainer extends AbstractContainer<GatewayREQ, GatewayRES> {

	Interceptor<GatewayREQ, GatewayRES> interceptor = new GatewayInterceptor(this);
	
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
		GatewayREQ interceptorGatewayREQ =req;
		GatewayRES interceptorGatewayRES =null;
		try {
			GatewayREQ beforeGatewayREQ = interceptor.before(interceptorGatewayREQ, args);
			if(beforeGatewayREQ!=null){
				interceptorGatewayREQ=beforeGatewayREQ;
			}
			interceptorGatewayRES = interceptor.interceptor(interceptorGatewayREQ, args);
		} finally {
			GatewayRES afterGatewayRES = interceptor.after(interceptorGatewayREQ, interceptorGatewayRES, args);
			if(afterGatewayRES!=null){
				interceptorGatewayRES=afterGatewayRES;
			}
		}
		
		return interceptorGatewayRES;
	}

	@Override
	public void destory() throws Exception {
		// TODO Auto-generated method stub

	}

}
