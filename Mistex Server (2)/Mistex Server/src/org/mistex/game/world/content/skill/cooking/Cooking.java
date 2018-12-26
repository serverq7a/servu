package org.mistex.game.world.content.skill.cooking;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.mistex.game.world.clip.region.ObjectDef;
import org.mistex.game.world.clip.region.Region;
import org.mistex.game.world.content.achievement.AchievementHandler;
import org.mistex.game.world.content.achievement.AchievementList;
import org.mistex.game.world.content.interfaces.InterfaceText;
import org.mistex.game.world.content.interfaces.impl.AchievementTab;
import org.mistex.game.world.content.skill.SkillHandler;
import org.mistex.game.world.event.CycleEvent;
import org.mistex.game.world.event.CycleEventContainer;
import org.mistex.game.world.event.CycleEventHandler;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.item.ItemAssistant;


public class Cooking {

	public static enum CookingItems {
		BEEF_MEAT(2132, 2142, 2146, 1, 300.0, 7, 7, true),
		RAT_MEAT(2134, 2142, 2146, 1, 300.0, 7, 7, true),
		BEAR_MEAT(2136, 2142, 2146, 1, 300.0, 7, 7, true),
		CHICKEN(2138, 2140, 2144, 1, 300.0, 7, 7, true),
		RABBIT(3226, 3228, 7222, 1, 300.0, 7, 7, true),
		UHTHANKI(1859, 1861, 323, 1, 400.0, 7, 7, true),
		THIN_SNAIL(3363, 3369, 3375, 12, 500, 17, 17, true),
		LEAN_SNAIL(3365, 3371, 3375, 17, 80, 26, 26, true),
		SPIDER_STICK(6293, 6297, 6301, 16, 80, 25, 25, true),
		SPIDER_SHAFT(6295, 6299, 6303, 16, 80, 25, 25, true),
		ROASTED_RABBIT(3226, 7223, 7222, 16, 500, 25, 25, true),
		FAT_SNAIL(3367, 3373, 3375, 22, 95, 28, 28, true),
		CHOMPY(2876, 2878, 2880, 30, 140, 38, 38, true),
		RED_BERRY_PIE(2321, 2325, 2329, 10, 78, 15, 15, false),
		MEAT_PIE(2317, 2327, 2329, 20, 110, 25, 25, false),
		MUD_PIE(2319, 7170, 2329, 29, 128, 35, 35, false),
		APPLE_PIE(7168, 2323, 2329, 30, 130, 35, 35, false),
		GARDEN_PIE(7176, 7178, 2329, 34, 138, 39, 39, false),
		FISH_PIE(7186, 7188, 2329, 47, 164, 52, 52, false),
		ADMIRAL_PIE(7196, 7198, 2329, 70, 210, 77, 77, false),
		WILD_PIE(7206, 7208, 2329, 85, 240, 90, 90, false),
		SUMMER_PIE(7216, 7218, 2329, 95, 260, 100, 100, false),
		STEW(2001, 2003, 2005, 25, 117, 30, 30, true),
		CURRY(2009, 2011, 2013, 60, 280, 65, 65, true),
		PIZZA(2287, 2289, 2305, 35, 143, 38, 38, true),
		CAKE(1889, 1891, 1903, 40, 180, 38, 38, false),
		BREAD(2307, 2309, 2311, 1, 40, 5, 5, false),
		PITTA_BREAD(1863, 1865, 1867, 58, 40, 65, 65, true),
		SPICY_SAUCE(7072, 7072, 2880, 9, 25, 38, 38, true),
		SCRAMBLED_EGG(7076, 7078, 7090, 13, 50, 16, 16, true),
		FRIED_ONION(1871, 7084, 7092, 42, 60, 45, 45, true),
		FRIED_MUSHROOM(7080, 7082, 7094, 46, 60, 52, 52, true),
		POTATO(1942, 6701, 6699, 9, 25, 38, 38, false),
		NETTLE(4237, 4239, 6699, 9, 25, 38, 38, true),
		BARLEY(6006, 6008, 6008, 1, 0, 1, 1, true),
		SHRIMP(317, 315, 323, 1, 30, 34, 34, true),
		KARAMBWANJI(3150, 3151, 3148, 1, 10, 34, 34, true),
		SARDINE(327, 325, 369, 1, 3000, 38, 38, true),
		ANCHOVIES(321, 319, 323, 1, 3000, 34, 34, true),
		HERRING(345, 347, 357, 5, 4000, 37, 37, true),
		MACKEREL(353, 355, 357, 10, 4500, 35, 35, true),
		TROUT(335, 333, 343, 15, 4000, 50, 50, true),
		COD(341, 339, 343, 17, 5150, 39, 39, true),
		PIKE(349, 351, 343, 20, 2000, 52, 52, true),
		SALMON(331, 329, 343, 25, 5000, 58, 58, true),
		SLIMY_EEL(3379, 3381, 3383, 28, 95, 58, 58, true),
		TUNA(359, 361, 367, 30, 5500, 65, 65, true),
		KARAMBWAN(3142, 3144, 3148, 30, 190, 100, 100, true),
		CAVE_EEL(5001, 5003, 5002, 38, 115, 40, 40, true),
		LOBSTER(377, 379, 381, 40, 6500, 74, 66, true),
		BASS(363, 365, 367, 43, 6750, 80, 80, true),
		SWORDFISH(371, 373, 375, 45, 7000, 86, 81, true),
		LAVA_EEL(2148, 2149, 3383, 53, 60, 72, 72, true),
		SHARK(383, 385, 387, 80, 8500, 104, 94, true),
		SEA_TURTLE(395, 397, 399, 82, 9000, 110, 110, true),
		MANTA_RAY(389, 391, 393, 91, 10000, 112, 112, true), 
		MONKFISH(7944, 7946, 7948, 62, 7000, 90, 87, true),
		ROCKTAIL(15270, 15272, 15274, 93, 12000, 115, 115, true);

		private int rawId;
		private int cookedId;
		private int burntId;
		private int cookLevel;
		private double experience;
		private int burnStopLevel;
		private int burnStopLevelWith;
		private boolean fireCook;

		private static Map<Integer, CookingItems> cookingItems = new HashMap<Integer, CookingItems>();

		public static CookingItems forId(int id) {
			return cookingItems.get(id);
		}

		static {
			for (CookingItems item : CookingItems.values()) {
				cookingItems.put(item.rawId, item);
			}
		}

		private CookingItems(int rawId, int cookedId, int burntId, int cookLevel, double experience, int burnStopLevel, int burnStopLevelWith, boolean fireCook) {
			this.rawId = rawId;
			this.cookedId = cookedId;
			this.burntId = burntId;
			this.cookLevel = cookLevel;
			this.experience = experience;
			this.burnStopLevel = burnStopLevel;
			this.burnStopLevelWith = burnStopLevelWith;
			this.fireCook = fireCook;
		}

		public int getRawId() {
			return rawId;
		}

		public int getCookedId() {
			return cookedId;
		}

		public int getBurntID() {
			return burntId;
		}

		public int getCookLevel() {
			return cookLevel;
		}

		public double getExperience() {
			return experience;
		}

		public int getBurnStopLevel() {
			return burnStopLevel;
		}

		public int getBurnStopLevelWith() {
			return burnStopLevelWith;
		}

		public boolean fireCook() {
			return fireCook;
		}
	}

	public static boolean handleInterface(Client player, int item, int objectId, int objectX, int objectY) {
		CookingItems cook = CookingItems.forId(item);
		if (cook == null)
			return false;
		player.getPA().closeAllWindows();
		ObjectDef obj = ObjectDef.getObjectDef(objectId);
		player.fireId = objectId;
		player.fireX = objectX;
		player.fireY = objectY;
		player.fireHeight = 0;
		if (obj != null) {
			String name = obj.name;
			if (name.equalsIgnoreCase("fire") || name.equalsIgnoreCase("fireplace")) {
				player.setStatedInterface("cookFire");
                player.setTempInteger(item);
				
				player.getPA().sendFrame246(13716, 200, item);
				player.getPA().sendFrame126(ItemAssistant.getItemName(item), 13717);
				player.getPA().sendFrame164(1743);
				return true;
			}
			if (name.equalsIgnoreCase("stove") || name.equalsIgnoreCase("range") || name.equalsIgnoreCase("cooking range") || name.equalsIgnoreCase("cooking pot")) {
				player.setStatedInterface("cookRange");
				player.setTempInteger(item);		
				player.getPA().sendFrame246(13716, 200, item);
				player.getPA().sendFrame126("" + ItemAssistant.getItemName(item) + "", 13717);
				player.getPA().sendFrame164(1743);
				return true;
			}
		}
		return true;
	}

	public static void handleCookingTick(final Client player, final int amount) {
		player.isSkilling = true;
		CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
			int cookAmount = amount;
			@Override
			public void execute(CycleEventContainer container) {
				if(player.getStatedInterface().equalsIgnoreCase("cookFire") && !Region.objectExists(player.fireId, player.fireX, player.fireY, player.fireHeight)) {
					player.getPA().closeAllWindows();
					player.setTempInteger(0);
					container.stop();
					return;
				}
				if (!player.isSkilling || !player.getItems().playerHasItem(player.getTempInteger()) || cookAmount == 0) {
					player.setTempInteger(0);
					container.stop();
					return;
				}
				handleCooking(player);
				cookAmount--;
				container.setTick(4);
			}
			@Override
			public void stop() {
				player.getPA().resetAnimation();
			}
		}, 1);
	}
	
	@SuppressWarnings("static-access")
	public static void handleCooking(final Client player) {
		final CookingItems toCook = CookingItems.forId(player.getTempInteger());
		if (toCook == null)
			return; // Means the item exists in cooking enum
		if (player.playerLevel[player.playerCooking] < toCook.getCookLevel()) {
			SkillHandler.hasRequiredLevel(player, player.playerCooking, toCook.getCookLevel(), "cooking", "cook");
			return;
		}
		player.getPA().removeAllWindows();
		player.getItems().deleteItem(player.getTempInteger(), 1);
		if (player.getStatedInterface() == "cookFire") {
			player.startAnimation(897);
		} else if (player.getStatedInterface() == "cookRange") {
			player.startAnimation(896);
		} else {
			player.startAnimation(896);
		}
		if (!toCook.fireCook() && player.getStatedInterface() == "cookFire")
			giveRewards(player, player.getTempInteger(), true);
		else
			giveRewards(player, player.getTempInteger(), false);

		/* some food always burns on fire */

	}

	public static void handleButtons(Client player, int buttonId) {
		switch (buttonId) {
		case 53152:// cook 1
			handleCookingTick(player, 1);
			break;
		case 53151:// cook 5
			handleCookingTick(player, 5);
			break;
		case 53149:// cook all
			handleCookingTick(player, 28);
			break;
		}
	}
	
	@SuppressWarnings("static-access")
	public static void giveRewards(Client player, int item, boolean forcedBurn) {
		CookingItems food = CookingItems.forId(item);
		final double burnBonus = 3.0;
		double burn_chance = 55.0 - burnBonus;
		final double cook_level = player.playerLevel[player.playerCooking];
		final double lev_needed = food.getCookLevel();
		final double burn_stop = food.getBurnStopLevel();
		final double multi_a = burn_stop - lev_needed;
		final double burn_dec = burn_chance / multi_a;
		final double multi_b = cook_level - lev_needed;
		burn_chance -= multi_b * burn_dec;
		double randNum = new Random().nextDouble() * 100.0;
		if ((burn_chance >= randNum && !forcedBurn)) {
			player.getItems().addItem(food.getCookedId(), 1);
			player.getPA().addSkillXP((int) food.getExperience(), player.playerCooking);
			player.sendMessage("You successfully cook a " + ItemAssistant.getItemName(food.getCookedId()).toLowerCase() + ".");
			player.foodCooked += 1;
			if (player.foodCooked == 1) {
				player.sendMessage("@war@You have started the achievement: Iron Chef ");
				InterfaceText.writeText(new AchievementTab(player));
			}
			if (player.foodCooked == 10000)
				AchievementHandler.activateAchievement(player, AchievementList.IRON_CHEF);
		} else {
			player.getItems().addItem(food.getBurntID(), 1);
			player.sendMessage("You accidentally burn the " + ItemAssistant.getItemName(food.getCookedId()).toLowerCase() + ".");
		}
	}
}
