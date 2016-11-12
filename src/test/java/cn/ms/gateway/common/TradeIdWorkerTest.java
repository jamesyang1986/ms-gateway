package cn.ms.gateway.common;

public class TradeIdWorkerTest {

	public static void main(String[] args) {
		TradeIdWorker tradeIdWorker=new TradeIdWorker(0, 0);
		for (int i = 0; i < 10; i++) {
			System.out.println(tradeIdWorker.nextId());
		}
		
	}
	
}
