package cn.ms.gateway.common.log.impl;

import java.util.logging.Level;

import cn.ms.gateway.common.log.Logger;
import cn.ms.gateway.common.log.LoggerFactory.InternalLoggerFactory;

public class JdkLoggerFactory implements InternalLoggerFactory {
	
	public Logger getLogger(Class<?> clazz) {
		return new JdkLogger(clazz);
	}
	
	public Logger getLogger(String name) {
		return new JdkLogger(name);
	}
}


class JdkLogger extends Logger { 
	
	private java.util.logging.Logger log;
	private String clazzName;
	
	JdkLogger(Class<?> clazz) {
		log = java.util.logging.Logger.getLogger(clazz.getName());
		clazzName = clazz.getName();
	}
	
	JdkLogger(String name) {
		log = java.util.logging.Logger.getLogger(name);
		clazzName = name;
	}
	
	public void debug(String message) {
		log.logp(Level.FINE, clazzName, Thread.currentThread().getStackTrace()[1].getMethodName(), message);
	}
	
	public void debug(String message,  Throwable t) {
		log.logp(Level.FINE, clazzName, Thread.currentThread().getStackTrace()[1].getMethodName(), message, t);
	}
	
	public void info(String message) {
		log.logp(Level.INFO, clazzName, Thread.currentThread().getStackTrace()[1].getMethodName(), message);
	}
	
	public void info(String message, Throwable t) {
		log.logp(Level.INFO, clazzName, Thread.currentThread().getStackTrace()[1].getMethodName(), message, t);
	}
	
	public void warn(String message) {
		log.logp(Level.WARNING, clazzName, Thread.currentThread().getStackTrace()[1].getMethodName(), message);
	}
	
	public void warn(String message, Throwable t) {
		log.logp(Level.WARNING, clazzName, Thread.currentThread().getStackTrace()[1].getMethodName(), message, t);
	}
	
	public void error(String message) {
		log.logp(Level.SEVERE, clazzName, Thread.currentThread().getStackTrace()[1].getMethodName(), message);
	}
	
	public void error(String message, Throwable t) {
		log.logp(Level.SEVERE, clazzName, Thread.currentThread().getStackTrace()[1].getMethodName(), message, t);
	}
	 
	public void fatal(String message) {
		log.logp(Level.SEVERE, clazzName, Thread.currentThread().getStackTrace()[1].getMethodName(), message);
	}
 
	public void fatal(String message, Throwable t) {
		log.logp(Level.SEVERE, clazzName, Thread.currentThread().getStackTrace()[1].getMethodName(), message, t);
	}
	
	public boolean isDebugEnabled() {
		return log.isLoggable(Level.FINE);
	}
	
	public boolean isInfoEnabled() {
		return log.isLoggable(Level.INFO);
	}
	
	public boolean isWarnEnabled() {
		return log.isLoggable(Level.WARNING);
	}
	
	public boolean isErrorEnabled() {
		return log.isLoggable(Level.SEVERE);
	}
	
	public boolean isFatalEnabled() {
		return log.isLoggable(Level.SEVERE);
	}
	
	@Override
	public boolean isTraceEnabled() {
		return log.isLoggable(Level.FINEST); //TODO
	}

	@Override
	public void trace(String message) { 
		log.logp(Level.FINEST, clazzName, Thread.currentThread().getStackTrace()[1].getMethodName(), message);
	}

	@Override
	public void trace(String message, Throwable t) { 
		log.logp(Level.FINEST, clazzName, Thread.currentThread().getStackTrace()[1].getMethodName(), message, t);
	}

	@Override
	public void debug(Throwable t, String format, Object... args) {
		this.debug(String.format(format, args), t);
	}

	@Override
	public void info(Throwable t, String format, Object... args) {
		this.info(String.format(format, args), t);
	}

	@Override
	public void warn(Throwable t, String format, Object... args) {
		this.warn(String.format(format, args), t);
	}

	@Override
	public void error(Throwable t, String format, Object... args) {
		this.error(String.format(format, args), t);
	}

	@Override
	public void fatal(Throwable t, String format, Object... args) {
		this.fatal(String.format(format, args), t);
	}

	@Override
	public void trace(Throwable t, String format, Object... args) {
		this.trace(String.format(format, args), t);
	}
}