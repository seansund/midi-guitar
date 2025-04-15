package com.dex.midi.event;


import com.dex.midi.model.GuitarPositions;

public interface MidiEventListener extends AutoCloseable {
	
	void noteOn(PitchEvent e);
	
	void noteOff(PitchEvent e);
	
	void pitchBend(PitchBendEvent e);

	void guitarPositions(GuitarPositions positions);
	
}
