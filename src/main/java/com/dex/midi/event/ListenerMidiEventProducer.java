package com.dex.midi.event;

import java.util.Collection;

public interface ListenerMidiEventProducer extends MidiEventProducer {
    public void addMidiEventListener(MidiEventListener l);

    public void removeMidiEventListener(MidiEventListener l);

    public Collection<MidiEventListener> getListeners();

}
