package com.dex.midi.chord;

public interface ChordEventListenable {

    void addChordEventListener(ChordEventListener l);

    void removeChordEventListener(ChordEventListener l);
}
