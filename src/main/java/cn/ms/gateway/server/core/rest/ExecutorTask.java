package cn.ms.gateway.server.core.rest;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.util.List;
import java.util.concurrent.Callable;

import cn.ms.gateway.server.common.utils.SerializeUtils;
import cn.ms.gateway.server.core.rest.controller.URLController;
import cn.ms.gateway.server.core.rest.entity.HttpResponseBuilder;
import cn.ms.gateway.server.core.rest.entity.HttpResult;
import cn.ms.gateway.server.core.rest.interceptor.Interceptor;

public class ExecutorTask implements Callable<DefaultFullHttpResponse> {

    // httpContext used for controller invoking
    private final HttpContext httpContext;
    // related controller
    private final URLController handler;
    // interceptor list
    private final List<Interceptor> interceptor;

    public ExecutorTask(HttpContext httpContext, List<Interceptor> interceptor, URLController handler) {
        this.httpContext = httpContext;
        this.interceptor = interceptor;
        this.handler = handler;
    }

    @Override
    public DefaultFullHttpResponse call() {
        // call interceptor chain of filter
        for (Interceptor every : interceptor) {
            if (!every.filter(httpContext)) {
            	return HttpResponseBuilder.create(httpContext, HttpResponseStatus.FORBIDDEN);        // httpcode 403
            }
        }

        // call controller method
        HttpResult result = handler.call(httpContext);
        DefaultFullHttpResponse response;
        switch (result.getHttpStatus()) {
        case SUCCESS:
            if (result.getHttpContent() != null) {
                ByteBuf content = Unpooled.wrappedBuffer(SerializeUtils.encode(result.getHttpContent()));
                response = HttpResponseBuilder.create(httpContext, content);                                              // httpcode 200
            } else {
            	response = HttpResponseBuilder.create(httpContext, HttpResponseStatus.NO_CONTENT);          // httpcode 204
            }
            break;
        case RESPONSE_NOT_VALID:
            response = HttpResponseBuilder.create(httpContext, HttpResponseStatus.BAD_GATEWAY);            // httpcode 502
            break;
        case PARAMS_CONVERT_ERROR:
        case PARAMS_NOT_MATCHED:
            response = HttpResponseBuilder.create(httpContext, HttpResponseStatus.BAD_REQUEST);             // httpcode 400
            break;
        case SYSTEM_ERROR:
            response = HttpResponseBuilder.create(httpContext, HttpResponseStatus.INTERNAL_SERVER_ERROR);    // httpcode 500
            break;
        default:
            response = HttpResponseBuilder.create(httpContext, HttpResponseStatus.INTERNAL_SERVER_ERROR);    // httpcode 500
            break;
        }

        // call interceptor chain of *handler*. returned DefaultFullHttpResponse
        // will be replaced to original instance
        for (Interceptor every : interceptor) {
            DefaultFullHttpResponse newResponse = every.handler(httpContext, response);
            if (newResponse != null) {
            	response = newResponse;
            }
        }

        return response;
    }

}
