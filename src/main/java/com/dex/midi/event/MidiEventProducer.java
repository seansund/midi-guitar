package com.dex.midi.event;

import java.util.Collection;

public interface MidiEventProducer {

	public void mergeProducers(MidiEventProducer that);

	public void fireNoteOn(PitchEvent e);
	
	public void fireNoteOff(PitchEvent e);
	
	public void firePitchBend(PitchBendEvent e);
	
	public void close();
}
