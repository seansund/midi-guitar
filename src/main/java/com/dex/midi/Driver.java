package com.dex.midi;

import com.dex.midi.event.MidiEventException;
import com.dex.midi.event.MidiEventListenerSource;
import com.dex.midi.event.MidiEventObservableSource;
import com.dex.midi.event.SimpleMidiEventProducer;
import com.dex.midi.handler.PrintMidiEventListener;
import com.dex.midi.util.SimpleLogger;

import javax.annotation.Nonnull;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Transmitter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class Driver implements MidiDriver {
	
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

	@Override
	public MidiEventListenerSource getMidiEventListenerSource() {
		return SimpleMidiEventProducer.getInstance();
	}

	@Override
	public MidiEventObservableSource getMidiEventObservableSource() {
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

	protected void init(MidiEventListenerSource p) {
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
	
	@Override
	public void run() {
		run(() -> {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {

                for (String line = in.readLine(); line != null; line = in.readLine()) {
                    if ("EXIT".equalsIgnoreCase(line)) {
                        throw new RuntimeException("Exit");
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException("Error reading stream", e);
            }
		});
	}

	public void run(@Nonnull DriverControl control) {
		final String method = "run";

		try {
			startup();
			getMidiEventProducer().addMidiEventListener(new PrintMidiEventListener());

			control.waitForClose();
		} catch (MidiUnavailableException e) {
			SimpleLogger.log(Level.SEVERE, this, method, "Midi resources unavailable", e);
		} catch (MidiEventException e) {
			SimpleLogger.log(Level.SEVERE, this, method, "Exception processing midi events", e);
		} finally {
			SimpleLogger.log(Level.INFO, this, method, "Closing down midi connection");
			close();
		}

	}

	@Override
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
		try (Driver d = new Driver()) {
			d.run();
		}

		System.exit(0);
	}
}
