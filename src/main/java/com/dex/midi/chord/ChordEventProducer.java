package com.dex.midi.chord;

import java.util.List;

public interface ChordEventProducer extends AutoCloseable {

	void fireChordChange(List<Chord> chords);

}
