package com.impalah.JDoomK1.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class LogUtils {
	public static <T> void logInfo(String message, Class<T> javaClass, Object[] object) {
		final Logger log = LoggerFactory.getLogger(javaClass);
		
		log.info(message, object);
	}
	
	public static <T> void logError(String message, Class<T> javaClass, Object[] object) {
		final Logger log = LoggerFactory.getLogger(javaClass);
		
		log.error(message, object);
	}
	
	public static <T> void logWarn(String message, Class<T> javaClass, Object[] object) {
		final Logger log = LoggerFactory.getLogger(javaClass);
		
		log.warn(message, object);
	}
}
