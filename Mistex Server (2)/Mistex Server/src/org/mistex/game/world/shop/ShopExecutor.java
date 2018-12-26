package org.mistex.game.world.shop;

import org.mistex.game.MistexUtility;
import org.mistex.game.world.World;
import org.mistex.game.world.content.interfaces.InterfaceText;
import org.mistex.game.world.content.interfaces.impl.InformationTab;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.Player;
import org.mistex.game.world.player.item.Item;
import org.mistex.game.world.player.item.ItemAssistant;
import org.mistex.game.world.shop.Shop.Stock;

/**
 * Executes shops
 * @author Omar | Play Boy & Michael | Chex
 */
public class ShopExecutor {
	
	/**
	 * Shop color
	 */
	public static String SHOP_COLOR = "<col=0C871D>";

	/**
	 * Opens the shop interface
	 * @param c
	 * @param shop
	 */
	public static void open(Client c, int shop) {
		c.shopInterfaceOpen = true;
		Shop s = Shops.get(shop);
		update(c, s);
		for (int i = 0; i < s.stocks.size(); i++) {
			c.getPA().sendFrame126(s.stocks.get(i).price + "," + s.stocks.get(i).currency, 28000 + i);
		}
		c.shopping = true;
		c.shopIndex = shop;
	}

	/**
	 * Closes the shop interface
	 * @param c
	 */
	public static void close(Client c) {
		c.shopping = false;
		c.shopIndex = -1;
		c.shopInterfaceOpen = false;
		c.getPA().closeAllWindows();
		  InterfaceText.writeText(new InformationTab(c));
	}

	/**
	 * Updates the shop interface
	 * @param c
	 * @param shop
	 */
	protected static void update(Client c, Shop shop) {
		if (c.shopInterfaceOpen) {
			c.getItems().resetItems(3823);
			int length = shop.stocks.size();
			c.getOutStream().createFrameVarSizeWord(53);
			c.getOutStream().writeWord(3900);
			c.getOutStream().writeWord(length);
			for (int i = 0; i < length; i++) {
				if (shop.stocks.get(i).amount < 255) {
					c.getOutStream().writeByte(shop.stocks.get(i).amount);
				} else {
					c.getOutStream().writeByte(255);
					c.getOutStream().writeDWord_v2(2126432612);
				}
				c.getOutStream().writeWordBigEndianA(shop.stocks.get(i).id + 1);
			}
			c.getOutStream().endFrameVarSizeWord();
			c.flushOutStream();
			c.getPA().sendFrame248(3824, 3822);
			c.getPA().sendFrame126(shop.name, 3901);
		}
	}

	/**
	 * Buy value for shop items
	 * @param c
	 * @param shop
	 * @param stock
	 */
	public static void buyValue(Client c, int shop, int stock) {
		if (c.shopIndex == 13) {
			c.sendMessage("["+SHOP_COLOR+"*</col>] Skill capes are 99k (99,000) coins each. Each cape comes with a hood.");
			return;
		}
		Stock s = Shops.get(shop).stocks.get(stock);
		c.sendMessage("["+SHOP_COLOR+"*</col>] "+SHOP_COLOR+""+ItemAssistant.getItemName(s.id) + "</col> currency costs "+SHOP_COLOR+"" + MistexUtility.formatValue(s.price) + "</col> "+SHOP_COLOR+"" + s.getCurrencyName(s.price) + "</col>.");
	}

	/**
	 * Buying shop items
	 * @param c
	 * @param shop
	 * @param stock
	 * @param amount
	 */
	public static void buy(Client c, int shop, int stock, int amount) {
		if (c.shopInterfaceOpen) {
			Stock s = Shops.get(shop).stocks.get(stock);
			if (s == null) {
				c.sendMessage("["+SHOP_COLOR+"*</col>] This shop does not sell that item.");
				return;
			}
			if (s.amount < amount && c.shopIndex != 13) {
				amount = s.amount;
			}
			if (s.price > 0) {
				if (s.pointCurrency()) {
					int points = s.getCurrencyPoints(c);
					if (points < s.price) {
						c.sendMessage("["+SHOP_COLOR+"*</col>] You don't have enough "+SHOP_COLOR+"" + s.getCurrencyName(0) + "</col>.");
						return;
					}
					amount = Math.min(points / s.price, amount);
					amount = checkBuyLimits(c, s.id, amount);
					if (amount <= 0) {
						if (s.removeTimer != -1) {
							Shops.get(shop).removeStock(stock);
						}
						return;
					}
					s.removeCurrencyPoints(c, amount * s.price);
				} else {
					int item = s.getCurrencyItem();
					if (!c.getItems().playerHasItem(item, s.price)) {
						c.sendMessage("["+SHOP_COLOR+"*</col>] You don't have enough "+SHOP_COLOR+"" + s.getCurrencyName(0) + "</col>.");
						return;
					}
					amount = Math.min(c.playerItemsN[c.getItems().getItemSlot(item)] / s.price, amount);
					amount = checkBuyLimits(c, s.id, amount * (c.shopIndex == 13 ? 2 : 1));
					if (amount <= 0) {
						if (s.removeTimer != -1) {
							Shops.get(shop).removeStock(stock);
						}
						return;
					}
					c.getItems().deleteItem(item, amount * s.price);
				}
			} else {
				amount = checkBuyLimits(c, s.id, amount);
				if (amount <= 0) {
					return;
				}
			}
			
			if (c.shopIndex != 13)
				s.amount -= amount;

			if (s.amount <= 0 && s.removeTimer != -1) {
				Shops.get(shop).removeStock(stock);
			}
			
			if (c.shopIndex == 13) {
				c.getItems().addItem(s.id + (c.total99s > 1 ? 1 : 2), amount/2);
				c.getItems().addItem(s.id, amount/2);
			} else {
				c.getItems().addItem(s.id, amount);
				c.sendMessage("["+SHOP_COLOR+"*</col>] You have purchased "+SHOP_COLOR+""+MistexUtility.format(amount)+"</col>x of "+SHOP_COLOR+"" + ItemAssistant.getItemName(s.id) + "</col>!");
			}
			for (Player p : World.players) {
				if (p != null && p.shopIndex == shop) {
					update((Client) p, Shops.get(shop));
				}
			}
		}

	}

	/**
	 * Checks the buy limits
	 * @param c
	 * @param id
	 * @param amount
	 * @return
	 */
	private static int checkBuyLimits(Client c, int id, int amount) {
		if (Item.itemStackable[id] && c.getItems().playerHasItem(id)) {
			int current = c.playerItemsN[c.getItems().getItemSlot(id)];
			if (amount + current > Integer.MAX_VALUE) {
				amount = Integer.MAX_VALUE - current;
				c.sendMessage("["+SHOP_COLOR+"*</col>] You don't have enough space in your inventory.");
			}
		} else if (!Item.itemStackable[id]) {
			int free = c.getItems().freeSlots();
			if (amount > free) {
				amount = free;
				c.sendMessage("["+SHOP_COLOR+"*</col>] You don't have enough space in your inventory.");
			}
		}
		return amount;
	}

	/**
	 * Selling value for items
	 * @param c
	 * @param shop
	 * @param slot
	 */
	public static void sellValue(Client c, int shop, int slot) {
		int item = c.playerItems[slot] - 1;
		Stock s = Shops.get(shop).getSellStock(item);
		if (s == null && (shop != 0 || !ItemAssistant.tradeable(item) || item == 995)|| shop == 13 || shop == 32) {
			c.sendMessage("["+SHOP_COLOR+"*</col>] You can't sell "+SHOP_COLOR+"" + ItemAssistant.getItemName(item).toLowerCase() + " </col>to this store.");
			return;
		}
		int price = (int) ((s != null ? s.price : c.getItems().getItemShopValue(item)) * 0.66f);
		if (price >= 5000000) {
			c.sendMessage("["+SHOP_COLOR+"*</col>] "+SHOP_COLOR+""+MistexUtility.capitalize(ItemAssistant.getItemName(item).toLowerCase()) + " </col>is too expensive to sell to this store.");
			return;
		}
		c.sendMessage("["+SHOP_COLOR+"*</col>] "+ItemAssistant.getItemName(item) + " </col>to store will buy for "+SHOP_COLOR+"" + MistexUtility.formatValue(price) + " " + (s != null ? s.getCurrencyName(price) : "coins") + "</col>to.");
	}

	/**
	 * Selling items to shop
	 * @param c
	 * @param shop
	 * @param slot
	 * @param amount
	 */
	public static void sell(Client c, int shop, int slot, int amount) {
		if (c.shopInterfaceOpen) {
			int item = c.playerItems[slot] - 1;
			Stock s = Shops.get(shop).getSellStock(item);
			if (s == null && (shop != 0 || !ItemAssistant.tradeable(item) || item == 995)|| shop == 13) {
				c.sendMessage("["+SHOP_COLOR+"*</col>] You can't sell "+SHOP_COLOR+"" + ItemAssistant.getItemName(item).toLowerCase() + " </col>to this store.");
				return;
			}
			int current = 0;
			for (int i = 0; i < 28; i++) {
				if (c.playerItems[i] - 1 == item) {
					current += c.playerItemsN[i];
				}
			}
			if (amount > current) {
				amount = current;
			}
			if (amount <= 0) {
				return;
			}
			int initPrice = (int) (s != null ? s.price : c.getItems().getItemShopValue(item));

			if (initPrice >= 5000000) {
				c.sendMessage("["+SHOP_COLOR+"*</col>] "+SHOP_COLOR+""+MistexUtility.capitalize(ItemAssistant.getItemName(item).toLowerCase()) + " </col>is too expensive to sell to this store.");
				return;
			}
			if (s != null) {
				s.amount += amount;
			}
			if (amount == 1 || Item.itemStackable[item]) {
				c.getItems().deleteItem(item, slot, amount);
			} else {
				c.getItems().deleteItem3(item, amount);
			}
			for (Player p : World.players) {
				if (p != null && p.shopIndex == shop) {
					update((Client) p, Shops.get(shop));
				}
			}
			if (initPrice > 0) {
				int price = (int) (initPrice * 0.66f);
				if (price <= 0)
					price = 1;
				int currency = s != null ? s.getCurrencyItem() : 995;
				if (c.getItems().playerHasItem(currency, 1)) {
					amount = Math.min((Integer.MAX_VALUE - c.playerItemsN[c.getItems().getItemSlot(currency)]) / price, amount);
				} else if (c.getItems().freeSlots() > 0) {
					amount = Math.min(Integer.MAX_VALUE / price, amount);
				}
				if (s != null && amount > Integer.MAX_VALUE - s.amount) {
					amount = Integer.MAX_VALUE - s.amount;
				}
				c.getItems().addItem(currency, price * amount);
			}
		}
	}

}
