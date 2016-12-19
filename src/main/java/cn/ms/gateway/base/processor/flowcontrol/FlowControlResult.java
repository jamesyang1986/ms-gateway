package cn.ms.gateway.base.processor.flowcontrol;

public enum FlowControlResult {

	/**流控正常**/
	FC_OK,
	
	/**并发异常**/
	CCT_EXCEPTION,
	/**并发流量溢出**/
	CCT_OVERFLOW,
	
	/**QPS异常**/
	QPS_EXCEPTION,
	/**QPS流控溢出**/
	QPS_OVERFLOW,
	;
	
}
