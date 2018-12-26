package org.mistex.game.world.content.pvp;

import org.mistex.game.MistexUtility;
import org.mistex.game.world.World;
import org.mistex.game.world.event.CycleEvent;
import org.mistex.game.world.event.CycleEventContainer;
import org.mistex.game.world.event.CycleEventHandler;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.Player;

public class PvPHandler {

	/*
	 * Standard interface & frame Id for the interface used
	 */
	static int INTERFACE = 25347, FRAME = 25350, PVP_LEVEL_FRAME = 28502, PVP_EP_FRAME = 28503;
	
	/**
	 * Handles the interfaces
	 * @param player player sending the interfaces
	 */
	public static void handleInterfaces(Client player) {
		player.getPA().walkableInterface(getTargetBar(player.targetPercentage));
		player.getPA().sendFrame126(playerHasTarget(player) ? MistexUtility.capitalize(player.targetName) : "None", getTargetFrame((player.targetPercentage)));
		player.getPA().sendFrame126("EP: @"+getColourForEp(player.EP)+"@"+player.EP+"%", PVP_EP_FRAME);
		player.getPA().sendFrame126(getLevelDifference(player,false) +" - "+getLevelDifference(player,true), PVP_LEVEL_FRAME);
	}
	
	/**
	 * calculates the difference
	 * @param player 
	 * @param up if you want to calculate up or down
	 * @return level difference
	 */
	static int getLevelDifference(Client player, boolean up) {
		int difference = 0;
		if (up)
			difference = (int)player.getCombatLevel() + player.wildLevel; 
		 else 
			difference =(int)player.getCombatLevel() - player.wildLevel;
		return difference < 3 ? 3 : difference > 126 && up ? 126 : difference;
	}
	
	/**
	 * Gets the interface Id for your 
	 * @param percentage
	 * @return interface ID
	 */
	static int getTargetBar(int percentage) {
		if (percentage >= 1 && percentage < 5)
			return INTERFACE + 6;
		else if (percentage >= 5 && percentage < 9)
			return INTERFACE + 12;
		else if (percentage >= 10 && percentage < 20)
			return INTERFACE + 18;
		else if (percentage >= 20 && percentage < 30)
			return INTERFACE + 24;
		else if (percentage >= 30 && percentage < 40)
			return INTERFACE + 30;
		else if (percentage >= 40 && percentage < 50)
			return INTERFACE + 36;
		else if (percentage >= 50 && percentage < 59)
			return INTERFACE + 42;
		else if (percentage == 60)
			return 25395;
		return INTERFACE;
	}
	
	/**
	 * Gets the frame for target percentage
	 * @param percentage target percentage
	 * @return frame ID
	 */
	static int getTargetFrame(int percentage) {
		if (percentage >= 1 && percentage < 5)
			return FRAME + 6;
		else if (percentage >= 5 && percentage < 9)
			return FRAME + 12;
		else if (percentage >= 10 && percentage < 20)
			return FRAME + 18;
		else if (percentage >= 20 && percentage < 30)
			return FRAME + 24;
		else if (percentage >= 30 && percentage < 40)
			return FRAME + 30;
		else if (percentage >= 40 && percentage < 50)
			return FRAME + 36;
		else if (percentage >= 50 && percentage < 59)
			return FRAME + 42;
		else if (percentage == 60)
			return 25398;
		return FRAME;
	}
	
	/**
	 * Checks if you have a target
	 * @param player
	 * @return
	 */
	static boolean playerHasTarget(Client player) {
		return player.targetIndex != 0 && (player.targetName != "" || player.targetName != null);
	}
	
	/**
	 * Assigns a target for 
	 * @param player
	 */
	public static void assignTarget(Client player) {
		for (Player players : World.players) {
			if (players != null) {
				Client p = (Client)players;
				if (p.logoutTimer <= 0 && player.logoutTimer <= 0 && p.playerId != player.playerId && !playerHasTarget(p) && !playerHasTarget(player) && player.inWild() && p.inWild() && p.targetPercentage >= PvPConfig.TARGET_PERCENTAGE_REQUIRED &&
						(p.combatLevel == player.combatLevel || p.combatLevel == player.combatLevel - 1 || p.combatLevel == player.combatLevel +1)) {
					setTarget(player, p.playerId, p.playerName);
					setTarget(p, player.playerId, player.playerName);
				}
			}
		}
	}
	
	/**
	 * Sets a player target
	 * @param player
	 * @param targetPlayerId
	 * @param targetName
	 */
	static void setTarget(Client player, int targetPlayerId, String targetName) {
		player.targetIndex = targetPlayerId;
		player.targetName = targetName;
		if (World.players[targetPlayerId] != null) {
			player.getPA().createPlayerHints(10, player.targetIndex);
		}
	}
	
	/**
	 * Gets the colour for the ep percentage
	 * @param ep player's earned potential
	 * @return colour
	 */
	static String getColourForEp(int ep) {
		if (ep >= 75)
			return "gre";
		if (ep >= 50 && ep < 75)
			return "yel";
		if (ep >= 25 && ep < 50)
			return "or1";
		return "red";
	}
	
	/**
	 * Checks if the target is a null
	 * @param targetName players target name
	 * @return null or not
	 */
	static boolean targetIsNull(String targetName) {
		for (Player p : World.players)
			if (p != null && p.playerName.equalsIgnoreCase(targetName))
				return false;
		return true;
	}
	
	/**
	 * Handles player to gain earned potential
	 * @param player
	 */
	public static void handleEP(final Client player) {
		player.EP_ACTIVE = true;
		CycleEventHandler.getSingleton().addEvent(player ,new CycleEvent() {
			int timer = 0;
			@Override
			public void execute(CycleEventContainer container) {
				//player.sendMessage(""+player.logoutTimer);
				//player.sendMessage(""+targetIsNull(player.targetName));
				if (player.logoutTimer >= 0 && targetIsNull(player.targetName))
					player.logoutTimer--;
				if (targetIsNull(player.targetName) && (player.logoutTimer == 900 || player.logoutTimer == 800 || player.logoutTimer == 700 || player.logoutTimer == 600 || 
					player.logoutTimer == 500 || player.logoutTimer == 400 || player.logoutTimer == 300 || player.logoutTimer == 200 || player.logoutTimer == 100)) {
					player.sendMessage("Your target has "+player.logoutTimer / 100+" "+(player.logoutTimer == 100 ? "minute" : "minutes")+" to return else you will be assigned anothern target.");
				}
				if (player.logoutTimer == 1 && targetIsNull(player.targetName)) {
					player.sendMessage("Your target has left this world and did not return.");
					player.getPA().createPlayerHints(-1, player.targetIndex);
					setTarget(player, 0, null);
				}
				/*
				 * ep system
				 */
				timer++;
				if (timer == 100) {
					if (player.inWild()) {
						player.EP_MINUTES++;;
						if (player.targetPercentage < 100)
							player.targetPercentage++;
					}
					timer = 0;
				}
				if (player.EP_MINUTES == PvPConfig.EP_AMOUNT_REQUIRED) {
					player.EP_MINUTES = 0;
					if (player.inWild() && PvPDrops.getTotalNet(player) >= 76000) {
						if (player.EP + PvPConfig.EP_INCREASE_AMOUNT <= 100)
							player.EP += PvPConfig.EP_INCREASE_AMOUNT;
						else
							player.EP = 100;
					}
				}
				//player.sendMessage("Current timer: "+timer+" total minutes: "+player.EP_MINUTES);
				//player.sendMessage("target percentage: "+player.targetPercentage+" total EP: "+player.EP);
				//player.sendMessage("logout timer: "+player.logoutTimer);
				if (!playerHasTarget(player) && player.targetPercentage >= PvPConfig.TARGET_PERCENTAGE_REQUIRED)
					assignTarget(player);
				if (!player.inWild()) {
					if (!playerHasTarget(player)) 
						container.stop();
					else {
						player.safeTimer--;
						if (player.safeTimer == 0) {
							player.sendMessage("You were too long in a safe area and lost your target.");
							((Client)World.players[player.targetIndex]).sendMessage("Your target became inactive and you will be assigned a new one.");
							resetTarget(player);
						}
						if (player.safeTimer == 900 || player.safeTimer == 800 || player.safeTimer == 700 || player.safeTimer == 600 ||
								player.safeTimer == 500 || player.safeTimer == 400 || player.safeTimer == 300 || player.safeTimer == 200 ||
								player.safeTimer == 100) {
							((Client)World.players[player.targetIndex]).sendMessage("Your target is in a safe zone and have "+player.safeTimer / 100+" minutes to return.");
							player.sendMessage("You must leave the safe zone in  "+player.safeTimer / 100+" minutes or you will loose your target!");
						}
					}
				}
			}
			@Override
			public void stop() {
				player.EP_ACTIVE = false;
			}
		}, 1);
	}
	
	/**
	 * Resets the target
	 * @param player
	 */
	static void resetTarget(Client player) {
		player.getPA().createPlayerHints(-1, player.targetIndex);
		((Client)World.players[player.targetIndex]).getPA().createPlayerHints(-1, player.playerId);
		World.players[player.targetIndex].targetIndex = 0;
		World.players[player.targetIndex].safeTimer = 1000;
		player.targetIndex = 0;
		player.targetPercentage = 0;
		player.safeTimer = 1000;
	}
	
	/**
	 * Handles the logout
	 * @param player
	 */
	public static void handleLogout(Client player) {
		Client target = (Client)World.players[player.targetIndex];
		if (target != null) {
			if (target.logoutTimer <= 0)
				target.logoutTimer = 1000;
			target.sendMessage("Your target has left this world and has "+target.logoutTimer / 100+" minutes to return.");
			target.targetIndex = 0;
		}
	}
	
	/**
	 * Handles the login
	 * @param player player loggin in
	 */
	public static void handleLogin(Client player) {
		Client target = (Client)World.players[getTargetIndex(player.targetName)];
		if (target != null && target.logoutTimer > 0) {
			target.sendMessage("Your target has logged in and you resigned him as a target!");
			setTarget(target, player.playerId, player.playerName);
			setTarget(player, target.playerId, target.playerName);
		} else {
			setTarget(player, 0, null);
		}
		if (target != null && target.logoutTimer <= 0) {
			player.targetPercentage = 0;
		}
		if (player.inWild())
			handleEP(player);
	}
	
	/**
	 * Gets the target's player index by
	 * @param name
	 * @return target player index
	 */
	public static int getTargetIndex(String name) {
		for (Player p : World.players) 
			if (p != null && p.playerName.equalsIgnoreCase(name))
				return p.playerId;
		return 0;
	}
}
