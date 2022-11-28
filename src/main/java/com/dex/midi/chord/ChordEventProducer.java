package com.dex.midi.chord;

import java.util.List;

public interface ChordEventProducer {

	public void fireChordChange(List<Chord> chords);
}
