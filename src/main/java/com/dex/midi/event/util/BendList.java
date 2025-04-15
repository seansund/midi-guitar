package com.dex.midi.event.util;

import lombok.experimental.Delegate;

import java.util.ArrayList;
import java.util.List;

public class BendList implements List<BendEntry>, BendValues {

	@Delegate
	private final List<BendEntry> bends;
	
	public BendList() {
		bends = new ArrayList<>();
	}
	
	public BendList(List<BendEntry> bends) {
		this.bends = bends;
	}

	@Override
	public double[] getFourierValues() {
		// FastFourierTransform expects to get x^2 number of values
		// so set the size of the list so that x is the largest
		// int value where x^2 fits within bends.size()
		int size = (int)Math.pow((int)Math.sqrt(bends.size()), 2);
		assert(size <= bends.size());
		
		double[] values = new double[size];
		
		for (int i = 0; i < size; i++) {
			BendEntry e = bends.get(i);
			values[i++] = e.getBendValue();
		}
		
		return values;
	}
	
}
