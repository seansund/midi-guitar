package com.dex.midi.chord;

public interface ListenerChordEventProducer extends ChordEventProducer {

    void addChordEventListener(ChordEventListener l);

    void removeChordEventListener(ChordEventListener l);
}
