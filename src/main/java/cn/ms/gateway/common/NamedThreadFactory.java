package cn.ms.gateway.common;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程工厂类
 * 
 * @author lry
 */
public class NamedThreadFactory implements ThreadFactory {
	
	private boolean daemon;
	private final ThreadGroup group;
	private final String namePrefix;
	private final AtomicInteger threadNumber = new AtomicInteger(1);
    
    public NamedThreadFactory(String name, boolean daemon) {
    	this(name);
    	this.daemon = daemon;
    }
    
    public NamedThreadFactory(String name) {
        SecurityManager s = System.getSecurityManager();
        group = (s != null)? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
        namePrefix = "gateway-pool-" + name + "-thread-";
        daemon = Thread.currentThread().isDaemon();
    }
    
    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
        t.setDaemon(daemon);
        
        if (t.getPriority() != Thread.NORM_PRIORITY){
        	t.setPriority(Thread.NORM_PRIORITY);
        }
        
        return t;
    }
    
}