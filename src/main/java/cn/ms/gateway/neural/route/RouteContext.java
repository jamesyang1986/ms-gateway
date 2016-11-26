package cn.ms.gateway.neural.route;

import java.util.Map;
import java.util.Map.Entry;
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

	public static final String SEQ = "@";
	public static final String ANY_VAL = "*";

	// 数据结构:Set<渠道ID KEY, 机构号 KEY, 交易类型 KEY, 交易码 KEY, ...>
	private ConcurrentHashSet<String> routeDataKeyMap = new ConcurrentHashSet<String>();
	private ConcurrentSkipListMap<String, ConcurrentSkipListMap<String, Entry<String, ConcurrentSkipListSet<ServiceApp>>>> routeRuleMap = new ConcurrentSkipListMap<String, ConcurrentSkipListMap<String, Entry<String, ConcurrentSkipListSet<ServiceApp>>>>();

	public void addRouteRule(ServiceApp serviceApp) {
		
	}
	
	public ConcurrentSkipListSet<ServiceApp> selectApps(String serviceId, Map<String, String> parameters) {
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
		for (Map.Entry<String, ConcurrentSkipListMap<String, Entry<String, ConcurrentSkipListSet<ServiceApp>>>> entry : routeRuleMap.entrySet()) {
			if (matches(entry.getKey(), routeRuleRegex)) {// 进行路由KEY的匹配
				ConcurrentSkipListMap<String, Entry<String, ConcurrentSkipListSet<ServiceApp>>> serviceAppMap=entry.getValue();
				
				//$NON-NLS-第四步：根据消费服务ID查找可用的服务实例集$
				Entry<String, ConcurrentSkipListSet<ServiceApp>> serviceAppEntry= serviceAppMap.get(serviceId);
				ConcurrentSkipListSet<ServiceApp> serviceAppSet =serviceAppEntry.getValue();
				if(!serviceAppSet.isEmpty()){
					return serviceAppSet;
				}
				
				break;
			}
		}
		
		return null;
	}

	/**
	 * 匹配校验
	 * 
	 * @param key
	 * @param regex
	 * @return
	 */
	private boolean matches(String key, String regex) {
		//$NON-NLS-全匹配$
		if (key.equals(regex)) {
			return true;
		}

		//$NON-NLS-*号匹配$
		if (key.length() != regex.length()) {
			return false;
		}
		String keyTemp = key, regexTemp = regex;
		while (regexTemp.indexOf(ANY_VAL) > -1) {
			int index = regexTemp.indexOf(ANY_VAL);
			regexTemp = regexTemp.substring(0, index) + regexTemp.substring(index + 1);
			keyTemp = keyTemp.substring(0, index) + keyTemp.substring(index + 1);
		}
		if (keyTemp.equals(regexTemp)) {
			return true;
		}

		//$NON-NLS-号段匹配$

		//$NON-NLS-正则匹配$
		if (key.matches(regex)) {
			return true;
		}

		return false;
	}
}
