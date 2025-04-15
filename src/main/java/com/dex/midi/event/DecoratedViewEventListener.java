package com.dex.midi.event;

import com.dex.midi.view.DecoratedView;

public interface DecoratedViewEventListener<T> {

	void decoratedViewChanged(DecoratedView<T> v);
}
