package cn.ms.gateway.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import cn.ms.gateway.base.filter.Filter;
import cn.ms.gateway.base.filter.FilterChain;
import cn.ms.gateway.base.filter.FilterType;
import cn.ms.gateway.base.filter.IFilter;

/**
 * 过滤器上下文
 * 
 * @author lry
 *
 * @param <REQ> 请求对象
 * @param <RES> 响应对象
 */
public class FilterContext<REQ, RES> {

	private ConcurrentHashMap<FilterType, ConcurrentHashMap<Integer, IFilter<REQ, RES>>> filterDatas = new ConcurrentHashMap<FilterType, ConcurrentHashMap<Integer, IFilter<REQ, RES>>>();

	@SuppressWarnings("unchecked")
	public FilterContext() throws Exception {
		//$NON-NLS-初始化所有处理器$
		Map<FilterType, List<IFilter<REQ, RES>>> filterMap = new LinkedHashMap<FilterType, List<IFilter<REQ, RES>>>();
		@SuppressWarnings("rawtypes")
		ServiceLoader<IFilter> serviceloader = ServiceLoader.load(IFilter.class);
		for (IFilter<REQ, RES> filter : serviceloader) {
			Filter filterAnnotation = filter.getClass().getAnnotation(Filter.class);
			if (filterAnnotation != null) {
				//$NON-NLS-根据过滤器类型分组收集$
				FilterType filterType = filterAnnotation.value();
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
			List<IFilter<REQ, RES>> filters=filterMap.get(filterType);
			if (filters == null || filters.isEmpty() ) {
				continue;
			}
			addFilters(filters);
		}
	}

	/**
	 * 添加单个过滤器
	 * 
	 * @param filter
	 * @throws Exception
	 */
	public void addFilter(IFilter<REQ, RES> filter) throws Exception {
		if (filter == null) {
			throw new RuntimeException("'filter'不能为空");
		}
		if (!(filter instanceof IFilter)) {
			throw new RuntimeException("'" + filter.getClass().getName()
					+ "'必须为'" + IFilter.class.getName() + "'的子类");
		}
		Filter filterAnnotation = filter.getClass().getAnnotation(Filter.class);
		if (filterAnnotation == null) {
			throw new RuntimeException("'" + filter.getClass().getName()
					+ "'必须包含注解'" + Filter.class.getName() + "'");
		}

		FilterType filterType = filterAnnotation.value();
		List<IFilter<REQ, RES>> filterList = new ArrayList<IFilter<REQ, RES>>();
		ConcurrentHashMap<Integer, IFilter<REQ, RES>> filters = filterDatas.get(filterType);
		if (!(filters == null || filters.isEmpty())) {
			filterList.addAll(filters.values());
		}
		filterList.add(filter);

		//$NON-NLS-分组进行组内排序过滤器$
		Collections.sort(filterList, new Comparator<IFilter<REQ, RES>>() {
			@Override
			public int compare(IFilter<REQ, RES> o1, IFilter<REQ, RES> o2) {
				return o1.getClass().getAnnotation(Filter.class).order()
						- o2.getClass().getAnnotation(Filter.class).order();
			}
		});

		//$NON-NLS-收集有序服务$
		ConcurrentHashMap<Integer, IFilter<REQ, RES>> map = new ConcurrentHashMap<Integer, IFilter<REQ, RES>>();
		for (int i = 0; i < filterList.size(); i++) {
			map.put(i, filterList.get(i));
		}
		filterDatas.put(filterType, map);
	}
	
	/**
	 * 添加一组过滤器
	 * 
	 * @param filters
	 * @throws Exception
	 */
	public void addFilters(List<IFilter<REQ, RES>> filters) throws Exception {
		if (filters == null || filters.isEmpty()) {
			throw new RuntimeException("filters不能为空");
		}

		for (IFilter<REQ, RES> filter : filters) {
			this.addFilter(filter);
		}
	}

	/**
	 * 获取指定的过滤器
	 * 
	 * @param filterClazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getFilter(Class<T> filterClazz) {
		Filter filter = filterClazz.getAnnotation(Filter.class);
		ConcurrentHashMap<Integer, IFilter<REQ, RES>> tempFilterMap = filterDatas.get(filter.value());
		for (Map.Entry<Integer, IFilter<REQ, RES>> entry : tempFilterMap.entrySet()) {
			if (filterClazz.getName().equals(entry.getValue().getClass().getName())) {
				return (T) entry.getValue();
			}
		}
		
		return null;
	}
	
	/**
	 * 执行过滤
	 * 
	 * @param req
	 * @param res
	 * @param args
	 * @throws Throwable
	 */
	public void filterChain(REQ req, RES res, Object... args)
			throws Throwable {
		try {
			if (!doFilterChain(FilterType.PRE, req, res, args)) {
				return;
			}
			doFilterChain(FilterType.ROUTE, req, res, args);
		} catch (Throwable t) {
			doFilterChain(FilterType.ERROR, req, res, t, args);
		} finally {
			doFilterChain(FilterType.POST, req, res, args);
		}
	}

	/**
	 * 执行过滤链
	 * 
	 * @param filterType
	 * @param req
	 * @param res
	 * @param args
	 * @return
	 * @throws Throwable
	 */
	private boolean doFilterChain(FilterType filterType, REQ req,
			RES res, Object... args) throws Throwable {
		ConcurrentHashMap<Integer, IFilter<REQ, RES>> filters = filterDatas.get(filterType);
		if (filters == null || filters.isEmpty()) {
			return true;
		}

		FilterChain<REQ, RES> filterChain = new FilterChain<REQ, RES>(filters);
		return filterChain.doFilterChain(new AtomicInteger(0), req, res, args);
	}

}
