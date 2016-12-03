package cn.ms.gateway.entity;

import java.util.Arrays;
import java.util.Map;

public class Request {

	private String serviceId;
	
	// params include query string and body param
	private Map<String, String> httpParams;
	// attribute map values
	private Map<String, Object> httpAttributes;
	// header values
	private Map<String, String> httpHeaders;
	// client request with unique id, fetch it from http header(Request-Id)
	private String requestId;
	// ip address from origin client, fetch it from getRemoteAddr()
	// or header X-FORWARDED-FOR
	private String remoteAddress;
	// raw uri exclude query string
	private String uri;
	// http uri terms split by "/"
	private String[] terms;
	// 请求报文体
	private String httpBody;
	
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public Map<String, String> getHttpParams() {
		return httpParams;
	}
	public void setHttpParams(Map<String, String> httpParams) {
		this.httpParams = httpParams;
	}
	public Map<String, Object> getHttpAttributes() {
		return httpAttributes;
	}
	public void setHttpAttributes(Map<String, Object> httpAttributes) {
		this.httpAttributes = httpAttributes;
	}
	public Map<String, String> getHttpHeaders() {
		return httpHeaders;
	}
	public void setHttpHeaders(Map<String, String> httpHeaders) {
		this.httpHeaders = httpHeaders;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getRemoteAddress() {
		return remoteAddress;
	}
	public void setRemoteAddress(String remoteAddress) {
		this.remoteAddress = remoteAddress;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String[] getTerms() {
		return terms;
	}
	public void setTerms(String[] terms) {
		this.terms = terms;
	}
	public String getHttpBody() {
		return httpBody;
	}
	public void setHttpBody(String httpBody) {
		this.httpBody = httpBody;
	}
	
	@Override
	public String toString() {
		return "Request [serviceId= " + serviceId + ", httpParams=" + httpParams + ", httpAttributes="
				+ httpAttributes + ", httpHeaders=" + httpHeaders
				+ ", requestId=" + requestId + ", remoteAddress="
				+ remoteAddress + ", uri=" + uri + ", terms="
				+ Arrays.toString(terms) + ", httpBody=" + httpBody + "]";
	}
	
}
