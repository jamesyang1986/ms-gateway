package cn.ms.gateway.server.core.rest.interceptor;

import cn.ms.gateway.server.core.rest.interceptor.internal.AccessLog;

public enum InterceptorHelpers {
    ACCESS_LOG(new AccessLog());

    InterceptorHelpers(Interceptor instance) {
        this.instance = instance;
    }

    public Interceptor instance;
}
