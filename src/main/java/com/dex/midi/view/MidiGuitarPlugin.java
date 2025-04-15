package com.dex.midi.view;

import com.dex.midi.event.DecoratedViewEventProducer;
import com.dex.midi.event.MidiControlEventProducer;
import com.dex.midi.event.MidiEventProducer;

import java.util.Collection;

public interface MidiGuitarPlugin<T> extends AutoCloseable {
	
	// display values
	
	String getLabel();
	
	Collection<T> getControlComponents();
	
	Collection<T> getDisplayComponents();
	
	FretboardViewFactory getFretboardViewFactory();
	
	void setFretboardViewFactory(FretboardViewFactory factory);
	
	DecoratedViewFactory<T> getDecoratedViewFactory();
	
	void setDecoratedViewFactory(DecoratedViewFactory<T> dFactory);
	
	// event producers
	
	MidiEventProducer getMidiEventProducer();
	
	FretboardViewEventProducer getFretboardViewEventProducer();
	
	void setFretboardViewEventProducer(FretboardViewEventProducer p);
	
	MidiControlEventProducer getMidiControlEventProducer();
	
	void setMidiControlEventProducer(MidiControlEventProducer p);

	DecoratedViewEventProducer<T> getDecoratedViewEventProducer();
	
	void setDecoratedViewEventProducer(DecoratedViewEventProducer<T> p);
	
	void init();
	
}
