package cn.ms.gateway.core;

import cn.ms.gateway.core.entity.GatewayRES;

/**
 * 网关状态码
 * 
 * @author lry
 */
public enum StatusCode {

	//$NON-NLS-执行成功 - 状态码组$
	SC10_OK(1000, "Success", "Gateway success."),

	
	//$NON-NLS-客户端异常 - 状态码组$
	// 请求头
	SC20_HEADER_NOT_NULL(2001, "Illegal request header parameters cannot be empty", "Request header parameter'%s'cannot be empty."), 
	SC20_HEADER_NOTEQN_LENGTH(2002, "Illegal request header parameter value length", "The length of the request header parameter '%s' must be '%s' bit."), 
	SC20_HEADER_NOTEQN_TYPE(2003, "Illegal request header parameter value type", "The type of request header parameter'%s'must be'%s'."),
	// 请求PATH
	SC21_PATH_SERVICEID_NULL(2101, "Illegal service ID", "Service ID in PATH cannot be empty or empty string."),
	// 请求参数

	
	//$NON-NLS-网关接入异常 - 状态码组$
	SC30_(3000, "Gateway access exception", "Gateway access exception."),
	SC31_BLACKWHITE_BLACK(3101, "Blacklist IP", "IP '%s' for the black list IP."),
	SC31_BLACKWHITE_WHITE(3102, "Non Whitelist IP", "IP '%s' is not a white list IP."),
	SC31_BLACKWHITE_ILLEGAL(3103, "Illegal request IP", "Request IP cannot be empty or null string."),
	
	//$NON-NLS-网关接出异常 - 状态码组$
	SC81_NO_PROVIDER(8101, "Failure to register center or route selection", "Service ID '%s' no available service provider."),

	
	//$NON-NLS-服务器异常 - 状态码组$
	SC90_FAIL(9001, "Gateway Failure", "Gateway failure."),

	;

	int code;
	String title;
	String msg;

	public static GatewayRES Wrapper(StatusCode statusCode) {
		return Wrapper(statusCode, new Object[]{});
	}

	public static GatewayRES Wrapper(StatusCode statusCode, Object... args) {
		GatewayRES res = new GatewayRES();
		res.putHeader("code", statusCode.getCode());
		res.putHeader("title", statusCode.getTitle());
		res.putHeader("msg",
				(args == null || args.length < 1) ? statusCode.getMsg()
						: String.format(statusCode.getMsg(), args));

		return res;
	}

	StatusCode(int code, String title, String msg) {
		this.code = code;
		this.title = title;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
