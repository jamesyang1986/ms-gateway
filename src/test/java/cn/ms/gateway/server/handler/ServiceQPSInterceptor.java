package cn.ms.gateway.server.handler;

import io.netty.handler.codec.http.DefaultFullHttpResponse;
import cn.ms.gateway.server.common.annotations.Interceptor;
import cn.ms.gateway.server.core.NestyOptionProvider;
import cn.ms.gateway.server.core.rest.HttpContext;

@Interceptor
public class ServiceQPSInterceptor extends cn.ms.gateway.server.core.rest.interceptor.Interceptor {

    @Override
    public boolean install(NestyOptionProvider nesty) {
        return super.install(nesty);
    }

    @Override
    public boolean filter(HttpContext context) {
        return super.filter(context);
    }

    @Override
    public DefaultFullHttpResponse handler(HttpContext context, DefaultFullHttpResponse response) {
        return super.handler(context, response);
    }
}
