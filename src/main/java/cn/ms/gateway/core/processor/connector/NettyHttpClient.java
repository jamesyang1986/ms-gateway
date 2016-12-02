package cn.ms.gateway.core.processor.connector;

import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders.Names;
import io.netty.handler.codec.http.HttpHeaders.Values;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpVersion;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import cn.ms.gateway.core.processor.connector.response.NettyHttpResponseFuture;
import cn.ms.gateway.core.processor.connector.support.AdditionalChannelInitializer;
import cn.ms.gateway.core.processor.connector.support.NettyChannelPool;

@SuppressWarnings("deprecation")
public class NettyHttpClient {

	private NettyChannelPool channelPool;
	private ConfigBuilder configBuilder;

	private NettyHttpClient(ConfigBuilder configBuilder) {
		this.configBuilder = configBuilder;
		this.channelPool = new NettyChannelPool(configBuilder.getMaxPerRoute(),
				configBuilder.getConnectTimeOutInMilliSecondes(),
				configBuilder.getMaxIdleTimeInMilliSecondes(),
				configBuilder.getForbidForceConnect(),
				configBuilder.getAdditionalChannelInitializer(),
				configBuilder.getOptions(), configBuilder.getGroup());
	}

	public NettyHttpResponseFuture doPost(NettyHttpRequest request) throws Exception {
		HttpRequest httpRequest = create(request, HttpMethod.POST);
		InetSocketAddress route = new InetSocketAddress(request.getUri().getHost(), request.getUri().getPort());

		return channelPool.sendRequest(route, httpRequest);
	}

	public NettyHttpResponseFuture doGet(NettyHttpRequest request) throws Exception {
		HttpRequest httpRequest = create(request, HttpMethod.GET);
		InetSocketAddress route = new InetSocketAddress(request.getUri().getHost(), request.getUri().getPort());
		
		return channelPool.sendRequest(route, httpRequest);
	}

	public void close() throws InterruptedException {
		channelPool.close();
	}

	public ConfigBuilder getConfigBuilder() {
		return configBuilder;
	}

	public void setConfigBuilder(ConfigBuilder configBuilder) {
		this.configBuilder = configBuilder;
	}
	
	public static HttpRequest create(NettyHttpRequest request, HttpMethod httpMethod) {
		HttpRequest httpRequest = null;
		if (HttpMethod.POST == httpMethod) {
			httpRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1,httpMethod, request.getUri().getRawPath(), request.getContent().retain());
			httpRequest.headers().set(Names.CONTENT_LENGTH,
					request.getContent().readableBytes());
		} else {
			httpRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1,
					httpMethod, request.getUri().getRawPath());
		}

		for (Entry<String, Object> entry : request.getHeaders().entrySet()) {
			httpRequest.headers().set(entry.getKey(), entry.getValue());
		}

		httpRequest.headers().set(Names.CONNECTION, Values.KEEP_ALIVE);
		httpRequest.headers().set(Names.HOST, request.getUri().getHost());

		return httpRequest;
	}

	public static final class ConfigBuilder {
		@SuppressWarnings("rawtypes")
		private Map<ChannelOption, Object> options = new HashMap<ChannelOption, Object>();
		// max idle time for a channel before close
		private int maxIdleTimeInMilliSecondes;
		// max time wait for a channel return from pool
		private int connectTimeOutInMilliSecondes;

		/**
		 * value is false indicates that when there is not any channel in pool
		 * and no new channel allowed to be create based on maxPerRoute, a new
		 * channel will be forced to create.Otherwise, a
		 * <code>TimeoutException</code> will be thrown value is false.
		 */
		private boolean forbidForceConnect = false;
		private AdditionalChannelInitializer additionalChannelInitializer;

		// max number of channels allow to be created per route
		private Map<String, Integer> maxPerRoute;
		private EventLoopGroup customGroup;

		public ConfigBuilder() {
		}

		public NettyHttpClient build() {
			return new NettyHttpClient(this);
		}

		public ConfigBuilder maxPerRoute(Map<String, Integer> maxPerRoute) {
			this.maxPerRoute = maxPerRoute;
			return this;
		}

		public ConfigBuilder connectTimeOutInMilliSecondes(
				int connectTimeOutInMilliSecondes) {
			this.connectTimeOutInMilliSecondes = connectTimeOutInMilliSecondes;
			return this;
		}

		public ConfigBuilder option(@SuppressWarnings("rawtypes") ChannelOption key, Object value) {
			options.put(key, value);
			return this;
		}

		public ConfigBuilder maxIdleTimeInMilliSecondes(
				int maxIdleTimeInMilliSecondes) {
			this.maxIdleTimeInMilliSecondes = maxIdleTimeInMilliSecondes;
			return this;
		}

		public ConfigBuilder additionalChannelInitializer(
				AdditionalChannelInitializer additionalChannelInitializer) {
			this.additionalChannelInitializer = additionalChannelInitializer;
			return this;
		}

		public ConfigBuilder customGroup(EventLoopGroup customGroup) {
			this.customGroup = customGroup;
			return this;
		}

		public ConfigBuilder forbidForceConnect(boolean forbidForceConnect) {
			this.forbidForceConnect = forbidForceConnect;
			return this;
		}

		@SuppressWarnings("rawtypes")
		public Map<ChannelOption, Object> getOptions() {
			return options;
		}

		public int getMaxIdleTimeInMilliSecondes() {
			return maxIdleTimeInMilliSecondes;
		}

		public AdditionalChannelInitializer getAdditionalChannelInitializer() {
			return additionalChannelInitializer;
		}

		public Map<String, Integer> getMaxPerRoute() {
			return maxPerRoute;
		}

		public int getConnectTimeOutInMilliSecondes() {
			return connectTimeOutInMilliSecondes;
		}

		public EventLoopGroup getGroup() {
			return this.customGroup;
		}

		public boolean getForbidForceConnect() {
			return this.forbidForceConnect;
		}
	}
	
}
