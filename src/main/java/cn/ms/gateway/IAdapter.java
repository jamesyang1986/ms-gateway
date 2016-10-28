package cn.ms.gateway;

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
	void start();
	
	/**
	 * 销毁
	 */
	void destroy() throws Exception;
	
}
