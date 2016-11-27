package cn.ms.gateway;

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
	public void refresh() throws Exception {

	}

	@Override
	public <CUS> void inject(CUS cus) throws Exception {

	}

	@Override
	public void doFilter(FilterChain<REQ, RES> chain, REQ req, RES res, Object... args) throws Throwable {
		chain.doFilter(req, res, args);
	}

}
