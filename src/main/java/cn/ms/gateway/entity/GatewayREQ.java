package cn.ms.gateway.entity;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;

/**
 * 网关请求对象
 * 
 * @author lry
 */
public class GatewayREQ {

	String content;
	String originURI="http://localhost:8844/serviceId";
	HttpRequest request;
	ChannelHandlerContext ctx;
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getOriginURI() {
		return originURI;
	}
	public void setOriginURI(String originURI) {
		this.originURI = originURI;
	}
	public HttpRequest getRequest() {
		return request;
	}
	public void setRequest(HttpRequest request) {
		this.request = request;
	}
	public ChannelHandlerContext getCtx() {
		return ctx;
	}
	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}
	
}
