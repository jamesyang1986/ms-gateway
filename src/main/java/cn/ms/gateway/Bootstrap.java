package cn.ms.gateway;

import cn.ms.gateway.core.MsGateway;

public class Bootstrap {

	public static void main(String[] args) {
		MsGateway msGateway = null;
		try {
			msGateway = new MsGateway();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				msGateway.shutdown();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
}
