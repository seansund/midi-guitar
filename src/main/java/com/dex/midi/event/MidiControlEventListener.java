package com.dex.midi.event;

import com.dex.midi.model.MusicalKey;
import com.dex.midi.model.StringOffset;

public interface MidiControlEventListener {

	void keyChanged(MusicalKey key);
	
	void stringOffsetChanged(StringOffset offset);
}
