package org.mistex.game.world.content.skill.runecrafting;

import org.mistex.game.world.player.Client;

@SuppressWarnings("static-access")
public class Pouches {

	public static int[][] pouchData = { { 5509, 1, 3 }, { 5510, 25, 6 },
			{ 5512, 50, 9 }, { 5514, 75, 12 }, };

	public static void fillPouch(final Client c, final int itemId) {
		for (int index = 0; index < pouchData.length; index++) {
			if (itemId == pouchData[index][0]) {
				if (c.playerLevel[c.playerRunecrafting] >= pouchData[index][1]) {
					while (c.getItems().playerHasItem(1436)) {
						int spaceAvailable = (pouchData[index][2] - c.pouch[index]);
						if (spaceAvailable > 0) {
							c.pouch[index] += 1;
							c.getItems().deleteItem(1436, 1);
						} else {
							break;
						}
					}
					c.sendMessage("You fill your "+ c.getItems().getItemName(itemId));
				} else {
					c.sendMessage("You need a runecrafting level of "+ pouchData[index][1] + " to fill this pouch.");
					return;
				}
			}
		}
	}

	public static void emptyPouch(final Client c, final int itemId) {
		for (int index = 0; index < pouchData.length; index++) {
			if (itemId == pouchData[index][0]) {
				while (c.pouch[index] > 0) {
					if (c.getItems().freeSlots() > 0) {
						c.getItems().addItem(1436, 1);
						c.pouch[index] -= 1;
					} else {
						break;
					}
				}
				c.sendMessage("You empty your "+ c.getItems().getItemName(itemId));
			}
		}
	}

	public static void checkPouch(final Client c, final int itemId) {
		for (int index = 0; index < pouchData.length; index++) {
			if (itemId == pouchData[index][0]) {
				c.sendMessage("You still have rune essence(s) in your "+ c.getItems().getItemName(itemId) + ".");
			}
		}
	}
}