package cn.ms.gateway;

import java.util.ArrayList;
import java.util.List;

public class FilterChainContext<REQ, RES> {

	private List<IFilter<REQ, RES>> filters = new ArrayList<IFilter<REQ, RES>>();
	
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
