package cn.ms.gateway.base.connector;

import io.netty.handler.codec.http.HttpResponse;
import cn.ms.gateway.entity.GatewayRES;

public interface IProxyCallback {

	public void before(HttpResponse response) throws Exception;
	
	void callback(GatewayRES res) throws Exception;

}
