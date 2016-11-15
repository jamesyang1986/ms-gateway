package cn.ms.gateway.core.container;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServer;
import io.reactivex.netty.protocol.http.server.HttpServerBuilder;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import io.reactivex.netty.protocol.http.server.RequestHandler;
import io.reactivex.netty.spectator.SpectatorEventsListenerFactory;
import io.reactivex.netty.spectator.http.HttpServerListener;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.ThreadContext;

import rx.Observable;
import rx.functions.Func1;
import cn.ms.gateway.base.container.IContainer;
import cn.ms.gateway.base.filter.IFilterFactory;
import cn.ms.gateway.common.Conf;
import cn.ms.gateway.common.Constants;
import cn.ms.gateway.common.TradeIdWorker;
import cn.ms.gateway.common.log.Logger;
import cn.ms.gateway.common.log.LoggerFactory;
import cn.ms.gateway.common.utils.NetUtils;
import cn.ms.gateway.core.AssemblySupport;
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
	
	TradeIdWorker tradeIdWorker;
	
	HttpServerListener listener;
	HttpServer<ByteBuf, ByteBuf> server;
	SpectatorEventsListenerFactory factory;
	HttpServerBuilder<ByteBuf, ByteBuf> httpServerBuilder;
	
	
	public RxNettyContainer(IFilterFactory<GatewayREQ, GatewayRES> filterFactory) {
		this.filterFactory = filterFactory;
	}
	
	@Override
	public void init() throws Exception {
		tradeIdWorker = new TradeIdWorker(0, 0);
		factory = new SpectatorEventsListenerFactory();
		
		ServerBootstrap bootstrap=new ServerBootstrap();
		RequestHandler<ByteBuf, ByteBuf> requestHandler=new RequestHandler<ByteBuf, ByteBuf>() {
			@Override
			public Observable<Void> handle(HttpServerRequest<ByteBuf> request, final HttpServerResponse<ByteBuf> response) {
				long tradeStartTime=System.currentTimeMillis();
				String tradeId=String.valueOf(tradeIdWorker.nextId());
				ThreadContext.put(Constants.TRADEID_KEY, tradeId);
		    	logger.info("=====交易开始=====");
				
				final GatewayREQ req = new GatewayREQ();
				req.setTradeId(tradeId);
				req.setTradeStartTime(tradeStartTime);
				req.setInput(request);
				req.setOutput(response);
				
				try {
					//$NON-NLS-获取客户端端IP$
			        String clientIP = request.getHeaders().get("X-Forwarded-For");
			        if (clientIP == null) {
			            InetSocketAddress insocket = (InetSocketAddress) request.getNettyChannel().remoteAddress();
			            clientIP = insocket.getAddress().getHostAddress();
			        }
			        req.setClientHost(clientIP);
					
			        //$NON-NLS-读取参数$
					if(!request.getQueryParameters().isEmpty()){
						for (Map.Entry<String, List<String>> entry:request.getQueryParameters().entrySet()) {
							if(entry.getValue().size()==1){
								req.putClientParam(entry.getKey(), entry.getValue().get(0));
							}
						}
						req.setClientParameters(request.getQueryParameters());
					}
					
					//$NON-NLS-请求头$
			        List<Entry<String, String>> headers=request.getHeaders().entries();
			        for (Entry<String, String> entry:headers) {
			        	req.putClientHeader(entry.getKey(), entry.getValue());
					}
					
					return request.getContent().map(new Func1<ByteBuf, Void>(){
						@Override
						public Void call(ByteBuf data) {
		    				//$NON-NLS-读取请求报文$
		    				req.setClientContent(data.toString(Charset.defaultCharset()));

		    				GatewayRES res = null;
		    				try {
		    					res = filterFactory.doFilter(req);
		    					if(res==null){
		    						res = new GatewayRES();
									res.setContent("微服务网关请求失败");
			    				}
							} catch (Exception e) {
								logger.error(e, "网关过滤器执行失败", e.getMessage());
								
								res = new GatewayRES();
								res.setContent("微服务网关异常");
							}
		    			
		    				AssemblySupport.HttpServerResponse(req, res);
		                    return null;
						}
					});
				} catch (Exception e) {
					logger.error(e, "网关接入回调执行失败", e.getMessage());
					
					GatewayRES res = new GatewayRES();
					res.setContent("服务器异常");
					AssemblySupport.HttpServerResponse(req, res);
					return null;
				}
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
