package com.dex.midi.util;

import java.util.*;

public class OccuranceHashSet<E> implements Set<E> {

	private Map<E, Occurance<E>> map = new HashMap<E, Occurance<E>>();
	
	public static class Occurance<T> {
		
		private T value;
		private int count = 1;
		
		public Occurance(T value) {
			this.value = value;
		}
		
		public int add() {
			return ++count;
		}
		
		public int subtract() {
			return --count;
		}
		
		public T getValue() {
			return value;
		}
		
		public int getCount() {
			return count;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((value == null) ? 0 : value.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			
			if (obj instanceof Occurance) {
				@SuppressWarnings("rawtypes")
				Occurance other = (Occurance) obj;
				if (value == null) {
					if (other.value != null) {
						return false;
					}
				} else if (!value.equals(other.value)) {
					return false;
				}
			} else {
				if (value == null) {
					if (obj != null) {
						return false;
					}
				} else if (!value.equals(obj)) {
					return false;
				}
			}
			
			return true;
		}
		
		
	}

	@Override
	public boolean add(E key) {
		Occurance<E> o = map.get(key);
		
		if (o == null) {
			map.put(key, new Occurance<E>(key));
		} else {
			o.add();
		}
		
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		
		for (E key : c) {
			this.add(key);
		}
		
		return true;
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public boolean contains(Object key) {
		return map.containsKey(key);
	}

	@Override
	public boolean containsAll(Collection<?> keys) {
		boolean result = true;
		
		if (keys.isEmpty()) {
			result = false;
		} else {
			for (Object key : keys) {
				result &= this.contains(key);
			}
		}
		
		return result;
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public Iterator<E> iterator() {
		return map.keySet().iterator();
	}

	@Override
	public boolean remove(Object key) {
		boolean result = false;
		
		Occurance<E> o = map.get(key);
		if (o != null) {
			o.subtract();
			
			if (o.getCount() == 0) {
				map.remove(key);
			}
			
			result = true;
		}
		
		return result;
	}

	@Override
	public boolean removeAll(Collection<?> keys) {
		boolean result = true;
		
		for (Object key : keys) {
			result &= this.remove(key);
		}
		
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean retainAll(Collection<?> keys) {
		Map<E, Occurance<E>> temp = new HashMap<E, Occurance<E>>();
		
		for (Object key : keys) {
			temp.put((E)key, map.get(key));
		}
		map = temp;
		
		return false;
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public Object[] toArray() {
		return map.keySet().toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return map.keySet().toArray(a);
	}
	
	public int count(E key) {
		Occurance<E> o = map.get(key);
		
		int count = 0;
		if (o != null) {
			count = o.getCount();
		}
		
		return count;
	}
}
