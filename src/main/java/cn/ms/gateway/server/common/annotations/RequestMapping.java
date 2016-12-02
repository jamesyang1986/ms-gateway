package cn.ms.gateway.server.common.annotations;

import cn.ms.gateway.server.common.HttpConstants;
import cn.ms.gateway.server.common.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
    String value();
    RequestMethod method() default RequestMethod.GET;
    String consumes() default HttpConstants.HEADER_CONTENT_TYPE_JSON;
    String produces() default HttpConstants.HEADER_CONTENT_TYPE_TEXT;
}