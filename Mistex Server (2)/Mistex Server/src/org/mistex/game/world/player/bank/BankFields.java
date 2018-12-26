package org.mistex.game.world.player.bank;

import org.mistex.game.world.player.Client;

/**
 * Holds all the fields for a bank object.
 * 
 * @author Chex
 */
public class BankFields {

	/**
	 * The banks maximum size.
	 */
	public static final int BANK_SIZE = 350;

	/**
	 * The beginning of the interface id's for the bank interface's children.
	 */
	public static final int BASE_ID = 50000;

	/**
	 * The bank interface id.
	 */
	public static final int PARENT_INTERFACE_ID = 5292;

	/**
	 * The bank scroll interface id.
	 */
	public static final int INTERFACE_SCROLL_ID = 5385;

	/**
	 * The bank inventory interface id.
	 */
	public static final int PARENT_INVENTORY_INTERFACE_ID = 5063;

	/**
	 * The bank inventory interface id.
	 */
	public static final int INVENTORY_INTERFACE_ID = 5064;

	/**
	 * The list that holds all eight bank tabs for one bank.
	 */
	private BankTab[] bankTabs = new BankTab[9];

	/**
	 * This flag checks if the player will withdraw bank items in their note
	 * form.
	 */
	private boolean noteWithdrawal;

	/**
	 * This flag swaps items.
	 */
	private boolean insertItems;

	/**
	 * Sets the note withdrawal flag, to check if a player will withdraw items
	 * in their note form.
	 * 
	 * @param noteWithdrawal
	 *            - Toggle the note withdraw
	 */
	public void setNoteWithdrawal(boolean noteWithdrawal) {
		this.noteWithdrawal = noteWithdrawal;
		getClient().getPA().sendFrame36(1011, noteWithdrawal ? 0 : 1);
	}

	/**
	 * Sets the item swapping flag.
	 * 
	 * @param insertItems
	 *            - Toggle inserting items.
	 */
	public void setItemInserting(boolean insertItems) {
		this.insertItems = insertItems;
		getClient().getPA().sendFrame36(1009, insertItems ? 0 : 1);
	}

	/**
	 * Checks if the player will withdraw the item as a note.
	 * 
	 * @return True if the player has toggled withdrawing items as notes.
	 */
	public boolean withdrawAsNote() {
		return noteWithdrawal;
	}

	/**
	 * Checks if the player will swap or insert items.
	 * 
	 * @return True if the player has toggled inserting items.
	 */
	public boolean insertItems() {
		return insertItems;
	}

	/**
	 * The maximum amount of tabs the server/client can support
	 */
	public static final int TABS = 8;

	/**
	 * Gets the tabs currently in the bank
	 * 
	 * @return The bank tabs
	 */
	public BankTab[] getBankTabs() {
		return bankTabs;
	}

	/**
	 * The tab currently open
	 */
	private int currentTab;

	/**
	 * The owner of the bank
	 */
	private Client client;

	/**
	 * Gets the owner of the bank
	 * 
	 * @return The bank owner
	 */
	public Client getClient() {
		return client;
	}

	/**
	 * Set the owner of the bank
	 * 
	 * @param client
	 *            - The new owner
	 */
	public void setClient(Client client) {
		this.client = client;
	}

	/**
	 * Gets the current tab the owner is viewing
	 * 
	 * @return The current viewed tab
	 */
	public int getCurrentTab() {
		return currentTab;
	}

	/**
	 * Updates the tab currently being viewed
	 * 
	 * @param tab
	 *            - The tab to view
	 */
	public void setTab(int tab) {
		this.currentTab = tab;
	}
}