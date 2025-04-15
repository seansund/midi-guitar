package com.dex.midi.event;

import com.dex.midi.model.Pitch;
import lombok.Getter;
import lombok.Setter;

import javax.sound.midi.ShortMessage;

@Getter
@Setter
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

    public void setBaseNote(Pitch basePitch) {
		this.basePitch = basePitch;
	}

	public int getBendAmount() {

		if (basePitch != null) {
			return basePitch.distance(bendPitch);
		}
		
		return 0;
	}
	
	public String toString() {
		return String.format(STRING_FORMAT, type, basePitch, bendPitch, stdDeviation, slope);
	}
}
