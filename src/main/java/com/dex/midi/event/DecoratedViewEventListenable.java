package com.dex.midi.event;

import java.util.Collection;

public interface DecoratedViewEventListenable<T> {

    public void addDecoratedViewEventListener(DecoratedViewEventListener<T> l);

    public void removeDecoratedViewEventListener(DecoratedViewEventListener<T> l);

    public Collection<DecoratedViewEventListener<T>> getListeners();
}
