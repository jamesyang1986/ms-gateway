package cn.ms.gateway.core.connector;

import cn.ms.gateway.base.adapter.ICallback;
import cn.ms.gateway.entity.GatewayREQ;
import cn.ms.gateway.entity.GatewayRES;

public class NettyConnectorTest {

	public static void main(String[] args) throws Exception {
		try {
			NettyConnector connector=new NettyConnector();
			connector.init();
			connector.shutdown();
			
			GatewayREQ req=new GatewayREQ();
			
			connector.connector(req, new ICallback<GatewayREQ, GatewayRES>() {
				@Override
				public void exception(Throwable t) {
				}
				@Override
				public GatewayRES callback(GatewayRES res, Object... args) throws Exception {
					System.out.println("回调响应:"+res.getContent());
					return null;
				}
				@Override
				public void before(GatewayRES req, Object... args) throws Exception {
				}
				@Override
				public void after(GatewayRES req, Object... args) throws Exception {
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
