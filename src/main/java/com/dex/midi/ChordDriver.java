package com.dex.midi;

import com.dex.midi.chord.Chord;
import com.dex.midi.chord.ChordEventListener;
import com.dex.midi.chord.MidiChordListener;
import com.dex.midi.chord.SimpleChordEventProducer;
import com.dex.midi.event.MidiEventListenerSource;
import com.dex.midi.util.SimpleLogger;

import java.util.List;
import java.util.logging.Level;

public class ChordDriver extends com.dex.midi.Driver implements ChordEventListener {

	@Override
	public void init(MidiEventListenerSource p) {
		SimpleChordEventProducer cp = new SimpleChordEventProducer();
		
		MidiChordListener l = new MidiChordListener(cp);
		cp.addChordEventListener(this);
		
		p.addMidiEventListener(l);
	}
	
	@Override
	public void chordChange(List<Chord> chords) {
		final String method = "chordChange";
		
		SimpleLogger.log(Level.INFO, ChordDriver.class, method, "{0}", chords);
	}
	
	public static void main(String[] args) {
		new ChordDriver().run();
	}
}
