package cn.ms.gateway.core.filter.pre;

import java.util.HashMap;
import java.util.Map;

import cn.ms.gateway.base.filter.FilterType;
import cn.ms.gateway.base.filter.MSFilter;
import cn.ms.gateway.common.Conf;
import cn.ms.gateway.common.annotation.Filter;
import cn.ms.gateway.entity.GatewayREQ;
import cn.ms.gateway.entity.GatewayRES;
import cn.ms.gateway.neural.blackwhitelist.BlackWhiteIPListFactory;
import cn.ms.gateway.neural.blackwhitelist.BlackWhiteIPListType;

/**
 * IP 白名单过滤
 * 
 * @author lry
 */
@Filter(value = FilterType.PRE, order = 100)
public class IpBlackWhiteListPreFilter extends MSFilter<GatewayREQ, GatewayRES> {

	private BlackWhiteIPListFactory ipFilterFactory = new BlackWhiteIPListFactory();

	@Override
	public void init() throws Exception {
		this.refresh();
	}

	@Override
	public void refresh() throws Exception {
		Map<BlackWhiteIPListType, String> bwIPs = new HashMap<BlackWhiteIPListType, String>();
		bwIPs.put(BlackWhiteIPListType.BLACKLIST, Conf.CONF.getBlackListIP());
		bwIPs.put(BlackWhiteIPListType.WHITELIST, Conf.CONF.getWhiteListIP());

		ipFilterFactory.setBlackWhiteIPs(bwIPs);
	}

	@Override
	public boolean check(GatewayREQ req, GatewayRES res, Object... args) throws Exception {
		return true;
	}

	@Override
	public GatewayRES run(GatewayREQ req, GatewayRES res, Object... args) throws Exception {
		String clientIP = req.getClientHost();
		if (clientIP == null || clientIP.length() < 1) {
			ipFilterFactory.check(BlackWhiteIPListType.BLACKLIST, req.getClientHost());
		} else {

		}

		return null;
	}

}
