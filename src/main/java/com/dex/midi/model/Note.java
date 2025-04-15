package com.dex.midi.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public enum Note implements Serializable {
	C("C"), Csharp("C#", "Db"), D("D"), Dsharp("D#", "Eb"), E("E"), F("F"), Fsharp("F#", "Gb"), G("G"), Gsharp("G#", "Ab"), A("A"), Asharp("A#", "Bb"), B("B");
	
	private static final Map<String, Note> MAP = new HashMap<>();
	
	private final String value;
	private final String flatValue;
	
	static {
		for (Note n : Note.values()) {
			MAP.put(n.value, n);
			MAP.put(n.flatValue, n);
		}
	}
	
	Note(String value) {
		this(value, value);
	}
	
	Note(String value, String flatValue) {
		this.value = value;
		this.flatValue = flatValue;
	}
	
	public static Note getNote(String value) {
		return MAP.get(value);
	}
	
	public int index() {
		return Note.indexOf(this);
	}
	
	public String toString() {
		return toString(true);
	}
	
	public String toString(boolean sharp) {
		return sharp ? value : flatValue;
	}
	
	public int absoluteDistance(Note n) {
		int distance = 0;
		
		if (n != null) {
			// get the index of the note in the array
			int i1 = Note.indexOf(this);
			int i2 = Note.indexOf(n);
			
			// calculate two distances, one for straight-line and the other
			// for wrapping around
			int dist1 = Math.abs(i1 - i2);
			int dist2 = Math.abs(i2 -(i1 + Note.values().length));
			
			// get the smaller of the two distances
			distance = Math.min(dist1, dist2);
		}
		
		return distance;
	}
	
	public int distance(Note n) {
		return Note.distance(this, n);
	}
	
	public static int distance(Note n1, Note n2) {
		int distance = 0;
		
		if (n1 != null && n2 != null) {
			int i1 = Note.indexOf(n1);
			int i2 = Note.indexOf(n2);
			
			distance = i2 - i1;
		}
		
		return distance;
	}
	
	public Note transpose(int steps) {
		return Note.transpose(this, steps);
	}
	
	public static Note transpose(Note note, int steps) {
		
		// shift the current index by the number of steps (+/-)
		// add a shift of 12
		int index = Note.indexOf(note) + steps;
		while (index < 0) {
			index += 12;
		}
		
		// move the index within the octave
		index = index % 12;
		
		return Note.values()[index];
	}
	
	public static Note atIndex(int index) {
		int i = index;
		while (i < 0) {
			i += 12;
		}
		
		i = i % 12;
		
		return Note.values()[i];
	}
	
	public static int indexOf(Note n, Note relativeTo) {
		int rootIndex = Note.indexOf(relativeTo);
		int index = Note.indexOf(n);
		
		if (index < rootIndex) {
			index += 12;
		}
		
		return index - rootIndex;
	}
	
	public static int indexOf(Note n) {
		int index = -1;
		
		if (n != null) {
			Note[] notes = Note.values();
			for (int i = 0; i < notes.length; i++) {
				Note v = notes[i];
				
				if (v.equals(n)) {
					index = i;
					break;
				}
			}
		}
		
		return index;
	}
}
