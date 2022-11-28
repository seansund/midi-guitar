package com.dex.midi.chord;

import java.util.List;

public interface ChordEventProducer {

	public void addChordEventListener(ChordEventListener l);
	
	public void removeChordEventListener(ChordEventListener l);
	
	public void fireChordChange(List<Chord> chords);
}
