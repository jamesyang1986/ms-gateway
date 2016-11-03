package cn.ms.gateway;

import cn.ms.gateway.base.container.IContainer;
import cn.ms.gateway.core.container.NettyConf;
import cn.ms.gateway.core.container.NettyContainer;
import cn.ms.gateway.core.entity.GatewayREQ;
import cn.ms.gateway.core.entity.GatewayRES;

/**
 * 微服务网关
 * 
 * @author lry
 */
public enum Bootstrap {

	INSTANCE;
	
	IContainer<GatewayREQ, GatewayRES> container = null;

	public void init() throws Exception {
		NettyConf nettyConf = new NettyConf();
		container = new NettyContainer(nettyConf);
		
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
