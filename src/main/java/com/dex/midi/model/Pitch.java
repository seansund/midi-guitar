package com.dex.midi.model;

import com.dex.midi.util.SimpleLogger;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

@Getter
public class Pitch implements Comparable<Pitch>, Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	
	private static final String STRING_FORMAT = "%s%d";
	
	private final Note note;
	private final int octave;
	
	public Pitch(Note note, int octave) {
		super();
		
		this.note = note;
		this.octave = octave;
	}
	
	public Pitch(int midiPitch) {
		super();
		
		// midiPitch(60) = C4
		this.note = Note.atIndex(midiPitch % 12);
		this.octave = (midiPitch / 12) - 1;
	}
	
	public static Pitch determinePitch(double frequency) {
		double pos = (12 * Math.log(frequency / 440.0) / Math.log(2.0)) + 69;
		long pitchPosition = Math.round(pos);
		
		int octave = (int)(pitchPosition / 12) - 1;
		int index = (int)(pitchPosition % 12);
		Note n = Note.atIndex(index);
		
		return new Pitch(n, octave);
	}
	
	public static double calculateFrequency(Pitch p) {
		return Pitch.calculateFrequency(p.note, p.octave);
	}
	
	public static double calculateFrequency(Note note, int octave) {
		
		// f = 440 * (2 ^ ((pitchPosition - 69) / 12))
		int index = Pitch.getPitchIndex(octave, note);
		return 440 * Math.pow(2, (index - 69.0) / 12.0);
	}
	
	protected static int getPitchIndex(Pitch p) {
		return getPitchIndex(p.octave, p.note);
	}
	
	protected static int getPitchIndex(int octave, Note note) {
		return ((octave + 1) * 12) + Note.indexOf(note);
	}
	
	public int getMidiPitch() {
		return Pitch.getPitchIndex(this);
	}
	
	public Pitch transpose(int steps) {
		return Pitch.transpose(this, steps);
	}
	
	public static Pitch transpose(Pitch p, int steps) {
		Pitch newPitch = p;
		
		if (steps != 0) {
			newPitch = Pitch.transpose(p.octave, p.note, steps);
		}
		
		return newPitch;
	}
	
	public static Pitch transpose(int octave, Note note, int steps) {
		int index = Pitch.getPitchIndex(octave, note) + steps;
		
		return new Pitch(index);
	}
	
	public static int distance(Pitch p1, Pitch p2) {
		int distance = 0;
		
		if (p1 != null && p2 != null) {
			distance = Pitch.getPitchIndex(p2) - Pitch.getPitchIndex(p1);
		}
		
		return distance;
		
	}
	
	public int distance(Pitch pitch) {
		return Pitch.distance(this, pitch);
	}
	
	public String toString() {
		return String.format(STRING_FORMAT, note, octave);
	}

	@Override
	public int hashCode() {
		final int prime = 31;

		return Objects.hash(prime, note, octave);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Pitch other) {
            if (this == other) {
				return true;
			}
			if (!getClass().equals(other.getClass())) {
				return false;
			}
			if (!note.equals(other.note)) {
				return false;
			}
            return octave == other.octave;
        }
		return false;
	}

    public double getFrequency() {
		return Pitch.calculateFrequency(note, octave);
	}

	@Override
	public int compareTo(Pitch o) {
		if (o != null) {
			int compare = this.octave - o.octave;
			
			if (compare != 0) {
				return compare;
			}

			return Note.indexOf(this.note) - Note.indexOf(o.note);
		}
		
		return -1;
	}
	
	public static void main(String[] args) {
		final String method = "main";
		
		Pitch[] ps = new Pitch[] {
				Pitch.determinePitch(16.35),   // C0
				Pitch.determinePitch(87.3071), // F2
				Pitch.determinePitch(82.4),    // E2
				Pitch.determinePitch(110),     // A2
				Pitch.determinePitch(146.8),   // D3
				Pitch.determinePitch(195.5),   // G3
				Pitch.determinePitch(247.9),   // B3
				Pitch.determinePitch(329.6),   // E4
				new Pitch(Note.A, 4),		   // A4 (midiPitch=69
				new Pitch(60),				   // C4 (midiPitch=60
				new Pitch(Note.C, 4).transpose(5) // C4
			};
		
		Pitch prev = null;
		for (Pitch p : ps) {
			SimpleLogger.log(Level.INFO, Pitch.class, method, "Pitch: {0} ({1}, {2})", p, p.getFrequency(), p.getMidiPitch());
			if (prev != null) {
				SimpleLogger.log(Level.INFO, Pitch.class, method, "   Distance ({0}, {1}): {2}", p, prev, prev.distance(p));
			}
			prev = p;
		}
		
		List<Pitch> p = Arrays.asList(ps);
		Collections.sort(p);
		
		SimpleLogger.log(Level.INFO, Pitch.class, method, p.toString());
	}
}
