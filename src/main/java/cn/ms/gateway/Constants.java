package cn.ms.gateway;

import java.util.regex.Pattern;

public class Constants {
	
	public static final String DEFAULT_ENCODEY="UTF-8";

	public static final String PARAM_LENGTH_KEY="length";
	public static final String PARAM_TYPE_KEY="type";
	public static final Pattern PARAM_PATTERN = Pattern.compile("\\{(.*?)\\}");
	public static final String PARAM_SEQ=";";
	public static final String PARAM_MODEL_SEQ=",";
	
}
