package cn.ms.gateway.core.connector;

import cn.ms.gateway.base.adapter.ICallback;
import cn.ms.gateway.entity.GatewayREQ;
import cn.ms.gateway.entity.GatewayRES;

/**
 * 回调传输对象
 * 
 * @author lry
 */
public class CallbackTransferObject {

	/** 交易ID **/
	String tradeId;
	/** 交易开始时间 **/
	long tradeStartTime;
	/** 路由开始时间 **/
	long routeStartTime;
	/** 回调函数 **/
	ICallback<GatewayREQ, GatewayRES> callback;
	
	public CallbackTransferObject() {
	}
	
	public CallbackTransferObject(String tradeId, long tradeStartTime,
			long routeStartTime, ICallback<GatewayREQ, GatewayRES> callback) {
		super();
		this.tradeId = tradeId;
		this.tradeStartTime = tradeStartTime;
		this.routeStartTime = routeStartTime;
		this.callback = callback;
	}

	public String getTradeId() {
		return tradeId;
	}
	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}
	public long getTradeStartTime() {
		return tradeStartTime;
	}
	public void setTradeStartTime(long tradeStartTime) {
		this.tradeStartTime = tradeStartTime;
	}
	public long getRouteStartTime() {
		return routeStartTime;
	}
	public void setRouteStartTime(long routeStartTime) {
		this.routeStartTime = routeStartTime;
	}
	public ICallback<GatewayREQ, GatewayRES> getCallback() {
		return callback;
	}
	public void setCallback(ICallback<GatewayREQ, GatewayRES> callback) {
		this.callback = callback;
	}

}
