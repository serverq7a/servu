package org.mistex.game.world.content.skill.magic;

import org.mistex.game.world.content.skill.Skill.Skills;
import org.mistex.game.world.event.CycleEvent;
import org.mistex.game.world.event.CycleEventContainer;
import org.mistex.game.world.event.CycleEventHandler;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.Player;
import org.mistex.game.world.player.PlayerConfiguration;

public class SuperHeat {

	private Client c;
	
	public SuperHeat(Client c) {
			this.c = c;
		}
	
	/**
	 * Experience variable
	 */
	private int exp;
	
	/**
	 * Ore id variable
	 */
	private int oreId;
	
	/**
	 * Ore id 2 variable
	 */
	private int oreId2;
	
	/**
	 * Bar id variable
	 */
	private int barId;
	
	/**
	 * Smelting experience
	 */
	private final int[] SMELT_EXP = { 6, 13, 15, 18, 23, 30, 38, 50 };
	
	/**
	 * Smithing bars
	 */
	private final int[] SMELT_BARS = { 2349, 2351, 2355, 2353, 2357, 2359, 2361, 2363 };
	
	/**
	 * Ore Ids
	 */
	private final int[] ORE_1 = { 438, 440, 442, 440, 444, 447, 449, 451 };
	
	/**
	 * Ores 2
	 */
	private final int[][] ORE_2 = { { 436, 1 }, { -1, 0 }, { -1, 0 }, { 453, 2 }, { -1, 0 }, { 453, 4 }, { 453, 6 }, { 453, 8 }, };
	
	/**
	 * Bar requirements
	 */
	private final int[] BAR_REQS = { 1, 15, 20, 30, 40, 50, 70, 85 };
	
	/**
	 * Checks if player can heat bars
	 * @param barType
	 * @return
	 */
	public boolean canHeat(int barType) {
		for (int j = 0; j < SMELT_BARS.length; j++) {
			if (barType == SMELT_BARS[j]) {
				return c.playerLevel[Player.playerSmithing] >= BAR_REQS[j];
			}
		}
		return false;
	}
	
	/**
	 * Gets the player experience
	 * @param barType
	 * @return
	 */
	public int getExp(int barType) {
		for (int j = 0; j < SMELT_BARS.length; j++) {
			if (barType == SMELT_BARS[j]) {
				return SMELT_EXP[j];
			}
		}
		return 0;
	}
	
	/**
	 * Gets player ore 2 amount
	 * @param barType
	 * @return
	 */
	public int getOre2Amount(int barType) {
		for (int j = 0; j < SMELT_BARS.length; j++) {
			if (barType == SMELT_BARS[j]) {
				return ORE_2[j][1];
			}
		}
		return 0;
	}

	/**
	 * Gets player's ore
	 * @param barType
	 * @return
	 */
	public int getOre(int barType) {
		for (int j = 0; j < SMELT_BARS.length; j++) {
			if (barType == SMELT_BARS[j]) {
				return ORE_1[j];
			}
		}
		return 0;
	}

	/**
	 * Gets player's ore2
	 * @param barType
	 * @return
	 */
	public int getOre2(int barType) {
		for (int j = 0; j < SMELT_BARS.length; j++) {
			if (barType == SMELT_BARS[j]) {
				return ORE_2[j][0];
			}
		}
		return 0;
	}

	/**
	 * Reset smithing
	 */
	public void resetSmithingStuff() {
		c.getPA().resetVariables();
		c.getPA().closeAllWindows();
		c.isSmelting = false;
	}
	
	/**
	 * Check if player has ores
	 * @param barType
	 * @return
	 */
	public boolean hasOres(int barType) {
		if (getOre2(barType) > 0)
			return c.getItems().playerHasItem(getOre(barType)) && c.getItems().playerHasItem(getOre2(barType));
		else
			return c.getItems().playerHasItem(getOre(barType));
	}

	/**
	 * Checks how much ore player has
	 * @param barType
	 * @return
	 */
	public boolean hasOresAmount(int barType) {
		if (getOre2Amount(barType) > 0)
			return c.getItems().playerHasItem(getOre(barType)) && c.getItems().playerHasItem(getOre2Amount(barType));
		else
			return c.getItems().playerHasItem(getOre(barType));
	}
	
	/**
	 * Heats the ore
	 * @param barType
	 */
	public void heat(int barType) {
		if (c.smeltAmount > 0) {
			if (hasOres(barType) && c.getItems().playerHasItem(getOre2(barType), getOre2Amount(barType))) {
				c.getItems().deleteItem(oreId, c.getItems().getItemSlot(oreId), 1);
				if (oreId2 > 0)
					c.getItems().deleteItem(oreId2, getOre2Amount(barType));
					c.getItems().deleteItem(561, 1);
					c.getItems().deleteItem(554, 4);
				c.getItems().addItem(barId, 1);
				c.getPA().addSkillXP(exp * Skills.SMITHING.getMultiplier(), Player.playerSmithing);
				c.getPA().addSkillXP(exp * PlayerConfiguration.MAGIC_EXP_RATE, Player.playerMagic);
				c.getPA().refreshSkill(Player.playerSmithing);
				c.getPA().refreshSkill(Player.playerMagic);
				c.smeltAmount--;
				c.smeltTimer = 1;
			} else {
				c.sendMessage("You do not have the required ores to heat this.");
				resetSmithingStuff();
			}
		} else {
			c.getPA().resetVariables();
		}
	}
	
	/**
	 * Starts heating
	 * @param barType
	 * @param oreName
	 */
	public void startHeating(final int barType, final String oreName) {
		if (canHeat(barType)) {
			if (hasOres(barType) && c.getItems().playerHasItem(getOre2(barType), getOre2Amount(barType))) {
				this.exp = getExp(barType);
				this.oreId = getOre(barType);
				this.oreId2 = getOre2(barType);
				this.barId = barType;
				c.getPA().closeAllWindows();
				c.startAnimation(722);
				c.gfx0(148);
				CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						container.stop();
					}
					@Override
					public void stop() {
						c.smeltAmount = c.getItems().getItemAmount(getOre(barType));
						c.sendMessage("You super heat the " + oreName +".");
						heat(barType);
					}
				}, 2);
			} else {
				c.sendMessage("You do have the required ores to superheat.");
			}
		} else {
			c.sendMessage("You must have a higher smithing level to superheat this.");
			resetSmithingStuff();
		}
	}
}