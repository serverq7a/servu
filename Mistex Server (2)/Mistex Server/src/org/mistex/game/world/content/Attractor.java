package org.mistex.game.world.content;

import org.mistex.game.world.player.Client;

public class Attractor {

	public static final int[] RESTRICTED_ITEMS = new int[] { 4740, 1115, 1117, 1119, 1121, 1123, 1125, 1127, 2583, 2591, 2599, 2607, 2615, 2623, 2653, 2261, 2269, 3481, 6617, 10690, 10691, 10697, 10698, 10776, 10780, 10782, 10798, 10800, 13800, 13805 };
	
	private enum Backpack {
		ATTRACTOR(10498, 0.5),
		ACCUMULATOR(10499, 0.75),
		ALERTER(20068, 0.875);
		
		private int item;
		private double chance;
		
		private Backpack(int item, double chance) {
			this.item = item;
			this.chance = chance;
		}
		
		public double getChance() {
			return chance;
		}
		
		public int getItem() {
			return item;
		}
		
		public static Backpack forId(int item) {
			for (Backpack backpack : values()) {
				if (backpack.getItem() == item) {
					return backpack;
				}
			}
			return null;
		}
	}
	
	public static boolean activate(Client c) {
		Backpack backpack = Backpack.forId(c.playerEquipment[c.playerCape]);
		if (backpack == null) 
			return false;
		if (Math.random() > backpack.getChance() && canAttract(c)) {
			return true;
		}
		
		return false;
	}
	
	private static boolean canAttract(Client c) {
		for (int item : RESTRICTED_ITEMS) {
			if (c.getItems().isWearingItem(item)) {
				return false;
			}
		}
		return true;
	}
}