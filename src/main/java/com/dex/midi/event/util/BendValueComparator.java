package com.dex.midi.event.util;

public class BendValueComparator implements BendEntryComparator {
	
	private static BendEntryComparator instance = null;
	
	private BendEntryComparator inverse;
	private boolean invert = false;
	
	public static BendEntryComparator getInstance() {
		if (instance == null) {
			instance = new BendValueComparator();
		}
		return instance;
	}
	
	public BendValueComparator() {
		super();
		
		this.inverse = new BendValueComparator(this, !invert);
	}
	
	public BendValueComparator(BendEntryComparator inverse, boolean invert) {
		super();
		
		this.inverse = inverse;
		this.invert = invert;
	}

	@Override
	public int compare(BendEntry e1, BendEntry e2) {
		int compare = 0;
		
		if (e1 != null && e2 != null) {
			compare = e1.getBendValue() - e2.getBendValue();
		}
		
		if (invert) {
			compare *= -1;
		}
		
		return compare;
	}

	@Override
	public BendEntryComparator invert() {
		return inverse;
	}
}
