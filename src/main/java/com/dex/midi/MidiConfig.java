package com.dex.midi;

import com.dex.midi.event.MidiEventProducer;
import com.dex.midi.event.SimpleMidiEventProducer;
import com.dex.midi.model.Note;
import com.dex.midi.model.Pitch;
import lombok.Getter;
import lombok.Setter;

import java.util.Properties;

public class MidiConfig {
	
	public static final int BEND_MAX = 16383;
	public static final double STDDEV_THRESHOLD = 15.0;
	
	private static MidiConfig instance;

	private final MidiEventProducer p;
	
	private final Note[] baseOpenNotes = new Note[] { Note.E, Note.B, Note.G, Note.D, Note.A, Note.E };
	private final Pitch[] baseOpenPitches = new Pitch[] { new Pitch(Note.E, 4), new Pitch(Note.B, 3), new Pitch(Note.G, 3), new Pitch(Note.D, 3), new Pitch(Note.A, 2), new Pitch(Note.E, 2) };
	
	@Setter
    @Getter
    private int channelOffset = 0;
	@Getter
    private int[] stringOffset = new int[] { 0, 0, 0, 0, 0, 0 };
	@Setter
    @Getter
    private int bendCountThreshold = 1024;
	@Setter
    @Getter
    private Double bendCenter = 0.0;
	@Getter
    private final int stringCount = 6;
	@Getter
    private final int fretCount = 21;
	private Note[] openNotes;
	private Pitch[] openPitches;
	@Setter
    @Getter
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

    public MidiEventProducer getProducer() {
		return p;
	}

    public int getStringOffset(int stringIndex) {
		return stringOffset[stringIndex];
	}

    public void setStringOffset(int stringIndex, int offset) {
		this.stringOffset[stringIndex] = offset;
		openNotes = null;
	}

	public void setStringOffset(int[] stringOffset) {
		this.stringOffset = stringOffset;
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

}
