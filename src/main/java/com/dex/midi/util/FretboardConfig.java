package com.dex.midi.util;

import com.dex.midi.model.GuitarPosition;
import com.dex.midi.MidiConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public abstract class FretboardConfig<E> {
	
	private int stringCount;
	private int fretCount;
	
	private E[][] values;
	
	public FretboardConfig() {
		super();
		
		MidiConfig cfg = MidiConfig.getInstance();
		
		this.stringCount = cfg.getStringCount();
		this.fretCount = cfg.getFretCount();
		
		values = createArray(stringCount, fretCount);
	}
	
	protected abstract E[][] createArray(int stringCount, int fretCount);
	
	@SuppressWarnings("rawtypes")
	protected void parseFile(String fileName, Class clazz) {
		final String method = "parseFile";
		
		BufferedReader reader = null;
		
		try {
			InputStream in = clazz.getResourceAsStream(fileName);
			reader = new BufferedReader(new InputStreamReader(in));
			
			String line = reader.readLine();
			int lineNum = 0;
			while (line != null) {
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
				line = reader.readLine();
			}
		} catch (IOException e) {
			SimpleLogger.log(Level.SEVERE, this, method, String.format("Error reading config file: %s", fileName), e);
		} finally {
			if (reader != null) {
				try { reader.close(); } catch (IOException ignore) { }
			}
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

	public int getStringCount() {
		return stringCount;
	}

	public int getFretCount() {
		return fretCount;
	}
	
}
