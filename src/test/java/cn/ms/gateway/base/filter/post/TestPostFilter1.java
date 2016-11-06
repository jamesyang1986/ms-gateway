package cn.ms.gateway.base.filter.post;

import cn.ms.gateway.base.filter.FilterType;
import cn.ms.gateway.base.filter.IFilter;
import cn.ms.gateway.common.annotation.Filter;
import cn.ms.gateway.common.annotation.FilterEnable;

@FilterEnable
@Filter(value=FilterType.POST, order=10)
public class TestPostFilter1 implements IFilter<String, String> {

	@Override
	public void init() throws Exception {
		
	}
	
	@Override
	public boolean check(String req, String res, Object...args) {
		System.out.println(this.getClass().getName()+" ---> check");
		return true;
	}

	@Override
	public String run(String req, String res, Object...args) {
		System.out.println(this.getClass().getName()+" ---> run");
		return null;
	}

}
