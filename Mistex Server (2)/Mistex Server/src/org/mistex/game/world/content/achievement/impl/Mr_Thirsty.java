package org.mistex.game.world.content.achievement.impl;

import org.mistex.game.world.content.achievement.Achievement;
import org.mistex.game.world.content.achievement.AchievementHandler.AcheivementType;
import org.mistex.game.world.player.Client;

/**
 * Achievement for drinking 1,000 potions
 * 
 * @author Play Boy | Omar
 */

public class Mr_Thirsty extends Achievement {

	@Override
	public boolean canGet(Client player, Achievement achievement) {
		return player.potionsDrank == 1000;
	}

	@Override
	public void giveReward(Client player, Achievement achievement) {
		player.getBank().addItem(996, 500000);
	}

	@Override
	public String[] getDescription() {
		return new String[] { "line 1" };
	}

	@Override
	public String getRewardString() {
		return "100K coins";
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
