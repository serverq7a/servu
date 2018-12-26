package org.mistex.game.world.content.skill.randomevents;

import org.mistex.game.MistexUtility;
import org.mistex.game.world.content.skill.randomevents.impl.*;
import org.mistex.game.world.player.Client;

public class EventHandler {

	/**
	 * Handles the event called if player is woodcutting
	 * @param c
	 */
	public static void handleWoodcuttingEvent(Client c) {
		int random = MistexUtility.random(60);
		if (c.playerIsWoodcutting) {
			switch (random) {
			case 30:
				EvilChicken.spawnChicken(c);
			break;
			
			case 60:
				//FreakyForester.teleportToLocation(c);
			break;
			
			case 1:
				SpiritTree.spawnSpiritTree(c);
			break;
			}
		}
	}
	
	/**
	 * Handles the random event if player is fishing
	 * @param c
	 */
	public static void handleFishingEvent(Client c) {
		int random = MistexUtility.random(60);
		if (c.playerIsFishing) {
			switch (random) {
			case 30:
				EvilChicken.spawnChicken(c);
			break;
			
			case 60:
				//FreakyForester.teleportToLocation(c);
			break;
			
			case 1:
				RiverTroll.spawnRiverTroll(c);
			break;
			}
		}
	}
	
	
	
	/**
	 * Handles the event if player is skilling
	 * @param c
	 */
	public static void handleEvent(Client c) {
		int random = MistexUtility.random(50);
		switch (random) {
			case 25:
				EvilChicken.spawnChicken(c);
			break;
			
			case 50:
				//FreakyForester.teleportToLocation(c);
			break;
		}
	}

	private static int[][] failCoords = { 
		{ 3333, 3333 }, { 3196, 3193 }, { 3084, 3549 }, { 2974, 3346 }, { 2781, 3506 }, { 2810, 3508 }, 
	};

	public static void failEvent(final Client c) {
		int loc = MistexUtility.random(failCoords.length - 1);
		c.teleportToX = failCoords[loc][0];
		c.teleportToY = failCoords[loc][1];
		c.sendMessage("You wake up in a strange location...");
	}

	public static void changeToSidebar(Client c, int[] bar) {
		for (int i = 0; i < 14; i++) {
			c.setSidebarInterface(i, -1);
		}
		c.setSidebarInterface(bar[0], bar[1]);
		c.getPA().changeToSidebar(bar[0]);
	}

}