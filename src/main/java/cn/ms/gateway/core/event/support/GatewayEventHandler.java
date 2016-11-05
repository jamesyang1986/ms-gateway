package cn.ms.gateway.core.event.support;

import org.apache.logging.log4j.ThreadContext;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpHeaders.Names;
import io.netty.handler.codec.http.HttpHeaders.Values;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import cn.ms.gateway.base.connector.IConnector;
import cn.ms.gateway.base.connector.IProxyCallback;
import cn.ms.gateway.common.log.Logger;
import cn.ms.gateway.common.log.LoggerFactory;
import cn.ms.gateway.entity.GatewayREQ;

import com.lmax.disruptor.EventHandler;

/**
 * 网关事件处理器
 * 
 * @author lry
 */
@SuppressWarnings("deprecation")
public class GatewayEventHandler implements EventHandler<GatewayREQ> {

	public static final Logger logger = LoggerFactory
			.getLogger(GatewayEventHandler.class);

	private IConnector connector;

	public GatewayEventHandler(IConnector connector) {
		this.connector = connector;
	}

	@Override
	public void onEvent(final GatewayREQ gatewayREQ, long sequence, boolean endOfBatch) throws Exception {
		try {
			gatewayREQ.setRouteStartTime(System.currentTimeMillis());
			ThreadContext.put("tradeId", gatewayREQ.getTradeId());// 线程参数继续
			logger.info("=====路由开始=====");
			
			connector.connect(gatewayREQ, new IProxyCallback() {
				@Override
				public void before(HttpResponse response) throws Exception {
				}
				
				@Override
				public void callback(String content) throws Exception {
					ThreadContext.put("tradeId", gatewayREQ.getTradeId());// 线程参数继续
					
					try {
						//$NON-NLS-通道响应$
						FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, 
								HttpResponseStatus.OK, Unpooled.wrappedBuffer(content.getBytes()));
						response.headers().set(Names.CONTENT_TYPE, "text/plain");
						response.headers().set(Names.CONTENT_LENGTH, response.content().readableBytes());
						if (HttpHeaders.isKeepAlive(gatewayREQ.getRequest())) {
							response.headers().set(Names.CONNECTION, Values.KEEP_ALIVE);
						}
						
						gatewayREQ.getCtx().writeAndFlush(response);
					} catch (Throwable t) {
						t.printStackTrace();
					} finally {
						//TODO req对象的对象引用问题?
						logger.info("[路由总耗时:%sms]=====路由结束=====", (System.currentTimeMillis() - gatewayREQ.getRouteStartTime()));
						logger.info("[网关总耗时:%sms]=====交易结束=====", (System.currentTimeMillis() - gatewayREQ.getTradeStartTime()));
					}
				}
			});
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	public void before(HttpResponse response) throws Exception {
		
	}
	
}