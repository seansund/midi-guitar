package com.dex.midi;


import com.dex.midi.event.MidiEventListenerSource;
import com.dex.midi.event.MidiEventObservableSource;

public interface MidiDriver extends Runnable, AutoCloseable {

    MidiEventListenerSource getMidiEventListenerSource();

    MidiEventObservableSource getMidiEventObservableSource();
}
