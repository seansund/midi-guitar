package com.dex.midi.event;

import com.dex.midi.view.DecoratedView;

import java.util.Collection;

public interface DecoratedViewEventProducer<T> {

	public void addDecoratedViewEventListener(DecoratedViewEventListener<T> l);
	
	public void removeDecoratedViewEventListener(DecoratedViewEventListener<T> l);
	
	public void mergeProducers(DecoratedViewEventProducer<T> that);
	
	public Collection<DecoratedViewEventListener<T>> getListeners();
	
	public void fireDecoratedViewEvent(DecoratedView<T> v);
}
