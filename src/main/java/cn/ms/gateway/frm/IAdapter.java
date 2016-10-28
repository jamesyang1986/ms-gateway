package cn.ms.gateway.frm;

/**
 * 适配器
 * 
 * @author lry
 */
public interface IAdapter {

	/**
	 * 初始化
	 */
	void init() throws Exception;
	
	/**
	 * 启动
	 */
	void start() throws Exception;
	
	/**
	 * 销毁
	 */
	void destroy() throws Exception;
	
}
