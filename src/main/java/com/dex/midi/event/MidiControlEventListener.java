package com.dex.midi.event;

import com.dex.midi.model.MusicalKey;

public interface MidiControlEventListener {

	public void keyChanged(MusicalKey key);
	
	public void stringOffsetChanged(int stringIndex, int newOffset);
}
