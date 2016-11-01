package cn.ms.gateway.base.support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import cn.ms.gateway.base.IFilter;
import cn.ms.gateway.base.IGateway;
import cn.ms.gateway.base.annotation.Filter;
import cn.ms.gateway.base.annotation.FilterEnable;

/**
 * 微服务网关核心
 * 
 * @author lry
 *
 * @param <REQ>
 * @param <RES>
 */
public class Gateway<REQ, RES> implements IGateway<REQ, RES> {

	/**离线过滤器**/
	private Map<String, IFilter<REQ, RES>> serviceFilterOffLineMap = new LinkedHashMap<String, IFilter<REQ, RES>>();
	/**在线过滤器**/
	private Map<String, IFilter<REQ, RES>> serviceFilterOnLineMap = new LinkedHashMap<String, IFilter<REQ, RES>>();

	public void addProcessor() {
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void init() throws Exception {
		//$NON-NLS-初始化所有处理器$
		List<IFilter<REQ, RES>> filterList = new ArrayList<IFilter<REQ, RES>>();
		@SuppressWarnings("rawtypes")
		ServiceLoader<IFilter> serviceloader = ServiceLoader.load(IFilter.class);
		for (IFilter<REQ, RES> filter : serviceloader) {
			if (filter.getClass().isAnnotationPresent(Filter.class)) {
				filterList.add(filter);
			}
		}

		//$NON-NLS-排序$
		Collections.sort(filterList, new Comparator<IFilter<REQ, RES>>() {
			@Override
			public int compare(IFilter<REQ, RES> o1, IFilter<REQ, RES> o2) {
				return o1.getClass().getAnnotation(Filter.class).order() - o2.getClass().getAnnotation(Filter.class).order();
			}
		});

		//$NON-NLS-收集有序服务$
		for (IFilter<REQ, RES> filter : filterList) {
			String filterName = filter.filterName();
			if (filterName == null || filterName.length() < 1) {
				filterName = filter.getClass().getName();
			}
			
			FilterEnable filterEnable = filter.getClass().getAnnotation(FilterEnable.class);
			if(filterEnable==null || (!filterEnable.value())){
				serviceFilterOffLineMap.put(filterName, filter);//离线过滤器				
			}else{
				serviceFilterOnLineMap.put(filterName, filter);//在线过滤器
			}
		}

		System.out.println(serviceFilterOnLineMap);
		System.out.println(serviceFilterOffLineMap);
	}

	@Override
	public void start() throws Exception {

	}

	@Override
	public RES handler(REQ req, Object... args) throws Throwable {
		return null;
	}

	@Override
	public void destory() throws Exception {

	}

}
