package com.dex.midi.event;

import com.dex.midi.model.MusicalKey;

public class SimpleMidiControlEventProducer extends AbstractEventProducer<MidiControlEventListener>
		implements MidiControlEventProducer {

	@Override
	public void addKeyChangeEventListener(MidiControlEventListener l) {
		super.addListener(l);
	}
	
	@Override
	public void removeKeyChangeEventListener(MidiControlEventListener l) {
		super.addListener(l);
	}
	
	@Override
	public void fireKeyChanged(MusicalKey key) {
		for (MidiControlEventListener l : iterate()) {
			l.keyChanged(key);
		}
	}

	@Override
	public void fireStringOffsetChanged(int stringIndex, int stringOffset) {
		for (MidiControlEventListener l : iterate()) {
			l.stringOffsetChanged(stringIndex, stringOffset);
		}
	}
	
	@Override
	public void mergeProducers(MidiControlEventProducer that) {
		addAllListeners(that.getListeners());
	}

}
