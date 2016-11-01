package cn.ms.gateway.base.filter;

import org.junit.Test;

import cn.ms.gateway.base.support.Gateway;

public class FilterGatewayTest {

	@Test
	public void initTest() {
		try {
			Gateway<String, String> gateway=new Gateway<String, String>();
			gateway.init();
			gateway.handler("", "");
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
	
}
