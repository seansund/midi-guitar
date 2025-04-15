package com.dex.midi.chord;

import io.reactivex.rxjava3.core.Observable;

import java.util.List;

public interface ChordEventObservable {
    Observable<List<Chord>> getObservable();
}
