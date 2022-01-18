package com.dex.midi.util;

import java.util.List;

public class StatisticUtil {

	public static double mean(List<Integer> list) {
		int total = 0;
		
		for (Integer i : list) {
			total += i.intValue();
		}
		
		return total/list.size();
	}
	
	public static double standardDeviation(List<Integer> list) {
		return standardDeviation(list, null);
	}
	
	public static double standardDeviation(List<Integer> list, Double mean) {
		if (mean == null) {
			mean = Double.valueOf(StatisticUtil.mean(list));
		}
		
		double avg = mean.doubleValue();
		
		double diff = 0.0;
		for (Integer i : list) {
			diff += Math.pow(i - avg, 2);
		}
		
		double variance = diff / list.size();
		
		return Math.sqrt(variance);
	}
}
