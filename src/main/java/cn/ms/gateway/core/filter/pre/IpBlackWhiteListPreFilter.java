package cn.ms.gateway.core.filter.pre;

import java.util.HashMap;
import java.util.Map;

import cn.ms.gateway.base.filter.annotation.Filter;
import cn.ms.gateway.base.filter.annotation.FilterType;
import cn.ms.gateway.base.filter.support.MSFilter;
import cn.ms.gateway.common.Conf;
import cn.ms.gateway.core.StatusCode;
import cn.ms.gateway.entity.GatewayREQ;
import cn.ms.gateway.entity.GatewayRES;
import cn.ms.gateway.neural.blackwhitelist.BlackWhiteListFactory;
import cn.ms.gateway.neural.blackwhitelist.BlackWhiteListType;

/**
 * IP 黑/白名单过滤
 * 
 * @author lry
 */
@Filter(value = FilterType.PRE, order = 100)
public class IpBlackWhiteListPreFilter extends MSFilter<GatewayREQ, GatewayRES> {

	private BlackWhiteListFactory ipFilterFactory = new BlackWhiteListFactory();

	@Override
	public void init() throws Exception {
		this.refresh();
	}

	@Override
	public void refresh() throws Exception {
		Map<BlackWhiteListType, String> bwIPs = new HashMap<BlackWhiteListType, String>();
		//$NON-NLS-收集黑名单$
		String blackList = Conf.CONF.getBlackList();
		if (blackList != null) {
			if (blackList.length() > 0) {
				bwIPs.put(BlackWhiteListType.BLACKLIST, blackList);
			}
		}
		//$NON-NLS-收集白名单$
		String whiteList = Conf.CONF.getWhiteList();
		if (whiteList != null) {
			if (whiteList.length() > 0) {
				bwIPs.put(BlackWhiteListType.WHITELIST, whiteList);
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
	public GatewayRES run(GatewayREQ req, GatewayRES res, Object... args) throws Exception {
		String clientIP = req.getClientHost();
		if(!Conf.CONF.getBlackWhiteList().equalsIgnoreCase(BlackWhiteListType.NON.toString())){//黑白名单模式未关闭
			if (clientIP == null || clientIP.length() <= 0) {//拦截非法绕过的请求
				return StatusCode.Wrapper(StatusCode.SC31_BLACKWHITE_ILLEGAL);
			}else{
				if (Conf.CONF.getBlackWhiteList().equalsIgnoreCase(BlackWhiteListType.WHITELIST.toString())) {// 白名单开关校验
					boolean isWhiteList = ipFilterFactory.check(BlackWhiteListType.WHITELIST, req.getClientHost());
					if (!isWhiteList) {//不是白名单
						return StatusCode.Wrapper(StatusCode.SC31_BLACKWHITE_WHITE, req.getClientHost());
					}
				} else if(Conf.CONF.getBlackWhiteList().equalsIgnoreCase(BlackWhiteListType.BLACKLIST.toString())){// 黑名单开关校验
					boolean isBlackList = ipFilterFactory.check(BlackWhiteListType.BLACKLIST, req.getClientHost());
					if (isBlackList) {//是黑名单
						return StatusCode.Wrapper(StatusCode.SC31_BLACKWHITE_BLACK, req.getClientHost());
					}
				}
			}
		}
		
		return null;
	}

}
