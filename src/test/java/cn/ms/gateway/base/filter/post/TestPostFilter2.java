package cn.ms.gateway.base.filter.post;

import cn.ms.gateway.base.IFilter;
import cn.ms.gateway.base.annotation.Filter;
import cn.ms.gateway.base.annotation.FilterEnable;
import cn.ms.gateway.base.type.FilterType;

@FilterEnable(value=false)
@Filter(value=FilterType.POST, order=20)
public class TestPostFilter2 implements IFilter<String, String> {

	@Override
	public String filterName() {
		return "TestPostFilter2";
	}

	@Override
	public boolean check(String req, String res, Object...args) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String run(String req, String res, Object...args) {
		// TODO Auto-generated method stub
		return null;
	}

}
