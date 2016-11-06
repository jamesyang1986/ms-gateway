package cn.ms.gateway.core.processer;

import io.netty.handler.codec.http.HttpResponse;

import org.apache.logging.log4j.ThreadContext;

import cn.ms.gateway.base.connector.ICallback;
import cn.ms.gateway.base.connector.IConnector;
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
public class ProcesserHandler implements EventHandler<GatewayREQ> {

	public static final Logger logger = LoggerFactory.getLogger(ProcesserHandler.class);

	private IConnector<GatewayRES, GatewayRES, HttpResponse> connector;

	public ProcesserHandler(IConnector<GatewayRES, GatewayRES, HttpResponse> connector) {
		this.connector = connector;
	}

	@Override
	public void onEvent(final GatewayREQ gatewayREQ, long sequence, boolean endOfBatch) throws Exception {
		try {
			gatewayREQ.setRouteStartTime(System.currentTimeMillis());
			ThreadContext.put(Constants.TRADEID_KEY, gatewayREQ.getTradeId());// 线程参数继续
			logger.info("=====路由开始=====");
			
			connector.connect(gatewayREQ, new ICallback<GatewayRES, GatewayRES, HttpResponse>() {
				@Override
				public void before(HttpResponse response, Object... args) throws Exception {
				}
				
				@Override
				public GatewayRES callback(GatewayRES gatewayRES, Object... args) throws Exception {
					ThreadContext.put(Constants.TRADEID_KEY, gatewayREQ.getTradeId());// 线程参数继续
					//$NON-NLS-组装响应结果$
					AssemblySupport.HttpServerResponse(gatewayREQ, gatewayRES);
					
					return null;
				}
			});
		} catch (Throwable t) {
			logger.error(t, "微服务网关远程路由异常: %s", t.getMessage());
		}
	}

}