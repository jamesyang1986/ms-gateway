package cn.ms.gateway.core.connector;

import io.netty.handler.codec.http.FullHttpResponse;
import cn.ms.gateway.base.ICallback;
import cn.ms.gateway.common.utils.NetUtils;
import cn.ms.gateway.entity.GatewayREQ;
import cn.ms.gateway.entity.GatewayRES;

public class NettyConnectorTest {

	public static void main(String[] args) {
		try {
			NettyConnector nettyConnector=new NettyConnector();
			nettyConnector.init();
			nettyConnector.start();
			
			GatewayREQ req=new GatewayREQ();
			req.setContent("I am OK");
			req.setClientHost(NetUtils.getLocalIp());
			
			for (int i = 0; i < 10; i++) {
				nettyConnector.connect(req, new ICallback<GatewayRES, GatewayRES, FullHttpResponse>() {
					@Override
					public void before(FullHttpResponse bef, Object... args) throws Exception {
						
					}
					@Override
					public GatewayRES callback(GatewayRES res, Object... args) throws Exception {
						System.out.println(res.getContent());
						return null;
					}
				});				
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
}
