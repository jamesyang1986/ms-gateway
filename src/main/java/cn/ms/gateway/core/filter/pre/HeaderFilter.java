package cn.ms.gateway.core.filter.pre;

import cn.ms.gateway.base.filter.annotation.Filter;
import cn.ms.gateway.base.filter.support.MSFilter;
import cn.ms.gateway.entity.GatewayREQ;
import cn.ms.gateway.entity.GatewayRES;

@Filter
public class HeaderFilter extends MSFilter<GatewayREQ, GatewayRES>{

	@Override
	public boolean check(GatewayREQ req, GatewayRES res, Object... args)
			throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public GatewayRES run(GatewayREQ req, GatewayRES res, Object... args)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
