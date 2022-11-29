package com.dex.midi.event;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class SubjectMidiEventProducer implements MidiEventProducer, MidiEventObservable {

    private PublishSubject<PitchEvent> noteOnSubject = PublishSubject.create();
    private PublishSubject<PitchEvent> noteOffSubject = PublishSubject.create();
    private PublishSubject<PitchBendEvent> pitchBendSubject = PublishSubject.create();

    @Override
    public void mergeProducers(MidiEventProducer that) {
        if (that instanceof SubjectMidiEventProducer) {
            final SubjectMidiEventProducer thatP = (SubjectMidiEventProducer) that;

            thatP.noteOnSubject.subscribe(this.noteOnSubject);
            thatP.noteOffSubject.subscribe(this.noteOffSubject);
            thatP.pitchBendSubject.subscribe(this.pitchBendSubject);
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
    public void fireNoteOn(PitchEvent e) {
        noteOnSubject.onNext(e);
    }

    @Override
    public void fireNoteOff(PitchEvent e) {
        noteOffSubject.onNext(e);
    }

    @Override
    public void firePitchBend(PitchBendEvent e) {
        pitchBendSubject.onNext(e);
    }

    @Override
    public void close() {
        noteOnSubject.onComplete();
        noteOffSubject.onComplete();
        pitchBendSubject.onComplete();
    }
}
