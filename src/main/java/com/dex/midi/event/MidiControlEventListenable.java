package com.dex.midi.event;

import java.util.Collection;

public interface MidiControlEventListenable {

    void addKeyChangeEventListener(MidiControlEventListener l);

    void removeKeyChangeEventListener(MidiControlEventListener l);

    Collection<MidiControlEventListener> getListeners();
}
