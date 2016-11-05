package cn.ms.gateway.core.container;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpRequest;

import org.apache.logging.log4j.ThreadContext;

import cn.ms.gateway.common.TradeIdWorker;
import cn.ms.gateway.common.log.Logger;
import cn.ms.gateway.common.log.LoggerFactory;
import cn.ms.gateway.entity.GatewayREQ;

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
        	ThreadContext.put("tradeId", tradeId);
        	logger.info("=====交易开始=====");
        	
            HttpContent httpContent = (HttpContent) msg;
            ByteBuf buf = httpContent.content();
            String content=buf.toString(io.netty.util.CharsetUtil.UTF_8);
            buf.release();

            GatewayREQ req=new GatewayREQ();
            req.setTradeId(String.valueOf(tradeId));
            req.setTradeStartTime(tradeStartTime);
            req.setContent(content);
            req.setRequest(request);
            req.setCtx(ctx);
            
            try {
            	callback.callback(req, request);
			} catch (Throwable e) {
				e.printStackTrace();
			}
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    	cause.printStackTrace();
        ctx.close();
    }

}
