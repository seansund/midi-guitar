package com.dex.midi.event;

import com.dex.midi.view.DecoratedView;

import java.util.Collection;

public interface DecoratedViewEventProducer<T> {

	void mergeProducers(DecoratedViewEventProducer<T> that);

	void fireDecoratedViewEvent(DecoratedView<T> v);
}
