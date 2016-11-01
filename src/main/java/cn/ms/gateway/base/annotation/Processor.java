package cn.ms.gateway.base.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * SPI Service
 * 
 * @author lry
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface Processor {

	/**
	 * 扩展点ID
	 * 
	 * @return
	 */
	String value() default "";

	/**
	 * 扩展点名称
	 * 
	 * @return
	 */
	String name() default "";

	/**
	 * 执行顺序,从小到大依次执行
	 * 
	 * @return
	 */
	int order() default 0;
}
