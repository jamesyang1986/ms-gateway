package cn.ms.gateway.core.connector;

import io.netty.handler.codec.http.FullHttpResponse;
import cn.ms.gateway.Perf;
import cn.ms.gateway.base.ICallback;
import cn.ms.gateway.common.Conf;
import cn.ms.gateway.common.utils.NetUtils;
import cn.ms.gateway.entity.GatewayREQ;
import cn.ms.gateway.entity.GatewayRES;

public class NettyConnectorPerfTest {
	
	public static void main(String[] args) throws Exception{
		Conf.CONF.setConnectorWorkerThreadNum(32);
		final NettyConnector nettyConnector=new NettyConnector();
		nettyConnector.init();
		nettyConnector.start();
		
		final GatewayREQ req=new GatewayREQ();
		req.setContent("I am OK");
		req.setClientHost(NetUtils.getLocalIp());
		
		Perf perf = new Perf(){ 
			public TaskInThread buildTaskInThread() {
				return new TaskInThread(){
					public void initTask() throws Exception {
					}
					
					public void doTask() throws Exception {
						try {
							nettyConnector.connect(req, new ICallback<GatewayRES, GatewayRES, FullHttpResponse>() {
								@Override
								public void before(FullHttpResponse bef, Object... args) throws Exception {
									
								}
								@Override
								public GatewayRES callback(GatewayRES res, Object... args) throws Exception {
									if(!res.getContent().contains("I am OK")){
										throw new RuntimeException();
									}
									return null;
								}
							});	
						} catch (Throwable t) {
							t.printStackTrace();
						}
					}
				};
			} 
		}; 
		perf.loopCount = 2000000;
		perf.threadCount = 16;
		perf.logInterval = 100000;
		perf.run();
		perf.close();
	} 

}
