package com.dex.midi.view;

import com.dex.midi.event.AbstractEventProducer;
import com.dex.midi.util.SimpleLogger;

import java.util.logging.Level;

public class SimpleFretboardViewEventProducer extends AbstractEventProducer<FretboardViewEventListener>
		implements FretboardViewEventProducer {

	@Override
	public void addFretboardViewEventListener(FretboardViewEventListener l) {
		addListener(l);
	}
	
	@Override
	public void removeFretboardViewEventListener(FretboardViewEventListener l) {
		removeListener(l);
	}

	@Override
	public void fireViewChanged(String viewId) {
		for (FretboardViewEventListener l : iterate()) {
			l.viewChanged(viewId);
		}
	}

	@Override
	public void fireViewFactoryChanged(FretboardViewFactory factory) {
		final String method = "fireViewFactoryChanged";
		
		SimpleLogger.log(Level.INFO, this, method, "View factory changed");
		for (FretboardViewEventListener l : iterate()) {
			l.viewFactoryChanged(factory);
		}
	}

	@Override
	public void mergeProducers(FretboardViewEventProducer that) {
		addAllListeners(that.getListeners());
	}
}
