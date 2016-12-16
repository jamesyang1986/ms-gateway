package cn.ms.gateway.base.router;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cn.ms.gateway.base.processor.router.RouterContext;

import com.weibo.api.motan.rpc.URL;

public class RouterContextTest {

	public static void main(String[] args) {
		RouterContext rc = new RouterContext();

		// 第一步:设置路由匹配规则参数KEY
		rc.addOrUpRouteParamKey("areaId&channelId&consumerId");// 地区ID&渠道ID&消费服务ID

		// 第二步:管理端添加路由规则
		Set<String> routeDataSet1 = new HashSet<String>();// 所有记录的serviceId不能重复
		routeDataSet1.add("AtTran:1.0.0:S01");
		rc.addOrUpRouteRule("(beijing)&(weixin0[1-5])&(AtTra.)", routeDataSet1);

		Set<String> routeDataSet2 = new HashSet<String>();
		routeDataSet2.add("atbillquery:1.0.0:S01");
		rc.addOrUpRouteRule("((beijing|shenzheng))&(weixin0[6-9])&(atbill.*)", routeDataSet2);

		// 第三步:网关根据路由规则中的服务ID进行订阅服务提供者,并自动添加至服务清单中
		Set<URL> routeListSet = new HashSet<URL>();
		routeListSet.add(URL.valueOf("http://10.24.1.62:29001/AtTran?version=1.0.0&group=S01"));// 服务AtTran
		routeListSet.add(URL.valueOf("http://10.24.1.62:29002/AtTran?version=1.0.0&group=S01"));// 服务AtTran
		routeListSet.add(URL.valueOf("http://10.24.1.63:29001/atbillquery?version=1.0.0&group=S01"));// 服务atbillquery
		rc.addOrUpProviders(routeListSet);

//		// 查看当前数据
//		System.out.println("1>>>>>" + rc.routeParamKeys);
//		System.out.println("2>>>>>" + rc.routeRuleMap);
//		System.out.println("3>>>>>" + rc.routeProviderMap);

		// 第四步:模拟请求
		Map<String, String> parameters1 = new HashMap<String, String>();
		parameters1.put("areaId", "beijing");
		parameters1.put("channelId", "weixin04");
		parameters1.put("consumerId", "AtTran");
		System.out.println("路由选择结果清单:" + rc.selectRouteList("AtTran", parameters1));

		Map<String, String> parameters2 = new HashMap<String, String>();
		parameters2.put("areaId", "shenzheng");
		parameters2.put("channelId", "weixin07");
		parameters2.put("consumerId", "atbillquery");
		System.out.println("路由选择结果清单:" + rc.selectRouteList("atbillquery", parameters2));
	}
	
}
