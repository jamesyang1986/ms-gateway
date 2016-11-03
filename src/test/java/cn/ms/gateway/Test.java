package cn.ms.gateway;

import java.text.DecimalFormat;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.Sequencer;
import com.lmax.disruptor.YieldingWaitStrategy;

public class Test {

	public static void main(String[] args) {
		try {
			new Test().testRingBuffer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//使用RingBuffer测试
    public void testRingBuffer() throws Exception
    {
        //创建一个单生产者的RingBuffer，EventFactory是填充缓冲区的对象工厂
        //YieldingWaitStrategy等"等待策略"指出消费者等待数据变得可用前的策略
        final RingBuffer<TestObj> ringBuffer = RingBuffer. createSingleProducer(new EventFactory<TestObj>() {
            public TestObj newInstance() {
                return new TestObj(0);
            }
        } , 1024*1024, new YieldingWaitStrategy());
        //创建消费者指针
        final SequenceBarrier barrier = ringBuffer.newBarrier();
        
        final long objCount=100000000;
		Thread producer = new Thread(new Runnable() {//生产者
            public void run() {
                for ( long i=1;i<=objCount;i++ )
                {
                    long index = ringBuffer.next();//申请下一个缓冲区Slot
                    ringBuffer.get(index).setValue(i);//对申请到的Slot赋值
                    ringBuffer.publish(index);//发布，然后消费者可以读到
                }
            }
        });
        Thread consumer = new Thread(new Runnable() {//消费者
            @SuppressWarnings("unused")
			public void run() {
                TestObj readObj = null;
                int readCount = 0;
                long readIndex = Sequencer.INITIAL_CURSOR_VALUE;
                while ( readCount < objCount )//读取objCount个元素后结束
                {
                    try{
                        long nextIndex = readIndex + 1;//当前读取到的指针+1，即下一个该读的位置
                        long availableIndex = barrier.waitFor(nextIndex);//等待直到上面的位置可读取
                        while ( nextIndex <= availableIndex )//从下一个可读位置到目前能读到的位置(Batch!)
                        {
                            readObj = ringBuffer.get(nextIndex);//获得Buffer中的对象
                            //DoSomethingAbout(readObj);
                            readCount++;
                            nextIndex ++;
                        }
                        readIndex = availableIndex;//刷新当前读取到的位置
                    }catch ( Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }
        });
        
        long timeStart = System.currentTimeMillis();//统计时间
        producer.start();
        consumer.start();
        consumer.join();
        producer.join();
        long timeEnd = System.currentTimeMillis();
        DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance();
        System.out.println("Total time for 1000000 is " + (timeEnd - timeStart) + " ms,tps is" +
                " = " + df.format(objCount/(timeEnd - timeStart)*1000) );
        
    }
	
}
