package cn.ms.gateway.base.processor.router;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.regex.Pattern;

import com.weibo.api.motan.rpc.URL;

/**
 * <b style="font-size:18px">分组路由上下文</b><br>
 * <br>
 * 使用方式:<br>
 * 第一步：void addOrUpRouteParamKeys(String routeParamKey)-->初始化路由规则参数KEYs(项目启动时统一规定好)<br>
 * 第二步：void addOrUpRouteRule(String routeRules, Set<String> svgSet)-->添加一条路由规则(包括其允许消费的服务清单)<br>
 * 第三步：void addOrUpProviders(Set<URL> providerURLs)-->添加一组服务提供者清单<br>
 * 第四步：RouterResult selectRouteList(String serviceId, Map<String, String> parameters)-->根据需要消费的服务ID和路由参数组,进行分组选择路由清单<br>
 * 第五步：boolean containsRouteProviderMapKey(String routeProviderMapKey)-->使用场景：判断是否需要重新订阅<br>
 * <br>
 * 常用匹配规则(支持全匹配和正则匹配):<br>
 * <b style="color:red">.</b>：匹配任意一个字符串<br>
 * <b style="color:red">a.*</b>：匹配以a为开头的任意字符串<br>
 * <b style="color:red">m$</b>：匹配以m为结尾的字符串<br>
 * <b style="color:red">(a|b|c)</b>：匹配a、b、c中的任意一个字符串,即枚举匹配<br>
 * <b style="color:red">[1-9]</b>：匹配1到9中的任意一个数<br>
 * <b style="color:red">[a-z]</b>：匹配a到z中的人一个小写字母<br>
 * ...<br>
 * 
 * @author lry
 */
public class RouterContext {

	/** 规则分割符 **/
	public static final String RULE_SEQ = "&";
	/** serviceId、version和group分割符 **/
	public static final String SVG_SEQ = ":";
	
	/** 请求头中需要路由的参数KEY集合 **/
	private ConcurrentSkipListSet<String> routeParamKeys = new ConcurrentSkipListSet<String>();
	/** 数据结构：Map<规则串, Map< serviceId, serviceId:version:group >> **/
	private ConcurrentHashMap<String, ConcurrentHashMap<String, String>> routeRuleMap = new ConcurrentHashMap<String, ConcurrentHashMap<String, String>>();
	/** 数据结构：Map< serviceId:version:group, Map< host:port, URL> >, 其中host:port的作用是用于对URL实例进去去重**/
	private ConcurrentHashMap<String, ConcurrentHashMap<String, URL>> routeProviderMap = new ConcurrentHashMap<String, ConcurrentHashMap<String, URL>>();

	/**
	 * 第一步：初始化路由规则参数KEYs(项目启动时统一规定好)
	 * 
	 * @param routeRuleParamKey eg：areaId&channelId&consumerId
	 */
	public void addOrUpRouteParamKey(String routeParamKey) {
		ConcurrentSkipListSet<String> tempRouteParamKeys = new ConcurrentSkipListSet<String>();
		String[] routeRuleParamArray=routeParamKey.split(RULE_SEQ);
		for (String routeRuleParam:routeRuleParamArray) {
			tempRouteParamKeys.add(routeRuleParam);
		}
		this.routeParamKeys = tempRouteParamKeys;//必须为覆盖式变更
	}

	/**
	 * 第二步：添加一条路由规则(包括其允许消费的服务清单)
	 * 
	 * @param routeRules 规则串全量
	 * @param svgSet Set{serviceId:version:group, serviceId:version:group,....}
	 */
	public void addOrUpRouteRule(String routeRules, Set<String> svgSet) {
		if (routeRules == null || routeRules.isEmpty()) {
			throw new RuntimeException("'routeRules'不能为空");
		}
		if (svgSet == null || svgSet.isEmpty()) {
			throw new RuntimeException("'svgSet'不能为空");
		}
		
		// 组装路由规则
		ConcurrentHashMap<String, String> routeDataMap = routeRuleMap.get(routeRules);
		if (routeDataMap == null) {
			routeDataMap = new ConcurrentHashMap<String, String>();
		}
		
		Set<String> serviceIdSet=new HashSet<String>();
		for (String svg : svgSet) {// svg = serviceId:version:group
			String[] svgArray=svg.split(SVG_SEQ);
			if(svgArray.length!=3){
				throw new RuntimeException("非法规则(路由服务的格式必须为:“serviceId:version:group”)");
			}
			
			String serviceId=svgArray[0];
			if(serviceIdSet.contains(serviceId)){//校验服务ID是否重复
				throw new RuntimeException("单条规则内部的服务ID不能重复");
			}
			serviceIdSet.add(serviceId);//用于serviceId去重
			routeDataMap.put(serviceId, serviceId + SVG_SEQ + svgArray[1] + SVG_SEQ + svgArray[2]);
		}

		//TODO 如何清理掉废弃后的路由规则记录?
		routeRuleMap.put(routeRules, routeDataMap);
	}

	/**
	 * 第三步：添加一组服务提供者清单
	 * 
	 * @param providerURLs 支持单、多组提供者注入
	 */
	public void addOrUpProviders(Set<URL> providerURLs) {
		// 计算至临时空间
		ConcurrentHashMap<String, ConcurrentHashMap<String, URL>> tempRouteProviderMap = new ConcurrentHashMap<String, ConcurrentHashMap<String, URL>>();
		for (URL url : providerURLs) {
			String serviceIdVersionGroup=url.getPath()+SVG_SEQ+url.getVersion()+SVG_SEQ+url.getGroup();//serviceId:version:group
			ConcurrentHashMap<String, URL> routeListDataMap = tempRouteProviderMap.get(serviceIdVersionGroup);
			if (routeListDataMap == null) {
				routeListDataMap = new ConcurrentHashMap<String, URL>();
			}
			routeListDataMap.put(url.getHost() + SVG_SEQ + url.getPort(), url);//添加至单个serviceId:version:group的组内
			tempRouteProviderMap.put(serviceIdVersionGroup, routeListDataMap);//缓存至临时空间
		}
		
		// 针对同组进行(相同svg为同组)直接覆盖式变更
		for (Map.Entry<String, ConcurrentHashMap<String, URL>> entry:tempRouteProviderMap.entrySet()) {
			this.routeProviderMap.put(entry.getKey(), entry.getValue());
		}
	}

	/**
	 * 使用场景：判断是否需要重新订阅
	 * 
	 * @param routeProviderMapKey
	 * @return
	 */
	public boolean containsRouteProviderMapKey(String routeProviderMapKey) {
		return routeProviderMap.containsKey(routeProviderMapKey);
	}
	
	/**
	 * 根据需要消费的服务ID和路由参数组,进行分组选择路由清单
	 * 
	 * @param serviceId 路由服务ID
	 * @param parameters 路由参数组(请求头参数)
	 * @return
	 */
	public RouterResult selectRouteList(String serviceId, Map<String, String> parameters) {
		//$NON-NLS-第一步：数据校验$
		if (serviceId == null || serviceId.length() < 1) {
			return RouterResult.build(ResultType.NOT_NULL_PARAM.wapper("serviceId", serviceId));// ResultType用法一:多个参数
		} else if (parameters == null || parameters.isEmpty()) {
			return RouterResult.build(ResultType.NOT_NULL_PARAM.wapper("parameters", parameters));
		} else if (routeParamKeys.isEmpty()) {// 没有初始化路由规则参数
			return RouterResult.build(ResultType.NO_INIT_PARAM.wapper("routeParamKeys", routeParamKeys));
		} else if (routeRuleMap.isEmpty()) {// 没有可用的路由规则
			return RouterResult.build(ResultType.NO_AVA_RULE.wapper("routeRuleMap", routeRuleMap));
		} else if (routeProviderMap.isEmpty()) {// 没有已订阅的服务
			return RouterResult.build(ResultType.NO_SUBSCRIBE_PROVIDER.wapper("routeProviderMap", routeProviderMap));
		}

		//$NON-NLS-第二步：从parameters中获取参数值,实时组装路由待匹配KEY$
		String routeRuleRegex = "";//等待与路由规则进行匹配的串
		for (String routeDataKey : routeParamKeys) {
			String tempVal = parameters.get(routeDataKey);
			if (tempVal == null || tempVal.length()<1) {
				return RouterResult.build(ResultType.REQPARAM_NOT_NULL.wapper(routeDataKey));// ResultType用法二:单个参数
			}
			routeRuleRegex += tempVal + RULE_SEQ;
		}
		if (routeRuleRegex.endsWith(RULE_SEQ)) {// 去尾部的&
			routeRuleRegex = routeRuleRegex.substring(0, routeRuleRegex.length() - RULE_SEQ.length());
		}
		if (routeRuleRegex == null || routeRuleRegex.length() < 1) {// 路由值结果校验
			return RouterResult.build(ResultType.ILLEGAL_REQ_ROUTERULE.wapper("routeRuleRegex==" + routeRuleRegex));
		}

		//$NON-NLS-第三步：路由KEY四重匹配$
		AccessId accessId = null;
		List<URL> routeURLs = new ArrayList<URL>();
		for (Map.Entry<String, ConcurrentHashMap<String, String>> entry : routeRuleMap.entrySet()) {
			if (matches(entry.getKey(), routeRuleRegex)) {// 进行路由KEY的匹配
				if (entry.getValue() == null || entry.getValue().isEmpty()) {
					return RouterResult.build(ResultType.ROUERULE_NOAVA_SERVICES.wapper(routeRuleRegex));
				}

				//$NON-NLS-第四步：根据服务ID查找serviceId:version:group$
				String svgStr = entry.getValue().get(serviceId);
				if (svgStr == null) {
					return RouterResult.build(ResultType.SVG_NOT_NULL.wapper());
				}
				
				//$NON-NLS-路由服务格式校验$
				String[] svgArray=svgStr.split(SVG_SEQ);
				if(svgArray.length!=3){
					return RouterResult.build(ResultType.ILLEGAL_SVG_FROMART.wapper());
				}
				accessId = new AccessId(svgArray[0], svgArray[1], svgArray[2]);

				if(!routeProviderMap.isEmpty()){
					//$NON-NLS-第五步：根据serviceId:version:group来查找可用的服务提供者清单$
					ConcurrentHashMap<String, URL> routeListURLMap = routeProviderMap.get(svgStr);
					if (routeListURLMap == null || routeListURLMap.isEmpty()) {
						return RouterResult.build(ResultType.NO_AVA_PROVIDER.wapper(svgStr, routeListURLMap));
					}

					//$NON-NLS-第六步：收集可用的服务提供者列表$
					routeURLs.addAll(routeListURLMap.values());					
				}
				
				break;
			}
		}

		// 最终结果校验
		if (accessId == null && routeURLs.isEmpty()) {
			return RouterResult.build(ResultType.NOTFOUND_ROUTE_RULE);// ResultType用法三:没有参数
		} else {
			return RouterResult.build(ResultType.ROUTE_SELECT_OK, routeURLs);// 路由成功
		}
	}

	/**
	 * 匹配校验规则<br>
	 * <br>
	 * 常用匹配规则:<br>
	 * .-->匹配任意一个字符串<br>
	 * a.*-->匹配以a为开头的任意字符串<br>
	 * m$-->匹配以m为结尾的字符串<br>
	 * (a|b|c)-->匹配a、b、c中的任意一个字符串,即枚举匹配<br>
	 * [1-9]-->匹配1到9中的任意一个数<br>
	 * [a-z]-->匹配a到z中的人一个小写字母<br>
	 * @param regex
	 * @param input
	 * @return
	 */
	private boolean matches(String regex, String input) {
		//$NON-NLS-全匹配$
		if (input.equals(regex)) {
			return true;
		}
		
		//$NON-NLS-正则匹配$
		if (Pattern.matches(regex, input)) {
			return true;
		}

		return false;
	}

	public static void main(String[] args) {
		RouterContext rc = new RouterContext();
		System.out.println(rc.matches("(?:weixin.*)", "weixin001"));
	}
	
}
