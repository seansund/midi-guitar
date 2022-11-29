package com.dex.midi.chord;

import com.dex.midi.event.AbstractEventProducer;

import java.util.List;

public class SimpleChordEventProducer extends AbstractEventProducer<ChordEventListener> implements ListenerChordEventProducer {

	@Override
	public void addChordEventListener(ChordEventListener l) {
		addListener(l);
	}

	@Override
	public void removeChordEventListener(ChordEventListener l) {
		removeListener(l);
	}
	
	@Override
	public void fireChordChange(List<Chord> chords) {
		for (ChordEventListener l : iterate()) {
			l.chordChange(chords);
		}
	}

}
