package org.mistex.game.world.content.trade;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import org.mistex.game.MistexConfiguration;
import org.mistex.game.MistexUtility;
import org.mistex.game.world.World;
import org.mistex.game.world.content.achievement.AchievementHandler;
import org.mistex.game.world.content.achievement.AchievementList;
import org.mistex.game.world.content.interfaces.InterfaceText;
import org.mistex.game.world.content.interfaces.impl.AchievementTab;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.PlayerSave;
import org.mistex.game.world.player.item.GameItem;
import org.mistex.game.world.player.item.Item;
import org.mistex.game.world.player.item.ItemAssistant;

public class Trading {

	private Client c;

	public Trading(Client Client) {
		c = Client;
	}

	public CopyOnWriteArrayList<GameItem> offeredItems = new CopyOnWriteArrayList<GameItem>();

	public void displayWAndI(Client c) {
		Client o = (Client) World.players[c.tradeWith];
		c.playerTradeWealth = 0;
		o.playerTradeWealth = 0;
		for (GameItem item : c.getTrade().offeredItems) {
			c.playerTradeWealth += (c.getItems().getItemPrice(item.id) * item.amount);
		}

		for (GameItem item : o.getTrade().offeredItems) {
			o.playerTradeWealth += (c.getItems().getItemPrice(item.id) * item.amount);
		}

		int playerDifference1 = (c.playerTradeWealth - o.playerTradeWealth);
		int playerDifference2 = (o.playerTradeWealth - c.playerTradeWealth);

		boolean player1HasMore = (playerDifference1 > playerDifference2);
		boolean equalsSame = (c.playerTradeWealth == o.playerTradeWealth);

		if (c.playerTradeWealth < -1) {
			c.playerTradeWealth = 2147483647;
		}
		if (o.playerTradeWealth < -1) {
			o.playerTradeWealth = 2147483647;
		}

		String playerValue1 = "" + c.getPA().getTotalAmount(c, c.playerTradeWealth) + " (" + MistexUtility.format(c.playerTradeWealth) + ")";
		String playerValue2 = "" + c.getPA().getTotalAmount(o, o.playerTradeWealth) + " (" + MistexUtility.format(o.playerTradeWealth) + ")";

		if (c.playerTradeWealth < -1) {
			playerValue1 = "+" + playerValue1;
		}
		if (o.playerTradeWealth < -1) {
			playerValue2 = "+" + playerValue2;
		}
		if (equalsSame) {
			c.getPA().sendFrame126("@yel@Equal Trade", 23504);
			o.getPA().sendFrame126("@yel@Equal Trade", 23504);
		} else if (player1HasMore) {
			c.getPA().sendFrame126("-@red@" + c.getPA().getTotalAmount(c, playerDifference1) + " (" + MistexUtility.format(playerDifference1) + ")", 23504);
			o.getPA().sendFrame126("+@yel@" + o.getPA().getTotalAmount(o, playerDifference1) + " (" + MistexUtility.format(playerDifference1) + ")", 23504);
		} else if (!player1HasMore) {
			c.getPA().sendFrame126("+@yel@" + c.getPA().getTotalAmount(c, playerDifference2) + " (" + MistexUtility.format(playerDifference2) + ")", 23504);
			o.getPA().sendFrame126("-@red@" + o.getPA().getTotalAmount(o, playerDifference2) + " (" + MistexUtility.format(playerDifference2) + ")", 23504);
		}
		c.getPA().sendFrame126(playerValue1, 23506);
		o.getPA().sendFrame126(playerValue1, 23507);
		c.getPA().sendFrame126(playerValue2, 23507);
		o.getPA().sendFrame126(playerValue2, 23506);
		c.getPA().sendFrame126(MistexUtility.formatPlayerName(o.playerName) + " has\\n " + o.getItems().freeSlots() + " free\\n inventory slots.", 23505);
		o.getPA().sendFrame126(MistexUtility.formatPlayerName(c.playerName) + " has\\n " + c.getItems().freeSlots() + " free\\n inventory slots.", 23505);
		c.getPA().sendFrame126("Trading with: " + MistexUtility.formatPlayerName(o.playerName) + "", 3417);
		o.getPA().sendFrame126("Trading with: " + MistexUtility.formatPlayerName(c.playerName) + "", 3417);
	}

	public void requestTrade(int id) {
		try {
			declineTrade();

			if (c.inTrade || c.inDuel || c.inDuelArena()) {
				c.sendMessage("You cannot trade at the moment.");
				return;
			}

			World.getPlayerHandler();
			Client e = (Client) World.players[id];
			if (id == c.playerId) {
				return;
			}
			
            if(c.playerIsBusy())
                return;
            if(e.playerIsBusy()) {
                c.sendMessage("Other player is busy at the moment.");
                return;
            }
			if (e.inTrade) {
				c.sendMessage("Other player is busy at the moment.");
				return;
			}
			if (e.inDuel || e.duelStatus >= 1 || c.duelingWith >= 1) {

				return;
			}
			if (e.inDuelArena() || c.inDuelArena()) {
				c.sendMessage("You cannot request trade in the duel arena.");
				return;
			}

			if (!c.inTrade && e.tradeRequested && e.tradeWith == c.playerId) {
				c.tradeWith = id;
				c.getTrade().openTrade();
				e.getTrade().openTrade();
			} else if (!c.inTrade) {
				c.tradeRequested = true;
				c.tradeWith = id;
				c.turnPlayerTo(e.absX, e.absY);
				c.sendMessage("Sending trade request...");
				e.sendMessage(c.playerName + ":tradereq:");
			}
		} catch (Exception var3) {
			MistexUtility.println("Error requesting trade.");
		}

	}

	public void openTrade() {
		World.getPlayerHandler();
		Client o = (Client) World.players[c.tradeWith];
		if (o != null) {
			if (c.teleTimer <= 0) {
				if (o.teleTimer <= 0) {
					c.inTrade = true;
					c.canOffer = true;
					c.tradeStatus = 1;
					c.tradeRequested = false;
					c.getItems().resetItems(3322);
					resetTItems(3415);
					resetOTItems(3416);
					String out = o.playerName;
					if (o.playerRights == 1) {
						out = "@cr1@" + out;
					} else if (o.playerRights == 2) {
						out = "@cr2@" + out;
					}

					displayWAndI(c);
					c.getPA().sendFrame126("", 3431);
					c.getPA().sendFrame126("Are you sure you want to make this trade?", 3535);

					c.getPA().sendFrame248(3323, 3321);
				}
			}
		}
	}

	public void resetTItems(int WriteFrame) {
		c.getOutStream().createFrameVarSizeWord(53);
		c.getOutStream().writeWord(WriteFrame);
		int len = offeredItems.toArray().length;
		int current = 0;
		c.getOutStream().writeWord(len);

		for (Iterator<GameItem> var5 = offeredItems.iterator(); var5.hasNext(); ++current) {
			GameItem i = (GameItem) var5.next();
			if (i.amount > 254) {
				c.getOutStream().writeByte(255);
				c.getOutStream().writeDWord_v2(i.amount);
			} else {
				c.getOutStream().writeByte(i.amount);
			}

			c.getOutStream().writeWordBigEndianA(i.id + 1);
		}

		if (current < 27) {
			for (int var6 = current; var6 < 28; ++var6) {
				c.getOutStream().writeByte(1);
				c.getOutStream().writeWordBigEndianA(-1);
			}
		}

		c.getOutStream().endFrameVarSizeWord();
		c.flushOutStream();
	}

	public boolean fromTrade(int itemId, int fromSlot, int amount) {
		World.getPlayerHandler();
		Client o = (Client) World.players[c.tradeWith];

		if (o == null) {
			return false;
		}
		
		if (c.playerRights == 2) {
			return false;
		}
		
        if (!c.inTrade || !c.canOffer) {
            declineTrade();
            return false;
        }

		if (itemId > 0) {
			c.sendMessage("Disabled to prevent scamming. Decline the trade");
			return false;
		}
		return true;
	}

	@SuppressWarnings("static-access")
	public boolean tradeItem(int itemID, int fromSlot, int amount) {
		World.getPlayerHandler();
		Client o = (Client) World.players[c.tradeWith];

		if (c.playerRights == 2) {
			return false;
		}
		if (o == null) {
			return false;
		}
		
        if (!c.inTrade || !c.canOffer) {
            declineTrade();
            return false;
        }
        
        if(!c.getItems().playerHasItem(itemID, amount))
            return false;

		int[] var8 = MistexConfiguration.ITEM_TRADEABLE;
		int var7 = MistexConfiguration.ITEM_TRADEABLE.length;

		int inTrade;
		int item;
		for (item = 0; item < var7; ++item) {
			inTrade = var8[item];
			if (itemID == 18509 || itemID == 21406) {
				c.sendMessage("You can\'t trade this item.");
				return false;
			}
			if (inTrade == itemID && c.playerRights != 3) {
				c.sendMessage("You can\'t trade this item.");
				return false;
			}
		}
		
        for (final int i: MistexConfiguration.ITEM_TRADEABLE) {
            if (itemID == i) {
            	c.sendMessage("You can not trade "+c.getItems().getItemName(itemID)+".");
            	return false;
            	
            }
        }

		c.tradeConfirmed = false;
		o.tradeConfirmed = false;
		if(amount > c.playerItemsN[fromSlot]) {
			return false;
		}
		if (c.playerRights == 3) {
			c.sendMessage("iD:"+itemID+"/"+c.playerItems[fromSlot]+", itemamt: "+amount+"/"+c.playerItemsN[fromSlot]); 
		}
		if (itemID != 995 & c.playerItems[fromSlot] == 996){
			return false;
		}
		if(!((c.playerItems[fromSlot] == itemID+1) && (c.playerItemsN[fromSlot] >= amount))) {
			c.sendMessage("You don't have that amount!");
			return false;
		}
		if (!c.getItems().playerHasItem(itemID, 1)) {
			return false;
		} else {
			if (!ItemAssistant.isStackable(itemID) && !Item.itemIsNote[itemID]) {
				if (amount > 28) {
					amount = 28;
				}

				for (inTrade = 0; inTrade < amount; ++inTrade) {
					if (c.getItems().playerHasItem(itemID, 1)) {
						offeredItems.add(new GameItem(itemID, 1));
						c.getItems().deleteItem(itemID, c.getItems().getItemSlot(itemID), 1);
						displayWAndI(c);
					}
				}

				displayWAndI(c);
				c.getItems().resetItems(3322);
				resetTItems(3415);
				o.getTrade().resetOTItems(3416);
				c.getPA().sendFrame126("", 3431);
				o.getPA().sendFrame126("", 3431);
			}

			if (c.inTrade && c.canOffer) {
				if (c.getItems().getItemCount(itemID) < amount) {
					amount = c.getItems().getItemCount(itemID);
					if (amount == 0) {
						return false;
					}
				}

				if (!c.getItems().playerHasItem(itemID, amount)) {
					System.out.println("You do not have this item...");
					return false;
				} else {
					if (ItemAssistant.isStackable(itemID) || Item.itemIsNote[itemID]) {
						boolean var10 = false;
						Iterator<GameItem> var11 = offeredItems.iterator();

						while (var11.hasNext()) {
							GameItem var9 = (GameItem) var11.next();
							if (var9.id == itemID) {
								var10 = true;
								var9.amount += amount;
								c.getItems().deleteItem(itemID, c.getItems().getItemSlot(itemID), amount);
								displayWAndI(c);
								break;
							}
						}

						if (!var10) {
							offeredItems.add(new GameItem(itemID, amount));
							c.getItems().deleteItem(itemID, fromSlot, amount);
							displayWAndI(c);
						}
					}

					displayWAndI(c);
					c.getItems().resetItems(3322);
					resetTItems(3415);
					o.getTrade().resetOTItems(3416);
					c.getPA().sendFrame126("", 3431);
					o.getPA().sendFrame126("", 3431);
					return true;
				}
			} else {
				declineTrade();
				return false;
			}
		}
	}
	
	public static boolean twoTraders(Client c, Client o) {
		int count = 0;
		for (int i = 0; i < World.players.length; i++) {
			Client temp = (Client) World.players[i];
			if (temp != null) {
				if (temp.tradeWith == c.playerId
						|| temp.tradeWith == o.playerId) {
					count++;
				}
			}
		}
		return count == 2;
	}

	public void resetTrade() {
		offeredItems.clear();
		c.inTrade = false;
		c.tradeWith = 0;
		c.canOffer = true;
		c.tradeConfirmed = false;
		c.tradeConfirmed2 = false;
		c.acceptedTrade = false;
		c.getPA().removeAllWindows();
		c.tradeResetNeeded = false;
		c.getPA().sendFrame126("Are you sure you want to make this trade?", 3535);
	}

	public void declineTrade() {
		c.tradeStatus = 0;
		declineTrade(true);
	}

	public void declineTrade(boolean tellOther) {
		c.getPA().removeAllWindows();
		World.getPlayerHandler();
		Client o = (Client) World.players[c.tradeWith];
		if (tellOther && o != null) {
			o.getTrade().declineTrade(false);
			o.getTrade().c.getPA().removeAllWindows();
		}

		Iterator<GameItem> var4 = offeredItems.iterator();

		while (var4.hasNext()) {
			GameItem item = (GameItem) var4.next();
			if (item.amount >= 1) {
				if (ItemAssistant.isStackable(item.id)) {
					c.getItems().addItem(item.id, item.amount);
				} else {
					for (int i = 0; i < item.amount; ++i) {
						c.getItems().addItem(item.id, 1);
					}
				}
			}
		}

		c.canOffer = true;
		c.tradeConfirmed = false;
		c.tradeConfirmed2 = false;
		offeredItems.clear();
		c.inTrade = false;
		c.tradeWith = 0;
	}

	public void resetOTItems(int WriteFrame) {
		World.getPlayerHandler();
		Client o = (Client) World.players[c.tradeWith];
		if (o != null) {
			c.getOutStream().createFrameVarSizeWord(53);
			c.getOutStream().writeWord(WriteFrame);
			int len = o.getTrade().offeredItems.toArray().length;
			int current = 0;
			c.getOutStream().writeWord(len);

			for (Iterator<GameItem> var6 = o.getTrade().offeredItems.iterator(); var6.hasNext(); ++current) {
				GameItem i = (GameItem) var6.next();
				if (i.amount > 254) {
					c.getOutStream().writeByte(255);
					c.getOutStream().writeDWord_v2(i.amount);
				} else {
					c.getOutStream().writeByte(i.amount);
				}

				c.getOutStream().writeWordBigEndianA(i.id + 1);
			}

			if (current < 27) {
				for (int var7 = current; var7 < 28; ++var7) {
					c.getOutStream().writeByte(1);
					c.getOutStream().writeWordBigEndianA(-1);
				}
			}

			c.getOutStream().endFrameVarSizeWord();
			c.flushOutStream();
		}
	}

	public void confirmScreen() {
		World.getPlayerHandler();
		Client o = (Client) World.players[c.tradeWith];
        if(!c.inTrade) {
            declineTrade();
            return;
        }
		if (o != null) {
			c.canOffer = false;
			c.getItems().resetItems(3214);
			String SendTrade = "Absolutely nothing!";
			String SendAmount = "";
			int Count = 0;
			Iterator<GameItem> var6 = offeredItems.iterator();

			GameItem item;
			while (var6.hasNext()) {
				item = (GameItem) var6.next();
				if (item.id > 0) {
					if (item.amount >= 1000 && item.amount < 1000000) {
						SendAmount = "@cya@" + item.amount / 1000 + "K @whi@(" + MistexUtility.format(item.amount) + ")";
					} else if (item.amount >= 1000000) {
						SendAmount = "@gre@" + item.amount / 1000000 + " million @whi@(" + MistexUtility.format(item.amount) + ")";
					} else {
						SendAmount = MistexUtility.format(item.amount);
					}

					if (Count == 0) {
						c.getItems();
						SendTrade = ItemAssistant.getItemName(item.id);
					} else {
						c.getItems();
						SendTrade = SendTrade + "\\n" + ItemAssistant.getItemName(item.id);
					}

					if (ItemAssistant.isStackable(item.id)) {
						SendTrade = SendTrade + " x " + SendAmount;
					}

					++Count;
				}
			}

			c.getPA().sendFrame126(SendTrade, 3557);
			SendTrade = "Absolutely nothing!";
			SendAmount = "";
			Count = 0;
			var6 = o.getTrade().offeredItems.iterator();

			while (var6.hasNext()) {
				item = (GameItem) var6.next();
				if (item.id > 0) {
					if (item.amount >= 1000 && item.amount < 1000000) {
						SendAmount = "@cya@" + item.amount / 1000 + "K @whi@(" + MistexUtility.format(item.amount) + ")";
					} else if (item.amount >= 1000000) {
						SendAmount = "@gre@" + item.amount / 1000000 + " million @whi@(" + MistexUtility.format(item.amount) + ")";
					} else {
						SendAmount = MistexUtility.format(item.amount);
					}

					if (Count == 0) {
						c.getItems();
						SendTrade = ItemAssistant.getItemName(item.id);
					} else {
						c.getItems();
						SendTrade = SendTrade + "\\n" + ItemAssistant.getItemName(item.id);
					}

					if (ItemAssistant.isStackable(item.id)) {
						SendTrade = SendTrade + " x " + SendAmount;
					}

					++Count;
				}
			}

			c.getPA().sendFrame126(SendTrade, 3558);
			c.getPA().sendFrame248(3443, 197);
		}
	}

	public void giveItems() {
		World.getPlayerHandler();
		Client o = (Client) World.players[c.tradeWith];
		if (o != null) {
			try {
				Iterator<GameItem> var3 = o.getTrade().offeredItems.iterator();

				while (var3.hasNext()) {
					GameItem item = (GameItem) var3.next();
					if (item.id > 0) {
						c.getItems().addItem(item.id, item.amount);
						c.getTradeLog().tradeReceived(ItemAssistant.getItemName(item.id), item.amount);
						o.getTradeLog().tradeGive(ItemAssistant.getItemName(item.id), item.amount);
					}
				}
				c.tradesCompleted += 1;
				if (c.tradesCompleted == 1)
					c.sendMessage("@war@You have started the achievement: The Trader ");
					InterfaceText.writeText(new AchievementTab(c));
				if (c.tradesCompleted == 500)
					AchievementHandler.activateAchievement(c, AchievementList.THE_TRADER);
				c.getPA().removeAllWindows();
				PlayerSave.saveGame(c);
				PlayerSave.saveGame(o);
				c.tradeResetNeeded = true;
			} catch (Exception var4) {
				;
			}

		}
	}

}
