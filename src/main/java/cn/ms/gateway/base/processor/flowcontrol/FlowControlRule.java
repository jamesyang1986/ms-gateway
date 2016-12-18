package cn.ms.gateway.base.processor.flowcontrol;

public class FlowControlRule {
	
	private String flowControlKey;

	//$NON-NLS-并发控制$
	private int cctNum;
	private long cctRryTimeout;//尝试获取超时时间,单位为毫秒
	
	//$NON-NLS-QPS控制$
	private double qpsNum;
	private long warmupPeriod;
	private long qpsRryTimeout;//尝试获取超时时间,单位为毫秒
	
	public String getFlowControlKey() {
		return flowControlKey;
	}
	public void setFlowControlKey(String flowControlKey) {
		this.flowControlKey = flowControlKey;
	}
	public int getCctNum() {
		return cctNum;
	}
	public void setCctNum(int cctNum) {
		this.cctNum = cctNum;
	}
	public long getCctRryTimeout() {
		return cctRryTimeout;
	}
	public void setCctRryTimeout(long cctRryTimeout) {
		this.cctRryTimeout = cctRryTimeout;
	}
	public double getQpsNum() {
		return qpsNum;
	}
	public void setQpsNum(double qpsNum) {
		this.qpsNum = qpsNum;
	}
	public long getWarmupPeriod() {
		return warmupPeriod;
	}
	public void setWarmupPeriod(long warmupPeriod) {
		this.warmupPeriod = warmupPeriod;
	}
	public long getQpsRryTimeout() {
		return qpsRryTimeout;
	}
	public void setQpsRryTimeout(long qpsRryTimeout) {
		this.qpsRryTimeout = qpsRryTimeout;
	}
	
}
