package cn.ms.gateway.core.filter.route;

import cn.ms.gateway.base.connector.IConnector;
import cn.ms.gateway.base.filter.annotation.Filter;
import cn.ms.gateway.base.filter.annotation.FilterType;
import cn.ms.gateway.base.filter.support.MSFilter;
import cn.ms.gateway.common.log.Logger;
import cn.ms.gateway.common.log.LoggerFactory;
import cn.ms.gateway.entity.GatewayREQ;
import cn.ms.gateway.entity.GatewayRES;

@Filter(value = FilterType.ROUTE, order = 100)
public class ConnectorFilter extends MSFilter<GatewayREQ, GatewayRES> {

	private static final Logger logger = LoggerFactory.getLogger(ConnectorFilter.class);
	
	IConnector<GatewayREQ, GatewayRES> connector = null;

	@SuppressWarnings("unchecked")
	@Override
	public <MOD> void mod(MOD mod) throws Exception {
		if (mod instanceof IConnector) {
			connector = (IConnector<GatewayREQ, GatewayRES>) mod;
		}
	}

	@Override
	public boolean check(GatewayREQ req, GatewayRES res, Object... args) throws Exception {
		return true;
	}

	@Override
	public GatewayRES run(GatewayREQ req, GatewayRES res, Object... args) throws Exception {
		try {
			res = connector.connector(req, args);
		} catch (Exception e) {
			logger.error("网关路由异常: %s", e.getMessage());
			
			res=new GatewayRES();
			res.setContent("网关路由异常:"+e.getMessage());
		}
		
		return res;
	}

}
