package com.dex.midi.chord;

import com.dex.midi.event.*;
import com.dex.midi.event.util.Disposables;
import com.dex.midi.model.GuitarPositions;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.List;

public class MidiEventChordAdapter implements MidiEventListener, ChordEventObservableSource, ChordEventListenerSource {
    final private SimpleChordEventProducer producer;
    final private MidiChordListener listener;
    private Disposable disposable;

    public MidiEventChordAdapter() {
        this(new SimpleChordEventProducer());
    }

    public MidiEventChordAdapter(SimpleChordEventProducer producer) {
        this.producer = producer;
        this.listener = new MidiChordListener(producer);
    }

    public MidiEventChordAdapter registerListener(MidiEventListenable listenable) {
        listenable.addMidiEventListener(this);

        return this;
    }

    public MidiEventChordAdapter registerObservable(MidiEventObservable observableSource) {
        final Disposable newDisposable = Disposables.from(
                observableSource.getNoteOnObservable().subscribe(this::noteOn),
                observableSource.getNoteOffObservable().subscribe(this::noteOff),
                observableSource.getPitchBendObservable().subscribe(this::pitchBend),
                observableSource.getGuitarPositionObservable().subscribe(this::guitarPositions)
        );

        if (this.disposable == null || this.disposable.isDisposed()) {
            this.disposable = newDisposable;
        } else {
            this.disposable = Disposables.from(
                    this.disposable,
                    newDisposable
            );
        }

        return this;
    }

    @Override
    public void addChordEventListener(ChordEventListener l) {
        producer.addChordEventListener(l);
    }

    @Override
    public void removeChordEventListener(ChordEventListener l) {
        producer.removeChordEventListener(l);
    }

    @Override
    public Observable<List<Chord>> getObservable() {
        return producer.getObservable();
    }

    @Override
    public void fireChordChange(List<Chord> chords) {
        producer.fireChordChange(chords);
    }

    @Override
    public void noteOn(PitchEvent e) {
        listener.noteOn(e);
    }

    @Override
    public void noteOff(PitchEvent e) {
        listener.noteOff(e);
    }

    @Override
    public void pitchBend(PitchBendEvent e) {
        listener.pitchBend(e);
    }

    @Override
    public void guitarPositions(GuitarPositions positions) {
        listener.guitarPositions(positions);
    }

    @Override
    public void close() {
        producer.close();
        listener.close();
        disposable.dispose();
    }
}
