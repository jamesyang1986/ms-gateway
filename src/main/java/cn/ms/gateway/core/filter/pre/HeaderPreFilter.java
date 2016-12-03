package cn.ms.gateway.core.filter.pre;

import cn.ms.gateway.Conf;
import cn.ms.gateway.base.filter.Filter;
import cn.ms.gateway.base.filter.FilterType;
import cn.ms.gateway.base.filter.IFilter;
import cn.ms.gateway.core.processor.parameter.ParameterContext;
import cn.ms.gateway.core.processor.parameter.ParameterModel;
import cn.ms.gateway.core.processor.parameter.ParameterTypeCheck;
import cn.ms.gateway.entity.Request;
import cn.ms.gateway.entity.Response;
import cn.ms.gateway.entity.ResponseType;

import com.weibo.api.motan.util.ConcurrentHashSet;

/**
 * 请求头参数校验
 * 
 * @author lry
 */
@Filter(value = FilterType.PRE, order = 100)
public class HeaderPreFilter extends IFilter<Request, Response> {

	private ConcurrentHashSet<ParameterModel> headerParams = new ConcurrentHashSet<ParameterModel>();
	
	public HeaderPreFilter() {
		try {
			ref(Conf.CONF.getHeaders());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将配置参数CONF中定义的对请求头的规范读取到set中
	 */
	@Override
	public <REF> void ref(REF ref) throws Exception {
		if(ref instanceof String){
			String headers=(String)ref;
			Conf.CONF.setHeaders(headers);
			headerParams = ParameterContext.convert(headers);
		}
	}
	
	@Override
	public boolean doBefore(Request req, Response res, Object... args) throws Throwable {
		if (!headerParams.isEmpty()) {
			for (ParameterModel attribute : headerParams) {// 遍历需要验证的参数
				String headerVal = req.getHttpHeaders().get(attribute.getParamKey());
				// 第一步：参数有无的校验
				if (headerVal == null || headerVal.length() < 1) {
					res.setResponseType(ResponseType.HEADER_PARAM_NOTNULL.wrapper(attribute.getParamKey()));
					return false;
				} else {
					// 第二步：需要校验长度
					if (attribute.getLength() > 0) {
						if (attribute.getLength() != headerVal.length()) {
							res.setResponseType(ResponseType.HEADER_PARAM_LENGTH_NOTEQ.wrapper(attribute.getParamKey(), attribute.getLength()));
							return false;
						}
					}
					// 第三步：需要校验类型
					if (!(attribute.getType() == null || attribute.getType().length() < 1)) {
						if (!ParameterTypeCheck.checkType(attribute.getType(), headerVal)) {
							res.setResponseType(ResponseType.HEADER_PARAM_TYPE_ILLEGAL.wrapper(attribute.getParamKey(), attribute.getType()));
							return false;
						}
					}
				}
			}
		}
		
		return true;
	}

}
