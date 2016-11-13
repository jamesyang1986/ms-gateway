package cn.ms.gateway.core.container;

import cn.ms.gateway.base.adapter.ICallback;
import cn.ms.gateway.base.adapter.IHandler;
import cn.ms.gateway.base.filter.IFilterFactory;
import cn.ms.gateway.entity.GatewayREQ;
import cn.ms.gateway.entity.GatewayRES;

/**
 * 基于Netty实现微服务网关容器
 * 
 * @author lry
 */
public class NettyContainerHandler implements IHandler<GatewayREQ, GatewayRES> {

	IFilterFactory<GatewayREQ, GatewayRES> filter;

	public NettyContainerHandler(IFilterFactory<GatewayREQ, GatewayRES> filter) {
		this.filter = filter;
	}

	@Override
	public void before(GatewayREQ req, Object... args) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public GatewayRES handler(GatewayREQ req,
			ICallback<GatewayREQ, GatewayRES> callback, Object... args)
			throws Exception {
		return filter.doFilter(req, args);
	}

	@Override
	public void after(GatewayREQ req, Object... args) throws Exception {
		// TODO Auto-generated method stub

	}

}
