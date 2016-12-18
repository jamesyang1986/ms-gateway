package cn.ms.gateway.base.processor.flowcontrol;


public abstract class FlowControlHandler<REQ, RES> {

	REQ req;
	RES res;
	Object[] args;
	
	public FlowControlHandler(REQ req, RES res, Object... args) {
		
	}
	
	public void handler(){
		
	}
	
	public abstract void doHandler(REQ req, RES res, Object... args);
	
}
