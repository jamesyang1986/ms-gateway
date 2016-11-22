package cn.ms.gateway.neural.route;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cn.ms.gateway.neural.router.RouterContext;

public class RouteTest2 {

	RouterContext rc = new RouterContext();
	List<URL> serviceList = new ArrayList<URL>();

	@Before
	public void init() throws Exception {
		// 设置路由字段
		rc.setRouteKeyArray("code;type");

		// 添加路由规则
		rc.addRouteRule("A001;query", "10.24.1.1:8080");
		rc.addRouteRule("A001;query", "10.24.1.2:8080");
		rc.addRouteRule("A001", "10.24.1.3:8080");
		rc.addRouteRule("A001", "10.24.1.4:8080");
		rc.addRouteRule("A002;query", "10.24.2.1:8080");
		rc.addRouteRule("A002", "10.24.2.2:8080");
		rc.addRouteRule("A002", "10.24.2.3:8080");

		// 添加服务提供者
		serviceList.add(new URL("http", "10.24.1.1", 8080, ""));
		serviceList.add(new URL("http", "10.24.1.2", 8080, ""));
		serviceList.add(new URL("http", "10.24.1.3", 8080, ""));
		serviceList.add(new URL("http", "10.24.2.1", 8080, ""));
		serviceList.add(new URL("http", "10.24.2.2", 8080, ""));
	}

	@Test
	public void test1() throws Exception {
		Map<String, String> param = new HashMap<String, String>();
		param.put("code", "A001");
		param.put("type", "query");
		Assert.assertEquals(rc.getRouteList(param, serviceList).size(), 2);
	}
	
	@Test
	public void test2() throws Exception {
		Map<String, String> param = new HashMap<String, String>();
		param.put("code", "A002");
		param.put("type", "query");
		Assert.assertEquals(rc.getRouteList(param, serviceList).size(), 1);
	}

}
