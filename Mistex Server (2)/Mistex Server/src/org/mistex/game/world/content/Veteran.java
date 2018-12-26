package org.mistex.game.world.content;

import org.mistex.game.world.player.Client;


public class Veteran {
	
	/**
	 * Cost of claiming cape & hood
	 */
	public static int COST = 1000000;
	
	/**
	 * Game time required
	 */
	public static int GAME_TIME = 41353;
	
	/**
	 * Veteran cape ID
	 */
	public static int VETERAN_CAPE = 20763;
	
	/**
	 * Veteran hood ID
	 */
	public static int VETERAN_HOOD = 20764;
	
	/**
	 * Checks if player can claim the items
	 * @param c
	 * @return
	 */
	public static boolean canClaim(Client c) {
		if (c.gameTime < 41353) {
			c.getDH().sendDialogues(3002, 7143);
			return false;
		}
		if (!c.getItems().playerHasItem(995, COST)) {
			c.getDH().sendDialogues(3003, 7143);
			return false;
		}
		
		return true;
	}
	
	/**
	 * Claims the veteran items
	 * @param c
	 */
	public static void claimVeteran(Client c) {
		if (!canClaim(c)) {
			return;
		}
		c.getItems().deleteItem(995, COST);
		c.getItems().addItem(VETERAN_CAPE, 1);
		c.getItems().addItem(VETERAN_HOOD, 1);
		c.getDH().sendDialogues(3004, 7143);
		//c.playerTitle = 100;
		c.sendMessage("@red@YOU WILL HAVE TO WAIT FOR THE NEXT CLIENT UPDATE FOR THE 'VETERAN' TITLE!");
	}

}
