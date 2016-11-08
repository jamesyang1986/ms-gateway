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
 * IP 黑/白名单过滤
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
		//$NON-NLS-收集黑名单$
		String blackList = Conf.CONF.getBlackListIPs();
		if (blackList != null) {
			if (blackList.length() > 0) {
				bwIPs.put(BlackWhiteIPListType.BLACKLIST, blackList);
			}
		}
		//$NON-NLS-收集白名单$
		String whiteList = Conf.CONF.getWhiteListIPs();
		if (whiteList != null) {
			if (whiteList.length() > 0) {
				bwIPs.put(BlackWhiteIPListType.WHITELIST, whiteList);
			}
		}
		//$NON-NLS-更新黑/白名单$
		if (!bwIPs.isEmpty()) {
			ipFilterFactory.setBlackWhiteIPs(bwIPs);
		}
	}

	@Override
	public boolean check(GatewayREQ req, GatewayRES res, Object... args)
			throws Exception {
		return true;
	}

	@Override
	public GatewayRES run(GatewayREQ req, GatewayRES res, Object... args)
			throws Exception {
		String clientIP = req.getClientHost();
		if (clientIP != null) {
			if (clientIP.length() > 0) {
				boolean isBlackList = ipFilterFactory.check(BlackWhiteIPListType.BLACKLIST, req.getClientHost());
				if(isBlackList){
					res=new GatewayRES();
					res.setContent(req.getClientHost()+"为黑名单IP");
					return res;
				}
				
				boolean isWhiteList = ipFilterFactory.check(BlackWhiteIPListType.WHITELIST, req.getClientHost());
				if(!isWhiteList){
					res=new GatewayRES();
					res.setContent(req.getClientHost()+"不为白名单IP");
					return res;
				}
			}
		}

		return null;
	}

}
