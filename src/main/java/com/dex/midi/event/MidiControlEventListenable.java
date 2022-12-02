package com.dex.midi.event;

import java.util.Collection;

public interface MidiControlEventListenable {

    public void addKeyChangeEventListener(MidiControlEventListener l);

    public void removeKeyChangeEventListener(MidiControlEventListener l);

    public Collection<MidiControlEventListener> getListeners();
}
