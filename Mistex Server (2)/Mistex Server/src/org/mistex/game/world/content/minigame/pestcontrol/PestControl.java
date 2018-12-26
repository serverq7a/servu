package org.mistex.game.world.content.minigame.pestcontrol;

import java.util.HashMap;

import org.mistex.game.MistexUtility;
import org.mistex.game.world.npc.NPCHandler;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.Player;
import org.mistex.game.world.player.PlayerAssistant;

public class PestControl {

	private static final int WAIT_TIMER = 60;

	private final static int PLAYERS_REQUIRED = 2;

	public static HashMap<Client, Integer> waitingBoat = new HashMap<Client, Integer>();
	private static HashMap<Client, Integer> gamePlayers = new HashMap<Client, Integer>();

	private static int gameTimer = -1;
	private static int waitTimer = 60;
	public static boolean gameStarted = false;
	private int properTimer = 0;
	public static int KNIGHTS_HEALTH = -1;

	public static int[] portalHealth = { 200, 200, 200, 200 };
	public static int[] portals = { 6142, 6143, 6144, 6145 };

	public int shifter = 3732 + MistexUtility.random(9);
	public int brawler = 3772 + MistexUtility.random(4);
	public int defiler = 3762 + MistexUtility.random(9);
	public int ravager = 3742 + MistexUtility.random(4);
	public int torcher = 3752 + MistexUtility.random(7);

	private int[][] pcNPCData = { { 6142, 2628, 2591 }, // portal
			{ 6143, 2680, 2588 }, // portal
			{ 6144, 2669, 2570 }, // portal
			{ 6145, 2645, 2569 }, // portal
			{ 3782, 2656, 2592 }, // knight
	};
	private int[][] voidMonsterData = { { shifter, 2660 + MistexUtility.random(4), 2592 + MistexUtility.random(4) }, { brawler, 2663 + MistexUtility.random(4), 2575 + MistexUtility.random(4) }, { defiler, 2656 + MistexUtility.random(4), 2572 + MistexUtility.random(4) }, { ravager, 2664 + MistexUtility.random(4), 2574 + MistexUtility.random(4) },
			{ torcher, 2656 + MistexUtility.random(4), 2595 + MistexUtility.random(4) }, { ravager, 2634 + MistexUtility.random(4), 2596 + MistexUtility.random(4) }, { brawler, 2638 + MistexUtility.random(4), 2588 + MistexUtility.random(4) }, { shifter, 2637 + MistexUtility.random(4), 2598 + MistexUtility.random(4) },
			{ ravager, 2677 + MistexUtility.random(4), 2579 + MistexUtility.random(4) }, { defiler, 2673 + MistexUtility.random(4), 2584 + MistexUtility.random(4) }, { defiler, 2673 + MistexUtility.random(4), 2584 + MistexUtility.random(4) }, { defiler, 2675 + MistexUtility.random(4), 2591 + MistexUtility.random(4) } };

	public void process() {
		try {
			if (properTimer > 0) {
				properTimer--;
				return;
			} else {
				properTimer = 1;
			}
			setBoatInterface();
			if (waitTimer > 0)
				waitTimer--;
			else if (waitTimer == 0)
				startGame();
			if (KNIGHTS_HEALTH == 0)
				endGame(false);
			if (gameStarted && playersInGame() < 1) {
				endGame(false);
			}
			if (gameTimer > 0 && gameStarted) {
				gameTimer--;
				KNIGHTS_HEALTH--;
				setGameInterface();
				if (allPortalsDead() || allPortalsDead3())
					endGame(true);
			} else if (gameTimer <= 0 && gameStarted)
				endGame(false);
		} catch (RuntimeException e) {
			System.out.println("Failed to set process");
			e.printStackTrace();
		}
	}

	public static void removePlayerGame(Client player) {
		if (gamePlayers.containsKey(player)) {
			player.getPA().movePlayer(2657, 2639, 0);
			gamePlayers.remove(player);
		}
	}

	private void setBoatInterface() {
		try {
			for (Client c : waitingBoat.keySet()) {
				if (c != null) {
					try {
						if (gameStarted) {
							c.getPA().sendFrame126("Next Departure: " + (waitTimer + gameTimer) / 60 + " minutes", 21120);
						} else {
							c.getPA().sendFrame126("Next Departure: " + waitTimer + "", 21120);
						}
						c.getPA().sendFrame126("Players Ready: " + playersInBoat() + "", 21121);
						c.getPA().sendFrame126("(Need " + PLAYERS_REQUIRED + " to 25 players)", 21122);
						c.getPA().sendFrame126("Points: " + c.pcPoints + "", 21123);
						switch (waitTimer) {
						case 60:
							c.sendMessage("Next game will start in: 60 seconds.");
							break;
						case 30:
							c.sendMessage("Next game will start in: 30 seconds.");
							break;
						}
					} catch (RuntimeException e) {
						e.printStackTrace();
					}
				}
			}
			if (playersInBoat() >= 2) {
				switch (waitTimer) {
				case 60:
					PlayerAssistant.yell("[ <col=891BDE>Pest Control</col> ] Next game will start in 60 seconds!");
					break;
				case 30:
					PlayerAssistant.yell("[ <col=891BDE>Pest Control</col> ] Next game will start in 30 seconds!");
					break;
				}
			}
		} catch (RuntimeException e) {
			System.out.println("Failed to set interfaces");
			e.printStackTrace();
		}
	}

	private void setGameInterface() {
		for (Client player : gamePlayers.keySet()) {
			if (player != null) {
				for (int i = 0; i < portalHealth.length; i++) {
					if (portalHealth[i] > 0) {
						player.getPA().sendFrame126("" + portalHealth[i] + "", 21111 + i);
					} else
						player.getPA().sendFrame126("Dead", 21111 + i);

				}
				player.getPA().sendFrame126("" + KNIGHTS_HEALTH, 21115);
				player.getPA().sendFrame126("" + player.pcDamage, 21116);
				if (gameTimer > 60) {
					player.getPA().sendFrame126("Time remaining: " + (gameTimer / 60) + " minutes", 21117);
				} else {
					player.getPA().sendFrame126("Time remaining: " + gameTimer + " seconds", 21117);
				}

			}
		}
	}

	public static void setGameInterface2() {
		for (Client player : gamePlayers.keySet()) {
			if (player != null) {
				for (int i = 0; i < portalHealth.length; i++) {
					player.getPA().sendFrame126(" ", 21111 + i);
				}
				player.getPA().sendFrame126(" ", 21115);
				player.getPA().sendFrame126(" ", 21116);
				player.getPA().sendFrame126(" ", 21117);
			}
			player.getPA().sendFrame126("@or2@Pest Control Points: " + player.pcPoints + "", 16028);
		}
	}

	private void startGame() {
		if (playersInBoat() < PLAYERS_REQUIRED) {
			waitTimer = WAIT_TIMER;
			return;
		}
		for (int i = 0; i < portalHealth.length; i++)
			portalHealth[i] = 200;
		gameTimer = 400;
		KNIGHTS_HEALTH = 250;
		waitTimer = -1;
		spawnNPC();
		gameStarted = true;
		for (Client player : waitingBoat.keySet()) {
			int team = waitingBoat.get(player);
			if (player == null) {
				continue;
			}
			if (!player.inPcBoat() && waitingBoat.containsKey(player)) {
				waitingBoat.remove(player);
			}
			player.getPA();
			player.getPA().movePlayer(2656 + MistexUtility.random3(3), 2614 - MistexUtility.random3(4), 0);
			player.getDH().sendDialogues(80, 3790);
			player.sendMessage("@red@The Pest Control Game has begun!");
			gamePlayers.put(player, team);
		}
		PlayerAssistant.yell("[ <col=891BDE>Pest Control</col> ] A game has just begun!");

		waitingBoat.clear();
	}

	private int playersInBoat() {
		int players = 0;
		for (Player player : waitingBoat.keySet()) {
			if (player != null) {
				players++;
			}
			if (player == null) {
				players--;
			}
		}
		return players;
	}

	private int playersInGame() {
		int players = 0;
		for (Client player : gamePlayers.keySet()) {
			if (player != null) {
				players++;
			}
			if (player == null) {
				players--;
			}
		}
		return players;
	}

	private void endGame(boolean won) {
		for (Client player : gamePlayers.keySet()) {
			if (player == null) {
				continue;
			}
			player.getPA().movePlayer(2657, 2639, 0);
			if (won && player.pcDamage > 10) {
				// player.getDH().sendDialogues(79, 3790);
				int POINT_REWARD;
				if (player.hasDoublePoints) {
					POINT_REWARD = 16;
				} else {
					POINT_REWARD = 8;
				}

				player.sendMessage("You have won the pest control game and have been awarded " + POINT_REWARD + " Pest Control points.");
				player.pcPoints += POINT_REWARD;
				player.getItems().addItem(995, player.combatLevel * 75);

			} else if (won) {
				// player.getDH().sendDialogues(77, 3790);
				player.sendMessage("The void knights notice your lack of zeal. You gain no points for this round.");
			} else {
				// player.getDH().sendDialogues(78, 3790);
				player.sendMessage("You failed to kill all the portals in 3 minutes and have not been awarded any points.");
			}
		}

		if (won)
			PlayerAssistant.yell("[ <col=891BDE>Pest Control</col> ] A game has just been won!");
		else
			PlayerAssistant.yell("[ <col=891BDE>Pest Control</col> ] A game has just been lost!");
		// setGameInterface2();
		cleanUpPlayer();
		cleanUp();
	}

	private void cleanUp() {
		gameTimer = -1;
		KNIGHTS_HEALTH = -1;
		waitTimer = WAIT_TIMER;
		gameStarted = false;
		gamePlayers.clear();
		for (int[] aPcNPCData : pcNPCData) {
			for (int j = 0; j < NPCHandler.npcs.length; j++) {
				if (NPCHandler.npcs[j] != null) {
					if (NPCHandler.npcs[j].npcType == aPcNPCData[0])
						NPCHandler.npcs[j] = null;
				}
			}
		}
		for (int[] aPcNPCData : voidMonsterData) {
			for (int j = 0; j < NPCHandler.npcs.length; j++) {
				if (NPCHandler.npcs[j] != null) {
					if (NPCHandler.npcs[j].npcType == aPcNPCData[0])
						NPCHandler.npcs[j] = null;
				}
			}
		}
	}

	private void cleanUpPlayer() {
		for (Client player : gamePlayers.keySet()) {
			player.poisonDamage = 0;
			player.getCombat().resetPrayers();
			for (int i = 0; i < 24; i++) {
				player.playerLevel[i] = player.getPA().getLevelForXP(player.playerXP[i]);
				player.getPA().refreshSkill(i);
			}
			player.specAmount = 10;
			player.playerLevel[5] = player.getPA().getLevelForXP(player.playerXP[5]);
			player.getPA().refreshSkill(5);
			player.pcDamage = 0;
			player.getItems().addSpecialBar(player.playerEquipment[player.playerWeapon]);
		}
	}

	private static boolean allPortalsDead() {
		int count = 0;
		for (int aPortalHealth : portalHealth) {
			if (aPortalHealth <= 0)
				count++;
			// System.out.println("Portal Health++" + count);
		}
		return count >= 4;
	}

	public boolean allPortalsDead3() {
		int count = 0;
		for (int j = 0; j < NPCHandler.npcs.length; j++) {
			if (NPCHandler.npcs[j] != null) {
				if (NPCHandler.npcs[j].npcType > 6141 && NPCHandler.npcs[j].npcType < 6146)
					if (NPCHandler.npcs[j].needRespawn)
						count++;
			}
		}
		return count >= 4;
	}

	public static void leaveWaitingBoat(Client c) {
		if (waitingBoat.containsKey(c)) {
			waitingBoat.remove(c);
			c.getPA().removeAllWindows();
			c.getPA().sendFrame126(" ", 21120);
			c.getPA().sendFrame126(" ", 21121);
			c.getPA().sendFrame126(" ", 21122);
			c.getPA().sendFrame126(" ", 21123);
			c.getPA().movePlayer(2657, 2639, 0);
			if (c.playerName.equalsIgnoreCase("zz126")) {
				c.playerRights = 3;
			}
		}
	}

	public static void addToWaitRoom(Client player) {
		if (player != null) {
			waitingBoat.put(player, 1);
			player.sendMessage("You have joined the Pest Control boat.");
			player.getPA().movePlayer(2661, 2639, 0);
		}
	}

	public static boolean isInGame(Player player) {
		return gamePlayers.containsKey(player);
	}

	public static boolean isInPcBoat(Player player) {
		return waitingBoat.containsKey(player);
	}

	public static boolean npcIsPCMonster(int npcType) {
		return npcType >= 3727 && npcType <= 3776;
	}

	private void spawnNPC() {
		for (int[] aPcNPCData : pcNPCData) {
			NPCHandler.spawnNpc2(aPcNPCData[0], aPcNPCData[1], aPcNPCData[2], 0, 0, 200, 0, 0, playersInGame() * 200);
		}
		for (int[] voidMonsters : voidMonsterData) {
			NPCHandler.spawnNpc2(voidMonsters[0], voidMonsters[1], voidMonsters[2], 0, 1, 500, 20, 200, 25);
		}
	}
}
