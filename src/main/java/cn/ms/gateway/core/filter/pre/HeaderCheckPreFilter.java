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
public class HeaderCheckPreFilter extends IFilter<Request, Response> {

	private ConcurrentHashSet<ParameterModel> headerParams = new ConcurrentHashSet<ParameterModel>();
	
	public HeaderCheckPreFilter() {
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
				if (headerVal == null || headerVal.length() < 1) {// 第一步：参数有无的校验
					//$NON-NLS-参数不存在$
					res.setResponseType(ResponseType.HEADER_PARAM_NOTNULL.wrapper(attribute.getParamKey()));
					System.out.println("KEY:"+attribute.getParamKey()+", "+res.getResponseType().getMsg());
					return false;
				} else {
					if (attribute.getLength() > 0) {// 第二步：需要校验长度
						if (attribute.getLength() != headerVal.length()) {
							//$NON-NLS-长度不相等$
							res.setResponseType(ResponseType.HEADER_PARAM_LENGTH_NOTEQ.wrapper(attribute.getParamKey(), attribute.getLength()));
							return false;
						}
					}

					if (!(attribute.getType() == null || attribute.getType().length() < 1)) {// 第三步：需要校验类型
						if (!ParameterTypeCheck.checkType(attribute.getType(), headerVal)) {
							// 校验类型失败
							res.setResponseType(ResponseType.HEADER_PARAM_TYPE_ILLEGAL.wrapper(attribute.getParamKey(), attribute.getType()));
							return false;
						}
					}
				}
			}
		}
		
		res.setHttpBody("响应报文体");
		res.setResponseType(ResponseType.SUCCESS);
		
		return true;
	}

}
