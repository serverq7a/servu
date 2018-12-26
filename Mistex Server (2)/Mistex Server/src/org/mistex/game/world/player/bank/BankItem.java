package org.mistex.game.world.player.bank;

/**
 * Represents an item deposited in the bank.
 * 
 * @author Michael | Chex
 */
public class BankItem {
	
	/**
	 * The item id
	 */
	private int itemID;
	
	/**
	 * The item amount
	 */
	private int itemAmount;
	
	/** 
	 * Initializes a new bank item
	 * @param index  - The index to be set in the bank
	 * @param itemID - The item id to add
	 * @param amount - The item amount
	 */
	public BankItem(int itemID, int amount) {
		setAmount(amount);
		setID(itemID);
	}
	
	/**
	 * Gets the amount of the item
	 * @return The item amount
	 */
	public int getAmount() {
		return itemAmount;
	}
	
	/**
	 * Gets the item id
	 * @return The item id
	 */
	public int getID() {
		return itemID;
	}
	
	/**
	 * Sets the item amount
	 * @param amount - The new item amount
	 */
	public void setAmount(int amount) {
		this.itemAmount = amount;
	}
	
	/**
	 * Sets the item id
	 * @param itemID - The new item id
	 */
	public void setID(int itemID) {
		this.itemID = itemID;
	}
	
	/**
	 * Creates a copy of an existing bank object
	 */
	public BankItem copy() {
		return new BankItem(itemID, itemAmount);
	}

	@Override
	public String toString() {
		return "[" + itemID + ", " + itemAmount + "]";
	}
}