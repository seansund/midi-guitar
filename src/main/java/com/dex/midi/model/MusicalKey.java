package com.dex.midi.model;

import com.dex.midi.util.SimpleLogger;
import com.dex.midi.util.StringUtil;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;


public enum MusicalKey {
	G(Note.G),
	Aflat(Note.Gsharp, false), // Ab, Bb, C, Db, Eb, F, G
	A(Note.A),
	Bflat(Note.Asharp, false),
	B(Note.B),
	C(Note.C),
	Dflat(Note.Csharp, false), // Db, Eb, F, Gb, Ab, Bb, C
	D(Note.D),
	Eflat(Note.Dsharp, false), 
	E(Note.E),
	F(Note.F),
	Fsharp(Note.Fsharp, Type.MAJOR), // F#, G#, A#, B, C#, D#, F
	// TODO this is kludgy.  should this not be an enum?
	Gminor(Note.G, Type.MINOR),
	Aflatminor(Note.Gsharp, Type.MINOR, false), // Ab, Bb, C, Db, Eb, F, G
	Aminor(Note.A, Type.MINOR),
	Bflatminor(Note.Asharp, Type.MINOR, false),
	Bminor(Note.B, Type.MINOR),
	Cminor(Note.C, Type.MINOR),
	Dflatminor(Note.Csharp, Type.MINOR, false), // Db, Eb, F, Gb, Ab, Bb, C
	Dminor(Note.D, Type.MINOR),
	Eflatminor(Note.Dsharp, Type.MINOR, false), 
	Eminor(Note.E, Type.MINOR),
	Fminor(Note.F, Type.MINOR),
	Fsharpminor(Note.Fsharp, Type.MINOR); // F#, G#, A#, B, C#, D#, F
	
	private static final String KEY_FORMAT = "%s%s";
	
	private static final Map<String, MusicalKey> MAP = new HashMap<>();
	private static final List<MusicalKey> MAJORS = new ArrayList<>(12);
	private static final List<MusicalKey> MINORS = new ArrayList<>(12);
	
	static {
		for (MusicalKey k : MusicalKey.values()) {
			MAP.put(k.toString(false, true), k);
			MAP.put(k.toString(false, false), k);
			
			if (Type.MAJOR.equals(k.type)) {
				MAJORS.add(k);
			} else {
				MINORS.add(k);
			}
		}
	}
	
	@Getter
    private final Note tonic;
	private final Type type;
	private final Note relative;
	private final boolean sharp;
	
	MusicalKey(Note tonic) {
		this(tonic, true);
	}
	
	MusicalKey(Note tonic, boolean sharp) {
		this(tonic, Type.MAJOR, sharp);
	}
	
	MusicalKey(Note tonic, Type type) {
		this(tonic, type, true);
	}
	
	MusicalKey(Note tonic, Type type, boolean sharp) {
		
		this.tonic = tonic;
		this.type = type;
		this.sharp = sharp;
		
		if (Type.MAJOR.equals(type)) {
			this.relative = tonic.transpose(-3);
		} else {
			this.relative = tonic.transpose(3);
		}
	}
	
	public static MusicalKey lookupKey(Note tonic, Type type) {
		String key = String.format(MusicalKey.KEY_FORMAT, tonic.toString(), type.toString());
		
		return MAP.get(key);
	}

    public String toString() {
		return toString(true);
	}
	
	public String toString(boolean includeRelative) {
		return toString(includeRelative, sharp);
	}
	
	public String toString(boolean includeRelative, boolean sharp) {
		final String method = "toString";
		
		final StringBuilder buf = new StringBuilder(20);
		
		buf.append(tonic.toString(sharp)).append(type.toString());
		if (includeRelative) {
			MusicalKey relative = getRelative();
			
			if (relative == null) {
				SimpleLogger.log(Level.WARNING, this, method, "Could not find relative key: {0}", tonic);
			} else {
				buf.append(" / ").append(relative.toString(false));
			}
		}
		
		return buf.toString();
	}
	
	public MusicalKey getRelative() {
		return MusicalKey.lookupKey(relative, type.next());
	}
	
	public static List<MusicalKey> majors() {
		return MAJORS;
	}
	
	public static List<MusicalKey> minors() {
		return MINORS;
	}
	
	public static int indexOf(MusicalKey k) {
		return Type.MAJOR.equals(k.type) ? MAJORS.indexOf(k) : MINORS.indexOf(k);
	}
	
	public enum Type {
		MAJOR(StringUtil.EMPTY), MINOR("m");
		
		private final String display;
		
		Type(String display) {
			this.display = display;
		}
		
		public Type next() {
			return MAJOR.equals(this) ? MINOR : MAJOR;
		}
		
		public String toString() {
			return display;
		}
	}
}
