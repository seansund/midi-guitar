package com.dex.midi.event;

import com.dex.midi.model.MusicalKey;
import com.dex.midi.model.StringOffset;

import java.util.Collection;

public interface MidiControlEventProducer {

	public void mergeProducers(MidiControlEventProducer that);

	public void fireKeyChanged(MusicalKey key);
	
	public void fireStringOffsetChanged(StringOffset stringOffset);
}
