package org.mistex.game.world.content.achievement.impl;

import org.mistex.game.world.content.achievement.Achievement;
import org.mistex.game.world.content.achievement.AchievementHandler.AcheivementType;
import org.mistex.game.world.player.Client;

/**
 * Achievement for killing Jad beast 10 times
 * 
 * @author Play Boy | Omar
 */

public class Jad_killer extends Achievement {

	@Override
	public boolean canGet(Client player, Achievement achievement) {
		return player.jadKills == 10;
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
		return "500k coins";
	}

	@Override
	public int getPoints() {
		return 2;
	}

	@Override
	public AcheivementType getAcheivementType() {
		return AcheivementType.MEDIUM;
	}

}

