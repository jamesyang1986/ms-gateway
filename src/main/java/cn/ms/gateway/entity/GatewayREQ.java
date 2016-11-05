package cn.ms.gateway.entity;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;

/**
 * 网关请求对象
 * 
 * @author lry
 */
public class GatewayREQ {

	/**
	 * 交易ID
	 */
	String tradeId;
	/**
	 * 交易开始时间
	 */
	long tradeStartTime;
	/**
	 * 路由开始时间
	 */
	long routeStartTime;
	/**
	 * 请求报文
	 */
	String content;
	HttpRequest request;
	ChannelHandlerContext ctx;
	/**
	 * 远程通讯地址
	 */
	String originURI="http://localhost:8844/serviceId";
	
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public HttpRequest getRequest() {
		return request;
	}
	public void setRequest(HttpRequest request) {
		this.request = request;
	}
	public ChannelHandlerContext getCtx() {
		return ctx;
	}
	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}
	public String getOriginURI() {
		return originURI;
	}
	public void setOriginURI(String originURI) {
		this.originURI = originURI;
	}
	
}
