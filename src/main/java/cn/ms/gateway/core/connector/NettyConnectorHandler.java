package cn.ms.gateway.core.connector;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.util.CharsetUtil;
import cn.ms.gateway.base.adapter.ICallback;
import cn.ms.gateway.common.log.Logger;
import cn.ms.gateway.common.log.LoggerFactory;
import cn.ms.gateway.entity.GatewayREQ;
import cn.ms.gateway.entity.GatewayRES;

public class NettyConnectorHandler extends ChannelInboundHandlerAdapter {

private static final Logger logger=LoggerFactory.getLogger(NettyConnectorHandler.class);
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		//$NON-NLS-通过通道获取回调函数$
		ICallback<GatewayREQ, GatewayRES> callback = ctx.channel().attr(NettyConnector.CHANNEL_CALLBACK_KEY).get();
		
		if (msg instanceof FullHttpResponse) {
//			callback.before((FullHttpResponse) msg);
		}
		
		if (msg instanceof HttpContent) {
			ByteBuf buf = null;
			HttpContent content = null;

			try {
				content = (HttpContent) msg;
				buf = content.content();
				String resContent = buf.toString(CharsetUtil.UTF_8);
				GatewayRES gatewayRES=new GatewayRES();
				gatewayRES.setContent(resContent);
				
				callback.callback(null, gatewayRES);
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
    	logger.error(cause, "The connector processor handler is error: %s", cause.getMessage());
        ctx.close();
    }

}
