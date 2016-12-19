package cn.ms.gateway.base.processor.flowcontrol;

public interface FlowControlHandler<REQ, RES> {

	void doHandler();
	
}
