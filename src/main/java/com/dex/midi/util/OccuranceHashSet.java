package com.dex.midi.util;

import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

public class OccuranceHashSet<E> implements Set<E> {

	private Map<E, Occurance<E>> map = new HashMap<>();
	
	@Getter
    public static class Occurance<T> {
		
		private final T value;
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

        @Override
		public int hashCode() {
			final int prime = 31;

			return Objects.hash(prime, value);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			
			if (obj instanceof @SuppressWarnings("rawtypes") Occurance other) {
                if (value == null) {
                    return other.value == null;
				} else return value.equals(other.value);
			} else {
				if (value == null) {
                    return false;
				} else return value.equals(obj);
			}
        }
		
		
	}

	@Override
	public boolean add(E key) {
		Occurance<E> o = map.get(key);
		
		if (o == null) {
			map.put(key, new Occurance<>(key));
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
	public boolean containsAll(final Collection<?> keys) {

		if (keys.isEmpty()) {
			return false;
		} else {
			return keys.stream().allMatch(this::contains);
		}
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

		Occurance<E> o = map.get(key);
		if (o != null) {
			o.subtract();
			
			if (o.getCount() == 0) {
				map.remove(key);
			}
			
			return true;
		}
		
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> keys) {boolean result = true;
		return keys.stream().allMatch(this::remove);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean retainAll(Collection<?> keys) {

		map = map.entrySet().stream()
				.filter(e -> keys.contains(e.getKey()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

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
	
	public int count(final E key) {
		final Occurance<E> o = map.get(key);
		
		if (o != null) {
			return o.getCount();
		}
		
		return 0;
	}
}
