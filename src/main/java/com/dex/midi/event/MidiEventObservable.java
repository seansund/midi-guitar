package com.dex.midi.event;

import com.dex.midi.model.GuitarPosition;
import io.reactivex.rxjava3.core.Observable;

public interface MidiEventObservable {

    public Observable<PitchEvent> getNoteOnObservable();

    public Observable<PitchEvent> getNoteOffObservable();

    public Observable<PitchBendEvent> getPitchBendObservable();

    public Observable<GuitarPosition[]> getGuitarPositionObservable();
}
