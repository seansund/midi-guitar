package com.dex.midi.event;


import com.dex.midi.model.GuitarPosition;

public interface MidiEventListener {
	
	public void noteOn(PitchEvent e);
	
	public void noteOff(PitchEvent e);
	
	public void pitchBend(PitchBendEvent e);

	public void guitarPositions(GuitarPosition[] positions);
	
	public void close();
}
