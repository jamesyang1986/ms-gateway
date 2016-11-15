package cn.ms.gateway.core;

import cn.ms.gateway.common.log.Logger;
import cn.ms.gateway.common.log.LoggerFactory;
import cn.ms.gateway.entity.GatewayREQ;
import cn.ms.gateway.entity.GatewayRES;

/**
 * 静态装配支持类
 * 
 * @author lry
 */
public class AssemblySupport {

	public static final Logger logger = LoggerFactory.getLogger(AssemblySupport.class);

	/**
	 * 通道响应
	 * 
	 * @param req
	 * @param res
	 * @param content
	 */
	public static void HttpServerResponse(GatewayREQ req, GatewayRES res) {
		try {
			//$NON-NLS-通道响应$
			req.getOut().writeStringAndFlush(res.getContent());
		} catch (Throwable t) {
			logger.error(t, "网关响应装配异常: %s", t.getMessage());
		} finally {
			logger.info("[网关总耗时:%sms]=====交易结束=====", (System.currentTimeMillis() - req.getTradeStartTime()));
		}
	}

}
