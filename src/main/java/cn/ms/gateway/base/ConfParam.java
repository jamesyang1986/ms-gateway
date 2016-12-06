package cn.ms.gateway.base;

/**
 * 配置参数
 * 
 * @author lry
 */
public enum ConfParam {

	//$NON-NLS-基本常数$
	DEF_ENCODE("defEncode", "UTF-8", "默认编码"),
	
	//$NON-NLS-请求头校验$
	CHECK_HEADER("checkHeader", true, "请求头参数校验开关"), 
	HEADER_RULE("headerRule", "channelId;tradeId{length=32};callId{length=32}", "请求头校验参数规则数据");
	
	;
	
	//$NON-NLS-内部属性$
	private volatile String name;
	private volatile Object value;
	private volatile long longValue;
	private volatile int intValue;
	private volatile boolean boolValue;
	private volatile String msg;
	
	ConfParam(String name, Object value, String msg) {
		this.name = name;
		this.value = value;
		this.msg = msg;
	}

	ConfParam(String name, long longValue, String msg) {
		this.name = name;
		this.value = String.valueOf(longValue);
		this.longValue = longValue;
		this.msg = msg;
	}

	ConfParam(String name, int intValue, String msg) {
		this.name = name;
		this.value = String.valueOf(intValue);
		this.intValue = intValue;
		this.msg = msg;
	}

	ConfParam(String name, boolean boolValue, String msg) {
		this.name = name;
		this.value = String.valueOf(boolValue);
		this.boolValue = boolValue;
		this.msg = msg;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getStringValue() {
		return String.valueOf(value);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getAutoValue() {
		return (T)value;
	}
	
	public void setStringValue(String value) {
		this.value=value;
	}
	
	public long getLongValue() {
		return longValue;
	}

	public void setLongValue(long longValue) {
		this.longValue = longValue;
	}

	public int getIntValue() {
		return intValue;
	}

	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}

	public boolean isBoolValue() {
		return boolValue;
	}

	public void setBoolValue(boolean boolValue) {
		this.boolValue = boolValue;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
