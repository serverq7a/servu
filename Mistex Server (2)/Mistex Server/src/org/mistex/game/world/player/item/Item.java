package org.mistex.game.world.player.item;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Logger;

import org.mistex.game.Mistex;
import org.mistex.game.MistexConfiguration;

public class Item {
	
	private static final Logger logger = Logger.getLogger(Mistex.class.getName());

	public static boolean playerCape(int itemId) {
		String[] data = { "TokHaar-Kal", "Ava's accumulator", "cloak", "cape",
				"Ava's attractor", "Ava's alerter" };
		String item = getItemName(itemId);
		if (item == null) {
			return false;
		}
		boolean item1 = false;
		for (int i = 0; i < data.length; i++) {
			if (item.endsWith(data[i]) || item.contains(data[i])) {
				item1 = true;
			}
		}
		return item1;
	}

	public static boolean playerBoots(int itemId) {
		String[] data = { "Glaiven boots", "Ragefire boots", "Steadfast boots",
				"Flippers", "Shoes", "shoes", "boots", "Boots" };
		String item = getItemName(itemId);
		if (item == null) {
			return false;
		}
		boolean item1 = false;
		for (int i = 0; i < data.length; i++) {
			if (item.endsWith(data[i]) || item.contains(data[i])) {
				item1 = true;
			}
		}
		return item1;
	}

	public static boolean playerGloves(int itemId) {
		String[] data = { "Combat bracelet (4)", "Combat bracelet", "Bracelet",
				"Gloves", "gloves", "glove", "Glove", "gauntlets", "Gauntlets",
				"vamb" };
		String item = getItemName(itemId);
		if (item == null) {
			return false;
		}
		boolean item1 = false;
		for (int i = 0; i < data.length; i++) {
			if (item.endsWith(data[i]) || item.contains(data[i])) {
				item1 = true;
			}
		}
		return item1;
	}

	public static boolean playerShield(int itemId) {
		String[] data = { "Toktz-ket-xil", "defender", "kiteshield", "Book",
				"book", "Kiteshield", "shield", "Shield", "Kite", "kite",
				"spirit", "Mages' book", };
		String item = getItemName(itemId);
		if (item == null) {
			return false;
		}
		boolean item1 = false;
		for (int i = 0; i < data.length; i++) {
			if (item.endsWith(data[i]) || item.contains(data[i])) {
				item1 = true;
			}
		}
		return item1;
	}

	public static boolean playerAmulet(int itemId) {
		String[] data = { "Gnome scarf", "scarf", "Phoenix necklace",
				"necklace", "amulet", "Amulet", "Pendant", "pendant", "Symbol",
				"symbol", "Arcane stream", "Arcane blast", "Arcane pulse" };
		String item = getItemName(itemId);
		if (item == null) {
			return false;
		}
		boolean item1 = false;
		for (int i = 0; i < data.length; i++) {
			if (item.endsWith(data[i]) || item.contains(data[i])) {
				item1 = true;
			}
		}
		return item1;
	}

	public static boolean playerArrows(int itemId) {
		String[] data = { "Arrows", "arrows", "Arrow", "arrow", "Bolts",
				"bolts", "Bolt rack" };
		String item = getItemName(itemId);
		if (item == null) {
			return false;
		}
		boolean item1 = false;
		for (int i = 0; i < data.length; i++) {
			if (item.endsWith(data[i]) || item.contains(data[i])) {
				item1 = true;
			}
		}
		return item1;
	}

	public static boolean playerRings(int itemId) {
		String[] data = { "ring", "rings", "Ring", "Rings", };
		String item = getItemName(itemId);
		if (item == null) {
			return false;
		}
		boolean item1 = false;
		for (int i = 0; i < data.length; i++) {
			if (item.endsWith(data[i]) || item.contains(data[i])) {
				item1 = true;
			}
		}
		return item1;
	}

	public static boolean playerHats(int itemId) {
		String[] data = { "Fez", "Bearhead", "Wolf mask", "Bat mask",
				"Penguin mask", "Cat mask", "Guthix mitre", "Saradomin mitre",
				"Zamorak mitre", "mitre", "Feather headdress", "boater",
				"cowl", "peg", "coif", "helm", "coif", "mask", "hat",
				"headband", "hood", "disguise", "cavalier", "full", "tiara",
				"helmet", "Hat", "ears", "partyhat", "beret", "facemask", "sallet", 
				"hat(g)", "hat(t)", "bandana", "Helm", "helm", "helm (e)", "helm (charged)",
				"helmet (charged)", "helmet (e)", "Helm of neitiznot (e)", "Helm of neitiznot (charged)", "helm (g)"
			};
		String item = getItemName(itemId);
		if (item == null) {
			return false;
		}
		for (int i = 0; i < data.length; i++) {
			if (item.endsWith(data[i]) || item.contains(data[i])) {
				return true;
			}
		}
		return false;
	}

	public static boolean playerLegs(int itemId) {
		String[] data = { "tassets", "chaps", "bottoms", "gown", "trousers",
				"platelegs", "robe", "plateskirt", "legs", "leggings",
				"shorts", "Skirt", "skirt", "cuisse", "Trousers", "Witchdoctor robes" };
		String item = getItemName(itemId);
		if (item == null) {
			return false;
		}
		boolean item1 = false;
		for (int i = 0; i < data.length; i++) {
			if ((item.endsWith(data[i]) || item.contains(data[i]))
					&& (!item.contains("top") && (!item.contains("robe (g)") && (!item
							.contains("robe (t)"))))) {
				item1 = true;
			}
		}
		return item1;
	}

	public static boolean playerBody(int itemId) {
		String[] data = { "body", "Runecrafter robe", "top", "Priest gown", "apron", "shirt",
				"platebody", "robetop", "body(g)", "body(t)",
				"Wizard robe (g)", "Wizard robe (t)", "body", "brassard",
				"blouse", "tunic", "leathertop", "Saradomin plate",
				"chainbody", "hauberk", "Shirt", "torso", "chestplate",
				"Saradomin", "Guthix", "Zamorak", "Battle-mage robe",
				"Trickster robe", "Vanguard body", "Moonclan armour", "Runecrafter robe" };
		String item = getItemName(itemId);
		if (item == null) {
			return false;
		}
		boolean item1 = false;
		for (int i = 0; i < data.length; i++) {
			if (item.endsWith(data[i]) || item.contains(data[i])) {
				item1 = true;
			}
		}
		return item1;
	}

	private static String[] fullbody = { "top", "chestplate", "shirt", "Runecrafter robe",
			"platebody", "Ahrims robetop", "Karils leathertop", "brassard",
			"Robe top", "robetop", "platebody (t)", "platebody (g)",
			"chestplate", "torso", "hauberk", "Dragon chainbody", "plate",
			"Rock-shell plate", "Morrigan's leather body", "Wizard robe (g)","Wizard robe (t)",
			"Snakeskin body", "Monk's robe", "Vesta's chainbody", "Battle-mage robe", "Trickster robe",
			"Vanguard body", "Moonclan armour", "Fishing jacket", "Runecrafter robe",
			
		};

	private static String[] fullhat = { "Wolf mask", "Bat mask",
			"Penguin mask", "Cat mask", "med helm", "coif", "Dharok's helm",
			"hood", "Initiate helm", "Coif", "Helm of neitiznot",
			"Armadyl helmet", "Berserker helm", "Archer helm", "Farseer helm",
			"Warrior helm", "helm (e)", "helm (charged)", "helmet (charged)", "helmet (e)", "helm(e)", "Void",
			"Helm of neitiznot (e)", "Helm of neitiznot (charged)", "Fishing hat"
		};

	private static String[] fullmask = { "Wolf mask", "Bat mask",
			"Penguin mask", "Cat mask", "Full slayer helmet", "Slayer helmet",
			"Full slayer helmet", "full helm", "mask", "Verac's helm",
			"Guthan's helm", "Karil's coif", "mask", "Torag's helm", "sallet",
			"Saradomin full", "Rune full helm(t)", "Rune full helm(g)", "Black full helm(t)", "Black full helm(g)",
			"Adam full helm(g)","Adam full helm(t)", "helm", "Slayer helmet (e)", "Slayer helmet (charged)", "Full slayer helmet (e)",
			"Full slayer helmet (charged)", "Void"
		};

	public static boolean isFullBody(int itemId) {
		String weapon = getItemName(itemId);
		if (weapon == null)
			return false;
		for (int i = 0; i < fullbody.length; i++) {
			if (weapon.endsWith(fullbody[i]) || weapon.contains(fullbody[i])) {
				return true;
			}
		}
		return false;
	}

	public static boolean isFullHelm(int itemId) {
		String weapon = getItemName(itemId);
		if (weapon == null)
			return false;
		for (int i = 0; i < fullhat.length; i++) {
			if (weapon.endsWith(fullhat[i]) && itemId != 2631) {
				return true;
			}
		}
		return false;
	}

	public static boolean isFullMask(int itemId) {
		String weapon = getItemName(itemId);
		if (weapon == null)
			return false;
		for (int i = 0; i < fullmask.length; i++) {
			if (weapon.endsWith(fullmask[i]) && itemId != 2631) {
				return true;
			}
		}
		return false;
	}
	

	private static String[] scroll = {"scroll", "Scroll"};

	public static boolean isScroll(int itemId) {
		String summonScroll = getItemName(itemId);
		if (summonScroll == null)
			return false;
		for (int i = 0; i < scroll.length; i++) {
			if (summonScroll.endsWith(scroll[i]) && itemId != 2720 && itemId != 2804 && itemId != 2789) {
				return true;
			}
		}
		return false;
	}

	public static String getItemName(int id) {
		for (int j = 0; j < Mistex.itemHandler.ItemList.length; j++) {
			if (Mistex.itemHandler.ItemList[j] != null)
				if (Mistex.itemHandler.ItemList[j].itemId == id)
					return Mistex.itemHandler.ItemList[j].itemName;
		}
		return null;
	}

	public static boolean[] itemStackable = new boolean[MistexConfiguration.ITEM_LIMIT];
	public static boolean[] itemIsNote = new boolean[MistexConfiguration.ITEM_LIMIT];
	public static int[] targetSlots = new int[MistexConfiguration.ITEM_LIMIT];

	public static void load() {
		int counter = 0;
		int c;
		try {
			FileInputStream dataIn = new FileInputStream(new File("./Data/data/stackable.dat"));
			while ((c = dataIn.read()) != -1) {
				if (c == 0) {
					itemStackable[15243] = true;
					itemStackable[15263] = true;
					itemStackable[12437] = true;
					itemStackable[12434] = true;
					itemStackable[12825] = true;
					itemStackable[12435] = true;
					itemStackable[12437] = true;
					itemStackable[7478] = true;
					itemIsNote[15273] = true;
					itemIsNote[18831] = true;
					itemStackable[15273] = true;
					itemStackable[counter] = false;
				} else {
					itemStackable[counter] = true;
				}
				counter++;
			}
			dataIn.close();
			logger.info("Items have been loaded.");
		} catch (IOException e) {
			System.out
					.println("Critical error while loading stackabledata! Trace:");
			e.printStackTrace();
		}

		counter = 0;

		try {
			FileInputStream dataIn = new FileInputStream(new File("./Data/data/notes.dat"));
			while ((c = dataIn.read()) != -1) {
				if (c == 0) {
					itemIsNote[counter] = true;
				} else {
					itemIsNote[counter] = false;
				}
				counter++;
			}
			int[] stackableItems = {
					18016, 18831, 13879, 13880, 13881, 13882,15243, 19152, 19157, 19162, 19467, 
					18201, 4561, 13280, 13883, 12158, 12159, 12160, 12161, 12162, 12163, 12155,
			}; 
		for (int i = 0; i < stackableItems.length; i++) {
			for (int j = 0; j < scroll.length; j++) {
				itemStackable[stackableItems[i]] = true;
				itemStackable[stackableItems[j]] = true;
			}
		}
			dataIn.close();
		} catch (IOException e) {
			System.out.println("Critical error while loading notedata! Trace:");
			e.printStackTrace();
		}

		counter = 0;
		try {
			FileInputStream dataIn = new FileInputStream(new File("./Data/data/equipment.dat"));
			while ((c = dataIn.read()) != -1) {
				targetSlots[counter++] = c;
			}
			dataIn.close();
		} catch (IOException e) {
			System.out.println("Critical error while loading notedata! Trace:");
			e.printStackTrace();
		}
	}
}