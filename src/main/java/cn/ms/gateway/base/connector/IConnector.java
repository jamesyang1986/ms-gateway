package cn.ms.gateway.base.connector;

import cn.ms.gateway.base.adapter.IAdapter;
import cn.ms.gateway.base.adapter.ICallback;

public interface IConnector<REQ, RES> extends IAdapter {

	void connector(REQ req, ICallback<REQ, RES> callback, Object... args) throws Exception;

}
