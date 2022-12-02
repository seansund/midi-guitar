package com.dex.midi.event;

import com.dex.midi.model.GuitarPosition;

import java.util.Collection;

public interface MidiEventProducer {

	public void mergeProducers(MidiEventProducer that);

	public void fireNoteOn(PitchEvent e);
	
	public void fireNoteOff(PitchEvent e);
	
	public void firePitchBend(PitchBendEvent e);

	public void fireGuitarPositions(GuitarPosition[] positions);

	public void close();
}
