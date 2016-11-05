package cn.ms.gateway.base;

import java.util.List;

import cn.ms.gateway.IAdapter;
import cn.ms.gateway.base.filter.FilterType;
import cn.ms.gateway.base.filter.IFilter;

/**
 * 微服务网关
 * 
 * @author lry
 *
 * @param <REQ>
 * @param <RES>
 */
public interface IGateway<REQ, RES> extends IAdapter {

	//$NON-NLS-添加自定义过滤器$
	void addFilter(IFilter<REQ, RES> filter);
	void addFilters(List<IFilter<REQ, RES>> filters);

	//$NON-NLS-获取指定过滤器$
	<T> T getFilter(Class<T> t);
	<T> T getFilter(FilterType filterType, String id);
	
	/**
	 * The Gateway handler center.
	 * 
	 * @param req
	 * @param args
	 * @return
	 * @throws Throwable
	 */
	RES handler(REQ req, Object... args) throws Throwable;

}
