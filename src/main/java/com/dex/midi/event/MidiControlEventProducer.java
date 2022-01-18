package com.dex.midi.event;

import com.dex.midi.model.MusicalKey;

import java.util.Collection;

public interface MidiControlEventProducer {

	public void addKeyChangeEventListener(MidiControlEventListener l);
	
	public void removeKeyChangeEventListener(MidiControlEventListener l);
	
	public void mergeProducers(MidiControlEventProducer that);
	
	public Collection<MidiControlEventListener> getListeners();
	
	public void fireKeyChanged(MusicalKey key);
	
	public void fireStringOffsetChanged(int stringIndex, int stringOffset);
}
