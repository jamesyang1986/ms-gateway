package cn.ms.gateway.common;


/**
 * 常数定义
 * 
 * @author lry
 */
public class Constants {

	/**
	 * 网关内部交易ID KEY
	 */
	public static final String TRADEID_KEY="tradeId";
	/**
	 * 业务交易流水KEY
	 */
	public static final String BIZNO_KEY="bizno";
	/**
	 * 系统调用流水KEY
	 */
	public static final String SYSNO_KEY="sysno";
	/**
	 * 渠道ID KEY
	 */
	public static final String CHANNELID_KEY="channelId";
	
	public static final String PRODUCER_TYPE_SINGLE="SINGLE";
	public static final String PRODUCER_TYPE_MULTI="MULTI";
	
	public static final String WS_BLOCKING_WAIT="BLOCKING_WAIT";
	public static final String WS_SLEEPING_WAIT="SLEEPING_WAIT";
	public static final String WS_YIELDING_WAIT="YIELDING_WAIT";
	
}
