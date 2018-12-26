package org.mistex.game.world.content.achievement.impl;

import org.mistex.game.world.content.achievement.Achievement;
import org.mistex.game.world.content.achievement.AchievementHandler.AcheivementType;
import org.mistex.game.world.player.Client;

/**
 * Achievement for killing 100 rock crabs
 * 	
 * @author Play Boy | Omar
 */

public class Crabs extends Achievement {

	@Override
	public boolean canGet(Client player, Achievement achievement) {
		return player.rockCrabKills == 100;
	}

	@Override
	public void giveReward(Client player, Achievement achievement) {
		player.getBank().addItem(996, 100000);
	}

	@Override
	public String[] getDescription() {
		return new String[] { "line 1" };
	}

	@Override
	public String getRewardString() {
		return "100k coins";
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

