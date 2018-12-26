package org.mistex.game.world.npc;

import org.mistex.game.MistexUtility;

public class Drop {
	
	public static enum ChanceType {
		ALWAYS	  (0),
		COMMON	  (15),
		UNCOMMON  (65),
		RARE	  (215),
		VERY_RARE (512);

		
		private double chance;
		
		private ChanceType(double chance) {
			this.chance = chance;
		}
		
		public double getChance(boolean row) {
			return 1 - (1 / chance) * (row ? 1.15 : 1);
		}
	}
	
	private int item, min, max;
	
	public Drop(int item, int min, int max) {
		this.item = item;
		this.min = min;
		this.max = max;
	}
	
	public int getCount() {
		return MistexUtility.random(min, max);
	}

	public int getItem() {
		return item;
	}
	
	@Override
	public String toString() {
		return "<id:" + item + ", Amount:" + (min + (min == max ? "" : "-" + max) + ", Rarity:");
	}
}