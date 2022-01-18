package com.dex.midi.event.util;

public class BendTickComparator implements BendEntryComparator {
	
	private static BendEntryComparator instance = null;
	
	private BendEntryComparator inverse;
	
	public static BendEntryComparator getInstance() {
		if (instance == null) {
			instance = new BendTickComparator();
		}
		return instance;
	}
	
	public BendTickComparator() {
		super();
		
		this.inverse = new BendTickComparator(this);
	}
	
	public BendTickComparator(BendEntryComparator inverse) {
		super();
		
		this.inverse = inverse;
	}

	@Override
	public int compare(BendEntry e1, BendEntry e2) {
		int compare = 0;
		
		if (e1 != null && e2 != null) {
			compare = (int)(e1.getTick() - e2.getTick());
		}
		
		return compare;
	}

	@Override
	public BendEntryComparator invert() {
		return inverse;
	}
}
