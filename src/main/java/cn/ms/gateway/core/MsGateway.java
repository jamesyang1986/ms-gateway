package cn.ms.gateway.core;

import cn.ms.gateway.base.Gateway;
import cn.ms.gateway.base.connector.IConnector;
import cn.ms.gateway.base.container.IContainer;
import cn.ms.gateway.core.connector.NettyConnector;
import cn.ms.gateway.core.container.NettyContainer;
import cn.ms.gateway.core.filter.route.ConnectorFilter;
import cn.ms.gateway.entity.GatewayREQ;
import cn.ms.gateway.entity.GatewayRES;

public class MsGateway extends Gateway<GatewayREQ, GatewayRES> {

	IConnector<GatewayREQ, GatewayRES> connector = null;
	IContainer<GatewayREQ, GatewayRES> container = null;

	public MsGateway() {
		try {
			//$NON-NLS-创建资源$
			connector = new NettyConnector();
			// 向指定过滤器注入连接器
			filterFactory.getFilter(ConnectorFilter.class).inject(connector);

			// 向网关容器中注入网关处理器
			container = new NettyContainer(filterFactory);
			// 向微服务网关中注入网关容器
			super.inject(connector, container);

			
			//$NON-NLS-初始化$
			super.init();

			
			//$NON-NLS-启动$
			super.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
