package com.dex.midi.chord;

import com.dex.midi.model.Pitch;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
public class StringPitch implements Comparable<StringPitch> {
	
	private int stringIndex;
	private Pitch pitch;
	
	public StringPitch(int stringIndex) {
		this(stringIndex, null);
	}
	
	public StringPitch(int stringIndex, Pitch pitch) {
		super();
		
		this.stringIndex = stringIndex;
		this.pitch = pitch;
	}

    @Override
	public int hashCode() {
		final int prime = 31;
		return Objects.hash(prime, stringIndex);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof StringPitch other) {
            if (this == other) {
				return true;
			}
			if (!getClass().equals(other.getClass())) {
				return false;
			}
            return stringIndex == other.stringIndex;
        }
		return false;
	}
	
	@Override
	public int compareTo(StringPitch o) {
		// sort based on the pitch and ignore the string index
		if (o != null) {
			return this.pitch.compareTo(o.pitch);
		}
		
		return -1;
	}

}
