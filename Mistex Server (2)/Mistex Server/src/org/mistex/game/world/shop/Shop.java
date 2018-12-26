package org.mistex.game.world.shop;

import java.util.ArrayList;

import org.mistex.game.world.content.SkillLead;
import org.mistex.game.world.player.Client;

/**
 * Shop
 * @author Omar | Play Boy & Michael | Chex
 */
public class Shop {

	/**
	 * Array list for stocks
	 */
	public ArrayList<Stock> stocks = new ArrayList<Stock>();
	
	/**
	 * Shop name
	 */
	public final String name;

	/**
	 * Shop
	 * @param name
	 * @param data
	 */
	protected Shop(String name, String data) {
		this.name = name;
		if (data != null) {
			String[] components = data.replaceAll("<", "").split(">");
			int length = Math.min(components.length, Shops.MAX_STOCKS);
			for (int i = 0; i < length; i++) {
				stocks.add(new Stock(components[i]));
			}
		}
	}

	/**
	 * Adds stock to shops
	 * @param item
	 * @param amount
	 * @param price
	 * @param deletable
	 * @return
	 */
	protected Stock addStock(int item, int amount, double price, boolean deletable) {
		stocks.add(new Stock("id:" + item + ", amount:" + amount + ", price:" + (int) (price) + ", currency:coins>"));
		if (deletable)
			stocks.get(stocks.size() - 1).setRemoveTimer((byte) 4);
		return stocks.get(stocks.size() - 1);
	}

	/**
	 * Removes stock from shops
	 * @param slot
	 */
	protected void removeStock(int slot) {
		stocks.remove(slot);
		stocks.trimToSize();
	}

	/**
	 * Restores shop
	 * @return
	 */
	protected boolean restore() {
		boolean changed = false;
		for (int index = 0; index < stocks.size(); index++) {
			Stock stock = stocks.get(index);
			if (stock.removeTimer > stock.defaultTimer) {
				stock.removeTimer--;
				if (stock.removeTimer <= 0) {
					removeStock(index);
					changed = true;
				}
			} else if (stock.amount < stock.defaultamount) {
				stock.amount++;
				changed = true;
			} else if (stock.amount > stock.defaultamount) {
				stock.amount--;
				changed = true;
			}
		}
		return changed;
	}

	/**
	 * Get's the stock sell
	 * @param id
	 * @return
	 */
	protected Stock getSellStock(int id) {
		for (Stock s : stocks) {
			if (s.id == id && !s.pointCurrency()) {
				return s;
			}
		}
		return null;
	}

	protected static class Stock {

		protected static final byte COINS = 0;
		protected static final byte TOKKULS = 1;
		protected static final byte DONATOR_POINTS = 2;
		protected static final byte VOTE_POINTS = 3;
		protected static final byte PK_POINTS = 4;
		protected static final byte ACHIEVEMENT_POINTS = 5;
		protected static final byte PEST_POINTS = 6;
		protected static final byte SLAYER_POINTS = 7;
		protected static final byte SKILLING_POINTS = 8;
		protected static final byte PRESTIGE_POINTS = 9;
		protected static final byte DUNG_TOKENS = 10;

		protected short id = 0;
		protected byte removeTimer = -1;
		protected byte defaultTimer = 0;
		protected int amount = 1;
		protected int defaultamount = amount;
		protected int price = 0;
		protected byte currency = COINS;

		private Stock(String data) {
			String[] components = data.replaceAll(" ", "").toLowerCase().split(",");
			for (String s : components) {
				if (s.startsWith("id:")) {
					id = Short.parseShort(s.substring(3));
				} else if (s.startsWith("amount:")) {
					amount = Integer.parseInt(s.substring(7));
					defaultamount = amount;
				} else if (s.startsWith("price:")) {
					price = Integer.parseInt(s.substring(6).replace("m", "000000").replace("k", "000"));
				} else if (s.startsWith("currency:")) {
					s = s.substring(9);
					if (s.equals("tokkuls")) {
						currency = TOKKULS;
					} else if (s.equals("donator")) {
						currency = DONATOR_POINTS;
					} else if (s.equals("vote")) {
						currency = VOTE_POINTS;
					} else if (s.equals("pk")) {
						currency = PK_POINTS;
					} else if (s.equals("achievement")) {
						currency = ACHIEVEMENT_POINTS;
					} else if (s.equals("pest")) {
						currency = PEST_POINTS;
					} else if (s.equals("slayer")) {
						currency = SLAYER_POINTS;
					} else if (s.equals("skilling")) {
						currency = SKILLING_POINTS;
					} else if (s.equals("prestige")) {
						currency = PRESTIGE_POINTS;
					} else if (s.equals("dungeoneering")) {
						currency = DUNG_TOKENS;
					}
				}
			}
		}

		protected boolean pointCurrency() {
			return currency == DUNG_TOKENS || currency == PRESTIGE_POINTS || currency == SKILLING_POINTS || currency == DONATOR_POINTS || currency == VOTE_POINTS || currency == PK_POINTS || currency == ACHIEVEMENT_POINTS || currency == PEST_POINTS || currency == SLAYER_POINTS;
		}

		protected String getCurrencyName(int amount) {
			String name = "null";
			if (currency == COINS) {
				name = "coin";
			} else if (currency == TOKKULS) {
				name = "tokkul";
			} else if (currency == DONATOR_POINTS) {
				name = "donator point";
			} else if (currency == VOTE_POINTS) {
				name = "vote point";
			} else if (currency == PK_POINTS) {
				name = "pk point";
			} else if (currency == ACHIEVEMENT_POINTS) {
				name = "achievement point";
			} else if (currency == PEST_POINTS) {
				name = "pest control point";
			} else if (currency == SLAYER_POINTS) {
				name = "slayer point";
			} else if (currency == SKILLING_POINTS) {
				name = "skilling point";
			} else if (currency == PRESTIGE_POINTS) {
				name = "prestige point";
			} else if (currency == DUNG_TOKENS) {
				name = "dungeoneering token";
			}
			return name + (amount != 1 ? "s" : "");
		}

		protected int getCurrencyItem() {
			if (currency == COINS) {
				return 995;
			} else if (currency == TOKKULS) {
				return 6529;
			} else {
				return 0;
			}
		}

		protected int getCurrencyPoints(Client c) {
			if (currency == DONATOR_POINTS) {
				return c.donatorPoints;
			} else if (currency == VOTE_POINTS) {
				return c.votingPoints;
			} else if (currency == PK_POINTS) {
				return c.pkPoints;
			} else if (currency == ACHIEVEMENT_POINTS) {
				return c.achievementPoints;
			} else if (currency == PEST_POINTS) {
				return c.pcPoints;
			} else if (currency == SLAYER_POINTS) {
				return c.slayerPoints;
			} else if (currency == SKILLING_POINTS) {
				return c.skillingPoints;
			} else if (currency == DUNG_TOKENS) {
				return c.dungTokens;
			} else if (currency == PRESTIGE_POINTS) {
				return c.prestigePoints;
			} else {
				return 0;
			}
		}

		protected void removeCurrencyPoints(Client c, int amount) {
			if (currency == DONATOR_POINTS) {
				c.donatorPoints -= amount;
			} else if (currency == VOTE_POINTS) {
				c.votingPoints -= amount;
			} else if (currency == PK_POINTS) {
				c.pkPoints -= amount;
			} else if (currency == ACHIEVEMENT_POINTS) {
				c.achievementPoints -= amount;
			} else if (currency == PEST_POINTS) {
				c.pcPoints -= amount;
			} else if (currency == SLAYER_POINTS) {
				c.slayerPoints -= amount;
			} else if (currency == SKILLING_POINTS) {
				c.skillingPoints -= amount;
			} else if (currency == PRESTIGE_POINTS) {
				c.prestigePoints -= amount;
			} else if (currency == DUNG_TOKENS) {
				c.dungTokens -= amount;
			}
		}

		public void setRemoveTimer(byte removeTimer) {
			this.removeTimer = removeTimer;
		}
	}

	/**
	 * Skillcapes list
	 */
	public static int[] skillCapes = { 9747, 9753, 9750, 9768, 9756, 9759, 9762, 9801, 9807, 9783, 9798, 9804, 9780, 9795, 9792, 9774, 9771, 9777, 9786, 9810, 9765, 9948 };

	/**
	 * Opens the skillcapes shop
	 * @param client
	 */
	public static void openSkillcapeShop(Client client) {
		client.shopInterfaceOpen = true;
		client.shopping = true;
		client.shopIndex = 13;
		client.getItems().resetItems(3823);
		client.getOutStream().createFrameVarSizeWord(53);
		client.getOutStream().writeWord(3900);
		client.getOutStream().writeWord(client.total99s);
		for (int i = 0; i < skillCapes.length; i++) {
			if (client.getLevelForXP(client.playerXP[i]) < 99)
				continue;
			client.getOutStream().writeByte(1);
			client.getOutStream().writeWordBigEndianA(skillCapes[i] + (client.total99s > 1 ? 1 : 0) + 1);
		}
		client.getOutStream().endFrameVarSizeWord();
		client.flushOutStream();
		client.getPA().sendFrame248(3824, 3822);
		client.getPA().sendFrame126("Skill Cape Shop", 3901);
		for (int i = 0; i < client.total99s; i++) {
			client.getPA().sendFrame126(99000 + "," + 0, 28000 + i);
		}
	}
	
	/**
	 * Opens the untrimmed skillcapes shop
	 * @param client
	 */
	public static void openUntrimedSkillcapeShop(Client client) {
		int length = 0;
		for (int i = 0; i < SkillLead.leaders.length; i++) {
			if (SkillLead.leaders[i].getName().equals(client.playerName)) {
				length++;
			}
		}
		client.shopInterfaceOpen = true;
		client.shopping = true;
		client.shopIndex = 13;
		client.getItems().resetItems(3823);
		client.getOutStream().createFrameVarSizeWord(53);
		client.getOutStream().writeWord(3900);
		client.getOutStream().writeWord(length);
		for (int index = 0; index < SkillLead.leaders.length; index++) {
			if (!SkillLead.leaders[index].getName().equals(client.playerName))
				continue;
			client.getOutStream().writeByte(255);
			client.getOutStream().writeDWord_v2(2126432612);
			client.getOutStream().writeWordBigEndianA(skillCapes[index] + 1);
		}
		client.getOutStream().endFrameVarSizeWord();
		client.flushOutStream();
		client.getPA().sendFrame248(3824, 3822);
		client.getPA().sendFrame126("Untrimed Skill Cape Shop", 3901);
		for (int i = 0; i < length; i++) {
			client.getPA().sendFrame126(99000 + "," + 0, 28000 + i);
		}
	}
	
	/**
	 * Adds skillcapes to the shop
	 * @param client
	 */
	public static void addSkillCapes(Client client) {
		for (int i = 0; i < skillCapes.length; i++) {
			if (client.getLevelForXP(client.playerXP[i]) < 99)
				continue;
			Shops.get(13).addStock(skillCapes[i] + (client.total99s > 1 ? 1 : 0), 1, 99000, false);
		}
	}
	
	/**
	 * Buys skillcapes from the shop
	 * @param c
	 * @param removeId
	 * @param amount
	 */
	public static void buySkillCape(Client c, int removeId, int amount) {
		if (c.shopIndex == 13) {
			int slotsFree = c.getItems().freeSlots();
			if (slotsFree < 2 || !c.getItems().playerHasItem(995, 99000))
				return;
			if (slotsFree > amount) slotsFree = amount;
			while (slotsFree / 2 > 0) {
				c.getItems().deleteItem(995, 99000);
				c.getItems().addItem(removeId, 1);
				c.getItems().addItem(removeId + 1 + (c.total99s > 1 ? 0 : 1), 1);
				slotsFree -= 2;
			}
			c.getItems().resetItems(3823);
			return;
		}
	}
}