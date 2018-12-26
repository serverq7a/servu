package org.mistex.game.world.content.skill.crafting;

import org.mistex.game.world.event.CycleEvent;
import org.mistex.game.world.event.CycleEventContainer;
import org.mistex.game.world.event.CycleEventHandler;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.item.ItemAssistant;

public class JewelryMaking extends CraftingData {

	public static final int GOLD_BAR = 2357;

	public static void mouldRing(Client c, int item, int amount) {
		RingData ring = RingData.forId(item);
		if (ring == null) {
			c.sendMessage("That cannot be created into a ring.");
			return;
		}
		mouldJewelry(c, ring.getItemReq(), ring.getProduct(), amount, ring.getReqLevel(), ring.getExp());
	}

	public static void mouldNecklace(Client c, int item, int amount) {
		NecklaceData necklace = NecklaceData.forId(item);
		if (necklace == null) {
			c.sendMessage("That cannot be created into a necklace.");
			return;
		}
		mouldJewelry(c, necklace.getItemReq(), necklace.getProduct(), amount, necklace.getReqLevel(), necklace.getExp());
	}

	public static void mouldAmulet(Client c, int item, int amount) {
		AmuletData amulet = AmuletData.forId(item);
		if (amulet == null) {
			c.sendMessage("That cannot be created into an amulet.");
			return;
		}
		mouldJewelry(c, amulet.getItemReq(), amulet.getProduct(), amount, amulet.getReqLevel(), amulet.getExp());
	}

	private static int time;

	public static void mouldJewelry(final Client c, final int required, final int itemId, final int amount, final int level, final int xp) {
		if (c.isSkilling == true) {
			return;
		}
		if (c.playerLevel[12] < level) {
			c.sendMessage("You need a crafting level of " + level + " to mould this item.");
			return;
		}
		if (!c.getItems().playerHasItem(2357)) {
			c.sendMessage("You need a gold bar to mould this item.");
			return;
		}
		final String itemRequired = ItemAssistant.getItemName(required);
		if (!c.getItems().playerHasItem(required)) {
			c.sendMessage("You need " + ((itemRequired.startsWith("A") || itemRequired.startsWith("E") || itemRequired.startsWith("O")) ? "an" : "a") + " " + itemRequired.toLowerCase() + " to mould this item.");
			return;
		}
		time = amount;
		c.getPA().removeAllWindows();
		final String itemName = ItemAssistant.getItemName(itemId);
		c.startAnimation(899);
		c.isSkilling = true;
		CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				if (c.isSkilling == true) {
					time--;
					if (time == 0) {
						container.stop();
					}
					if (c.getItems().playerHasItem(2357) && c.getItems().playerHasItem(required)) {
						c.getItems().deleteItem(2357, 1);
						if (required != 2357) {
							c.getItems().deleteItem(required, 1);
						}
						c.startAnimation(899);
						new JewelryMaking().addExp(c, xp);
						new JewelryMaking().giveItem(c, itemId, 1, "You make " + ((itemName.startsWith("A") || itemName.startsWith("E") || itemName.startsWith("O")) ? "an" : "a") + " " + itemName.toLowerCase());
					} else {
						container.stop();
					}
				} else {
					container.stop();
				}
			}

			@Override
			public void stop() {
				c.isSkilling = false;
			}
		}, 4);
	}

	public static void stringAmulet(final Client c, final int itemUsed, final int usedWith) {
		final int amuletId = (itemUsed == 1759 ? usedWith : itemUsed);
		for (AmuletData amulet : AmuletData.values()) {
			if (amuletId == amulet.getProduct()) {
				c.getItems().deleteItem(1759, 1);
				c.getItems().deleteItem(amuletId, 1);
				c.getItems().addItem(amulet.getStringedProduct(), 1);
				new JewelryMaking().addExp(c, amulet.getExp());
			}
		}
	}

	private final static int[][] MOULD_INTERFACE_IDS = {
	/* Rings */
	{ 1635, 1637, 1639, 1641, 1643, 1645, 6583 },
	/* Neclece */
	{ 1654, 1656, 1658, 1660, 1662, 1664, 11128 },
	/* amulet */
	{ 1673, 1675, 1677, 1679, 1681, 1683, 6585 }

	};

	public static void jewelryInterface(final Client c) {
		c.getPA().showInterface(4161);
		/* Rings */
		if (c.getItems().playerHasItem(1592, 1)) {
			for (int i = 0; i < MOULD_INTERFACE_IDS[0].length; i++) {
				c.getPA().sendFrame34(MOULD_INTERFACE_IDS[0][i], i, 4233, 1);
			}
			c.getPA().sendFrame34(1643, 4, 4233, 1);
			c.getPA().sendFrame126("", 4230);
			c.getPA().sendFrame246(4229, 0, -1);
		} else {
			c.getPA().sendFrame246(4229, 120, 1592);
			for (int i = 0; i < MOULD_INTERFACE_IDS[0].length; i++) {
				c.getPA().sendFrame34(-1, i, 4233, 1);
			}
			c.getPA().sendFrame126("You need a ring mould to craft rings.", 4230);
		}
		/* Necklace */
		if (c.getItems().playerHasItem(1597, 1)) {
			for (int i = 0; i < MOULD_INTERFACE_IDS[1].length; i++) {
				c.getPA().sendFrame34(MOULD_INTERFACE_IDS[1][i], i, 4239, 1);
			}
			c.getPA().sendFrame34(1662, 4, 4239, 1);
			c.getPA().sendFrame246(4235, 0, -1);
			c.getPA().sendFrame126("", 4236);
		} else {
			c.getPA().sendFrame246(4235, 120, 1597);
			c.getPA().sendFrame126("You need a necklace mould to craft necklaces", 4236);
			for (int i = 0; i < MOULD_INTERFACE_IDS[1].length; i++) {
				c.getPA().sendFrame34(-1, i, 4239, 1);
			}
		}
		/* Amulets */
		if (c.getItems().playerHasItem(1595, 1)) {
			for (int i = 0; i < MOULD_INTERFACE_IDS[2].length; i++) {
				c.getPA().sendFrame34(MOULD_INTERFACE_IDS[2][i], i, 4245, 1);
			}
			c.getPA().sendFrame34(1681, 4, 4245, 1);
			c.getPA().sendFrame246(4241, 0, -1);
			c.getPA().sendFrame126("", 4242);
		} else {
			c.getPA().sendFrame246(4241, 120, 1595);
			c.getPA().sendFrame126("You need a amulet mould to craft necklaces", 4242);
			for (int i = 0; i < MOULD_INTERFACE_IDS[2].length; i++) {
				c.getPA().sendFrame34(-1, i, 4245, 1);
			}
		}
	}
}