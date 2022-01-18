package com.dex.midi.util;

import java.util.Comparator;

public class StringComparator implements Comparator<Object> {

	@Override
	public int compare(Object s1, Object s2) {
		return s1.toString().compareTo(s2.toString());
	}

}
