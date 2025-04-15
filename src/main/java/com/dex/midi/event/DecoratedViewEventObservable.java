package com.dex.midi.event;

import com.dex.midi.view.DecoratedView;
import io.reactivex.rxjava3.core.Observable;

public interface DecoratedViewEventObservable<T> {
    Observable<DecoratedView<T>> getObservable();
}
