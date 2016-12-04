package cn.ms.gateway;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import cn.ms.gateway.entity.ResponseType;

/**
 * 网关请求统计
 * 
 * @author lry
 */
public class GatewayStatistics {

	private static ConcurrentHashMap<ResponseType, AtomicLong> allResponseTypeData=new ConcurrentHashMap<ResponseType, AtomicLong>();
	
	static {
		for (ResponseType responseType : ResponseType.values()) {
			allResponseTypeData.put(responseType, new AtomicLong(0));
		}
	}
	
	public static void incrAllResponseTypeData(ResponseType responseType) {
		allResponseTypeData.get(responseType).incrementAndGet();
	}

}
