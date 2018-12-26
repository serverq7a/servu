package org.mistex.game.world.content.achievement.impl;

import org.mistex.game.world.content.achievement.Achievement;
import org.mistex.game.world.content.achievement.AchievementHandler.AcheivementType;
import org.mistex.game.world.player.Client;

/**
 * Achievement for reporting a player for the first time for a valid reason
 * 
 * @author Play Boy | Omar
 */

public class Law_Abiding_Citizen extends Achievement {

	@Override
	public boolean canGet(Client player, Achievement achievement) {
		return player.playerReported = true;
	}

	@Override
	public void giveReward(Client player, Achievement achievement) {
		player.getBank().addItem(996, 10000);
	}

	@Override
	public String[] getDescription() {
		return new String[] { "line 1" };
	}

	@Override
	public String getRewardString() {
		return "10K coins";
	}

	@Override
	public int getPoints() {
		return 1;
	}

	@Override
	public AcheivementType getAcheivementType() {
		return AcheivementType.EASY;
	}


}
