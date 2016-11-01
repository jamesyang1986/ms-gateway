package cn.ms.gateway.base.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface FilterEnable {

	/**
	 * 过滤器开关
	 * 
	 * @return
	 */
	boolean value() default true;
	
}
