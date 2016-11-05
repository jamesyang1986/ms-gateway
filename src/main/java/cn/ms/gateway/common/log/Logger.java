package cn.ms.gateway.common.log;

public abstract class Logger {
	
	public void debug(String format, Object... args){
		debug(String.format(format, args));
	} 
	
	public void info(String format, Object... args){
		info(String.format(format, args));
	}
	
	public void warn(String format, Object... args){
		warn(String.format(format, args));
	}
	
	public void error(String format, Object... args){
		error(String.format(format, args));
	} 
	
	public void trace(String format, Object... args){
		trace(String.format(format, args));
	} 
	
	public abstract void debug(String message);
	public abstract void debug(String message, Throwable t); 
	public abstract void info(String message);
	public abstract void info(String message, Throwable t);
	public abstract void warn(String message);
	public abstract void warn(String message, Throwable t);
	public abstract void error(String message);
	public abstract void error(String message, Throwable t);
	public abstract void fatal(String message);
	public abstract void fatal(String message, Throwable t);
	public abstract void trace(String message);
	public abstract void trace(String message, Throwable t);
	public abstract boolean isDebugEnabled();
	public abstract boolean isInfoEnabled();
	public abstract boolean isWarnEnabled();
	public abstract boolean isErrorEnabled();
	public abstract boolean isFatalEnabled();
	public abstract boolean isTraceEnabled();
	
}

