package org.mistex.game.world.content;

import org.mistex.game.MistexUtility;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.item.ItemAssistant;


public class MysteryBox {
	
	/**
	 * Mystery box item id
	 */
	public static int MysteryBox = 6199;
	
	/**
	 * Holds the regular items
	 */
	static int[] REGULARS = { 
		2673,2669,2671,2675,2657,2653,2655,2659,2665,2661,2663,2667,3479,3480,3478,
		2591,2593,2595,2597,2607,2609,2611,2613,2615,2617,2619,2621,3473,3475,3476,7362,7366,7370,7374,7378,7382,7386,7390,7394,
		2583,2585,2587,2589,2599,2601,2603,2605,2623,2625,2627,2629,3472,3474,3477,7364,7368,7372,7376,7380,7384,7388,7392,7396 };
		
	/**
	 * Holds the shitty items
	 */
	static int[] SHITS = { 
		1333,1319,1215,1079,544,542,1127,4587,1187,1149,1249,1305,1377,1434 };
		
	/**
	 * Holds the rare items
	 */
	static int[] RARES = {
		11724, 11726, 11728
		
	};
	
	/**
	 * Checks if player can open the mystery box
	 * @param c
	 * @return
	 */
	public static boolean canOpen(Client c) {
		if (c.getItems().playerHasItem(MysteryBox)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Opens the mystery box
	 * @param c
	 */
	@SuppressWarnings("static-access")
	public static void openBox(Client c) {
		if (canOpen(c)) {
				c.getItems().deleteItem(MysteryBox, 1);
				int item = randomHandle();
				c.getItems().addItem(item, 1);
				c.sendMessage("[ <col=AB521B>Mistex</col> ] You recieved a <col=AB521B>" + ItemAssistant.getItemName(item) + "</col>.");
			if (c.getItems().getItemPrice(item) > 5000000) {
				c.getPA().yell("[ <col=AB521B>Mistex</col> ]  <col=AB521B>"+MistexUtility.capitalize(c.playerName)+"</col> recieved a <col=AB521B>" + ItemAssistant.getItemName(item) + "</col> from a mystery box!");
			}
		}
	}

	/**
	 * Handles the random item the player will recieve
	 * @return
	 */
	public static int randomHandle() {
		if (Math.random() >= 1 - 1/150.0)
			return RARES[(int) (Math.random() * RARES.length)];
		if (Math.random() >= 1 - 1/1.7)
			return REGULARS[(int) (Math.random() * REGULARS.length)];
		return SHITS[(int) (Math.random() * SHITS.length)];
	}	


}
