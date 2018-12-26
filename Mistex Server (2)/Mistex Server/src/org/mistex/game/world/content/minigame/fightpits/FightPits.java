package org.mistex.game.world.content.minigame.fightpits;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.mistex.game.MistexUtility;
import org.mistex.game.world.player.Client;

@SuppressWarnings("static-access")
public class FightPits {

	public static final String PLAYING = "PLAYING";
	public static final String WAITING = "WAITING";

	private static String pitsChampion = " ";

	public static int gameStartTimer = 180;

	public static boolean gameStarted = false;

	private static Map<Client, String> playerMap = Collections.synchronizedMap(new HashMap<Client, String>());


	private static final int MINIGAME_START_POINT_X = 2392;
	private static final int MINIGAME_START_POINT_Y = 5139;

	private static final int EXIT_GAME_X = 2399;
	private static final int EXIT_GAME_Y = 5169;

	public static final int EXIT_WAITING_X = 2399;
	public static final int EXIT_WAITING_Y = 5177;

	private static final int WAITING_ROOM_X = 2399;
	private static final int WAITING_ROOM_Y = 5175;

	public static String getState(Client c) {
		return playerMap.get(c);
	}

	private static final int TOKKUL_ID = 995;

	public static void addPlayer(Client c) {
		playerMap.put(c, WAITING);
		c.getPA().movePlayer(WAITING_ROOM_X, WAITING_ROOM_Y, 0);
	}

	private static void enterGame(Client c) {
		playerMap.put(c, PLAYING);
		int teleportToX = MINIGAME_START_POINT_X + MistexUtility.random(12);
		int teleportToY = MINIGAME_START_POINT_Y + MistexUtility.random(12);
		c.getPA().movePlayer(teleportToX, teleportToY, 0);
		c.sendMessage("If you are the last one left, leave the arena for your reward.");
		c.getPA().yell("[ <col=DEDE1B>Fight Pits</col> ] A game has just started!");
	}

	public static void handleDeath(Client c) {
		playerMap.remove(c);
		c.getPA().movePlayer(2399, 5177, 0);
		c.absX = 2399;
		c.absY = 5177;
		c.getPA().walkableInterface(-1);
	}

	public static void removePlayer(Client c, boolean forceRemove) {
		if (forceRemove) {
			c.getPA().movePlayer(EXIT_WAITING_X, EXIT_WAITING_Y, 0);
			playerMap.remove(c);
			return;
		}
		
		String state = playerMap.get(c);
		if (state == null) {
			c.getPA().movePlayer(EXIT_WAITING_X, EXIT_WAITING_Y, 0);
			return;
		}

		if (state.equals(PLAYING)) {
			if (getListCount(PLAYING) - 1 == 0 && !forceRemove) {
				pitsChampion = c.playerName;
				c.headIcon = 21;
				c.updateRequired = true;
				c.getItems().addItem(TOKKUL_ID, 15000 + MistexUtility.random3(500000));
				c.getPA().yell("[ <col=DEDE1B>Fight Pits</col> ] <col=DEDE1B>"+MistexUtility.capitalize(c.playerName)+"</col> has won the game!");
			}
			c.getPA().movePlayer(EXIT_GAME_X, EXIT_GAME_Y, 0);
		} else if (state.equals(WAITING)) {
			c.getPA().movePlayer(EXIT_WAITING_X, EXIT_WAITING_Y, 0);
			c.getPA().walkableInterface(-1);
		}
		playerMap.remove(c);

		if (state.equals(PLAYING)) {
			if (!forceRemove) {
				playerMap.put(c, WAITING);
			}
		}
	}

	public static int getListCount(String state) {
		int count = 0;
		for (String s : playerMap.values()) {
			if (state == s) {
				count++;
			}
		}
		return count;
	}

	public static void update() {
		for (Client c : playerMap.keySet()) {
			String status = playerMap.get(c);
			@SuppressWarnings("unused")
			boolean updated = status == WAITING ? updateWaitingRoom(c) : updateGame(c);
		}
	}

	public static boolean updateWaitingRoom(Client c) {
		if (c.inFightPits()) {
			c.getPA().sendFrame126("Next Game Begins In : " + gameStartTimer, 2805);
			c.getPA().sendFrame126("Champion: JalYt-Ket-"+ MistexUtility.capitalize(pitsChampion).replaceAll("_", " "),2806);
			c.getPA().sendFrame36(560, 1);
			c.getPA().walkableInterface(2804);
		}
		return true;
		
	}

	public static boolean updateGame(Client c) {
		if (c.inFightPits()) {
		c.getPA().sendFrame126("Players Remaining: " + getListCount(PLAYING),2805);
		c.getPA().sendFrame126("Champion: JalYt-Ket-" + pitsChampion, 2806);
		c.getPA().sendFrame36(560, 1);
		c.getPA().walkableInterface(2804);
		}
		return true;
	}


	public static void beginGame() {
		for (Client c : playerMap.keySet()) {
			enterGame(c);
		}
	}
	
	public static void cantStart() {
		for (Client c : playerMap.keySet()) {
			c.sendMessage("There needs to be atleast 2 players to start!");
		}
	}
}