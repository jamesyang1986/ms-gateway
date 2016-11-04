package cn.ms.gateway.core.connector;

public class ConnectorConf {

	private int connectorBossThreadNum=0;
	private int connectorWorkerThreadNum=0;

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

}
