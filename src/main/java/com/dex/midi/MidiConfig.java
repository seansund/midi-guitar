package com.dex.midi;

import com.dex.midi.event.ListenerMidiEventProducer;
import com.dex.midi.event.MidiEventProducer;
import com.dex.midi.event.SimpleMidiEventProducer;
import com.dex.midi.model.Note;
import com.dex.midi.model.Pitch;

import java.util.Properties;

public class MidiConfig {
	
	public static final int BEND_MAX = 16383;
	public static final double STDDEV_THRESHOLD = 15.0;
	
	private static MidiConfig instance = null;

	private MidiEventProducer p = null;
	
	private final Note[] baseOpenNotes = new Note[] { Note.E, Note.B, Note.G, Note.D, Note.A, Note.E };
	private final Pitch[] baseOpenPitches = new Pitch[] { new Pitch(Note.E, 4), new Pitch(Note.B, 3), new Pitch(Note.G, 3), new Pitch(Note.D, 3), new Pitch(Note.A, 2), new Pitch(Note.E, 2) };
	
	private int channelOffset = 0;
	private int[] stringOffset = new int[] { 0, 0, 0, 0, 0, 0 };
	private int bendCountThreshold = 1024;
	private Double bendCenter = 0.0;
	private int stringCount = 6;
	private int fretCount = 21;
	private Note[] openNotes = null;
	private Pitch[] openPitches = null;
	private int keyIndex = 0;

	public MidiConfig() {
		this(SimpleMidiEventProducer.getInstance());
	}

	public MidiConfig(MidiEventProducer p) {
		super();
		this.p = p;
	}

	public static MidiConfig getInstance() {
		if (instance == null) {
			instance = new MidiConfig();
			instance.init();
		}
		return instance;
	}
	
	public MidiConfig init() {
		return this.init((String)null);
	}
	
	public MidiConfig init(String configFile) {
		// TODO implement
		
		return this;
	}
	
	public MidiConfig init(Properties props) {
		// TODO implement
		
		return this;
	}
	
	public Double getBendCenter() {
		return bendCenter;
	}
	public void setBendCenter(Double bendCenter) {
		this.bendCenter = bendCenter;
	}
	public int getBendCountThreshold() {
		return bendCountThreshold;
	}
	public void setBendCountThreshold(int bendCountThreshold) {
		this.bendCountThreshold = bendCountThreshold;
	}
	public MidiEventProducer getProducer() {
		return p;
	}
	public int getChannelOffset() {
		return channelOffset;
	}
	public void setChannelOffset(int channelOffset) {
		this.channelOffset = channelOffset;
	}
	public int getStringOffset(int stringIndex) {
		return stringOffset[stringIndex];
	}
	public int[] getStringOffset() {
		return stringOffset;
	}
	public void setStringOffset(int stringIndex, int offset) {
		this.stringOffset[stringIndex] = offset;
		openNotes = null;
	}
	public void setStringOffset(int[] stringOffset) {
		this.stringOffset = stringOffset;
	}
	public int getStringCount() {
		return stringCount;
	}
	public int getFretCount() {
		return fretCount;
	}
	public Note[] getOpenNotes() {
		if (openNotes == null) {
			Note[] notes = new Note[baseOpenNotes.length];
			
			for (int i = 0; i < baseOpenNotes.length; i++) {
				int offset = stringOffset[i];
				Note base = baseOpenNotes[i];
				
				notes[i] = base.transpose(offset);
			}
			
			openNotes = notes;
		}
		
		return openNotes;
	}
	public Note getOpenNote(int stringIndex) {
		// TODO validate stringIndex in range
		return getOpenNotes()[stringIndex];
	}
	
	public Pitch[] getOpenPitches() {
		if (openPitches == null) {
			Pitch[] pitches = new Pitch[baseOpenPitches.length];
			
			for (int i = 0; i < baseOpenPitches.length; i++) {
				int offset = stringOffset[i];
				Pitch base = baseOpenPitches[i];
				
				pitches[i] = base.transpose(offset);
			}
			
			openPitches = pitches;
		}
		
		return openPitches;
	}
	public Pitch getOpenPitch(int stringIndex) {
		// TODO validate stringIndex in range
		return getOpenPitches()[stringIndex];
	}

	public int getKeyIndex() {
		return keyIndex;
	}

	public void setKeyIndex(int keyIndex) {
		this.keyIndex = keyIndex;
	}

}
