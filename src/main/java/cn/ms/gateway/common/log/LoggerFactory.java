package cn.ms.gateway.common.log;


public class LoggerFactory {
	
	public static interface InternalLoggerFactory {
		Logger getLogger(Class<?> clazz);
		Logger getLogger(String name);
	}
	
	private static InternalLoggerFactory factory;
	static {
		initDefaultFactory();
	}
	
	public static void setLoggerFactory(InternalLoggerFactory factory) {
		if (factory != null) {
			LoggerFactory.factory = factory;
		}
	}
	
	public static Logger getLogger(Class<?> clazz) {
		return factory.getLogger(clazz);
	}
	
	public static Logger getLogger(String name) {
		return factory.getLogger(name);
	}
	
	
	public static void initDefaultFactory() {
		if (factory != null){
			return ;
		}
		
		try {
			Class.forName("org.apache.logging.log4j.Logger");
			String defaultFactory = String.format("%s.impl.Log4j2LoggerFactory", Logger.class.getPackage().getName());
			Class<?> factoryClass = Class.forName(defaultFactory);
			factory = (InternalLoggerFactory)factoryClass.newInstance();
		} catch (Exception e) {  
			e.printStackTrace();
		}
		
	} 

}
