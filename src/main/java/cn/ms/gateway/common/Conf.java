package cn.ms.gateway.common;

import cn.ms.gateway.neural.blackwhitelist.BlackWhiteListType;

/**
 * 配置项
 * 
 * @author lry
 */
public enum Conf {

	CONF;
	
	//$NON-NLS-Neural配置参数$
	/**黑/白名单开关**/
	private String blackWhiteList=BlackWhiteListType.NON.toString();
	/**IP黑名单**/
	private String blackList;
	/**IP白名单**/
	private String whiteList;
	/**请求参数**/
	private String params="serviceId";
	/**请求头参数**/
	private String headers="channelId;bizno{length=32};sysno{length=32}";
	
	//$NON-NLS-连接器配置项$
	private int connectorBossThreadNum=0;
	private int connectorWorkerThreadNum=0;
	private int readerIdleTimeSeconds = 30;//读操作空闲30秒
	private int writerIdleTimeSeconds = 60;//写操作空闲60秒
	private int allIdleTimeSeconds = 100;//读写全部空闲100秒
	
	//$NON-NLS-网关容器配置项$
	private int port=9000;
	private int bossGroupThread=0;
	private int workerGroupThread=0;
	
	//$NON-NLS-处理器配置项$
	/**The size of the ring buffer, must be power of 2.**/
	private int ringBufferSize=1024;
	/**An Executor to execute event processors**/
	private int executorThread=10;
	/**The claim strategy to use for the ring buffer.**/
	private String producerType="SINGLE";
	/**The wait strategy to use for the ring buffer.**/
	private String waitStrategy="YIELDING_WAIT";
	
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
	public int getConnectorBossThreadNum() {
		return connectorBossThreadNum;
	}
	public void setConnectorBossThreadNum(int connectorBossThreadNum) {
		this.connectorBossThreadNum = connectorBossThreadNum;
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
	public int getRingBufferSize() {
		return ringBufferSize;
	}
	public void setRingBufferSize(int ringBufferSize) {
		this.ringBufferSize = ringBufferSize;
	}
	public int getExecutorThread() {
		return executorThread;
	}
	public void setExecutorThread(int executorThread) {
		this.executorThread = executorThread;
	}
	public String getProducerType() {
		return producerType;
	}
	public void setProducerType(String producerType) {
		this.producerType = producerType;
	}
	public String getWaitStrategy() {
		return waitStrategy;
	}
	public void setWaitStrategy(String waitStrategy) {
		this.waitStrategy = waitStrategy;
	}
	
}
