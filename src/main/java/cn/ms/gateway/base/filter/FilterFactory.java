package cn.ms.gateway.base.filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import cn.ms.gateway.base.Gateway;
import cn.ms.gateway.base.filter.annotation.Filter;
import cn.ms.gateway.base.filter.annotation.FilterType;
import cn.ms.gateway.base.filter.support.AbstractFilterFactory;
import cn.ms.gateway.common.log.Logger;
import cn.ms.gateway.common.log.LoggerFactory;

/**
 * 微服务网关过滤器
 * 
 * @author lry
 *
 * @param <REQ>
 * @param <RES>
 */
public class FilterFactory<REQ, RES> extends AbstractFilterFactory<REQ, RES> {

	private static final Logger logger=LoggerFactory.getLogger(Gateway.class);

	@SuppressWarnings("unchecked")
	public FilterFactory() {
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
						return o1.getClass().getAnnotation(Filter.class).order()
								- o2.getClass().getAnnotation(Filter.class).order();
					}
				});

				//$NON-NLS-收集有序服务$
				super.addFilters(filterList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void init() throws Exception {

	}

	@Override
	public void start() throws Exception {

	}

	@Override
	public RES doFilter(REQ req, Object... args) throws Exception {
		RES res = null;
		try {
			try {
				//$NON-NLS-PRE过滤器过滤$
				res = doHandler(FilterType.PRE, req, res, args);
				if(res!=null){
					return res;
				}
			} catch (Throwable t) {
				logger.error("The execute "+FilterType.PRE.getCode()+" filter is error: " + t.getMessage(), t);
				
				res = doHandler(FilterType.ERROR, req, res, args);
				return doHandler(FilterType.POST, req, res, args);
			}

			try {
				//$NON-NLS-ROUTE过滤器过滤$
				res = doHandler(FilterType.ROUTE, req, res, args);
				if(res!=null){
					return res;
				}
			} catch (Throwable t) {
				logger.error("The execute "+FilterType.ROUTE.getCode()+" filter is error: " + t.getMessage(), t);
				
				res = doHandler(FilterType.ERROR, req, res, args);
				return doHandler(FilterType.POST, req, res, args);
			}
		} catch (Throwable t) {
			logger.error("The execute "+FilterType.PRE.getCode()+" and "+FilterType.PRE.getCode()+" filter is error: " + t.getMessage(), t);
			
			//$NON-NLS-ERROR过滤器过滤$
			return doHandler(FilterType.ERROR, req, res, args);
		} finally {
			try {
				//$NON-NLS-PRE过滤器过滤$
				doHandler(FilterType.POST, req, res, args);
			} catch (Throwable t) {
				logger.error("The execute "+FilterType.POST.getCode()+" filter is error: " + t.getMessage(), t);
				
				res = doHandler(FilterType.ERROR, req, res, args);
			}
		}
		
		return res;
	}

	/**
	 * 执行指定类型的过滤器
	 * 
	 * @param filterType
	 * @param req
	 * @param res
	 * @param args
	 * @return
	 * @throws Throwable
	 */
	private RES doHandler(FilterType filterType, REQ req, RES res, Object... args)  throws Exception {
		Map<String, IFilter<REQ, RES>> filterPREMap = serviceFilterOnLineMap.get(filterType.getCode());
		if(filterPREMap==null){
			return null;
		}
		
		for (Map.Entry<String, IFilter<REQ, RES>> entryFilterPRE : filterPREMap.entrySet()) {
			IFilter<REQ, RES> filterPRE = entryFilterPRE.getValue();
			boolean checkResult = filterPRE.check(req, res, args);
			if (checkResult) {
				RES resTemp = filterPRE.run(req, res, args);
				if (resTemp != null) {
					res = resTemp;
					break;
				}
			}
		}

		return res;
	}
	
	@Override
	public void shutdown() throws Exception {

	}

}
