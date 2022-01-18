package com.dex.midi.event;

import com.dex.midi.util.SimpleLogger;

import java.util.logging.Level;


public class SimpleMidiEventProducer extends AbstractEventProducer<MidiEventListener> implements
		MidiEventProducer {
	
	private static MidiEventProducer instance = null;
	
	protected SimpleMidiEventProducer() {
		super();
	}
	
	public static MidiEventProducer getInstance() {
		if (instance == null) {
			instance = new SimpleMidiEventProducer();
		}
		return instance;
	}

	@Override
	public void addMidiEventListener(MidiEventListener l) {
		final String method = "addMidiEventListener";
		if (l == null) {
			SimpleLogger.log(Level.WARNING, this, method, "Null MidiEventListener");
		}
		super.addListener(l);
	}

	@Override
	public void removeMidiEventListener(MidiEventListener l) {
		super.removeListener(l);
	}
	
	@Override
	public void fireNoteOn(PitchEvent e) {
		for (MidiEventListener l : iterate()) {
			l.noteOn(e);
		}
	}
	
	@Override
	public void fireNoteOff(PitchEvent e) {
		for (MidiEventListener l : iterate()) {
			l.noteOff(e);
		}
	}
	
	@Override
	public void firePitchBend(PitchBendEvent e) {
		for (MidiEventListener l : iterate()) {
			l.pitchBend(e);
		}
	}

	@Override
	public void close() {
		for (MidiEventListener l : iterate()) {
			l.close();
		}
	}

	@Override
	public void mergeProducers(MidiEventProducer that) {
		addAllListeners(that.getListeners());
	}

}
