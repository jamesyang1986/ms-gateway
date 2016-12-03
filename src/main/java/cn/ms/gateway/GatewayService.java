package cn.ms.gateway;

import cn.ms.gateway.entity.Request;
import cn.ms.gateway.entity.Response;
import cn.ms.netty.server.common.HttpConstants;
import cn.ms.netty.server.common.RequestMethod;
import cn.ms.netty.server.common.annotations.Controller;
import cn.ms.netty.server.common.annotations.PathVariable;
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
	public HttpResult biz(HttpSession httpContext, @PathVariable("serviceId") String serviceId) {

		//组装请求对象
		Request req = new Request();
		req.setServiceId(serviceId);
		req.setHttpAttributes(httpContext.getHttpAttributes());
		req.setHttpBody(httpContext.getHttpBody());
		req.setHttpHeaders(httpContext.getHttpHeaders());
		req.setHttpParams(httpContext.getHttpParams());
		req.setRemoteAddress(httpContext.getRemoteAddress());
		req.setRequestId(httpContext.getRequestId());
		req.setTerms(httpContext.getTerms());
		req.setUri(httpContext.getUri());
		
		Response res = Response.build();
		try {
			Gateway.INSTANCE.gfc.filterChain(req, res);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		return res.buildHttpResult();
	}

}
