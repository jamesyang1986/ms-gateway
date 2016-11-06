package cn.ms.gateway.base.filter;

/**
 * 微服务网关
 * 
 * @author lry
 *
 * @param <REQ>
 * @param <RES>
 */
public abstract class MSFilter<REQ, RES> implements IFilter<REQ, RES> {

	@Override
	public void init() throws Exception {

	}

	@Override
	public <MOD> void inject(MOD mod) {

	}

}
