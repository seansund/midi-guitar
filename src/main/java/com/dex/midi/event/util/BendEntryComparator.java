package com.dex.midi.event.util;

import java.util.Comparator;


public interface BendEntryComparator extends Comparator<BendEntry> {
	
	public BendEntryComparator invert();
}
