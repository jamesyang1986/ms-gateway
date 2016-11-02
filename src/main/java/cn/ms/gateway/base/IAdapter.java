package cn.ms.gateway.base;

/**
 * 适配器
 * 
 * @author lry
 */
public interface IAdapter {

	/**
	 * The initialization.
	 * 
	 * @throws Exception
	 */
	void init() throws Exception;
	
	/**
	 * The Start of Gateway.
	 * 
	 * @throws Exception
	 */
	void start() throws Exception;
	
	/**
	 * The destruction.
	 * 
	 * @throws Exception
	 */
	void destory() throws Exception;

}
