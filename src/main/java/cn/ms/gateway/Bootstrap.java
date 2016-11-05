package cn.ms.gateway;

import cn.ms.gateway.base.Gateway;
import cn.ms.gateway.base.IGateway;
import cn.ms.gateway.base.connector.IConnector;
import cn.ms.gateway.base.container.IContainer;
import cn.ms.gateway.base.interceptor.Interceptor;
import cn.ms.gateway.core.connector.ConnectorConf;
import cn.ms.gateway.core.connector.NettyConnector;
import cn.ms.gateway.core.container.NettyConf;
import cn.ms.gateway.core.container.NettyContainer;
import cn.ms.gateway.core.disruptor.DisruptorConf;
import cn.ms.gateway.core.disruptor.DisruptorFactory;
import cn.ms.gateway.core.disruptor.IDisruptor;
import cn.ms.gateway.core.entity.GatewayREQ;
import cn.ms.gateway.core.entity.GatewayRES;
import cn.ms.gateway.core.filter.route.HttpProxyRouteFilter;
import cn.ms.gateway.core.interceptor.GatewayInterceptor;

/**
 * 微服务网关
 * 
 * @author lry
 */
public enum Bootstrap {

	INSTANCE;

	/** 网关核心 **/
	IGateway<GatewayREQ, GatewayRES> gateway = null;
	/** 拦截器 **/
	Interceptor<GatewayREQ, GatewayRES> interceptor = null;
	/** 事件处理器 **/
	IDisruptor disruptor = null;
	/** 连接器 **/
	IConnector connector=null;
	/** 网关容器 **/
	IContainer<GatewayREQ, GatewayRES> container = null;
	
	Bootstrap() {
		interceptor = new GatewayInterceptor();
		gateway = new Gateway<GatewayREQ, GatewayRES>();
		connector=new NettyConnector(new ConnectorConf());
		disruptor = new DisruptorFactory(new DisruptorConf(), connector);
		container = new NettyContainer(gateway, new NettyConf(), interceptor);
	}

	public void init() throws Exception {
		gateway.init();
		connector.init();
		container.init();
		disruptor.init();
		
		//$NON-NLS-注入Disruptor$
		HttpProxyRouteFilter httpProxyRouteFilter = gateway.getFilter(HttpProxyRouteFilter.class);
		httpProxyRouteFilter.setDisruptor(disruptor);
	}

	public void start() throws Exception {
		gateway.start();
		connector.start();
		disruptor.start();
		container.start();
	}

	public void destory() throws Exception {
		gateway.destory();
		connector.destory();
		disruptor.destory();
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
