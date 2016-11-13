package cn.ms.gateway.core;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpHeaders.Names;
import io.netty.handler.codec.http.HttpHeaders.Values;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import cn.ms.gateway.common.log.Logger;
import cn.ms.gateway.common.log.LoggerFactory;
import cn.ms.gateway.entity.GatewayREQ;
import cn.ms.gateway.entity.GatewayRES;

/**
 * 静态装配支持类
 * 
 * @author lry
 */
@SuppressWarnings("deprecation")
public class AssemblySupport {

	public static final Logger logger = LoggerFactory.getLogger(AssemblySupport.class);
	
	/**
	 * 通道响应
	 * 
	 * @param gatewayREQ
	 * @param content
	 */
	public static void HttpServerResponse(GatewayREQ gatewayREQ, GatewayRES gatewayRES) {
		try {
			//$NON-NLS-通道响应$
			FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, 
					HttpResponseStatus.OK, Unpooled.wrappedBuffer(gatewayRES.getContent().getBytes()));
			response.headers().set(Names.CONTENT_TYPE, "text/plain");
			response.headers().set(Names.CONTENT_LENGTH, response.content().readableBytes());
			if (HttpHeaders.isKeepAlive(gatewayREQ.getRequest())) {
				response.headers().set(Names.CONNECTION, Values.KEEP_ALIVE);
			}
			
			gatewayREQ.getCtx().writeAndFlush(response);
		} catch (Throwable t) {
			logger.error(t, "网关响应装配异常: %s", t.getMessage());
		} finally {
			//TODO req对象的对象引用问题?
			logger.info("[路由总耗时:%sms]=====路由结束=====", (System.currentTimeMillis() - gatewayREQ.getRouteStartTime()));
			logger.info("[网关总耗时:%sms]=====交易结束=====", (System.currentTimeMillis() - gatewayREQ.getTradeStartTime()));
		}
	}
	
}
