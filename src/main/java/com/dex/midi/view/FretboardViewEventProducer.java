package com.dex.midi.view;

import java.util.Collection;


public interface FretboardViewEventProducer {

	void addFretboardViewEventListener(FretboardViewEventListener l);
	
	void removeFretboardViewEventListener(FretboardViewEventListener l);
	
	void mergeProducers(FretboardViewEventProducer that);
	
	Collection<FretboardViewEventListener> getListeners();
	
	void fireViewChanged(String viewId);
	
	void fireViewFactoryChanged(FretboardViewFactory factory);
}
