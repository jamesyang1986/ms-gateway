package cn.ms.gateway.core.filter.route;

import org.apache.logging.log4j.ThreadContext;

import cn.ms.gateway.base.adapter.ICallback;
import cn.ms.gateway.base.connector.IConnector;
import cn.ms.gateway.base.filter.annotation.Filter;
import cn.ms.gateway.base.filter.support.MSFilter;
import cn.ms.gateway.common.Constants;
import cn.ms.gateway.common.log.Logger;
import cn.ms.gateway.common.log.LoggerFactory;
import cn.ms.gateway.core.AssemblySupport;
import cn.ms.gateway.core.connector.NettyConnector;
import cn.ms.gateway.entity.GatewayREQ;
import cn.ms.gateway.entity.GatewayRES;

@Filter
public class ConnectorFilter extends MSFilter<GatewayREQ, GatewayRES> {

	private Logger logger=LoggerFactory.getLogger(NettyConnector.class);
	
	IConnector<GatewayREQ, GatewayRES> connector = null;

	@SuppressWarnings("unchecked")
	@Override
	public <MOD> void inject(MOD mod) throws Exception {
		if (mod instanceof IConnector) {
			connector = (IConnector<GatewayREQ, GatewayRES>) mod;
		}
	}

	@Override
	public boolean check(GatewayREQ req, GatewayRES res, Object... args)
			throws Exception {
		return true;
	}

	@Override
	public GatewayRES run(final GatewayREQ req, GatewayRES res, Object... args) throws Exception {
		req.setRouteStartTime(System.currentTimeMillis());
		logger.info("=====交易开始=====");
		
		connector.connector(req, new ICallback<GatewayREQ, GatewayRES>() {
			@Override
			public void before(GatewayRES res, Object... args) throws Exception {
				
			}
			@Override
			public GatewayRES callback(GatewayRES res, Object... args) throws Exception {
				ThreadContext.put(Constants.TRADEID_KEY, req.getTradeId());// 线程参数继续
				
				AssemblySupport.HttpServerResponse(req, res);
				return null;
			}
			@Override
			public void after(GatewayRES res, Object... args) throws Exception {
				
			}
		}, args);
		
		return null;
	}

}
