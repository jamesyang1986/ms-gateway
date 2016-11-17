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
	NON,
	
	/**
	 * 单机流量
	 */
	INJVM,
	
	/**
	 * 分布式流控
	 */
	DISTR;
	
}
