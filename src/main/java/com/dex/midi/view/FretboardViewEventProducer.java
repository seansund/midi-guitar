package com.dex.midi.view;

import java.util.Collection;


public interface FretboardViewEventProducer {

	public void addFretboardViewEventListener(FretboardViewEventListener l);
	
	public void removeFretboardViewEventListener(FretboardViewEventListener l);
	
	public void mergeProducers(FretboardViewEventProducer that);
	
	public Collection<FretboardViewEventListener> getListeners();
	
	public void fireViewChanged(String viewId);
	
	public void fireViewFactoryChanged(FretboardViewFactory factory);
}
