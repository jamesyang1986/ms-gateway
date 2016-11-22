package cn.ms.gateway.neural.router;

import java.net.URL;
import java.util.List;
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

	// 交易码;交易类型;机构号
	ConcurrentHashMap<String, List<URL>> routeRuleMap = new ConcurrentHashMap<String, List<URL>>();

	public List<URL> getRouteList(String routerKey, List<URL> serviceList) {

		return null;
	}

}
