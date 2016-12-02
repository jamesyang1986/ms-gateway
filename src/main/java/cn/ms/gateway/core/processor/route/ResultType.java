package cn.ms.gateway.core.processor.route;

/**
 * 分组路由结果类型
 * 
 * @author lry
 */
public enum ResultType {

	ROUTE_SELECT_OK("路由成功"),

	NOT_NULL_PARAM("参数'%s' == '%s'不能为空"), 
	NO_INIT_PARAM("没有初始化'%s' == '%s'"), 
	REQPARAM_NOT_NULL("请求参数'%s'不能为空"), 
	ILLEGAL_REQ_ROUTERULE("非法的路由请求规则'%s'"), 
	ROUERULE_NOAVA_SERVICES("路由规则'%s'没有映射的服务清单"), 
	SVS_NOT_NULL("serviceId:version:sceneId的值不能为空"), 
	NO_AVA_PROVIDER("服务'%s'没有可用的提供者(provider-->%s)"), 
	NOTFOUND_ROUTE_RULE("没有匹配到可用的路由规则");

	private String msg;

	ResultType(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	//$NON-NLS-渲染器$
	public ResultType wapper(Object... msg) {
		this.setMsg(String.format(this.getMsg(), msg));
		return this;
	}

}
