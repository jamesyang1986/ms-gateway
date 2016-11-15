package cn.ms.gateway.entity;

public class INOUT<IN, OUT> {

	IN in;
	OUT out;

	public IN getIn() {
		return in;
	}

	public void setIn(IN in) {
		this.in = in;
	}

	public OUT getOut() {
		return out;
	}

	public void setOut(OUT out) {
		this.out = out;
	}

}
