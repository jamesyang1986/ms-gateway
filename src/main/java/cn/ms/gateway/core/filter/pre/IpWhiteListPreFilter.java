package cn.ms.gateway.core.filter.pre;

import java.net.InetSocketAddress;

import cn.ms.gateway.base.filter.FilterType;
import cn.ms.gateway.base.filter.IFilter;
import cn.ms.gateway.common.annotation.Filter;
import cn.ms.gateway.entity.GatewayREQ;
import cn.ms.gateway.entity.GatewayRES;

/**
 * IP 白名单过滤
 * 
 * @author lry
 */
@Filter(value = FilterType.PRE, order=100)
public class IpWhiteListPreFilter implements IFilter<GatewayREQ, GatewayRES> {

	@Override
	public void init() throws Exception {
		
	}
	
	@Override
	public boolean check(GatewayREQ req, GatewayRES res, Object... args) {
		return true;
	}

	@Override
	public GatewayRES run(GatewayREQ req, GatewayRES res, Object... args) {
		String clientIP = req.getRequest().headers().get("X-Forwarded-For");
		if (clientIP == null) {
			InetSocketAddress insocket = (InetSocketAddress) req.getCtx()
					.channel().remoteAddress();
			clientIP = insocket.getAddress().getHostAddress();
		}

		if (clientIP == null || clientIP.length() < 1) {

		} else {

		}

		return null;
	}

}
