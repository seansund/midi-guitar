package com.dex.midi.event.util;

import com.dex.midi.MidiConfig;
import com.dex.midi.event.BendType;
import com.dex.midi.event.PitchBendEvent;
import com.dex.midi.event.PitchEvent;
import com.dex.midi.util.SimpleLogger;
import com.dex.midi.util.StatisticUtil;
import com.dex.midi.util.StringUtil;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

public class StringState {
	
	private static final String LOG_PATH = "/home/seansund/guitar/logs/";
	
	private int stringIndex;
	private MidiConfig config;
	
	private PitchEvent rootEvent;
	private PitchBendEvent bendEvent;
	private BendList bendList = new BendList();
	
	private boolean logEnabled = false;
	
	public StringState(int stringIndex, MidiConfig c) {
		super();
		
		this.stringIndex = stringIndex;
		
		if (c == null) {
			this.config = new MidiConfig();
		} else {
			this.config = c;
		}
	}
	
	public int getStringIndex() {
		return stringIndex;
	}
	
	public PitchEvent getRootEvent() {
		return rootEvent;
	}
	
	public void setRootEvent(PitchEvent e) {
		rootEvent = e;
	}
	
	public PitchBendEvent getBendEvent() {
		return bendEvent;
	}
	
	public void setBendEvent(PitchBendEvent e) {
		bendEvent = e;
	}
	
	public boolean isBendEventFired() {
		return bendEvent != null;
	}
	
	public int getBendCount() {
		return bendList.size();
	}
	
	public BendList getBendList() {
		return bendList;
	}
	
	public void setBendList(List<BendEntry> bendList) {
		this.bendList = bendList instanceof BendList ? (BendList)bendList : new BendList(bendList);
	}
	
	public List<BendEntry> addPitchBend(int bendValue, long tick) {
		
		bendList.add(new BendEntry(bendValue, tick));
		
		processBendList();
		
		return bendList;
	}
	
	public void clearBendList() {
		
		if (rootEvent != null) {
			log(stringIndex, bendList, rootEvent, bendEvent);
		}
		bendList.clear();
		
		bendEvent = null;
	}
	
	protected void log(int stringIndex, List<BendEntry> bendList, PitchEvent rootEvent, PitchBendEvent bendEvent) {
		if (logEnabled) {
			logInternal(stringIndex, bendList, rootEvent, bendEvent);
		}
	}
	
	protected void logInternal(int stringIndex, List<BendEntry> bendList, PitchEvent rootEvent, PitchBendEvent bendEvent) {
		final String method = "logInternal";
		
		// make a shallow copy of the array
		List<BendEntry> l = new ArrayList<BendEntry>(bendList);
		
		// determine log file name
		String fileName = String.format("%sbendList-%d-%d.csv", LOG_PATH, stringIndex, Long.toHexString(rootEvent.getTick()));
		File file = new File(fileName);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		
		if (bendEvent == null) {
			bendEvent = generatePitchBendEvent();
		}
		SimpleLogger.log(Level.INFO, this, method, bendEvent.toString());
		
		// get the output buffer
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), Charset.defaultCharset()));
			
			// write out the header
			writer.write("Note,BendNote,StdDev,Slope,EventType,Distance,Tick,Bend Value,");
			writer.newLine();
			Object[] vals = new Object[6];
			Object[] defaultVals = new Object[6];
			
			int index = 0;
			vals[index++] = rootEvent.getPitch();
			
			if (bendEvent != null) {
				vals[index++] = bendEvent.getBendPitch();
				vals[index++] = bendEvent.getStdDeviation();
				vals[index++] = bendEvent.getSlope();
				vals[index++] = bendEvent.getType();
				vals[index++] = bendEvent.getBendAmount();
			}
			
			for (BendEntry entry : l) {
				StringBuffer line = new StringBuffer(100);
				
				// this is a little kludgy
				for (Object val : vals) {
					if (val != null) {
						line.append(val);
					}
					line.append(StringUtil.COMMA);
				}
				vals = defaultVals;
				
				line.append(entry.getTick()).
					append(StringUtil.COMMA).
					append(entry.getBendValue()).
					append(StringUtil.COMMA);
				
				writer.write(line.toString());
				writer.newLine();
			}
		} catch (FileNotFoundException e) {
			SimpleLogger.log(Level.WARNING, this, method, "Unable to write to file", e);
		} catch (IOException e) {
			SimpleLogger.log(Level.WARNING, this, method, "Error writing to file", e);
		} finally {
			if (writer != null) {
				try {
					writer.flush();
					writer.close();
				} catch (IOException ignore) { }
				writer = null;
			}
		}
	}
	
	protected void processBendList() {
		final String method = "processBendList";
		
		if ((getBendCount() % config.getBendCountThreshold()) == 0) {
			PitchBendEvent e = generatePitchBendEvent();
			
			// make sure stdDev is within range that we should take action
			if (BendType.NONE.equals(e.getType()) && !isBendEventFired()) {
				SimpleLogger.log(Level.INFO, this, method, "Sending PitchBendEvent: {0}", e.toString());
				
				setBendEvent(e);
				
				// create and fire event
				config.getProducer().firePitchBend(e);
			}
		}
	}
	
	public PitchBendEvent generatePitchBendEvent() {
		BendList bendList = getBendList();
		
		FastFourierTransformer transform = new FastFourierTransformer(DftNormalization.STANDARD);
		double[] vals = bendList.getForierValues();
		Complex[] result = transform.transform(vals, TransformType.FORWARD);
		System.out.println("=== Transform result: " + Arrays.asList(result));
		
		double stdDev = calculateStandardDev(bendList);
		double slope = calculateSlope(bendList, stdDev);
		
		PitchEvent ne = getRootEvent();
		
		// record that event has already been fired
		return new PitchBendEvent(ne, stdDev, slope);
	}
	
	protected double calculateStandardDev(List<BendEntry> bendList) {
		List<Integer> bendValueList = buildBendValueList(bendList);
		
		return StatisticUtil.standardDeviation(bendValueList, config.getBendCenter());
	}
	
	protected List<Integer> buildBendValueList(List<BendEntry> bendList) {
		List<Integer> bendValueList = new ArrayList<Integer>(bendList.size());
		for (BendEntry e : bendList) {
			bendValueList.add(e.getBendValue());
		}
		
		return bendValueList;
	}
	
	protected double calculateSlope(List<BendEntry> bendList, double stdDev) {
		// sort the list according to the ticks (timestamp) just in case
		Collections.sort(bendList, BendTickComparator.getInstance());
		
		List<BendEntry> peakList = findBendPeaks(bendList, stdDev);
		
		BendEntryComparator c = new BendValueCenterComparator(config.getBendCenter().intValue());
		
		BendEntry maxPeak = null;
		BendEntry prevPeak = null;
		int index = 1;
		if (peakList.size() > 1) {
			maxPeak = peakList.get(0);
			
			for (int i = 1, s = peakList.size(); i < s; i++) {
				BendEntry e = peakList.get(i);
				
				if (c.compare(e, maxPeak) > 0) {
					maxPeak = e;
					index = i;
				}
			}
			
			prevPeak = peakList.get(index - 1);
		}
		
		double slope = 0.0;
		if (maxPeak != null && prevPeak != null) {
			double rise = maxPeak.getBendValue() - prevPeak.getBendValue();
			double run = (double)(maxPeak.getTick() - prevPeak.getTick());
			
			slope = (rise / run)*1000;
		}
		
		return slope;
	}
	
	protected List<BendEntry> findBendPeaks(List<BendEntry> bendList, double stdDev) {
		List<BendEntry> peakList = new ArrayList<BendEntry>(bendList.size()/2);
		
		BendEntry prevBend = bendList.get(0);
		peakList.add(prevBend);
		
		BendEntryComparator c = BendValueComparator.getInstance();
		for (int i = 1, s = bendList.size(); i < s; i++) {
			BendEntry e = bendList.get(i);
			
			if (i == 1 && c.compare(e, prevBend) < 0) {
				c = c.invert();
			}
			
			// we are in the middle of a slope
			if (c.compare(e, prevBend) > 0) {
				prevBend = e;
			
			// if the current value is less than the current peak, then the
			// prevBend is a peak.  add it to the list and invert the comparator
			} else if (c.compare(e, prevBend) < 0) {
				peakList.add(prevBend);
				
				c = c.invert();
			}
		}
		
		return peakList;
	}
}
