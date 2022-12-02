package com.dex.midi.event;

import com.dex.midi.view.DecoratedView;
import io.reactivex.rxjava3.core.Observable;

public interface DecoratedViewEventObservable<T> {
    public Observable<DecoratedView<T>> getObservable();
}
