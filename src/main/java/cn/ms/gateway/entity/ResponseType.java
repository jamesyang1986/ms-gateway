package cn.ms.gateway.entity;

public enum ResponseType {

	SUCCESS(1000, "Gateway processing success"),
	
	
	FAILURE(9001, "Gateway processing failed.")
	;
	

	int code;
	String msg;

	ResponseType(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	//$NON-NLS-默认建造方法$
	public ResponseType build(Object... args) {
		setMsg(String.format(getMsg(), args));
		return this;
	}

}
