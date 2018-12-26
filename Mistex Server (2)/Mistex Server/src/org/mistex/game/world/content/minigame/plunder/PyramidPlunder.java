package org.mistex.game.world.content.minigame.plunder;

import org.mistex.game.world.event.CycleEventHandler;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.Player;

/**
 * Handles the pyramid plunder minigame.
 * 
 * @author Michael | Chex
 */
public class PyramidPlunder {

	// 1927 4477 - Pyramid Plunder Lvl 21
	// 1954 4477 - Pyramid Plunder Lvl 31
	// 1936 4453 - Pyramid Plunder Lvl 41
	// 1955 4448 - Pyramid Plunder Lvl 51
	// 1927 4453 - Pyramid Plunder Lvl 61
	// 1943 4421 - Pyramid Plunder Lvl 71
	// 1974 4420 - Pyramid Plunder Lvl 81

	public static void handleObjectClick(final Client c, final int id, final int obX, final int obY) {
//		if (c.plunderFloor <= 0) return;
		
		if (PlunderData.isLooted(id)) {
			c.sendMessage("This urn has already been looted.");
			return;
		}

		if (PlunderData.isUrn(id) || PlunderData.hasSnake(id)) {
			if (c.isSkilling) return;
			c.isSkilling = true;
			double chance = (1 / (double) (c.playerLevel[Player.playerThieving]) / 7.0) * (c.plunderFloor * 10 + 11);
			final boolean success = chance < Math.random();
			
			c.startAnimation(success ? 7270 : 7271);
			c.canPlayerMove = false;
			
			CycleEventHandler.getSingleton().addEvent(c, new UrnEvent(c, id, obX, obY, success), 4);
		}
		
		if (PlunderData.isDoor(id)) {
			c.startAnimation(832);
			CycleEventHandler.getSingleton().addEvent(c, new DoorEvent(c, id, obX, obY), 1);
		}
		
		if (PlunderData.EXIT_ID == id) {
			c.sendMessage("Leaving plunder... :(");
		}

		if (PlunderData.GRAND_GOLD_CHEST.getData()[0] == id) {
			c.canPlayerMove = false;
			c.isSkilling = true;
			c.startAnimation(832);
			CycleEventHandler.getSingleton().addEvent(c, new GoldenChestEvent(c, obX, obY), 1);
		}
	}
}