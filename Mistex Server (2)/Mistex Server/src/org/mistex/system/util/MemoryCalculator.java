package org.mistex.system.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MemoryCalculator {

	public static void handleMemoryUsage() {
		DecimalFormat decimalFormat = new DecimalFormat("0.0#%");
		NumberFormat memoryFormat = NumberFormat.getInstance();
		long totalMemory = Runtime.getRuntime().totalMemory();
		long freeMemory = Runtime.getRuntime().freeMemory();
		long usedMemory = totalMemory - freeMemory;
		System.out.println("Total Used Memory: " + memoryFormat.format(usedMemory / 1024) + "/" + memoryFormat.format(totalMemory / 1024) + " MB, " + decimalFormat.format(((double) usedMemory / (double) totalMemory)));
		System.out.println("Free Memory: " + memoryFormat.format(freeMemory / 1024) + " MB, " + decimalFormat.format((double) freeMemory / (double) totalMemory));
	}

}
