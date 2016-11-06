package cn.ms.gateway.core.container;

public class ContainerConf {

	/**
	 * HTTP暴露端口
	 */
	int port=9500;
	int bossGroupThread=0;
	int workerGroupThread=0;
	
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
	
}
