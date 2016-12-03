package cn.ms.gateway.entity;

public enum ResponseType {

	SUCCESS(1000, "Gateway processing success."),
	
	//$NON-NLS-请求头参数校验$
	HEADER_PARAM_NOTNULL(2001, "The request header parameter must contain a '%s'."),
	HEADER_PARAM_LENGTH_NOTEQ(2002, "The length of the request header parameter value '%s' must be '%s'."),
	HEADER_PARAM_TYPE_ILLEGAL(2003, "The type of the request header parameter value '%s' must be '%s'."),
	
	FAILURE(9001, "Gateway processing failed."),
	;
	

	int code;
	String msg;
	final String MSG;

	ResponseType(int code, String MSG) {
		this.code = code;
		this.MSG = MSG;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg==null?this.MSG:msg;
	}

	public ResponseType setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	//$NON-NLS-默认建造方法$
	public ResponseType wrapper(Object... args) {
		return this.setMsg(String.format(this.MSG, args));
	}
	
	@Override
	public String toString() {
		return "ResponseType[code="+code+", msg="+msg+"]";
	}

}
