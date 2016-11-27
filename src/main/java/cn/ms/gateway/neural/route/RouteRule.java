package cn.ms.gateway.neural.route;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;

import cn.ms.gateway.common.ConcurrentHashSet;

/**
 * 路由规则
 * 
 * @author lry
 */
public class RouteRule {

	public static final String RULE_SEQ = "@";
	public static final String ROUTE_SERVICE_SEQ = ";";
	public static final String ROUTE_SERVICE_VERSION_SEQ = ":";

	// 路由规则“渠道ID@机构ID@交易类型@交易码@...”
	private String rules;
	// 数据结构:Map<service:version, Set<ip:port>>
	private ConcurrentHashMap<String, ConcurrentHashSet<InetSocketAddress>> serviceApps;

	public String getRules() {
		return rules;
	}

	public void setRules(String rules) {
		this.rules = rules;
	}

	public ConcurrentHashMap<String, ConcurrentHashSet<InetSocketAddress>> getServiceApps() {
		return serviceApps;
	}

	public void setServiceApps(
			ConcurrentHashMap<String, ConcurrentHashSet<InetSocketAddress>> serviceApps) {
		this.serviceApps = serviceApps;
	}

}
