package cn.ms.gateway.base.filter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 过滤器链
 * 
 * @author lry
 *
 * @param <REQ>
 * @param <RES>
 */
public class FilterChain<REQ, RES> {

	private ConcurrentHashMap<Integer, IFilter<REQ, RES>> filters = new ConcurrentHashMap<Integer, IFilter<REQ, RES>>();

	public FilterChain(ConcurrentHashMap<Integer, IFilter<REQ, RES>> filters) {
		this.filters = filters;
	}

	/**
	 * 执行过滤器
	 * 
	 * @param no
	 * @param req
	 * @param res
	 * @param args
	 * @return
	 * @throws Throwable
	 */
	public boolean doFilterChain(AtomicInteger no, REQ req, RES res, Object... args) throws Throwable {
		if (no.get() == filters.size()) {
			return true;
		}

		IFilter<REQ, RES> filter = filters.get(no.getAndIncrement());
		if (filter.check(req, res, args)) {
			return filter.doChain(no, req, res, this, args);
		}

		return true;
	}
}
