package com.dex.midi.view;

import com.dex.midi.chord.*;
import com.dex.midi.util.StringUtil;
import com.dex.midi.view.AbstractMidiGuitarPlugin;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ChordMidiGuitarPlugin extends AbstractMidiGuitarPlugin<JComponent> {
	
	private static final String DEFAULT_TITLE = "Chord Midi Guitar Plugin";

	private Collection<JComponent> c;
	private JLabel label;
	final private ChordEventListenerSource p = new SimpleChordEventProducer();
	
	public ChordMidiGuitarPlugin() {
		super(DEFAULT_TITLE);
	}
	
	protected void initInternal() {
		super.initInternal();
		
		getMidiEventProducer().addMidiEventListener(new MidiChordListener(p));
	}
	
	@Override
	public Collection<JComponent> getDisplayComponents() {
		if (c == null) {
			JPanel d = new JPanel();
			d.setPreferredSize(new Dimension(200, 50));
			
			Border b = BorderFactory.createLineBorder(Color.BLACK);
			d.setBorder(b);
			
			label = new JLabel(StringUtil.EMPTY);
			
			Font f = label.getFont();
			Font newFont = new Font(f.getName(), f.getStyle(), (int)(f.getSize() * 2));
			label.setFont(newFont);
			
			d.add(label);
			
			p.addChordEventListener(new ChordEventHandler());
			
			c = new ArrayList<JComponent>(1);
			c.add(d);
		}
		
		return c;
	}
	
	public class ChordEventHandler implements ChordEventListener {
		@Override
		public void chordChange(List<Chord> chords) {
			if (label == null) {
				return;
			}

			final String text = chords.stream()
					.map(Chord::toString)
					.collect(Collectors.joining(","));

			label.setText(text);
			label.repaint();
		}

		public void close() {
		}
	}

}
