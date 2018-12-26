package org.mistex.game.world.content.skill.thieving;

import org.mistex.game.MistexUtility;
import org.mistex.game.world.World;
import org.mistex.game.world.content.skill.Skill.Skills;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.Player;

public class WallSafes {

	static Client c;
	
	/**
	 * Checks if wallsafes is enabled.
	 */
	public final static boolean WALL_SAFES_ENABLED = true;
	
	/**
	 * Quantity 
	 */
	private static final int QUANTITY = 1;

	/**
	 * Thieving XP.
	 */
	private static final int XP = 500;

	/**
	 * Level Required
	 */
	public static final int LEVEL_REQUIRED = 40;

	/**
	 * Animation ID (Animation)
	 */
	private static final int UNLOCKING_ANIM = 2247;

	/**
	 * Wall Safe ID (Object)
	 */
	public static final int WALL_SAFE = 7236;

	/**
	 * Money ID and Onyx ID.
	 */
	private static final int COINS = 995, ONYX = 6571;

	/**
	 * Gems ID
	 */
	public static int[] randomGems ={995, 1617, 1619, 1621, 1623, 1625, 1627, 1629 };
	
	/**
	 * You crack the safe.
	 */
	private static final String SUCCESSFUL = "You crack the safe.";

	/**
	 * You fail to crack the safe.
	 */
	private static final String UNSUCCESSFUL = "You fail to crack the safe.";

	/**
	 * You need a thieving level of at least "+LEVEL_REQUIRED+" to crack the wall safe.
	 */
	public static final String AT_LEAST = "You need a thieving level of at least "+LEVEL_REQUIRED+" to crack the wall safe.";

	/**
	 * Checks if wallsafes are enabled.
	 */
	public static void checkEnabled() {
		if (!WALL_SAFES_ENABLED) {
			c.sendMessage("Wall safes are currently disabled.");
			return;
		}
	}
	
	/**
	 * Gets uncut onyx.
	 */
	public static void getOnyx() {
		int onyx = MistexUtility.random(0100);
		if (onyx < 909) {
			c.sendMessage("You manage to get a uncut onyx!");
			c.getItems().addItem(ONYX, QUANTITY);
		} else if (onyx > 908) {
			c.sendMessage("You see a uncut onyx, but you fail to grab it.");
		}
	}
	
	/**
	 * Generates random rewards via Misc.random
	 * 
	 * @param c
	 *  The player or client
	 */
    	public static void getRandomReward(Client c) { 
    		int random = MistexUtility.random(19);
    		if(random < 13) {
    			c.wallSafes += 1;
    			c.startAnimation(UNLOCKING_ANIM);
    			c.getPA().addSkillXP(XP * Skills.THIEVING.getMultiplier(), Player.playerThieving);
    		}
    		switch (random) {                    
        		case 0:
        		case 1:
        		case 2:
        		case 3:
        			int baseAmt = ((random % 10) > 1) ? 2500 : 1000;
        			int addAmt = ((random % 10) > 1) ? ((random % 10) * 2000) : (random * 1500);
        			c.getItems().addItem(COINS, baseAmt  + addAmt + MistexUtility.random(2500));
        			//getOnyx();
        		break;

        		case 4:
        		case 5:
        		case 6:
        		case 7:
        		case 8:
        		case 9:
        		case 10:
        		case 11:
        			c.getItems().addItem(randomGems[MistexUtility.random(randomGems.length-1)], QUANTITY);
        		break;
    		}
    		if(random >= 12)
    			appendHit(MistexUtility.random(random - 11), c);
    			c.sendMessage(random < 12 ? SUCCESSFUL : UNSUCCESSFUL);
   	 }
	
	/**
	 * Cause a hit.
	 * 
	 * @param damage
	 *  The damage
	 *
	 * @param c
	 *  The player or client
	 */
	public static void appendHit(int damage, Client c) {
		World.players[c.playerId].setHitDiff(damage);
		World.players[c.playerId].playerLevel[3] -= damage;
		c.getPA().refreshSkill(3);
		World.players[c.playerId].setHitUpdateRequired(true);	
		World.players[c.playerId].updateRequired = true;		
	}
	
	/**
	 * Checks the wall safe.
	 * 
	 * @param c
	 *  The player or client
	 *
	 * @param objectType
	 *  Object ID
	 */
	public static void checkWallSafe(Client c, int objectType) {
		switch (objectType) {
			case WALL_SAFE:
				checkEnabled();
				if (c.playerLevel[17] >= LEVEL_REQUIRED) {
					if (System.currentTimeMillis() - c.lastThieve < 2500)
						return;
					c.lastThieve = System.currentTimeMillis();
					c.turnPlayerTo(c.objectX, c.objectY);
					getRandomReward(c);
					antiAutoClick(c);
				} else {
					c.sendMessage(AT_LEAST);
				}
				break;
		}
	}
	
	public static void antiAutoClick(Client c) {	
		int random = MistexUtility.random(50);
			if (random == 1) {
					c.getPA().movePlayer(3047, 4974, 0);
				}
				c.sendMessage("You have randomly teleported!");
				c.handleHitMask(5);
				c.dealDamage(5);
				c.getPA().sendFrame107();
				c.startAnimation(4367);
			}
	
	
}

