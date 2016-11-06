package cn.ms.gateway.core.filter.pre;

import cn.ms.gateway.base.filter.FilterType;
import cn.ms.gateway.base.filter.IFilter;
import cn.ms.gateway.common.annotation.Filter;
import cn.ms.gateway.entity.GatewayREQ;
import cn.ms.gateway.entity.GatewayRES;

/**
 * URL PATH校验
 * 
 * @author lry
 */
@Filter(value = FilterType.PRE, order=110)
public class PathPreFilter implements IFilter<GatewayREQ, GatewayRES> {

	@Override
	public void init() throws Exception {
		
	}
	
	@Override
	public boolean check(GatewayREQ req, GatewayRES res, Object... args) {
		return true;
	}

	@Override
	public GatewayRES run(GatewayREQ req, GatewayRES res, Object... args) {
		return null;
	}

}
