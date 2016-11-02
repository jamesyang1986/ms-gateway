package cn.ms.gateway.base.support;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.ms.gateway.base.IGateway;
import cn.ms.gateway.base.annotation.Filter;
import cn.ms.gateway.base.annotation.FilterEnable;
import cn.ms.gateway.base.filter.IFilter;

/**
 * 微服务网关核心抽象类
 * 
 * @author lry
 *
 * @param <REQ>
 * @param <RES>
 */
public abstract class AbstractGateway<REQ, RES> implements IGateway<REQ, RES> {

	/** 在线过滤器 **/
	protected Map<String, Map<String, IFilter<REQ, RES>>> serviceFilterOnLineMap = new LinkedHashMap<String, Map<String, IFilter<REQ, RES>>>();
	/** 离线过滤器 **/
	protected Map<String, Map<String, IFilter<REQ, RES>>> serviceFilterOffLineMap = new LinkedHashMap<String, Map<String, IFilter<REQ, RES>>>();

	@Override
	public void addFilter(IFilter<REQ, RES> filter) {
		String filterName = filter.filterName();
		if (filterName == null || filterName.length() < 1) {
			filterName = filter.getClass().getName();
		}

		FilterEnable filterEnable = filter.getClass().getAnnotation(FilterEnable.class);
		if (filterEnable == null || filterEnable.value()) {// 在线过滤器
			// 过滤器注解
			Filter filterAnnotation = filter.getClass().getAnnotation(Filter.class);
			if (filterAnnotation != null) {
				String code = filterAnnotation.value().getCode();
				Map<String, IFilter<REQ, RES>> filterMap = serviceFilterOnLineMap.get(code);
				if (filterMap == null) {
					filterMap = new LinkedHashMap<String, IFilter<REQ, RES>>();
				}
				filterMap.put(filterName, filter);

				serviceFilterOnLineMap.put(code, filterMap);
			}
		} else {// 离线过滤器
				// 过滤器注解
			Filter filterAnnotation = filter.getClass().getAnnotation(Filter.class);
			if (filterAnnotation != null) {
				String code = filterAnnotation.value().getCode();
				Map<String, IFilter<REQ, RES>> filterMap = serviceFilterOffLineMap.get(code);
				if (filterMap == null) {
					filterMap = new LinkedHashMap<String, IFilter<REQ, RES>>();
				}
				filterMap.put(filterName, filter);

				serviceFilterOffLineMap.put(code, filterMap);
			}
		}
	}

	@Override
	public void addFilters(List<IFilter<REQ, RES>> filters) {
		for (IFilter<REQ, RES> filter : filters) {
			this.addFilter(filter);
		}
	}

}
