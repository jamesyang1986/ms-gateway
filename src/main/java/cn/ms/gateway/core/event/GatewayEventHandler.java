package cn.ms.gateway.core.event;

import io.netty.handler.codec.http.HttpResponse;

import org.apache.logging.log4j.ThreadContext;

import cn.ms.gateway.base.connector.IConnector;
import cn.ms.gateway.base.connector.IConnectorCallback;
import cn.ms.gateway.common.Constants;
import cn.ms.gateway.common.log.Logger;
import cn.ms.gateway.common.log.LoggerFactory;
import cn.ms.gateway.core.AssemblySupport;
import cn.ms.gateway.entity.GatewayREQ;
import cn.ms.gateway.entity.GatewayRES;

import com.lmax.disruptor.EventHandler;

/**
 * 网关事件处理器
 * 
 * @author lry
 */
public class GatewayEventHandler implements EventHandler<GatewayREQ> {

	public static final Logger logger = LoggerFactory.getLogger(GatewayEventHandler.class);

	private IConnector connector;

	public GatewayEventHandler(IConnector connector) {
		this.connector = connector;
	}

	@Override
	public void onEvent(final GatewayREQ gatewayREQ, long sequence, boolean endOfBatch) throws Exception {
		try {
			gatewayREQ.setRouteStartTime(System.currentTimeMillis());
			ThreadContext.put(Constants.TRADEID_KEY, gatewayREQ.getTradeId());// 线程参数继续
			logger.info("=====路由开始=====");
			
			connector.connect(gatewayREQ, new IConnectorCallback() {
				@Override
				public void before(HttpResponse response) throws Exception {
				}
				
				@Override
				public void callback(GatewayRES gatewayRES) throws Exception {
					ThreadContext.put(Constants.TRADEID_KEY, gatewayREQ.getTradeId());// 线程参数继续
					
					//$NON-NLS-组装响应结果$
					AssemblySupport.HttpServerResponse(gatewayREQ, gatewayRES);
				}
			});
		} catch (Throwable t) {
			logger.error(t, "微服务网关远程路由异常: %s", t.getMessage());
		}
	}

}