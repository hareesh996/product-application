package com.hari.microservices.util.logger;

import org.slf4j.LoggerFactory;

import com.hari.microservices.util.Util;

public final class Logger {

	private final org.slf4j.Logger logger;

	public Logger(Class<?> clazz) {
		super();
		this.logger = LoggerFactory.getLogger(clazz);
	}

	public static Logger getInstance(Class<?> clazz) {
		return new Logger(clazz);
	}

	public void debug(Throwable throwable, Object... logMessage) {
		this.logger.debug(Util.concat(logMessage), throwable);
	}

	public void debug(Object ...logMessage) {
		this.logger.debug(Util.concat(logMessage));
	}
	
	public void error(Throwable throwable, Object ...logMessage) {
		this.logger.error(Util.concat(logMessage), throwable);
	}

	public void error(Object ...logMessage) {
		this.logger.error(Util.concat(logMessage));
	}
	
	public void warn(Throwable throwable, Object ...logMessage) {
		this.logger.warn(Util.concat(logMessage), throwable);
	}

	public void warn(Object ...logMessage) {
		this.logger.warn(Util.concat(logMessage));
	}
	
	public void info(Throwable throwable, Object ...logMessage) {
		this.logger.info(Util.concat(logMessage), throwable);
	}
	
	public void info(Object ...logMessage) {
		this.logger.warn(Util.concat(logMessage));
	}

}
