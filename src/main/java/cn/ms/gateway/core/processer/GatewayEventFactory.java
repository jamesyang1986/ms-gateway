package cn.ms.gateway.core.processer;

import cn.ms.gateway.entity.GatewayREQ;

import com.lmax.disruptor.EventFactory;

/**
 * 根据环形区域的大小,会提前创建好所有区域
 * 
 * @author lry
 */
public class GatewayEventFactory implements EventFactory<GatewayREQ> {

	/**
	 * 启动时会创建ringBufferSize个实例
	 */
	@Override
	public GatewayREQ newInstance() {
		return new GatewayREQ();
	}

}
