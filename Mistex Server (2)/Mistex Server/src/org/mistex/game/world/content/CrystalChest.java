package org.mistex.game.world.content;

import org.mistex.game.MistexUtility;
import org.mistex.game.world.event.CycleEvent;
import org.mistex.game.world.event.CycleEventContainer;
import org.mistex.game.world.event.CycleEventHandler;
import org.mistex.game.world.player.Client;

public class CrystalChest {
	
	/**
	 * All the items that the chest will reward
	 */
	private static final int[] CHEST_REWARDS = { 
		1079, 1093, 1333, 1319, 11732, 391, 1725, 451, 1969, 1725, 1373,
		4131, 1127, 1201, 1359, 2651, 1185, 1163, 1113, 1147, 1065, 1099,
		2493, 2487, 2499, 1712, 4587, 1215, 4153, 10828, 2491, 1731, 2497, 2503,
		4093, 4091, 7158, 1289, 1261, 1347, 1432, 2615, 2617, 2619, 2621, 
		2623, 2625, 2627, 2629, 3476, 3477, 3840, 6587, 6589, 6591, 6605,
		6611, 6613, 6619, 6621, 6623, 6625, 6627, 6629, 6631, 6633, 6739,
		3122, 6809, 10564, 
		
	};
	
	/**
	 * Both key halves ID
	 */
	public static final int[] KEY_HALVES = { 985, 987 };
	
	/**
	 * The key ID
	 */
	public static final int KEY = 989;
	
	/**
	 * The dragonstone ID
	 */
	private static final int DRAGONSTONE = 1631;
	
	/**
	 * The chest opening animation
	 */
	private static final int OPEN_ANIMATION = 881;

	/**
	 * The key making
	 * @param c
	 */
	public static void makeKey(Client c) {
		if (c.getItems().playerHasItem(toothHalf(), 1)&& c.getItems().playerHasItem(loopHalf(), 1)) {
			c.getItems().deleteItem(toothHalf(), 1);
			c.getItems().deleteItem(loopHalf(), 1);
			c.getItems().addItem(KEY, 1);
			c.getPA().sendStatement("You successfully put the two halves together.");
		}
	}
	
	/**
	 * Checks if player can open the chest
	 * @param c
	 * @return
	 */
	public static boolean canOpen(Client c) {
		if (c.getItems().playerHasItem(KEY)) {
			return true;
		} else {
			c.getPA().sendStatement("The chest is locked");
			return false;
		}
	}

	/**
	 * Opens the chest
	 * @param c
	 * @param id
	 * @param x
	 * @param y
	 */
	public static void searchChest(final Client c, final int id, final int x,final int y) {
		if (canOpen(c)) {
			c.getPA().sendStatement("You unlock the chest with your key.");
			c.getItems().deleteItem(KEY, 1);
			c.startAnimation(OPEN_ANIMATION);
			c.getPA().checkObjectSpawn(id + 1, x, y, 0, 10);
			CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
	            public void execute(CycleEventContainer p) {
					c.getItems().addItem(DRAGONSTONE, 1);
					c.getItems().addItem(995, MistexUtility.random(8230));
					c.getItems().addItem(CHEST_REWARDS[MistexUtility.random(getLength() - 1)], 1);
					c.getPA().sendStatement("You find some treasure in the chest.");
					c.getPA().checkObjectSpawn(id, x, y, 0, 10);
					p.stop();
				}
				@Override
				public void stop() {
					// TODO Auto-generated method stub
					
				}
			}, 5);
		}
	}

	/**
	 * Picks a random reward from chest items
	 * @return
	 */
	public static int getLength() {
		return CHEST_REWARDS.length;
	}
	
	public static int toothHalf(){
		return KEY_HALVES[0];
	}
	
	public static int loopHalf(){
		return KEY_HALVES[1];
	}

}
