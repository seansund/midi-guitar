package com.dex.midi.event;

import com.dex.midi.view.DecoratedView;

public class SimpleDecoratedViewEventProducer<T> extends AbstractEventProducer<DecoratedViewEventListener<T>> 
		implements DecoratedViewEventProducer<T> {

	@Override
	public void addDecoratedViewEventListener(DecoratedViewEventListener<T> l) {
		super.addListener(l);
	}

	@Override
	public void removeDecoratedViewEventListener(DecoratedViewEventListener<T> l) {
		super.removeListener(l);
	}
	
	@Override
	public void fireDecoratedViewEvent(DecoratedView<T> v) {
		for (DecoratedViewEventListener<T> l : iterate()) {
			l.decoratedViewChanged(v);
		}
	}
	
	@Override
	public void mergeProducers(DecoratedViewEventProducer<T> p) {
		addAllListeners(p.getListeners());
	}

}
