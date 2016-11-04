package cn.ms.gateway;

import cn.ms.gateway.base.Gateway;
import cn.ms.gateway.base.IGateway;
import cn.ms.gateway.base.container.IContainer;
import cn.ms.gateway.base.interceptor.Interceptor;
import cn.ms.gateway.core.connector.ConnectorConf;
import cn.ms.gateway.core.container.NettyConf;
import cn.ms.gateway.core.container.NettyContainer;
import cn.ms.gateway.core.disruptor.DisruptorConf;
import cn.ms.gateway.core.disruptor.IDisruptor;
import cn.ms.gateway.core.disruptor.support.DisruptorFactory;
import cn.ms.gateway.core.entity.GatewayREQ;
import cn.ms.gateway.core.entity.GatewayRES;
import cn.ms.gateway.core.interceptor.GatewayInterceptor;

/**
 * 微服务网关
 * 
 * @author lry
 */
public enum Bootstrap {

	INSTANCE;
	
	/**网关容器**/
	IContainer<GatewayREQ, GatewayRES> container = null;
	/**网关拦截器**/
	Interceptor<GatewayREQ, GatewayRES> interceptor = null;
	/**网关核心**/
	IGateway<GatewayREQ, GatewayRES> gateway = null;
	IDisruptor disruptor = null;
	
	Bootstrap() {
		interceptor = new GatewayInterceptor();
		gateway = new Gateway<GatewayREQ, GatewayRES>();
		
		DisruptorConf conf=new DisruptorConf();
		conf.setExecutorThread(10);
		ConnectorConf connectorConf=new ConnectorConf();
		disruptor = new DisruptorFactory(conf, connectorConf);
		
		NettyConf nettyConf = new NettyConf();
		container = new NettyContainer(gateway, nettyConf, interceptor);
	}
	
	public void init() throws Exception {
		container.init();
	}
	
	public void start() throws Exception {
		container.start();
	}

	public void destory() throws Exception {
		container.destory();
	}

	public static void main(String[] args) {
		try {
			// 第一步初始化
			INSTANCE.init();
			// 第二步启动容器
			INSTANCE.start();
		} catch (Exception e) {
			e.printStackTrace();

			try {
				// 异常则销毁
				INSTANCE.destory();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

}
