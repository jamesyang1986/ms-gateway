package cn.ms.gateway;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;

public class SequentialThreeConsumers {
	public static class MyEvent {
		public Object a;
		public Object b;
		public Object c;
		public Object d;
	}

	private static EventFactory<MyEvent> factory = new EventFactory<MyEvent>() {
		@Override
		public MyEvent newInstance() {
			return new MyEvent();
		}
	};

	private static EventHandler<MyEvent> handler1 = new EventHandler<MyEvent>() {
		@Override
		public void onEvent(MyEvent event, long sequence, boolean endOfBatch) throws Exception {
			Thread.sleep(300);
			System.out.println("This is handler1.");
			event.b = event.a;
		}
	};

	private static EventHandler<MyEvent> handler2 = new EventHandler<MyEvent>() {
		@Override
		public void onEvent(MyEvent event, long sequence, boolean endOfBatch) throws Exception {
			Thread.sleep(200);
			System.out.println("This is handler2.");
			event.c = event.b;
		}
	};

	private static EventHandler<MyEvent> handler3 = new EventHandler<MyEvent>() {
		@Override
		public void onEvent(MyEvent event, long sequence, boolean endOfBatch) throws Exception {
			Thread.sleep(100);
			System.out.println("This is handler3.");
			event.d = event.c;
		}
	};

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		Disruptor<MyEvent> disruptor = new Disruptor<MyEvent>(factory, 1024, DaemonThreadFactory.INSTANCE);
		disruptor.handleEventsWith(handler1).then(handler2).then(handler3);
		disruptor.start();
		while (true) {
			disruptor.publishEvent(new EventTranslator<MyEvent>() {
				@Override
				public void translateTo(MyEvent event, long sequence) {
					System.out.println("--------");
				}
			});
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}