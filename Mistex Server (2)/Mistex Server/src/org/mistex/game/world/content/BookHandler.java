package org.mistex.game.world.content;

import org.mistex.game.world.content.interfaces.InterfaceText;
import org.mistex.game.world.content.interfaces.impl.InformationTab;
import org.mistex.game.world.player.Client;

/**
 * Handles opening books
 * @author Omar | Play Boy
 */

public class BookHandler {
	
	/**
	 * Checks if player can open the book
	 * @param p
	 * @param itemId
	 * @return
	 */
	public static boolean canOpen(Client p, int itemId) {
		if (!p.getItems().playerHasItem(itemId)) {
			return false;
		}
		return true;
	}
	
	/**
	 * Opens the vote book
	 * @param p
	 * @param itemId
	 */
	public static void openVoteBook(Client p, int itemId) {
		if (!canOpen(p, itemId)) {
			return;
		}
		p.getItems().deleteItem(757, p.getItems().getItemSlot(757), 1);
		p.votingPoints += 1 * (p.getRights().isExtremeDonator() ? 2 : 1);
		InterfaceText.writeText(new InformationTab(p));
		p.sendMessage("You open the book and recieve 1 vote point.");		
	}
	
	/**
	 * Opens the chicken book
	 * @param c
	 * @param itemId
	 */
	public static void openChickenBook(Client p, int itemId) {	
		if (!canOpen(p, itemId)) {
			return;
		}
		p.getItems().deleteItem(7464, p.getItems().getItemSlot(7464), 1);
		p.getItems().addItem(10732, 1);
		p.getItems().addItem(11019, 1);
		p.getItems().addItem(11020, 1);
		p.getItems().addItem(11021, 1);
		p.getItems().addItem(11022, 1);
		p.sendMessage("You read the chicken book and were rewarded with a chicken outfit.");
	}
	
	/**
	 * Opens the magic box
	 * @param p
	 * @param itemId
	 */
	public static void openMagicBox(Client p, int itemId) {
		if (!canOpen(p, itemId)) {
			return;
		}
		p.getItems().deleteItem(10025, p.getItems().getItemSlot(10025), 1);
		p.sendMessage("You open the magic box and were rewarded with 500 of each rune.");
		for (int i = 554; i <= 566; i++) {
			p.getItems().addItem(i, 500);
		}
	}


}
