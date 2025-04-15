package com.dex.midi;

import com.dex.midi.chord.*;
import com.dex.midi.event.MidiEventListenerSource;
import com.dex.midi.util.SimpleLogger;

import java.util.List;
import java.util.logging.Level;

@Deprecated
public class ChordDriver extends com.dex.midi.Driver implements ChordEventListener {
	private MidiEventChordAdapter adapter;

	@Override
	public void init(MidiEventListenerSource p) {
		adapter = new MidiEventChordAdapter().registerListener(p);

		adapter.addChordEventListener(this);
	}
	
	@Override
	public void chordChange(List<Chord> chords) {
		final String method = "chordChange";
		
		SimpleLogger.log(Level.INFO, ChordDriver.class, method, "{0}", chords);
	}

	@Override
	public void close() {
		super.close();

		if (adapter != null) {
			adapter.close();
		}
	}
	
	public static void main(String[] args) {
		new ChordDriver().run();
	}
}
