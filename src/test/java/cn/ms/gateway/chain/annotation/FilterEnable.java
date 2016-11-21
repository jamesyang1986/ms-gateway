package cn.ms.gateway.chain.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 过滤器开关
 * 
 * @author lry
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface FilterEnable {

	/**
	 * 过滤器开关,true表示开启,false表示关闭
	 * 
	 * @return
	 */
	boolean value() default true;
	
}
