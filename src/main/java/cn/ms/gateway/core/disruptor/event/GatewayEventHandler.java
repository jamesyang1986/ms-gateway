package cn.ms.gateway.core.disruptor.event;

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
import cn.ms.gateway.core.connector.IConnectorCallback;
import cn.ms.gateway.core.entity.GatewayREQ;

import com.lmax.disruptor.EventHandler;

/**
 * 网关事件处理器
 * 
 * @author lry
 */
@SuppressWarnings("deprecation")
public class GatewayEventHandler implements EventHandler<GatewayREQ> {

	IConnector connector;

	public GatewayEventHandler(IConnector connector) {
		this.connector = connector;
	}

	@Override
	public void onEvent(final GatewayREQ event, long sequence, boolean endOfBatch) throws Exception {
		try {
			connector.connect(event, new IConnectorCallback() {
				
				@Override
				public void before(HttpResponse response) throws Exception {
				}
				
				@Override
				public void callback(String content) throws Exception {
					try {
						//$NON-NLS-通道响应$
						FullHttpResponse response = new DefaultFullHttpResponse(
								HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(content.getBytes()));
						response.headers().set(Names.CONTENT_TYPE, "text/plain");
						response.headers().set(Names.CONTENT_LENGTH,response.content().readableBytes());
						if (HttpHeaders.isKeepAlive(event.getRequest())) {
							response.headers().set(Names.CONNECTION,Values.KEEP_ALIVE);
						}
						event.getCtx().write(response);
						event.getCtx().flush();
					} catch (Throwable t) {
						t.printStackTrace();
					}
				}

			});
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

}