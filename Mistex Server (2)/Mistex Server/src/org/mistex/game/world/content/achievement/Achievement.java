package org.mistex.game.world.content.achievement;

import org.mistex.game.world.content.achievement.AchievementHandler.AcheivementType;
import org.mistex.game.world.player.Client;

/**
 * Handles all achievements
 * 
 * @author Daniel | Eclipse
 */
public abstract class Achievement {

	public abstract boolean canGet(Client player, Achievement achievement);

	public abstract void giveReward(Client player, Achievement achievement);

	public abstract String[] getDescription();
	
	public abstract String getRewardString();

	public abstract int getPoints();

	public abstract AcheivementType getAcheivementType();
	
}
