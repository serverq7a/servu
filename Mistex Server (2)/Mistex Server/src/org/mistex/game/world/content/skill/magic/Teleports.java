package org.mistex.game.world.content.skill.magic;

import org.mistex.game.MistexUtility;
import org.mistex.game.world.player.Client;

@SuppressWarnings("static-access")
public class Teleports {

	private enum Teleport { 
		VARROCK(4140, 25, new int[]{554,563,556}, new int[]{1,1,3}, new int[]{3212,3423}, "modern"),
		LUMBRIDGE(4143, 31, new int[]{557,556,563}, new int[]{1,3,1}, new int[]{3222,3218}, "modern"),
		FALADOR(4146, 37, new int[]{555,556,563}, new int[]{1,3,1}, new int[]{2964,3378}, "modern"),
		CAMELOT(4150, 45, new int[]{556,563}, new int[]{5,1}, new int[]{2757,3477}, "modern"),
		ARDOUGNE(6004, 51, new int[]{555,563}, new int[]{2,2}, new int[]{2662,3305}, "modern"),
		WATCHTOWER(6005, 58, new int[]{557,563}, new int[]{2,2}, new int[]{2549,3112}, "modern"),
		TROLLHEIM(29031, 61, new int[]{554,563}, new int[]{2,2}, new int[]{2910,3612}, "modern"),
		APE_ATOLL(72038, 64, new int[]{554,555,563,1963}, new int[]{2,2,2,1}, new int[]{2755,2784}, "modern");
		
		int levelReq, clickingButton;
		int[] reqItems, reqAmounts, newLoc;
		
		String spellBook; 
		private Teleport(int clickingButton, int levelReq, int[] reqItems, int[] reqAmounts, int[] newLoc, String spellBook) {
			this.levelReq = levelReq;
			this.reqItems = reqItems;
			this.reqAmounts = reqAmounts;
			this.newLoc = newLoc;
			this.clickingButton = clickingButton;
			this.spellBook = spellBook;
		}
	}

	private static boolean hasItems(Client c, int[] items, int[] amount) {
		int temp = 0;
		for (int i = 0; i < items.length; i++) {
			if (c.getItems().playerHasItem(items[i], amount[i])) {
				temp += 1;
			}
		}
		return temp == items.length;
	}
	
	private static void deleteItems(Client c, int[] items, int[] amount) {
		for (int i = 0; i < items.length; i++) {
			c.getItems().deleteItem(items[i], c.getItems().getItemSlot(items[i]), amount[i]);
		}
	}

	private static Teleport getTele(int clickingButton) {
		for (Teleport t : Teleport.values()) {
			if (t.clickingButton == clickingButton) {
				return t;
			}
		}
		return null;
	}
	
	public static void failedTeleport(Client c) {
		c.gfx100(287);
		c.dealDamage(10);
		c.handleHitMask(10);
		c.getPA().refreshSkill(3);
		c.sendMessage("The runes explode while trying to teleport.");
	}
	
	public static void teleport(Client c, int clickingButton) {
		Teleport t = getTele(clickingButton);
		if (t != null) {
			if (c.playerLevel[c.playerMagic] >= t.levelReq) {
				if (hasItems(c, t.reqItems, t.reqAmounts)) {
					if (System.currentTimeMillis() - c.lastCast > 5000) {
						if (MistexUtility.random(15) == MistexUtility.random(15)) {
							failedTeleport(c);
						} else {
							c.getPA().startTeleport(t.newLoc[0], t.newLoc[1], 0, t.spellBook);
							deleteItems(c, t.reqItems, t.reqAmounts);
							c.sendMessage("You safely teleport to "+MistexUtility.optimizeText(t.name().toLowerCase().replaceAll("_", ""))+".");
						}
					}
				} else {
					c.sendMessage("You do not have the required runes to cast this spell.");
				}
			} else {
				c.nextChat = 0;
				c.getPA().sendStatement("You need a magic level of at least "+t.levelReq+" to teleport to "+MistexUtility.optimizeText(t.name().toLowerCase().replaceAll("_", ""))+".");
			}
		}
	}
}