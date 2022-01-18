package com.dex.midi.event;

import com.dex.midi.model.Pitch;

import javax.sound.midi.ShortMessage;

public class PitchBendEvent extends MidiEvent<ShortMessage> {
	
	private static final String STRING_FORMAT = "[PitchBend(%s): %s-%s, %f, %f]";

	private Pitch basePitch;
	private double stdDeviation;
	private double slope;
	private BendType type;
	private Pitch bendPitch;
	
	public PitchBendEvent() {
		super();
	}
	
	public PitchBendEvent(PitchEvent ne, double stdDeviation, double slope) {
		this(ne.getMessage(), ne.getTick(), ne.getPitch(), stdDeviation, slope);
	}
	
	public PitchBendEvent(ShortMessage msg, long tick, Pitch basePitch, double stdDeviation, double slope) {
		super(msg, tick);
		
		this.basePitch = this.bendPitch = basePitch;
		this.stdDeviation = stdDeviation;
		this.slope = slope;
		
		// TODO determine type from slope
		type = BendType.NONE;
		
		// TODO determine resulting bend note based on stdDev
	}
	
	public double getStdDeviation() {
		return stdDeviation;
	}

	public void setStdDeviation(double stdDeviation) {
		this.stdDeviation = stdDeviation;
	}

	public double getSlope() {
		return slope;
	}

	public void setSlope(double slope) {
		this.slope = slope;
	}

	public BendType getType() {
		return type;
	}

	public void setType(BendType type) {
		this.type = type;
	}

	public Pitch getBasePitch() {
		return basePitch;
	}

	public void setBaseNote(Pitch basePitch) {
		this.basePitch = basePitch;
	}

	public Pitch getBendPitch() {
		return bendPitch;
	}

	public void setBendPitch(Pitch bendPitch) {
		this.bendPitch = bendPitch;
	}
	
	public int getBendAmount() {
		int amount = 0;
		
		if (basePitch != null) {
			amount = basePitch.distance(bendPitch);
		}
		
		return amount;
	}
	
	public String toString() {
		return String.format(STRING_FORMAT, type, basePitch, bendPitch, stdDeviation, slope);
	}
}
