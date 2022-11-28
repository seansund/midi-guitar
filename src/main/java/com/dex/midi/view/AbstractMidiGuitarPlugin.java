package com.dex.midi.view;

import com.dex.midi.Driver;
import com.dex.midi.event.*;
import com.dex.midi.util.SimpleLogger;

import javax.sound.midi.MidiUnavailableException;
import java.util.Collection;
import java.util.logging.Level;

public abstract class AbstractMidiGuitarPlugin<T> implements MidiGuitarPlugin<T> {
	
	private String label;
	protected FretboardViewFactory factory;
	protected DecoratedViewFactory<T> dFactory;
	
	private Driver driver = null;
	private FretboardViewEventProducer fvep = null;
	private MidiControlEventProducer mcep = null;
	private DecoratedViewEventProducer<T> dvp = null;
	
	private boolean initialized = false;
	
	public AbstractMidiGuitarPlugin(String label) {
		this.label = label;
		
		driver = Driver.getInstance();
		try {
			driver.startup();
		} catch (MidiUnavailableException e) {
			SimpleLogger.log(Level.WARNING, this, SimpleLogger.CONSTRUCTOR, "Exception starting up the midi driver", e);
		} catch (MidiEventException e) {
			SimpleLogger.log(Level.WARNING, this, SimpleLogger.CONSTRUCTOR, "Exception handling midi events", e);
		}
	}
	
	@Override
	public Collection<T> getControlComponents() {
		
		// Nothing to do
		return null;
	}
	
	@Override
	public Collection<T> getDisplayComponents() {
		
		// Nothing to do
		return null;
	}
	
	@Override
	public FretboardViewFactory getFretboardViewFactory() {
		return factory;
	}
	
	@Override
	public void setFretboardViewFactory(FretboardViewFactory factory) {
		this.factory = factory;
	}
	
	@Override
	public DecoratedViewFactory<T> getDecoratedViewFactory() {
		return dFactory;
	}
	
	@Override
	public void setDecoratedViewFactory(DecoratedViewFactory<T> dFactory) {
		this.dFactory = dFactory;
	}
	
	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public ListenerMidiEventProducer getMidiEventProducer() {
		return SimpleMidiEventProducer.getInstance();
	}
	
	@Override
	public FretboardViewEventProducer getFretboardViewEventProducer() {
		if (fvep == null) {
			fvep = new SimpleFretboardViewEventProducer();
		}
		return fvep;
	}
	
	@Override
	public void setFretboardViewEventProducer(FretboardViewEventProducer p) {
		if (fvep != null && p != null) {
			p.mergeProducers(fvep);
		}
		
		fvep = p;
	}
	
	@Override
	public MidiControlEventProducer getMidiControlEventProducer() {
		if (mcep == null) {
			mcep = new SimpleMidiControlEventProducer();
		}
		return mcep;
	}
	
	@Override
	public void setMidiControlEventProducer(MidiControlEventProducer p) {
		if (mcep != null && p != null) {
			p.mergeProducers(mcep);
		}
		mcep = p;
	}

	@Override
	public DecoratedViewEventProducer<T> getDecoratedViewEventProducer() {
		if (dvp == null) {
			dvp = new SimpleDecoratedViewEventProducer<T>();
		}
		return dvp;
	}

	@Override
	public void setDecoratedViewEventProducer(DecoratedViewEventProducer<T> p) {
		if (dvp != null && p != null) {
			p.mergeProducers(dvp);
		}
		dvp = p;
	}
	
	@Override
	public final void init() {
		if (!initialized) {
			initialized = true;
			
			initInternal();
		}
	}
	
	protected void initInternal() {
		
	}
	
	@Override
	public void close() {
		driver.close();
	}
}
