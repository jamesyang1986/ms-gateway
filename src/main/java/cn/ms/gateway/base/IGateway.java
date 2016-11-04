package cn.ms.gateway.base;

import java.util.List;

import cn.ms.gateway.base.filter.IFilter;
import cn.ms.gateway.base.type.FilterType;

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
	 * @param res
	 * @param args
	 * @return
	 * @throws Throwable
	 */
	RES handler(REQ req, RES res ,Object... args) throws Throwable;

}
