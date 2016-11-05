package cn.ms.gateway.base.connector;

import cn.ms.gateway.entity.GatewayRES;
import io.netty.handler.codec.http.HttpResponse;

public interface IProxyCallback {

	public void before(HttpResponse response) throws Exception;
	
	void callback(GatewayRES res) throws Exception;

}
