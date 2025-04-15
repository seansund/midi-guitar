package com.dex.midi.event;

import com.dex.midi.model.GuitarPositions;

public interface MidiEventProducer extends AutoCloseable {

	void mergeProducers(MidiEventProducer that);

	void fireNoteOn(PitchEvent e);
	
	void fireNoteOff(PitchEvent e);
	
	void firePitchBend(PitchBendEvent e);

	void fireGuitarPositions(GuitarPositions positions);

}
