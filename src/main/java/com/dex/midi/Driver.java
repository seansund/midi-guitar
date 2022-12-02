package com.dex.midi;

import com.dex.midi.event.MidiEventListenerSource;
import com.dex.midi.event.MidiEventException;
import com.dex.midi.event.SimpleMidiEventProducer;
import com.dex.midi.handler.PrintMidiEventListener;
import com.dex.midi.util.SimpleLogger;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Transmitter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class Driver {
	
	private static final String EXIT = "exit";
	
	private static Driver instance = null;
	private boolean started = false;
	private Transmitter transmitter;
	private MidiGuitarReceiver receiver;
	
	public static Driver getInstance() {
		if (instance == null) {
			instance = new Driver();
		}
		return instance;
	}

	public MidiEventListenerSource getMidiEventProducer() {
		return SimpleMidiEventProducer.getInstance();
	}

	public MidiConfig getMidiConfig() {
		return new MidiConfig(getMidiEventProducer());
	}

	public Transmitter getTransmitter() throws MidiUnavailableException {
		return MidiSystem.getTransmitter();
	}
	
	public MidiGuitarReceiver getReceiver() {
		if (receiver == null) {
			receiver = new MidiGuitarReceiver(getMidiConfig());
		}
		return receiver;
	}

	protected void init(MidiEventListenerSource p) throws MidiEventException {
		// nothing to do
	}
	
	public void startup() throws MidiUnavailableException, MidiEventException {
		final String method = "startup";
		
		if (!started) {
			started = true;
			
			transmitter = getTransmitter();
			SimpleLogger.log(Level.INFO, this, method, "Transmitter created");
			
			init(getMidiEventProducer());
			transmitter.setReceiver(getReceiver());
		}
	}
	
	public void run() {
		final String method = "run";
		
		BufferedReader in = null;
		try {
			startup();
			getMidiEventProducer().addMidiEventListener(new PrintMidiEventListener());
			
			in = new BufferedReader(new InputStreamReader(System.in));
			String line = in.readLine();
			while (line != null) {
				if (Driver.EXIT.equalsIgnoreCase(line)) {
					break;
				}
				line = in.readLine();
			}
		} catch (MidiUnavailableException e) {
			SimpleLogger.log(Level.SEVERE, this, method, "Midi resources unavailable", e);
		} catch (IOException e) {
			SimpleLogger.log(Level.SEVERE, this, method, "Input stream error", e);
		} catch (MidiEventException e) {
			SimpleLogger.log(Level.SEVERE, this, method, "Exception processing midi events", e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException ignore) { }
			}
			
			close();
		}
	}
	
	public void close() {
		final String method = "close";

		if (transmitter != null) {
			SimpleLogger.log(Level.INFO, this, method, "Closing transmitter...");
			transmitter.close();
			transmitter = null;
		}
		
		if (receiver != null) {
			SimpleLogger.log(Level.INFO, this, method, "Closing receiver...");
			receiver.close();
			receiver = null;
		}
		
		started = false;
	}
	
	public static void main(String[] args) {
		new Driver().run();

		System.exit(0);
	}
}
