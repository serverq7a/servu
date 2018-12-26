package org.mistex.game.world.content.skill.runecrafting;

import org.mistex.game.world.content.skill.Skill;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.item.ItemAssistant;

public class Runecrafting extends Skill  {

	public Runecrafting() {
		super(Skills.RUNECRAFTING);
	}

	protected static enum runecraftingData{

		AIR(2478, 556, 5, new int[] { 1, 11, 22, 33, 44, 55, 66, 77, 88, 99 }), 
		MIND(2479, 558, 5.5, new int[] { 1, 14, 28, 42, 56, 70, 84, 98 }), 
		WATER(2480, 555, 6, new int[] { 5, 19, 38, 57, 76, 95 }), 
		EARTH(2481,557, 6.5, new int[] { 9, 26, 52, 78 }), 
		FIRE(2482, 554, 7,new int[] { 14, 35, 70 }), 
		BODY(2483, 559, 7.5, new int[] { 20,46, 92 }),
		COSMIC(2484, 564, 8, new int[] { 27, 59 }),
		CHAOS(2487, 562, 8.5, new int[] { 35, 74 }),
		NATURE(2486, 561, 9,new int[] { 44, 91 }), 
		LAW(2485, 563, 9.5, new int[] { 54 }), 
		DEATH(2488, 560, 10, new int[] { 65 }), 
		BLOOD(30624, 565, 10.5, new int[] { 77 });

		private int altarId, runeId;
		private double xp;
		private int[] multiplier;

		private runecraftingData(int altarId, int runeId, double xp, int[] multiplier) {
			this.altarId = altarId;
			this.runeId = runeId;
			this.xp = xp;
			this.multiplier = multiplier;
		}

		public int getAltarId() {
			return altarId;
		}

		public int getRuneId() {
			return runeId;
		}

		public double getXp() {
			return xp;
		}

		public int getLevel() {
			return multiplier[0];
		}
	}

	public static void craftRunes(final Client c, final int objectId) {
		for (runecraftingData r : runecraftingData.values()) {
			if (r == null) {
				return;
			}
			if (objectId == r.getAltarId()) {
				if (c.playerLevel[20] >= r.getLevel()) {
					if (c.getItems().playerHasItem(1436)) {
						c.startAnimation(791);
						c.gfx100(186);
						int multiplier = 1;
						for (int i = 1; i < r.multiplier.length; i++) {
							if (c.playerLevel[20] >= r.multiplier[i]) {
								multiplier = i;
							}
						}
						int remove = c.getItems().getItemAmount(1436);
						c.getItems().deleteItem3(1436, remove);
						new Runecrafting().addExp(c, r.getXp() * remove);
						new Runecrafting().giveItem(c, r.getRuneId(), multiplier * remove, "You craft the essence into "+ItemAssistant.getItemName(r.getRuneId())+".");
					}
				} else {
					c.sendMessage("You need a runecrafting level of "+ r.getLevel() + " to craft this rune.");
					return;
				}
			}
		}
	}
}