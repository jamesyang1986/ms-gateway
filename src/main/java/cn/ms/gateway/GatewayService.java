package cn.ms.gateway;

import java.util.HashMap;
import java.util.Map;

import cn.ms.gateway.entity.Request;
import cn.ms.gateway.entity.Response;
import cn.ms.netty.server.common.HttpConstants;
import cn.ms.netty.server.common.RequestMethod;
import cn.ms.netty.server.common.annotations.Controller;
import cn.ms.netty.server.common.annotations.Header;
import cn.ms.netty.server.common.annotations.PathVariable;
import cn.ms.netty.server.common.annotations.RequestBody;
import cn.ms.netty.server.common.annotations.RequestMapping;
import cn.ms.netty.server.core.rest.HttpSession;
import cn.ms.netty.server.core.rest.entity.HttpResult;

/**
 * 微服务控制器
 * 
 * @author lry
 */
@Controller
@RequestMapping("/gateway/v1")
public class GatewayService {

	static {
		HttpConstants.MS_NETTY_SERVER="MS-GATEWAY";
	}
	
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
	public HttpResult biz(HttpSession httpContext,
			@Header(value = "channelId", required = false) String channelId,
			@Header(value = "tradeId", required = false) String tradeId,
			@Header(value = "callId", required = false) String callId,
			@PathVariable("serviceId") String serviceId,
			@RequestBody String context) {

		String content = "channelId:" + channelId + ", tradeId:" + tradeId
				+ ", callId:" + callId + ", serviceId: " + serviceId + ", context: " + context;
		System.out.println(content);
		
		System.out.println(httpContext.getHttpHeaders());
		
		Request req = new Request();
		Response res = new Response();

		try {
			//Gateway.INSTANCE.gfc.filterChain(req, res);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		System.out.println(httpContext.getRequestId());
		Map<String, String> httpHeaders=new HashMap<String, String>();
		httpHeaders.put("Code", "1000");
		httpHeaders.put("Msg", "成功了");

		return new HttpResult(content, httpHeaders);
	}

}
