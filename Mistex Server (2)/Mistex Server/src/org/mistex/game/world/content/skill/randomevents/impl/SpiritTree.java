package org.mistex.game.world.content.skill.randomevents.impl;

import org.mistex.game.Mistex;
import org.mistex.game.MistexUtility;
import org.mistex.game.world.player.Client;

/**
 * Spirit tree random event
 * @author Omar | Play Boy
 */
@SuppressWarnings("static-access")
public class SpiritTree {

	/**
	 * Spirit tree ints
	 */
	private static int[][] spiritTree = { { 3, 10, 438, 19, 1 },
			{ 11, 20, 439, 40, 1 }, { 21, 40, 440, 80, 3 },
			{ 61, 90, 441, 105, 4 }, { 91, 110, 442, 120, 5 },
			{ 111, 138, 443, 150, 7 }, };

	/**
	 * Spawns the spirit tree
	 * @param c
	 */
	public static void spawnSpiritTree(Client c) {
		for (int[] aSpiritTree : spiritTree) {
			if (c.combatLevel >= aSpiritTree[0] && c.combatLevel <= aSpiritTree[1]) {
				Mistex.npcHandler.spawnNpc(c, aSpiritTree[2], c.getAbsX()+ MistexUtility.random(1), c.getAbsY() + MistexUtility.random(1), c.heightLevel, 0, aSpiritTree[3], aSpiritTree[4],aSpiritTree[4] * 10, aSpiritTree[4] * 10, true, false);
			}
		}
	}
}