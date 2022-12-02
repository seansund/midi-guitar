package com.dex.midi.event;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.subjects.Subject;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractEventObservable<T> {

    private final Map<T, Disposable> instances = new HashMap<>();

    public boolean isRegistered(T listener) {
        return instances.containsKey(listener);
    }

    public <S> void registerListener(Subject<S> subject, T listener, Consumer<? super S> onNext) {
        if (isRegistered(listener)) {
            return;
        }

        instances.put(listener, subject.subscribe(onNext));
    }

    public void dispose(T listener) {
        final Disposable disposable = instances.remove(listener);

        if (disposable != null) {
            disposable.dispose();
        }
    }

    public Collection<T> getListeners() {
        return instances.keySet();
    }
}
