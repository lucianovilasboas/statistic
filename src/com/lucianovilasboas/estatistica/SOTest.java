package com.lucianovilasboas.estatistica;

import java.util.Arrays;
import java.util.IntSummaryStatistics;

public class SOTest {
	public static void main(String[] args) {
		int[] tab = { 12, 1, 21, 8 };
		IntSummaryStatistics stat = Arrays.stream(tab).summaryStatistics();
		int min = stat.getMin();
		int max = stat.getMax();
		System.out.println("Min = " + min);
		System.out.println("Max = " + max);
		
		System.out.println(stat);
	}
}