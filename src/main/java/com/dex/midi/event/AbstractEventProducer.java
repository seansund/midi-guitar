package com.dex.midi.event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class AbstractEventProducer<T> {
	
	private final List<T> listeners = new ArrayList<>(5);
	
	protected void addListener(T l) {
		if (l != null && !listeners.contains(l)) {
			listeners.add(l);
		}
	}
	
	protected void addAllListeners(Collection<T> c) {
		if (c != null && c != listeners) {
			listeners.addAll(c);
		}
	}
	
	protected void removeListener(T l) {
		listeners.remove(l);
	}
	
	public Collection<T> getListeners() {
		return listeners;
	}
	
	protected Iterable<T> iterate() {
		return listeners;
	}
}
