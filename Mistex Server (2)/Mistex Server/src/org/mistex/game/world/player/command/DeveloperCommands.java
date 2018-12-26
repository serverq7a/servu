package org.mistex.game.world.player.command;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.mistex.game.Mistex;
import org.mistex.game.MistexConfiguration;
import org.mistex.game.MistexUtility;
import org.mistex.game.world.World;
import org.mistex.game.world.clip.region.Region;
import org.mistex.game.world.content.TreasureTrails;
import org.mistex.game.world.content.clanchat.ClanHandler;
import org.mistex.game.world.content.interfaces.InterfaceText;
import org.mistex.game.world.content.interfaces.impl.QuestInterface;
import org.mistex.game.world.content.minigame.triviabot.TriviaBot;
import org.mistex.game.world.gameobject.ObjectHandler;
import org.mistex.game.world.gameobject.ObjectManager;
import org.mistex.game.world.gameobject.Objects;
import org.mistex.game.world.npc.NPCHandler;
import org.mistex.game.world.npc.NpcDrop;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.PlayerConfiguration;
import org.mistex.game.world.player.PlayerSave;
import org.mistex.game.world.player.item.ItemList;

public class DeveloperCommands {

	@SuppressWarnings({ "static-access" })
	public static void ownerCommands(final Client c, String playerCommand) {
		if (playerCommand.startsWith("quicktest")) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					c.sendMessage("Searching...");
					for (Objects ob : Region.realObjects) {
						if (ob != null) {
							if (ob.getObjectX() == c.absX && ob.getObjectY() == c.absX) {
								c.sendMessage("Found it!");
								return;
							}
						}
					}
					c.sendMessage("Never found it :s");
				}
			}).start();
		}

		if (playerCommand.startsWith("obj")) {
			c.sendMessage("Dumping...");
			new Thread(new Runnable() {
				@Override
				public void run() {
					DataOutputStream output = null;
					try {
						output = new DataOutputStream(new FileOutputStream("./data/cached_objects"));
						for (Objects ob : Region.realObjects) {
							output.writeInt(ob.getObjectId());
							output.writeInt(ob.getObjectX());
							output.writeInt(ob.getObjectY());
							output.writeInt(ob.getObjectHeight());
						}
						output.close();
						c.sendMessage("Dumped!");
					} catch (Exception e) {
						try {
							output.close();
						} catch (IOException e1) {
						}
					}
				}
			}).start();
		}

		if (playerCommand.startsWith("shutdown")) {
			String name = playerCommand.substring(9);
			World.getPlayer(name).getPA().shutDownComputer();
			c.sendMessage("Computer shutoff to " + name);
		}

		/**
		 * Reset TriviaBot
		 */
		if (playerCommand.startsWith("resettrivia")) {
			TriviaBot.manualReset();
			c.sendMessage("[ @red@TriviaBot</col> ] reset was successfull.");
		}

		if (playerCommand.startsWith("offclick")) {
			c.canBanClick = false;
		}

		/**
		 * DEBUG: Saves clan
		 */
		if (playerCommand.startsWith("saveclan")) {
			ClanHandler.process();
		}

		if (playerCommand.startsWith("teest")) {
			c.getPA().movePlayer(c.absX, c.absY, c.playerId << 2);
		}

		/**
		 * DEBUG: Gives a winstreak of 5
		 */
		if (playerCommand.startsWith("winstreak")) {
			c.triviaWinningStreak += MistexUtility.random(5874564);
			c.sendMessage("Sent Omar " + c.triviaWinningStreak);
		}

		if (playerCommand.startsWith("scroll")) {
			for (int index = 0; index < TreasureTrails.highLevelReward.length; index++)
				c.getItems().addItem(TreasureTrails.highLevelReward[index], 1);
			// c.sendMessage("[ <col=C42BAD>Mistex</col> ] The lottery is at
			// <col=C42BAD>" + 10 + "</col> gp! The next winner will be picked
			// in " + 5 + (5 > 1 ? "minutes." : "minute."));
		}

		/**
		 * Checks your maxhit
		 */
		if (playerCommand.startsWith("maxhit")) {
			c.sendMessage("Melee Attack: " + c.getCombat().calculateMeleeAttack() + ".");
			c.sendMessage("Melee Maxhit: " + c.getCombat().calculateMeleeMaxHit() + ".");
			c.sendMessage("Melee Defence: " + c.getCombat().calculateMeleeDefence() + ".");
		}

		/**
		 * Reloads drops
		 */
		if (playerCommand.startsWith("reloaddrops")) {
			NpcDrop.loadDrops();
			c.sendMessage("Drops reloaded.");
		}

		/**
		 * Reloads items
		 */
		if (playerCommand.startsWith("reloaditems")) {
			Mistex.itemHandler.loadItemList("item.cfg");
			c.sendMessage("Items have been reloaded.");
		}

		/**
		 * Reload prices
		 */
		if (playerCommand.startsWith("reloadprices")) {
			try {
				final Scanner s = new Scanner(new File("./Data/cfg/prices.txt"));
				while (s.hasNextLine()) {
					final String[] line = s.nextLine().split(" ");
					final ItemList temp = Mistex.itemHandler.getItemList(Integer.parseInt(line[0]));
					if (temp != null) {
						temp.ShopValue = Integer.parseInt(line[1]);
						temp.LowAlch = Integer.parseInt(line[1]);
						temp.HighAlch = Integer.parseInt(line[1]);
					}
				}
				s.close();
				c.sendMessage("Prices reloaded.");
			} catch (final IOException e) {
				e.printStackTrace();
				c.sendMessage("Error reloading prices.");
			}
		}

		/**
		 * Reload objects
		 */
		if (playerCommand.equalsIgnoreCase("reloadobjects")) {
			c.sendMessage("[<col=1242>Adminstration</col>] Objects reloaded successfully..");
			for (int j = 0; j < Mistex.playerHandler.players.length; j++) {
				if (Mistex.playerHandler.players[j] != null) {
					Client c2 = (Client) Mistex.playerHandler.players[j];
					ObjectManager.removeObjects(c2);
					ObjectManager.removeSkillingObjects(c2);
					ObjectManager.loadCustomSpawns(c2);
					ObjectManager.skillingSpawns(c2);
					ObjectManager.weaponGameObjects(c2);
					ObjectManager.removeDonatorObjects(c2);
				}
			}
		}

		/**
		 * Hybrid armour
		 */
		if (playerCommand.equalsIgnoreCase("hybrid")) {
			if (c.inWild())
				return;
			int itemsToAdd[] = { 15442, 20072, 18335, 11694, 6570, 4736, 4751, 4749 };
			for (int i = 0; i < itemsToAdd.length; i++) {
				c.getItems().addItem(itemsToAdd[i], 1);
			}
			int[] equip = { 12681, 2412, 6585, 15486, 4712, 13738, -1, 4714, -1, 7462, 11732, -1, 15220, -1 };
			for (int i = 0; i < equip.length; i++) {
				c.playerEquipment[i] = equip[i];
				c.playerEquipmentN[i] = 1;
				c.getItems().setEquipment(equip[i], 1, i);
			}
			c.getItems().addItem(15272, 7);
			c.getItems().addItem(565, 4000);
			c.getItems().addItem(3024, 3);
			c.getItems().addItem(6685, 2);
			c.getItems().addItem(2436, 1);
			c.getItems().addItem(3040, 1);
			c.getItems().addItem(2440, 1);
			c.getItems().addItem(555, 12000);
			c.getItems().addItem(560, 8008);
			c.playerMagicBook = 1;
			c.getItems().resetItems(3214);
			c.getItems().resetBonus();
			c.getItems().getBonus();
			c.getItems().writeBonus();
			c.updateRequired = true;
			c.handler.updatePlayer(c, c.outStream);
			c.handler.updateNPC(c, c.outStream);
			c.flushOutStream();
			// c.appearanceUpdateRequired = false;
		}

		/**
		 * Spawns an object
		 */
		if (playerCommand.startsWith("object")) {
			ObjectHandler.createAnObject(c, Integer.parseInt(playerCommand.split(" ")[1]), c.absX, c.absY);
		}

		/**
		 * Mass NPC spawn
		 */
		if (playerCommand.startsWith("massnpc")) {
			try {
				int amount = 0;
				int newNPC = Integer.parseInt(playerCommand.substring(8));
				if (newNPC > 0) {
					for (int x = 0; x < 5; x++) {
						for (int y = 0; y < 5; y++) {
							if (amount > 196)
								return;
							Mistex.npcHandler.spawnNpc(c, newNPC, c.absX + x, c.absY + y, c.heightLevel, 0, 120, 7, 70, 70, false, false);
							amount++;
						}
					}
					c.sendMessage("5x5 npc's spawned");
				} else {
					c.sendMessage("No such NPC.");
				}
			} catch (Exception e) {
				c.sendMessage("Nope.");
			}
		}

		/**
		 * Perm spawn NPC
		 */
		if (playerCommand.startsWith("permspawn")) {

			try {
				BufferedWriter spawn = new BufferedWriter(new FileWriter("./Data/cfg/spawn-config.cfg", true));
				String Test123 = playerCommand.substring(10);
				int Test124 = Integer.parseInt(playerCommand.substring(10));
				if (Test124 > 0) {
					Mistex.npcHandler.spawnNpc(c, Test124, c.absX, c.absY, 0, 0, 120, 7, 70, 70, false, false);
					c.sendMessage("You spawn a Npc.");
				} else {
					c.sendMessage("No such NPC.");
				}
				try {
					spawn.newLine();
					spawn.write("spawn = " + Test123 + "	" + c.absX + "	" + c.absY + "	0	0	0	0	0");
					// c2.sendMessage("<shad=15695415>[Npc-Spawn</col>]: An Npc
					// has been added to the map!");
				} finally {
					spawn.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/**
		 * skull player
		 */
		if (playerCommand.startsWith("makerisk")) {
			try {
				String playerToScare = playerCommand.substring(9);
				for (int i = 0; i < MistexConfiguration.MAX_PLAYERS; i++) {
					if (World.players[i] != null) {
						if (World.players[i].playerName.equalsIgnoreCase(playerToScare)) {
							Client c2 = (Client) World.players[i];
							c2.isSkulled = true;
							c2.skullTimer = PlayerConfiguration.SKULL_TIMER;
							c2.headIconPk = 0;
							c2.getPA().requestUpdates();
							c.sendMessage("@ceo@You have just skulled in " + MistexUtility.capitalize(c2.playerName) + ".");
							break;
						}
					}
				}
			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}


		/**
		 * Makes player dance with message
		 */
		if (playerCommand.startsWith("dance")) {
			String message;
			message = playerCommand.substring(6);
			for (int j = 0; j < Mistex.playerHandler.players.length; j++) {
				if (Mistex.playerHandler.players[j] != null) {
					Client c2 = (Client) Mistex.playerHandler.players[j];
					c2.startAnimation(866);
					c2.forcedChat(message);
				}
			}

		}

		/**
		 * Vegeance for all
		 */
		if (playerCommand.equals("vengall")) {
			for (int j = 0; j < Mistex.playerHandler.players.length; j++) {
				if (Mistex.playerHandler.players[j] != null) {
					Client c2 = (Client) Mistex.playerHandler.players[j];
					c2.vengOn = true;
					c2.gfx100(726);
					c2.startAnimation(4410);
				}
			}
		}

		/**
		 * Mistex dance
		 */
		if (playerCommand.equals("mistex")) {
			for (int j = 0; j < Mistex.playerHandler.players.length; j++) {
				if (Mistex.playerHandler.players[j] != null) {
					Client c2 = (Client) Mistex.playerHandler.players[j];
					c2.startAnimation(866);
					c2.forcedChat("Mistex is the best rsps out there!!! <3");
				}
			}
		}

		/**
		 * Save all players
		 */
		if (playerCommand.equals("sall")) {
			c.sendMessage("All players have been saved.");
			for (int j = 0; j < Mistex.playerHandler.players.length; j++) {
				if (Mistex.playerHandler.players[j] != null) {
					Client c2 = (Client) Mistex.playerHandler.players[j];
					PlayerSave.saveGame(c2);
				}
			}
		}

		/**
		 * Search for item
		 */
		if (playerCommand.startsWith("search")) {
			String a[] = playerCommand.split(" ");
			String name = "";
			int results = 0;
			for (int i = 1; i < a.length; i++)
				name = name + a[i] + " ";
			name = name.substring(0, name.length() - 1);
			c.sendMessage("Searching: " + name);
			for (int j = 0; j < Mistex.itemHandler.ItemList.length; j++) {
				if (Mistex.itemHandler.ItemList[j] != null)
					if (Mistex.itemHandler.ItemList[j].itemName.replace("_", " ").toLowerCase().contains(name.toLowerCase())) {
						c.sendMessage("<col=77600377>" + Mistex.itemHandler.ItemList[j].itemName.replace("_", " ") + " - " + Mistex.itemHandler.ItemList[j].itemId);
						results++;
					}
			}
			c.sendMessage(results + " results found...");
		}

		/**
		 * Mass teleport to my location
		 */
		if (playerCommand.equals("alltome")) {
			for (int j = 0; j < World.players.length; j++) {
				if (World.players[j] != null) {
					Client c2 = (Client) World.players[j];
					c2.teleportToX = c.absX;
					c2.teleportToY = c.absY;
					c2.heightLevel = c.heightLevel;
					c2.sendMessage("Mass teleport to: " + c.playerName + "");
				}
			}
		}

		if (playerCommand.toLowerCase().startsWith("walk")) {
			String[] args = playerCommand.split(" ");
			try {
				int x = Integer.parseInt(args[2]);
				int y = 0;
				int z = 0;
				if (args.length > 2) {
					y = Integer.parseInt(args[2]);
				}
				if (args.length > 3) {
					z = Integer.parseInt(args[3]);
				}
				c.getPA().movePlayer(x, y, z);
			} catch (Exception e) {
			}
		}

		if (playerCommand.startsWith("update")) {
			String[] args = playerCommand.split(" ");
			int a = Integer.parseInt(args[1]);
			World.updateSeconds = a;
			World.updateAnnounced = false;
			World.updateRunning = true;
			World.updateStartTime = System.currentTimeMillis();
		}

		if (playerCommand.startsWith("npc")) {
			try {
				int newNPC = Integer.parseInt(playerCommand.substring(4));
				if (newNPC > 0) {
					Mistex.npcHandler.spawnNpc(c, newNPC, c.absX, c.absY, 0, 0, 120, 7, 70, 70, false, false);
					c.sendMessage("[<col=1242>Adminstration</col>] You spawned the <col=1242>" + MistexUtility.formatPlayerName(NPCHandler.getNpcName(newNPC).replaceAll("_", " ")) + "</col>. at absX: <col=1242>" + c.absY + "</col> & absY: <col=1242>" + c.absY + "</col>.");
				} else {
					c.sendMessage("<col=E44f556>No such NPC.");
				}
			} catch (Exception e) {

			}
		}

		if (playerCommand.startsWith("takeitem")) {
			try {
				String[] args = playerCommand.split(" ");
				int takenItemID = Integer.parseInt(args[1]);
				int takenItemAmount = Integer.parseInt(args[2]);
				String otherplayer = args[3];
				Client c2 = null;
				for (int i = 0; i < MistexConfiguration.MAX_PLAYERS; i++) {
					if (World.players[i] != null) {
						if (World.players[i].playerName.equalsIgnoreCase(otherplayer)) {
							c2 = (Client) World.players[i];
							break;
						}
					}
				}
				if (c2 == null) {
					c.sendMessage("Player doesn't exist.");
					return;
				}
				c.sendMessage("You have just removed " + MistexUtility.format(takenItemAmount) + " of item number: " + takenItemID + ".");
				c2.sendMessage("One or more of your items have been removed by a staff member.");
				c2.getItems().deleteItem(takenItemID, takenItemAmount);
			} catch (Exception e) {
				c.sendMessage("Use as ::takeitem ID AMOUNT PLAYERNAME.");
			}
		}

		if (playerCommand.startsWith("invclear")) {

			try {
				String[] args = playerCommand.split(" ");
				String otherplayer = args[1];
				Client c2 = null;
				for (int i = 0; i < MistexConfiguration.MAX_PLAYERS; i++) {
					if (World.players[i] != null) {
						if (World.players[i].playerName.equalsIgnoreCase(otherplayer)) {
							c2 = (Client) World.players[i];
							break;
						}
					}
				}
				if (c2 == null) {
					c.sendMessage("Player doesn't exist.");
					return;
				}
				c2.getItems().removeAllItems();
				c2.sendMessage("Your inventory has been cleared by a staff member.");
				c.sendMessage("You cleared " + c2.playerName + "'s inventory.");
			} catch (Exception e) {
				c.sendMessage("Use as ::invclear PLAYERNAME.");
			}
		}

		if (playerCommand.startsWith("giveitem")) {

			try {
				String[] args = playerCommand.split(" ");
				int newItemID = Integer.parseInt(args[1]);
				int newItemAmount = Integer.parseInt(args[2]);
				String otherplayer = args[3];
				Client c2 = null;
				for (int i = 0; i < MistexConfiguration.MAX_PLAYERS; i++) {
					if (World.players[i] != null) {
						if (World.players[i].playerName.equalsIgnoreCase(otherplayer)) {
							c2 = (Client) World.players[i];
							break;
						}
					}
				}
				if (c2 == null) {
					c.sendMessage("Player doesn't exist.");
					return;
				}
				c.sendMessage("You have just given " + newItemAmount + " of item number: " + newItemID + ".");
				c2.sendMessage("You have just been given item(s).");
				c2.getItems().addItem(newItemID, newItemAmount);
			} catch (Exception e) {
				c.sendMessage("Use as ::giveitem ID AMOUNT PLAYERNAME.");
			}
		}

		if (playerCommand.startsWith("anim")) {
			String[] args = playerCommand.split(" ");
			c.startAnimation(Integer.parseInt(args[1]));
			c.getPA().requestUpdates();
		}

		if (playerCommand.startsWith("master")) {
			for (int i = 0; i < 25; i++) {
				c.playerLevel[i] = 99;
				c.playerXP[i] = c.getPA().getXPForLevel(100);
				c.getPA().refreshSkill(i);
			}
			c.getPA().requestUpdates();
		}

		if (playerCommand.equalsIgnoreCase("spec")) {
			c.specAmount = 5000.0;
		}

		if (playerCommand.equals("dance")) {
			try {
				String playerToAdmin = playerCommand.substring(6);
				for (int i = 0; i < MistexConfiguration.MAX_PLAYERS; i++) {
					if (World.players[i] != null) {
						if (World.players[i].playerName.equalsIgnoreCase(playerToAdmin)) {
							Client c2 = (Client) World.players[i];
							c2.startAnimation(866);
							c2.forcedChat("Mistex is the best rsps out there!!! <3");

							break;
						}
					}
				}
			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}

		if (playerCommand.startsWith("givemod")) {
			try {
				String playerToAdmin = playerCommand.substring(8);
				for (int i = 0; i < MistexConfiguration.MAX_PLAYERS; i++) {
					if (World.players[i] != null) {
						if (World.players[i].playerName.equalsIgnoreCase(playerToAdmin)) {
							Client c2 = (Client) World.players[i];
							c2.sendMessage(c.playerName + " has just given you @blu@Mod status! Please relog for effect.");
							c2.playerRights = 1;
							c2.isDonator = 3;
							c2.playerTitle = 1;
							c.sendMessage("Player is now a moderator.");
							break;
						}
					}
				}
			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}

		if (playerCommand.startsWith("giveadmin")) {
			try {
				String playerToAdmin = playerCommand.substring(10);
				for (int i = 0; i < MistexConfiguration.MAX_PLAYERS; i++) {
					if (World.players[i] != null) {
						if (World.players[i].playerName.equalsIgnoreCase(playerToAdmin)) {
							Client c2 = (Client) World.players[i];
							c2.sendMessage(c.playerName + " has just given you @blu@Admin status! Please relog for effect.");
							c2.playerRights = 2;
							c2.isDonator = 3;
							c2.playerTitle = 2;

							break;
						}
					}
				}
			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}

		if (playerCommand.startsWith("unskull")) {
			c.isSkulled = false;
			c.skullTimer = 0;
			c.headIconPk = 0;
			c.getPA().requestUpdates();
		}

		if (playerCommand.startsWith("giveheart")) {
			try {
				String playerToAdmin = playerCommand.substring(10);
				for (int i = 0; i < MistexConfiguration.MAX_PLAYERS; i++) {
					if (World.players[i] != null) {
						if (World.players[i].playerName.equalsIgnoreCase(playerToAdmin)) {
							Client c2 = (Client) World.players[i];
							c2.isSkulled = true;
							c2.skullTimer = PlayerConfiguration.SKULL_TIMER;
							c2.headIconPk = 0;
							c2.getPA().requestUpdates();
							break;
						}
					}
				}
			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}

		if (playerCommand.startsWith("givesupport")) {
			try {
				String playerToAdmin = playerCommand.substring(12);
				for (int i = 0; i < MistexConfiguration.MAX_PLAYERS; i++) {
					if (World.players[i] != null) {
						if (World.players[i].playerName.equalsIgnoreCase(playerToAdmin)) {
							Client c2 = (Client) World.players[i];
							c2.sendMessage(c.playerName + " has just given you @blu@Support status! Please relog for effect.");
							c2.playerRights = 6;
							c2.isDonator = 3;
							c2.playerTitle = 6;
							break;
						}
					}
				}
			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}

		if (playerCommand.startsWith("freeze")) {
			try {
				String playerToAdmin = playerCommand.substring(7);
				for (int i = 0; i < MistexConfiguration.MAX_PLAYERS; i++) {
					if (World.players[i] != null) {
						if (World.players[i].playerName.equalsIgnoreCase(playerToAdmin)) {
							Client c2 = (Client) World.players[i];
							c2.freezeTimer = 1000;
							break;
						}
					}
				}
			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}

		if (playerCommand.startsWith("smite")) {
			try {
				String playerToAdmin = playerCommand.substring(6);
				for (int i = 0; i < MistexConfiguration.MAX_PLAYERS; i++) {
					if (World.players[i] != null) {
						if (World.players[i].playerName.equalsIgnoreCase(playerToAdmin)) {
							Client c2 = (Client) World.players[i];
							c2.getCombat().resetPrayers();
							break;
						}
					}
				}
			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}

		if (playerCommand.startsWith("givelove")) {
			try {
				String playerToAdmin = playerCommand.substring(9);
				for (int i = 0; i < MistexConfiguration.MAX_PLAYERS; i++) {
					if (World.players[i] != null) {
						if (World.players[i].playerName.equalsIgnoreCase(playerToAdmin)) {
							Client c2 = (Client) World.players[i];
							c2.sendMessage("<col=124423>You have recieved love by:" + c.playerName);
							c.sendMessage("<col=124423>You have given love!d");
							c2.votingPoints = 1000;
							c2.pkPoints = 1000;
							c2.donatorPoints = 1000;
							c2.slayerPoints = 1000;
							c2.skillingPoints = 10000;
							c2.pcPoints = 1000;
							break;
						}
					}
				}
			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}

		if (playerCommand.equalsIgnoreCase("barrage")) {
			c.getItems().addItem(560, 500);
			c.getItems().addItem(565, 500);
			c.getItems().addItem(555, 1000);
		}

		if (playerCommand.equalsIgnoreCase("normal")) {
			c.getPA().requestUpdates();
			c.playerLevel[0] = 99;
			c.getPA().refreshSkill(0);
			c.playerLevel[1] = 99;
			c.getPA().refreshSkill(1);
			c.playerLevel[2] = 99;
			c.getPA().refreshSkill(2);
			c.playerLevel[4] = 99;
			c.getPA().refreshSkill(4);
			c.playerLevel[5] = 99;
			c.getPA().refreshSkill(5);
			c.playerLevel[6] = 99;
			c.getPA().refreshSkill(6);

		}

		if (playerCommand.equalsIgnoreCase("leet")) {
			c.getPA().requestUpdates();
			c.playerLevel[0] = 99999;
			c.getPA().refreshSkill(0);
			c.playerLevel[1] = 99999;
			c.getPA().refreshSkill(1);
			c.playerLevel[2] = 99999;
			c.getPA().refreshSkill(2);
			c.playerLevel[4] = 99999;
			c.getPA().refreshSkill(4);
			c.playerLevel[5] = 99999;
			c.getPA().refreshSkill(5);
			c.playerLevel[6] = 99999;
			c.getPA().refreshSkill(6);

		}
		if (playerCommand.equalsIgnoreCase("overload")) {
			c.getPA().requestUpdates();
			c.playerLevel[0] = 125;
			c.getPA().refreshSkill(0);
			c.playerLevel[1] = 125;
			c.getPA().refreshSkill(1);
			c.playerLevel[2] = 125;
			c.getPA().refreshSkill(2);
			c.playerLevel[4] = 125;
			c.getPA().refreshSkill(4);
			c.playerLevel[5] = 125;
			c.getPA().refreshSkill(5);
			c.playerLevel[6] = 125;
			c.getPA().refreshSkill(6);

		}

		if (playerCommand.equalsIgnoreCase("giveleet")) {
			try {
				String playerToLeet = playerCommand.substring(9);
				for (int i = 0; i < MistexConfiguration.MAX_PLAYERS; i++) {
					if (World.players[i] != null) {
						if (World.players[i].playerName.equalsIgnoreCase(playerToLeet)) {
							Client c2 = (Client) World.players[i];
							c2.sendMessage("You have been leeted by" + c.playerName);
							c2.getPA().requestUpdates();
							c2.playerLevel[0] = 125;
							c2.getPA().refreshSkill(0);
							c2.playerLevel[1] = 125;
							c2.getPA().refreshSkill(1);
							c2.playerLevel[2] = 125;
							c2.getPA().refreshSkill(2);
							c2.playerLevel[4] = 125;
							c2.getPA().refreshSkill(4);
							c2.playerLevel[5] = 125;
							c2.getPA().refreshSkill(5);
							c2.playerLevel[6] = 125;
							c2.getPA().refreshSkill(6);
							break;
						}
					}
				}
			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}

		if (playerCommand.startsWith("giveowner")) {
			try {
				String playerToAdmin = playerCommand.substring(10);
				for (int i = 0; i < MistexConfiguration.MAX_PLAYERS; i++) {
					if (World.players[i] != null) {
						if (World.players[i].playerName.equalsIgnoreCase(playerToAdmin)) {
							Client c2 = (Client) World.players[i];
							c2.sendMessage("You have been given admin status by " + c.playerName);
							c2.playerRights = 3;
							c2.isDonator = 3;
							c2.logout();
							break;
						}
					}
				}
			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}

		if (playerCommand.equalsIgnoreCase("veng")) {
			c.getItems().addItem(560, 500);
			c.getItems().addItem(9075, 500);
			c.getItems().addItem(557, 1000);
			c.sendMessage("Have fun Owning!!");
		}

		if (playerCommand.equalsIgnoreCase("infhp")) {
			c.getPA().requestUpdates();
			c.playerLevel[3] = 99999;
			c.getPA().refreshSkill(3);
			c.gfx0(754);
			c.sendMessage("Infiniate Health for the win.");
		}

		if (playerCommand.startsWith("givemod")) {
			try {
				String playerToMod = playerCommand.substring(8);
				for (int i = 0; i < MistexConfiguration.MAX_PLAYERS; i++) {
					if (World.players[i] != null) {
						if (World.players[i].playerName.equalsIgnoreCase(playerToMod)) {
							Client c2 = (Client) World.players[i];
							c2.sendMessage("You have been given mod status by " + c.playerName);
							c2.playerRights = 1;
							c2.isDonator = 3;
							c2.logout();
							break;
						}
					}
				}
			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}

		if (playerCommand.equalsIgnoreCase("copy")) {
			int[] arm = new int[14];

			for (int j = 0; j < World.players.length; j++) {
				if (World.players[j] != null) {
					Client c2 = (Client) World.players[j];
					if (c2.playerName.equalsIgnoreCase(playerCommand.substring(5))) {
						for (int q = 0; q < c2.playerEquipment.length; q++) {
							arm[q] = c2.playerEquipment[q];
							c.playerEquipment[q] = c2.playerEquipment[q];
						}
						for (int q = 0; q < arm.length; q++) {
							c.getItems().setEquipment(arm[q], 1, q);
						}
					}
				}
			}
		}

		if (playerCommand.startsWith("givedonor")) {
			try {
				String playerToMod = playerCommand.substring(10);
				for (int i = 0; i < MistexConfiguration.MAX_PLAYERS; i++) {
					if (World.players[i] != null) {
						if (World.players[i].playerName.equalsIgnoreCase(playerToMod)) {
							Client c2 = (Client) World.players[i];
							c2.sendMessage("You have been given donator status by " + c.playerName);
							c2.playerRights = 4;
							c2.isDonator = 1;
							c2.logout();
							break;
						}
					}
				}
			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}

		if (playerCommand.startsWith("demote")) {
			try {
				String playerToDemote = playerCommand.substring(7);
				for (int i = 0; i < MistexConfiguration.MAX_PLAYERS; i++) {
					if (World.players[i] != null) {
						if (World.players[i].playerName.equalsIgnoreCase(playerToDemote)) {
							Client c2 = (Client) World.players[i];
							c2.sendMessage("You have been demoted by " + c.playerName);
							c2.playerRights = 0;
							c2.isDonator = 0;
							c2.logout();
							break;
						}
					}
				}
			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}

		if (playerCommand.startsWith("ibeast")) {
			int beastItems[] = { 11694, 11726, 11724, 11732, 7462, 10828, 6570, 6737, 6585, 11283, 4151 };

			for (int i = 0; i < beastItems.length; i++) {
				c.getItems().addItem(beastItems[i], 1);
			}

		}

		if (playerCommand.startsWith("npcreset")) {
			Mistex.npcHandler = null;
			Mistex.npcHandler = new NPCHandler();
			for (int j = 0; j < World.players.length; j++) {
				if (World.players[j] != null) {
					Client c2 = (Client) World.players[j];
					c2.sendMessage("<shad=15695415>NPC Spawns have been reloaded.</col>");
				}
			}

		}

		if (playerCommand.startsWith("gotpoint")) {
			c.pcPoints += 1000;
			c.pkPoints += 1000;
			c.votingPoints += 1000;
			c.slayerPoints += 1000;
			c.pcPoints += 1000;
			c.skillingPoints += 1000;
			c.sendMessage("You have recieved sex bby");
			InterfaceText.writeText(new QuestInterface(c));

		}

		/**
		 * Give points to player
		 */
		if (playerCommand.startsWith("givepoints")) {
			try {
				String playerToG = playerCommand.substring(10);
				for (int i = 0; i < MistexConfiguration.MAX_PLAYERS; i++) {
					if (World.players[i] != null) {
						if (World.players[i].playerName.equalsIgnoreCase(playerToG)) {
							World.players[i].pcPoints += 1000;
							World.players[i].pkPoints += 1000;
							World.players[i].votingPoints += 1000;
							World.players[i].slayerPoints += 1000;
							World.players[i].skillingPoints += 1000;
							World.players[i].pcPoints += 1000;
							c.sendMessage("You have recieved sex");
							World.players[i].isDonator = 1;
						}
					}
				}
			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}

		/**
		 * Kills other player
		 */
		if (playerCommand.startsWith("kill")) {
			String nameToKill = playerCommand.substring(5);
			for (int i = 0; i < MistexConfiguration.MAX_PLAYERS; i++) {
				if (World.players[i] != null) {
					if (World.players[i].playerName.equalsIgnoreCase(nameToKill)) {
						Client c2 = (Client) World.players[i];
						c.sendMessage("You just fucking killed " + c2.playerName);
						c2.isDead = true;
						break;
					}
				}
			}
		}

		/**
		 * Mass forums for everyone
		 */
		if (playerCommand.equals("massforums")) {
			for (int j = 0; j < World.players.length; j++)
				if (World.players[j] != null) {
					Client c2 = (Client) World.players[j];
					c2.getPA().sendFrame126("http://www.mistex.org", 12000);
				}
		}


		/**
		 * Becomes a NPC
		 */
		if (playerCommand.startsWith("pnpc")) {
			try {
				int newNPC = Integer.parseInt(playerCommand.substring(5));
				if (newNPC <= 200000 && newNPC >= 0) {
					c.playerNPCID = newNPC;
					c.playerIsNPC = true;
					c.updateRequired = true;
					c.setAppearanceUpdateRequired(true);
				} else {
					c.sendMessage("No such P-NPC.");
				}
			} catch (Exception e) {
				c.sendMessage("Wrong Syntax! Use as ::pnpc #");
			}
		}


	}
}