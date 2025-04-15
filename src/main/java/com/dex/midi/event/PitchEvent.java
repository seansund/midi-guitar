package com.dex.midi.event;

import com.dex.midi.model.GuitarPosition;
import com.dex.midi.model.Pitch;
import lombok.Getter;
import lombok.Setter;

import javax.sound.midi.ShortMessage;

@Setter
@Getter
public class PitchEvent extends MidiEvent<ShortMessage> {
	
	private static final String STRING_FORMAT = "[NoteEvent(%s): %s]";

	private GuitarPosition guitarFret;
	private Type type;
	
	public PitchEvent() {
		super();
	}
	
	public PitchEvent(ShortMessage msg, long tick, GuitarPosition guitarFret) {
		super(msg, tick);
		
		this.guitarFret = guitarFret;
		
		int command = msg.getCommand();
		if (msg.getData2() == 0) {
			command = ShortMessage.NOTE_OFF;
		}
		
		type = Type.getType(command);
	}

    public int getVelocity() {
		return getMessage().getData2();
	}

	public int getFretIndex() {
		return guitarFret.getFretIndex();
	}

	public int getStringIndex() {
		return guitarFret.getStringIndex();
	}

	public Pitch getPitch() {
		return guitarFret.getPitch();
	}

	public String toString() {
		return String.format(STRING_FORMAT, type, guitarFret);
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
