package cn.ms.gateway.base.filter.post;

import cn.ms.gateway.base.filter.FilterType;
import cn.ms.gateway.base.filter.MSFilter;
import cn.ms.gateway.common.annotation.Filter;
import cn.ms.gateway.common.annotation.FilterEnable;

@FilterEnable(value=false)
@Filter(value=FilterType.POST, order=20)
public class TestPostFilter2 extends MSFilter<String, String> {

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
