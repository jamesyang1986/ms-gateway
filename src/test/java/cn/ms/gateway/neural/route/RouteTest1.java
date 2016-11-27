package cn.ms.gateway.neural.route;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cn.ms.gateway.common.ConcurrentHashSet;
import cn.ms.gateway.neural.route.entity.RouteRule;
import cn.ms.gateway.neural.route.entity.ServiceApp;

public class RouteTest1 {

	RouteContext rc = new RouteContext();

	@Before
	public void init() throws Exception {
		//$NON-NLS-组装规则$
		RouteRule routeRule1=new RouteRule();
		routeRule1.setRules("weixin*@A00*");
		List<ServiceApp> serviceApps1=new ArrayList<ServiceApp>();
		ConcurrentHashSet<InetSocketAddress> set1=new ConcurrentHashSet<InetSocketAddress>();
		set1.add(new InetSocketAddress("10.24.1.10", 8080));
		set1.add(new InetSocketAddress("10.24.1.11", 8080));
		set1.add(new InetSocketAddress("10.24.1.12", 8080));
		serviceApps1.add(new ServiceApp("servic1:1.0.0", set1));
		routeRule1.setServiceApps(serviceApps1);
		rc.addRouteRule(routeRule1);
		
		RouteRule routeRule2=new RouteRule();
		routeRule2.setRules("app@A00*");
		List<ServiceApp> serviceApps2=new ArrayList<ServiceApp>();
		ConcurrentHashSet<InetSocketAddress> set2=new ConcurrentHashSet<InetSocketAddress>();
		set2.add(new InetSocketAddress("10.24.1.13", 8080));
		set2.add(new InetSocketAddress("10.24.1.14", 8080));
		serviceApps2.add(new ServiceApp("servic2:1.0.0", set2));
		routeRule2.setServiceApps(serviceApps2);
		rc.addRouteRule(routeRule2);
		
		//设置路由数据结构
		rc.addRouteDataKey("channelId");
		rc.addRouteDataKey("code");
	}

	@Test
	public void test1() throws Exception {
		//模拟请求
		Map<String, String> parameters=new HashMap<String, String>();
		parameters.put("channelId", "weixin3");
		parameters.put("code", "A001");
		//查找：指定渠道的某类交易码的服务分组
		ConcurrentHashSet<InetSocketAddress> rs=rc.doSelectApps("servic1", parameters);
		Assert.assertEquals(rs.size(), 3);
		Assert.assertTrue(rs.contains(new InetSocketAddress("10.24.1.10", 8080)));
		Assert.assertTrue(rs.contains(new InetSocketAddress("10.24.1.11", 8080)));
		Assert.assertTrue(rs.contains(new InetSocketAddress("10.24.1.12", 8080)));
	}
	
	@Test
	public void test2() throws Exception {
		//模拟请求
		Map<String, String> parameters=new HashMap<String, String>();
		parameters.put("channelId", "app");
		parameters.put("code", "A002");
		//查找：指定渠道的某类交易码的服务分组
		ConcurrentHashSet<InetSocketAddress> rs=rc.doSelectApps("servic2", parameters);
		Assert.assertEquals(rs.size(), 2);
		Assert.assertTrue(rs.contains(new InetSocketAddress("10.24.1.13", 8080)));
		Assert.assertTrue(rs.contains(new InetSocketAddress("10.24.1.14", 8080)));
	}

}
