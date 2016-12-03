package cn.ms.gateway;

import cn.ms.gateway.entity.Request;
import cn.ms.gateway.entity.Response;
import cn.ms.netty.server.common.RequestMethod;
import cn.ms.netty.server.common.annotations.Controller;
import cn.ms.netty.server.common.annotations.Header;
import cn.ms.netty.server.common.annotations.PathVariable;
import cn.ms.netty.server.common.annotations.RequestBody;
import cn.ms.netty.server.common.annotations.RequestMapping;

/**
 * 微服务控制器
 * 
 * @author lry
 */
@Controller
@RequestMapping("/gateway/v1")
public class GatewayService {

	/**
	 * 网关业务调用
	 * 
	 * @param channelId 渠道ID
	 * @param tradeId 业务ID
	 * @param callId 调用ID
	 * @param serviceId 服务ID
	 * @param context 请求报文
	 * @return
	 */
	@RequestMapping(value = "/biz/{serviceId}", method = RequestMethod.POST)
	public String biz(
			@Header(value = "channelId", required = false) String channelId,
			@Header(value = "tradeId", required = false) String tradeId,
			@Header(value = "callId", required = false) String callId,
			@PathVariable("serviceId") String serviceId,
			@RequestBody String context) {

		String msg = "channelId:" + channelId + ", tradeId:" + tradeId
				+ ", callId:" + callId + ", serviceId: " + serviceId + ", context: " + context;
		System.out.println(msg);

		Request req = new Request();
		Response res = new Response();

		try {
			Gateway.INSTANCE.gfc.filterChain(req, res);
		} catch (Throwable e) {
			e.printStackTrace();
		}

		return msg;
	}

}
