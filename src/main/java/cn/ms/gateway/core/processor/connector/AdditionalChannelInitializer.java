package cn.ms.gateway.core.processor.connector;

import io.netty.channel.Channel;

public interface AdditionalChannelInitializer {

	void initChannel(Channel ch) throws Exception;
	
}
