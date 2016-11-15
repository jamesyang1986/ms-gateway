package cn.ms.gateway.core.filter.route;

import cn.ms.gateway.base.connector.IConnector;
import cn.ms.gateway.base.filter.annotation.Filter;
import cn.ms.gateway.base.filter.annotation.FilterType;
import cn.ms.gateway.base.filter.support.MSFilter;
import cn.ms.gateway.entity.GatewayREQ;
import cn.ms.gateway.entity.GatewayRES;

@Filter(value = FilterType.ROUTE, order = 100)
public class ConnectorFilter extends MSFilter<GatewayREQ, GatewayRES> {

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
	public GatewayRES run(GatewayREQ req, GatewayRES res, Object... args) throws Exception {
		try {
			res = connector.connector(req, args);
		} catch (Exception e) {
			res=new GatewayRES();
			res.setContent("网关路由异常:"+e.getMessage());
		}
		
		return res;
	}

}
