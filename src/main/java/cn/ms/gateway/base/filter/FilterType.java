package cn.ms.gateway.base.filter;

/**
 * 过滤器类型
 * 
 * @author lry
 */
public enum FilterType {

	/**
	 * 预处理类过滤器
	 */
	PRE,
	
	/**
	 * 路由类过滤器
	 */
	ROUTE,

	/**
	 * 响应前类过滤器
	 */
	POST,

	/**
	 * 错误类过滤器
	 */
	ERROR;

}
