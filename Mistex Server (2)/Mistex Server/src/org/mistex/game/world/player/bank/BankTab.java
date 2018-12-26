package org.mistex.game.world.player.bank;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael | Chex
 */
public class BankTab {
	
	/**
	 * The bank object the tab belongs to
	 */
	private Bank parentBank;
	
	/**
	 * The bank tab's id
	 */
	private int tabID;
	
	/**
	 * The items within the tab
	 */
	private List<BankItem> items = new ArrayList<BankItem>();
	
	/**
	 * Creates a new bank tab given a parent bank
	 * @param bank - The bank the tab belongs to
	 * @param tabID - The bank tab's id
	 */
	public BankTab(Bank bank, int tabID) {
		this.parentBank = bank;
		this.tabID = tabID;
	}

	/**
	 * Retrieves the BankItem object for an index in the bank tab items list
	 * @param index - The index to look up in the bank tab items list
	 * @return The BankItem object for the index (if index is valid), otherwise returns a blank BankObject
	 */
	public BankItem itemForIndex(int index) {
		if (index >= getItems().size() || getItems().get(index) == null)
			return new BankItem(0, 0);
		return getItems().get(index);
	}
	
	/**
	 * Adds an item to the current bank item list
	 * @param itemID - The item id to add
	 * @param amount - The amount of item id
	 */
	public void addItem(int itemID, int amount) {
		getItems().add(getItems().size(), new BankItem(itemID, amount));
	}
	
	/**
	 * Updates a tab given a tab index and sends info to client
	 */
	public void sendTabItems() {
		parentBank.getClient().getPA().sendFrame126("" + parentBank.getCurrentBankTab().getItems().size(), BankFields.BASE_ID + 17);
		parentBank.getClient().getPA().sendFrame126("" + parentBank.getUsedSlots(), BankFields.BASE_ID + 19);
		
		parentBank.getClient().getOutStream().createFrameVarSizeWord(53);
		parentBank.getClient().getOutStream().writeWord(BankFields.BASE_ID + 63);
		parentBank.getClient().getOutStream().writeWord(getItems().size());
		
		for (BankItem item : getItems()) {
			if (item.getAmount() > 254) {
				parentBank.getClient().getOutStream().writeByte(255);
				parentBank.getClient().getOutStream().writeDWord_v2(item.getAmount());
			} else {
				parentBank.getClient().getOutStream().writeByte(item.getAmount());
			}
			parentBank.getClient().getOutStream().writeWordBigEndianA(item.getID());
		}
		parentBank.getClient().getOutStream().endFrameVarSizeWord();
		parentBank.getClient().flushOutStream();
	}
	
	/**
	 * Collapses the tab and shifts all tabs to fill in other empty tabs
	 */
	public void collapse() {
		parentBank.getTab(0).getItems().addAll(getItems());
		getItems().clear();
		boolean reset = false;
		for (int index = 0; index <= Bank.TABS; index++) {
			BankTab tab = parentBank.getTab(index);
			if (tab.getItems().isEmpty() || reset) {
				if (index + 1 <= Bank.TABS) {
					tab.getItems().addAll(parentBank.getTab(index + 1).getItems());
					parentBank.getTab(index + 1).getItems().clear();
					reset = true;
				}
			}
		}
		
		int usedTabs = parentBank.getUsedTabs();
		
		for (int index = 0; index <= usedTabs + 1; index++) {
			parentBank.requestTabUpdate(index);
		}
		
		if (usedTabs <= Bank.TABS) {
			parentBank.getClient().getPA().sendFrame171(0, BankFields.BASE_ID + 61);
			parentBank.getClient().getPA().sendFrame171(0, BankFields.BASE_ID + 33 + usedTabs * 2);
			parentBank.getClient().getPA().sendFrame70(usedTabs == 0 ? 0 : 48 * (usedTabs-1), 0, BankFields.BASE_ID + 61);
		} else {
			parentBank.getClient().getPA().sendFrame171(1, BankFields.BASE_ID + 61);
		}
		
		if (parentBank.getCurrentBankTab().getItems().isEmpty()) {
			parentBank.openBank(0);
		}
	}

	/**
	 * Gets the tab's items
	 * @return The bank tab's items
	 */
	public List<BankItem> getItems() {
		return items;
	}
	
	/**
	 * Sets the item list to a new list specified
	 * @param items - The item list to set the current items to
	 */
	public void setItems(List<BankItem> items) {
		this.items = items;
	}

	/**
	 * Retrieves the tab id
	 * @return The tab id
	 */
	public int getID() {
		return tabID;
	}

}