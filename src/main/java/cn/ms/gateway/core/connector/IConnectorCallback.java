package cn.ms.gateway.core.connector;

public interface IConnectorCallback {

	void onReturn(String content) throws Throwable;

}
