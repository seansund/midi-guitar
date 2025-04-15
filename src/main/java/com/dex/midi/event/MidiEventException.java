package com.dex.midi.event;

import java.io.Serial;

public class MidiEventException extends Exception {

	@Serial
	private static final long serialVersionUID = -5427355585761839681L;

	public MidiEventException() {
		super();
	}

	public MidiEventException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public MidiEventException(String arg0) {
		super(arg0);
	}

	public MidiEventException(Throwable arg0) {
		super(arg0);
	}

}
