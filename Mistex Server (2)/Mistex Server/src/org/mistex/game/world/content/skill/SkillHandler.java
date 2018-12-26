package org.mistex.game.world.content.skill;

import java.util.Random;

import org.mistex.game.MistexUtility;
import org.mistex.game.world.content.interfaces.InterfaceText;
import org.mistex.game.world.content.interfaces.impl.InformationTab;
import org.mistex.game.world.player.Client;

public class SkillHandler {

	public static final boolean view190 = true;

	public static void resetPlayerSkillVariables(Client c) {
		if (c.playerIsMining) {
			for (int i = 0; i < 2; i++) {
				c.miningProp[i] = -1;
			}
		} else if (c.playerIsCooking) {
			for (int i2 = 0; i2 < 6; i2++) {
				c.cookingProp[i2] = -1;

			}
		}
	}

	public static boolean[] isSkilling = new boolean[25];

	public static long lastSkillingAction;


	public static void resetPlayerVariables(Client c) {
		c.isSkilling = false;
		c.getPA().removeAllWindows();
	}
	
	public static void giveBad(Client c, String i) {
		if (c.skillingPoints == 0) {
			return;
		}
			c.skillingPoints -= 1;
			c.sendMessage("<col=309E08>The gods do not like how you skill. They removed 1 skilling point.");
			InterfaceText.writeText(new InformationTab(c));
		
	}
	
	public static void giveSkillingPoints(Client c, String i) {
		int randomPoints = MistexUtility.random(15); 
		if (randomPoints == 0) {
			c.skillingPoints += 1;
			c.sendMessage("<col=309E08>While training "+i+", you were rewarded with 1 skilling point!");
		} else {
			c.skillingPoints += randomPoints;
			c.sendMessage("<col=309E08>While training "+i+", you were rewarded with "+randomPoints+" skilling points!");
		}
		InterfaceText.writeText(new InformationTab(c));
	}
	
	public static void resetSkillingVariables(Client c) {
		for (int skill = 0; skill < isSkilling.length; skill++) {
			isSkilling[skill] = false;
		}
	}

	public static String getLine(Client c) {
		return c.below459 ? ("\\n\\n\\n\\n") : ("\\n\\n\\n\\n\\n");
	}

	public static boolean noInventorySpace(Client c, String skill) {
		if (c.getItems().freeSlots() == 0) {
			c.sendMessage("You haven't got enough inventory space to continue "+ skill + "!");
			c.getPA().sendStatement("You haven't got enough inventory space to continue "+ skill + "!");
			return false;
		}
		return true;
	}

	public static boolean hasRequiredLevel(final Client c, int id, int lvlReq,
			String skill, String event) {
		if (c.playerLevel[id] < lvlReq) {
			c.sendMessage("You dont't have a high enough " + skill+ " level to " + event + "");
			c.sendMessage("You at least need the " + skill + " level of "+ lvlReq + ".");
			c.getPA().sendStatement("You haven't got high enough " + skill + " level to "+ event + "!");
			return false;
		}
		return true;
	}

	public static void deleteTime(Client c) {
		c.doAmount--;
	}

	public static boolean skillCheck(int level, int levelRequired, int itemBonus) {
		double chance = 0.0;
		double baseChance = levelRequired < 11 ? 15 : levelRequired < 51 ? 10 : 5;
		chance = baseChance + ((level - levelRequired) / 2d) + (itemBonus / 10d);
		return chance >= (new Random().nextDouble() * 100.0);
	}
}