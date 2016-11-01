package cn.ms.gateway.base.filter.pre;

import cn.ms.gateway.base.IFilter;
import cn.ms.gateway.base.annotation.Filter;
import cn.ms.gateway.base.annotation.FilterEnable;
import cn.ms.gateway.base.type.FilterType;

@FilterEnable
@Filter(value=FilterType.PRE, order=30)
public class TestPreFilter3 implements IFilter<String, String> {

	@Override
	public String filterName() {
		return "TestPreFilter3";
	}

	@Override
	public boolean check(String req, String res, Object...args) {
		System.out.println(filterName()+" ---> check");
		return true;
	}

	@Override
	public String run(String req, String res, Object...args) {
		System.out.println(filterName()+" ---> run");
		return req;
	}

}
