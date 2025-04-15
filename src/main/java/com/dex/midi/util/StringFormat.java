package com.dex.midi.util;

public class StringFormat {
	
	public static String asString(Object s, int length) {
		return StringFormat.asString(s.toString(), length, true);
	}
	
	public static String asString(Object s, int length, boolean leftAlign) {
		return StringFormat.asString(s, length, leftAlign);
	}
	
	public static String asString(String s, int length) {
		return StringFormat.asString(s, length, true);
	}
	
	public static String asString(String s, int length, boolean leftAlign) {
		StringBuffer result = new StringBuffer();
		
		if (s.length() < length) {
			if (leftAlign) {
				result.append(s);
			}

            result.append(StringUtil.SPACE.repeat(length - s.length()));
			
			if (!leftAlign) {
				result.append(s);
			}
		} else if (s.length() == length) {
			result.append(s);
		} else {
			result.append(s, 0, length);
		}
		
		return result.toString();
	}
	
	public static String asString(int val, int length) {
		return StringFormat.asString(String.valueOf(val), length, false);
	}
	
	public static String asString(int val, int length, boolean leftAlign) {
		return StringFormat.asString(String.valueOf(val), length, leftAlign);
	}

}
