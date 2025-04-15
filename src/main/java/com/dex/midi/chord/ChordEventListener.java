package com.dex.midi.chord;

import java.util.List;

public interface ChordEventListener extends AutoCloseable {

	void chordChange(List<Chord> chords);
}
