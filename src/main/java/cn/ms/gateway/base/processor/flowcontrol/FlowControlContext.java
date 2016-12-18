package cn.ms.gateway.base.processor.flowcontrol;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.RateLimiter;

/**
 * 流量控制
 * 
 * @author lry
 */
public class FlowControlContext {

	//并发公平锁
	public static final boolean CCT_FAIL=true;
	//限流参数分隔符
	public static final String FC_SEQ="&";
	
	private ConcurrentHashMap<FlowControlRule, String[]> flowControlKeyDatas = new ConcurrentHashMap<FlowControlRule, String[]>();
	private ConcurrentHashMap<String, Semaphore> cctDataMap = new ConcurrentHashMap<String, Semaphore>();
	private ConcurrentHashMap<String, RateLimiter> qpsDataMap = new ConcurrentHashMap<String, RateLimiter>();

	public static void main(String[] args) {
		FlowControlContext fcc = new FlowControlContext();
		Set<FlowControlRule> data=new HashSet<FlowControlRule>();
		fcc.add(data);
	}
	
	public void add(Set<FlowControlRule> data) {
		if(data==null||data.isEmpty()){
			throw new RuntimeException("data不能为空, data="+data);
		}
		
		for (FlowControlRule flowControlRule : data) {
			flowControlKeyDatas.put(flowControlRule, flowControlRule.getFlowControlKey().split(FC_SEQ));
		}
	}

	public <REQ, RES> void check(Map<String, String> data, FlowControlHandler<REQ, RES> handler) {
		for (Map.Entry<FlowControlRule, String[]> entry : flowControlKeyDatas.entrySet()) {
			FlowControlRule fcr=entry.getKey();
			String fcKeyVals="";
			
			//$NON-NLS-计算限流KEY值$
			String[] fcKeys=entry.getValue();
			for (int no=0;no<fcKeys.length;no++) {
				fcKeyVals+=data.get(fcKeys[no]);
				if(no<fcKeys.length-1){
					fcKeyVals+=FC_SEQ;
				}
			}
			
			//$NON-NLS-并发控制$
			Semaphore semaphore = cctDataMap.get(fcKeyVals);
			if(semaphore==null){
				if(fcr.getCctNum()>0){
					semaphore=new Semaphore(fcr.getCctNum(), CCT_FAIL);
					cctDataMap.put(fcKeyVals, semaphore);
				}
			}
			if(semaphore!=null){//并发模式开启了
				boolean cctResult=false;
				try {
					cctResult = semaphore.tryAcquire(fcr.getCctRryTimeout(), TimeUnit.MILLISECONDS);
					if(cctResult){
						try {
							//TODO
							
						} catch (Exception e) {
							throw e;
						}
					}
				} catch (InterruptedException e) {//并发获取异常
					e.printStackTrace();
				} finally {
					if(cctResult){//如果获取成功,则必须释放
						semaphore.release();
					}
				}
			}
		}
	}
	
	public <REQ, RES> void name(String fcKeyVals, FlowControlRule fcr, FlowControlHandler<REQ, RES> handler) {
		//$NON-NLS-QPS限流异常$
		RateLimiter rateLimiter=qpsDataMap.get(fcKeyVals);
		if(rateLimiter==null){
			if(fcr.getQpsNum()>0){//QPS限流已开启了
				if(fcr.getWarmupPeriod()>0){//预热模式
					rateLimiter=RateLimiter.create(fcr.getQpsNum(), fcr.getWarmupPeriod(), TimeUnit.MILLISECONDS);
				}else{//非预热模式
					rateLimiter=RateLimiter.create(fcr.getQpsNum());
				}
			}
		}
		if(rateLimiter!=null){
			boolean qpsResult=false;
			try {
				qpsResult = rateLimiter.tryAcquire(fcr.getQpsRryTimeout(), TimeUnit.MILLISECONDS);
				if(qpsResult){
				} else {
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
