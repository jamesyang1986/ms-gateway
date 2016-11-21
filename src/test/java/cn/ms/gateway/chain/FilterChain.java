package cn.ms.gateway.chain;

import java.util.ArrayList;
import java.util.List;

/**
 * 过滤链
 * 
 * @author lry
 *
 * @param <REQ>
 * @param <RES>
 */
public class FilterChain<REQ, RES> {

	private List<IFilter<REQ, RES>> filters = new ArrayList<IFilter<REQ, RES>>();
	// 调用链上的过滤器时，记录过滤器的位置用
	private int index = 0;

	public FilterChain<REQ, RES> addFilter(FilterContext<REQ, RES> filter) {
		filters.add(filter);
		return this;
	}

	public void doFilter(REQ req, RES res, Object... args) throws Throwable {
		if (index == filters.size()) {
			return;
		}
		// 得到当前过滤器
		IFilter<REQ, RES> filter = filters.get(index++);
		boolean ischeck = filter.check(req, res, args);
		if (ischeck) {
			try {
				filter.before(req, res, args);
				filter.doFilter(this, req, res, args);
			} finally {
				filter.after(req, res, args);
			}
		}
	}
}