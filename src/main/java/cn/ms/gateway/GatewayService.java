package cn.ms.gateway;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Joiner;
import com.weibo.api.motan.util.NetUtils;

import cn.ms.gateway.entity.Request;
import cn.ms.gateway.entity.Response;
import cn.ms.netty.server.common.HttpConstants;
import cn.ms.netty.server.common.RequestMethod;
import cn.ms.netty.server.common.annotations.Controller;
import cn.ms.netty.server.common.annotations.PathVariable;
import cn.ms.netty.server.common.annotations.RequestMapping;
import cn.ms.netty.server.core.NestyServerMonitor;
import cn.ms.netty.server.core.rest.HttpSession;
import cn.ms.netty.server.core.rest.URLResource;
import cn.ms.netty.server.core.rest.controller.URLController;
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
			Bootstrap.INSTANCE.gfc.filterChain(req, res);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		return res.buildHttpResult();
	}

	
	@RequestMapping(value = "/health")
	public String health() {
		return "ok";
	}
	
	@RequestMapping(value = "/openapis")
	public List<Map<String,String>> openapis() {
		List<Map<String,String>> openapis=new ArrayList<Map<String,String>>();
		
		String protocol = Bootstrap.INSTANCE.builder.getProtocol().getCode();
		String host=NetUtils.getLocalAddress().getHostAddress();
		int port = Bootstrap.INSTANCE.builder.getPort();
		
		for (Map.Entry<URLResource, URLController> entry:NestyServerMonitor.getResourcesMap().entrySet()) {
			Map<String,String> apiData=new HashMap<String, String>();
			apiData.put("method", entry.getKey().requestMethod().name());
			apiData.put("url", protocol+"://"+host+":"+port+"/"+Joiner.on("/").skipNulls().join(entry.getKey().fragments()));
			openapis.add(apiData);
		}
		
		return openapis;
	}
	
}
