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
	 * 网关业务调用<br>
	 * <br>
	 * 访问URL:[http/https]://[ip]:[port]/gateway/v1/biz/[serviceId]<br>
	 * <br>
	 * 请求头参数:<br>
	 * @param channelId 渠道ID<br>
	 * @param tradeId 业务ID<br>
	 * @param callId 调用ID<br>
	 * <br>
	 * @return
	 */
	@RequestMapping(value = "/biz/{serviceId}", method = RequestMethod.POST)
	public HttpResult biz(HttpSession httpSession, @PathVariable("serviceId") String serviceId) {
		//组装请求对象
		Request req = new Request();
		req.setServiceId(serviceId);
		req.setHttpAttributes(httpSession.getHttpAttributes());
		req.setHttpBody(httpSession.getHttpBody());
		req.setHttpHeaders(httpSession.getHttpHeaders());
		req.setHttpParams(httpSession.getHttpParams());
		req.setRemoteAddress(httpSession.getRemoteAddress());
		req.setRequestId(httpSession.getRequestId());
		req.setTerms(httpSession.getTerms());
		req.setUri(httpSession.getUri());
		
		Response res = Response.build();
		try {
			Gateway.INSTANCE.gfc.filterChain(req, res);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		return res.buildHttpResult();
	}

}
