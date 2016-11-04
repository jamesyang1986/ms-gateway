package cn.ms.gateway.core.connector;

public interface IConnectorCallback {

	void before() throws Exception;

	void callback(String content) throws Exception;

	void after() throws Exception;

}
