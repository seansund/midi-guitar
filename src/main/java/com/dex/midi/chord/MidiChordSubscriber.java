package com.dex.midi.chord;

import com.dex.midi.event.MidiEventObservable;
import com.dex.midi.event.MidiEventProducer;
import com.dex.midi.event.PitchEvent;
import com.dex.midi.event.SubjectMidiEventProducer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MidiChordSubscriber {

    private Set<StringPitch> stringPitch = new HashSet<StringPitch>(12);

    public MidiChordSubscriber(MidiEventObservable mp, ChordEventProducer p) {
        super();

        mp.getNoteOnObservable().subscribe((PitchEvent e) -> {
            stringPitch.add(new StringPitch(e.getStringIndex(), e.getPitch()));

            List<Chord> chords = Chord.identifyChords(stringPitch);
            p.fireChordChange(chords);
        });
        mp.getNoteOffObservable().subscribe((PitchEvent e) -> {
            stringPitch.remove(new StringPitch(e.getStringIndex()));

            List<Chord> chords = Chord.identifyChords(stringPitch);
            p.fireChordChange(chords);
        });
    }
}
