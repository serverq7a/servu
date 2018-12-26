package org.mistex.game.world.content.skill.randomevents.impl;

import org.mistex.game.MistexUtility;
import org.mistex.game.world.npc.NPCHandler;
import org.mistex.game.world.player.Client;

public class RockGolem {
	
	public static int[][] rockGolem = { { 3, 10, 413, 19, 1 },
		{ 11, 20, 414, 40, 1 }, { 21, 40, 415, 80, 3 },
		{ 61, 90, 416, 105, 4 }, { 91, 110, 417, 120, 5 },
		{ 111, 138, 418, 150, 7 }, };

	public static void spawnRockGolem(Client c) {
		for (int[] aRockGolem : rockGolem) {
				if (c.combatLevel >= aRockGolem[0] && c.combatLevel <= aRockGolem[1]) {
					NPCHandler.spawnNpc(c, aRockGolem[2], c.getAbsX() + MistexUtility.random(1), c.getAbsY()+ MistexUtility.random(1), c.heightLevel, 0, aRockGolem[3],aRockGolem[4], aRockGolem[4] * 10,aRockGolem[4] * 10, true, false);
			}
		}
	}

}
