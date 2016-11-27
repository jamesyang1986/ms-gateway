package cn.ms.gateway.neural.route;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import cn.ms.gateway.common.ConcurrentHashSet;
import cn.ms.gateway.neural.route.entity.RouteRule;
import cn.ms.gateway.neural.route.entity.ServiceApp;
import cn.ms.gateway.neural.route.exception.IllegalRequestException;
import cn.ms.gateway.neural.route.exception.NoFoundProviderException;
import cn.ms.gateway.neural.route.exception.NoFoundRouteRuleException;
import cn.ms.gateway.neural.route.exception.ParameNoInitException;

/**
 * 分组路由<br>
 * <br>
 * 路由数据元:渠道ID、交易码、交易类型、机构号...<br>
 * 
 * @author lry
 */
public class RouteContext implements IRoute {

	//$NON-NLS-常数定义$
	public static final String SEQ = "@";
	public static final String ANY_VAL = "*";
	public static final String SERVICE_SEQ = ";";
	public static final String SERVICE_VERSION_SEQ = ":";

	// 数据结构:Set<渠道ID KEY, 机构号 KEY, 交易类型 KEY, 交易码 KEY, ...>
	private ConcurrentSkipListSet<String> routeDataKeyMap = new ConcurrentSkipListSet<String>();
	// 数据结构:Map<规则KEY(支持匹配), Map<serviceId, ServiceApp{serviceId@version, Set<ip:port>}>
	private ConcurrentHashMap<String, ConcurrentHashMap<String, ServiceApp>> routeRuleMap = new ConcurrentHashMap<String, ConcurrentHashMap<String, ServiceApp>>();

	/**
	 * 添加路由规则参数<br>
	 * <br>
	 * 有序<br>
	 * @param routeKey
	 */
	@Override
	public void addRouteDataKey(String routeKey) {
		routeDataKeyMap.add(routeKey);
	}
	
	/**
	 * 添加一条规则<br>
	 * <br>数据结构:{rules, Map<serviceId:version, Set<host : port>}
	 * 
	 * @param routeRule
	 */
	@Override
	public void addRouteRule(RouteRule routeRule) {
		ConcurrentHashMap<String, ServiceApp> tempMap=new ConcurrentHashMap<String, ServiceApp>();
		for (ServiceApp serviceApp:routeRule.getServiceApps()) {
			String serviceIdVersion=serviceApp.getServiceIdVersion();
			String serviceId=serviceIdVersion.substring(0, serviceIdVersion.indexOf(SERVICE_VERSION_SEQ));
			tempMap.put(serviceId, new ServiceApp(serviceIdVersion, serviceApp.getApps()));
		}
		
		routeRuleMap.put(routeRule.getRules(), tempMap);
	}

	/**
	 * 根据服务ID和路由参数进行分组选择服务实例
	 * 
	 * @param serviceId
	 * @param parameters
	 * @return
	 */
	@Override
	public ConcurrentHashSet<InetSocketAddress> doSelectApps(String serviceId, Map<String, String> parameters) {
		//$NON-NLS-第一步：数据校验$
		if (serviceId == null || serviceId.length() < 1) {
			throw new IllegalRequestException("serviceId不能为空");
		} else if (parameters == null || parameters.isEmpty()) {
			throw new IllegalRequestException("parameters不能为空");
		} else if (routeDataKeyMap.isEmpty()) {
			throw new ParameNoInitException("routeDataKeyMap不能为空");
		} else if (routeRuleMap.isEmpty()) {
			throw new ParameNoInitException("routeRuleMap不能为空");
		}

		//$NON-NLS-第二步：实时组装路由KEY$
		String routeRuleRegex = "";
		for (String routeDataKey : routeDataKeyMap) {
			String tempVal = parameters.get(routeDataKey);
			if (tempVal == null || tempVal.isEmpty()) {
				throw new IllegalRequestException("非法请求");
			}
			routeRuleRegex += tempVal + SEQ;
		}
		if (routeRuleRegex.endsWith(SEQ)) {
			routeRuleRegex = routeRuleRegex.substring(0, routeRuleRegex.length() - SEQ.length());
		}

		//$NON-NLS-第三步：路由KEY四重匹配$
		for (Map.Entry<String, ConcurrentHashMap<String, ServiceApp>> entry : routeRuleMap.entrySet()) {
			if (matches(routeRuleRegex, entry.getKey())) {// 进行路由KEY的匹配
				ConcurrentHashMap<String, ServiceApp> serviceAppMap = entry.getValue();

				//$NON-NLS-第四步：根据消费服务ID查找可用的服务实例集$
				ServiceApp serviceApp = serviceAppMap.get(serviceId);
				if (serviceApp.getApps().isEmpty()) {
					throw new NoFoundProviderException("没有可用的提供者");
				} else {
					return serviceApp.getApps();
				}
			}
		}

		throw new NoFoundRouteRuleException("没有找到可匹配的规则");
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

		//$NON-NLS-正则匹配$
		if (rule.matches(regex)) {
			return true;
		}

		return false;
	}
	
}
