package cn.ms.gateway.core.connector;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.util.CharsetUtil;
import cn.ms.gateway.base.connector.IProxyCallback;

/**
 * 网关
 * 
 * @author lry
 */
public class NettyHandler extends ChannelInboundHandlerAdapter {

	IProxyCallback callback;

	public NettyHandler(IProxyCallback callback) {
		this.callback = callback;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof HttpResponse) {
			callback.before((HttpResponse) msg);
		}
		if (msg instanceof HttpContent) {
			ByteBuf buf = null;
			HttpContent content = null;

			try {
				content = (HttpContent) msg;
				buf = content.content();
				String resContent = buf.toString(CharsetUtil.UTF_8);

				callback.callback(resContent);
			} finally {
				buf.release();
			}
		}
	}
}