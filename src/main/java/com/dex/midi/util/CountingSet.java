package com.dex.midi.util;

import java.util.*;

public class CountingSet<E> implements Set<E> {
	
	private Map<E, Integer> countMap = new LinkedHashMap<E, Integer>(20);
	private List<E> list = new ArrayList<E>(20);
	
	public CountingSet() {
		super();
	}
	
	public CountingSet(Collection<? extends E> c) {
		this();
		
		if (c instanceof CountingSet) {
			@SuppressWarnings("unchecked")
			CountingSet<E> n = (CountingSet<E>)c;
			
			this.countMap.putAll(n.countMap);
			this.list.addAll(n.list);
		} else {
			addAll(c);
		}
	}

	@Override
	public boolean add(E val) {
		boolean result = false;
		
		Integer count = countMap.get(val);
		if (count == null) {
			count = Integer.valueOf(1);
			
			result = true;
		} else {
			result = count.intValue() == 0;
			
			count = Integer.valueOf(count.intValue() + 1);
		}
		countMap.put(val, count);
		list.add(val);
		
		return result;
	}

	@Override
	public boolean addAll(Collection<? extends E> values) {
		boolean result = false;
		
		for (E val : values) {
			result |= add(val);
		}
		
		return result;
	}

	@Override
	public void clear() {
		countMap.clear();
		list.clear();
	}

	@Override
	public boolean contains(Object val) {
		return countMap.containsKey(val);
	}

	@Override
	public boolean containsAll(Collection<?> values) {
		boolean contains = false;
		
		if (values != null && !values.isEmpty()) {
			contains = true;
			
			for (Object val : values) {
				contains &= countMap.containsKey(val);
			}
		}
		
		return contains;
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	@Override
	public Iterator<E> iterator() {
		return countMap.keySet().iterator();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean remove(Object val) {
		boolean result = false;
		
		Integer count = countMap.get(val);
		if (count != null) {
			if (count.intValue() > 1) {
				count = Integer.valueOf(count.intValue() - 1);
			} else {
				count = null;
			}
			result = true;
		}
		
		try {
			countMap.put((E)val, count);
			int pos = list.lastIndexOf(val);
			list.remove(pos);
		} catch (ClassCastException ex) { }
		
		return result;
	}
	
	@Override
	public boolean removeAll(Collection<?> values) {
		boolean result = false;
		
		for (Object val : values) {
			result |= remove(val);
		}
		
		return result;
	}

	@Override
	public boolean retainAll(Collection<?> values) {
		// TODO implement
		
		return false;
	}

	@Override
	public int size() {
		return countMap.size();
	}

	@Override
	public Object[] toArray() {
		return countMap.keySet().toArray();
	}

	@Override
	public <T> T[] toArray(T[] t) {
		return countMap.keySet().toArray(t);
	}

}
