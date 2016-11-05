package cn.ms.gateway.core.container;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpRequest;

import org.apache.logging.log4j.ThreadContext;

import cn.ms.gateway.common.Constants;
import cn.ms.gateway.common.TradeIdWorker;
import cn.ms.gateway.common.log.Logger;
import cn.ms.gateway.common.log.LoggerFactory;
import cn.ms.gateway.core.AssemblySupport;
import cn.ms.gateway.entity.GatewayREQ;
import cn.ms.gateway.entity.GatewayRES;

public class NettyContainerHandler extends ChannelInboundHandlerAdapter {
    
	private Logger logger=LoggerFactory.getLogger(NettyContainerHandler.class);
	
	private IContainerCallback callback;
	private HttpRequest request;
	private TradeIdWorker tradeIdWorker=new TradeIdWorker(0, 0);
	
	public NettyContainerHandler(IContainerCallback callback) {
		this.callback=callback;
	}
    
	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            request = (HttpRequest) msg;
        }
        if (msg instanceof HttpContent) {
        	long tradeStartTime=System.currentTimeMillis();
        	String tradeId=String.valueOf(tradeIdWorker.nextId());
        	ThreadContext.put(Constants.TRADEID_KEY, tradeId);
        	logger.info("=====交易开始=====");
        	
            HttpContent httpContent = (HttpContent) msg;
            ByteBuf buf = httpContent.content();
            String content=buf.toString(io.netty.util.CharsetUtil.UTF_8);
            buf.release();

            GatewayREQ gatewayREQ=new GatewayREQ();
            gatewayREQ.setTradeId(String.valueOf(tradeId));
            gatewayREQ.setTradeStartTime(tradeStartTime);
            gatewayREQ.setContent(content);
            gatewayREQ.setRequest(request);
            gatewayREQ.setCtx(ctx);
            
            try {
            	GatewayRES gatewayRES = callback.callback(gatewayREQ, request);
            	
            	if(gatewayRES!=null){
            		//$NON-NLS-组装响应结果$
            		AssemblySupport.HttpServerResponse(gatewayREQ, gatewayRES);
            	}
			} catch (Throwable t) {
				logger.error(t, "微服务网关处理异常: %s", t.getMessage());
			}
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    	logger.error(cause, "微服务网关Netty接入异常: %s", cause.getMessage());
        ctx.close();
    }

}
