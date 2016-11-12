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
						Document doc=Jsoup.connect("http://localhost:9500").ignoreContentType(true).get();
						System.out.println(doc.text());
						if(!doc.text().contains("This is 65")){
							throw new RuntimeException();
						}
					}
				};
			} 
		}; 
		perf.loopCount = 1000000;
		perf.threadCount = 16;
		perf.logInterval = 10000;
		perf.run();
		perf.close();
	} 
	
}
