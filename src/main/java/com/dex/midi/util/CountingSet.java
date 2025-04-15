package com.dex.midi.util;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CountingSet<E> implements Set<E> {
	
	private final Map<E, Integer> countMap = new LinkedHashMap<>(20);
	private final List<E> list = new ArrayList<>(20);
	
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

	public static <T> Collector<T, ?, Set<T>> collector() {
		return new Collector<T, CountingSet<T>, Set<T>>() {
			@Override
			public Supplier<CountingSet<T>> supplier() {
				return CountingSet::new;
			}

			@Override
			public BiConsumer<CountingSet<T>, T> accumulator() {
				return CountingSet::add;
			}

			@Override
			public BinaryOperator<CountingSet<T>> combiner() {
				return (set1, set2) -> {
					set1.addAll(set2);
					return set1;
				};
			}

			@Override
			public Function<CountingSet<T>, Set<T>> finisher() {
				return CountingSet::new;
			}

			@Override
			public Set<Characteristics> characteristics() {
				return Set.of(Characteristics.UNORDERED);
			}
		};
	}


	@Override
	public boolean add(E val) {

		list.add(val);

		final Integer count = countMap.get(val);
		if (count == null) {
			countMap.put(val, 1);
			return true;
		} else {
			countMap.put(val, count + 1);
			return count == 0;
		}
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
			if (count > 1) {
				count = count - 1;
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
