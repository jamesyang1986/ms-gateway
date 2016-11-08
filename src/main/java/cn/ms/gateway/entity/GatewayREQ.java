package cn.ms.gateway.entity;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.ms.gateway.common.utils.NetUtils;

/**
 * 网关请求对象
 * 
 * @author lry
 */
public class GatewayREQ {

	public static final String LOACALHOST = NetUtils.getLocalIp();

	//$NON-NLS-网关系统参数$
	/** 交易ID **/
	String tradeId;
	/** 交易开始时间 **/
	long tradeStartTime;
	/** 路由开始时间 **/
	long routeStartTime;
	/** 网关本机HOST **/
	String localHost = LOACALHOST;

	//$NON-NLS-请求对象$
	/** 客户端HOST **/
	String clientHost;
	/** 请求报文 **/
	String content;
	Map<String, String> params = new HashMap<String, String>();
	Map<String, List<String>> parameters = new HashMap<String, List<String>>();
	HttpRequest request;
	ChannelHandlerContext ctx;

	//$NON-NLS-远程通讯信息$
	String remoteHost = "localhost";
	int remotePort = 80;
	String remoteProtocol = "http";
	String remoteAddress = "localhost:8844";
	String remotePath = "/serviceId";
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

	public String getLocalHost() {
		return localHost;
	}

	public void setLocalHost(String localHost) {
		this.localHost = localHost;
	}

	public String getClientHost() {
		return clientHost;
	}

	public void setClientHost(String clientHost) {
		this.clientHost = clientHost;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public Map<String, List<String>> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, List<String>> parameters) {
		this.parameters = parameters;
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

	public String getRemotePath() {
		return remotePath;
	}

	public void setRemotePath(String remotePath) {
		this.remotePath = remotePath;
	}

	public void setRemoteAddress(String remoteAddress) {
		this.remoteAddress = remoteAddress;
	}

	public void setRemoteURI(String remoteURI) {
		this.remoteURI = remoteURI;
	}

	//$NON-NLS-特殊情况$
	public void putParam(String key, String val) {
		this.params.put(key, val);
	}

	public void putAllParam(Map<String, String> params) {
		this.params.putAll(params);
	}

	public void putParameter(String key, List<String> vals) {
		this.parameters.put(key, vals);
	}

	public void putAllParameter(Map<String, List<String>> parameters) {
		this.parameters.putAll(parameters);
	}

	public String getRemoteURI() {
		return (remoteURI == null || remoteURI.length() < 1) ? (getRemoteProtocol()
				+ "://" + getRemoteAddress())
				: (remoteURI);
	}

	public String getRemoteAddress() {
		return (remoteAddress == null || remoteAddress.length() < 1) ? (getRemoteHost()
				+ ":" + getRemotePort())
				: (remoteAddress);
	}

}
