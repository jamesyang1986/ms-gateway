package cn.ms.gateway.core.connector;

import cn.ms.gateway.core.entity.GatewayREQ;
import cn.ms.gateway.core.entity.GatewayRES;

public class NettyConnectorTest {

	public static void main(String[] args) throws Exception {
		try {
			RxNettyConnector connector=new RxNettyConnector();
			connector.init();
			connector.start();
			
			GatewayREQ req=new GatewayREQ();
			GatewayRES res = connector.connector(req);
			
			System.out.println(res.getContent());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
