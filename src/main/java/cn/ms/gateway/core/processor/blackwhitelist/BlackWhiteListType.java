package cn.ms.gateway.core.processor.blackwhitelist;

/**
 * IP 过滤类型
 * 
 * @author lry
 */
public enum BlackWhiteListType {

	/**
	 * 黑名单
	 */
	BLACKLIST,

	/**
	 * 白名单
	 */
	WHITELIST,
	
	/**
	 * 非黑白名单模式
	 */
	NON;

}
