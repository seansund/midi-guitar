package com.dex.midi.event;

import com.dex.midi.model.MusicalKey;
import com.dex.midi.model.StringOffset;

public interface MidiControlEventListener {

	public void keyChanged(MusicalKey key);
	
	public void stringOffsetChanged(StringOffset offset);
}
