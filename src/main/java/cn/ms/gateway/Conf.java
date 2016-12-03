package cn.ms.gateway;

public enum Conf {

	CONF;
	
	private volatile String headers="channelId;bizno{length=32};sysno{length=32}";

	public String getHeaders() {
		return headers;
	}

	public void setHeaders(String headers) {
		this.headers = headers;
	}
	
}
