package com.dex.midi.event;


public interface MidiEventListener {
	
	public void noteOn(PitchEvent e);
	
	public void noteOff(PitchEvent e);
	
	public void pitchBend(PitchBendEvent e);
	
	public void close();
}
