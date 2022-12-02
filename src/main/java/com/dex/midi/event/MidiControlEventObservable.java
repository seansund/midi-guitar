package com.dex.midi.event;

import com.dex.midi.model.MusicalKey;
import com.dex.midi.model.StringOffset;
import io.reactivex.rxjava3.core.Observable;

public interface MidiControlEventObservable {
    public Observable<MusicalKey> getKeyChangeObservable();

    public Observable<StringOffset> getStringOffsetObservable();
}
