package com.dex.midi.event;

import com.dex.midi.view.DecoratedView;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class SimpleDecoratedViewEventProducer<T> extends AbstractEventObservable<DecoratedViewEventListener<T>> implements DecoratedViewEventListenerSource<T>, DecoratedViewEventObservableSource<T> {

    private static SimpleDecoratedViewEventProducer<?> instance;

    private final BehaviorSubject<DecoratedView<T>> subject = BehaviorSubject.create();

    public static SimpleDecoratedViewEventProducer<?> getInstance() {
        if (instance != null) {
            return instance;
        }

        return instance = new SimpleDecoratedViewEventProducer<>();
    }

    @Override
    public void addDecoratedViewEventListener(DecoratedViewEventListener<T> l) {
        registerListener(subject, l, l::decoratedViewChanged);
    }

    @Override
    public void removeDecoratedViewEventListener(DecoratedViewEventListener<T> l) {
        dispose(l);
    }

    @Override
    public Observable<DecoratedView<T>> getObservable() {
        return subject;
    }

    @Override
    public void mergeProducers(DecoratedViewEventProducer<T> that) {
        if (that instanceof SimpleDecoratedViewEventProducer<T> thatP) {
            subject.subscribe(thatP.subject);
        }
    }

    @Override
    public void fireDecoratedViewEvent(DecoratedView<T> v) {
        subject.onNext(v);
    }
}
