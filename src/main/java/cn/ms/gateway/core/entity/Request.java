package cn.ms.gateway.core.entity;

public class Request {

	private String data;
	private String msg;
	
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	@Override
	public String toString() {
		return "Request [data=" + data + ", msg=" + msg + "]";
	}

}
