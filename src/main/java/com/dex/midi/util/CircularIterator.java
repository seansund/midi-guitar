package com.dex.midi.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class CircularIterator<E> implements Iterator<E> {

	private List<E> list;
	private int startIndex;
	private int currentIndex;
	
	public CircularIterator(List<E> list, E start) {
		super();
		
		this.list = list;
		this.startIndex = this.currentIndex = this.list.indexOf(start);
	}
	
	public CircularIterator(E[] list, E start) {
		super();
		
		this.list = Arrays.asList(list);
		this.startIndex = this.currentIndex = this.list.indexOf(start);
	}
	
	public void reset() {
		currentIndex = startIndex;
	}
	
	public int getStartIndex() {
		return startIndex;
	}
	
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	
	public void setStartIndex(E start) {
		this.startIndex = this.list.indexOf(start);
	}
	
	@Override
	public boolean hasNext() {
		return true;
	}

	@Override
	public E next() {
		
		// if we are at the end of the list, move it back to the front
		if (currentIndex >= list.size()) {
			currentIndex = 0;
		}
		
		return list.get(currentIndex++);
	}

	@Override
	public void remove() {
		// nothing to do
	}

}
