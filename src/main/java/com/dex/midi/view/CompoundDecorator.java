package com.dex.midi.view;

import java.util.Arrays;
import java.util.Objects;
import java.util.List;
import java.util.stream.Collectors;

public class CompoundDecorator<T> implements Decorator<T> {
	
	private final Decorator<T> defaultDecorator;
	private final List<? extends Decorator<T>> children;
	
	public CompoundDecorator(Decorator<T> defaultDecorator, Decorator<T>... dArray) {
		this(defaultDecorator, Arrays.asList(dArray));
	}
	
	public CompoundDecorator(Decorator<T> defaultDecorator, List<? extends Decorator<T>> children) {
		this.defaultDecorator = defaultDecorator != null ? defaultDecorator : new NoopDecorator<>();
		this.children = children != null
				? children.stream().filter(Objects::nonNull).collect(Collectors.toList())
				: List.of();
	}

	@Override
	public void decorate(T c) {
		defaultDecorator.decorate(c);

		children.forEach((Decorator<T> d) -> d.decorate(c));
	}
}

class NoopDecorator<T> implements Decorator<T> {
	public void decorate(T d) {
	}
}
