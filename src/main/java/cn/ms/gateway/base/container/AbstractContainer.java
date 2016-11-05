package cn.ms.gateway.base.container;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ServiceLoader;

import cn.ms.gateway.base.IGateway;
import cn.ms.gateway.base.interceptor.IInterceptor;
import cn.ms.gateway.common.annotation.Interceptor;

/**
 * 容器抽象模块
 * 
 * @author lry
 */
public abstract class AbstractContainer<REQ, RES> implements IContainer<REQ, RES> {

	protected IGateway<REQ, RES> gateway = null;
	private Map<String, IInterceptor<REQ, RES>> interceptorMap = new LinkedHashMap<String, IInterceptor<REQ, RES>>();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public AbstractContainer(IGateway<REQ, RES> gateway) {
		this.gateway = gateway;

		ServiceLoader<IInterceptor> serviceloader = ServiceLoader.load(IInterceptor.class);
		for (IInterceptor<REQ, RES> interceptor : serviceloader) {
			Interceptor interceptorAnnotation = interceptor.getClass().getAnnotation(Interceptor.class);
			if (interceptorAnnotation != null) {
				interceptorMap.put(interceptor.getClass().getName(), interceptor);
			}
		}
	}

	public RES sendGatewayHandler(REQ req, Object... args) throws Throwable {
		return gateway.handler(req, args);
	}

	public void beforeInterceptor(REQ req, Object... args) throws Throwable {
		for (Map.Entry<String, IInterceptor<REQ, RES>> entry : interceptorMap.entrySet()) {
			IInterceptor<REQ, RES> interceptor = entry.getValue();
			interceptor.before(req, args);
		}
	}

	public void afterInterceptor(REQ req, RES res, Object... args)
			throws Throwable {
		for (Map.Entry<String, IInterceptor<REQ, RES>> entry : interceptorMap.entrySet()) {
			IInterceptor<REQ, RES> interceptor = entry.getValue();
			interceptor.after(req, res, args);
		}
	}

}
