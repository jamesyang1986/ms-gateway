package cn.ms.gateway.core.connector;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpContent;
import io.netty.util.CharsetUtil;

import org.apache.logging.log4j.ThreadContext;

import cn.ms.gateway.common.Constants;
import cn.ms.gateway.common.log.Logger;
import cn.ms.gateway.common.log.LoggerFactory;
import cn.ms.gateway.entity.GatewayRES;

/**
 * 基于Netty通讯Handler
 * 
 * @author lry
 */
public class NettyConnectorHandler extends ChannelInboundHandlerAdapter {

	private static final Logger logger=LoggerFactory.getLogger(NettyConnectorHandler.class);
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		//$NON-NLS-通过通道获取回调函数$
		CallbackTransferObject callbackTransferObject = getCallbackTransferObject(ctx);
		
		if (msg instanceof HttpContent) {
			ByteBuf buf = null;
			HttpContent content = null;

			try {
				content = (HttpContent) msg;
				buf = content.content();
				String resContent = buf.toString(CharsetUtil.UTF_8);
				GatewayRES gatewayRES=new GatewayRES();
				gatewayRES.setContent(resContent);
				
				callbackTransferObject.getCallback().callback(gatewayRES);
			} finally {
				buf.release();//释放
			}
		}
	}
	
	@Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    	logger.error(cause, "网关路由异常: %s", cause.getMessage());
    	
    	//$NON-NLS-通过通道获取回调函数$
    	CallbackTransferObject callbackTransferObject = getCallbackTransferObject(ctx);
    	callbackTransferObject.getCallback().exception(cause);
    	
        ctx.close();
    }
    
    /**
     * 路由通讯接收结果前处理
     * 
     * @param ctx
     * @return
     */
    private CallbackTransferObject getCallbackTransferObject(ChannelHandlerContext ctx) {
    	CallbackTransferObject callbackTransferObject = ctx.channel().attr(NettyConnector.CHANNEL_CALLBACK_KEY).get();
		
    	ThreadContext.put(Constants.TRADEID_KEY, callbackTransferObject.getTradeId());// 线程参数继续
		logger.info("[路由总耗时:%sms]=====路由结束=====", (System.currentTimeMillis() - callbackTransferObject.getRouteStartTime()));
		
		return callbackTransferObject;
	}

}
