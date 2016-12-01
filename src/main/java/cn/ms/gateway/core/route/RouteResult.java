package cn.ms.gateway.core.route;

import java.util.List;

import com.weibo.api.motan.rpc.URL;

/**
 * 分组路由结果<br>
 * <br>
 * 包括结果类型和结果服务提供者清单
 * 
 * @author lry
 */
public class RouteResult {

	// 返回结果类型
	private ResultType resultType;
	// 服务提供者清单
	private List<URL> urls;

	RouteResult(ResultType resultType, List<URL> urls) {
		this.resultType = resultType;
		this.urls = urls;
	}

	public ResultType getResultType() {
		return resultType;
	}

	public void setResultType(ResultType resultType) {
		this.resultType = resultType;
	}

	public List<URL> getUrls() {
		return urls;
	}

	public void setUrls(List<URL> urls) {
		this.urls = urls;
	}

	//$NON-NLS-建造器$
	public static RouteResult build(ResultType resultType) {
		return build(resultType, null);
	}

	public static RouteResult build(ResultType resultType, List<URL> urls) {
		return new RouteResult(resultType, urls);
	}

	@Override
	public String toString() {
		return "RouteResult [resultType=[resultType= "+resultType+", msg=" + resultType.getMsg() + "], urls=" + urls + "]";
	}

}
