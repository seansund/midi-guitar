package com.dex.midi.event.util;

import io.reactivex.rxjava3.disposables.Disposable;

import java.util.Arrays;
import java.util.Collection;

public class Disposables implements Disposable {
    private Collection<Disposable> disposables;
    private boolean disposed = false;

    public Disposables(Collection<Disposable> disposables) {
        this.disposables = disposables;
    }

    public static Disposable from(Disposable... disposables) {
        return new Disposables(Arrays.asList(disposables));
    }

    @Override
    public void dispose() {
        if (disposed) {
            return;
        }

        disposables.forEach(Disposable::dispose);
        disposed = true;
    }

    @Override
    public boolean isDisposed() {
        return disposed;
    }
}
