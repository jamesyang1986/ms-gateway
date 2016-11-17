package cn.ms.gateway.entity;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.ms.gateway.common.utils.NetUtils;

/**
 * 网关请求对象
 * 
 * @author lry
 */
public class GatewayREQ extends INOUT<HttpServerRequest<ByteBuf>, HttpServerResponse<ByteBuf>> {

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
	String clientContent="";
	String clientUri;
	Map<String, String> clientParams = new HashMap<String, String>();
	Map<String, List<String>> clientParameters = new HashMap<String, List<String>>();
	Map<String, String> clientHeaders = new HashMap<String, String>();

	//$NON-NLS-远程通讯信息$
	String remoteURI = "http://v.juhe.cn/xiangji_weather/real_time_weather.php";

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

	public String getClientContent() {
		return clientContent;
	}

	public void setClientContent(String clientContent) {
		this.clientContent = clientContent;
	}

	public Map<String, String> getClientParams() {
		return clientParams;
	}

	public void setClientParams(Map<String, String> clientParams) {
		this.clientParams = clientParams;
	}

	public Map<String, List<String>> getClientParameters() {
		return clientParameters;
	}

	public void setClientParameters(Map<String, List<String>> clientParameters) {
		this.clientParameters = clientParameters;
	}

	public Map<String, String> getClientHeaders() {
		return clientHeaders;
	}

	public void setClientHeaders(Map<String, String> clientHeaders) {
		this.clientHeaders = clientHeaders;
	}

	public String getClientUri() {
		return clientUri;
	}

	public void setClientUri(String clientUri) {
		this.clientUri = clientUri;
	}

	//$NON-NLS-特殊情况$
	public void putClientHeader(String key, String val) {
		this.clientHeaders.put(key, val);
	}

	public void putClientHeaders(Map<String, String> clientHeaders) {
		this.clientHeaders.putAll(clientHeaders);
	}

	public void putClientParam(String key, String val) {
		this.clientParams.put(key, val);
	}

	public void putClientParams(Map<String, String> clientParams) {
		this.clientParams.putAll(clientParams);
	}

	public void putClientParameter(String key, List<String> vals) {
		this.clientParameters.put(key, vals);
	}

	public void putClientParameters(Map<String, List<String>> clientParameters) {
		this.clientParameters.putAll(clientParameters);
	}

	public String getRemoteURI() {
		return remoteURI;
	}

	public void setRemoteURI(String remoteURI) {
		this.remoteURI = remoteURI;
	}

}
