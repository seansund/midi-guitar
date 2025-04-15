package com.dex.midi.chord;

import com.dex.midi.event.AbstractEventObservable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

import java.util.List;

public class SimpleChordEventProducer extends AbstractEventObservable<ChordEventListener> implements ChordEventObservableSource, ChordEventListenerSource {

	final private BehaviorSubject<List<Chord>> subject = BehaviorSubject.create();

	@Override
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

	@Override
	public void addChordEventListener(ChordEventListener l) {
		registerListener(subject, l, l::chordChange);
	}

	@Override
	public void removeChordEventListener(ChordEventListener l) {
		dispose(l);
	}
}
