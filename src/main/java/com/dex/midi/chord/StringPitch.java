package com.dex.midi.chord;

import com.dex.midi.model.Pitch;

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
	
	public int getStringIndex() {
		return stringIndex;
	}

	public void setStringIndex(int stringIndex) {
		this.stringIndex = stringIndex;
	}

	public Pitch getPitch() {
		return pitch;
	}

	public void setPitch(Pitch pitch) {
		this.pitch = pitch;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + stringIndex;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof StringPitch) {
			StringPitch other = (StringPitch) obj;
			if (this == other) {
				return true;
			}
			if (!getClass().equals(other.getClass())) {
				return false;
			}
			if (stringIndex != other.stringIndex) {
				return false;
			}
			return true;
		}
		return false;
	}
	
	@Override
	public int compareTo(StringPitch o) {
		int compare = -1;
		
		// sort based on the pitch and ignore the string index
		if (o != null) {
			compare = this.pitch.compareTo(o.pitch);
		}
		
		return compare;
	}

}
