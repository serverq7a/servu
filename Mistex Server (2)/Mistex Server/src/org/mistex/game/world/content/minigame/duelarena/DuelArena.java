package org.mistex.game.world.content.minigame.duelarena;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import org.mistex.game.Mistex;
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

@SuppressWarnings({"static-access", "unused"})
public class DuelArena {

	private Client c;
	
	public DuelArena(Client Client) {
		this.c = Client;
	}
	
	public CopyOnWriteArrayList<GameItem> otherStakedItems = new CopyOnWriteArrayList<GameItem>();
	public CopyOnWriteArrayList<GameItem> stakedItems = new CopyOnWriteArrayList<GameItem>();
	
	public boolean isStackable(int itemId) {
		if (itemId < 1) {
			return false;
		}

		return Item.itemStackable[itemId];
	}
	
	public void requestDuel(int id) {
		try {
			
			if (c.getRights().isAdminstrator()) {
				c.sendMessage("Dueling has been disabled for Adminstrators");
				return;
			}

			if (c.inDuel) {
				c.sendMessage("You are already in a duel.");
				return;
			}
			
			if (c.gameTime < 15) {
				c.sendMessage("You can not do this yet! You are still a new player.");
				return;
			}
			
			if (c.hasNpc) {
				this.c.sendMessage("You can not request a duel with a pet!");
				return;
			}

			if (c.inTrade) {
				this.c.sendMessage("You cannot do this while trading.");
				return;
			}

			if (id == this.c.playerId) {
				return;
			}

			if (!this.c.inDuelArena()) {
				this.c.sendMessage("You are not in the duel arena...");
				return;
			}

			World.getPlayerHandler();
			Client o = (Client) World.players[id];

			if (o == null) {
				return;
			}
            if(c.playerIsBusy())
                return;
            if(o.playerIsBusy()) {
                c.sendMessage("Other player is busy at the moment.");
                return;
            }
			if (o.inTrade) {
				c.sendMessage("You cannot do this while trading.");
			}

			if (this.c.duelStatus > 0 || o.duelStatus > 0) {
				this.c.sendMessage("The other player is busy at the moment.");
				return;
			}
			if (!o.inDuelArena()) {
				this.c.sendMessage("This player is not in the duel arena.");
				return;
			}


			this.resetDuel();
			this.resetDuelItems();
			this.c.duelingWith = id;
			this.c.duelRequested = true;
			if (this.c.duelStatus == 0 && o.duelStatus == 0 && this.c.duelRequested && o.duelRequested && this.c.duelingWith == o.getId() && o.duelingWith == this.c.getId()) {
				if (this.c.goodDistance(this.c.getX(), this.c.getY(), o.getX(), o.getY(), 1)) {
					this.c.getDuel().openDuel();
					o.getDuel().openDuel();
				} else {
					this.c.sendMessage("You need to get closer to your opponent to start the duel.");
				}
			} else {
				this.c.sendMessage("Sending duel request...");
				o.sendMessage(this.c.playerName + ":duelreq:");
			}
		} catch (Exception var3) {
			;
		}

	}

	public void openDuel() {
		World.getPlayerHandler();
		Client o = (Client) World.players[this.c.duelingWith];
		final Client z = o;

		if (o != null) {

			if (this.c.teleTimer <= 0) {
				if (o.teleTimer <= 0) {
						this.c.duelStatus = 1;
						this.refreshduelRules();
						this.refreshDuelScreen();
						this.c.usingSpecial = false;
						o.usingSpecial = false;
						this.c.canOffer = true;
				        c.openDuel = true;
				        o.openDuel = true;

						for (int i = 0; i < this.c.playerEquipment.length; ++i) {
							this.sendDuelEquipment(this.c.playerEquipment[i], this.c.playerEquipmentN[i], i);
						}

						this.c.getPA().sendFrame126("Dueling with: " + o.playerName + " (level-" + o.combatLevel + ")", 6671);
						this.c.getPA().sendFrame126("", 6684);

						this.c.getPA().sendFrame248(6575, 3321);
						this.c.getItems().resetItems(3322);
				}
			}
		}
	}

	public void sendDuelEquipment(int itemId, int amount, int slot) {

		if (itemId != 0) {
			this.c.getOutStream().createFrameVarSizeWord(34);
			this.c.getOutStream().writeWord(13824);
			this.c.getOutStream().writeByte(slot);
			this.c.getOutStream().writeWord(itemId + 1);
			if (amount > 254) {
				this.c.getOutStream().writeByte(255);
				this.c.getOutStream().writeDWord(amount);
			} else {
				this.c.getOutStream().writeByte(amount);
			}

			this.c.getOutStream().endFrameVarSizeWord();
			this.c.flushOutStream();
		}

	}

	public void refreshduelRules() {
		for (int i = 0; i < this.c.duelRule.length; ++i) {
			this.c.duelRule[i] = false;
		}

		this.c.getPA().sendFrame87(286, 0);
		this.c.duelOption = 0;
	}

	public void refreshDuelScreen() {
		World.getPlayerHandler();
		Client o = (Client) World.players[this.c.duelingWith];
        if(!c.openDuel && !o.openDuel) {
            declineDuel();
            return;
        }
		if (o != null) {
			this.c.getOutStream().createFrameVarSizeWord(53);
			this.c.getOutStream().writeWord(6669);
			this.c.getOutStream().writeWord(this.stakedItems.toArray().length);
			int current = 0;

			GameItem i;
			Iterator<GameItem> var4;
			for (var4 = this.stakedItems.iterator(); var4.hasNext(); ++current) {
				i = (GameItem) var4.next();
				if (i.amount > 254) {
					this.c.getOutStream().writeByte(255);
					this.c.getOutStream().writeDWord_v2(i.amount);
				} else {
					this.c.getOutStream().writeByte(i.amount);
				}

				if (i.id > 30000 || i.id < 0) {
					i.id = 30000;
				}

				this.c.getOutStream().writeWordBigEndianA(i.id + 1);
			}

			int var5;
			if (current < 27) {
				for (var5 = current; var5 < 28; ++var5) {
					this.c.getOutStream().writeByte(1);
					this.c.getOutStream().writeWordBigEndianA(-1);
				}
			}

			this.c.getOutStream().endFrameVarSizeWord();
			this.c.flushOutStream();
			this.c.getOutStream().createFrameVarSizeWord(53);
			this.c.getOutStream().writeWord(6670);
			this.c.getOutStream().writeWord(o.getDuel().stakedItems.toArray().length);
			current = 0;

			for (var4 = o.getDuel().stakedItems.iterator(); var4.hasNext(); ++current) {
				i = (GameItem) var4.next();
				if (i.amount > 254) {
					this.c.getOutStream().writeByte(255);
					this.c.getOutStream().writeDWord_v2(i.amount);
				} else {
					this.c.getOutStream().writeByte(i.amount);
				}

				if (i.id > 30000 || i.id < 0) {
					i.id = 30000;
				}

				this.c.getOutStream().writeWordBigEndianA(i.id + 1);
			}

			if (current < 27) {
				for (var5 = current; var5 < 28; ++var5) {
					this.c.getOutStream().writeByte(1);
					this.c.getOutStream().writeWordBigEndianA(-1);
				}
			}

			this.c.getOutStream().endFrameVarSizeWord();
			this.c.flushOutStream();
		}
	}
	
	public boolean stakeItem(int itemID, int fromSlot, int amount) {
		World.getPlayerHandler();
		Client o = (Client) World.players[this.c.duelingWith];
        if(!c.openDuel && !o.openDuel) {
            declineDuel();
            return false;
        }
        if(!c.getItems().playerHasItem(itemID, amount))
            return false;
        
        if (amount <= 0)
            return false;
        
		if (c.playerRights < 3) {
			return false;
		}
			int[] var8 = MistexConfiguration.ITEM_TRADEABLE;
			int item = MistexConfiguration.ITEM_TRADEABLE.length;

			int amountOwned;
			int found;
			for (found = 0; found < item; ++found) {
				amountOwned = var8[found];
				if (amountOwned == itemID) {
					this.c.sendMessage("You can\'t stake this item.");
					return false;
				}
			}
			if (itemID != 995 & c.playerItems[fromSlot] == 996){
				return false;
			}
			if(!((c.playerItems[fromSlot] == itemID+1) && (c.playerItemsN[fromSlot] >= amount))) {
				c.sendMessage("You don't have that amount!");
				return false;
			}

				amountOwned = this.c.getItems().getItemAmount(itemID);
				if (amountOwned < amount) {
					amount = amountOwned;
				}
				c.sendMessage("Amount owned = " + amount);
				if (amount <= 0) {
					return false;
				} else if (o == null) {
					this.declineDuel();
					return false;
				} else if (o.duelStatus > 0 && this.c.duelStatus > 0) {
					if (!this.c.canOffer) {
						return false;
					} else {
						this.changeDuelStuff();

						if (!this.c.getItems().playerHasItem(itemID, amount)) {
							return false;
						} else {

							if (!isStackable(itemID)) {
								for (found = 0; found < amount; ++found) {
									if (this.c.getItems().playerHasItem(itemID, 1)) {
										this.stakedItems.add(new GameItem(itemID, 1));
										this.c.getItems().deleteItem(itemID, this.c.getItems().getItemSlot(itemID), 1);
									}
								}

								this.c.getItems().resetItems(3214);
								this.c.getItems().resetItems(3322);
								o.getItems().resetItems(3214);
								o.getItems().resetItems(3322);
								this.refreshDuelScreen();
								o.getDuel().refreshDuelScreen();
								this.c.getPA().sendFrame126("", 6684);
								o.getPA().sendFrame126("", 6684);
							}

							if (isStackable(itemID) || Item.itemIsNote[itemID]) {
								boolean var10 = false;
								Iterator<GameItem> var9 = this.stakedItems.iterator();

								while (var9.hasNext()) {
									GameItem var11 = (GameItem) var9.next();
									if (var11.id == itemID) {
										var10 = true;
										var11.amount += amount;
										this.c.getItems().deleteItem(itemID, this.c.getItems().getItemSlot(itemID), amount);
										break;
									}
								}

								if (!var10) {
									this.c.getItems().deleteItem(itemID, fromSlot, amount);
									this.stakedItems.add(new GameItem(itemID, amount));
								}
							}

							this.c.getItems().resetItems(3214);
							this.c.getItems().resetItems(3322);
							o.getItems().resetItems(3214);
							o.getItems().resetItems(3322);
							this.refreshDuelScreen();
							o.getDuel().refreshDuelScreen();
							this.c.getPA().sendFrame126("", 6684);
							o.getPA().sendFrame126("", 6684);
							return true;
						}
					}
				} else {
					this.declineDuel();
					o.getDuel().declineDuel();
					return false;
				}
	}

	public boolean fromDuel(int itemId, int fromSlot, int amount) {
		World.getPlayerHandler();
		Client o = (Client) World.players[this.c.duelingWith];

        if(!c.openDuel && !o.openDuel) {
            declineDuel();
            return false;
        }
		
		if (c.playerRights == 2) {
			return false;
		}
		if (o == null) {
			this.declineDuel();
			return false;
		} else if (o.duelStatus > 0 && this.c.duelStatus > 0) {
			if (!this.c.canOffer) {
				return false;
			} else {
				this.changeDuelStuff();
				boolean goodSpace = true;
				if (!Item.itemStackable[itemId]) {
					for (int item = 0; item < amount; ++item) {
						Iterator<GameItem> var8 = this.stakedItems.iterator();

						while (var8.hasNext()) {
							GameItem item1 = (GameItem) var8.next();
							if (item1.id == itemId) {
								if (!item1.stackable) {
									if (this.c.getItems().freeSlots() - 1 < this.c.duelSpaceReq) {
										goodSpace = false;
									} else {
										this.stakedItems.remove(item1);
										this.c.getItems().addItem(itemId, 1);
									}
								} else if (this.c.getItems().freeSlots() - 1 < this.c.duelSpaceReq) {
									goodSpace = false;
								} else if (item1.amount > amount) {
									item1.amount -= amount;
									this.c.getItems().addItem(itemId, amount);
								} else if (this.c.getItems().freeSlots() - 1 < this.c.duelSpaceReq) {
									goodSpace = false;
								} else {
									amount = item1.amount;
									this.stakedItems.remove(item1);
									this.c.getItems().addItem(itemId, amount);
								}
								break;
							}

							o.duelStatus = 1;
							this.c.duelStatus = 1;
							this.c.getItems().resetItems(3214);
							this.c.getItems().resetItems(3322);
							o.getItems().resetItems(3214);
							o.getItems().resetItems(3322);
							this.c.getDuel().refreshDuelScreen();
							o.getDuel().refreshDuelScreen();
							o.getPA().sendFrame126("", 6684);
						}
					}
				}

				Iterator<GameItem> var10 = this.stakedItems.iterator();

				while (var10.hasNext()) {
					GameItem var9 = (GameItem) var10.next();
					if (var9.id == itemId) {
						if (var9.stackable) {
							if (var9.amount > amount) {
								var9.amount -= amount;
								this.c.getItems().addItem(itemId, amount);
							} else {
								amount = var9.amount;
								this.stakedItems.remove(var9);
								this.c.getItems().addItem(itemId, amount);
							}
						}
						break;
					}
				}

				o.duelStatus = 1;
				this.c.duelStatus = 1;
				this.c.getItems().resetItems(3214);
				this.c.getItems().resetItems(3322);
				o.getItems().resetItems(3214);
				o.getItems().resetItems(3322);
				this.c.getDuel().refreshDuelScreen();
				o.getDuel().refreshDuelScreen();
				o.getPA().sendFrame126("", 6684);
				if (!goodSpace) {
					this.c.sendMessage("You have too many rules set to remove that item.");
					return true;
				} else {
					return true;
				}
			}
		} else {
			this.declineDuel();
			o.getDuel().declineDuel();
			return false;
		}
	}

	public void confirmDuel() {
		World.getPlayerHandler();
		Client o = (Client) World.players[this.c.duelingWith];
		if (o != null) {
			String itemId = "";
			Iterator<GameItem> lineNumber = this.stakedItems.iterator();

			GameItem rulesOption;
			while (lineNumber.hasNext()) {
				rulesOption = (GameItem) lineNumber.next();
				if (!Item.itemStackable[rulesOption.id] && !Item.itemIsNote[rulesOption.id]) {
					this.c.getItems();
					itemId = itemId + ItemAssistant.getItemName(rulesOption.id) + "\\n";
				} else {
					this.c.getItems();
					itemId = itemId + ItemAssistant.getItemName(rulesOption.id) + " x " + MistexUtility.format(rulesOption.amount) + "\\n";
				}
			}

			this.c.getPA().sendFrame126(itemId, 6516);
			itemId = "";
			lineNumber = o.getDuel().stakedItems.iterator();

			while (lineNumber.hasNext()) {
				rulesOption = (GameItem) lineNumber.next();
				if (!Item.itemStackable[rulesOption.id] && !Item.itemIsNote[rulesOption.id]) {
					this.c.getItems();
					itemId = itemId + ItemAssistant.getItemName(rulesOption.id) + "\\n";
				} else {
					this.c.getItems();
					itemId = itemId + ItemAssistant.getItemName(rulesOption.id) + " x " + MistexUtility.format(rulesOption.amount) + "\\n";
				}
			}

			this.c.getPA().sendFrame126(itemId, 6517);
			this.c.getPA().sendFrame126("", 8242);

			for (int var8 = 8238; var8 <= 8253; ++var8) {
				this.c.getPA().sendFrame126("", var8);
			}

			this.c.getPA().sendFrame126("Hitpoints will be restored.", 8250);
			this.c.getPA().sendFrame126("Boosted stats will be restored.", 8238);
			if (this.c.duelRule[8]) {
				this.c.getPA().sendFrame126("There will be obstacles in the arena.", 8239);
			}

			this.c.getPA().sendFrame126("", 8240);
			this.c.getPA().sendFrame126("", 8241);
			String[] var7 = new String[] { "Players cannot forfeit!", "Players cannot move.", "Players cannot use range.", "Players cannot use melee.", "Players cannot use magic.", "Players cannot drink pots.", "Players cannot eat food.", "Players cannot use prayer." };
			int var6 = 8242;

			for (int i = 0; i < 8; ++i) {
				if (this.c.duelRule[i]) {
					this.c.getPA().sendFrame126(var7[i], var6);
					++var6;
				}
			}

			this.c.getPA().sendFrame126("", 6571);
			this.c.getPA().sendFrame248(6412, 197);
		}
	}

	public void startDuel() {

		World.getPlayerHandler();
		Client o = (Client) World.players[this.c.duelingWith];

		if (o == null) {
			this.duelVictory();
		}

		this.c.headIconHints = 2;
		this.c.followId = -1;
		this.c.vengOn = false;
		int item;
		if (this.c.duelRule[7]) {
			for (item = 0; item < this.c.PRAYER.length; ++item) {
				this.c.prayerActive[item] = false;
				this.c.getPA().sendFrame36(this.c.PRAYER_GLOW[item], 0);
			}

			this.c.headIcon = -1;
			this.c.getPA().requestUpdates();
		}

		if (this.c.duelRule[11]) {
			this.c.getItems().removeItem(this.c.playerEquipment[0], 0);
		}

		if (this.c.duelRule[12]) {
			this.c.getItems().removeItem(this.c.playerEquipment[1], 1);
		}

		if (this.c.duelRule[13]) {
			this.c.getItems().removeItem(this.c.playerEquipment[2], 2);
		}

		if (this.c.duelRule[14] || this.c.duelRule[16] && this.c.getItems().is2handed(this.c.getItems().getItemName(this.c.playerEquipment[this.c.playerWeapon]).toLowerCase(), this.c.playerEquipment[this.c.playerWeapon])) {
			this.c.getItems().removeItem(this.c.playerEquipment[3], 3);
		}

		if (this.c.duelRule[15]) {
			this.c.getItems().removeItem(this.c.playerEquipment[4], 4);
		}

		if (this.c.duelRule[16]) {
			this.c.getItems().removeItem(this.c.playerEquipment[5], 5);
		}

		if (this.c.duelRule[17]) {
			this.c.getItems().removeItem(this.c.playerEquipment[7], 7);
		}

		if (this.c.duelRule[18]) {
			this.c.getItems().removeItem(this.c.playerEquipment[9], 9);
		}

		if (this.c.duelRule[19]) {
			this.c.getItems().removeItem(this.c.playerEquipment[10], 10);
		}

		if (this.c.duelRule[20]) {
			this.c.getItems().removeItem(this.c.playerEquipment[12], 12);
		}

		if (this.c.duelRule[21]) {
			this.c.getItems().removeItem(this.c.playerEquipment[13], 13);
		}

		this.c.duelStatus = 5;
		this.c.getPA().removeAllWindows();
		this.c.getCombat().resetPrayers();
		this.c.specAmount = 10.0D;
		this.c.getItems().addSpecialBar(this.c.playerEquipment[this.c.playerWeapon]);
		if (this.c.duelRule[8]) {
			if (this.c.duelRule[1]) {
				this.c.getPA().movePlayer(this.c.duelTeleX, this.c.duelTeleY, 0);
			} else {
				this.c.getPA().movePlayer(3366 + MistexUtility.random(12), 3246 + MistexUtility.random(6), 0);
			}
		} else if (this.c.duelRule[1]) {
			this.c.getPA().movePlayer(this.c.duelTeleX, this.c.duelTeleY, 0);
		} else {
			this.c.getPA().movePlayer(3335 + MistexUtility.random(12), 3246 + MistexUtility.random(6), 0);
		}

		this.c.getPA().createPlayerHints(10, o.playerId);
		this.c.getPA().showOption(3, 0, "Attack", 1);

		for (item = 0; item < 20; ++item) {
			this.c.playerLevel[item] = this.c.getPA().getLevelForXP(this.c.playerXP[item]);
			this.c.getPA().refreshSkill(item);
		}

		Iterator<GameItem> var3 = o.getDuel().stakedItems.iterator();

		while (var3.hasNext()) {
			GameItem var4 = (GameItem) var3.next();
			this.otherStakedItems.add(new GameItem(var4.id, var4.amount));
		}

		this.c.getPA().requestUpdates();
	}

	public void duelVictory() {
		if (this.c.duelStatus >= 5) {
			World.getPlayerHandler();
			Client o = (Client) World.players[this.c.playerKilled];

			if (o != null) {
				this.c.getPA().sendFrame126("" + o.combatLevel, 6839);
				this.c.getPA().sendFrame126(o.playerName, 6840);
				o.getCombat().resetPlayerAttack();
				PlayerSave.saveGame(o);
			} else {
				this.c.getPA().sendFrame126("", 6839);
				this.c.getPA().sendFrame126("", 6840);
			}

			this.c.getCombat().resetPrayers();

			for (int i = 0; i < 23; ++i) {
				this.c.playerLevel[i] = this.c.getPA().getLevelForXP(this.c.playerXP[i]);
				this.c.getPA().refreshSkill(i);
			}

			this.c.getPA().refreshSkill(3);
			this.duelRewardInterface();
			this.c.getPA().showInterface(6733);
			this.c.getPA().movePlayer(3362 + MistexUtility.random(5), 3263 + MistexUtility.random(5), 0);
			this.c.getPA().requestUpdates();
			this.c.getPA().showOption(2, 0, "Challenge", 3);
			this.c.getPA().createPlayerHints(10, -1);
			this.c.canOffer = true;
			this.c.duelSpaceReq = 0;
			this.c.duelingWith = 0;
	        c.openDuel = false;
	        o.openDuel = false;
			this.c.getCombat().resetPlayerAttack();
			this.c.duelRequested = false;
			c.duelsWon += 1;
			if (c.duelsWon == 1)
				c.sendMessage("@war@You have started the achievement: The Duelist ");
				InterfaceText.writeText(new AchievementTab(c));
			if (c.duelsWon == 100)
				AchievementHandler.activateAchievement(c, AchievementList.THE_DUELIST);
		}
	}

	public void duelRewardInterface() {

		this.c.getOutStream().createFrameVarSizeWord(53);
		this.c.getOutStream().writeWord(6822);
		this.c.getOutStream().writeWord(this.otherStakedItems.toArray().length);

		GameItem item;
		for (Iterator<GameItem> var2 = this.otherStakedItems.iterator(); var2.hasNext(); this.c.getOutStream().writeWordBigEndianA(item.id + 1)) {
			item = (GameItem) var2.next();
			if (item.amount > 254) {
				this.c.getOutStream().writeByte(255);
				this.c.getOutStream().writeDWord_v2(item.amount);
			} else {
				this.c.getOutStream().writeByte(item.amount);
			}

			if (item.id > 30000 || item.id < 0) {
				item.id = 30000;
			}
		}

		this.c.getOutStream().endFrameVarSizeWord();
		this.c.flushOutStream();
	}

	public void claimStakedItems() {
		Iterator<GameItem> var2 = this.otherStakedItems.iterator();

		GameItem item;
		int amount;
		int a;
		while (var2.hasNext()) {
			item = (GameItem) var2.next();
			if (item.id > 0 && item.amount > 0) {
				if (Item.itemStackable[item.id]) {
					if (!this.c.getItems().addItem(item.id, item.amount)) {
						Mistex.itemHandler.createGroundItem(this.c, item.id, this.c.getX(), this.c.getY(), item.amount, this.c.getId());
					}
				} else {
					amount = item.amount;

					for (a = 1; a <= amount; ++a) {
						if (!this.c.getItems().addItem(item.id, 1)) {
							Mistex.itemHandler.createGroundItem(this.c, item.id, this.c.getX(), this.c.getY(), 1, this.c.getId());
						}
					}
				}
			}
		}

		var2 = this.stakedItems.iterator();

		while (var2.hasNext()) {
			item = (GameItem) var2.next();
			if (item.id > 0 && item.amount > 0) {
				if (Item.itemStackable[item.id]) {
					if (!this.c.getItems().addItem(item.id, item.amount)) {
						Mistex.itemHandler.createGroundItem(this.c, item.id, this.c.getX(), this.c.getY(), item.amount, this.c.getId());
					}
				} else {
					amount = item.amount;

					for (a = 1; a <= amount; ++a) {
						if (!this.c.getItems().addItem(item.id, 1)) {
							Mistex.itemHandler.createGroundItem(this.c, item.id, this.c.getX(), this.c.getY(), 1, this.c.getId());
						}
					}
				}
			}
		}
		//this.c.getTradeLog().tradeReceived(c.getItems().getItemName(item.id), item.amount);
		//o.getTradeLog().tradeGive(c.getItems().getItemName(item.id), item.amount);
		PlayerSave.saveGame(this.c);
		this.resetDuel();
		this.resetDuelItems();
		this.c.duelStatus = 0;
	}

	public void declineDuel() {
		World.getPlayerHandler();
		Client o = (Client) World.players[this.c.duelingWith];
		if (o == null || this.c.duelStatus <= 4 && o.duelStatus <= 4) {
			this.c.getPA().removeAllWindows();
			this.c.canOffer = true;
			this.c.duelStatus = 0;
			this.c.duelingWith = 0;
	        c.openDuel = false;
	        o.openDuel = false;
			this.c.duelSpaceReq = 0;
			Iterator<GameItem> var3 = this.stakedItems.iterator();

			while (var3.hasNext()) {
				GameItem i = (GameItem) var3.next();
				if (i.amount >= 1) {
					if (!Item.itemStackable[i.id] && !Item.itemIsNote[i.id]) {
						this.c.getItems().addItem(i.id, 1);
					} else {
						this.c.getItems().addItem(i.id, i.amount);
					}
				}
			}

			this.stakedItems.clear();

			for (int var4 = 0; var4 < this.c.duelRule.length; ++var4) {
				this.c.duelRule[var4] = false;
			}

		}
	}

	public void resetDuel() {
		Client o = (Client) World.players[c.duelingWith];
		this.c.getPA().showOption(2, 0, "Challenge", 3);
		this.c.headIconHints = 0;

		for (int i = 0; i < this.c.duelRule.length; ++i) {
			this.c.duelRule[i] = false;
		}

		this.c.getPA().createPlayerHints(10, -1);
		this.c.duelStatus = 0;
		this.c.canOffer = true;
		this.c.duelSpaceReq = 0;
		this.c.duelingWith = 0;
        c.openDuel = false;
        o.openDuel = false;
		this.c.getPA().requestUpdates();
		this.c.getCombat().resetPlayerAttack();
		this.c.duelRequested = false;
	}

	public void resetDuelItems() {
		this.stakedItems.clear();
		this.otherStakedItems.clear();
	}

	public void changeDuelStuff() {

		World.getPlayerHandler();
		Client o = (Client) World.players[this.c.duelingWith];
		if (o != null) {
			o.duelStatus = 1;
			this.c.duelStatus = 1;
			o.getPA().sendFrame126("", 6684);
			this.c.getPA().sendFrame126("", 6684);
		}
	}

	public void selectRule(int i) {
		World.getPlayerHandler();
		Client o = (Client) World.players[this.c.duelingWith];
		if (o != null) {
			if (this.c.canOffer) {
				if (this.c.duelStatus < 4) {
					if (o.duelingWith == this.c.playerId && o.playerId == this.c.duelingWith) {
						this.changeDuelStuff();
						o.duelSlot = this.c.duelSlot;
						if (i >= 11 && this.c.duelSlot > -1) {
							if (this.c.playerEquipment[this.c.duelSlot] > 0) {
								if (!this.c.duelRule[i]) {
									this.c.duelSpaceReq = this.c.duelSpaceReq + 1;
								} else {
									this.c.duelSpaceReq = this.c.duelSpaceReq - 1;
								}
							}

							if (o.playerEquipment[o.duelSlot] > 0) {
								if (!o.duelRule[i]) {
									o.duelSpaceReq = o.duelSpaceReq + 1;
								} else {
									o.duelSpaceReq = o.duelSpaceReq - 1;
								}
							}
						}

						if (i >= 11 && (this.c.getItems().freeSlots() < this.c.duelSpaceReq || o.getItems().freeSlots() < o.duelSpaceReq)) {
							this.c.sendMessage("You or your opponent don\'t have the required space to set this rule.");
							if (this.c.playerEquipment[this.c.duelSlot] > 0) {
								this.c.duelSpaceReq = this.c.duelSpaceReq - 1;
							}

							if (o.playerEquipment[o.duelSlot] > 0) {
								o.duelSpaceReq = o.duelSpaceReq - 1;
							}

						} else {
							if (!this.c.duelRule[i]) {
								this.c.duelRule[i] = true;
								this.c.duelOption += this.c.DUEL_RULE_ID[i];
							} else {
								this.c.duelRule[i] = false;
								this.c.duelOption -= this.c.DUEL_RULE_ID[i];
							}

							this.c.getPA().sendFrame87(286, this.c.duelOption);
							o.duelOption = this.c.duelOption;
							o.duelRule[i] = this.c.duelRule[i];
							o.getPA().sendFrame87(286, o.duelOption);
							if (this.c.duelRule[8]) {
								if (this.c.duelRule[1]) {
									this.c.duelTeleX = 3366 + MistexUtility.random(12);
									o.duelTeleX = this.c.duelTeleX - 1;
									this.c.duelTeleY = 3246 + MistexUtility.random(6);
									o.duelTeleY = this.c.duelTeleY;
								}
							} else if (this.c.duelRule[1]) {
								this.c.duelTeleX = 3335 + MistexUtility.random(12);
								o.duelTeleX = this.c.duelTeleX - 1;
								this.c.duelTeleY = 3246 + MistexUtility.random(6);
								o.duelTeleY = this.c.duelTeleY;
							}

						}
					}
				}
			}
		}
	}
	
	
}
