package cn.ms.gateway.base.filter;

import org.junit.Test;

import cn.ms.gateway.base.Gateway;

public class FilterGatewayTest {

	@Test
	public void initTest() {
		try {
			Gateway<String, String> gateway=new Gateway<String, String>();
			gateway.init();
			String res=gateway.handler(null);
			System.out.println("返回结果:"+res);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
	
}
