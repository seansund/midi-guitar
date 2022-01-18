package com.dex.midi.event.util;

public class BendEntry {
	private int bendValue;
	private long tick;

	public BendEntry(int bendValue, long tick) {
		super();
		
		this.bendValue = bendValue;
		this.tick = tick;
	}
	
	public int getBendValue() {
		return bendValue;
	}
	
	public void setBendValue(int bendValue) {
		this.bendValue = bendValue;
	}
	
	public long getTick() {
		return tick;
	}
	
	public void setTick(long tick) {
		this.tick = tick;
	}

}
