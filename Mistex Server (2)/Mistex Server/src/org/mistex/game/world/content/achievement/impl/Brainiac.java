package org.mistex.game.world.content.achievement.impl;

import org.mistex.game.world.content.achievement.Achievement;
import org.mistex.game.world.content.achievement.AchievementHandler.AcheivementType;
import org.mistex.game.world.player.Client;


/**
 * Achievement for winning trivia bot 100 times
 * 	
 * @author Play Boy | Omar
 */

public class Brainiac extends Achievement {

	@Override
	public boolean canGet(Client player, Achievement achievement) {
		return player.triviaWon == 100;
	}

	@Override
	public void giveReward(Client player, Achievement achievement) {
		player.getBank().addItem(996, 1000000);
	}

	@Override
	public String[] getDescription() {
		return new String[] { "line 1" };
	}

	@Override
	public String getRewardString() {
		return "1m coins";
	}

	@Override
	public int getPoints() {
		return 3;
	}

	@Override
	public AcheivementType getAcheivementType() {
		return AcheivementType.DIFFICULT;
	}

}
