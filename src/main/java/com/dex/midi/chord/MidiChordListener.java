package com.dex.midi.chord;

import com.dex.midi.event.MidiEventListener;
import com.dex.midi.event.PitchBendEvent;
import com.dex.midi.event.PitchEvent;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MidiChordListener implements MidiEventListener {
	
	private Set<StringPitch> stringPitch = new HashSet<StringPitch>(12);
	
	private ChordEventProducer p = null;
	
	public MidiChordListener(ChordEventProducer p) {
		super();
		
		this.p = p;
	}
	
	@Override
	public void noteOn(PitchEvent e) {
		stringPitch.add(new StringPitch(e.getStringIndex(), e.getPitch()));
		
		List<Chord> chords = Chord.identifyChords(stringPitch);
		p.fireChordChange(chords);
	}
	
	@Override
	public void noteOff(PitchEvent e) {
		stringPitch.remove(new StringPitch(e.getStringIndex()));
		
		List<Chord> chords = Chord.identifyChords(stringPitch);
		p.fireChordChange(chords);
	}
	
	@Override
	public void pitchBend(PitchBendEvent e) {
		// nothing to do
	}
	
	@Override
	public void close() {
		// nothing to do
	}
}
