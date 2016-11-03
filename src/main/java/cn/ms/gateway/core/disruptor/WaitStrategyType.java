package cn.ms.gateway.core.disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.YieldingWaitStrategy;

public class WaitStrategyType {

	public static final WaitStrategy BLOCKING_WAIT=new BlockingWaitStrategy();
	public static final WaitStrategy SLEEPING_WAIT=new SleepingWaitStrategy();
	public static final WaitStrategy YIELDING_WAIT=new YieldingWaitStrategy();

}
