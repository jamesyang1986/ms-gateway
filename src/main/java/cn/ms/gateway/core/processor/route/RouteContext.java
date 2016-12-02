package cn.ms.gateway.core.processor.route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import com.weibo.api.motan.rpc.URL;

/**
 * 分组路由上下文
 * 数据结构：routeRule,{serviceId:version:sceneId;serviceId:version:sceneId;....},sysId,规则状态,变更时间,操作IP,备注
 * 
 * @author lry
 */
public class RouteContext {

	//$NON-NLS-常数定义$
	public static final String SVS_SEQ = ":";
	public static final String VERSION_KEY = "version";
	public static final String VERSION_DEF_VAL = "1.0.0";
	public static final String GROUP_KEY = "group";
	// 规则分割、匹配符号
	public static final String SEQ = "&";
	public static final String ANY_VAL = "*";

	// 请求头中需要路由的参数KEY集合
	private ConcurrentSkipListSet<String> routeRuleParamKeys = new ConcurrentSkipListSet<String>();

	// 数据结构：Map<规则串, Map<serviceId, serviceId:version:sceneId>>
	private ConcurrentHashMap<String, ConcurrentHashMap<String, RouteService>> routeRuleMap = new ConcurrentHashMap<String, ConcurrentHashMap<String, RouteService>>();
	// 数据结构：Map<serviceId:version:sceneId, Map<host:port, URL>
	private ConcurrentHashMap<String, ConcurrentHashMap<String, URL>> routeListMap = new ConcurrentHashMap<String, ConcurrentHashMap<String, URL>>();

	public RouteContext() {
		// 第一步:设置路由匹配规则参数KEY
		this.addRouteRuleParamKey("channelId");
		this.addRouteRuleParamKey("code");

		// 第二步:管理端添加路由规则
		Set<String> routeRules = new HashSet<String>();
		routeRules.add("weixin**");
		routeRules.add("A00*");
		Set<RouteService> routeDataSet = new HashSet<RouteService>();
		routeDataSet.add(RouteService.build("AtTran", "1.0.0", "S01"));
		routeDataSet.add(RouteService.build("atbillquery", "1.0.0", "S02"));
		this.addRouteRule(routeRules, routeDataSet);

		// 第三步:网关根据路由规则中的服务ID进行订阅服务提供者,并自动添加至服务清单中
		// 服务AtTran
		Set<URL> routeListSet1 = new HashSet<URL>();
		routeListSet1.add(URL.valueOf("http://10.24.1.62:29002/AtTran?version=1.0.0&group=S01"));
		routeListSet1.add(URL.valueOf("http://10.24.1.62:29002/AtTran?version=1.0.0&group=S01"));
		this.addRouteList("AtTran", "1.0.0", "S01", routeListSet1);
		// 服务atbillquery
		Set<URL> routeListSet2 = new HashSet<URL>();
		routeListSet2.add(URL.valueOf("http://10.24.1.62:29001/atbillquery?version=1.0.0&group=S02"));
		this.addRouteList("atbillquery", "1.0.0", "S02", routeListSet2);

		// 查看当前数据
		System.out.println("1>>>>>" + routeRuleParamKeys);
		System.out.println("2>>>>>" + routeRuleMap);
		System.out.println("3>>>>>" + routeListMap);

		// 第四步:模拟请求
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("channelId", "weixin09");
		parameters.put("code", "A003");
		System.out.println("路由选择结果清单:" + selectRouteList("atbillquery", parameters));
	}

	public static void main(String[] args) {
		new RouteContext();
	}

	/**
	 * 添加路由规则参数KEY
	 * 
	 * @param routeRuleParamKey
	 */
	public void addRouteRuleParamKey(String routeRuleParamKey) {
		this.routeRuleParamKeys.add(routeRuleParamKey);
	}

	/**
	 * 添加路由规则参数KEY
	 * 
	 * @param routeRuleParamKeys
	 */
	public void setRouteRuleParamKeys(Set<String> routeRuleParamKeys) {
		ConcurrentSkipListSet<String> tempRouteRuleParamKeys = new ConcurrentSkipListSet<String>();
		tempRouteRuleParamKeys.addAll(routeRuleParamKeys);

		this.routeRuleParamKeys = tempRouteRuleParamKeys;
	}

	/**
	 * 添加一条路由规则
	 * 
	 * @param routeRules
	 * @param routeServices
	 */
	public void addRouteRule(Set<String> routeRules, Set<RouteService> routeServices) {
		if (routeRules == null || routeRules.isEmpty()) {
			throw new RuntimeException("'routeRules'不能为空");
		}
		if (routeServices == null || routeServices.isEmpty()) {
			throw new RuntimeException("'routeDataSet'不能为空");
		}
		// 组装路由规则
		String routeRule = "";
		for (String tempRouteRule : routeRules) {
			routeRule += tempRouteRule + SEQ;
		}
		if (routeRule.endsWith(SEQ)) {
			routeRule = routeRule.substring(0, routeRule.length() - SEQ.length());
		}

		ConcurrentHashMap<String, RouteService> routeDataMap = routeRuleMap.get(routeRule);
		if (routeDataMap == null) {
			routeDataMap = new ConcurrentHashMap<String, RouteService>();
		}

		for (RouteService routeService : routeServices) {// routeData = serviceId:version:sceneId
			routeDataMap.put(routeService.getServiceId(), routeService);
		}

		routeRuleMap.put(routeRule, routeDataMap);
	}

	/**
	 * 添加一条服务清单记录
	 * 
	 * @param serviceId
	 *            服务ID
	 * @param version
	 *            服务版本号
	 * @param sceneId
	 *            场景ID
	 * @param routeListSet
	 */
	public void addRouteList(String serviceId, String version, String sceneId,
			Set<URL> routeListSet) {
		String svs = serviceId + SVS_SEQ + version + SVS_SEQ + sceneId;

		ConcurrentHashMap<String, URL> routeListDataMap = routeListMap.get(svs);
		if (routeListDataMap == null) {
			routeListDataMap = new ConcurrentHashMap<String, URL>();
		}

		for (URL url : routeListSet) {
			routeListDataMap.put(url.getHost() + ":" + url.getPort(), url);
		}

		routeListMap.put(svs, routeListDataMap);
	}

	/**
	 * 选择路由清单
	 * 
	 * @param serviceId
	 *            路由服务ID
	 * @param parameters
	 *            路由参数组
	 * @return
	 */
	public RouteResult selectRouteList(String serviceId, Map<String, String> parameters) {
		//$NON-NLS-第一步：数据校验$
		if (serviceId == null || serviceId.length() < 1) {
			// ResultType用法一:多个参数
			return RouteResult.build(ResultType.NOT_NULL_PARAM.wapper("serviceId", serviceId));
		} else if (parameters == null || parameters.isEmpty()) {
			return RouteResult.build(ResultType.NOT_NULL_PARAM.wapper("parameters", parameters));
		} else if (routeRuleParamKeys.isEmpty()) {
			return RouteResult.build(ResultType.NO_INIT_PARAM.wapper("routeRuleParamKeys", routeRuleParamKeys));
		} else if (routeRuleMap.isEmpty()) {
			return RouteResult.build(ResultType.NO_INIT_PARAM.wapper("routeRuleMap", routeRuleMap));
		} else if (routeListMap.isEmpty()) {
			return RouteResult.build(ResultType.NO_INIT_PARAM.wapper("routeListMap", routeListMap));
		}

		//$NON-NLS-第二步：实时组装路由KEY$
		String routeRuleRegex = "";
		for (String routeDataKey : routeRuleParamKeys) {
			String tempVal = parameters.get(routeDataKey);
			if (tempVal == null || tempVal.isEmpty()) {
				// ResultType用法二:单个参数
				return RouteResult.build(ResultType.REQPARAM_NOT_NULL.wapper(routeDataKey));
			}
			routeRuleRegex += tempVal + SEQ;
		}
		if (routeRuleRegex.endsWith(SEQ)) {// 去尾
			routeRuleRegex = routeRuleRegex.substring(0, routeRuleRegex.length() - SEQ.length());
		}
		if (routeRuleRegex == null || routeRuleRegex.length() < 1) {// 路由值结果校验
			return RouteResult.build(ResultType.ILLEGAL_REQ_ROUTERULE.wapper("routeRuleRegex==" + routeRuleRegex));
		}

		//$NON-NLS-第三步：路由KEY四重匹配$
		List<URL> routeURLs = new ArrayList<URL>();
		for (Map.Entry<String, ConcurrentHashMap<String, RouteService>> entry : routeRuleMap.entrySet()) {
			if (matches(routeRuleRegex, entry.getKey())) {// 进行路由KEY的匹配
				if (entry.getValue() == null || entry.getValue().isEmpty()) {
					return RouteResult.build(ResultType.ROUERULE_NOAVA_SERVICES.wapper(routeRuleRegex));
				}

				//$NON-NLS-第四步：根据服务ID查找serviceId:version:sceneId$
				RouteService routeService = entry.getValue().get(serviceId);
				if (routeService == null) {
					return RouteResult.build(ResultType.SVS_NOT_NULL.wapper());
				}

				//$NON-NLS-第五步：根据serviceId:version:sceneId来查找可用的服务提供者清单$
				ConcurrentHashMap<String, URL> routeListURLMap = routeListMap.get(routeService.buildKey());
				if (routeListURLMap == null || routeListURLMap.isEmpty()) {
					return RouteResult.build(ResultType.NO_AVA_PROVIDER.wapper(routeService.buildKey(), routeListURLMap));
				}

				//$NON-NLS-第六步：收集可用的服务提供者列表$
				routeURLs.addAll(routeListURLMap.values());
				break;
			}
		}

		// 最终结果校验
		if (routeURLs.isEmpty()) {
			// ResultType用法三:没有参数
			return RouteResult.build(ResultType.NOTFOUND_ROUTE_RULE);
		} else {
			// 路由成功
			return RouteResult.build(ResultType.ROUTE_SELECT_OK, routeURLs);
		}
	}

	/**
	 * 匹配校验
	 * 
	 * @param key
	 * @param regex
	 * @return
	 */
	private boolean matches(String rule, String regex) {
		//$NON-NLS-全匹配$
		if (rule.equals(regex)) {
			return true;
		}

		//$NON-NLS-*号匹配$
		if (rule.length() != regex.length()) {
			return false;
		}
		String ruleTemp = rule, regexTemp = regex;
		while (regexTemp.indexOf(ANY_VAL) > -1) {
			int index = regexTemp.indexOf(ANY_VAL);
			regexTemp = regexTemp.substring(0, index) + regexTemp.substring(index + 1);
			ruleTemp = ruleTemp.substring(0, index) + ruleTemp.substring(index + 1);
		}
		if (ruleTemp.equals(regexTemp)) {
			return true;
		}

		//$NON-NLS-号段匹配$
		// TODO

		//$NON-NLS-正则匹配$
		if (rule.matches(regex)) {
			return true;
		}

		return false;
	}

}
