package cn.ms.gateway.core;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders.Names;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;
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
	 * @param req
	 * @param res
	 * @param content
	 */
	public static void HttpServerResponse(GatewayREQ req, GatewayRES res) {
		try {
			//$NON-NLS-通道响应$
			FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,
					Unpooled.wrappedBuffer(res.getContent().getBytes(CharsetUtil.UTF_8)));
			response.headers().set(Names.CONTENT_TYPE, "text/plain");
			response.headers().set(Names.CONTENT_LENGTH, response.content().readableBytes());
//			if (HttpHeaders.isKeepAlive(req.getRequest())) {
//				response.headers().set(Names.CONNECTION, Values.KEEP_ALIVE);
//			}

			req.getCtx().writeAndFlush(response);
		} catch (Throwable t) {
			logger.error(t, "网关响应装配异常: %s", t.getMessage());
		} finally {
			logger.info("[网关总耗时:%sms]=====交易结束=====", (System.currentTimeMillis() - req.getTradeStartTime()));
		}
	}

}
