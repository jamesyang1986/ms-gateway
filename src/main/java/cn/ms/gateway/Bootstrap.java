package cn.ms.gateway;

import io.netty.handler.codec.http.HttpResponse;
import cn.ms.gateway.base.Gateway;
import cn.ms.gateway.base.IGateway;
import cn.ms.gateway.base.connector.IConnector;
import cn.ms.gateway.base.container.IContainer;
import cn.ms.gateway.base.processer.IProcesser;
import cn.ms.gateway.common.log.Logger;
import cn.ms.gateway.common.log.LoggerFactory;
import cn.ms.gateway.core.connector.ConnectorConf;
import cn.ms.gateway.core.connector.NettyConnector;
import cn.ms.gateway.core.container.ContainerConf;
import cn.ms.gateway.core.container.NettyContainer;
import cn.ms.gateway.core.filter.route.HttpProxyRouteFilter;
import cn.ms.gateway.core.processer.DisruptorEventConf;
import cn.ms.gateway.core.processer.DisruptorEventSupport;
import cn.ms.gateway.entity.GatewayREQ;
import cn.ms.gateway.entity.GatewayRES;

/**
 * 微服务网关
 * 
 * @author lry
 */
public enum Bootstrap {

	INSTANCE;
	
	private static final Logger logger=LoggerFactory.getLogger(Bootstrap.class);

	/** 网关核心 **/
	IGateway<GatewayREQ, GatewayRES> gateway = null;
	/** 事件处理器 **/
	IProcesser processer = null;
	/** 连接器 **/
	IConnector<GatewayRES, GatewayRES, HttpResponse> connector=null;
	/** 网关容器 **/
	IContainer<GatewayREQ, GatewayRES> container = null;
	
	Bootstrap() {
		gateway = new Gateway<GatewayREQ, GatewayRES>();
		connector=new NettyConnector(new ConnectorConf());
		processer = new DisruptorEventSupport(new DisruptorEventConf(), connector);
		container = new NettyContainer(gateway, new ContainerConf());
	}

	public void init() throws Exception {
		gateway.init();
		connector.init();
		container.init();
		processer.init();
		
		//$NON-NLS-注入Disruptor$
		HttpProxyRouteFilter httpProxyRouteFilter = gateway.getFilter(HttpProxyRouteFilter.class);
		httpProxyRouteFilter.setEvent(processer);
	}

	public void start() throws Exception {
		gateway.start();
		connector.start();
		processer.start();
		container.start();
	}

	public void destory() throws Exception {
		gateway.destory();
		connector.destory();
		processer.destory();
		container.destory();
	}

	public static void main(String[] args) {
		try {
			// 第一步初始化
			INSTANCE.init();
			// 第二步启动容器
			INSTANCE.start();
		} catch (Exception e) {
			logger.error(e, "启动微服务网关异常: %s", e.getMessage());
			
			try {
				// 异常则销毁
				INSTANCE.destory();
			} catch (Exception e1) {
				logger.error(e1, "关闭微服务网关异常: %s", e1.getMessage());
			}
		}
	}

}
