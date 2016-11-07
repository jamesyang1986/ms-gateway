package cn.ms.gateway;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class TestPerf {

	public static void main(String[] args) throws Exception{
		Perf perf = new Perf(){ 
			public TaskInThread buildTaskInThread() {
				return new TaskInThread(){
					public void initTask() throws Exception {
					}
					
					public void doTask() throws Exception {
						Document doc=Jsoup.connect("http://127.0.0.1:9500").ignoreContentType(true).get();
						if(!doc.text().contains("I am OK")){
							throw new RuntimeException();
						}
					}
				};
			} 
		}; 
		perf.loopCount = 100000;
		perf.threadCount = 16;
		perf.logInterval = 100000;
		perf.run();
		perf.close();
	} 
	
}
