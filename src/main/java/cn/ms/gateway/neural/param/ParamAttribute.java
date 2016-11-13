package cn.ms.gateway.neural.param;

public class ParamAttribute {

	private String paramKey;
	private int length;
	private String type;

	public String getParamKey() {
		return paramKey;
	}

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}
	public void setLength(int length) {
		this.length = length;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getLength() {
		return length;
	}

	public String getType() {
		return type;
	}

	@Override
	public String toString() {
		return "ParamAttribute [paramKey=" + paramKey + ", length=" + length
				+ ", type=" + type + "]";
	}

}
