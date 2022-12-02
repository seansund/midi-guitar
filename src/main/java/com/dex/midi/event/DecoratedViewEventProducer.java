package com.dex.midi.event;

import com.dex.midi.view.DecoratedView;

import java.util.Collection;

public interface DecoratedViewEventProducer<T> {

	public void mergeProducers(DecoratedViewEventProducer<T> that);

	public void fireDecoratedViewEvent(DecoratedView<T> v);
}
