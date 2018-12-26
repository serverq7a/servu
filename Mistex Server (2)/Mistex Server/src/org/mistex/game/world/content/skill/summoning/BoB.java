package org.mistex.game.world.content.skill.summoning;

import org.mistex.game.Mistex;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.bank.Bank;
import org.mistex.game.world.player.item.Item;


public class BoB {

	private Client c;
	
	private int[] bobItems = new int[30];

	public BoB(Client c) {
		this.c = c;
	}

	public void store() {
		c.isBanking = false;
		c.usingBoB = true;
		c.getOutStream().createFrameVarSizeWord(53);
		c.getOutStream().writeWord(2702);
		c.getOutStream().writeWord(30);
		for (int item : bobItems) {
			c.getOutStream().writeByte(1);
			c.getOutStream().writeWordBigEndianA(item > 0 ? item + 1 : 0);
		}
		c.getOutStream().endFrameVarSizeWord();
		c.flushOutStream();
		
		c.getItems().resetItems(Bank.INVENTORY_INTERFACE_ID);
		c.getOutStream().createFrame(248);
		c.getOutStream().writeWordA(2700);
		c.getOutStream().writeWord(Bank.PARENT_INVENTORY_INTERFACE_ID);
		c.flushOutStream();
		c.ResetKeepItems();
	}
	
	public void deposit(int item, int amount) {
		if (stored() >= 30) {
			c.sendMessage("Your BoB cannot store any more items.");
			return;
		}
		if (Item.itemStackable[item]) {
			c.sendMessage("You cannot store stackable items!");
			return;
		}
		if (stored() + amount > 30)
			amount = 30 - stored();
		if (c.getItems().playerHasItem(item)) {
			while (amount-- > 0 && c.getItems().playerHasItem(item)) {
				c.getItems().deleteItem(item, 1);
				addToBoB(item, 1);
			}
			store();
		}
	}
	
	public void withdraw(int slot, int item, int amount) {
		int initAmount = amount;
		while (hasItem(item) && amount-- > 0) {
			if (!c.getItems().addItem(item, 1)) {
				store();
				return;
			}
				removeFromBoB(slot, item, initAmount);
		}
		store();
	}

	public void dropItems() {
		for (int index = 0; index < bobItems.length; index++) {
			if (bobItems[index] > 0)
				Mistex.itemHandler.createGroundItem(c, bobItems[index], c.absX, c.absY, 1, c.playerId);
			bobItems[index] = 0;
		}
	}
	
	private boolean hasItem(int search) {
		for (int item : bobItems)
			if (item == search)
				return true;
		return false;
	}
	
	private void addToBoB(int add, int amount) {
		amount : while (amount-- > 0) {
			for (int index = 0; index < bobItems.length; index++) {
				if (bobItems[index] <= 0) {
					bobItems[index] = add;
					continue amount;
				}
			}
		}
	}
	
	private boolean removeFromBoB(int slot, int item, int amount) {
		if (slot < bobItems.length) {
			if (bobItems[slot] == item && amount == 0) {
				bobItems[slot] = -1;
				return true;
			}
			for (int index = 0; index < bobItems.length; index++) {
				if (bobItems[index] == item) {
					bobItems[index] = -1;
					return true;
				}
			}
		}
		return false;
	}
	
	private int stored() {
		int items = 0;
		for (int item : bobItems)
			if (item > 0)
				items++;
		return items;
	}

	public void withdrawBoB() {
		for (int index = 0; index < bobItems.length; index++) {
			if (!c.getItems().addItem(bobItems[index], 1)) return;
			removeFromBoB(index, bobItems[index], 1);
		}
	}
}