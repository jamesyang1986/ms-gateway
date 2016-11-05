package cn.ms.gateway.base.connector;

import io.netty.handler.codec.http.HttpResponse;

public interface IProxyCallback {

	public void before(HttpResponse response) throws Exception;
	
	void callback(String content) throws Exception;

}
