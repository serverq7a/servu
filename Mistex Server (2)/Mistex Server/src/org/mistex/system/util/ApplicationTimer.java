package org.mistex.system.util;

public class ApplicationTimer {

	private long cachedTime;

	public ApplicationTimer() {
		reset();
	}

	public void reset() {
		cachedTime = System.currentTimeMillis();
	}

	public long elapsed() {
		return System.currentTimeMillis() - cachedTime;
	}
}
