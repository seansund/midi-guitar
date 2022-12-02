package com.dex.midi.event;

import com.dex.midi.event.util.Disposables;
import com.dex.midi.model.GuitarPosition;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

import java.util.*;

public class SimpleMidiEventProducer implements MidiEventObservableSource, MidiEventListenerSource {

    private static SimpleMidiEventProducer instance;

    private Map<MidiEventListener, Disposable> listeners = new HashMap<>();

    private BehaviorSubject<PitchEvent> noteOnSubject = BehaviorSubject.create();
    private BehaviorSubject<PitchEvent> noteOffSubject = BehaviorSubject.create();
    private BehaviorSubject<PitchBendEvent> pitchBendSubject = BehaviorSubject.create();
    private BehaviorSubject<GuitarPosition[]> guitarPositionSubject = BehaviorSubject.create();

    private GuitarPosition[] positions = new GuitarPosition[6];

    public static SimpleMidiEventProducer getInstance() {
        if (instance != null) {
            return instance;
        }

        return instance = new SimpleMidiEventProducer();
    }

    @Override
    public void mergeProducers(MidiEventProducer that) {
        if (that instanceof SimpleMidiEventProducer) {
            final SimpleMidiEventProducer thatP = (SimpleMidiEventProducer) that;

            thatP.noteOnSubject.subscribe(this.noteOnSubject);
            thatP.noteOffSubject.subscribe(this.noteOffSubject);
            thatP.pitchBendSubject.subscribe(this.pitchBendSubject);
            thatP.guitarPositionSubject.subscribe(this.guitarPositionSubject);
        }
    }

    @Override
    public Observable<PitchEvent> getNoteOnObservable() {
        return noteOnSubject;
    }

    @Override
    public Observable<PitchEvent> getNoteOffObservable() {
        return noteOffSubject;
    }

    @Override
    public Observable<PitchBendEvent> getPitchBendObservable() {
        return pitchBendSubject;
    }

    @Override
    public Observable<GuitarPosition[]> getGuitarPositionObservable() {
        return guitarPositionSubject;
    }

    @Override
    public void fireNoteOn(PitchEvent e) {
        positions[e.getStringIndex()] = e.getGuitarFret();

        noteOnSubject.onNext(e);

        fireGuitarPositions(positions);
    }

    @Override
    public void fireNoteOff(PitchEvent e) {
        positions[e.getStringIndex()] = null;

        noteOffSubject.onNext(e);

        fireGuitarPositions(positions);
    }

    @Override
    public void firePitchBend(PitchBendEvent e) {
        pitchBendSubject.onNext(e);
    }

    @Override
    public void fireGuitarPositions(GuitarPosition[] e) {
        guitarPositionSubject.onNext(e);
    }

    @Override
    public void close() {
        noteOnSubject.onComplete();
        noteOffSubject.onComplete();
        pitchBendSubject.onComplete();
        guitarPositionSubject.onComplete();
    }

    @Override
    public void addMidiEventListener(MidiEventListener l) {
        if (listeners.containsKey(l)) {
            return;
        }

        final Disposable disposable = Disposables.from(
                noteOnSubject.subscribe(l::noteOn),
                noteOffSubject.subscribe(l::noteOff),
                pitchBendSubject.subscribe(l::pitchBend),
                guitarPositionSubject.subscribe(l::guitarPositions)
        );

        listeners.put(l, disposable);
    }

    @Override
    public void removeMidiEventListener(MidiEventListener l) {
        if (!listeners.containsKey(l)) {
            return;
        }

        final Disposable disposable = listeners.remove(l);

        disposable.dispose();
    }

    @Override
    public Collection<MidiEventListener> getListeners() {
        return listeners.keySet();
    }
}
