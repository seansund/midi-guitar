package com.dex.midi.util;

import java.util.List;

public class StatisticUtil {

	public static double mean(List<Integer> list) {
		int total = 0;
		
		for (Integer i : list) {
			total += i;
		}
		
		return (double) total / list.size();
	}
	
	public static double standardDeviation(List<Integer> list) {
		return standardDeviation(list, null);
	}
	
	public static double standardDeviation(List<Integer> list, Double mean) {
		if (mean == null) {
			mean = StatisticUtil.mean(list);
		}
		
		double avg = mean;
		
		double diff = 0.0;
		for (Integer i : list) {
			diff += Math.pow(i - avg, 2);
		}
		
		double variance = diff / list.size();
		
		return Math.sqrt(variance);
	}
}
