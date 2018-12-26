package org.mistex.game.world.content;

import java.util.ArrayList;

import org.mistex.game.MistexConfiguration;
import org.mistex.game.MistexUtility;
import org.mistex.game.world.World;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.bank.BankItem;
import org.mistex.game.world.player.item.ItemAssistant;

public class PriceChecker {
	
	private ArrayList<BankItem> items = new ArrayList<BankItem>();
	
	private Client client;
	
	private int total;
	
	private boolean isActive;
	
	public PriceChecker(Client client) {
		setClient(client);
	}
	
	private void setClient(Client client) {
		this.client = client;
	}
	
	public void setTotal(int total) {
		this.total = total;
	}
	
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	public Client getClient() {
		return client;
	}
	
	public int getTotal() {
		return total;
	}
	
	public boolean isActive() {
		return isActive;
	}
	
	public ArrayList<BankItem> getItems() {
		return items;
	}

	private static int getFramesForSlot[][] = { { 0, 18353 }, { 1, 18356 },
			{ 2, 18359 }, { 3, 18362 }, { 4, 18365 }, { 5, 18368 },
			{ 6, 18371 }, { 7, 18374 }, { 8, 18377 }, { 9, 18380 },
			{ 10, 18383 }, { 11, 18386 }, { 12, 18389 }, { 13, 18392 },
			{ 14, 18395 }, { 15, 18398 }, { 16, 18401 }, { 17, 18404 },
			{ 18, 18407 }, { 19, 18410 }, };

	@SuppressWarnings("unused")
	public void depositItem(int id, int amount) {
		if (id <= 0 || amount <= 0) return;
		
		if (getClient().inWild()) {
			getClient().sendMessage("You can't price check in the wilderness!");
			return;
		}
		
		if (getItems().size() >= 20) {
			getClient().sendMessage("The price-checker is currently full.");
			return;
		}
		
		for (int j = 0; j < MistexConfiguration.ITEM_TRADEABLE.length; j++) {
			if (id == MistexConfiguration.ITEM_TRADEABLE[j]) {
				getClient().sendMessage("This item is untrade-able.");
				return;
			}
		}
		
		if (getClient().inTrade) {
			Client o = (Client) World.players[getClient().tradeWith];
//			if (o != null) {
//				o.getTradeAndDuel().declineTrade();
//			}
			return;
		}
		
		if (getClient().duelStatus == 1) {
			Client o = (Client) World.players[getClient().duelingWith];
//			if (o != null) {
//				o.getTradeAndDuel().resetDuel();
//			}
			return;
		}
		
		if (getClient().getItems().getItemAmount(id) < amount) {
			amount = getClient().getItems().getItemAmount(id);
		}
		
		if (!getClient().getItems().playerHasItem(id, amount)) {
			return;
		}
		
		if (ItemAssistant.isStackable(id)) {
			boolean flag = false;
			for (BankItem item : getItems()) {
				if (item.getID() == id) {
					item.setAmount(item.getAmount() + amount);
					flag = true;
				}
			}
			
			if (!flag)
				getItems().add(new BankItem(id, amount));
		} else
			for (int i = 0; i < amount; i++)
				getItems().add(new BankItem(id, 1));
		
		getClient().getItems().deleteItem2(id, amount);
		
		setTotal(getTotal() + getClient().getItems().getItemPrice(id) * amount);
		
		updateChecker();
	}

	public void open() {
		setActive(true);
		setTotal(0);
		resetFrames();
		updateChecker();
		getClient().getPA().sendFrame248(43933, 5063);
	}

	public void resetFrames() {
		getClient().getPA().sendFrame126("" + MistexUtility.insertCommasToNumber(Integer.toString(getTotal())) + "",18351);
		getClient().getPA().sendFrame126("Click on items in your inventory to check their values",18352);
		for (int index = 0; index < 20; index++) {
			setFrame(index, getFramesForSlot[index][1], -1, -1, false);
		}
	}

	private void setFrame(int slotId, int frameId,int itemId, int amount, boolean store) {
		int totalAmount = getClient().getItems().getItemPrice(itemId) * amount;
		String total = MistexUtility.insertCommasToNumber(Integer.toString(totalAmount));
		if (!store) {
			getClient().getPA().sendFrame126("", frameId);
			getClient().getPA().sendFrame126("", frameId + 1);
			getClient().getPA().sendFrame126("", frameId + 2);
			return;
		}
		if (ItemAssistant.isStackable(itemId)) {
			getClient().getPA().sendFrame126("", frameId);
			getClient().getPA().sendFrame126(MistexUtility.insertCommasToNumber(Integer.toString(getClient().getItems().getItemPrice(itemId))) + " x " + amount, frameId + 1);
			getClient().getPA().sendFrame126(" = " + total, frameId + 2);
		} else {
			getClient().getPA().sendFrame126("", frameId);
			getClient().getPA().sendFrame126(MistexUtility.insertCommasToNumber(Integer.toString(getClient().getItems().getItemPrice(itemId))), frameId + 1);
			getClient().getPA().sendFrame126("", frameId + 2);
		}
	}
	
	public void sendItems() {
		getClient().getOutStream().createFrameVarSizeWord(53);
		getClient().getOutStream().writeWord(18246);
		getClient().getOutStream().writeWord(getItems().size());
		for (int i = 0; i < getItems().size(); i++) {
			if (getItems().get(i).getAmount() < 255) {
				getClient().getOutStream().writeByte(getItems().get(i).getAmount());
			} else {
				getClient().getOutStream().writeByte(255);
				getClient().getOutStream().writeDWord_v2(getItems().get(i).getAmount());
			}
			getClient().getOutStream().writeWordBigEndianA(getItems().get(i).getID() + 1);
		}
		getClient().getOutStream().endFrameVarSizeWord();
		getClient().flushOutStream();
	}

	public void updateChecker() {
		getItems().trimToSize();
		sendItems();
		getClient().getItems().resetItems(5064);
		for (int x = 0; x < getItems().size(); x++) {
			if (getItems().get(x).getAmount() > 0) {
				getClient().getPA().sendFrame126("", 18352);
				setFrame(x, getFramesForSlot[x][1], getItems().get(x).getID(), getItems().get(x).getAmount(), true);
			}
		}
		getClient().getPA().sendFrame126(""+ MistexUtility.insertCommasToNumber(Integer.toString(getTotal() < 0 ? 0 : getTotal())) + "",18351);
	}

	public void withdrawItem(int removeId, int slot, int amount) {
		if (!isActive())
			return;
		
		/* BankItem item = new BankItem(removeId, amount);
		
		if (amount > item.getAmount() && ItemAssistant.isStackable(item.getAmount()))
			amount = c.priceN[slot];
		
		if (!getItems().contains(item)) {
			return;
		}
		
		if (c.price[slot] >= 0 && c.getItems().freeSlots() > 0) {
			c.getItems().addItem(c.price[slot], amount);
			c.getItems();
			if (ItemAssistant.isStackable(c.price[slot]) && c.getItems().playerHasItem(c.price[slot], amount)) {
				c.priceN[slot] -= amount;
				c.price[slot] = c.priceN[slot] <= 0 ? 0 : c.price[slot];
			} else {
				c.priceN[slot] = 0;
				c.price[slot] = 0;
			}
		}
		c.total -= c.getItems().getItemPrice(removeId) * amount;
		for (int frames = 0; frames < getFramesForSlot.length; frames++) {
			if (slot == getFramesForSlot[frames][0]) {
				if (c.priceN[slot] >= 1) {
					setFrame(c, slot, getFramesForSlot[frames][1],c.price[slot], c.priceN[slot], true);
				} else {
					setFrame(c, slot, getFramesForSlot[frames][1],c.price[slot], c.priceN[slot], false);
				}
			}
		}
		updateChecker(); */
	}

}