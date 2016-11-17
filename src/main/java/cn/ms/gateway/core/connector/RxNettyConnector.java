package cn.ms.gateway.core.connector;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;
import io.reactivex.netty.RxNetty;
import io.reactivex.netty.protocol.http.client.HttpClient.HttpClientConfig.Builder;
import io.reactivex.netty.protocol.http.client.HttpClientRequest;
import io.reactivex.netty.protocol.http.client.HttpClientResponse;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.ThreadContext;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import cn.ms.gateway.base.connector.IConnector;
import cn.ms.gateway.common.Constants;
import cn.ms.gateway.common.log.Logger;
import cn.ms.gateway.common.log.LoggerFactory;
import cn.ms.gateway.entity.GatewayREQ;
import cn.ms.gateway.entity.GatewayRES;

/**
 * 基于RxNetty的路由接出
 * 
 * @author lry
 */
public class RxNettyConnector implements IConnector<GatewayREQ, GatewayRES> {

	private static final Logger logger = LoggerFactory.getLogger(RxNettyConnector.class);
	
	@Override
	public void init() throws Exception {
		
	}

	@Override
	public void start() throws Exception {

	}
	
	@Override
	public GatewayRES connector(final GatewayREQ req, Object... args) throws Exception {
		final GatewayRES res=new GatewayRES();
		
		HttpClientRequest<ByteBuf> request=HttpClientRequest.create(HttpVersion.HTTP_1_1, HttpMethod.POST, req.getRemoteURI());
		request=request.withContent(req.getClientContent());
		
		req.setRouteStartTime(System.currentTimeMillis());
		logger.info("=====路由开始=====");
		
		Builder builder=Builder.fromDefaultConfig().readTimeout(60, TimeUnit.SECONDS).responseSubscriptionTimeout(60, TimeUnit.SECONDS);
		Observable<HttpClientResponse<ByteBuf>> observable=RxNetty.createHttpRequest(request, builder.build());
		observable.flatMap(new Func1<HttpClientResponse<ByteBuf>, Observable<ByteBuf>>() {
        	@Override
        	public Observable<ByteBuf> call(HttpClientResponse<ByteBuf> response) {
        		return response.getContent();
        	}
		}).toBlocking().forEach(new Action1<ByteBuf>() {
			@Override
			public void call(ByteBuf data) {
				ThreadContext.put(Constants.TRADEID_KEY, req.getTradeId());
				logger.info("[路由耗时:%sms]=====路由结束=====", (System.currentTimeMillis() - req.getRouteStartTime()));
				
				res.setContent(data.toString(Charset.defaultCharset()));
			}
		});
		
		return res;
	}
	
	@Override
	public void shutdown() throws Exception {
		
	}

}
