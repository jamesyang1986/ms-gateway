package cn.ms.gateway.core.container;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.netty.protocol.http.server.HttpServer;
import io.reactivex.netty.protocol.http.server.HttpServerBuilder;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import io.reactivex.netty.protocol.http.server.RequestHandler;
import io.reactivex.netty.spectator.SpectatorEventsListenerFactory;
import io.reactivex.netty.spectator.http.HttpServerListener;
import rx.Observable;
import cn.ms.gateway.base.container.IContainer;
import cn.ms.gateway.base.filter.IFilterFactory;
import cn.ms.gateway.common.Conf;
import cn.ms.gateway.common.log.Logger;
import cn.ms.gateway.common.log.LoggerFactory;
import cn.ms.gateway.common.utils.NetUtils;
import cn.ms.gateway.entity.GatewayREQ;
import cn.ms.gateway.entity.GatewayRES;

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
	HttpServerBuilder<ByteBuf, ByteBuf> httpServerBuilder;

	public RxNettyContainer(IFilterFactory<GatewayREQ, GatewayRES> filterFactory) {
		this.filterFactory = filterFactory;
	}
	
	@Override
	public void init() throws Exception {
		factory = new SpectatorEventsListenerFactory();
		
		ServerBootstrap bootstrap=new ServerBootstrap();
		RequestHandler<ByteBuf, ByteBuf> requestHandler=new RequestHandler<ByteBuf, ByteBuf>() {
			@Override
			public Observable<Void> handle(HttpServerRequest<ByteBuf> request, HttpServerResponse<ByteBuf> response) {
				GatewayRES res = new GatewayRES();
				try {
					GatewayREQ req = new GatewayREQ();
					req.setIn(request);
					req.setOut(response);
//					req.setClientContent(request.getContent().toString());
					
					res.setContent("ddddddd");
//					res = filterFactory.doFilter(req);
					response.setStatus(HttpResponseStatus.OK);
				} catch (Exception e) {
					response.setStatus(HttpResponseStatus.INTERNAL_SERVER_ERROR);
				}
				
                response.writeStringAndFlush(res.getContent());
                return response.close();
			}
		};
		httpServerBuilder=new HttpServerBuilder<ByteBuf, ByteBuf>(bootstrap, Conf.CONF.getGwport(), requestHandler, true);
		httpServerBuilder.withMetricEventsListenerFactory(factory);
		server = httpServerBuilder.build();
		listener = factory.forHttpServer(server);
	}

	@Override
	public void start() throws Exception {
		server.start();
		logger.info("微服务网关启动成功,服务地址为: http://"+NetUtils.getLocalIp()+":"+server.getServerPort()+"/");
		server.waitTillShutdown();
	}

	@Override
	public void shutdown() throws Exception {
		if(server!=null){
			server.shutdown();			
		}
	}

}
