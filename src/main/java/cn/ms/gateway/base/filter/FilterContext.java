package cn.ms.gateway.base.filter;

/**
 * 过滤器上下文
 * 
 * @author lry
 *
 * @param <REQ>
 * @param <RES>
 */
public abstract class FilterContext<REQ, RES> implements IFilter<REQ, RES> {

	@Override
	public void init() throws Exception {
	}

	@Override
	public void refresh() throws Exception {
	}

	@Override
	public <IN> void inject(IN in) throws Exception {
	}

	@Override
	public void doFilter(FilterChain<REQ, RES> chain, REQ req, RES res, Object... args) throws Throwable {
		chain.doFilter(req, res, args);
	}

	@Override
	public void destroy() throws Exception {
	}

}
