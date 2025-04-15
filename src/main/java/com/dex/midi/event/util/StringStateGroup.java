package com.dex.midi.event.util;

import com.dex.midi.MidiConfig;
import com.dex.midi.event.PitchBendEvent;
import com.dex.midi.event.PitchEvent;

import java.util.List;

public class StringStateGroup {
	
	private final StringState[] status;
	
	public StringStateGroup(MidiConfig c) {
		status = new StringState[c.getStringCount()];
		
		for (int i = 0; i < status.length; i++) {
			status[i] = new StringState(i, c);
		}
	}
	
	public PitchEvent getRootEvent(int stringIndex) {
		return status[stringIndex].getRootEvent();
	}
	
	public void setRootEvent(int stringIndex, PitchEvent e) {
		status[stringIndex].setRootEvent(e);
	}
	
	public PitchBendEvent getBendEvent(int stringIndex) {
		return status[stringIndex].getBendEvent();
	}
	
	public void setBendEvent(int stringIndex, PitchBendEvent e) {
		status[stringIndex].setBendEvent(e);
	}
	
	public boolean isBendEventFired(int stringIndex) {
		return status[stringIndex].isBendEventFired();
	}
	
	public List<BendEntry> getBendList(int stringIndex) {
		return status[stringIndex].getBendList();
	}
	
	public void setBendList(int stringIndex, List<BendEntry> bendList) {
		status[stringIndex].setBendList(bendList);
	}
	
	public List<BendEntry> addPitchBend(int stringIndex, int bendValue, long tick) {
		return status[stringIndex].addPitchBend(bendValue, tick);
	}
	
	public void clearBendList(int stringIndex) {
		status[stringIndex].clearBendList();
	}
	
	public PitchBendEvent generatePitchBendEvent(int stringIndex) {
		return status[stringIndex].generatePitchBendEvent();
	}
	
	public StringState getStringState(int stringIndex) {
		return status[stringIndex];
	}
}
