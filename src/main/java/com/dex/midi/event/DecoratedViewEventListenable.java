package com.dex.midi.event;

import java.util.Collection;

public interface DecoratedViewEventListenable<T> {

    void addDecoratedViewEventListener(DecoratedViewEventListener<T> l);

    void removeDecoratedViewEventListener(DecoratedViewEventListener<T> l);

    Collection<DecoratedViewEventListener<T>> getListeners();
}
