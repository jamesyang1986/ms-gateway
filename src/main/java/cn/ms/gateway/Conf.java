package cn.ms.gateway;

public enum Conf {

	CONF;
	
	private volatile String headers="channelId;tradeId{length=32};callId{length=32}";

	public String getHeaders() {
		return headers;
	}

	public void setHeaders(String headers) {
		this.headers = headers;
	}
	
}
