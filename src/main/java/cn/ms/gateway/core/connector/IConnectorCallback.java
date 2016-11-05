package cn.ms.gateway.core.connector;

import io.netty.handler.codec.http.HttpResponse;

public interface IConnectorCallback {

	void before(HttpResponse response) throws Exception;

	void callback(String content) throws Exception;

}
