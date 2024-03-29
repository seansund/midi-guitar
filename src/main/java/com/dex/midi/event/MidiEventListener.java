package com.dex.midi.event;


import com.dex.midi.model.GuitarPositions;

public interface MidiEventListener {
	
	public void noteOn(PitchEvent e);
	
	public void noteOff(PitchEvent e);
	
	public void pitchBend(PitchBendEvent e);

	public void guitarPositions(GuitarPositions positions);
	
	public void close();
}
