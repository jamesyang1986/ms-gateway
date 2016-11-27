package cn.ms.gateway.base;

/**
 * 适配器
 * 
 * @author lry
 */
public interface IAdapter {

	/**
	 * The initialization of gateway.
	 * 
	 * @throws Exception
	 */
	void init() throws Exception;

	/**
	 * The start of gateway.
	 * 
	 * @throws Exception
	 */
	void startup() throws Exception;

	/**
	 * The shutdown of gateway.
	 * 
	 * @throws Exception
	 */
	void shutdown() throws Exception;

}
