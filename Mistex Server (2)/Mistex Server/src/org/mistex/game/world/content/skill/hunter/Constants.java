package org.mistex.game.world.content.skill.hunter;

import org.mistex.game.MistexUtility;
import org.mistex.game.world.player.Client;

public final class Constants {

	protected static final int HUNTER = 22, JAR = 11260, BUTTERFLY_NET = 10010,
			MAGIC_NET = 11259, BUTTERFLY_JAR = 10012, NPCID = 0, OBJECTID = 1,
			CATCHID = 2, ANIM = 3, ITEMID = 4, LAYANIM = 5, XP = 6, LOOT = 7,
			REQ = 8, BAIT = 9, ORIG = 10, TREE = 11;
	
	protected static boolean hasMagicNet(final Client c) {
		return c.playerEquipment[c.playerWeapon] == Constants.MAGIC_NET;
	}

	protected static boolean hasRegularNet(final Client c) {
		return c.playerEquipment[c.playerWeapon] == Constants.BUTTERFLY_NET;
	}

	protected static boolean breakJar(final Client c) {
		return MistexUtility.random(7) == 0;
	}

}
