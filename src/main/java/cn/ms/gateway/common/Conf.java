package cn.ms.gateway.common;

import cn.ms.gateway.neural.blackwhitelist.BlackWhiteListType;
import cn.ms.gateway.neural.flowcontrol.FlowControlType;

/**
 * 配置项
 * 
 * @author lry
 */
public enum Conf {

	CONF;
	
	//$NON-NLS-Neural配置参数$
	//$NON-NLS-IP黑白名单校验$
	/**黑/白名单开关**/
	private String blackWhiteList=BlackWhiteListType.NON.getCode();
	/**IP黑名单**/
	private String blackList;
	/**IP白名单**/
	private String whiteList;
	//$NON-NLS-配置好参数校验$
	/**请求参数**/
	private String params="serviceId";
	/**请求头参数**/
	private String headers="channelId;bizno{length=32};sysno{length=32}";
	/**请求参数**/
	private String flowControl=FlowControlType.NON.getCode();
	/**请求头参数**/
	private String flowControlList="channelId{cct=20,qps=1000};ip{cct=20,qps=1000}";
	
	//$NON-NLS-连接器配置项$
	private int connectorWorkerThreadNum=0;
	private int readerIdleTimeSeconds = 30;//读操作空闲30秒
	private int writerIdleTimeSeconds = 60;//写操作空闲60秒
	private int allIdleTimeSeconds = 100;//读写全部空闲100秒
	
	//$NON-NLS-网关容器配置项$
	private int port=9000;
	private boolean ssl=false;
	private int bossGroupThread=0;
	private int workerGroupThread=0;
	
	public String getBlackWhiteList() {
		return blackWhiteList;
	}
	public void setBlackWhiteList(String blackWhiteList) {
		this.blackWhiteList = blackWhiteList;
	}
	public String getBlackList() {
		return blackList;
	}
	public void setBlackList(String blackList) {
		this.blackList = blackList;
	}
	public String getWhiteList() {
		return whiteList;
	}
	public void setWhiteList(String whiteList) {
		this.whiteList = whiteList;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public String getHeaders() {
		return headers;
	}
	public void setHeaders(String headers) {
		this.headers = headers;
	}
	public String getFlowControl() {
		return flowControl;
	}
	public void setFlowControl(String flowControl) {
		this.flowControl = flowControl;
	}
	public String getFlowControlList() {
		return flowControlList;
	}
	public void setFlowControlList(String flowControlList) {
		this.flowControlList = flowControlList;
	}
	public int getConnectorWorkerThreadNum() {
		return connectorWorkerThreadNum;
	}
	public void setConnectorWorkerThreadNum(int connectorWorkerThreadNum) {
		this.connectorWorkerThreadNum = connectorWorkerThreadNum;
	}
	public int getReaderIdleTimeSeconds() {
		return readerIdleTimeSeconds;
	}
	public void setReaderIdleTimeSeconds(int readerIdleTimeSeconds) {
		this.readerIdleTimeSeconds = readerIdleTimeSeconds;
	}
	public int getWriterIdleTimeSeconds() {
		return writerIdleTimeSeconds;
	}
	public void setWriterIdleTimeSeconds(int writerIdleTimeSeconds) {
		this.writerIdleTimeSeconds = writerIdleTimeSeconds;
	}
	public int getAllIdleTimeSeconds() {
		return allIdleTimeSeconds;
	}
	public void setAllIdleTimeSeconds(int allIdleTimeSeconds) {
		this.allIdleTimeSeconds = allIdleTimeSeconds;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public boolean isSsl() {
		return ssl;
	}
	public void setSsl(boolean ssl) {
		this.ssl = ssl;
	}
	public int getBossGroupThread() {
		return bossGroupThread;
	}
	public void setBossGroupThread(int bossGroupThread) {
		this.bossGroupThread = bossGroupThread;
	}
	public int getWorkerGroupThread() {
		return workerGroupThread;
	}
	public void setWorkerGroupThread(int workerGroupThread) {
		this.workerGroupThread = workerGroupThread;
	}
	
}
