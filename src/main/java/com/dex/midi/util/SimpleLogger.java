package com.dex.midi.util;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SimpleLogger {
	
	public static final String CONSTRUCTOR = "[constructor]";
	
	private static final String LOGGER_NAME = "MidiLogger";
	
	public static void log(Level level, Object obj, String sourceMethod, String msg) {
		Logger logger = getLogger(obj);
		
		String sourceClass = getSourceClass(obj);
		logger.logp(level, sourceClass, sourceMethod, msg);
	}
	
	public static void log(Level level, Object obj, String sourceMethod, String msg, Object... params) {
		Logger logger = getLogger(obj);
		
		String sourceClass = getSourceClass(obj);
		logger.logp(level, sourceClass, sourceMethod, msg, params);
	}
	
	public static void log(Level level, Object obj, String sourceMethod, String msg, Throwable thrown) {
		Logger logger = getLogger(obj);
		
		String sourceClass = getSourceClass(obj);
		logger.logp(level, sourceClass, sourceMethod, msg, thrown);
	}
	
	protected static Logger getLogger(Object obj) {
		// should this be the class name?
		return Logger.getLogger(LOGGER_NAME);
	}
	
	public static String getSourceClass(Object obj) {
		String sourceClass = StringUtil.EMPTY;
		if (obj instanceof String) {
			sourceClass = (String)obj;
		} else if (obj instanceof Class) {
			sourceClass = ((Class<?>)obj).getSimpleName();
		} else if (obj != null) {
			sourceClass = obj.getClass().getSimpleName();
		}
		return sourceClass;
	}

}
