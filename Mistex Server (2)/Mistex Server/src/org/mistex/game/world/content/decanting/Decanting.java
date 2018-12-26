package org.mistex.game.world.content.decanting;

import org.mistex.game.world.player.Client;

public class Decanting {
	
	/**
	 * Checks if player can decant
	 * @param c
	 * @return
	 */
	public static boolean canDecant(Client c) {
		if (!c.getItems().playerHasItem(995, 100000)) {
			c.getDH().sendDialogues(1288, 300);
			return false;
		}
		if (c.getItems().freeSlots() == 0) {
			c.getDH().sendDialogues(1289, 300);
			return false;
		}
		if (c.getItems().freeSlots() == 28) {
			c.getDH().sendDialogues(1290, 300);
			return false;
		}
		return true;
	}
	
	/**
	 * The actual decanting
	 * @param c
	 */
	public static void startDecanting(Client c) {
		if (!canDecant(c)) {
			return;
		}
		c.getItems().deleteItem(995, 100000);
		for(Potion p : Potion.values()) {
			int full = p.getFullId();
			int half = p.getHalfId();
			int quarter = p.getQuarterId();
			int threeQuarters = p.getThreeQuartersId();
			int totalDoses = 0;
			int remainder = 0;
			int totalEmptyPots = 0;
			if(c.getItems().playerHasItem(threeQuarters)) {
				totalDoses += (3 * c.getItems().getItemAmount(threeQuarters));
				totalEmptyPots += c.getItems().getItemAmount(threeQuarters);
				c.getItems().deleteItemDECANTING(threeQuarters, c.getItems().getItemAmount(threeQuarters));
			}
			if(c.getItems().playerHasItem(half)) {
				totalDoses += (2 * c.getItems().getItemAmount(half));
				totalEmptyPots += c.getItems().getItemAmount(half);
				c.getItems().addItemDECANTING(half, c.getItems().getItemAmount(half));
			}
			if(c.getItems().playerHasItem(quarter)) {
				totalDoses += (1 * c.getItems().getItemAmount(quarter));
				totalEmptyPots += c.getItems().getItemAmount(quarter);
				c.getItems().deleteItemDECANTING(quarter, c.getItems().getItemAmount(quarter));
			}
			if(totalDoses > 0) {
				if(totalDoses >= 4)
					c.getItems().addItemDECANTING(full, totalDoses / 4);
				else if(totalDoses == 3)
					c.getItems().addItemDECANTING(threeQuarters, 1);
				else if(totalDoses == 2)
					c.getItems().addItemDECANTING(half, 1);
				else if(totalDoses == 1)
					c.getItems().addItemDECANTING(quarter, 1);
				if((totalDoses % 4) != 0) {
					totalEmptyPots -= 1;
					remainder = totalDoses % 4;
					if(remainder == 3)
						c.getItems().addItemDECANTING(threeQuarters, 1);
					else if(remainder == 2)
						c.getItems().addItemDECANTING(half, 1);
					else if(remainder == 1)
						c.getItems().addItemDECANTING(quarter, 1);
				}
				totalEmptyPots -= (totalDoses / 4);
				c.getItems().addItemDECANTING(229, totalEmptyPots);
				c.getDH().sendDialogues(1287, 300);
			}
		}
	}


}
