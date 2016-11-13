package cn.ms.gateway.common.spi.impl;

import cn.ms.gateway.common.spi.DemoInterface;

public class DemoInterfaceImpl1 implements DemoInterface {
	
	@Override
	public String getMsg() {
		return getClass().getName();
	}
	
}
