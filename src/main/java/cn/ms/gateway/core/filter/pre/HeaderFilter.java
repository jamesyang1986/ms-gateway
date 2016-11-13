package cn.ms.gateway.core.filter.pre;

import java.util.HashSet;
import java.util.Map;
import java.util.regex.Matcher;

import cn.ms.gateway.base.filter.annotation.Filter;
import cn.ms.gateway.base.filter.annotation.FilterType;
import cn.ms.gateway.base.filter.support.MSFilter;
import cn.ms.gateway.common.Conf;
import cn.ms.gateway.common.Constants;
import cn.ms.gateway.entity.GatewayREQ;
import cn.ms.gateway.entity.GatewayRES;
import cn.ms.gateway.neural.param.ParamAttribute;
import cn.ms.gateway.neural.param.ParamModuler;

/**
 * 请求头参数校验
 * 
 * @author lry
 */
@Filter(value = FilterType.PRE, order = 130)
public class HeaderFilter extends MSFilter<GatewayREQ, GatewayRES> {
	
	private HashSet<ParamAttribute> headerParams = new HashSet<ParamAttribute>();
	
	@Override
	public void init() throws Exception {
		refresh();
	}
	
	@Override
	public void refresh() throws Exception {
		headerParams.clear();
		String headerstr=Conf.CONF.getHeaders();
		if (headerstr != null) {
			if (headerstr.length() > 0) {
				String[] paramArray = headerstr.split(Constants.PARAM_SEQ);
				for (String pm : paramArray) {
					ParamAttribute paramAttribute=new ParamAttribute();
					int fno=pm.indexOf("{");
					paramAttribute.setParamKey((fno>0)?pm.substring(0, fno):pm);
					
					Matcher m = Constants.PARAM_PATTERN.matcher(pm);
					String paramstr = "";//length=5&type=int
					if (m.find()) {
						paramstr = m.group(1).replace(",", "&");
						Map<String,String> attributeMap=ParamModuler.getParamsMap(paramstr, Constants.DEFAULT_ENCODEY);
						String length=attributeMap.get(Constants.PARAM_LENGTH_KEY);
						if(length!=null){
							if(length.length()>0){
								//TODO 校验类型
								paramAttribute.setLength(Integer.parseInt(length));
							}
						}
						
						String type=attributeMap.get(Constants.PARAM_TYPE_KEY);
						if(type!=null){
							if(type.length()>0){
								paramAttribute.setType(type);
							}
						}
					}
					
					headerParams.add(paramAttribute);
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
	public GatewayRES run(GatewayREQ req, GatewayRES res, Object... args) throws Exception {
		if(!headerParams.isEmpty()){
			for (ParamAttribute attribute:headerParams) {//遍历需要验证的参数
				String headerVal=req.getHeaders().get(attribute.getParamKey());
				if(headerVal==null||headerVal.length()<1){//第一步：参数有无的校验
					res=new GatewayRES();
					res.setContent("参数不存在");
					return res;//$NON-NLS-参数不存在$
				}else{
					if(attribute.getLength()>0){//第二步：需要校验长度
						if(attribute.getLength()!=headerVal.length()){
							res=new GatewayRES();
							res.setContent("长度不相等");
							return res;//$NON-NLS-长度不相等$
						}
					}
					
					if(!(attribute.getType()==null||attribute.getType().length()<1)){//第三步：需要校验类型
						if(!ParamModuler.checkType(attribute.getType(), headerVal)){
							res=new GatewayRES();
							res.setContent("校验类型失败");
							return res;//校验类型失败
						}
					}
				}
			}
		}
		
		return null;
	}

}
