package cn.ms.gateway.entity;

public class INOUT<IN, OUT> {

	IN input;
	OUT output;
	
	public IN getInput() {
		return input;
	}
	public void setInput(IN input) {
		this.input = input;
	}
	public OUT getOutput() {
		return output;
	}
	public void setOutput(OUT output) {
		this.output = output;
	}

}
