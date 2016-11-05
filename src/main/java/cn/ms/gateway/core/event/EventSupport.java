package cn.ms.gateway.core.event;

import java.util.concurrent.ExecutorService;

import cn.ms.gateway.base.connector.IConnector;
import cn.ms.gateway.base.event.IEvent;
import cn.ms.gateway.common.thread.FixedThreadPoolExecutor;
import cn.ms.gateway.common.thread.NamedThreadFactory;
import cn.ms.gateway.core.event.support.GatewayEventFactory;
import cn.ms.gateway.core.event.support.GatewayEventHandler;
import cn.ms.gateway.entity.GatewayREQ;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

public class EventSupport implements IEvent {

	EventConf conf;
	Disruptor<GatewayREQ> disruptor;
	ExecutorService executorService;
	EventFactory<GatewayREQ> eventFactory;
	IConnector connector=null;

	public EventSupport(EventConf conf, IConnector connector) {
		this.conf=conf;
		this.connector=connector;
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public void init() throws Exception {
		eventFactory = new GatewayEventFactory();
		executorService=new FixedThreadPoolExecutor(conf.getExecutorThread(), new NamedThreadFactory("disruptorFactory")){
			@Override
			protected void beforeExecute(Thread t, Runnable r) {
			}
			
			@Override
			protected void afterExecute(Runnable r, Throwable t) {
			}
		};
		
		disruptor = new Disruptor<GatewayREQ>(eventFactory, conf.getRingBufferSize(),
				executorService, conf.getProducerType(), conf.getWaitStrategy());

		EventHandler<GatewayREQ> eventHandler = new GatewayEventHandler(connector);
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
	 * @throws Throwable
	 */
	@Override
	public void publish(GatewayREQ req, Object...args) throws Throwable {
		RingBuffer<GatewayREQ> ringBuffer = disruptor.getRingBuffer();
		long sequence = ringBuffer.next();

		try {
			// 获取该序号对应的事件对象
			GatewayREQ event = ringBuffer.get(sequence);
			event.setOriginURI(req.getOriginURI());
			event.setContent(req.getContent());
			event.setRequest(req.getRequest());
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

		if (executorService != null) {
			executorService.shutdown();
		}
	}

}
