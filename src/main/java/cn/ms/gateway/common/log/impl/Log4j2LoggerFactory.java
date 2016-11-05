package cn.ms.gateway.common.log.impl;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import cn.ms.gateway.common.log.Logger;
import cn.ms.gateway.common.log.LoggerFactory.InternalLoggerFactory;

public class Log4j2LoggerFactory implements InternalLoggerFactory {
	
	public Logger getLogger(Class<?> clazz) {
		return new Log4jLogger(clazz);
	}
	
	public Logger getLogger(String name) {
		return new Log4jLogger(name);
	}
}

class Log4jLogger extends Logger { 
	
	private org.apache.logging.log4j.Logger log;
	
	Log4jLogger(Class<?> clazz) {
		log = LogManager.getLogger(clazz);
	}
	
	Log4jLogger(String name) {
		log = LogManager.getLogger(name);
	}
	
	public void debug(String format, Object... args){
		String msg = String.format(format, args);
		log.log(Level.DEBUG, msg);
	} 
	
	public void info(String format, Object... args){
		String msg = String.format(format, args);
		log.log(Level.INFO, msg);
	}
	
	public void warn(String format, Object... args){
		String msg = String.format(format, args);
		log.log(Level.WARN, msg);
	}
	
	public void error(String format, Object... args){
		String msg = String.format(format, args);
		log.log(Level.ERROR, msg);
	}
	
	
	public void info(String message) {
		log.log(Level.INFO, message);
	}
	
	public void info(String message, Throwable t) {
		log.log(Level.INFO, message, t);
	}
	
	public void debug(String message) {
		log.log(Level.DEBUG, message);
	}
	
	public void debug(String message, Throwable t) {
		log.log(Level.DEBUG, message, t);
	}
	
	public void warn(String message) {
		log.log(Level.WARN, message);
	}
	
	public void warn(String message, Throwable t) {
		log.log(Level.WARN, message, t);
	}
	
	public void error(String message) {
		log.log(Level.ERROR, message);
	}
	
	public void error(String message, Throwable t) {
		log.log(Level.ERROR, message, t);
	}
	
	public void fatal(String message) {
		log.log(Level.FATAL, message);
	}
	
	public void fatal(String message, Throwable t) {
		log.log(Level.FATAL, message, t);
	}
	
	public boolean isDebugEnabled() {
		return log.isDebugEnabled();
	}
	
	public boolean isInfoEnabled() {
		return log.isInfoEnabled();
	}
	
	public boolean isWarnEnabled() {
		return log.isEnabled(Level.WARN);
	}
	
	public boolean isErrorEnabled() {
		return log.isEnabled(Level.ERROR);
	}
	
	public boolean isFatalEnabled() {
		return log.isEnabled(Level.FATAL);
	}

	@Override
	public void trace(String message) {
		log.log(Level.TRACE, message);
	}

	@Override
	public void trace(String message, Throwable t) {
		log.log(Level.TRACE, message, t);
	}

	@Override
	public boolean isTraceEnabled() { 
		return log.isTraceEnabled();
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