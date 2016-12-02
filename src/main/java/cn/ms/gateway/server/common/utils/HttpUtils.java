package cn.ms.gateway.server.common.utils;

import io.netty.handler.codec.http.FullHttpRequest;
import cn.ms.gateway.server.common.RequestMethod;

public class HttpUtils {

    @SuppressWarnings("deprecation")
	public static RequestMethod convertHttpMethodFromNetty(FullHttpRequest request) {
        try {
            return RequestMethod.valueOf(request.getMethod().name().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            return RequestMethod.UNKOWN;
        }
    }

    public static String truncateUrl(String url) {
        if (url.contains("?")){
        	url = url.substring(0, url.indexOf("?"));
        }
        
        return url;
    }
}
