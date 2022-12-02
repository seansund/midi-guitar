package com.dex.midi.view;

import java.util.Arrays;
import java.util.List;

public class CompoundDecorator<T> implements Decorator<T> {
	
	private Decorator<T> defaultDecorator = null;
	private List<? extends Decorator<T>> children = null;
	
	public CompoundDecorator(Decorator<T> defaultDecorator, Decorator<T>... dArray) {
		this(defaultDecorator, Arrays.asList(dArray));
	}
	
	public CompoundDecorator(Decorator<T> defaultDecorator, List<? extends Decorator<T>> children) {
		this.defaultDecorator = defaultDecorator;
		this.children = children;
	}

	@Override
	public void decorate(T c) {
		this.defaultDecorator.decorate(c);

		children.forEach((Decorator<T> d) -> d.decorate(c));
	}
	
}
