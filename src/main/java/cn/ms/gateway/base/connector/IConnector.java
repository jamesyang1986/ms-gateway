package cn.ms.gateway.base.connector;

import org.zbus.net.http.MessageClient;

import cn.ms.gateway.base.IAdapter;

public interface IConnector extends IAdapter {

	MessageClient connect(String address) throws Throwable;

}
