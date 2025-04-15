package com.dex.midi.event;

import com.dex.midi.model.MusicalKey;
import com.dex.midi.model.StringOffset;

public interface MidiControlEventProducer {

	void mergeProducers(MidiControlEventProducer that);

	void fireKeyChanged(MusicalKey key);
	
	void fireStringOffsetChanged(StringOffset stringOffset);
}
