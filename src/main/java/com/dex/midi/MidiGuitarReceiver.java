package com.dex.midi;

import com.dex.midi.event.ListenerMidiEventProducer;
import com.dex.midi.event.MidiEventProducer;
import com.dex.midi.event.PitchEvent;
import com.dex.midi.event.PitchEvent.Type;
import com.dex.midi.event.util.StringState;
import com.dex.midi.event.util.StringStateGroup;
import com.dex.midi.model.GuitarPosition;
import com.dex.midi.model.Pitch;
import com.dex.midi.util.SimpleLogger;

import javax.sound.midi.*;
import java.util.logging.Level;

public class MidiGuitarReceiver implements Receiver {
	
	// 01111111
	private static final int MASK = 0x007f;
	
	private MidiEventProducer p = null;
	
	// TODO get from configuration
	private MidiConfig config = null;
	private StringStateGroup stringStateGroup = null;
	
	public MidiGuitarReceiver() {
		this(MidiConfig.getInstance());
	}
	
	public MidiGuitarReceiver(MidiConfig config) {
		this.config = config;
		this.p = config.getProducer();
		
		this.stringStateGroup = new StringStateGroup(config);
	}
	
	@Override
	public void close() {
		p.close();
	}
	
	@Override
	public void send(MidiMessage msg, long tick) {
		if (msg instanceof ShortMessage) {
			handleShortMidiMessage((ShortMessage)msg, tick);
		} else if (msg instanceof SysexMessage) {
			handleSysexMidiMessage((SysexMessage)msg, tick);
		} else if (msg instanceof MetaMessage) {
			handleMetaMidiMessage((MetaMessage)msg, tick);
		}
	}
	
	public void handleShortMidiMessage(ShortMessage msg, long tick) {
		final String method = "handleShortMidiMessage";
		
		switch (msg.getCommand()) {
			case ShortMessage.NOTE_ON:
			case ShortMessage.NOTE_OFF: {
				handleNoteMessage(msg, tick);
				
				break;
			}
			case ShortMessage.PITCH_BEND: {
				handlePitchBendMessage(msg, tick);
				
				break;
			}
			default: {
				SimpleLogger.log(Level.WARNING, this, method, "Unhandled command: {0}", msg.getCommand());
				break;
			}
		}
	}
	
	protected void handleNoteMessage(ShortMessage msg, long tick) {
		
		// determine which string was played
		int stringIndex = getStringIndex(msg.getChannel());
		
		// determine which fret was played on that string
		int fretIndex = getFretIndex(stringIndex, msg.getData1());
		
		// clear the state of string bends for this string
		StringState stringState = stringStateGroup.getStringState(stringIndex);
		
		stringState.clearBendList();
		
		// store the values in a pitch event
		PitchEvent e = new PitchEvent(msg, tick, new GuitarPosition(stringIndex, fretIndex));
		if (Type.NOTE_ON.equals(e.getType())) {
			// update the state of the string since this is a new note event
			stringState.addPitchBend(config.getBendCenter().intValue(), tick);
			stringState.setRootEvent(e);
			
			p.fireNoteOn(e);
		} else {
			// clear the root note on the string since this is a note off event
			stringState.setRootEvent(null);
			
			p.fireNoteOff(e);
		}
	}
	
	protected int getStringIndex(int channel) {
		int stringIndex = channel - config.getChannelOffset();
		
		return stringIndex;
	}
	
	protected int getFretIndex(int stringIndex, int pitch) {
		
		int offset = 40;
		switch (stringIndex) {
			case 0:
				offset += 5;
			case 1:
				offset += 4;
			case 2:
				offset += 5;
			case 3:
				offset += 5;
			case 4:
				offset += 5;
				break;
			default:
				break;
		}
		
		return pitch - offset - config.getStringOffset(stringIndex);
	}
	
	protected Pitch getPitch(int midiPitch) {
		
		return new Pitch(midiPitch);
	}
	
	protected void handlePitchBendMessage(ShortMessage msg, long tick) {
		int stringIndex = getStringIndex(msg.getChannel());
		
		int bendValue = determineBendValue(msg);
		
		StringState stringState = stringStateGroup.getStringState(stringIndex);
		stringState.addPitchBend(bendValue, tick);
	}
	
	protected int determineBendValue(ShortMessage msg) {
		int lsb = msg.getData1();
		int msb = msg.getData2();
		
		// not sure if the MASK is necessary here but the information
		// is in the first 7 bits
		// MSB needs to be shifted 7 bits to create the 14 bit value
		int pitchBend = ((msb & MASK) << 7) | (lsb & MASK);
		
		return pitchBend;
	}
	
	protected void handleSysexMidiMessage(SysexMessage msg, long tick) {
		final String method = "handleSysexMidiMessage";
		// TODO implement
		SimpleLogger.log(Level.INFO, this, method, "SysexMessage: {0}", msg);
	}
	
	protected void handleMetaMidiMessage(MetaMessage msg, long tick) {
		final String method = "handleMetaMidiMessage";
		// TODO implement
		SimpleLogger.log(Level.INFO, this, method, "MetaMessage: {0}", msg);
	}
	
	public MidiEventProducer getMidiEventProducer() {
		return p;
	}
}
