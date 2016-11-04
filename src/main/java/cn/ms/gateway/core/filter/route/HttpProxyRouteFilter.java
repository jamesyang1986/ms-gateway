package cn.ms.gateway.core.filter.route;

import cn.ms.gateway.base.annotation.Filter;
import cn.ms.gateway.base.filter.IFilter;
import cn.ms.gateway.base.type.FilterType;
import cn.ms.gateway.core.connector.ConnectorConf;
import cn.ms.gateway.core.disruptor.DisruptorConf;
import cn.ms.gateway.core.disruptor.IDisruptor;
import cn.ms.gateway.core.disruptor.support.DisruptorFactory;
import cn.ms.gateway.core.entity.GatewayREQ;
import cn.ms.gateway.core.entity.GatewayRES;

@Filter(value = FilterType.ROUTE, order = 300)
public class HttpProxyRouteFilter implements IFilter<GatewayREQ, GatewayRES> {

	DisruptorConf conf = new DisruptorConf();
	ConnectorConf connectorConf = null;
	IDisruptor disruptor = null;

	public HttpProxyRouteFilter() {
		conf.setExecutorThread(10);
		connectorConf = new ConnectorConf();
		disruptor = new DisruptorFactory(conf, connectorConf);
		
		try {
			disruptor.init();// 初始化
			disruptor.start();// 启动
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String filterName() {
		return null;
	}

	@Override
	public boolean check(GatewayREQ req, GatewayRES res, Object... args) {
		return true;
	}

	@Override
	public GatewayRES run(GatewayREQ req, GatewayRES res, Object... args) {
		try {
			disruptor.publish(req);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
