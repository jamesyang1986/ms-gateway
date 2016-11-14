package cn.ms.gateway.core.container;

import java.util.Random;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.netty.RxNetty;
import io.reactivex.netty.channel.SingleNioLoopProvider;
import io.reactivex.netty.protocol.http.server.ErrorResponseGenerator;
import io.reactivex.netty.protocol.http.server.HttpServer;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import io.reactivex.netty.protocol.http.server.RequestHandler;
import io.reactivex.netty.server.ErrorHandler;
import io.reactivex.netty.spectator.SpectatorEventsListenerFactory;
import io.reactivex.netty.spectator.http.HttpServerListener;
import rx.Observable;
import cn.ms.gateway.base.container.IContainer;
import cn.ms.gateway.base.filter.IFilterFactory;
import cn.ms.gateway.common.Conf;
import cn.ms.gateway.common.log.Logger;
import cn.ms.gateway.common.log.LoggerFactory;
import cn.ms.gateway.entity.GatewayREQ;
import cn.ms.gateway.entity.GatewayRES;

import com.alibaba.fastjson.JSON;

/**
 * 基于Netty实现微服务网关容器
 * 
 * @author lry
 */
public class RxNettyContainer implements IContainer<GatewayREQ, GatewayRES> {

	private static final Logger logger = LoggerFactory.getLogger(RxNettyContainer.class);

	IFilterFactory<GatewayREQ, GatewayRES> filterFactory;
	
	HttpServerListener listener;
	HttpServer<ByteBuf, ByteBuf> server;
	SpectatorEventsListenerFactory factory;

	public RxNettyContainer(IFilterFactory<GatewayREQ, GatewayRES> filterFactory) {
		this.filterFactory = filterFactory;
	}
	
	public static void main(String[] args) {
		try {
			RxNettyContainer container=new RxNettyContainer(null);
			container.init();
			container.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void init() throws Exception {
		factory = new SpectatorEventsListenerFactory();
		RxNetty.useEventLoopProvider(new SingleNioLoopProvider());
		RxNetty.useMetricListenersFactory(factory);
		
		server = RxNetty.createHttpServer(Conf.CONF.getGwport(), new RequestHandler<ByteBuf, ByteBuf>() {
			@Override
			public Observable<Void> handle(HttpServerRequest<ByteBuf> request, HttpServerResponse<ByteBuf> response) {
				if(new Random().nextDouble()>0.5){
					throw new RuntimeException();
				}
                response.setStatus(HttpResponseStatus.OK);
                response.writeString(JSON.toJSONString(listener));
                return response.close();
			}
		}).withErrorHandler(new ErrorHandler() {
			@Override
			public Observable<Void> handleError(Throwable paramThrowable) {
				//TODO
				return null;
			}
		}).withErrorResponseGenerator(new ErrorResponseGenerator<ByteBuf>() {
			@Override
			public void updateResponse(HttpServerResponse<ByteBuf> paramHttpServerResponse, Throwable paramThrowable) {
				//TODO
			}
		});
		
		listener = factory.forHttpServer(server);
	}

	@Override
	public void start() throws Exception {
		server.start();
		logger.info("微服务网关启动成功,服务地址为:"+server.getServerPort());
		server.waitTillShutdown();
	}

	@Override
	public void shutdown() throws Exception {
		if(server!=null){
			server.shutdown();			
		}
	}

}
