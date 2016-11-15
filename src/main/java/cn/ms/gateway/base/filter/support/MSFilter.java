package cn.ms.gateway.base.filter.support;

import cn.ms.gateway.base.filter.IFilter;

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
	public void refresh() throws Exception {
		
	}

	@Override
	public <MOD> void mod(MOD mod) throws Exception {

	}

}
