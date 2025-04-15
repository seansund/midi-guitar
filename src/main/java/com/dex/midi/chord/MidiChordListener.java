package com.dex.midi.chord;

import com.dex.midi.event.MidiEventListener;
import com.dex.midi.event.PitchBendEvent;
import com.dex.midi.event.PitchEvent;
import com.dex.midi.model.GuitarPositions;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MidiChordListener implements MidiEventListener {
	
	private final Set<StringPitch> stringPitch = new HashSet<>(12);
	
	final private ChordEventProducer p;
	
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
	public void guitarPositions(GuitarPositions positions) {
		// TODO
	}

	@Override
	public void close() {
		// nothing to do
	}
}
