package org.mistex.game.world.content.skill.randomevents.impl;

import org.mistex.game.Mistex;
import org.mistex.game.MistexUtility;
import org.mistex.game.world.player.Client;

/**
 * Handles the evil chicken random event
 * @author Omar | Play Boy
 */
public class EvilChicken {

	/**
	 * Evil chicken ints
	 */
	private static int[][] chicken = { 
			{ 3, 10, 2463, 19, 1 }, { 11, 20, 2464, 40, 1 }, 
			{ 21, 40, 2465, 60, 2 }, { 41, 60, 2466, 80, 3 }, 
			{ 61, 90, 2467, 105, 4 }, { 91, 138, 2468, 120, 5 },
	};

	/**
	 * Spawns the evil chicken
	 * @param c
	 */
	@SuppressWarnings("static-access")
	public static void spawnChicken(Client c) {
		for (int[] aChicken : chicken) {
			if (c.combatLevel >= aChicken[0] && c.combatLevel <= aChicken[1]) {
				Mistex.npcHandler.spawnNpc(c, aChicken[2],c.getAbsX() + MistexUtility.random(1),c.getAbsY() + MistexUtility.random(1), c.heightLevel, 0,aChicken[3], aChicken[4], aChicken[4] * 10,aChicken[4] * 10, true, true);
			}
		}
	}
}