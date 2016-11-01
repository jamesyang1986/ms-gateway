package cn.ms.gateway.base;

/**
 * 适配器
 * 
 * @author lry
 */
public interface Adaptor {

	/**
	 * The initialization.
	 * 
	 * @throws Exception
	 */
	void init() throws Exception;
	
	/**
	 * The destruction.
	 * 
	 * @throws Exception
	 */
	void destory() throws Exception;

}
