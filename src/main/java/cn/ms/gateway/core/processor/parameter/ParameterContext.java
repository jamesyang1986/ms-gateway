package cn.ms.gateway.core.processor.parameter;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

import cn.ms.gateway.Constants;

import com.weibo.api.motan.util.ConcurrentHashSet;


/**
 * 参数校验
 * 
 * @author lry
 */
public class ParameterContext {
	
	/**
	 * 参数校验规则转换
	 * 
	 * @param headers
	 * @return
	 */
	public static ConcurrentHashSet<ParameterModel> convert(String headers) {
		ConcurrentHashSet<ParameterModel> headerParams = new ConcurrentHashSet<ParameterModel>();
		if (headers != null) {
			if (headers.length() > 0) {
				String[] paramArray = headers.split(Constants.PARAM_SEQ);
				for (String pm : paramArray) {
					int fno = pm.indexOf("{");
					ParameterModel paramAttribute = new ParameterModel();
					paramAttribute.setParamKey((fno > 0) ? pm.substring(0, fno) : pm);

					Matcher m = Constants.PARAM_PATTERN.matcher(pm);
					if (m.find()) {
						String paramstr = m.group(1).replace(",", "&");
						Map<String, String> attributeMap = ParameterContext.getParamsMap(paramstr, Constants.DEFAULT_ENCODEY);
						String length = attributeMap.get(Constants.PARAM_LENGTH_KEY);
						if (length != null) {
							if (length.length() > 0) {
								paramAttribute.setLength(Integer.parseInt(length));
							}
						}
						String type = attributeMap.get(Constants.PARAM_TYPE_KEY);
						if (type != null) {
							if (type.length() > 0) {
								paramAttribute.setType(type);
							}
						}
					}

					headerParams.add(paramAttribute);
				}
			}
		}
		
		return headerParams;
	}
	
	/**
	 * 将传入的“a=12&b=88”形式的字符串转换成map返回
	 * 
	 * @param queryString
	 * @param enc
	 * @return
	 */
	public static Map<String, String> getParamsMap(String queryString, String enc) {
		Map<String, String> params = new HashMap<String, String>();
		Map<String, String[]> paramsMap = getParamsArrayMap(queryString, enc);
		if (!paramsMap.isEmpty()) {
			for (Map.Entry<String, String[]> entry : paramsMap.entrySet()) {
				params.put(entry.getKey(), entry.getValue()[entry.getValue().length - 1]);
			}
		}
		return params;
	}

	public static Map<String, String[]> getParamsArrayMap(String queryString, String enc) {
		Map<String, String[]> paramsMap = new HashMap<String, String[]>();
		if (queryString != null && queryString.length() > 0) {
			int ampersandIndex, lastAmpersandIndex = 0;
			String subStr, param, value;
			String[] paramPair, values, newValues;
			do {
				ampersandIndex = queryString.indexOf('&', lastAmpersandIndex) + 1;
				if (ampersandIndex > 0) {
					subStr = queryString.substring(lastAmpersandIndex, ampersandIndex - 1);
					lastAmpersandIndex = ampersandIndex;
				} else {
					subStr = queryString.substring(lastAmpersandIndex);
				}
				paramPair = subStr.split("=");
				param = paramPair[0];
				value = paramPair.length == 1 ? "" : paramPair[1];
				try {
					value = URLDecoder.decode(value, enc);
				} catch (UnsupportedEncodingException ignored) {
				}
				if (paramsMap.containsKey(param)) {
					values = (String[]) paramsMap.get(param);
					int len = values.length;
					newValues = new String[len + 1];
					System.arraycopy(values, 0, newValues, 0, len);
					newValues[len] = value;
				} else {
					newValues = new String[] { value };
				}
				paramsMap.put(param, newValues);
			} while (ampersandIndex > 0);
		}
		return paramsMap;
	}
	
}
