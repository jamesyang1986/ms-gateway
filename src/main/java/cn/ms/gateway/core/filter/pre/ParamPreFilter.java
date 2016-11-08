package cn.ms.gateway.core.filter.pre;

import cn.ms.gateway.base.filter.FilterType;
import cn.ms.gateway.base.filter.MSFilter;
import cn.ms.gateway.common.Constants;
import cn.ms.gateway.common.annotation.Filter;
import cn.ms.gateway.entity.GatewayREQ;
import cn.ms.gateway.entity.GatewayRES;

/**
 * 参数校验
 * 
 * @author lry
 */
@Filter(value = FilterType.PRE, order = 120)
public class ParamPreFilter extends MSFilter<GatewayREQ, GatewayRES> {

	@Override
	public boolean check(GatewayREQ req, GatewayRES res, Object... args) throws Exception {
		return true;
	}

	@Override
	public GatewayRES run(GatewayREQ req, GatewayRES res, Object... args)
			throws Exception {
		if (!req.getParams().containsKey(Constants.SERVICEID_KEY)) {
			res = new GatewayRES();
			res.setContent("服务ID(serviceId)不能为空");

			return res;
		}

		return null;
	}

}
