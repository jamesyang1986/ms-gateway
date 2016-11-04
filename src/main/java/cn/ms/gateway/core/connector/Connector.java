package cn.ms.gateway.core.connector;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.zbus.net.IoDriver;
import org.zbus.net.http.MessageClient;

import cn.ms.gateway.base.connector.IConnector;
import cn.ms.gateway.common.thread.NamedThreadFactory;

public class Connector implements IConnector {

	private IoDriver driver = null;
	private ConnectorConf conf = null;
	private EventLoopGroup connectorBossGroup = null;
	private EventLoopGroup connectorWorkerGroup = null;
	private ConcurrentHashMap<String, MessageClient> messageClientMap = new ConcurrentHashMap<String, MessageClient>();

	public Connector(ConnectorConf conf) {
		this.conf = conf;
	}

	@Override
	public void init() throws Exception {
		connectorBossGroup = new NioEventLoopGroup(conf.getConnectorBossThreadNum(), 
				new NamedThreadFactory("Connector-bossgroup-pool-thread"));
		connectorWorkerGroup = new NioEventLoopGroup(conf.getConnectorWorkerThreadNum(), 
				new NamedThreadFactory("Connector-workergroup-pool-thread"));
	}

	@Override
	public void start() throws Exception {
		driver = new IoDriver(connectorBossGroup, connectorWorkerGroup);
	}

	@Override
	public MessageClient connect(String address)
			throws Throwable {
		MessageClient messageClient = messageClientMap.get(address);
		if (messageClient == null) {
			messageClient = new MessageClient(address, driver);
			messageClientMap.put(address, messageClient);
		}

		return messageClient;
	}

	@Override
	public void destory() throws Exception {
		if (!messageClientMap.isEmpty()) {
			for (Map.Entry<String, MessageClient> entry : messageClientMap
					.entrySet()) {
				entry.getValue().close();
			}
		}

		if (driver != null) {
			driver.close();
		}
	}

}
