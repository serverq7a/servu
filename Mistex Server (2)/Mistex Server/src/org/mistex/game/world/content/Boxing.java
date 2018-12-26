package org.mistex.game.world.content;

import org.mistex.game.MistexUtility;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.item.ItemAssistant;

public class Boxing {
	
	/**
	 * ID for blue boxing gloves
	 */
	public static final int BLUE_GLOVES = 7673;
	
	/**
	 * ID for red boxing gloves
	 */
	public static final int RED_GLOVES = 7671;
	
	/**
	 * Checks if player is in arena
	 * @param c
	 * @return
	 */
    public static final boolean inArena(Client c) {
        return c.absX >= 2857 && c.absX <= 2864 && c.absY >= 5094 && c.absY <= 5101;
    }
	
    /**
     * Checks if player can enter arena
     * @param c
     * @return
     */
	public static boolean canEnter(Client c) {
		if (!c.inDonatorZone() && !c.getRights().isDonator()) {
			c.sendMessage("You must be a donator to do this!");
			return false;
		}
		if (c.getItems().freeSlots() < 27) {
			c.sendMessage("Please empty your inventory before entering the boxing arena.");
			return false;
		}
		for (int i = 0; i < c.playerEquipment.length; i++) {
			if (c.playerEquipment[i] != -1) {
				c.sendMessage("Please remove all equipment before entering the boxing arena.");
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Gives a random pair of gloves
	 * @return
	 */
	public static int getRandomGloves() {
		int random = MistexUtility.random(0, 1);
		if (random == 1) {
			return RED_GLOVES;
		} else {
			return BLUE_GLOVES;
		}
	}
	
	/**
	 * Handles the barrier moving
	 * @param c
	 */
	public static void barrierMoving(Client c) {
		if (inArena(c)) {
			leaveArena(c);
		} else {
			enterArena(c);
		}
	}
	
	/**
	 * Enters the boxing arena
	 * @param c
	 */
	public static void enterArena(Client c) {
		if (canEnter(c)) {
			c.getItems().deleteAllItems();
			c.getPA().resetFollowers();
			c.inBoxingArena = true;
			c.getCombat().resetPrayers();
			c.getItems().setEquipment(getRandomGloves(), 1, c.playerWeapon);
			c.getItems();
			c.getCombat().getPlayerAnimIndex(ItemAssistant.getItemName(c.playerEquipment[c.playerWeapon]));
			c.isRunning2 = false;
			c.getPA().sendFrame36(173, 0);
			c.sendMessage("Goodluck!");
			c.teleBlockLength = 9999;
			c.teleBlockDelay = 9999;
			c.getPA().movePlayer(2860, 5098, 0);
		}
	}
	
	/**
	 * Leaves the boxing arena
	 * @param c
	 */
	public static void leaveArena(Client c) {
		if (c.underAttackBy > 0) {
			c.sendMessage("You can not leave while under attack.");
			return;
		}
		reset(c);
		c.getItems().deleteEquipment(1, c.playerWeapon);
		c.playerStandIndex = 2061;
		c.playerWalkIndex = 2064;
		c.playerRunIndex = 2077;
		c.teleBlockLength = 9999;
		c.teleBlockDelay = 9999;
		c.getPA().movePlayer(2854, 5100, 0);
	}
	
	/**
	 * Handles the player's death
	 * @param c
	 */
	public static void handleDeath(Client c) {
		c.getPA().movePlayer(2854, 5100, 0);
		c.sendMessage("Lights out buddy.");
		reset(c);
		c.getItems().deleteEquipment(1, c.playerWeapon);
		c.playerStandIndex = 2061;
		c.playerWalkIndex = 2064;
		c.playerRunIndex = 2077;
		c.teleBlockLength = 9999;
		c.teleBlockDelay = 9999;
	}
	
	/**
	 * Resets the player
	 * @param c
	 */
	public static void reset(Client c) {
		c.getPA().resetFollowers();
		c.getCombat().resetPlayerAttack();
		c.getCombat().resetPrayers();
		c.inBoxingArena = false;
		c.isRunning2 = true;
	}
	

}
