package com.dex.midi.event;

import com.dex.midi.model.GuitarPositions;
import io.reactivex.rxjava3.core.Observable;

public interface MidiEventObservable {

    Observable<PitchEvent> getNoteOnObservable();

    Observable<PitchEvent> getNoteOffObservable();

    Observable<PitchBendEvent> getPitchBendObservable();

    Observable<GuitarPositions> getGuitarPositionObservable();
}
