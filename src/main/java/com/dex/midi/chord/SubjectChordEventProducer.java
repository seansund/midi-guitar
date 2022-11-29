package com.dex.midi.chord;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

import java.util.List;

public class SubjectChordEventProducer implements ChordEventProducer {

    final private PublishSubject<List<Chord>> subject = PublishSubject.create();

    public Observable<List<Chord>> getObservable() {
        return subject;
    }

    @Override
    public void fireChordChange(List<Chord> chords) {
        subject.onNext(chords);
    }

    @Override
    public void close() {
        subject.onComplete();
    }
}
