package com.dex.midi.event;

import javax.sound.midi.MidiMessage;

public class MidiEvent<T extends MidiMessage> {
	
	private T message;
	private long tick;
	
	public MidiEvent() {
		super();
	}
	
	public MidiEvent(T message, long tick) {
		super();
		
		this.message = message;
		this.tick = tick;
	}
	
	public T getMessage() {
		return message;
	}
	public void setMessage(T message) {
		this.message = message;
	}
	public long getTick() {
		return tick;
	}
	public void setTick(long tick) {
		this.tick = tick;
	}
	
}
