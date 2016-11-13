package cn.ms.gateway.core.filter.route;

import cn.ms.gateway.base.adapter.ICallback;
import cn.ms.gateway.base.connector.IConnector;
import cn.ms.gateway.base.filter.annotation.Filter;
import cn.ms.gateway.base.filter.annotation.FilterType;
import cn.ms.gateway.base.filter.support.MSFilter;
import cn.ms.gateway.core.AssemblySupport;
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
	public GatewayRES run(final GatewayREQ req, GatewayRES res, Object... args) throws Exception {
		connector.connector(req, new ICallback<GatewayREQ, GatewayRES>() {
			@Override
			public void before(GatewayRES res, Object... args) throws Exception {
			}
			@Override
			public GatewayRES callback(GatewayRES res, Object... args) throws Exception {
				AssemblySupport.HttpServerResponse(req, res);
				return null;
			}
			@Override
			public void after(GatewayRES res, Object... args) throws Exception {
			}
			@Override
			public void exception(Throwable t) {
				GatewayRES res=new GatewayRES();
				res.setContent("网关路由异常:"+t.getMessage());
				AssemblySupport.HttpServerResponse(req, res);
			}
		}, args);
		
		return null;
	}

}
