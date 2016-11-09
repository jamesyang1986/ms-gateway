package cn.ms.gateway.neural.param;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;

public class ParamModuler {
	private static String headers = "bizno{length=5,type=int}{};sysno{length=8,type=float};channelId";

	public static void main(String[] args) {
		String[] array = headers.split(";");
		for (String par : array) {
			Pattern p = Pattern.compile("\\{(.*?)\\}");
			Matcher m = p.matcher(par);

			String paramstr = "";
			if (m.find()) {
				paramstr = m.group(1).replace(",", "&");
			}

			System.out.println(JSON
					.toJSONString(getParamsMap(paramstr, "UTF-8")));
		}
	}

	public static Map<String, String> getParamsMap(String queryString,
			String enc) {
		Map<String, String> params = new HashMap<String, String>();
		Map<String, String[]> paramsMap = getParamsArrayMap(queryString, enc);
		if (!paramsMap.isEmpty()) {
			for (Map.Entry<String, String[]> entry : paramsMap.entrySet()) {
				params.put(entry.getKey(),
						entry.getValue()[entry.getValue().length - 1]);
			}
		}

		return params;
	}

	public static Map<String, String[]> getParamsArrayMap(String queryString,
			String enc) {
		Map<String, String[]> paramsMap = new HashMap<String, String[]>();
		if (queryString != null && queryString.length() > 0) {
			int ampersandIndex, lastAmpersandIndex = 0;
			String subStr, param, value;
			String[] paramPair, values, newValues;

			do {
				ampersandIndex = queryString.indexOf('&', lastAmpersandIndex) + 1;
				if (ampersandIndex > 0) {
					subStr = queryString.substring(lastAmpersandIndex,
							ampersandIndex - 1);
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
