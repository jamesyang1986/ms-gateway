package cn.ms.gateway.core.disruptor;

import cn.ms.gateway.core.connector.ConnectorConf;
import cn.ms.gateway.core.connector.ZbusNettyConnector;
import cn.ms.gateway.core.disruptor.support.DisruptorFactory;
import cn.ms.gateway.core.entity.GatewayREQ;

public class GatewayDisruptorTest {

	public static void main(String[] args) {
		DisruptorConf conf=new DisruptorConf();
		conf.setExecutorThread(10);
		ConnectorConf connectorConf=new ConnectorConf();
		IDisruptor disruptor=new DisruptorFactory(conf, new ZbusNettyConnector(connectorConf));
		try {
			disruptor.init();//初始化
			disruptor.start();//启动
			
			for (int i = 0; i < 10; i++) {
				GatewayREQ req=new GatewayREQ();
				req.setContent("这是第"+(i+1)+"个请求");
				disruptor.publish(req);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			try {
				disruptor.destory();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
