package org.mistex.game.world.content.minigame.weapongame;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.mistex.game.MistexUtility;
import org.mistex.game.world.World;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.PlayerConfiguration;

/**
 * Weapon Game Minigame
 * @author Omar | Play Boy
 * @author Michael | Chex
 *
 */

public class WeaponGame {
	
	/* Variables */
	public static Map<Client, String> playerMap = Collections.synchronizedMap(new HashMap<Client, String>());
    public static String advanceMessage = "You advanced to the next tier!";
	public static final String PLAYING = "PLAYING";
	public static final String WAITING = "WAITING";
	private static Client playerInLead;
	public static int gameStartTimer = 500;
	public static boolean gameStarted = false;
	public static final int WEAPONMINIGAME_START_POINT_X = 3359;
	public static final int WEAPONMINIGAME_START_POINT_Y = 3413;
	public static final int WEAPONEXIT_GAME_X = 3351;
	public static final int WEAPONEXIT_GAME_Y = 3342;
	public static final int WEAPONEXIT_WAITING_X = PlayerConfiguration.RESPAWN_X;
	public static final int WEAPONEXIT_WAITING_Y = PlayerConfiguration.RESPAWN_Y;
	public static final int WEAPONWAITING_ROOM_X = 3351;
	public static final int WEAPONWAITING_ROOM_Y = 3342;
	
	
		/* Handles the spawning locations */
	public static int[][] spawns = new int[][] {
		{ 3356, 3405, 5, 7 },
		{ 3362, 3403, 2, 10 },
		{ 3362, 3425, 11, 4 },
		{ 3371, 3404, 3, 9 }
		};
	
	  public static int[] randomSpawn() {
		  int index = (int) (Math.random() * spawns.length);
		  int[] area = spawns[index];
		  int x = (int) (Math.random() * area[2]) + area[0];
		  int y = (int) (Math.random() * area[3]) + area[1];
		  return new int[] { x, y };
	  }
	  
	  	/* Yells a message to everyone in server */
	  public static void yell(String string) {
			for (int j = 0; j < World.players.length; j++) {
				if (World.players[j] != null) {
					Client c2 = (Client) World.players[j];
					c2.sendMessage(string);
				}
			}
		}
	  
		/* Checking if a player can enter the minigame */
	public static boolean canEnter(Client p) {
		if((p.getItems().freeSlots() == 28 &&  p.playerEquipment[p.playerHat] == -1) && (p.playerEquipment[p.playerCape] == -1) && (p.playerEquipment[p.playerAmulet] == -1) && (p.playerEquipment[p.playerChest] == -1) && (p.playerEquipment[p.playerShield] == -1) && (p.playerEquipment[p.playerLegs] == -1) && (p.playerEquipment[p.playerHands] == -1) && (p.playerEquipment[p.playerFeet] == -1) && (p.playerEquipment[p.playerWeapon] == -1)) {
			return true;
		} else {
			return false;
		}
	}
	
		/* Getting the state of player */
	public static String getState(Client c) {
		return playerMap.get(c);
	}
	

	/* Get the count of players */
public static int getListCount(String state) {
	int count = 0;
	for (String s : playerMap.values()) {
		if (state.equals(s)) {
			count++;
		}
	}
	return count;
}

		/* Add player to waiting room */
	public static void addPlayer(Client c) {
		if (c == null) return;
		synchronized (c) {
			playerMap.put(c, WAITING);
			if (playerInLead == null) {
				playerInLead = c;
			}
			c.getPA().movePlayer(WEAPONWAITING_ROOM_X, WEAPONWAITING_ROOM_Y, 0);
		}
	}

		/* Add player to game */
	public static void enterGame(Client c) {
		if (c == null) return;
		synchronized (c) {
			playerMap.put(c, PLAYING);
			if (playerInLead == null) {
				playerInLead = c;
				c.getPA().createPlayerHints2(10, playerInLead.playerId);
			}
			int spawn[] = randomSpawn();
			c.getPA().movePlayer(spawn[0], spawn[1], 0);
			c.sendMessage("<col=0079AD>You must kill everyone!");
			c.weaponKills = 0;
			handleWeaponTiers(c);
			refreshPlayer(c);
		}
	}
	
		/* Handles what happens killed a player*/
	public static void handleKilled(Client c) {
		if (c == null) return;
		synchronized (c) {
			if (c.weaponKills > playerInLead.weaponKills) {
				playerInLead = c;
			}		
			handleWeaponTiers(c);
		}
	}

		/* Handles what happens when a player dies */
	public static void handleDeath(Client c) {
		if (c == null) return;
		synchronized (c) {
			c.sendMessage("<col=0079AD>Kill them, kill them all!");
			int spawn[] = randomSpawn();
			c.getPA().movePlayer(spawn[0], spawn[1], 0);
			refreshPlayer(c);
		}
	}

		/* Removing player from game */
	public static void removePlayer(Client c, boolean forceRemove) {
		synchronized (c) {
			if (forceRemove) {
				c.getItems().removeAllItems1();
				refreshPlayer(c);
				playerMap.remove(c);
				return;
			}
		}
	}
	
		/* Refresh player */
	public static void refreshPlayer(Client c) {
		synchronized (c) {
			c.faceUpdate(0);
			c.playerLevel[5] = c.getPA().getLevelForXP(c.playerXP[5]);
			c.getPA().refreshSkill(5);
			c.playerLevel[3] = c.getPA().getLevelForXP(c.playerXP[3]);
			c.getPA().refreshSkill(3);
			c.getPA().resetFollowers();
		 for (int i = 0; i < 25; i++) {
		    c.getPA().setSkillLevel(i, c.playerLevel[i], c.playerXP[i]);
		    c.getPA().refreshSkill(i);
		 }
		for (int p = 0; p < c.PRAYER.length; p++) { 
		    c.prayerActive[p] = false;
		    c.getPA().sendFrame36(c.PRAYER_GLOW[p], 0);
		   }
		}
	}

		/* Handles what happens when a player logs out of minigame */
	public static void handleLogout(Client c) {
		c.getItems().removeAllItems1();
		WeaponGame.playerMap.remove(PLAYING);
		WeaponGame.removePlayer(c, true);
		c.getPA().movePlayer(3087, 3499, 0);
	}
	
		/* Handles what happens when player wins minigame */
	public static void handleWin(Client c) {
		synchronized (c) {
			yell("[ <col=0079AD>Weapon Game</col> ] <col=0079AD>"+MistexUtility.capitalize(playerInLead.playerName)+" has just won the game!");
			playerMap.remove(PLAYING);
			removeAll();
			c.getItems().removeAllItems1();
			c.headIcon = 21;
			c.updateRequired = true;
			c.getBank().addItem(996, 1000000);
			c.sendMessage("<col=0079AD>You were reward 1,000,000 coins for your win! It was sent to your bank.");
			c.getPA().movePlayer(WEAPONEXIT_WAITING_X, WEAPONEXIT_WAITING_Y, 0);
			c.getPA().walkableInterface(-1);
			c.getPA().createPlayerHints(0, playerInLead.playerId);
			gameStarted = false;
			
		}
	}

		/* Determines what to update */
	public static void update() {
		synchronized (playerMap) {
			for (Client c : playerMap.keySet()) {
				String status = playerMap.get(c);
				@SuppressWarnings("unused")
				boolean updated = status == WAITING ? updateWaitingRoom(c) : updateGame(c);
			}
		}
	}

		/* Updates waiting room */
	public static boolean updateWaitingRoom(Client c) {
		if (c.inWeaponLobby()) {
			c.getPA().sendFrame126("Last winner:",25349);
			c.getPA().sendFrame126(MistexUtility.capitalize(playerInLead == null ? "None" : playerInLead.playerName).replaceAll("_", " "), 25350);		
			c.getPA().sendFrame126("Players in lobby:", 25351);
			c.getPA().sendFrame126(""+getListCount(WAITING)+"", 25352);
			c.getPA().sendFrame126("Next game begins in: " + gameStartTimer, 25353);
			c.getPA().sendFrame36(560, 1);
			c.getPA().walkableInterface(25347);
		}
		return true;
	}

		/* Updates game */
	public static boolean updateGame(Client c) {
		if (c.inWeaponGame()) {
			c.getPA().sendFrame126("Current Tier:", 25349);
			c.getPA().sendFrame126(""+c.weaponKills, 25350);		
			c.getPA().sendFrame126("Leader:",25351);
			c.getPA().sendFrame126(playerInLead.playerName, 25352);
			c.getPA().sendFrame126("Players in game: " + getListCount(PLAYING), 25353);	
			c.getPA().sendFrame36(560, 1);
			c.getPA().walkableInterface(25347);
			c.getPA().createPlayerHints(10, playerInLead.playerId);
			  if (WeaponGame.getListCount(WeaponGame.PLAYING) == 1) {
	               WeaponGame.handleWin(c);         
	            }
		}
		return true;
		
	}
	
		/* Remove all players  */
	public static void removeAll() {
		for (Client c : playerMap.keySet()) {
			if (getListCount(PLAYING) >= 1) {
				playerMap.remove(PLAYING);
				c.getPA().movePlayer(WEAPONEXIT_WAITING_X, WEAPONEXIT_WAITING_Y, 0);
			}
		}
	}

		/* Begins the actual game */
	public static void beginGame() {
		yell("[ <col=0079AD>Weapon Game</col> ] A game has just started!");
		for (Client c : playerMap.keySet()) {
			enterGame(c);
		}
	}
	
		/* Determines if a game can start or not */
	public static void cantStart() {
		for (Client c : playerMap.keySet()) {
			if (getListCount(PLAYING) >= 1) {
				c.sendMessage("<col=0079AD>There is already a game active.");
			} else {
				c.sendMessage("<col=0079AD>There needs to be atleast 2 players to start!");
			}
			
		}
	}
	
			/* Weapon tiers */
	  public static void handleWeaponTiers(Client player) {
	        switch (player.weaponKills) {
	        case 0:
	            player.sendMessage("You start off with your fists!");
				player.specAmount = 10.0;
				player.getItems().addSpecialBar(player.playerEquipment[player.playerWeapon]);
				player.hasWeaponGameWeapon = true;
	            break;	        
	        case 1:
	            player.getItems().wearItem(1285, 1, 3);//Mith Sword
	            player.sendMessage("You advanced to the next tier!");
	            player.getDH().OneItemDialogue(advanceMessage, 1285);
				player.specAmount = 10.0;
				player.getItems().addSpecialBar(player.playerEquipment[player.playerWeapon]);
				player.hasWeaponGameWeapon = true;
	            break;
	        case 2:
	            player.getItems().wearItem(1211, 1, 3);//Adamant Dagger
	            player.sendMessage("You advanced to the next tier!");
	            player.getDH().OneItemDialogue(advanceMessage, 1211);
				player.specAmount = 10.0;
				player.getItems().addSpecialBar(player.playerEquipment[player.playerWeapon]);
				player.hasWeaponGameWeapon = true;
	            break;
	        case 3:
	            player.getItems().wearItem(2402, 1, 3);//Silver Light
	            player.sendMessage("You advanced to the next tier!");
	            player.getDH().OneItemDialogue(advanceMessage, 2402);
				player.specAmount = 10.0;
				player.getItems().addSpecialBar(player.playerEquipment[player.playerWeapon]);
				player.hasWeaponGameWeapon = true;
	            break;
	        case 4:
	            player.getItems().wearItem(1373, 1, 3);//Rune Battleaxe
	            player.sendMessage("You advanced to the next tier!");
	            player.getDH().OneItemDialogue(advanceMessage, 1373);
				player.specAmount = 10.0;
				player.getItems().addSpecialBar(player.playerEquipment[player.playerWeapon]);
				player.hasWeaponGameWeapon = true;
	            break;
	        case 5:
	            player.getItems().wearItem(1215, 1, 3);//Dragon Dagger
	            player.sendMessage("You advanced to the next tier!");
	            player.getDH().OneItemDialogue(advanceMessage, 1215);
				player.specAmount = 10.0;
				player.getItems().addSpecialBar(player.playerEquipment[player.playerWeapon]);
				player.hasWeaponGameWeapon = true;
	            break;
	        case 6:
	            player.getItems().wearItem(4587, 1, 3);//Dragon Scimitar
	            player.sendMessage("You advanced to the next tier!");
	            player.getDH().OneItemDialogue(advanceMessage, 4587);
				player.specAmount = 10.0;
				player.getItems().addSpecialBar(player.playerEquipment[player.playerWeapon]);
				player.hasWeaponGameWeapon = true;
	            break;
	        case 7:
	            player.getItems().wearItem(4153, 1, 3);//Granite Maul
	            player.sendMessage("You advanced to the next tier!");
	            player.getDH().OneItemDialogue(advanceMessage, 4153);
				player.specAmount = 10.0;
				player.getItems().addSpecialBar(player.playerEquipment[player.playerWeapon]);
				player.hasWeaponGameWeapon = true;
	            break;	            
	        case 8:
	            player.getItems().wearItem(4151, 1, 3);//Whip
	            player.sendMessage("You advanced to the next tier!");
	            player.getDH().OneItemDialogue(advanceMessage, 4151);
				player.specAmount = 10.0;
				player.getItems().addSpecialBar(player.playerEquipment[player.playerWeapon]);
				player.hasWeaponGameWeapon = true;
	            break;
	        case 9:
	            player.getItems().wearItem(11730, 1, 3);//Saradomin Sword
	            player.sendMessage("You advanced to the next tier!");
	            player.getDH().OneItemDialogue(advanceMessage, 11730);
				player.specAmount = 10.0;
				player.getItems().addSpecialBar(player.playerEquipment[player.playerWeapon]);
				player.hasWeaponGameWeapon = true;
	            break; 	        
	        case 10:
	            player.getItems().removeItem(1, 3);
				player.hasWeaponGameWeapon = true;
				player.specAmount = 10.0;
				player.getItems().addSpecialBar(player.playerEquipment[player.playerWeapon]);
				player.sendMessage("You have won!");
	            handleWin(player);
	            break;
	        }
	    }
	
	  
	
}