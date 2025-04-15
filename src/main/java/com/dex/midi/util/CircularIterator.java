package com.dex.midi.util;

import lombok.Getter;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class CircularIterator<E> implements Iterator<E> {

	final private List<E> list;
	@Getter
    private int startIndex;
	private int currentIndex;

	public CircularIterator(E[] list) {
		this(Arrays.asList(list));
	}

	public CircularIterator(List<E> list) {
		this(list, 0);
	}

	public CircularIterator(List<E> list, E start) {
		this(list, list.indexOf(start));
	}

	public CircularIterator(E[] list, E start) {
		this(Arrays.asList(list), start);
	}

	public CircularIterator(List<E> list, int startIndex) {
		super();

		if (startIndex < 0 || startIndex >= list.size()) {
			throw new ArrayIndexOutOfBoundsException("Start index is out of bounds: " + startIndex);
		}

		this.list = list;
		this.startIndex = this.currentIndex = startIndex;
	}

	public void reset() {
		currentIndex = startIndex;
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
