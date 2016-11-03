package cn.ms.gateway.core.disruptor.support;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.ms.gateway.core.disruptor.IDisruptor;
import cn.ms.gateway.core.disruptor.event.GatewayEventHandler;
import cn.ms.gateway.core.disruptor.event.GatewayEventFactory;
import cn.ms.gateway.core.entity.GatewayREQ;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

public class DisruptorFactory implements IDisruptor {

	Disruptor<GatewayREQ> disruptor;
	EventFactory<GatewayREQ> eventFactory = new GatewayEventFactory();
	ExecutorService executor = Executors.newSingleThreadExecutor();

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public void init() throws Exception {
		int ringBufferSize = 1024 * 1024; // RingBuffer 大小，必须是 2 的 N 次方
		disruptor = new Disruptor<GatewayREQ>(eventFactory, ringBufferSize,
				executor, ProducerType.SINGLE, new YieldingWaitStrategy());

		EventHandler<GatewayREQ> eventHandler = new GatewayEventHandler();
		disruptor.handleEventsWith(eventHandler);
	}

	@Override
	public void start() throws Exception {
		disruptor.start();
	}

	/**
	 * 发布事件<p>
	 * <p>
	 * Disruptor 的事件发布过程是一个两阶段提交的过程:<p>
	 * 第一步：先从 RingBuffer 获取下一个可以写入的事件的序号<p>
	 * 第二步：获取对应的事件对象，将数据写入事件对象<p>
	 * 第三部：将事件提交到 RingBuffer<p>
	 * <p>
	 * 注意：事件只有在提交之后才会通知 EventProcessor 进行处理
	 * 
	 * @param req
	 * @param args
	 */
	@Override
	public void publish(GatewayREQ req, Object...args) throws Exception {
		RingBuffer<GatewayREQ> ringBuffer = disruptor.getRingBuffer();
		long sequence = ringBuffer.next();

		try {
			// 获取该序号对应的事件对象
			GatewayREQ event = ringBuffer.get(sequence);
			event.setContent(req.getContent());
			event.setRequest(event.getRequest());
			event.setCtx(req.getCtx());
		} finally {
			// 发布事件
			ringBuffer.publish(sequence);
		}
	}

	@Override
	public void destory() throws Exception {
		if (disruptor != null) {
			// 关闭 disruptor，方法会堵塞，直至所有的事件都得到处理
			disruptor.shutdown();
		}

		if (executor != null) {
			executor.shutdown();
		}
	}

}
