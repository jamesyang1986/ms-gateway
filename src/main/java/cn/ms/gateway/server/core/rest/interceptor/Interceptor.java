package cn.ms.gateway.server.core.rest.interceptor;

import io.netty.handler.codec.http.DefaultFullHttpResponse;
import cn.ms.gateway.server.core.NestyOptionProvider;
import cn.ms.gateway.server.core.rest.HttpContext;

/**
 * Filter of http request and response
 */
public abstract class Interceptor {

    public boolean install(NestyOptionProvider nesty) {
        return true;
    }

    /**
     * http context of request information. user can update
     * the value of context.
     *
     * @param context http context
     * @return true if we continue the request or false will deny the request by http code 403
     */
    public boolean filter(final HttpContext context) {
        return true;
    }

    /**
     * http context of request information. user can update
     * the value of context or change the response. don't accept null
     * returned. it will be ignored
     *
     * @param context  http context
     * @param response represent response
     * @return response new response instance or current Object instance
     */
    public DefaultFullHttpResponse handler(final HttpContext context, DefaultFullHttpResponse response) {
        return response;
    }
}
