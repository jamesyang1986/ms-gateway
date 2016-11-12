package cn.ms.gateway.base.filter;

import java.util.List;

import cn.ms.gateway.base.adapter.IAdapter;
import cn.ms.gateway.base.filter.annotation.FilterType;

public interface IFilterFactory<REQ, RES> extends IAdapter {

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
	 * 过滤器执行入口
	 * 
	 * @param req
	 * @param args
	 * @return
	 * @throws Exception
	 */
	RES doFilter(REQ req, Object... args) throws Exception;

}
