package cn.ms.gateway;

import cn.ms.gateway.base.Gateway;
import cn.ms.gateway.base.IGateway;
import cn.ms.gateway.base.container.IContainer;
import cn.ms.gateway.base.interceptor.Interceptor;
import cn.ms.gateway.core.container.NettyConf;
import cn.ms.gateway.core.container.NettyContainer;
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
	
	IContainer<GatewayREQ, GatewayRES> container = null;
	Interceptor<GatewayREQ, GatewayRES> interceptor = null;
	IGateway<GatewayREQ, GatewayRES> gateway = null;
	
	Bootstrap() {
		interceptor = new GatewayInterceptor();
		gateway = new Gateway<GatewayREQ, GatewayRES>();
		
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
