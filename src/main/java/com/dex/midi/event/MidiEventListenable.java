package com.dex.midi.event;

import java.util.Collection;

public interface MidiEventListenable {
    public void addMidiEventListener(MidiEventListener l);

    public void removeMidiEventListener(MidiEventListener l);

    public Collection<MidiEventListener> getListeners();

}
