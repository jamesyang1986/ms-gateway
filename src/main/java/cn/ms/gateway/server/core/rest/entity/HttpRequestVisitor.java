package cn.ms.gateway.server.core.rest.entity;

import io.netty.handler.codec.http.HttpVersion;
import cn.ms.gateway.server.common.RequestMethod;

import java.util.Map;

public interface HttpRequestVisitor {
	String visitRemoteAddress();

	String visitURI();

	String[] visitTerms();

	RequestMethod visitHttpMethod();

	String visitHttpBody();

	Map<String, String> visitHttpParams();

	Map<String, String> visitHttpHeaders();

	HttpVersion visitHttpVersion();
}
