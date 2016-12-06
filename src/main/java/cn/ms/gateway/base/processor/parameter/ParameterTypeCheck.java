package cn.ms.gateway.base.processor.parameter;

import java.math.BigDecimal;

/**
 * 校验参数类型和长度
 * 
 * @author lry
 */
public class ParameterTypeCheck {

	public static boolean checkType(String type, String data) {
		switch (type) {
		case "int":
			try {
				Integer.parseInt(data);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		case "long":
			try {
				Long.parseLong(data);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		case "float":
			try {
				Float.parseFloat(data);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		case "short":
			try {
				Short.parseShort(data);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		case "double":
			try {
				Double.parseDouble(data);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		case "byte":
			try {
				Byte.parseByte(data);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		case "bigdecimal":
			try {
				@SuppressWarnings("unused")
				BigDecimal bg = new BigDecimal(data);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		case "boolean":
			if (data.equals("true")) {
				return true;
			} else if (data.equals("false")) {
				return true;
			} else {
				return false;
			}
		default:
			return false;
		}
	}

}
