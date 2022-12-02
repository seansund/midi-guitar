package com.dex.midi.event;

import com.dex.midi.event.util.Disposables;
import com.dex.midi.model.MusicalKey;
import com.dex.midi.model.StringOffset;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

import java.util.*;

public class SimpleMidiControlEventProducer implements MidiControlEventListenerSource, MidiControlEventObservableSource {

    private static SimpleMidiControlEventProducer instance;

    private final BehaviorSubject<MusicalKey> keyChangeSubject = BehaviorSubject.create();
    private final BehaviorSubject<StringOffset> stringOffsetSubject = BehaviorSubject.create();

    private final Map<MidiControlEventListener, Disposable> listeners = new HashMap<>();

    public static SimpleMidiControlEventProducer getInstance() {
        if (instance != null) {
            return instance;
        }

        return instance = new SimpleMidiControlEventProducer();
    }

    @Override
    public void addKeyChangeEventListener(MidiControlEventListener l) {
        if (listeners.containsKey(l)) {
            return;
        }

        listeners.put(l, Disposables.from(
                keyChangeSubject.subscribe(l::keyChanged),
                stringOffsetSubject.subscribe(l::stringOffsetChanged)
        ));
    }

    @Override
    public void removeKeyChangeEventListener(MidiControlEventListener l) {
        if (!listeners.containsKey(l)) {
            return;
        }

        final Disposable disposable = listeners.remove(l);

        disposable.dispose();
    }

    @Override
    public Collection<MidiControlEventListener> getListeners() {
        return listeners.keySet();
    }

    @Override
    public Observable<MusicalKey> getKeyChangeObservable() {
        return keyChangeSubject;
    }

    @Override
    public Observable<StringOffset> getStringOffsetObservable() {
        return stringOffsetSubject;
    }

    @Override
    public void mergeProducers(MidiControlEventProducer that) {
        if (that instanceof SimpleMidiControlEventProducer) {
            final SimpleMidiControlEventProducer thatP = (SimpleMidiControlEventProducer) that;

            keyChangeSubject.subscribe(thatP.keyChangeSubject);
            stringOffsetSubject.subscribe(thatP.stringOffsetSubject);
        }
    }

    @Override
    public void fireKeyChanged(MusicalKey key) {
        keyChangeSubject.onNext(key);
    }

    @Override
    public void fireStringOffsetChanged(StringOffset stringOffset) {
        stringOffsetSubject.onNext(stringOffset);
    }
}
