package com.dex.midi.chord;

import com.dex.midi.event.MidiEventObservable;
import com.dex.midi.event.PitchEvent;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MidiChordSubscriber {

    private final Set<StringPitch> stringPitch = new HashSet<>(12);

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
