package cn.ms.gateway.neural.route;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;

import cn.ms.gateway.common.ConcurrentHashSet;

/**
 * 分组路由<br>
 * <br>
 * 路由数据元:渠道ID、交易码、交易类型、机构号...<br>
 * 
 * @author lry
 */
public class RouteContext {

	//$NON-NLS-常数定义$
	public static final String SEQ = "@";
	public static final String ANY_VAL = "*";
	public static final String SERVICE_SEQ = ";";
	public static final String SERVICE_VERSION_SEQ = ":";

	// 数据结构:Set<渠道ID KEY, 机构号 KEY, 交易类型 KEY, 交易码 KEY, ...>
	private ConcurrentSkipListSet<String> routeDataKeyMap = new ConcurrentSkipListSet<String>();
	// 数据结构:Map<规则KEY(支持匹配), Map<serviceId, ServiceApp{serviceId@version, Set<ip:port>}>
	private ConcurrentSkipListMap<String, ConcurrentSkipListMap<String, ServiceApp>> routeRuleMap = new ConcurrentSkipListMap<String, ConcurrentSkipListMap<String, ServiceApp>>();

	public static void main(String[] args) {
		RouteContext rc=new RouteContext();
		
		//$NON-NLS-组装规则$
		RouteRule routeRule=new RouteRule();
		routeRule.setRules("weixin*@A00*");
		ConcurrentHashMap<String, ConcurrentHashSet<InetSocketAddress>> serviceApps=new ConcurrentHashMap<String, ConcurrentHashSet<InetSocketAddress>>();
		ConcurrentHashSet<InetSocketAddress> set1=new ConcurrentHashSet<InetSocketAddress>();
		set1.add(new InetSocketAddress("10.24.1.10", 8080));
		set1.add(new InetSocketAddress("10.24.1.11", 8080));
		set1.add(new InetSocketAddress("10.24.1.12", 8080));
		serviceApps.put("servic1:1.0.0", set1);
		ConcurrentHashSet<InetSocketAddress> set2=new ConcurrentHashSet<InetSocketAddress>();
		set2.add(new InetSocketAddress("10.24.1.13", 8080));
		set2.add(new InetSocketAddress("10.24.1.14", 8080));
		set2.add(new InetSocketAddress("10.24.1.15", 8080));
		serviceApps.put("servic2:1.0.0", set2);
		routeRule.setServiceApps(serviceApps);
		rc.addRouteRule(routeRule);
		System.out.println(rc.routeRuleMap);
		
		//设置路由数据结构
		rc.addRouteDataKey("channelId");
		rc.addRouteDataKey("code");
		
		//模拟请求
		Map<String, String> parameters=new HashMap<String, String>();
		parameters.put("channelId", "weixin3");
		parameters.put("code", "A001");
		//查找：指定渠道的某类交易码的服务分组
		System.out.println(rc.doSelectApps("servic1", parameters));
		
	}
	
	/**
	 * 添加路由规则参数<br>
	 * <br>
	 * 有序<br>
	 * @param routeKey
	 */
	public void addRouteDataKey(String routeKey) {
		routeDataKeyMap.add(routeKey);
	}
	
	/**
	 * 添加一条规则<br>
	 * <br>数据结构:{rules, Map<serviceId:version, Set<host : port>}
	 * 
	 * @param routeRule
	 */
	public void addRouteRule(RouteRule routeRule) {
		ConcurrentSkipListMap<String, ServiceApp> tempMap=new ConcurrentSkipListMap<String, ServiceApp>();
		for (Map.Entry<String, ConcurrentHashSet<InetSocketAddress>> serviceApp:routeRule.getServiceApps().entrySet()) {
			String serviceIdVersion=serviceApp.getKey();
			String serviceId=serviceIdVersion.substring(0, serviceIdVersion.indexOf(SERVICE_VERSION_SEQ));
			tempMap.put(serviceId, new ServiceApp(serviceIdVersion, serviceApp.getValue()));
		}
		
		routeRuleMap.put(routeRule.getRules(), tempMap);
	}

	public ConcurrentHashSet<InetSocketAddress> doSelectApps(String serviceId, Map<String, String> parameters) {
		//$NON-NLS-第一步：数据校验$
		if (serviceId == null || serviceId.length() < 1) {
			throw new RuntimeException("serviceId不能为空");
		} else if (parameters == null || parameters.isEmpty()) {
			throw new RuntimeException("parameters不能为空");
		} else if (routeDataKeyMap.isEmpty()) {
			throw new RuntimeException("routeDataKeyMap不能为空");
		} else if (routeRuleMap.isEmpty()) {
			throw new RuntimeException("routeRuleMap不能为空");
		}

		//$NON-NLS-第二步：实时组装路由KEY$
		String routeRuleRegex = "";
		for (String routeDataKey : routeDataKeyMap) {
			String tempVal = parameters.get(routeDataKey);
			if (tempVal == null || tempVal.isEmpty()) {
				throw new RuntimeException("非法请求");
			}
			routeRuleRegex += tempVal + SEQ;
		}
		if (routeRuleRegex.endsWith(SEQ)) {
			routeRuleRegex = routeRuleRegex.substring(0, routeRuleRegex.length() - SEQ.length());
		}

		//$NON-NLS-第三步：路由KEY四重匹配$
		for (Map.Entry<String, ConcurrentSkipListMap<String, ServiceApp>> entry : routeRuleMap.entrySet()) {
			if (matches(routeRuleRegex, entry.getKey())) {// 进行路由KEY的匹配
				ConcurrentSkipListMap<String, ServiceApp> serviceAppMap = entry.getValue();

				//$NON-NLS-第四步：根据消费服务ID查找可用的服务实例集$
				ServiceApp serviceApp = serviceAppMap.get(serviceId);
				if (serviceApp.getApps().isEmpty()) {
					throw new RuntimeException("没有可用的提供者");
				} else {
					return serviceApp.getApps();
				}
			}
		}

		throw new RuntimeException("没有找到可匹配的规则");
	}

	/**
	 * 匹配校验
	 * 
	 * @param key
	 * @param regex
	 * @return
	 */
	private boolean matches(String rule, String regex) {
		System.out.println(rule+"-->"+regex);
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

		//$NON-NLS-正则匹配$
		if (rule.matches(regex)) {
			return true;
		}

		return false;
	}
	
}
