package com.dex.midi.chord;

import com.dex.midi.util.SimpleLogger;

import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

public enum ChordStructure {
	MAJOR("", 1, 3, 5),
	MINOR("m", 1, -3, 5),
	DIMINISHED("dim", 1, -3, -5),
	AUGMENTED("aug", 1, 3, -6), // b6 = #5
	DOM7("7", 1, 3, 5, -7),
	MAJOR7("maj7", 1, 3, 5, 7),
	MINOR7("m7", 1, -3, 5, -7),
	DIMINISHED7("dim7", 1, -3, -5, -7),
	AUGMENTED7("aug7", 1, 3, -6, -7), // b6 = #5
	MINORMAJOR7("mmaj7", 1, -3, 5, 7),
	AUGMENTEDMAJOR7("aug7", 1, 3, -6, 7), // b6 = #5
	SEVENFLAT5("7b5", 1, 3, -5, -7),
	SEVENSHARP5("7#5", 1, 3, -6, -7), // b6 = #5
	MINORSEVENSHARP5("m7#5", 1, -3, -6, -7), // b6 = #5
	SEVENSHARP9("7#9", 1, -3, -7), // b3 = #2
	SEVENFLAT9("7b9", 1, -2, 3, 5, -7),
	SUS2("sus2", 1, 2, 5),
	SEVENSUS2("7sus2", 1, 2, 5, -7),
	SUS4("sus4", 1, 4, 5),
	SEVENSUS4("7sus4", 1, 4, 5, -7),
	ADD2("add2", 1, 2, 3, 5),
	MINORADD2("madd2", 1, 2, -3, 5),
	ADD4("add4", 1, 3, 4, 5),
	MINORADD4("madd4", 1, -3, 4, 5),
	SEVENADD4("7add4", 1, 3, 4, 5, -7),
	MINORSEVENADD4("m7add4", 1, -3, 4, 5, -7),
	MINORSEVENFLAT5("m7b5", 1, -3, -5, -7),
	FIVE("5", 1, 5),
	SIX("6", 1, 3, 5, 6),
	MINOR6("m6", 1, -3, 5, 6),
	SIX9("69", 1, 2, 3, 5, 6),
	MINOR69("m69", 1, 2, -3, 5, 6),
	NINE("9", 1, 2, 3, 5, -7),
	MINOR9("m9", 1, 2, -3, 5, -7),
	MAJOR9("maj9", 1, 2, 5, 7),
	MINORSHARP5("m#5", 1, -3, -6),
	ROOT("", 1);
	
	private static final Map<String, ChordStructure> MAP = new HashMap<>();
	private static final Set<String> FAILED_MAP = new HashSet<>();
	
	static {
		for (ChordStructure c : ChordStructure.values()) {
			MAP.put(ChordStructure.buildIntervalCode(c.intervals), c);
		}
		
		// Known ChordStructure that doesn't exist
		MAP.put(ChordStructure.buildIntervalCode(1, 4, 6), null);
	}
	
	private final String display;
	private final int[] intervals;
	
	ChordStructure(String display, int... intervals) {
		this.display = display;
		this.intervals = intervals;
	}
	
	private static String buildIntervalCode(List<Integer> intervals) {
		if (intervals == null) {
			return "";
		}

		return intervals.stream()
				.sorted(IntervalComparator.getInstance())
				.map(Object::toString)
				.collect(Collectors.joining(","));
	}
	
	private static String buildIntervalCode(int... intervals) {
		final List<Integer> l = Arrays.stream(intervals)
				.boxed()
				.collect(Collectors.toList());
		
		return buildIntervalCode(l);
	}
	
	public static ChordStructure getChordStructure(List<Integer> intervals) {
		final String method = "getChordStructure";
		
		String intervalCode = ChordStructure.buildIntervalCode(intervals);
		
		if (MAP.containsKey(intervalCode)) {
			return MAP.get(intervalCode);
		} else if (!FAILED_MAP.contains(intervalCode)) {
			FAILED_MAP.add(intervalCode);

			SimpleLogger.log(Level.WARNING, ChordStructure.class, method, "Unable to find ChordStructure: {0}", intervalCode);
		}
		
		return null;
	}
	
	public String toString() {
		return display;
	}
	
	private static class IntervalComparator implements Comparator<Integer> {

		private static IntervalComparator instance = null;
		
		public static Comparator<Integer> getInstance() {
			if (instance == null) {
				instance = new IntervalComparator();
			}
			return instance;
		}
		
		@Override
		public int compare(Integer int1, Integer int2) {
			if (int1 != null && int2 != null) {
				int compare = Math.abs(int1) - Math.abs(int2);
				
				if (compare == 0) {
					return int1 - int2;
				}

				return compare;
			}
			
			return -1;
		}
	}
}
