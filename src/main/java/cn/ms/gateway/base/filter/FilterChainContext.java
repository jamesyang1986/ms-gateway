package cn.ms.gateway.base.filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.ms.gateway.base.filter.annotation.Filter;
import cn.ms.gateway.base.filter.annotation.FilterType;

/**
 * 过滤链上下文
 * 
 * @author lry
 *
 * @param <REQ>
 * @param <RES>
 */
public class FilterChainContext<REQ, RES> {

	private static final Logger logger=LogManager.getLogger(FilterChainContext.class);
	
	private List<IFilter<REQ, RES>> filters = new ArrayList<IFilter<REQ, RES>>();
	
	@SuppressWarnings("unchecked")
	public FilterChainContext() {
		try {
			//$NON-NLS-初始化所有处理器$
			Map<String, List<IFilter<REQ, RES>>> filterMap = new LinkedHashMap<String, List<IFilter<REQ, RES>>>();
			
			@SuppressWarnings("rawtypes")
			ServiceLoader<IFilter> serviceloader = ServiceLoader.load(IFilter.class);
			for (IFilter<REQ, RES> filter : serviceloader) {
				Filter filterAnnotation = filter.getClass().getAnnotation(Filter.class);
				if (filterAnnotation != null) {
					//$NON-NLS-根据过滤器类型分组收集$
					String filterType = filterAnnotation.value().getCode();
					List<IFilter<REQ, RES>> filterList = filterMap.get(filterType);
					if (filterList == null) {
						filterList = new ArrayList<IFilter<REQ, RES>>();
					}
					
					filterList.add(filter);
					filterMap.put(filterType, filterList);
				}
			}

			//$NON-NLS-排序$
			for (FilterType filterType : FilterType.values()) {
				List<IFilter<REQ, RES>> filterList = filterMap.get(filterType.getCode());
				if (filterList == null || filterList.isEmpty()) {
					continue;
				}

				// 分组进行组内排序过滤器
				Collections.sort(filterList, new Comparator<IFilter<REQ, RES>>() {
					@Override
					public int compare(IFilter<REQ, RES> o1, IFilter<REQ, RES> o2) {
						return o1.getClass().getAnnotation(Filter.class).order() - o2.getClass().getAnnotation(Filter.class).order();
					}
				});

				//$NON-NLS-收集有序服务$
				filters.addAll(filterList);
			}
		} catch (Exception e) {
			logger.error("网关过滤器扫描异常,异常信息为: "+e.getMessage(), e);
		}
	}
	
	public FilterChainContext<REQ, RES> addFilter(IFilter<REQ, RES> filter) {
		filters.add(filter);
		return this;
	}
	
	public FilterChainContext<REQ, RES> addFilters(List<IFilter<REQ, RES>> filters) {
		filters.addAll(filters);
		return this;
	}
	
	public void doFilter(REQ req, RES res, Object... args) throws Throwable {
		new FilterChain<REQ, RES>(filters).doFilter(req, res, args);
	}
	
}
