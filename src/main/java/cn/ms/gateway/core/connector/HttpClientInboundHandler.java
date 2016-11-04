package cn.ms.gateway.core.connector;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders.Names;
import io.netty.handler.codec.http.HttpResponse;
import cn.ms.gateway.core.connector.IConnectorCallback;

@SuppressWarnings("deprecation")
public class HttpClientInboundHandler extends ChannelInboundHandlerAdapter {
	
	IConnectorCallback callback;
	
	public HttpClientInboundHandler(IConnectorCallback callback) {
		this.callback=callback;
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		if (msg instanceof HttpResponse) {
			HttpResponse response = (HttpResponse) msg;
			System.out.println("CONTENT_TYPE:" + response.headers().get(Names.CONTENT_LENGTH));
		}
		if (msg instanceof HttpContent) {
			HttpContent content = (HttpContent) msg;
			ByteBuf buf = content.content();
			
			String resContent=buf.toString(io.netty.util.CharsetUtil.UTF_8);
			try {
				callback.onReturn(resContent);
			} catch (Throwable e) {
				e.printStackTrace();
			}
			
			buf.release();
		}
	}
}