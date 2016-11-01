package cn.ms.gateway.base.type;

/**
 * 过滤器类型
 * 
 * @author lry
 */
public enum FilterType {

	/**
	 * 请求转发前执行的过滤器
	 */
	PRE("PRE", "请求转发前执行的过滤器"),

	/**
	 * 请求转发的过滤器
	 */
	ROUTE("ROUTE", "请求转发的过滤器"),

	/**
	 * 请求响应前执行的过滤器
	 */
	POST("POST", "请求响应前执行的过滤器"),

	/**
	 * 异常处理的过滤器
	 */
	ERROR("ERROR", "异常处理的过滤器");

	String code;
	String msg;

	FilterType(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
