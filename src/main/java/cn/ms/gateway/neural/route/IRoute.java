package cn.ms.gateway.neural.route;

import java.net.InetSocketAddress;
import java.util.Map;

import cn.ms.gateway.common.ConcurrentHashSet;
import cn.ms.gateway.neural.route.entity.RouteRule;

public interface IRoute {

	/**
	 * 添加路由规则参数<br>
	 * <br>
	 * 有序<br>
	 * 
	 * @param routeKey
	 */
	void addRouteDataKey(String routeKey);

	/**
	 * 添加一条规则<br>
	 * <br>
	 * 数据结构:{rules, Map<serviceId:version, Set<host : port>}
	 * 
	 * @param routeRule
	 */
	void addRouteRule(RouteRule routeRule);

	/**
	 * 根据服务ID和路由参数进行分组选择服务实例
	 * 
	 * @param serviceId
	 * @param parameters
	 * @return
	 */
	ConcurrentHashSet<InetSocketAddress> doSelectApps(String serviceId, Map<String, String> parameters);

}
