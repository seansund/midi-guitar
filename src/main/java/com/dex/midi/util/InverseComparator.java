package com.dex.midi.util;

import java.util.Comparator;

public class InverseComparator<T> implements Comparator<T> {

	private Comparator<T> c;
	
	public InverseComparator(Comparator<T> c) {
		this.c = c;
	}
	
	@Override
	public int compare(T o1, T o2) {
		return c.compare(o1, o2) * -1;
	}

}
