package com.dex.midi.chord;

import java.util.List;

public interface ChordEventListener {

	public void chordChange(List<Chord> chords);
}
