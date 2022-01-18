package com.dex.midi.event;

import com.dex.midi.model.GuitarPosition;
import com.dex.midi.model.Pitch;

import javax.sound.midi.ShortMessage;

public class PitchEvent extends MidiEvent<ShortMessage> {
	
	private static final String STRING_FORMAT = "[NoteEvent(%s): %s]";

	private GuitarPosition fret;
	private Type type;
	
	public PitchEvent() {
		super();
	}
	
	public PitchEvent(ShortMessage msg, long tick, GuitarPosition fret) {
		super(msg, tick);
		
		this.fret = fret;
		
		int command = msg.getCommand();
		if (msg.getData2() == 0) {
			command = ShortMessage.NOTE_OFF;
		}
		
		type = Type.getType(command);
	}
	
	public GuitarPosition getGuitarFret() {
		return fret;
	}

	public void setGuitarFret(GuitarPosition fret) {
		this.fret = fret;
	}
	
	public int getStringIndex() {
		return fret.getStringIndex();
	}
	
	public int getFretIndex() {
		return fret.getFretIndex();
	}
	
	public Pitch getPitch() {
		return fret.getPitch();
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
	public int getVelocity() {
		return getMessage().getData2();
	}
	
	public String toString() {
		return String.format(STRING_FORMAT, type, fret);
	}

	public static enum Type {
		NOTE_ON(ShortMessage.NOTE_ON), NOTE_OFF(ShortMessage.NOTE_OFF);
		
		private int value;
		
		Type(int value) {
			this.value = value;
		}
		
		public static Type getType(int value) {
			Type t = null;
			
			for (Type i : Type.values()) {
				if (i.value == value) {
					t = i;
					break;
				}
			}
			
			return t;
		}
	}
}
