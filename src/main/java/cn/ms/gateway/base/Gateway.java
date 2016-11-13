package cn.ms.gateway.base;

import cn.ms.gateway.base.connector.IConnector;
import cn.ms.gateway.base.container.IContainer;
import cn.ms.gateway.base.filter.FilterFactory;

/**
 * 微服务网关 <br>
 * <br>
 * 第一步：启动转发器<br>
 * 第二步：<br>
 * 第三步：启动网关容器<br>
 * 第四步：<br>
 * 
 * @author lry
 *
 * @param <REQ>
 * @param <RES>
 */
public class Gateway<REQ, RES> implements IGateway<REQ, RES> {

	//$NON-NLS-网关容器$
	protected FilterFactory<REQ, RES> filterFactory;
	protected IConnector<REQ, RES> connector;
	protected IContainer<REQ, RES> container;
	

	public Gateway() {
		// 创建过滤器
		filterFactory = new FilterFactory<REQ, RES>();
	}

	@Override
	public void inject(IConnector<REQ, RES> connector, IContainer<REQ, RES> container, Object... args)
			throws Exception {
		this.connector = connector;
		this.container = container;
	}

	@Override
	public void init() throws Exception {
		filterFactory.init();
		connector.init();
		container.init();
	}

	@Override
	public void start() throws Exception {
		filterFactory.start();
		connector.start();
		container.start();
	}

	@Override
	public void shutdown() throws Exception {
		container.shutdown();
		connector.shutdown();
		filterFactory.shutdown();
	}

}
