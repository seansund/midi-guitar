package com.dex.midi.event;

import java.util.Collection;

public interface MidiEventListenable {
    void addMidiEventListener(MidiEventListener l);

    void removeMidiEventListener(MidiEventListener l);

    Collection<MidiEventListener> getListeners();

}
