package cn.ms.gateway.base.filter;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 过滤器
 * 
 * @author lry
 *
 * @param <REQ> 请求对象
 * @param <RES> 响应对象
 */
public abstract class IFilter<REQ, RES> {

	/**
	 * 刷新
	 * 
	 * @param ref
	 * @throws Exception
	 */
	public void ref(Object ref) throws Exception {
		
	}

	/**
	 * 校验是否执行过滤器
	 * 
	 * @param req
	 * @param res
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public boolean check(REQ req, RES res, Object... args) throws Exception {
		return true;
	}

	/**
	 * 过滤器执行器
	 * 
	 * @param no
	 * @param req
	 * @param res
	 * @param filterChain
	 * @param args
	 * @return
	 * @throws Throwable
	 */
	public boolean doChain(AtomicInteger no, REQ req, RES res, FilterChain<REQ, RES> filterChain, Object... args) throws Throwable {
		boolean tempFlag = true;
		try {
			tempFlag = doBefore(req, res, args);
			if (tempFlag) {
				return filterChain.doFilterChain(no, req, res, filterChain, args);
			}
		} finally {
			doAfter(req, res, args);
		}

		return tempFlag;
	}

	/**
	 * 请求时执行
	 * 
	 * @param req
	 * @param res
	 * @param args
	 * @return
	 * @throws Throwable
	 */
	public abstract boolean doBefore(REQ req, RES res, Object... args) throws Throwable;

	/**
	 * 响应执行
	 * 
	 * @param req
	 * @param res
	 * @param args
	 * @throws Throwable
	 */
	public void doAfter(REQ req, RES res, Object... args) throws Throwable {
		
	}

}
