package cn.ms.gateway.neural.blackwhitelist;

/**
 * IP 过滤类型
 * 
 * @author lry
 */
public enum BlackWhiteListType {

	/**
	 * 黑名单
	 */
	BLACKLIST("BLACKLIST"),

	/**
	 * 白名单
	 */
	WHITELIST("WHITELIST"),

	/**
	 * 非黑白名单模式
	 */
	NON("NON");

	String code;

	BlackWhiteListType(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
