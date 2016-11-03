package cn.ms.gateway.core.filter.route;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpHeaders.Names;
import io.netty.handler.codec.http.HttpHeaders.Values;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import cn.ms.gateway.base.annotation.Filter;
import cn.ms.gateway.base.filter.IFilter;
import cn.ms.gateway.base.type.FilterType;
import cn.ms.gateway.core.entity.GatewayREQ;
import cn.ms.gateway.core.entity.GatewayRES;

@SuppressWarnings("deprecation")
@Filter(value=FilterType.ROUTE,order=300)
public class HttpProxyRouteFilter implements IFilter<GatewayREQ, GatewayRES>{

	@Override
	public String filterName() {
		return null;
	}

	@Override
	public boolean check(GatewayREQ req, GatewayRES res, Object... args) {
		return true;
	}

	@Override
	public GatewayRES run(GatewayREQ req, GatewayRES res, Object... args) {
		 try {
			 String resContent = "I am OK";
	         FullHttpResponse response = new DefaultFullHttpResponse(
	         		HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(resContent.getBytes("UTF-8")));
	         response.headers().set(Names.CONTENT_TYPE, "text/plain");
	         response.headers().set(Names.CONTENT_LENGTH, response.content().readableBytes());
	         if (HttpHeaders.isKeepAlive(req.getRequest())) {
	             response.headers().set(Names.CONNECTION, Values.KEEP_ALIVE);
	         }
	         req.getCtx().write(response);
	         req.getCtx().flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
