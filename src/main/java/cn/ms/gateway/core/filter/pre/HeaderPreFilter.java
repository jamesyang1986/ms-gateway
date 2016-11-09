package cn.ms.gateway.core.filter.pre;

import java.util.HashSet;

import cn.ms.gateway.base.filter.FilterType;
import cn.ms.gateway.base.filter.MSFilter;
import cn.ms.gateway.common.Conf;
import cn.ms.gateway.common.annotation.Filter;
import cn.ms.gateway.entity.GatewayREQ;
import cn.ms.gateway.entity.GatewayRES;

/**
 * 请求头参数校验
 * 
 * @author lry
 */
@Filter(value = FilterType.PRE, order = 130)
public class HeaderPreFilter extends MSFilter<GatewayREQ, GatewayRES> {

	private HashSet<String> headerParams = new HashSet<String>();
	
	@Override
	public void init() throws Exception {
		refresh();
	}

	@Override
	public void refresh() throws Exception {
		headerParams.clear();

		String paramsStr = Conf.CONF.getHeaders();
		if (paramsStr != null) {
			if (paramsStr.length() > 0) {
				String[] paramArray = paramsStr.split(";");
				for (String pm : paramArray) {
					//Map<String, String> param=ParamModuler.getParamsMap(pm, Constants.DEFAULT_ENCODEY);
					pm=(pm.indexOf("{"))>0?pm.substring(0,pm.indexOf("{")):pm;
					headerParams.add(pm);
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
		if (!headerParams.isEmpty()) {
			for (String param : headerParams) {
				if (!req.getRequest().headers().contains(param)) {
					res = new GatewayRES();
					res.setContent(String.format("请求头参数'%s'不能为空", param));
					return res;
				}
			}
		}

		return null;
	}

}
