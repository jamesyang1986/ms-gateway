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
	/** 添加单个过滤器 **/
	void addFilter(IFilter<REQ, RES> filter) throws Exception;

	/** 添加一组过滤器 **/
	void addFilters(List<IFilter<REQ, RES>> filters) throws Exception;

	//$NON-NLS-获取指定过滤器$
	/** 获取指定的过滤器 **/
	<T> T getFilter(Class<T> t) throws Exception;

	/** 获取指定的过滤器 **/
	<T> T getFilter(FilterType filterType, String id) throws Exception;

	/**
	 * The Gateway handler center.
	 * 
	 * @param req
	 * @param args
	 * @return
	 * @throws Exception
	 */
	RES handler(REQ req, Object... args) throws Exception;

}
