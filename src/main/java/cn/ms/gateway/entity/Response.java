package cn.ms.gateway.entity;

import java.util.HashMap;
import java.util.Map;

import cn.ms.netty.server.core.rest.entity.HttpResult;

public class Response {

	// 响应类型
	private ResponseType responseType=ResponseType.FAILURE;
	// 响应报文体
	private String httpBody="";

	Response() {
	}

	public ResponseType getResponseType() {
		return responseType;
	}

	public void setResponseType(ResponseType responseType) {
		this.responseType = responseType;
	}

	public String getHttpBody() {
		return httpBody;
	}

	public void setHttpBody(String httpBody) {
		this.httpBody = httpBody;
	}

	//$NON-NLS-建造方法$
	public static final Response build() {
		return new Response();
	}

	public HttpResult buildHttpResult() {
		ResponseType responseType = this.getResponseType();
		Map<String, String> httpHeaders = new HashMap<String, String>();
		httpHeaders.put("Code", String.valueOf(responseType.getCode()));
		httpHeaders.put("Msg", responseType.getMsg());

		return new HttpResult(this.getHttpBody(), httpHeaders);
	}

	@Override
	public String toString() {
		return "Response [responseType=" + responseType + ", httpBody=" + httpBody + "]";
	}

}
