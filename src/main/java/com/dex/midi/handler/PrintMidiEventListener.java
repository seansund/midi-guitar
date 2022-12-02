package com.dex.midi.handler;

import com.dex.midi.event.MidiEventListener;
import com.dex.midi.event.PitchBendEvent;
import com.dex.midi.event.PitchEvent;
import com.dex.midi.model.GuitarPosition;
import com.dex.midi.util.StringFormat;
import com.dex.midi.util.StringUtil;

public class PrintMidiEventListener implements MidiEventListener {
	
	private static final String PADDING = "          ";
	
	private PitchEvent[] events = new PitchEvent[] { null, null, null, null, null, null };

	@Override
	public void noteOn(PitchEvent e) {
		events[e.getStringIndex()] = e;
		
		print(e.getStringIndex());
	}

	@Override
	public void noteOff(PitchEvent e) {
		events[e.getStringIndex()] = null;
		
		print(e.getStringIndex());
	}

	@Override
	public void pitchBend(PitchBendEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void guitarPositions(GuitarPosition[] positions) {
		// TODO
	}

	protected void print(int stringIndex) {
		StringBuffer buf = new StringBuffer(80);
		buf.append(StringUtil.PIPE);
		
		for (int i = events.length - 1; i >= 0; i--) {
			PitchEvent e = events[i];
			
			StringBuffer b = new StringBuffer(12);
			
			if (i == stringIndex) {
				b.append(StringUtil.STAR);
			} else {
				b.append(StringUtil.SPACE);
			}
			
			if (e == null) {
				b.append(PADDING);
			} else {
				b.append(StringUtil.SPACE).
					append(StringFormat.asString(e.getFretIndex(), 2)).
					append(StringUtil.SPACE).
					append(StringFormat.asString(e.getPitch(), 2)).
					append(StringUtil.SPACE).
					append(StringFormat.asString(e.getVelocity(), 3, true));
			}
			
			buf.append(b).append(StringUtil.PIPE);
		}
		
		System.out.println(buf.toString());
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

}
