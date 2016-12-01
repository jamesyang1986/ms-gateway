package cn.ms.gateway.base.filter;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 过滤器注解
 * 
 * @author lry
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface Filter {

	/**
	 * 过滤器类型
	 * 
	 * @return
	 */
	FilterType value();
	
	/**
	 * 过滤器名称
	 * 
	 * @return
	 */
	String name() default "";

	/**
	 * 过滤器ID
	 * 
	 * @return
	 */
	String id() default "";

	/**
	 * 过滤权值
	 * 
	 * @return
	 */
	int order() default 0;

	/**
	 * 开关,默认为true
	 * 
	 * @return
	 */
	boolean enable() default true;

}
