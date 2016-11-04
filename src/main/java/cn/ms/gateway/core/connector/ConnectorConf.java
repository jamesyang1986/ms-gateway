package cn.ms.gateway.core.connector;

public class ConnectorConf {

	private int connectorBossThreadNum=0;
	private int connectorWorkerThreadNum=0;
	private int readerIdleTimeSeconds = 30;//读操作空闲30秒
	private int writerIdleTimeSeconds = 60;//写操作空闲60秒
	private int allIdleTimeSeconds = 100;//读写全部空闲100秒
	
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

}
