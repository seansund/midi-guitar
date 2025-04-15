package com.dex.midi.model;

import com.dex.midi.MidiConfig;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Setter
public class GuitarPosition implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	
	private static final String STRING_FORMAT = "[%d, %d, %s]";
	
	@Getter
    private int stringIndex;
	private int fretIndex;
	
	public GuitarPosition() {
		super();
	}
	
	public GuitarPosition(int stringIndex, int fretIndex) {
		this();
		
		validate(stringIndex, fretIndex);
		
		this.stringIndex = stringIndex;
		this.fretIndex = fretIndex;
	}
	
	protected final void validate(int stringIndex, int fretIndex) {
		MidiConfig cfg = MidiConfig.getInstance();
		
		if (stringIndex < 0 || stringIndex > cfg.getStringCount()) {
			throw new IllegalArgumentException(String.format("String index is out of range: %d", stringIndex));
		}
		
		if (fretIndex < 0 || fretIndex > cfg.getFretCount()) {
			throw new IllegalArgumentException(String.format("Fret index is out of range: %d", fretIndex));
		}
	}

    public int getFretIndex() {
		MidiConfig m = MidiConfig.getInstance();
		int stringOffset = m.getStringOffset(stringIndex);
		
		return validateFretIndex(fretIndex + stringOffset);
	}

    public int getAdjustedFretIndex() {
		MidiConfig m = MidiConfig.getInstance();
		int keyOffset = m.getKeyIndex();
		
		return validateFretIndex(getFretIndex() - keyOffset);
	}
	
	protected int validateFretIndex(int index) {
		if (index < 0) {
			index += 12;
		}
		
		return index;
	}
	
	public Note getNote() {
		
		MidiConfig cfg = MidiConfig.getInstance();
		Note openNote = cfg.getOpenNote(stringIndex);
		
		return openNote.transpose(fretIndex);
	}
	
	public Pitch getPitch() {
		
		MidiConfig cfg = MidiConfig.getInstance();
		Pitch openPitch = cfg.getOpenPitch(stringIndex);
		
		return openPitch.transpose(fretIndex);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;

		return Objects.hash(prime, fretIndex, stringIndex);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GuitarPosition other) {
            if (this == other) {
				return true;
			}
			if (!getClass().equals(other.getClass())) {
				return false;
			}
			if (fretIndex != other.fretIndex) {
				return false;
			}
            return stringIndex == other.stringIndex;
        }
		return false;
	}
	
	@Override
	public String toString() {
		return String.format(STRING_FORMAT, stringIndex, fretIndex, getPitch());
	}
}
