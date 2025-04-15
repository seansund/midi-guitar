package com.dex.midi.event;

import lombok.Getter;
import lombok.Setter;

import javax.sound.midi.MidiMessage;

@Setter
@Getter
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

}
