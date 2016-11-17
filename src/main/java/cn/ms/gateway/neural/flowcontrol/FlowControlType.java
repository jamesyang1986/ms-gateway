package cn.ms.gateway.neural.flowcontrol;

/**
 * 流控模式
 * 
 * @author lry
 */
public enum FlowControlType {

	/**
	 * 非流控
	 */
	NON("NON"),

	/**
	 * 单机流量
	 */
	INJVM("INJVM"),

	/**
	 * 分布式流控
	 */
	DISTR("DISTR");

	String code;

	FlowControlType(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
