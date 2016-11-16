package cn.ms.gateway.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * 网关响应对象
 * 
 * @author lry
 */
public class GatewayRES {

	private Map<String, String> headers = new HashMap<String, String>();
	private String content="";
	
	public GatewayRES() {
		headers.put("Content-Type", "text/plain;charset=utf-8");
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	//$NON-NLS-特殊组装$
	public void putHeader(String key, Object val) {
		this.headers.put(key, String.valueOf(val));
	}

	@Override
	public String toString() {
		return "GatewayRES [headers=" + headers + ", content=" + content + "]";
	}

}
