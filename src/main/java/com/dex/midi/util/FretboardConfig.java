package com.dex.midi.util;

import com.dex.midi.MidiConfig;
import com.dex.midi.model.GuitarPosition;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Level;

import static com.dex.midi.util.InputStreamLoader.buildBufferedReader;

public abstract class FretboardConfig<E> {
	
	@Getter
    private final int stringCount;
	@Getter
    private final int fretCount;
	
	private final E[][] values;
	
	public FretboardConfig() {
		super();
		
		MidiConfig cfg = MidiConfig.getInstance();
		
		this.stringCount = cfg.getStringCount();
		this.fretCount = cfg.getFretCount();
		
		values = createArray(stringCount, fretCount);
	}
	
	protected abstract E[][] createArray(int stringCount, int fretCount);
	
	protected void parseFile(String fileName, Class<?> clazz) {
		final String method = "parseFile";

        try (BufferedReader reader = buildBufferedReader(fileName, clazz)) {

            int lineNum = 0;
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                if (lineNum > 0) {
                    String[] cols = line.split(StringUtil.COMMA, -1);

                    // TODO this parsing probably needs to be a little better
                    int stringIndex = 0;
                    int fretIndex = lineNum - 1;
                    for (int i = cols.length - 1; i > 0; i--, stringIndex++) {
                        String s = cols[i];

                        if (s != null && !s.trim().isEmpty()) {
                            values[stringIndex][fretIndex] = processValue(stringIndex, fretIndex, s);
                        }
                    }
                }

                lineNum++;
            }
        } catch (IOException e) {
            SimpleLogger.log(Level.SEVERE, this, method, String.format("Error reading config file: %s", fileName), e);
        }
	}
	
	protected abstract E processValue(int stringIndex, int fretIndex, String s);
	
	public E getValue(GuitarPosition fret) {
		return getValue(fret.getStringIndex(), fret.getAdjustedFretIndex());
	}
	
	public E getValue(int stringIndex, int fretIndex) {
		E val = values[stringIndex][fretIndex];
		
		// wrap the frets after the 12th fret
		// should this be done for everyone or just those subclasses that need it?
		if (val == null && fretIndex > 11) {
			fretIndex = fretIndex - 12;
			val = values[stringIndex][fretIndex];
		}
		
		return val;
	}
}
