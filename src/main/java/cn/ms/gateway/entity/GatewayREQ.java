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
	Map<String, String> headers = new HashMap<String, String>();
	HttpRequest request;
	ChannelHandlerContext ctx;

	//$NON-NLS-远程通讯信息$
	String remoteURI = "http://www.qiushibaike.com";

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

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
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

	//$NON-NLS-特殊情况$
	//$NON-NLS-特殊情况$
	public void putHeader(String key, String val) {
		this.headers.put(key, val);
	}

	public void putHeaders(Map<String, String> headers) {
		this.headers.putAll(params);
	}

	public void putParam(String key, String val) {
		this.params.put(key, val);
	}

	public void putParams(Map<String, String> params) {
		this.params.putAll(params);
	}

	public void putParameter(String key, List<String> vals) {
		this.parameters.put(key, vals);
	}

	public void putParameters(Map<String, List<String>> parameters) {
		this.parameters.putAll(parameters);
	}

	public String getRemoteURI() {
		return remoteURI;
	}

	public void setRemoteURI(String remoteURI) {
		this.remoteURI = remoteURI;
	}

}
