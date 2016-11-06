package cn.ms.gateway.core.processer;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.ProducerType;

public class ProcesserConf {

	public static final WaitStrategy BLOCKING_WAIT=new BlockingWaitStrategy();
	public static final WaitStrategy SLEEPING_WAIT=new SleepingWaitStrategy();
	public static final WaitStrategy YIELDING_WAIT=new YieldingWaitStrategy();
	
	/**
	 * The size of the ring buffer, must be power of 2.
	 */
	int ringBufferSize=1024*1024;
	/**
	 * An Executor to execute event processors
	 */
	int executorThread=10;
	/**
	 * The claim strategy to use for the ring buffer.
	 */
	ProducerType producerType=ProducerType.SINGLE;
	/**
	 * The wait strategy to use for the ring buffer.
	 */
	WaitStrategy waitStrategy=YIELDING_WAIT;
	
	public int getRingBufferSize() {
		return ringBufferSize;
	}
	public void setRingBufferSize(int ringBufferSize) {
		this.ringBufferSize = ringBufferSize;
	}
	public int getExecutorThread() {
		return executorThread;
	}
	public void setExecutorThread(int executorThread) {
		this.executorThread = executorThread;
	}
	public ProducerType getProducerType() {
		return producerType;
	}
	public void setProducerType(ProducerType producerType) {
		this.producerType = producerType;
	}
	public WaitStrategy getWaitStrategy() {
		return waitStrategy;
	}
	public void setWaitStrategy(WaitStrategy waitStrategy) {
		this.waitStrategy = waitStrategy;
	}
	
	@Override
	public String toString() {
		return "DisruptorConf [ringBufferSize=" + ringBufferSize
				+ ", executorThread=" + executorThread + ", producerType="
				+ producerType + ", waitStrategy=" + waitStrategy + "]";
	}
	
}
