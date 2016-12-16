package cn.ms.gateway.base.processor.router;

/**
 * 分组路由结果类型
 * 
 * @author lry
 */
public enum ResultType {

	/** 路由成功 **/
	ROUTE_SELECT_OK("路由成功"),

	/** 参数'%s' == '%s'不能为空 **/
	NOT_NULL_PARAM("参数'%s' == '%s'不能为空"), 
	
	/** 没有初始化'%s' == '%s' **/
	NO_INIT_PARAM("没有初始化'%s' == '%s'"),
	/** 没有初始化'%s' == '%s' **/
	NO_AVA_RULE("没有可用的路由规则'%s' == '%s'"),
	/** 没有初始化'%s' == '%s' **/
	NO_SUBSCRIBE_PROVIDER("没有已订阅的服务'%s' == '%s'"), 
	
	/** 路由参数'%s'不能为空 **/
	REQPARAM_NOT_NULL("路由参数'%s'不能为空"), 
	/** 非法的路由请求规则'%s' **/
	ILLEGAL_REQ_ROUTERULE("非法的路由请求规则'%s'"), 
	/** 路由规则'%s'没有映射的服务清单 **/
	ROUERULE_NOAVA_SERVICES("路由规则'%s'没有映射的服务清单"), 
	/** serviceId:version:group的值不能为空 **/
	SVG_NOT_NULL("serviceId:version:group的值不能为空"), 
	/** 服务'%s'没有可用的提供者(provider-->%s) **/
	NO_AVA_PROVIDER("服务'%s'没有可用的提供者(provider-->%s)"), 
	/** 没有匹配到可用的路由规则 **/
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
