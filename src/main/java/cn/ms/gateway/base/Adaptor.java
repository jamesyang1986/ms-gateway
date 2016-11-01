package cn.ms.gateway.base;

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
