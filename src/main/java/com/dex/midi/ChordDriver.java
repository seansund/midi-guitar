package com.dex.midi;

import com.dex.midi.chord.*;
import com.dex.midi.event.MidiEventProducer;
import com.dex.midi.util.SimpleLogger;

import java.util.List;
import java.util.logging.Level;

public class ChordDriver extends com.dex.midi.Driver implements ChordEventListener {

	@Override
	public void init(MidiEventProducer p) {
		ChordEventProducer cp = new SimpleChordEventProducer();
		
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
