package com.dex.midi.event;

import io.reactivex.rxjava3.core.Observable;

public interface MidiEventObservable {

    public Observable<PitchEvent> getNoteOnObservable();

    public Observable<PitchEvent> getNoteOffObservable();

    public Observable<PitchBendEvent> getPitchBendObservable();
}
