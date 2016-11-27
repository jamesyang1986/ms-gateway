package cn.ms.gateway.neural.route.entity;

import java.util.List;


/**
 * 路由规则
 * 
 * @author lry
 */
public class RouteRule {

	// 路由规则“渠道ID@机构ID@交易类型@交易码@...”
	private String rules;
	// 数据结构:{service:version, Set<ip : port>}
	private List<ServiceApp> serviceApps;

	public String getRules() {
		return rules;
	}

	public void setRules(String rules) {
		this.rules = rules;
	}

	public List<ServiceApp> getServiceApps() {
		return serviceApps;
	}

	public void setServiceApps(List<ServiceApp> serviceApps) {
		this.serviceApps = serviceApps;
	}

}
