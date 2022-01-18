package com.dex.midi.event;

import java.util.Collection;

public interface MidiEventProducer {

	public void addMidiEventListener(MidiEventListener l);
	
	public void removeMidiEventListener(MidiEventListener l);
	
	public void mergeProducers(MidiEventProducer that);
	
	public Collection<MidiEventListener> getListeners();
	
	public void fireNoteOn(PitchEvent e);
	
	public void fireNoteOff(PitchEvent e);
	
	public void firePitchBend(PitchBendEvent e);
	
	public void close();
}
