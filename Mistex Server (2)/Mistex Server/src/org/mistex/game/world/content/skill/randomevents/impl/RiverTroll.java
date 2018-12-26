package org.mistex.game.world.content.skill.randomevents.impl;

import org.mistex.game.MistexUtility;
import org.mistex.game.world.npc.NPCHandler;
import org.mistex.game.world.player.Client;

public class RiverTroll {

	public static int[][] riverTroll = { { 3, 10, 391, 19, 1 },
			{ 11, 20, 392, 40, 1 }, { 21, 40, 393, 80, 3 },
			{ 61, 90, 394, 105, 4 }, { 91, 110, 395, 120, 5 },
			{ 111, 138, 396, 150, 7 }, };

	public static void spawnRiverTroll(Client c) {
		for (int[] aRiverTroll : riverTroll) {
				if (c.combatLevel >= aRiverTroll[0] && c.combatLevel <= aRiverTroll[1]) {
					NPCHandler.spawnNpc(c, aRiverTroll[2],c.getAbsX() + MistexUtility.random(1), c.getAbsY()+ MistexUtility.random(1), c.heightLevel, 0, aRiverTroll[3],aRiverTroll[4], aRiverTroll[4] * 10,aRiverTroll[4] * 10, true, true);
			}
		}
	}

}