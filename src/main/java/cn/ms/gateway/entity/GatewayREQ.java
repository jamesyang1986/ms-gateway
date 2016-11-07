package cn.ms.gateway.entity;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;

/**
 * 网关请求对象
 * 
 * @author lry
 */
public class GatewayREQ {

	//$NON-NLS-网关系统参数$
	/**交易ID**/
	String tradeId;
	/**交易开始时间**/
	long tradeStartTime;
	/**路由开始时间**/
	long routeStartTime;
	
	//$NON-NLS-请求对象$
	/**请求报文**/
	String content;
	HttpRequest request;
	ChannelHandlerContext ctx;
	
	/**
	 * 远程通讯地址
	 */
	String remoteHost;
	int remotePort=80;
	String remoteProtocol="http";
	String remoteAddress="localhost:8844";
	String remotePath="/serviceId";
	String remoteURI;

	
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
	public String getRemoteHost() {
		return remoteHost;
	}
	public void setRemoteHost(String remoteHost) {
		this.remoteHost = remoteHost;
	}
	public int getRemotePort() {
		return remotePort;
	}
	public void setRemotePort(int remotePort) {
		this.remotePort = remotePort;
	}
	public String getRemoteProtocol() {
		return remoteProtocol;
	}
	public void setRemoteProtocol(String remoteProtocol) {
		this.remoteProtocol = remoteProtocol;
	}
	public String getRemoteAddress() {
		return (remoteAddress==null||remoteAddress.length()<1)?(getRemoteHost()+":"+getRemotePort()):(remoteAddress);
	}
	public void setRemoteAddress(String remoteAddress) {
		this.remoteAddress = remoteAddress;
	}
	public String getRemoteURI() {
		return (remoteURI==null||remoteURI.length()<1)?(getRemoteProtocol()+"://"+getRemoteAddress()):(remoteURI);
	}
	public void setRemoteURI(String remoteURI) {
		this.remoteURI = remoteURI;
	}
	
}
