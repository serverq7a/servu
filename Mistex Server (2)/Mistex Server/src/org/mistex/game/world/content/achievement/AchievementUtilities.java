package org.mistex.game.world.content.achievement;

import org.mistex.game.world.player.Client;


/**
 * Utilities for the achievements.
 * 
 * @author Daniel | Eclipse
 */
public class AchievementUtilities {

	public static String getText(Client player, AchievementList achievement) {
		return getTextPrefix(player, achievement) + "    " + achievement.name().toLowerCase().replaceAll("_", " ");
	}
	
	/**
	 * Gets the text prefix for the achievement
	 * 
	 * @param player
	 * @param achievement
	 * @return
	 */
	private static String getTextPrefix(Client player, AchievementList achievement) {
		return isComplete(player, achievement) ? "@gre@" : "@red@";
	}

	
	/**
	 * checks if the player has completed the fast
	 * 
	 * @param player
	 * @param achievement
	 * @return
	 */
	private static boolean isComplete(Client player, AchievementList achievement) {
		return player.achievements[achievement.ordinal()];
	}
	
}
