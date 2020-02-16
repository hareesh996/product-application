package com.hari.microservices.util;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

public final class Util {
	
	public static String concat(Object... logs) {
		return StringUtils.join(logs);
	}
		
	public static <T> boolean isEmpty(T  value) {
		return ObjectUtils.isEmpty(value);
	}
	
}
