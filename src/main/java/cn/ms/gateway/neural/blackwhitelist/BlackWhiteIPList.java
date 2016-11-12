package cn.ms.gateway.neural.blackwhitelist;

import java.util.Map;

/**
 * IP 过滤器
 * 
 * @author lry
 */
public interface BlackWhiteIPList {

	/**
	 * 设置更新数据
	 * 
	 * @param blackWhiteIPs
	 */
	void setBlackWhiteIPs(Map<BlackWhiteIPListType, String> blackWhiteIPs);

	/**
	 * 校验黑白IP
	 * 
	 * @param blackWhiteIPListType
	 * @param ip
	 * @return
	 */
	boolean check(BlackWhiteIPListType blackWhiteIPListType, String ip);

}
