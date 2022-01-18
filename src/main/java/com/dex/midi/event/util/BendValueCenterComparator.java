package com.dex.midi.event.util;

public class BendValueCenterComparator extends BendValueComparator {
	
	private int bendCenter;
	
	public BendValueCenterComparator(int bendCenter) {
		super();
		
		this.bendCenter = bendCenter;
	}

	@Override
	public int compare(BendEntry e1, BendEntry e2) {
		int compare = 0;
		
		if (e1 != null && e2 != null) {
			int v1 = Math.abs(e1.getBendValue() - bendCenter);
			int v2 = Math.abs(e2.getBendValue() - bendCenter);
			
			compare = v1 - v2;
			
			// TODO add null compare
		}
		
		return compare;
	}
	
}
