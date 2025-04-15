package com.dex.midi.chord;

import com.dex.midi.model.Note;
import com.dex.midi.model.Pitch;
import com.dex.midi.util.CountingSet;
import com.dex.midi.util.SimpleLogger;

import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class Chord implements Comparable<Chord> {

	private static final int[] MAJOR_INTERVAL = { 1, -2, 2, -3, 3, 4, -5, 5, -6, 6, -7, 7 };
	
	private final Note root;
	private final ChordStructure structure;
	private final Note first;
	
	public Chord(Note root, ChordStructure structure) {
		this(root, structure, root);
	}
	
	public Chord(Note root, ChordStructure structure, Note first) {
		super();
		
		this.root = root;
		
		if (structure == null) {
			throw new IllegalArgumentException("ChordStructure cannot be null");
		}
		this.structure = structure;
		
		if (first == null) {
			first = root;
		}
		this.first = first;
	}
	
	public Chord(Note root, List<Integer> intervals, Note first) {
		this(root, ChordStructure.getChordStructure(intervals), first);
	}
	
	public static List<Chord> identifyChords(Collection<StringPitch> stringPitches) {

		if (stringPitches == null || stringPitches.isEmpty()) {
			return new ArrayList<>();
		}

		final Set<Note> noteSet = stringPitches.stream()
				.sorted()
				.map(StringPitch::getPitch)
				.filter(Objects::nonNull)
				.map(Pitch::getNote)
				.filter(Objects::nonNull)
				.collect(CountingSet.collector());

		return Chord.processNoteSet(noteSet);
	}
	
	public static List<Chord> identifyChords(Note[] stringNotes) {

		if (stringNotes == null || stringNotes.length == 0) {
			return new ArrayList<>();
		}
		
		final Set<Note> notes = Arrays.stream(stringNotes)
				.sorted(Collections.reverseOrder())
				.collect(CountingSet.collector());

		return Chord.processNoteSet(notes);
	}
	
	protected static List<Chord> processNoteSet(final Set<Note> noteSet) {

		if (noteSet == null || noteSet.isEmpty()) {
			return new ArrayList<>();
		}

		// try each of the notes as the root of the chord
		final Note first = noteSet.stream().findFirst().get();

		final List<Chord> chords = noteSet.stream()
				.map(root -> Chord.processNoteSet(root, noteSet, first))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.sorted()
				.collect(Collectors.toList());

		// Sort according to the natural sort order (chord structure priority)
		return Chord.processChordList(chords);
	}
	
	protected static List<Chord> processChordList(final List<Chord> chords) {

		if (chords.stream().anyMatch(Chord::isMajor)) {
			return chords.stream()
					.filter(Chord::isMajor)
					.collect(Collectors.toList());
		}

		return chords.stream()
				.sorted()
				.collect(Collectors.toList());
	}

	private static Set<Note> removeFirst(Set<Note> noteSet, Note first) {
		final Set<Note> copy = new CountingSet<>(noteSet);

		copy.remove(first);

		return copy;
	}
	
	protected static Optional<Chord> processNoteSet(Note root, Set<Note> origNoteSet, Note first) {
		final Set<Note> noteSet = (!root.equals(first))
				? removeFirst(origNoteSet, first)
				: origNoteSet;

		List<Integer> intervals = Chord.calculateIntervals(root, noteSet);
		
		try {
			return Optional.of(new Chord(root, intervals, first));
		} catch (Throwable ignore) {
			return Optional.empty();
		}
	}
	
	public static List<Integer> calculateIntervals(final Note root, final Set<Note> noteSet) {

		return noteSet.stream()
				.map(n -> Note.indexOf(n, root))
				.map(index -> MAJOR_INTERVAL[index])
				.collect(Collectors.toList());
	}
	
	public boolean isInversion() {
		return first != null && !root.equals(first);
	}
	
	public String toString() {
		final StringBuilder buf = new StringBuilder(20);
		
		buf.append(root).append(structure);
		if (isInversion()) {
			buf.append("/").append(first);
		}
		
		return buf.toString();
	}

	public boolean isMajor() {
		return ChordStructure.MAJOR.equals(this.structure) && !this.isInversion();
	}
	
	@Override
	public int compareTo(Chord c) {
		return structure.compareTo(c.structure);
	}
	
	public static void main(String[] args) {
		final String method = "main";
		
		List<Note> l1 = new ArrayList<>(6);
		l1.add(Note.E);
		l1.add(Note.C);
		l1.add(Note.G);
		l1.add(Note.E);
		l1.add(Note.C);
		
		List<Chord> c1 = Chord.identifyChords(l1.toArray(new Note[0]));
		SimpleLogger.log(Level.INFO, Chord.class, method, "{0}", c1);
		
		
		List<StringPitch> l2 = new ArrayList<>(6);
		l2.add(new StringPitch(5, new Pitch(Note.Fsharp, 4)));
		l2.add(new StringPitch(4, new Pitch(Note.D, 4)));
		l2.add(new StringPitch(3, new Pitch(Note.A, 3)));
		l2.add(new StringPitch(2, new Pitch(Note.D, 3)));
		l2.add(new StringPitch(1, new Pitch(Note.A, 2)));
		l2.add(new StringPitch(0, new Pitch(Note.Fsharp, 2)));
		
		List<Chord> c2 = Chord.identifyChords(l2);
		SimpleLogger.log(Level.INFO, Chord.class, method, "{0}", c2);
		
		
		Set<StringPitch> l3 = new HashSet<>(6);
		l3.add(new StringPitch(5, new Pitch(Note.G, 4)));
		l3.add(new StringPitch(4, new Pitch(Note.D, 4)));
		l3.add(new StringPitch(3, new Pitch(Note.G, 3)));
		l3.add(new StringPitch(2, new Pitch(Note.D, 3)));
		l3.add(new StringPitch(1, new Pitch(Note.B, 2)));
		l3.add(new StringPitch(0, new Pitch(Note.G, 2)));
		
		List<Chord> c3 = Chord.identifyChords(l3);
		SimpleLogger.log(Level.INFO, Chord.class, method, "{0}", c3);
	}
}
