package com.dex.midi.event;

import com.dex.midi.view.DecoratedView;

public interface DecoratedViewEventListener<T> {

	public void decoratedViewChanged(DecoratedView<T> v);
}
