package com.dex.midi.view;

import com.dex.midi.event.DecoratedViewEventProducer;
import com.dex.midi.event.MidiControlEventProducer;
import com.dex.midi.event.MidiEventProducer;

import java.util.Collection;

public interface MidiGuitarPlugin<T> {
	
	// display values
	
	public String getLabel();
	
	public Collection<T> getControlComponents();
	
	public Collection<T> getDisplayComponents();
	
	public FretboardViewFactory getFretboardViewFactory();
	
	public void setFretboardViewFactory(FretboardViewFactory factory);
	
	public DecoratedViewFactory<T> getDecoratedViewFactory();
	
	public void setDecoratedViewFactory(DecoratedViewFactory<T> dFactory);
	
	// event producers
	
	public MidiEventProducer getMidiEventProducer();
	
	public FretboardViewEventProducer getFretboardViewEventProducer();
	
	public void setFretboardViewEventProducer(FretboardViewEventProducer p);
	
	public MidiControlEventProducer getMidiControlEventProducer();
	
	public void setMidiControlEventProducer(MidiControlEventProducer p);

	public DecoratedViewEventProducer<T> getDecoratedViewEventProducer();
	
	public void setDecoratedViewEventProducer(DecoratedViewEventProducer<T> p);
	
	public void init();
	
	public void close();

}
