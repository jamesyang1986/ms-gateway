package cn.ms.gateway.core.interceptor;

import cn.ms.gateway.base.interceptor.IInterceptor;
import cn.ms.gateway.common.annotation.Interceptor;
import cn.ms.gateway.common.log.Logger;
import cn.ms.gateway.common.log.LoggerFactory;
import cn.ms.gateway.entity.GatewayREQ;
import cn.ms.gateway.entity.GatewayRES;

/**
 * Netty拦截器
 * 
 * @author lry
 */
@Interceptor
public class NettyInterceptor implements IInterceptor<GatewayREQ, GatewayRES> {

	public static final Logger logger=LoggerFactory.getLogger(NettyInterceptor.class);
	
	@Override
	public void before(GatewayREQ req, Object... args) throws Throwable {
		logger.info("请求进入");
	}

	@Override
	public void after(GatewayREQ req, GatewayRES res, Object... args)
			throws Throwable {
		logger.info("请求出去");
	}

}
