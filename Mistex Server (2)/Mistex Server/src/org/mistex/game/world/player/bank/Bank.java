package org.mistex.game.world.player.bank;

import org.mistex.game.MistexConfiguration;
import org.mistex.game.world.World;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.item.Item;
import org.mistex.game.world.player.item.ItemAssistant;

/**
 * Represents a bank with tab support
 * 
 * @author Michael | Chex
 */
public class Bank extends BankFields {

	/**
	 * Initialize a new bank
	 * 
	 * @param client
	 *            - The owner of the bank
	 */
	public Bank(Client client) {
		setClient(client);
		setTab(0);
		for (int index = 0; index < getBankTabs().length; index++) {
			getBankTabs()[index] = new BankTab(this, index++);
		}
	}

	/**
	 * Opens the owner's bank at the current tab viewed
	 */
	public void openBank() {
		openBank(0);
	}

	private boolean canBank() {
		if (getClient().inWeaponGame() || getClient().inWeaponLobby() || !getClient().inBankZone()) {
			getClient().sendMessage("You can't bank here!");
			return false;
		}

		if (getClient().inWild()) {
			getClient().sendMessage("You can't bank in the wilderness!");
			return false;
		}

		if (getClient().inTrade) {
			Client o = (Client) World.players[getClient().tradeWith];
			if (o != null) {
				o.getTrade().declineTrade();
			}
			return false;
		}

		if (getClient().duelStatus == 1) {
			Client o = (Client) World.players[getClient().duelingWith];
			if (o != null) {
				o.getDuel().resetDuel();
			}
			return false;
		}

		if (getClient().inTrade || getClient().inDuel || getClient().inWild() || getClient().inCwGame) {
			getClient().sendMessage("You can't store items at the moment.");
			return false;
		}

		return true;
	}

	/**
	 * Opens the owner's bank at the tab specified
	 * 
	 * @param tab
	 *            - The tab to open
	 */
	public boolean openBank(int tab) {
		if (tab > 0 && getTab(tab).getItems().isEmpty()) {
			getClient().sendMessage("Bank tabs are currently disabled");
			return false;
		}

		if (!canBank()) {
			return false;
		}

		if (getClient().getOutStream() != null && getClient() != null) {
			setItemInserting(false);
			setNoteWithdrawal(false);

			if (tab != getCurrentTab()) {
				getClient().getPA().sendFrame36(1000 + tab, 1);
				getClient().getPA().sendFrame36(1000 + getCurrentTab(), 0);
			}
			setTab(tab);
			refreshBank();
		}
		return true;
	}

	/**
	 * Adds an item to the current tab
	 * 
	 * @param itemID
	 *            - The item to add
	 * @param amount
	 *            - The item amount to add
	 * @return True if the item was added successfully
	 */
	public boolean addItem(int itemID, int amount) {
		if (hasItem(itemID))
			return addItem(getTabIndex(itemID), itemID, amount);
		return addItem(getCurrentTab(), itemID, amount);
	}

	/**
	 * Adds an item to the specified tab
	 * 
	 * @param tab
	 *            - The tab to add the item to add
	 * @param itemID
	 *            - The item to add
	 * @param amount
	 *            - The item amount to add
	 * @return True if the item was added successfully
	 */
	private boolean addItem(int tab, int itemID, int amount) {
		BankTab bankTab = getTab(tab);

		BankItem item = getBankItem(itemID);

		long currentAmount = item.getAmount();

		boolean hasItem = hasItem(itemID);

		if (currentAmount >= MistexConfiguration.MAXITEM_AMOUNT || amount + currentAmount < -1 || amount + currentAmount > MistexConfiguration.MAXITEM_AMOUNT) {
			getClient().sendMessage("You can't add anymore of that item to your bank.");
			return false;
		}

		if (hasItem) {
			amount += currentAmount;
			item.setAmount(amount);
		} else {
			bankTab.getItems().add(bankTab.getItems().size(), new BankItem(itemID, amount));
		}

		return true;
	}

	/**
	 * Removes an item from the bank
	 * 
	 * @param itemID
	 *            - The item id to remove
	 */
	public void removeItem(int itemID) {
		if (hasItem(itemID)) {
			for (BankTab tab : getBankTabs()) {
				if (tab != null) {
					tab.getItems().remove(getBankItem(itemID));
				}
			}
		}
	}

	/**
	 * Checks if the bank contains an item with a given item id
	 * 
	 * @param itemID
	 *            - The item id to check
	 * @return True if bank tab contains the item id
	 */
	public boolean hasItem(int itemID) {
		for (BankTab tab : getBankTabs()) {
			if (tab != null) {
				for (BankItem item : tab.getItems()) {
					if (item != null) {
						if (item.getID() == itemID) {
							return true;
						}
					}
				}

			}
		}
		return false;
	}

	/**
	 * Deposits an item to the current tab
	 * 
	 * @param itemID
	 *            - The item id to deposit
	 * @param amount
	 *            - The amount of the item to deposit
	 * @param playerInvSlot
	 *            - The slot from the player's inventory to delete
	 */
	public void deposit(int itemID, int amount, int playerInvSlot) {
		deposit(itemID, amount, playerInvSlot, true);
	}

	/**
	 * Deposits an item to the current tab
	 * 
	 * @param itemID
	 *            - The item id to deposit
	 * @param amount
	 *            - The amount of the item to deposit
	 * @param playerInvSlot
	 *            - The slot from the player's inventory to delete
	 * @param refresh
	 *            - Should the bank refresh its screen after a deposit?
	 */
	public void deposit(int itemID, int amount, int playerInvSlot, boolean refresh) {
		if (getCurrentTab() > TABS || amount < 1 || itemID < 0 || itemID > MistexConfiguration.ITEM_LIMIT || getClient().playerItems[playerInvSlot] <= 0 || getClient().playerItems[playerInvSlot] - 1 != itemID) {
			return;
		}

		if (!canBank()) {
			return;
		}

		if (getClient().getItems().getItemAmount(itemID) < amount) {
			amount = getClient().getItems().getItemAmount(itemID);
		}

		int itemShift = 1;

		if (Item.itemIsNote[itemID] && !Item.itemIsNote[itemID - 1]) {
			itemShift--;
		}

		if (Item.itemStackable[itemID] || getClient().playerItemsN[playerInvSlot] > 1) {
			if (addItem(itemID + itemShift, amount)) {
				getClient().getItems().deleteItem(itemID, playerInvSlot, amount);
				requestTabUpdate(getTabIndex(itemID + itemShift));
			}
		} else {
			int used = getUsedSlots();

			if (used + amount >= MistexConfiguration.ITEM_LIMIT)
				amount = MistexConfiguration.ITEM_LIMIT - used;

			addItem(itemID + 1, amount);
			while (amount > 0) {
				getClient().getItems().deleteItem2(itemID, amount--);
			}
		}

		if (refresh) {
			refreshBank();
		}
	}

	/**
	 * Withdraw an item from the bank
	 * 
	 * @param itemID
	 *            - The item id to withdraw
	 * @param amount
	 *            - The amount of the item to withdraw
	 */
	public void withdraw(int itemID, int amount) {
		if (itemID <= 0 || amount <= 0)
			return;

		if (!canBank()) {
			return;
		}

		if (!hasItem(itemID + 1))
			return;

		BankItem bankItem = getBankItem(itemID + 1);
		int bankItemAmount = bankItem.getAmount();

		if (withdrawAsNote()) {
			if (!Item.itemIsNote[itemID + 1])
				getClient().sendMessage("This item can't be withdrawn as a note.");
			else
				itemID++;
		}

		if (!Item.itemStackable[itemID] && getClient().getItems().freeSlots() < amount) {
			amount = getClient().getItems().freeSlots();
		}

		if (ItemAssistant.isStackable(itemID)) {

			if (bankItemAmount < amount)
				amount = bankItemAmount;

			if (getClient().getItems().addItem(itemID, amount)) {
				bankItem.setAmount(bankItemAmount - amount);
				if (bankItem.getAmount() <= 0)
					removeItem(bankItem.getID());
			}
			requestTabUpdate(getTabIndex(itemID + 1));
		} else {
			while (amount > 0 && bankItem.getAmount() > 0) {
				if (getClient().getItems().addItem(itemID, 1)) {
					bankItem.setAmount(bankItem.getAmount() - 1);
					if (bankItem.getAmount() <= 0) {
						removeItem(bankItem.getID());
						break;
					}
					amount--;
				} else {
					break;
				}
			}
		}

		if (getCurrentBankTab().getItems().isEmpty()) {
			BankTab tabTemp = getCurrentBankTab();
			if (tabTemp == null) {
				return;
			}
			openBank(0);
			tabTemp.collapse();
		}

		refreshBank();
	}

	/**
	 * Calculates the amount of bank slots used
	 * 
	 * @return The amount of slots used
	 */
	public int getUsedSlots() {
		int total = 0;
		for (BankTab tab : getBankTabs()) {
			if (tab != null) {
				total += tab.getItems().isEmpty() ? 0 : tab.getItems().size();
			}
		}
		return total;
	}

	/**
	 * Calculates the current amount of tabs active
	 * 
	 * @return The number of active tabs
	 */
	public int getUsedTabs() {
		int total = 0;
		for (BankTab tab : getBankTabs()) {
			if (tab != null) {
				total += tab.getItems().isEmpty() ? 0 : 1;
			}
		}
		return total;
	}

	/**
	 * Deposits the owner's inventory items
	 */
	public void depositInventory() {
		if (getClient().inWeaponGame() || getClient().inWeaponLobby() || !getClient().inBankZone()) {
			getClient().sendMessage("You can't bank here!");
			return;
		}

		if (getClient().inWild()) {
			getClient().sendMessage("You can't bank in the wilderness!");
			return;
		}

		if (getClient().inTrade) {
			Client o = (Client) World.players[getClient().tradeWith];
			if (o != null) {
				o.getTrade().declineTrade();
			}
			return;
		}

		if (getClient().duelStatus == 1) {
			Client o = (Client) World.players[getClient().duelingWith];
			if (o != null) {
				o.getDuel().resetDuel();
			}
			return;
		}

		if (getClient().inTrade || getClient().inDuel || getClient().inWild() || getClient().inCwGame) {
			getClient().sendMessage("You can't store items at the moment.");
			return;
		}

		for (int index = 0; index < getClient().playerItems.length; index++) {
			deposit(getClient().playerItems[index] - 1, getClient().playerItemsN[index], index, false);
		}
		refreshBank();
	}

	/**
	 * Removes and deposits the owner's worn equipment items
	 */
	public void depositWornEquipment() {
		if (getClient().inWeaponGame() || getClient().inWeaponLobby() || !getClient().inBankZone()) {
			getClient().sendMessage("You can't bank here!");
			return;
		}

		if (getClient().inWild()) {
			getClient().sendMessage("You can't bank in the wilderness!");
			return;
		}

		if (getClient().inTrade) {
			Client o = (Client) World.players[getClient().tradeWith];
			if (o != null) {
				o.getTrade().declineTrade();
			}
			return;
		}

		if (getClient().duelStatus == 1) {
			Client o = (Client) World.players[getClient().duelingWith];
			if (o != null) {
				o.getDuel().resetDuel();
			}
			return;
		}

		if (getClient().inTrade || getClient().inDuel || getClient().inWild() || getClient().inCwGame) {
			getClient().sendMessage("You can't store items at the moment.");
			return;
		}

		for (int index = 0; index < getClient().playerEquipment.length; index++) {
			if (getClient().playerEquipment[index] + 1 <= 0 || getClient().playerEquipmentN[index] <= 0)
				continue;
			addItem(getClient().playerEquipment[index] + 1, getClient().playerEquipmentN[index]);
			getClient().getItems().deleteEquipment(getClient().playerEquipment[index], index);
		}
		refreshBank();
	}

	/**
	 * Refreshes the bank at the current viewed tab
	 */
	public void refreshBank() {
		getCurrentBankTab().sendTabItems();
		getClient().getItems().resetItems(INVENTORY_INTERFACE_ID);
		getClient().getOutStream().createFrame(248);
		getClient().getOutStream().writeWordA(PARENT_INTERFACE_ID);
		getClient().getOutStream().writeWord(PARENT_INVENTORY_INTERFACE_ID);
		getClient().flushOutStream();
	}

	/**
	 * Moves an item to a designated tab
	 * 
	 * @param item
	 *            - The item to move
	 * @param tabID
	 *            - The tab id to move the item to
	 */
	public void itemToTab(BankItem item, int tabID) {
		getClient().sendMessage("Bank tabs have been disabled!");
		
		
		/*BankTab toTab = getTab(tabID);
		
		if (toTab == null) {
			return;
		}
		
		BankTab itemTab = getTab(getTabIndex(item.getID()));
		
		if (itemTab == null) {
			return;
		}
		
		if (!hasItem(item.getID()))
			return;
		if (itemTab.getItems().size() == 1 && toTab.getItems().isEmpty()) {
			getClient().sendMessage("You cannot create a new tab when you have one item in this tab.");
			return;
		}

		removeItem(item.getID());
		addItem(toTab.getID(), item.getID(), item.getAmount());

		requestTabUpdate(toTab.getID());
		requestTabUpdate(itemTab.getID());

		if (itemTab.getItems().isEmpty()) {
			itemTab.collapse();
		} else if (itemTab.getID() > 0) {
			requestTabUpdate(toTab.getID());
		}

		int usedTabs = getUsedTabs();

		if (usedTabs <= Bank.TABS) {
			getClient().getPA().sendFrame171(0, BASE_ID + 61);
			getClient().getPA().sendFrame171(0, BASE_ID + 33 + (usedTabs) * 2);
			getClient().getPA().sendFrame70(usedTabs == 0 ? 0 : 48 * (usedTabs - 1), 0, BASE_ID + 61);
		} else {
			getClient().getPA().sendFrame171(1, BASE_ID + 61);
		}

		openBank();*/
	}

	/**
	 * Updates a tab's preview item display as well as if the tab is
	 * enabled/disabled
	 * 
	 * @param tabID
	 *            - The tab to update
	 */
	public void requestTabUpdate(int tabID) {
		BankTab tab = getTab(tabID);
		if (tab == null) {
			return;
		}
		boolean isTabEmpty = tab.getItems().isEmpty();
		int itemID = -1;
		int itemAmount = -1;

		if (!isTabEmpty) {
			itemID = tab.getItems().get(0).getID() - 1;
			itemAmount = tab.getItems().get(0).getAmount();
			if (itemID == 995) {
				if (itemAmount >= 10000) {
					itemID += 9;
				} else if (itemAmount >= 1000) {
					itemID += 8;
				} else if (itemAmount >= 250) {
					itemID += 7;
				}
			}
		}

		if (tab.getID() != getUsedTabs())
			getClient().getPA().sendFrame171(isTabEmpty && tab.getID() > 1 ? 1 : 0, BASE_ID + 33 + tab.getID() * 2);

		if (tab.getID() > 0) {
			getClient().getPA().sendFrame34(itemID, 0, BASE_ID + 51 + tab.getID() - 1, itemAmount);
		}
	}

	/**
	 * Gets the tab currently being viewed
	 * 
	 * @return The current bank tab viewed
	 */
	public BankTab getCurrentBankTab() {
		return getBankTabs()[0];
	}

	/**
	 * Gets a tab with a specified tab id
	 * 
	 * @return The bank tab at a given id
	 */
	public BankTab getTab(int tab) {
		if (tab > TABS)
			tab = TABS;
		if (tab < 0)
			tab = 0;
		return getBankTabs()[tab] == null ? getBankTabs()[0] : getBankTabs()[tab];
	}

	/**
	 * Gets an item from the bank
	 * 
	 * @param itemID
	 *            - The item id to retrieve
	 * @return - The bank item with a given id (if valid), otherwise return a
	 *         blank bank item
	 */
	public BankItem getBankItem(int itemID) {
		if (hasItem(itemID)) {
			BankTab tab = getTab(getTabIndex(itemID));
			if (tab != null) {
				for (BankItem item : tab.getItems()) {
					if (item != null) {
						if (item.getID() == itemID)
							return item;
					}
				}

			}
		}
		return new BankItem(0, 0);
	}

	/**
	 * Gets the tab index for an item
	 * 
	 * @param itemID
	 *            - The item to look for
	 * @return The index of the tab where the item is stored (if itemID is not
	 *         in any tab, returns the main tab)
	 */
	public int getTabIndex(int itemID) {
		if (hasItem(itemID)) {
			for (int index = 0; index <= Bank.TABS; index++) {
				BankTab tab = getTab(index);
				if (tab != null) {
					for (BankItem item : tab.getItems()) {
						if (item != null) {
							if (item.getID() == itemID) {
								return index;
							}
						}
					}
				}
			}
		}
		return 0;
	}
	
	
}