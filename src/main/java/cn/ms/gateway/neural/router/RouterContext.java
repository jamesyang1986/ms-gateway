package cn.ms.gateway.neural.router;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 分组路由<br>
 * <br>
 * 第一步：配置分组规则<br>
 * 第二步：根据服务ID订阅服务<br>
 * 第三步：服务清单进行分组<br>
 * 第四步：根据分组ID获取可用提供者列表<br>
 * <br>
 * 初版约定：交易码、交易类型、机构号...
 * 
 * @author lry
 */
public class RouterContext implements IRouter {

	public static final String SEQ = ";";
	public static final String ANY_VAL = "*";

	Set<String> routeKeyArray = new HashSet<String>();
	// 数据结构{[regex,(ip:port)]}
	ConcurrentHashMap<String, Set<String>> routeRuleMap = new ConcurrentHashMap<String, Set<String>>();

	public void setRouteKeyArray(Set<String> routeKeyArray) {
		this.routeKeyArray = routeKeyArray;
	}

	public void setRouteKeyArray(String routeKeyArrayStr) {
		String[] array = routeKeyArrayStr.split(SEQ);
		if (array == null || array.length < 1) {
			throw new RuntimeException("非法字段");
		}

		routeKeyArray.clear();
		for (int i = 0; i < array.length; i++) {
			routeKeyArray.add(array[i]);
		}
	}

	/**
	 * 获取路由规则
	 * 
	 * @return
	 */
	public ConcurrentHashMap<String, Set<String>> getRouteRuleMap() {
		return routeRuleMap;
	}

	/**
	 * 添加一条路由规则
	 * 
	 * @param regex
	 * @param val
	 */
	public void addRouteRule(String regex, String val) {
		Set<String> list = routeRuleMap.get(regex);
		if (list == null) {
			list = new HashSet<String>();
		}
		list.add(val);
		routeRuleMap.put(regex, list);
	}

	/**
	 * 根据路由规则和所有服务提供者选择一个可用的提供者列表
	 * 
	 * @param routerKey
	 *            路由KEY
	 * @param serviceList
	 *            服务提供者
	 * @return
	 */
	public List<URL> getRouteList(Map<String, String> routeParam,
			List<URL> serviceList) {
		if (routeKeyArray == null || routeKeyArray.isEmpty()) {
			throw new RuntimeException("请求参数集合不能为空");
		}

		String routeKey = "";
		for (String rk : routeKeyArray) {
			if (!routeParam.containsKey(rk)) {
				throw new RuntimeException("非法请求");
			}

			String rpVal = routeParam.get(rk);
			if (rpVal.contains(SEQ)) {
				throw new RuntimeException("请求参数值中不能包含系统已占用的特殊字符:" + SEQ);
			}
			routeKey += rpVal + SEQ;
		}
		if (routeKey.endsWith(SEQ)) {// 去掉末尾
			routeKey = routeKey.substring(0, routeKey.lastIndexOf(SEQ));
		}

		List<URL> resultList = new ArrayList<URL>();
		for (Map.Entry<String, Set<String>> entry : routeRuleMap.entrySet()) {
			if (matches(routeKey, entry.getKey())) {// 匹配校验
				//$NON-NLS-筛选可用的服务提供者$
				for (URL url : serviceList) {
					if (entry.getValue().contains(
							url.getHost() + ":" + url.getPort())) {
						resultList.add(url);
					}
				}

				break;
			}
		}

		return resultList;
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
