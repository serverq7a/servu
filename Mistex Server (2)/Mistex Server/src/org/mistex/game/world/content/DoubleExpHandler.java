package org.mistex.game.world.content;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DoubleExpHandler {
	
	/**
	 * Bonus amount
	 */
	public static final int BONUS = 2; 
	
	/**
	 * Days
	 */
	public static final byte[] DAYS = { 
		Calendar.SATURDAY, Calendar.SUNDAY
	};
	
	/**
	 * Checks if it is a weekend
	 * @return
	 */
	public static boolean weekend() {
		Calendar cal = new GregorianCalendar();	
		int d = cal.get(Calendar.DAY_OF_WEEK);
		for (byte b = 0; b < DAYS.length; b++) {
			if (DAYS[b] == d) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * The double xp weekend modifier
	 * @return
	 */
	public static int weekendModifier() {	
		return (weekend() ? (int)(BONUS) : 1);
	}
	
}
