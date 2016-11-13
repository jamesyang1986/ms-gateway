package cn.ms.gateway.core.filter.pre;

import java.util.HashSet;

import cn.ms.gateway.base.filter.annotation.Filter;
import cn.ms.gateway.base.filter.annotation.FilterType;
import cn.ms.gateway.base.filter.support.MSFilter;
import cn.ms.gateway.common.Conf;
import cn.ms.gateway.entity.GatewayREQ;
import cn.ms.gateway.entity.GatewayRES;

/**
 * 参数校验
 * 
 * @author lry
 */
@Filter(value = FilterType.PRE, order = 120)
public class ParamPreFilter extends MSFilter<GatewayREQ, GatewayRES> {

	private HashSet<String> params = new HashSet<String>();

	@Override
	public void init() throws Exception {
		refresh();
	}

	@Override
	public void refresh() throws Exception {
		params.clear();

		String paramsStr = Conf.CONF.getParams();
		if (paramsStr != null) {
			if (paramsStr.length() > 0) {
				String[] paramArray = paramsStr.split(";");
				for (String pm : paramArray) {
					pm = (pm.indexOf("{")) > 0 ? pm.substring(0,
							pm.indexOf("{")) : pm;
					params.add(pm);
				}
			}
		}
	}

	@Override
	public boolean check(GatewayREQ req, GatewayRES res, Object... args)
			throws Exception {
		return true;
	}

	@Override
	public GatewayRES run(GatewayREQ req, GatewayRES res, Object... args)
			throws Exception {
		if (!params.isEmpty()) {
			for (String param : params) {
				if (!req.getParams().containsKey(param)) {
					res = new GatewayRES();
					res.setContent(String.format("请求参数'%s'不能为空", param));
					return res;
				}
			}
		}

		return null;
	}
}
