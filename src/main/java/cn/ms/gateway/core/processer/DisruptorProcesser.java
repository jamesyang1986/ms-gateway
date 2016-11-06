package cn.ms.gateway.core.processer;

import io.netty.handler.codec.http.HttpResponse;

import java.util.concurrent.ExecutorService;

import cn.ms.gateway.base.connector.IConnector;
import cn.ms.gateway.base.processer.IProcesser;
import cn.ms.gateway.common.Conf;
import cn.ms.gateway.common.Constants;
import cn.ms.gateway.common.thread.FixedThreadPoolExecutor;
import cn.ms.gateway.common.thread.NamedThreadFactory;
import cn.ms.gateway.entity.GatewayREQ;
import cn.ms.gateway.entity.GatewayRES;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

/**
 * 基于Disruptor实现网关的事件处理器
 * 
 * @author lry
 */
public class DisruptorProcesser implements IProcesser<GatewayRES, GatewayRES, HttpResponse> {

	Disruptor<GatewayREQ> disruptor;
	ExecutorService executorService;
	EventFactory<GatewayREQ> eventFactory;
	IConnector<GatewayRES, GatewayRES, HttpResponse> connector=null;

	@Override
	public void setConnector(IConnector<GatewayRES, GatewayRES, HttpResponse> connector) {
		this.connector=connector;
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public void init() throws Exception {
		eventFactory = new ProcesserEventFactory();
		executorService=new FixedThreadPoolExecutor(Conf.CONF.getExecutorThread(), new NamedThreadFactory("disruptorFactory")){
			@Override
			protected void beforeExecute(Thread t, Runnable r) {
			}
			
			@Override
			protected void afterExecute(Runnable r, Throwable t) {
			}
		};
		
		ProducerType producerType = null;
		if (Constants.PRODUCER_TYPE_SINGLE.equals(Conf.CONF.getProducerType())) {
			producerType = ProducerType.SINGLE;
		} else if (Constants.PRODUCER_TYPE_MULTI.equals(Conf.CONF.getProducerType())) {
			producerType = ProducerType.MULTI;
		}

		WaitStrategy waitStrategy = null;
		if (Constants.WS_BLOCKING_WAIT.equals(Conf.CONF.getWaitStrategy())) {
			waitStrategy = new BlockingWaitStrategy();
		} else if (Constants.WS_SLEEPING_WAIT.equals(Conf.CONF.getWaitStrategy())) {
			waitStrategy = new SleepingWaitStrategy();
		} else if (Constants.WS_YIELDING_WAIT.equals(Conf.CONF.getWaitStrategy())) {
			waitStrategy = new YieldingWaitStrategy();
		}
		
		disruptor = new Disruptor<GatewayREQ>(eventFactory, Conf.CONF.getRingBufferSize(), executorService, producerType, waitStrategy);
		EventHandler<GatewayREQ> eventHandler = new ProcesserHandler(connector);
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
			event.setTradeId(req.getTradeId());
			event.setTradeStartTime(req.getTradeStartTime());
			event.setOriginURI(req.getOriginURI());
			event.setContent(req.getContent());
			event.setRequest(req.getRequest());
			event.setCtx(req.getCtx());
		} finally {
			//TODO MDC问题需要处理?
			ringBuffer.publish(sequence);// 发布事件
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
