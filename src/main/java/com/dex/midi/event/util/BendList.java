package com.dex.midi.event.util;

import java.util.*;

public class BendList implements List<BendEntry>, BendValues {
	
	private List<BendEntry> bends;
	
	public BendList() {
		bends = new ArrayList<BendEntry>();
	}
	
	public BendList(List<BendEntry> bends) {
		this.bends = bends;
	}

	@Override
	public double[] getForierValues() {
		// FastForierTransform expects to get x^2 number of values
		// so set the size of the list so that x is the largest
		// int value where x^2 fits within bends.size()
		int size = (int)Math.pow((int)Math.sqrt(bends.size()), 2);
		assert(size <= bends.size());
		
		double[] values = new double[size];
		
		for (int i = 0; i < size; i++) {
			BendEntry e = bends.get(i);
			values[i++] = e.getBendValue();
		}
		
		return values;
	}
	
	@Override
	public void add(int location, BendEntry object) {
		bends.add(location, object);
	}

	@Override
	public boolean add(BendEntry object) {
		return bends.add(object);
	}

	@Override
	public boolean addAll(int location,
			Collection<? extends BendEntry> collection) {
		return bends.addAll(location, collection);
	}

	@Override
	public boolean addAll(Collection<? extends BendEntry> collection) {
		return bends.addAll(collection);
	}

	@Override
	public void clear() {
		bends.clear();
	}

	@Override
	public boolean contains(Object object) {
		return bends.contains(object);
	}

	@Override
	public boolean containsAll(Collection<?> collection) {
		return bends.containsAll(collection);
	}

	@Override
	public boolean equals(Object object) {
		return bends.equals(object);
	}

	@Override
	public BendEntry get(int location) {
		return bends.get(location);
	}

	@Override
	public int hashCode() {
		return bends.hashCode();
	}

	@Override
	public int indexOf(Object object) {
		return bends.indexOf(object);
	}

	@Override
	public boolean isEmpty() {
		return bends.isEmpty();
	}

	@Override
	public Iterator<BendEntry> iterator() {
		return bends.iterator();
	}

	@Override
	public int lastIndexOf(Object object) {
		return bends.lastIndexOf(object);
	}

	@Override
	public ListIterator<BendEntry> listIterator() {
		return bends.listIterator();
	}

	@Override
	public ListIterator<BendEntry> listIterator(int location) {
		return bends.listIterator(location);
	}

	@Override
	public BendEntry remove(int location) {
		return bends.remove(location);
	}

	@Override
	public boolean remove(Object object) {
		return bends.remove(object);
	}

	@Override
	public boolean removeAll(Collection<?> collection) {
		return bends.removeAll(collection);
	}

	@Override
	public boolean retainAll(Collection<?> collection) {
		return bends.retainAll(collection);
	}

	@Override
	public BendEntry set(int location, BendEntry object) {
		return bends.set(location, object);
	}

	@Override
	public int size() {
		return bends.size();
	}

	@Override
	public List<BendEntry> subList(int start, int end) {
		return bends.subList(start, end);
	}

	@Override
	public Object[] toArray() {
		return bends.toArray();
	}

	@Override
	public <T> T[] toArray(T[] array) {
		return bends.toArray(array);
	}
	
}
