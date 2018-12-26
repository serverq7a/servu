package org.mistex.game.world.player;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;

import org.mistex.game.Mistex;
import org.mistex.game.MistexConfiguration;
import org.mistex.game.MistexUtility;
import org.mistex.game.world.PathFinder;
import org.mistex.game.world.World;
import org.mistex.game.world.clip.region.Region;
import org.mistex.game.world.content.Boxing;
import org.mistex.game.world.content.DoubleExpHandler;
import org.mistex.game.world.content.SkillLead;
import org.mistex.game.world.content.SkillLead.SkillLeaderData;
import org.mistex.game.world.content.Teles;
import org.mistex.game.world.content.achievement.AchievementHandler;
import org.mistex.game.world.content.achievement.AchievementList;
import org.mistex.game.world.content.interfaces.InterfaceText;
import org.mistex.game.world.content.interfaces.impl.AchievementTab;
import org.mistex.game.world.content.interfaces.impl.InformationTab;
import org.mistex.game.world.content.minigame.fightpits.FightPits;
import org.mistex.game.world.content.minigame.weapongame.WeaponGame;
import org.mistex.game.world.content.pvp.PlayerKilling;
import org.mistex.game.world.content.skill.magic.SuperHeat;
import org.mistex.game.world.event.CycleEvent;
import org.mistex.game.world.event.CycleEventContainer;
import org.mistex.game.world.event.CycleEventHandler;
import org.mistex.game.world.npc.NPC;
import org.mistex.game.world.npc.NPCHandler;
import org.mistex.game.world.player.item.Item;

@SuppressWarnings({ "static-access", "unused" })
public class PlayerAssistant {

	private Client c;

	public PlayerAssistant(Client Client) {
		this.c = Client;
	}

	public void changeToSidebar(int i1) {
		c.outStream.createFrame(106);
		c.outStream.writeByteC(i1);
	}
	
	public int getSkillLevel(int skillId) {
		return getLevelForXP(c.playerXP[skillId]);
	}
	
	public int getHighestLevel(boolean findSkillId) {
		int highest = 0;
		int skillId = -1;
		for (int i = 0; i < 25; i++) {
			if(getSkillLevel(i) > highest) {
				highest = getSkillLevel(i);
				skillId = i;
			}			
		}
		return findSkillId ? skillId : highest;
	}
	
	public int getTotalLevel() {
		if(c == null)
			return 0;
		int totalLevel = (getLevelForXP(c.playerXP[0])
				+ getLevelForXP(c.playerXP[1]) + getLevelForXP(c.playerXP[2])
				+ getLevelForXP(c.playerXP[3]) + getLevelForXP(c.playerXP[4])
				+ getLevelForXP(c.playerXP[5]) + getLevelForXP(c.playerXP[6])
				+ getLevelForXP(c.playerXP[7]) + getLevelForXP(c.playerXP[8])
				+ getLevelForXP(c.playerXP[9]) + getLevelForXP(c.playerXP[10])
				+ getLevelForXP(c.playerXP[11]) + getLevelForXP(c.playerXP[12])
				+ getLevelForXP(c.playerXP[13]) + getLevelForXP(c.playerXP[14])
				+ getLevelForXP(c.playerXP[15]) + getLevelForXP(c.playerXP[16])
				+ getLevelForXP(c.playerXP[17]) + getLevelForXP(c.playerXP[18])
				+ getLevelForXP(c.playerXP[19]) + getLevelForXP(c.playerXP[20])
				+ getLevelForXP(c.playerXP[21]) + getLevelForXP(c.playerXP[22])
				+ getLevelForXP(c.playerXP[23]) + getLevelForXP(c.playerXP[24]));
		return totalLevel;
	}
	
	public void writeCommandLog(String command) {
	checkDateAndTime();
	String filePath = "./Data/logs/CommandLog/playerommands.txt";
	String filePath1 = "./Data/logs/CommandLog/staffommands.txt";
	String filePath2 = "./Data/logs/CommandLog/ownerommands.txt";
	String filePath3 = "./Data/logs/CommandLog/yellommands.txt";
	BufferedWriter bw = null;
	
	try 
	{				
	if ((c.playerRights > 0 && c.playerRights < 3) && c.isyelling == false) {
		bw = new BufferedWriter(new FileWriter(filePath1, true));
	} else if (c.playerRights == 3 && c.isyelling == false) {
		bw = new BufferedWriter(new FileWriter(filePath2, true));
	} else if (c.isyelling == true) {
		bw = new BufferedWriter(new FileWriter(filePath3, true));
		c.isyelling = false;
	} else if(c.playerRights == 0 && c.isyelling == false){
		bw = new BufferedWriter(new FileWriter(filePath, true));
	}
		bw.write("["+c.playerName+"]: "+"["+c.date+"]"+" - "+"["+c.connectedFrom+"] "+"::"+command);
		bw.newLine();
		bw.flush();
	} 
	catch (IOException ioe) 
	{
		ioe.printStackTrace();
	} 
	finally 
	{
		if (bw != null)
		{
			try 
			{
				bw.close();
			} 
			catch (IOException ioe2) 
			{
			}
		}
	}
}
	public void createPlayersProjectile(int x, int y, int offX, int offY,int angle, int speed, int gfxMoving, int startHeight, int endHeight, int lockon, int time, int h) {
		for (int i = 0; i < MistexConfiguration.MAX_PLAYERS; i++) {
			Player p = World.players[i];
			if (p != null) {
				if (c.heightLevel != h)
					continue;
				Client person = c;
				if (person != null) {
					if (c.heightLevel != h)
						continue;
					if (person.getOutStream() != null) {
						if (person.distanceToPoint(x, y) <= 25) {
							person.getPA().createProjectile(x, y, offX, offY,angle, speed, gfxMoving, startHeight, endHeight, lockon, time);
						}
					}
				}
			}
		}
	}
	
	public void createPlayersProjectile2(int x, int y, int offX, int offY, int angle, int speed, int gfxMoving, int startHeight, int endHeight, int lockon, int time, int slope, int h) {
		for (int i = 0; i < MistexConfiguration.MAX_PLAYERS; i++) {
			Player p = World.players[i];
			if (p != null) {
				if (c.heightLevel != h)
					continue;
				Client person = c;
				if (person != null) {
					if (c.heightLevel != h)
						continue;
					if (person.getOutStream() != null) {
						if (person.distanceToPoint(x, y) <= 25) {
							person.getPA().createProjectile2(x, y, offX, offY,angle, speed, gfxMoving, startHeight,endHeight, lockon, time, slope);
						}
					}
				}
			}
		}
	}
	
	public void Frame34(int frame, int item, int slot, int amount) {
		if (c.getOutStream() != null && c != null) {
			c.outStream.createFrameVarSizeWord(34);
			c.outStream.writeWord(frame);
			c.outStream.writeByte(slot);
			c.outStream.writeWord(item + 1);
			c.outStream.writeByte(255);
			c.outStream.writeDWord(amount);
			c.outStream.endFrameVarSizeWord();
		}
	}
	
	public boolean isMaxed() {
		int skill = 0;
		for (int i = 0; i < c.playerLevel.length; i++) {
			if (getLevelForXP(c.playerXP[i]) == 99) {
				skill++;
			}
		}
		return (skill == c.playerLevel.length);
	}

	public boolean handleGenieLamp(int actionButtonId) {
		int[][] GENIE_INFO = { { 10252, 0 }, { 10253, 2 }, { 10254, 4 }, { 10255, 6 }, { 11000, 1 }, { 11001, 3 }, { 11002, 5 }, { 11003, 16 }, { 11004, 15 }, { 11005, 17 }, { 11006, 12 }, { 11007, 20 }, { 47002, 18 }, { 54090, 19 }, { 11008, 14 }, { 11009, 13 }, { 11010, 10 }, { 11011, 7 }, { 11012, 11 }, { 11013, 8 }, { 11014, 9 } };
		for (int i = 0; i < GENIE_INFO.length; i++) {
			if (actionButtonId == GENIE_INFO[i][0]) {
				if (System.currentTimeMillis() - c.lampDelay > 2500) {
					if (c.getItems().playerHasItem(2528, 1)) {
						c.lampDelay = System.currentTimeMillis();
						c.getItems().deleteItem(2528, 1);
						c.getPA().addSkillXP(15000, GENIE_INFO[i][1]);
						c.getPA().closeAllWindows();
						return true;
					}
				}
			}
		}
		return false;
	}

	public static String[] skillName = { "Attack", "Defence", "Strength", "Hitpoints", "Ranged", "Prayer", "Magic", "Cooking", "Woodcutting", "Fletching", "Fishing", "Firemaking", "Crafting", "Smithing", "Mining", "Herblore", "Agility", "Thieving", "Slayer", "Farming", "Runecrafting", "Construction", "Hunter", "Summoning", "Dungeoneering" };

	public static String getSkillName(int i) {
		return skillName[i];
	}

	public double getAgilityRunRestore() {
		return 2260 - (c.playerLevel[16] * 10);
	}

	public void setConfig(int id, int state) {
		synchronized (c) {
			if (c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(36);
				c.getOutStream().writeWordBigEndian(id);
				c.getOutStream().writeByte(state);
				c.flushOutStream();
			}
		}
	}

	public void sendSkillXP(int skillID, int xp) {
		if (c.getOutStream() != null && c != null) {
			c.counterxp += xp;
			c.outStream.createFrame(124);
			c.outStream.writeByte(skillID);
			c.outStream.writeDWord(xp);
			c.outStream.writeDWord(c.counterxp);
			c.flushOutStream();
		}
	}

	public void resetIcons() {
		c.setSidebarInterface(0, 2423); // Combat
		c.setSidebarInterface(1, 31000); // Achievement
		c.setSidebarInterface(2, 3917); // Skilltab
		c.setSidebarInterface(3, 3213); // Inventory
		c.setSidebarInterface(4, 1644); // Equipment
		c.setSidebarInterface(5, 5608); // Prayer
		c.magicBooks();
		c.setSidebarInterface(7, 18128); // Clanchat
		c.setSidebarInterface(8, 5065); // Friends
		c.setSidebarInterface(9, 5715); // ignore
		c.setSidebarInterface(10, 2449); // Logout
		c.setSidebarInterface(11, 904); // Wrench
		c.setSidebarInterface(12, 147); // Emotes
		c.setSidebarInterface(13, 29000); // Player Profiler
		c.setSidebarInterface(14, 37000); // Quest
		c.setSidebarInterface(15, -1); // Summoning
		c.setSidebarInterface(16, 45500); // Settings
	}

	public void clearIcons() {
		c.setSidebarInterface(0, -1); // Combat
		c.setSidebarInterface(1, -1); // Achievement
		c.setSidebarInterface(2, -1); // Skilltab
		c.setSidebarInterface(3, -1); // Inventory
		c.setSidebarInterface(4, -1); // Equipment
		c.setSidebarInterface(5, -1); // Prayer
		c.setSidebarInterface(6, -1); // Magic
		c.setSidebarInterface(7, -1); // Clanchat
		c.setSidebarInterface(8, -1); // Friends
		c.setSidebarInterface(9, -1); // ignore
		c.setSidebarInterface(10, -1); // Logout
		c.setSidebarInterface(11, -1); // Wrench
		c.setSidebarInterface(12, -1); // Emotes
		c.setSidebarInterface(13, -1); // Player Profiler
		c.setSidebarInterface(14, -1); // Quest
		c.setSidebarInterface(15, -1); // Summoning
		c.setSidebarInterface(16, -1); // Settings
	}

	public void handleWeaponStyle() {
		if (c.fightMode == 0) {
			c.getPA().sendFrame36(43, c.fightMode);
		} else if (c.fightMode == 1) {
			c.getPA().sendFrame36(43, 3);
		} else if (c.fightMode == 2) {
			c.getPA().sendFrame36(43, 1);
		} else if (c.fightMode == 3) {
			c.getPA().sendFrame36(43, 2);
		}
	}

	public void sendFrame20(int id, int state) {
		synchronized (c) {
			if (c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(36);
				c.getOutStream().writeWordBigEndian(id);
				c.getOutStream().writeByte(state);
				c.flushOutStream();
			}
		}
	}

	public void barTut(Client c, int p, int a) {
		sendFrame20(406, a);
		sendFrame171(1, 12224);
		sendFrame171(1, 12225);
		sendFrame171(1, 12226);
		sendFrame171(1, 12227);
		sendFrame126("" + (p) + "%", 12224);
		walkableInterface(8680);
	}

	public void sendConfig(int id, int value) {
		if (value < 128) {
			sendFrame36(id, value);
		} else {
			sendFrame87(id, value);
		}
	}

	public boolean hasItem() {
		for (int i = 0; i < c.playerEquipment.length; i++) {
			if (c.playerEquipment[i] != -1)
				return true;
		}
		for (int i = 0; i < c.playerItems.length; i++) {
			if (c.playerItems[i] != 0)
				return true;
		}
		return false;
	}

	public void destroyItem(int itemId) {
		String itemName = c.getItems().getItemName(itemId);
		c.getItems().deleteItem(itemId, 1);
		c.sendMessage("Your " + itemName + " vanishes as you drop it on the ground.");
		c.destroyItem = 0;
	}

	public void checkIfComepletedAll() {
		if (c.achievementsCompleted == c.totalAchievements) {
			c.currentlyCompletedAll = true;
			AchievementHandler.activateAchievement(c, AchievementList.MYSTIC);
		}
	}

	public String getTotalAmount(Client c, int j) {
		if (j >= 10000 && j < 10000000) {
			return j / 1000 + "K";
		} else if (j >= 10000000 && j <= 2147483647) {
			return j / 1000000 + "M";
		} else {
			return "" + j + " gp";
		}
	}

	public double round(double valueToRound, int numberOfDecimalPlaces) {
		double multipicationFactor = Math.pow(10, numberOfDecimalPlaces);
		double interestedInZeroDPs = valueToRound * multipicationFactor;
		return Math.round(interestedInZeroDPs) / multipicationFactor;
	}

	public static void yell(String string) {
		for (int j = 0; j < World.players.length; j++) {
			if (World.players[j] != null) {
				Client c2 = (Client) World.players[j];
				c2.sendMessage(string);
			}
		}
	}

	public void fade(final int x, final int y, final int height) {
		if (System.currentTimeMillis() - c.lastAction > 4000) {
			c.lastAction = System.currentTimeMillis();
			c.resetWalkingQueue();
			CycleEventHandler.getSingleton().addEvent(this, new CycleEvent() {
				int tStage = 7;

				public void execute(CycleEventContainer container) {
					if (tStage == 7) {
						showInterface(18679);
					}
					if (tStage == 5) {
						// movePlayer(x, y, height);
						c.updateRequired = true;
						c.appearanceUpdateRequired = true;
					}
					if (tStage == 4) {
						showInterface(18460);
						// movePlayer(1, 1, 0);
					}
					if (tStage == 1) {
						container.stop();
						return;
					}
					if (tStage > 0) {
						tStage--;
					}
				}

				public void stop() {
					closeAllWindows();
					tStage = 0;
				}
			}, 1);
		}
	}

	public void chaosElementalEffect(Client c, int i) {
		switch (i) {
		case 0: // TELEPORT
			c.teleportToX = c.absX + MistexUtility.random(10);
			c.teleportToY = c.absY + MistexUtility.random(10);
			c.getCombat().resetPlayerAttack();
			break;
		case 1: // Disarming
			if (c.getItems().freeSlots() > 0) {
				int slot = MistexUtility.random(11);
				int item = c.playerEquipment[slot];
				c.getItems().removeItem(item, slot);
			}
			break;
		}
	}
	
	public String getYellRank() {
		switch (c.playerRights) {
		case 0:
			return "[Player]";
		case 1:
			return "[ @blu@Moderator</col> ] <img=0>";
		case 2:
			return "[ @yel@Administrator</col> ] <img=1>";
		case 3:
			return handleOwnerYell();
		case 4:
			return "[ <col=F20A0A>" + c.yellTag + "</col> ] <img=3>";
		case 5:
			return "[ <col=1880D6>" + c.yellTag + "</col> ] <img=4>";
		case 6:
			return "[ <col=9F09D6>" + c.yellTag + "</col> ] <img=5>";
		case 7:
			return "[ @whi@Support</col> ] <img=6>";
		}
		return "";
	}
	
	public String handleOwnerYell() {
		if (c.playerName.equalsIgnoreCase("play boy")) {
			return "[ <shade=1><col=F76323>"+c.yellTag+"</col></shade> ] <img=2>";
		}	
		return "[ <shade=1><col=078526>Developer</col></shade> ] <img=2>";
		
	}

	public void destroyInterface(int itemId) {
		String itemName = c.getItems().getItemName(itemId);
		String[][] info = { { "Are you sure you want to destroy this item?", "14174" }, { "Yes.", "14175" }, { "No.", "14176" }, { "", "14177" }, { "Put info here", "14182" }, { "More info here", "14183" }, { itemName, "14184" } };// make
																																																										// some
																																																										// kind
																																																										// of
																																																										// c.getItemInfo
		sendFrame34(itemId, 0, 14171, 1);
		for (int i = 0; i < info.length; i++)
			sendFrame126(info[i][0], Integer.parseInt(info[i][1]));
		c.destroyItem = itemId;
		sendFrame164(14170);
	}

	public void specialTeleport(int x, int y, int height, int npcId) {
		if (System.currentTimeMillis() - c.teleBlockDelay < c.teleBlockLength) {
			c.sendMessage("You are teleblocked and can't teleport.");
			return;
		}
		if (!c.isDead && c.teleTimer == 0) {
			c.stopMovement();
			removeAllWindows();
			c.teleX = x;
			c.teleY = y;
			c.npcIndex = 0;
			c.playerIndex = 0;
			c.faceUpdate(0);
			c.teleHeight = height;
			c.startAnimation(1816);
			c.teleTimer = 11;
			c.teleGfx = 342;
			c.teleEndAnimation = 65535;
			NPC n = Mistex.npcHandler.npcs[npcId];
			c.getPA().sendFrame99(0);
			c.getPA().walkableInterface(-1);
			c.getPA().showOption(3, 0, "Null", 1);
			n.gfx0(343);
			// n.requestAnimation(1818,0);
			n.facePlayer(c.playerId);
		}
	}

	public int getWearingAmount2() {
		int totalCash = 0;
		for (int i = 0; i < c.playerEquipment.length; i++) {
			if (c.playerEquipment[i] > 0) {
				totalCash += getItemValue(c.playerEquipment[i]);
			}
		}
		for (int i = 0; i < c.playerItems.length; i++) {
			if (c.playerItems[i] > 0) {
				totalCash += getItemValue(c.playerItems[i]);
			}
		}
		return totalCash;
	}

	public int getItemValue(int ItemID) {
		int shopValue = 0;
		for (int i = 0; i < MistexConfiguration.ITEM_LIMIT; i++) {
			if (Mistex.itemHandler.ItemList[i] != null) {
				if (Mistex.itemHandler.ItemList[i].itemId == ItemID) {
					shopValue = (int) Mistex.itemHandler.ItemList[i].ShopValue;
				}
			}
		}
		return shopValue;
	}

	public String checkTimeOfDay() {
		Calendar cal = new GregorianCalendar();
		int TIME_OF_DAY = cal.get(Calendar.AM_PM);
		if (TIME_OF_DAY > 0)
			return "PM";
		else
			return "AM";
	}

	public void checkDateAndTime() {
		Calendar cal = new GregorianCalendar();

		int YEAR = cal.get(Calendar.YEAR);
		int MONTH = cal.get(Calendar.MONTH) + 1;
		int DAY = cal.get(Calendar.DAY_OF_MONTH);
		int HOUR = cal.get(Calendar.HOUR_OF_DAY);
		int MIN = cal.get(Calendar.MINUTE);
		int SECOND = cal.get(Calendar.SECOND);

		String day = "";
		String month = "";
		String hour = "";
		String minute = "";
		String second = "";

		if (DAY < 10)
			day = "0" + DAY;
		else
			day = "" + DAY;
		if (MONTH < 10)
			month = "0" + MONTH;
		else
			month = "" + MONTH;
		if (HOUR < 10)
			hour = "0" + HOUR;
		else
			hour = "" + HOUR;
		if (MIN < 10)
			minute = "0" + MIN;
		else
			minute = "" + MIN;
		if (SECOND < 10)
			second = "0" + SECOND;
		else
			second = "" + SECOND;

		c.date = day + "/" + month + "/" + YEAR;
		c.currentTime = hour + ":" + minute + ":" + second;
	}

	Properties p = new Properties();

	public void sendFrame34a(int frame, int item, int slot, int amount) {
		if (c.outStream != null) {
			c.outStream.createFrameVarSizeWord(34);
			c.outStream.writeWord(frame);
			c.outStream.writeByte(slot);
			c.outStream.writeWord(item + 1);
			c.outStream.writeByte(255);
			c.outStream.writeDWord(amount);
			c.outStream.endFrameVarSizeWord();
		}
	}

	public void multiWay(int i1) {
		synchronized (c) {
			c.outStream.createFrame(61);
			c.outStream.writeByte(i1);
			c.updateRequired = true;
			c.setAppearanceUpdateRequired(true);
		}
	}

	public void clearClanChat() {
		sendFrame126("Talking in: ", 18139);
		sendFrame126("Owner: ", 18140);
		for (int id = 18144; id <= 18154; id++)
			updateClanRank("", id, 0);
	}

	public void clearClanChatv2() {
		if (c != null)
			c.getPA().sendFrame126("Talking in: @whi@None", 18139);
		c.getPA().sendFrame126("Owner: @whi@None", 18140);
		for (int j = 18144; j < 18244; j++) {
			c.getPA().sendFrame126("", j);
		}
	}

	public void resetAutocast() {
		c.autocastId = 0;
		c.autocasting = false;
		c.getPA().sendFrame36(108, 0);
	}

	public static void sendFrame126(Client c, String s, int id) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrameVarSizeWord(126);
			c.getOutStream().writeString(s);
			c.getOutStream().writeWordA(id);
			c.getOutStream().endFrameVarSizeWord();
			c.flushOutStream();
		}
	}

	public void sendFrame126(String s, int id) {
		if (!c.checkPacket126Update(s, id)) {
			int bytesSaved = (s.length() + 4);
			return;
		}
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrameVarSizeWord(126);
			c.getOutStream().writeString(s);
			c.getOutStream().writeWordA(id);
			c.getOutStream().endFrameVarSizeWord();
			c.flushOutStream();
		}
	}

	public void updateClanRank(String name, int id, int rank) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrameVarSizeWord(140);
			c.getOutStream().writeString(name);
			c.getOutStream().writeWordA(id);
			c.getOutStream().writeByte(rank);
			c.getOutStream().endFrameVarSizeWord();
			c.flushOutStream();
		}
	}

	public void sendLink(String s) {
		synchronized (c) {
			if (c.getOutStream() != null && c != null) {
				c.getOutStream().createFrameVarSizeWord(187);
				c.getOutStream().writeString(s);
			}
		}
	}

	public void setSkillLevel(int skillNum, int currentLevel, int XP) {
		synchronized (c) {
			if (c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(134);
				c.getOutStream().writeByte(skillNum);
				c.getOutStream().writeDWord_v1(XP);
				c.getOutStream().writeByte(currentLevel);
				c.flushOutStream();
			}
		}
	}

	public void sendFrame106(int sideIcon) {
		synchronized (c) {
			if (c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(106);
				c.getOutStream().writeByteC(sideIcon);
				c.flushOutStream();
				requestUpdates();
			}
		}
	}

	public void sendFrame107() {
		synchronized (c) {
			if (c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(107);
				c.flushOutStream();
			}
		}
	}

	public void sendFrame36(int id, int state) {
		synchronized (c) {
			if (c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(36);
				c.getOutStream().writeWordBigEndian(id);
				c.getOutStream().writeByte(state);
				c.flushOutStream();
			}
		}
	}

	public void sendFrame185(int Frame) {
		synchronized (c) {
			if (c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(185);
				c.getOutStream().writeWordBigEndianA(Frame);
			}
		}
	}

	public void showInterface(int interfaceid) {
		if (c.inTrade || c.inDuel) {
			return;
		}
		synchronized (c) {
			if (c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(97);
				c.getOutStream().writeWord(interfaceid);
				c.flushOutStream();
			}
		}
	}

	public void sendFrame248(int MainFrame, int SubFrame) {
		synchronized (c) {
			if (c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(248);
				c.getOutStream().writeWordA(MainFrame);
				c.getOutStream().writeWord(SubFrame);
				c.flushOutStream();
			}
		}
	}

	public void sendFrame246(int MainFrame, int SubFrame, int SubFrame2) {
		synchronized (c) {
			if (c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(246);
				c.getOutStream().writeWordBigEndian(MainFrame);
				c.getOutStream().writeWord(SubFrame);
				c.getOutStream().writeWord(SubFrame2);
				c.flushOutStream();
			}
		}
	}

	public void sendFrame171(int MainFrame, int SubFrame) {
		synchronized (c) {
			if (c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(171);
				c.getOutStream().writeByte(MainFrame);
				c.getOutStream().writeWord(SubFrame);
				c.flushOutStream();
			}
		}
	}

	public void sendFrame200(int MainFrame, int SubFrame) {
		synchronized (c) {
			if (c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(200);
				c.getOutStream().writeWord(MainFrame);
				c.getOutStream().writeWord(SubFrame);
				c.flushOutStream();
			}
		}
	}

	public void sendFrame70(int i, int o, int id) {
		synchronized (c) {
			if (c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(70);
				c.getOutStream().writeWord(i);
				c.getOutStream().writeWordBigEndian(o);
				c.getOutStream().writeWordBigEndian(id);
				c.flushOutStream();
			}
		}
	}

	public void sendFrame75(int MainFrame, int SubFrame) {
		synchronized (c) {
			if (c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(75);
				c.getOutStream().writeWordBigEndianA(MainFrame);
				c.getOutStream().writeWordBigEndianA(SubFrame);
				c.flushOutStream();
			}
		}
	}

	public void sendFrame164(int Frame) {
		synchronized (c) {
			if (c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(164);
				c.getOutStream().writeWordBigEndian_dup(Frame);
				c.flushOutStream();
			}
		}
	}

	public void setPrivateMessaging(int i) { // friends and ignore list status
		synchronized (c) {
			if (c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(221);
				c.getOutStream().writeByte(i);
				c.flushOutStream();
			}
		}
	}

	public void setChatOptions(int publicChat, int privateChat, int tradeBlock) {
		synchronized (c) {
			if (c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(206);
				c.getOutStream().writeByte(publicChat);
				c.getOutStream().writeByte(privateChat);
				c.getOutStream().writeByte(tradeBlock);
				c.flushOutStream();
			}
		}
	}

	public void sendFrame87(int id, int state) {
		synchronized (c) {
			if (c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(87);
				c.getOutStream().writeWordBigEndian_dup(id);
				c.getOutStream().writeDWord_v1(state);
				c.flushOutStream();
			}
		}
	}

	public void sendPM(long name, int rights, byte[] chatmessage, int messagesize) {
		synchronized (c) {
			if (c.getOutStream() != null && c != null) {
				c.getOutStream().createFrameVarSize(196);
				c.getOutStream().writeQWord(name);
				c.getOutStream().writeDWord(c.lastChatId++);
				c.getOutStream().writeByte(rights);
				c.getOutStream().writeBytes(chatmessage, messagesize, 0);
				c.getOutStream().endFrameVarSize();
				c.flushOutStream();
				String chatmessagegot = MistexUtility.textUnpack(chatmessage, messagesize);
				String target = MistexUtility.longToPlayerName(name);
			}
		}
	}

	public void createPlayerHints(int type, int id) {
		synchronized (c) {
			if (c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(254);
				c.getOutStream().writeByte(type);
				c.getOutStream().writeWord(id);
				c.getOutStream().write3Byte(0);
				c.flushOutStream();
			}
		}
	}

	public void createPlayerHints2(int type, int String) {
		synchronized (c) {
			if (c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(254);
				c.getOutStream().writeByte(type);
				c.getOutStream().writeWord(String);
				c.getOutStream().write3Byte(0);
				c.flushOutStream();
			}
		}
	}

	public void createObjectHints(int x, int y, int height, int pos) {
		synchronized (c) {
			if (c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(254);
				c.getOutStream().writeByte(pos);
				c.getOutStream().writeWord(x);
				c.getOutStream().writeWord(y);
				c.getOutStream().writeByte(height);
				c.flushOutStream();
			}
		}
	}

	public void loadPM(long playerName, int world) {
		synchronized (c) {
			if (c.getOutStream() != null && c != null) {
				if (world != 0) {
					world += 9;
				} else if (!MistexConfiguration.WORLD_LIST_FIX) {
					world += 1;
				}
				c.getOutStream().createFrame(50);
				c.getOutStream().writeQWord(playerName);
				c.getOutStream().writeByte(world);
				c.flushOutStream();
			}
		}
	}

	public void removeAllWindows() {
		synchronized (c) {
			if (c.getOutStream() != null && c != null) {
				c.getPA().resetVariables();
				c.getOutStream().createFrame(219);
				c.flushOutStream();
			}
		}
	}

	public void closeAllWindows() {
		synchronized (c) {
			if (c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(219);
				c.flushOutStream();
				c.isBanking = false;
			}
		}
	}

	public void sendFrame34(int id, int slot, int column, int amount) {
		synchronized (c) {
			if (c.getOutStream() != null && c != null) {
				c.outStream.createFrameVarSizeWord(34); // init item to smith
														// screen
				c.outStream.writeWord(column); // Column Across Smith Screen
				c.outStream.writeByte(4); // Total Rows?
				c.outStream.writeDWord(slot); // Row Down The Smith Screen
				c.outStream.writeWord(id + 1); // item
				c.outStream.writeByte(amount); // how many there are?
				c.outStream.endFrameVarSizeWord();
			}
		}
	}

	public void walkableInterface(int id) {
		synchronized (c) {
			if (c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(208);
				c.getOutStream().writeWordBigEndian_dup(id);
				c.flushOutStream();
			}
		}
	}

	public int mapStatus = 0;

	public void sendFrame99(int state) { // used for disabling map
		synchronized (c) {
			if (c.getOutStream() != null && c != null) {
				if (mapStatus != state) {
					mapStatus = state;
					c.getOutStream().createFrame(99);
					c.getOutStream().writeByte(state);
					c.flushOutStream();
				}
			}
		}
	}

	public void sendFrame35(int i1, int i2, int i3, int i4) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(35);
			c.getOutStream().writeByte(i1);
			c.getOutStream().writeByte(i2);
			c.getOutStream().writeByte(i3);
			c.getOutStream().writeByte(i4);
			c.updateRequired = true;
			c.appearanceUpdateRequired = true;
		}
	}

	public void sendCrashFrame() { // used for crashing cheat clients
		synchronized (c) {
			if (c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(123);
				c.flushOutStream();
			}
		}
	}

	/**
	 * Reseting animations for everyone
	 **/

	public void frame1() {
		synchronized (c) {
			for (int i = 0; i < MistexConfiguration.MAX_PLAYERS; i++) {
				if (Mistex.playerHandler.players[i] != null) {
					Client person = (Client) Mistex.playerHandler.players[i];
					if (person != null) {
						if (person.getOutStream() != null && !person.disconnected) {
							if (c.distanceToPoint(person.getX(), person.getY()) <= 25) {
								person.getOutStream().createFrame(1);
								person.flushOutStream();
								person.getPA().requestUpdates();
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Creating projectile
	 **/
	public void createProjectile(int x, int y, int offX, int offY, int angle, int speed, int gfxMoving, int startHeight, int endHeight, int lockon, int time) {
		synchronized (c) {
			if (c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(85);
				c.getOutStream().writeByteC((y - (c.getMapRegionY() * 8)) - 2);
				c.getOutStream().writeByteC((x - (c.getMapRegionX() * 8)) - 3);
				c.getOutStream().createFrame(117);
				c.getOutStream().writeByte(angle);
				c.getOutStream().writeByte(offY);
				c.getOutStream().writeByte(offX);
				c.getOutStream().writeWord(lockon);
				c.getOutStream().writeWord(gfxMoving);
				c.getOutStream().writeByte(startHeight);
				c.getOutStream().writeByte(endHeight);
				c.getOutStream().writeWord(time);
				c.getOutStream().writeWord(speed);
				c.getOutStream().writeByte(16);
				c.getOutStream().writeByte(64);
				c.flushOutStream();
			}
		}
	}

	public void createProjectile2(int x, int y, int offX, int offY, int angle, int speed, int gfxMoving, int startHeight, int endHeight, int lockon, int time, int slope) {
		synchronized (c) {
			if (c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(85);
				c.getOutStream().writeByteC((y - (c.getMapRegionY() * 8)) - 2);
				c.getOutStream().writeByteC((x - (c.getMapRegionX() * 8)) - 3);
				c.getOutStream().createFrame(117);
				c.getOutStream().writeByte(angle);
				c.getOutStream().writeByte(offY);
				c.getOutStream().writeByte(offX);
				c.getOutStream().writeWord(lockon);
				c.getOutStream().writeWord(gfxMoving);
				c.getOutStream().writeByte(startHeight);
				c.getOutStream().writeByte(endHeight);
				c.getOutStream().writeWord(time);
				c.getOutStream().writeWord(speed);
				c.getOutStream().writeByte(slope);
				c.getOutStream().writeByte(64);
				c.flushOutStream();
			}
		}
	}

	// projectiles for everyone within 25 squares
	public void createPlayersProjectile(int x, int y, int offX, int offY, int angle, int speed, int gfxMoving, int startHeight, int endHeight, int lockon, int time) {
		synchronized (c) {
			for (int i = 0; i < MistexConfiguration.MAX_PLAYERS; i++) {
				Player p = Mistex.playerHandler.players[i];
				if (p != null) {
					Client person = (Client) p;
					if (person != null) {
						if (person.getOutStream() != null) {
							if (person.distanceToPoint(x, y) <= 25) {
								if (p.heightLevel == c.heightLevel)
									person.getPA().createProjectile(x, y, offX, offY, angle, speed, gfxMoving, startHeight, endHeight, lockon, time);
							}
						}
					}
				}
			}
		}
	}

	public void createPlayersProjectile2(int x, int y, int offX, int offY, int angle, int speed, int gfxMoving, int startHeight, int endHeight, int lockon, int time, int slope) {
		synchronized (c) {
			for (int i = 0; i < MistexConfiguration.MAX_PLAYERS; i++) {
				Player p = Mistex.playerHandler.players[i];
				if (p != null) {
					Client person = (Client) p;
					if (person != null) {
						if (person.getOutStream() != null) {
							if (person.distanceToPoint(x, y) <= 25) {
								person.getPA().createProjectile2(x, y, offX, offY, angle, speed, gfxMoving, startHeight, endHeight, lockon, time, slope);
							}
						}
					}
				}
			}
		}
	}

	/**
	 ** GFX
	 **/
	public void stillGfx(int id, int x, int y, int height, int time) {
		synchronized (c) {
			if (c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(85);
				c.getOutStream().writeByteC(y - (c.getMapRegionY() * 8));
				c.getOutStream().writeByteC(x - (c.getMapRegionX() * 8));
				c.getOutStream().createFrame(4);
				c.getOutStream().writeByte(0);
				c.getOutStream().writeWord(id);
				c.getOutStream().writeByte(height);
				c.getOutStream().writeWord(time);
				c.flushOutStream();
			}
		}
	}

	// creates gfx for everyone
	public void createPlayersStillGfx(int id, int x, int y, int height, int time) {
		synchronized (c) {
			for (int i = 0; i < MistexConfiguration.MAX_PLAYERS; i++) {
				Player p = Mistex.playerHandler.players[i];
				if (p != null) {
					Client person = (Client) p;
					if (person != null) {
						if (person.getOutStream() != null) {
							if (person.distanceToPoint(x, y) <= 25) {
								person.getPA().stillGfx(id, x, y, height, time);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Objects, add and remove
	 **/
	public void object(int objectId, int objectX, int objectY, int face, int objectType) {
		synchronized (c) {
			if (c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(85);
				c.getOutStream().writeByteC(objectY - (c.getMapRegionY() * 8));
				c.getOutStream().writeByteC(objectX - (c.getMapRegionX() * 8));
				c.getOutStream().createFrame(101);
				c.getOutStream().writeByteC((objectType << 2) + (face & 3));
				c.getOutStream().writeByte(0);

				if (objectId != -1) { // removing
					c.getOutStream().createFrame(151);
					c.getOutStream().writeByteS(0);
					c.getOutStream().writeWordBigEndian(objectId);
					c.getOutStream().writeByteS((objectType << 2) + (face & 3));
				}
				c.flushOutStream();
			}
		}
	}

	public void checkObjectSpawn(int objectId, int objectX, int objectY, int face, int objectType) {
		if (c.distanceToPoint(objectX, objectY) > 60)
			return;
		synchronized (c) {
			if (c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(85);
				c.getOutStream().writeByteC(objectY - (c.getMapRegionY() * 8));
				c.getOutStream().writeByteC(objectX - (c.getMapRegionX() * 8));
				c.getOutStream().createFrame(101);
				c.getOutStream().writeByteC((objectType << 2) + (face & 3));
				c.getOutStream().writeByte(0);

				if (objectId != -1) { // removing
					c.getOutStream().createFrame(151);
					c.getOutStream().writeByteS(0);
					c.getOutStream().writeWordBigEndian(objectId);
					c.getOutStream().writeByteS((objectType << 2) + (face & 3));
				}
				c.flushOutStream();
			}
		}
	}

	/**
	 * Show option, attack, trade, follow etc
	 **/
	public String optionType = "null";

	public void showOption(int i, int l, String s, int a) {
		synchronized (c) {
			if (c.getOutStream() != null && c != null) {
				if (!optionType.equalsIgnoreCase(s)) {
					optionType = s;
					c.getOutStream().createFrameVarSize(104);
					c.getOutStream().writeByteC(i);
					c.getOutStream().writeByteA(l);
					c.getOutStream().writeString(s);
					c.getOutStream().endFrameVarSize();
					c.flushOutStream();
				}
			}
		}
	}

	/**
	 * Private Messaging
	 **/
	public void logIntoPM() {
		setPrivateMessaging(2);
		for (int i1 = 0; i1 < MistexConfiguration.MAX_PLAYERS; i1++) {
			Player p = Mistex.playerHandler.players[i1];
			if (p != null && p.isActive) {
				Client o = (Client) p;
				if (o != null) {
					o.getPA().updatePM(c.playerId, 1);
				}
			}
		}
		boolean pmLoaded = false;

		for (int i = 0; i < c.friends.length; i++) {
			if (c.friends[i] != 0) {
				for (int i2 = 1; i2 < MistexConfiguration.MAX_PLAYERS; i2++) {
					Player p = Mistex.playerHandler.players[i2];
					if (p != null && p.isActive && MistexUtility.playerNameToInt64(p.playerName) == c.friends[i]) {
						Client o = (Client) p;
						if (o != null) {
							if (c.playerRights >= 2 || p.privateChat == 0 || (p.privateChat == 1 && o.getPA().isInPM(MistexUtility.playerNameToInt64(c.playerName)))) {
								loadPM(c.friends[i], 1);
								pmLoaded = true;
							}
							break;
						}
					}
				}
				if (!pmLoaded) {
					loadPM(c.friends[i], 0);
				}
				pmLoaded = false;
			}
			for (int i1 = 1; i1 < MistexConfiguration.MAX_PLAYERS; i1++) {
				Player p = Mistex.playerHandler.players[i1];
				if (p != null && p.isActive) {
					Client o = (Client) p;
					if (o != null) {
						o.getPA().updatePM(c.playerId, 1);
					}
				}
			}
		}
	}

	public void updatePM(int pID, int world) { // used for private chat updates
		Player p = Mistex.playerHandler.players[pID];
		if (p == null || p.playerName == null || p.playerName.equals("null")) {
			return;
		}
		Client o = (Client) p;
		if (o == null) {
			return;
		}
		long l = MistexUtility.playerNameToInt64(Mistex.playerHandler.players[pID].playerName);

		if (p.privateChat == 0) {
			for (int i = 0; i < c.friends.length; i++) {
				if (c.friends[i] != 0) {
					if (l == c.friends[i]) {
						loadPM(l, world);
						return;
					}
				}
			}
		} else if (p.privateChat == 1) {
			for (int i = 0; i < c.friends.length; i++) {
				if (c.friends[i] != 0) {
					if (l == c.friends[i]) {
						if (o.getPA().isInPM(MistexUtility.playerNameToInt64(c.playerName))) {
							loadPM(l, world);
							return;
						} else {
							loadPM(l, 0);
							return;
						}
					}
				}
			}
		} else if (p.privateChat == 2) {
			for (int i = 0; i < c.friends.length; i++) {
				if (c.friends[i] != 0) {
					if (l == c.friends[i] && c.playerRights < 2) {
						loadPM(l, 0);
						return;
					}
				}
			}
		}
	}

	public boolean isInPM(long l) {
		for (int i = 0; i < c.friends.length; i++) {
			if (c.friends[i] != 0) {
				if (l == c.friends[i]) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Drink AntiPosion Potions
	 * 
	 * @param itemId
	 *            The itemId
	 * @param itemSlot
	 *            The itemSlot
	 * @param newItemId
	 *            The new item After Drinking
	 * @param healType
	 *            The type of poison it heals
	 */
	public void potionPoisonHeal(int itemId, int itemSlot, int newItemId, int healType) {
		c.attackTimer = c.getCombat().getAttackDelay(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
		if (c.duelRule[5]) {
			c.sendMessage("Potions has been disabled in this duel!");
			return;
		}
		if (!c.isDead && System.currentTimeMillis() - c.foodDelay > 900) {
			if (c.getItems().playerHasItem(itemId, 1, itemSlot)) {
				c.sendMessage("You drink the " + c.getItems().getItemName(itemId).toLowerCase() + ".");
				c.foodDelay = System.currentTimeMillis();
				// Actions
				if (healType == 1) {
					// Cures The Poison
				} else if (healType == 2) {
					// Cures The Poison + protects from getting poison again
				}
				c.startAnimation(0x33D);
				c.getItems().deleteItem(itemId, itemSlot, 1);
				c.getItems().addItem(newItemId, 1);
				requestUpdates();
			}
		}
	}
	/**
	 * Magic on items
	 **/

	public void magicOnItems(int slot, int itemId, int spellId) {

		switch (spellId) {
		case 1173:
			c.getSuperHeat().startHeating(itemId, c.getItems().getItemName(itemId));
			break;
		case 1162: // low alch
			if (System.currentTimeMillis() - c.alchDelay > 1000) {
				if (!c.getCombat().checkMagicReqs(49)) {
					break;
				}
				if (!c.getItems().playerHasItem(itemId, 1, slot) || itemId == 995) {
					return;
				}
				if (c.getItems().getItemShopValue(itemId) >= 5000000) {
					c.sendMessage("You may not alch items worth more than 5 million coins!");
					return;
				}		
				c.getItems().deleteItem(itemId, slot, 1);
				c.getItems().addItem(995, c.getItems().getItemShopValue(itemId) / 3);
				c.startAnimation(c.MAGIC_SPELLS[49][2]);
				c.gfx0(c.MAGIC_SPELLS[49][3]);
				c.alchDelay = System.currentTimeMillis();
				sendFrame106(6);
				addSkillXP(c.MAGIC_SPELLS[49][7] * PlayerConfiguration.MAGIC_EXP_RATE, 6);
				refreshSkill(6);
			}
			break;

		case 1178: // high alch
			if (System.currentTimeMillis() - c.alchDelay > 2000) {
				if (!c.getCombat().checkMagicReqs(50)) {
					break;
				}
				if (!c.getItems().playerHasItem(itemId, 1, slot) || itemId == 995) {
					return;
				}
				if (c.getItems().getItemShopValue(itemId) >= 5000000) {
					c.sendMessage("You may not alch items worth more than 5million coins!");
					return;
				}
				c.getItems().deleteItem(itemId, slot, 1);
				c.getItems().addItem(995, (int) (c.getItems().getItemShopValue(itemId) * .75));
				c.startAnimation(c.MAGIC_SPELLS[50][2]);
				c.gfx0(c.MAGIC_SPELLS[50][3]);
				c.alchDelay = System.currentTimeMillis();
				sendFrame106(6);
				addSkillXP(c.MAGIC_SPELLS[50][7] * PlayerConfiguration.MAGIC_EXP_RATE, 6);
				refreshSkill(6);
			}
			break;
		case 1155: // Lvl-1 enchant sapphire
		case 1165: // Lvl-2 enchant emerald
		case 1176: // Lvl-3 enchant ruby
		case 1180: // Lvl-4 enchant diamond
		case 1187: // Lvl-5 enchant dragonstone
		case 6003: // Lvl-6 enchant onyx
			c.getEnchanting().enchantItem(itemId, spellId);
			break;
		}
	}

	public String deathMsgs() {
		int deathMsgs = MistexUtility.random(9);
		switch (deathMsgs) {
		case 0:
			return "With a crushing blow, you defeat " + c.playerName + "";
		case 1:
			return "It's humiliating defeat for " + c.playerName + "";
		case 2:
			return "" + c.playerName + " didn't stand a chance against you.";
		case 3:
			return "You've defeated " + c.playerName + "";
		case 4:
			return "" + c.playerName + " regrets the day they met you in combat";
		case 5:
			return "It's all over for " + c.playerName + "";
		case 6:
			return "" + c.playerName + " falls before you might";
		case 7:
			return "Can anyone defeat you? Certainly not " + c.playerName + "";
		case 8:
			return "You were clearly a better fighter than " + c.playerName + "";
		default:
			return "You were clearly a better fighter than " + c.playerName + "";
		}
	}
	
	

	public void applyDead() {
		c.getDuel().stakedItems.clear();
		c.getPA().requestUpdates();
		c.respawnTimer = 15;
		c.isDead = false;
		c.freezeTimer = 1;
		if (c.duelStatus != 6) {
			c.killerId = findKiller();
			Client o = (Client) World.players[c.killerId];
			if (o != null) {
				if (c.killerId != c.playerId)
					if (c.killerId != c.playerId) {
						o.sendMessage(deathMsgs());
						if (o.inBoxingArena  && c.inBoxingArena && !c.inWild() && !o.inWild()) {
							o.getItems().addItem(995, MistexUtility.random(5000));
							o.sendMessage("You were rewarded with a random amount of coins for that kill.");
							return;
						}
						if (o.inWeaponGame() && !c.inWild() && !o.inWild()) {
							o.weaponKills += 1;
							WeaponGame.handleKilled(o);
							return;
						}
						if (c.inWild() && !c.inDuelArena() && !c.inWeaponGame()) {
							if (!antiPkPFarm()) {
								return;
							}
								o.specAmount = 10.0;
								o.getItems().addSpecialBar(o.playerEquipment[o.playerWeapon]);
								o.KC += 1;
								o.killStreak += 1;
								o.lastKilled1 = c.connectedFrom;
								o.getStreak().checkKillStreak();
								o.getStreak().killedPlayer();
								givePkP();
							
							}
						if (o.KC == 1 && !c.inWeaponGame())
							o.sendMessage("@war@You have started the achievement: Beast ");
						InterfaceText.writeText(new AchievementTab(o));
						if (o.KC == 1000 && !c.inWeaponGame())
							AchievementHandler.activateAchievement(o, AchievementList.BEAST);
						InterfaceText.writeText(new InformationTab(o));
						c.playerKilled = c.playerId;
						if (o.duelStatus == 5) {
							o.duelStatus++;
						}
					}
			}
			c.faceUpdate(0);
			CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
				public void execute(CycleEventContainer p) {
					c.npcIndex = 0;
					c.playerIndex = 0;
					p.stop();
				}

				@Override
				public void stop() {
				}
			}, 1);
			c.stopMovement();
			c.killStreak = 0;
			if (c.duelStatus <= 4) {
				c.getDuel().stakedItems.clear();
				c.sendMessage(MistexConfiguration.DEATH_MESSAGE);
			} else if (c.duelStatus != 6) {
				Client o2 = (Client) World.players[c.killerId];
				c.getDuel().stakedItems.clear();
				c.sendMessage("You have lost the duel!");
				PlayerSave.saveGame(o);
				PlayerSave.saveGame(c);
			}
			PlayerSave.saveGame(c);
			resetDamageDone();
			c.specAmount = 10;
			c.getItems().addSpecialBar(c.playerEquipment[c.playerWeapon]);
			c.lastVeng = 0;
			c.vengOn = false;
			resetFollowers();
			c.attackTimer = 10;
			c.tradeResetNeeded = true;
			removeAllWindows();
		}
	}
	
	public boolean antiPkPFarm() {
		Client o = (Client) World.players[c.killerId];
		if (o.connectedFrom.equals(c.connectedFrom)) {
			o.sendMessage("You can not kill someone from your own Ip!");
			return false;
		}
		if (o.lastKilled1 != null && o.lastKilled1.equals(c.connectedFrom)) {
			o.sendMessage("You already killed this player recently!");
			return false;
		}

		return true;
	}
	
	public void givePkP() {
		Client o = (Client) World.players[c.killerId];
		switch (o.playerRights) {
		case 3:
			o.sendMessage("Don't you just love it when Owners wreck bad horny bitches?");
			break;
		
		case 4:
			o.pkPoints += 13;
			o.sendMessage("Since you are a normal donator you earned 13 Pk points for that kill.");
			break;
			
		case 5:
			o.pkPoints += 15;
			o.sendMessage("Since you are a super donator you earned 15 Pk points for that kill.");		
			break;
			
		case 6:
			o.pkPoints += 20;
			o.sendMessage("Since you are an extreme donator you earned 20 Pk points for that kill.");
			break;
			
			default:
				o.pkPoints += 10;
				o.sendMessage("You earned 10 Pk points for that kill.");
				break;
		
		}
	}

	public void refreshPlayer(Client c) {
		c.stopMovement();
		c.faceUpdate(0);
		c.playerLevel[5] = c.getPA().getLevelForXP(c.playerXP[5]);
		c.getPA().refreshSkill(5);
		resetFollowers();
	}

	public void resetDamageDone() {
		for (int i = 0; i < World.players.length; i++) {
			if (World.players[i] != null) {
				World.players[i].damageTaken[c.playerId] = 0;
			}
		}
	}

	public int backupItems[] = new int[MistexConfiguration.BANK_SIZE];
	public int backupItemsN[] = new int[MistexConfiguration.BANK_SIZE];

	public void resetTb() {
		c.teleBlockLength = 0;
		c.teleBlockDelay = 0;
	}

	public void handleStatus(int i, int i2, int i3) {
		// Oh Sanity!
	}

	public void resetFollowers() {
		for (int j = 0; j < Mistex.playerHandler.players.length; j++) {
			if (Mistex.playerHandler.players[j] != null) {
				if (Mistex.playerHandler.players[j].followId == c.playerId) {
					Client c = (Client) Mistex.playerHandler.players[j];
					c.getPA().resetFollow();
				}
			}
		}
	}

	public void giveLife() {
		c.isDead = false;
		c.faceUpdate(-1);
		c.freezeTimer = 0;
		boolean canLooseItems = !c.inFunPk() && !c.inFightCaves() && !c.getPA().inPitsWait() && !c.inPcGame() && !c.inFightPits() && !c.inWeaponGame() && !c.getRights().isDeveloper() && !c.getRights().isAdminstrator() && !c.isKillingJad && !c.inChampionsBattle && !c.inChampionsBattle;
		if (c.duelStatus <= 4 && canLooseItems) {
			c.getItems().resetKeepItems();

			if (!c.isSkulled) {
				c.getItems().keepItem(0, true);
				c.getItems().keepItem(1, true);
				c.getItems().keepItem(2, true);
			}

			if (c.prayerActive[10] || c.curseActive[0]  && System.currentTimeMillis() - c.lastProtItem > 700) {
				c.getItems().keepItem(3, true);
			}
			c.getItems().dropAllItems();
			c.getItems().deleteAllItems();
			if (!c.isSkulled) {
				for (int i1 = 0; i1 < 3; i1++) {
					if (c.itemKeptId[i1] > 0) {
						c.getItems().addItem(c.itemKeptId[i1], 1);
					}
				}
			}

			if (c.prayerActive[10] || c.curseActive[0]) {
				if (c.itemKeptId[3] > 0) {
					c.getItems().addItem(c.itemKeptId[3], 1);
				}
			}

			c.getItems().resetKeepItems();
		}
		c.getCombat().resetPrayers();
		for (int i = 0; i < 23; i++) {
			c.playerLevel[i] = getLevelForXP(c.playerXP[i]);
			c.getPA().refreshSkill(i);
		}
		if (c.pitsStatus == 1) {
			movePlayer(2399, 5173, 0);
		} else if (c.inBoxingArena) {
			Boxing.handleDeath(c);
		} else if (c.inPcGame()) {
			movePlayer(2656, 2608, 0);
		} else if (c.inFightCaves()) {
			c.getPA().resetTzhaar();
		} else if (c.isKillingJad) {
			c.getPA().resetTzhaar();
		} else if (c.inFightPits()) {
			FightPits.handleDeath(c);
		} else if (c.inWeaponGame()) {
			WeaponGame.handleDeath(c);
		} else if (c.inChampionsBattle && c.inChampionsBattle()) {	
			handleChampionsBattleDeath();		
		} else if (c.duelStatus <= 4) {
			movePlayer(PlayerConfiguration.RESPAWN_X, PlayerConfiguration.RESPAWN_Y, 0);
			c.isSkulled = false;
			c.skullTimer = 0;
			c.attackedPlayers.clear();
		} else {
			final Client o = (Client) World.players[c.duelingWith];
			if (o != null) {
				o.getPA().createPlayerHints(10, -1);
				if (o.duelStatus == 6) {
					o.getDuel().duelVictory();
				}
			}
			movePlayer(PlayerConfiguration.DUELING_RESPAWN_X + (MistexUtility.random(PlayerConfiguration.RANDOM_DUELING_RESPAWN)), PlayerConfiguration.DUELING_RESPAWN_Y + (MistexUtility.random(PlayerConfiguration.RANDOM_DUELING_RESPAWN)), 0);
			if (c.duelStatus != 6) {
				c.getDuel().resetDuel();
			}
		}
		PlayerSave.saveGame(c);
		c.getCombat().resetPlayerAttack();
		resetAnimation();
		c.startAnimation(65535);
		c.getPA().resetAutocast();
		frame1();
		handleSummoningDeath();
		resetTb();
		c.isSkulled = false;
		c.attackedPlayers.clear();
		c.headIconPk = -1;
		c.skullTimer = -1;
		c.damageTaken = new int[MistexConfiguration.MAX_PLAYERS];
		c.getPA().requestUpdates();
		if (c.inWild() && !c.inFunPk() && !c.inFightPits() && !c.inWeaponGame() && !c.inWeaponLobby()) {
			c.DC += 1;
			c.killStreak = 0;
		}
		if (c.DC == 1)
			c.sendMessage("@war@You have started the achievement: Weakling ");
		InterfaceText.writeText(new AchievementTab(c));
		if (c.DC == 100)
			AchievementHandler.activateAchievement(c, AchievementList.WEAKLING);
		InterfaceText.writeText(new InformationTab(c));
		if (!c.inPcGame() || !c.inFightCaves() || !c.inFightPits()) {
			c.getPA().walkableInterface(-1);
		}
	}

	/*
	 * public void giveLife() { c.isDead = false; c.faceUpdate(-1);
	 * c.freezeTimer = 0; if (c.duelStatus <= 4 && !c.getPA().inPitsWait()) { if
	 * (!c.inPits && !c.inFightCaves() && !c.inFunPk()) {
	 * c.getItems().resetKeepItems(); if ((c.playerRights == 2 &&
	 * MistexConfiguration.ADMIN_DROP_ITEMS) || c.playerRights != 2) { if
	 * (!c.isSkulled) { c.getItems().keepItem(0, true); c.getItems().keepItem(1,
	 * true); c.getItems().keepItem(2, true); } if (c.prayerActive[10] &&
	 * System.currentTimeMillis() - c.lastProtItem > 700 &&
	 * System.currentTimeMillis() - c.lastProtItem > 700) {
	 * c.getItems().keepItem(3, true); }
	 * 
	 * c.getItems().dropAllItems(); c.getItems().dropAllItemsPVP();
	 * c.getItems().deleteAllItems(); if (!c.isSkulled) { for (int i1 = 0; i1 <
	 * 3; i1++) { if (c.itemKeptId[i1] > 0) {
	 * c.getItems().addItem(c.itemKeptId[i1], 1); } } } if (c.prayerActive[10])
	 * { if (c.itemKeptId[3] > 0) { c.getItems().addItem(c.itemKeptId[3], 1); }
	 * } } c.getItems().resetKeepItems(); } else if (c.pitsStatus == 1) {
	 * movePlayer(2399, 5173, 0); } else if (c.inPits) {
	 * FightPits.handleDeath(c); } } c.getCombat().resetPrayers(); for (int i =
	 * 0; i < 20; i++) { c.playerLevel[i] = getLevelForXP(c.playerXP[i]);
	 * c.getPA().refreshSkill(i); } if (c.pitsStatus == 1) { movePlayer(2399,
	 * 5173, 0); } else if (c.duelStatus <= 4) {
	 * 
	 * movePlayer(PlayerConfiguration.RESPAWN_X, PlayerConfiguration.RESPAWN_Y,
	 * 0); c.isSkulled = false; c.skullTimer = 0; c.attackedPlayers.clear(); }
	 * else if (c.inFightCaves()) { c.getPA().resetTzhaar(); } else { Client o =
	 * (Client) Mistex.playerHandler.players[c.duelingWith]; if (o != null) {
	 * o.getPA().createPlayerHints(10, -1); if (o.duelStatus == 6) {
	 * o.getTradeAndDuel().duelVictory(); } }
	 * movePlayer(PlayerConfiguration.DUELING_RESPAWN_X+
	 * (MistexUtility.random(PlayerConfiguration
	 * .RANDOM_DUELING_RESPAWN)),PlayerConfiguration.DUELING_RESPAWN_Y+
	 * (MistexUtility.random(PlayerConfiguration.RANDOM_DUELING_RESPAWN)),0); if
	 * (c.duelStatus != 6) { c.getTradeAndDuel().resetDuel(); } }
	 * c.getCombat().resetPlayerAttack(); resetAnimation();
	 * c.startAnimation(65535); frame1(); resetTb(); c.isSkulled = false;
	 * c.attackedPlayers.clear(); c.headIconPk = -1; c.skullTimer = -1;
	 * c.damageTaken = new int[MistexConfiguration.MAX_PLAYERS];
	 * c.getPA().requestUpdates(); if (c.inWild()) { c.DC += 1; c.killStreak =
	 * 0; } if (c.DC == 1)
	 * c.sendMessage("@war@You have started the achievement: Weakling "); if
	 * (c.DC == 100) AchievementHandler.activateAchievement(c,
	 * AchievementList.WEAKLING); InterfaceText.writeText(new
	 * InformationTab(c)); PlayerSave.saveGame(c); }
	 */

	/**
	 * Location change for digging, levers etc
	 **/

	public void changeLocation() {
		switch (c.newLocation) {
		case 1:
			sendFrame99(2);
			movePlayer(3578, 9706, -1);
			break;
		case 2:
			sendFrame99(2);
			movePlayer(3568, 9683, -1);
			break;
		case 3:
			sendFrame99(2);
			movePlayer(3557, 9703, -1);
			break;
		case 4:
			sendFrame99(2);
			movePlayer(3556, 9718, -1);
			break;
		case 5:
			sendFrame99(2);
			movePlayer(3534, 9704, -1);
			break;
		case 6:
			sendFrame99(2);
			movePlayer(3546, 9684, -1);
			break;
		}
		c.newLocation = 0;
	}

	public void processTeleport() {
		c.teleportToX = c.teleX;
		c.teleportToY = c.teleY;
		c.heightLevel = c.teleHeight;
		if (c.teleEndAnimation > 0) {
			c.startAnimation(c.teleEndAnimation);
		}

		if (c.teleEndGfx > 0) {
			c.gfx100(c.teleEndGfx);
			c.teleEndGfx = 0;
		}
	}

	public void movePlayer(int x, int y, int h) {
		c.resetWalkingQueue();
		c.teleportToX = x;
		c.teleportToY = y;
		c.heightLevel = h;
		requestUpdates();
		c.getPA().sendFrame99(0);
		c.getPA().walkableInterface(-1);
		c.getPA().showOption(3, 0, "Null", 1);
		// fade(x, y, h);
	}

	/**
	 * Following
	 **/
	public void followPlayer() {
		if (World.players[c.followId] == null || World.players[c.followId].isDead) {
			c.followId = 0;
			return;
		}
		if (c.freezeTimer > 0) {
			return;
		}

		if (inPitsWait()) {
			c.followId = 0;
		}

		if (c.isDead || c.playerLevel[3] <= 0)
			return;

		int otherX = World.players[c.followId].getX();
		int otherY = World.players[c.followId].getY();

		boolean sameSpot = (c.absX == otherX && c.absY == otherY);

		boolean hallyDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 2);
		boolean withinDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 2);
		boolean rangeWeaponDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 4);
		boolean bowDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 6);
		boolean mageDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 7);

		boolean castingMagic = (c.usingMagic || c.mageFollow || c.autocasting || c.spellId > 0) && mageDistance;
		boolean playerRanging = (c.usingRangeWeapon) && rangeWeaponDistance;
		boolean playerBowOrCross = (c.usingBow) && bowDistance;

		if (!c.goodDistance(otherX, otherY, c.getX(), c.getY(), 25)) {
			c.followId = 0;
			return;
		}
		if (c.goodDistance(otherX, otherY, c.getX(), c.getY(), 1)) {
			if (otherX != c.getX() && otherY != c.getY()) {
				stopDiagonal(otherX, otherY);
				return;
			}
		}

		if ((c.usingBow || c.mageFollow || (c.playerIndex > 0 && c.autocastId > 0)) && bowDistance && !sameSpot) {
			return;
		}

		if (c.getCombat().usingHally() && hallyDistance && !sameSpot) {
			return;
		}

		if (c.usingRangeWeapon && rangeWeaponDistance && !sameSpot) {
			return;
		}

		c.faceUpdate(c.followId + 32768);
		if (otherX == c.absX && otherY == c.absY) {
			int r = MistexUtility.random(3);
			switch (r) {
			case 0:
				walkTo(0, -1);
				break;
			case 1:
				walkTo(0, 1);
				break;
			case 2:
				walkTo(1, 0);
				break;
			case 3:
				walkTo(-1, 0);
				break;
			}
		} else if (c.isRunning2 && !withinDistance) {
			if (otherY > c.getY() && otherX == c.getX()) {
				playerWalk(otherX, otherY - 1);
			} else if (otherY < c.getY() && otherX == c.getX()) {
				playerWalk(otherX, otherY + 1);
			} else if (otherX > c.getX() && otherY == c.getY()) {
				playerWalk(otherX - 1, otherY);
			} else if (otherX < c.getX() && otherY == c.getY()) {
				playerWalk(otherX + 1, otherY);
			} else if (otherX < c.getX() && otherY < c.getY()) {
				playerWalk(otherX + 1, otherY + 1);
			} else if (otherX > c.getX() && otherY > c.getY()) {
				playerWalk(otherX - 1, otherY - 1);
			} else if (otherX < c.getX() && otherY > c.getY()) {
				playerWalk(otherX + 1, otherY - 1);
			} else if (otherX > c.getX() && otherY < c.getY()) {
				playerWalk(otherX + 1, otherY - 1);
			}
		} else {
			if (otherY > c.getY() && otherX == c.getX()) {
				playerWalk(otherX, otherY - 1);
			} else if (otherY < c.getY() && otherX == c.getX()) {
				playerWalk(otherX, otherY + 1);
			} else if (otherX > c.getX() && otherY == c.getY()) {
				playerWalk(otherX - 1, otherY);
			} else if (otherX < c.getX() && otherY == c.getY()) {
				playerWalk(otherX + 1, otherY);
			} else if (otherX < c.getX() && otherY < c.getY()) {
				playerWalk(otherX + 1, otherY + 1);
			} else if (otherX > c.getX() && otherY > c.getY()) {
				playerWalk(otherX - 1, otherY - 1);
			} else if (otherX < c.getX() && otherY > c.getY()) {
				playerWalk(otherX + 1, otherY - 1);
			} else if (otherX > c.getX() && otherY < c.getY()) {
				playerWalk(otherX - 1, otherY + 1);
			}
		}
		c.faceUpdate(c.followId + 32768);
	}

	public void getSpeared(int otherX, int otherY) {
		int x = c.absX - otherX;
		int y = c.absY - otherY;
		if (x > 0)
			x = 1;
		else if (x < 0)
			x = -1;
		if (y > 0)
			y = 1;
		else if (y < 0)
			y = -1;
		// moveCheck(x,y);
		c.lastSpear = System.currentTimeMillis();
	}

	public void playerWalk(int x, int y) {
		PathFinder.getPathFinder().findRoute(c, x, y, true, 1, 1);//
	}

	public void followNpc() {
		if (NPCHandler.npcs[c.followId] == null || NPCHandler.npcs[c.followId].isDead) {
			c.followId = 0;
			return;
		}
		if (c.freezeTimer > 0) {
			return;
		}
		if (c.isDead || c.playerLevel[3] <= 0)
			return;

		int otherX = NPCHandler.npcs[c.followId2].getX();
		int otherY = NPCHandler.npcs[c.followId2].getY();
		boolean withinDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 2);
		boolean goodDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 1);
		boolean hallyDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 2);
		boolean bowDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 8);
		boolean rangeWeaponDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 4);
		boolean sameSpot = c.absX == otherX && c.absY == otherY;
		if (!c.goodDistance(otherX, otherY, c.getX(), c.getY(), 25)) {
			c.followId2 = 0;
			return;
		}
		if (c.goodDistance(otherX, otherY, c.getX(), c.getY(), 1)) {
			if (otherX != c.getX() && otherY != c.getY()) {
				stopDiagonal(otherX, otherY);
				return;
			}
		}

		if ((c.usingBow || c.mageFollow || (c.npcIndex > 0 && c.autocastId > 0)) && bowDistance && !sameSpot) {
			return;
		}

		if (c.getCombat().usingHally() && hallyDistance && !sameSpot) {
			return;
		}

		if (c.usingRangeWeapon && rangeWeaponDistance && !sameSpot) {
			return;
		}

		c.faceUpdate(c.followId);
		if (otherX == c.absX && otherY == c.absY) {
			int r = MistexUtility.random(3);
			switch (r) {
			case 0:
				walkTo(0, -1);
				break;
			case 1:
				walkTo(0, 1);
				break;
			case 2:
				walkTo(1, 0);
				break;
			case 3:
				walkTo(-1, 0);
				break;
			}
		} else if (c.isRunning2 && !withinDistance) {
			/*
			 * if(otherY > c.getY() && otherX == c.getX()) { walkTo(0,
			 * getMove(c.getY(), otherY - 1) + getMove(c.getY(), otherY - 1)); }
			 * else if(otherY < c.getY() && otherX == c.getX()) { walkTo(0,
			 * getMove(c.getY(), otherY + 1) + getMove(c.getY(), otherY + 1)); }
			 * else if(otherX > c.getX() && otherY == c.getY()) {
			 * walkTo(getMove(c.getX(), otherX - 1) + getMove(c.getX(), otherX -
			 * 1), 0); } else if(otherX < c.getX() && otherY == c.getY()) {
			 * walkTo(getMove(c.getX(), otherX + 1) + getMove(c.getX(), otherX +
			 * 1), 0); } else if(otherX < c.getX() && otherY < c.getY()) {
			 * walkTo(getMove(c.getX(), otherX + 1) + getMove(c.getX(), otherX +
			 * 1), getMove(c.getY(), otherY + 1) + getMove(c.getY(), otherY +
			 * 1)); } else if(otherX > c.getX() && otherY > c.getY()) {
			 * walkTo(getMove(c.getX(), otherX - 1) + getMove(c.getX(), otherX -
			 * 1), getMove(c.getY(), otherY - 1) + getMove(c.getY(), otherY -
			 * 1)); } else if(otherX < c.getX() && otherY > c.getY()) {
			 * walkTo(getMove(c.getX(), otherX + 1) + getMove(c.getX(), otherX +
			 * 1), getMove(c.getY(), otherY - 1) + getMove(c.getY(), otherY -
			 * 1)); } else if(otherX > c.getX() && otherY < c.getY()) {
			 * walkTo(getMove(c.getX(), otherX + 1) + getMove(c.getX(), otherX +
			 * 1), getMove(c.getY(), otherY - 1) + getMove(c.getY(), otherY -
			 * 1)); } } else { if(otherY > c.getY() && otherX == c.getX()) {
			 * walkTo(0, getMove(c.getY(), otherY - 1)); } else if(otherY <
			 * c.getY() && otherX == c.getX()) { walkTo(0, getMove(c.getY(),
			 * otherY + 1)); } else if(otherX > c.getX() && otherY == c.getY())
			 * { walkTo(getMove(c.getX(), otherX - 1), 0); } else if(otherX <
			 * c.getX() && otherY == c.getY()) { walkTo(getMove(c.getX(), otherX
			 * + 1), 0); } else if(otherX < c.getX() && otherY < c.getY()) {
			 * walkTo(getMove(c.getX(), otherX + 1), getMove(c.getY(), otherY +
			 * 1)); } else if(otherX > c.getX() && otherY > c.getY()) {
			 * walkTo(getMove(c.getX(), otherX - 1), getMove(c.getY(), otherY -
			 * 1)); } else if(otherX < c.getX() && otherY > c.getY()) {
			 * walkTo(getMove(c.getX(), otherX + 1), getMove(c.getY(), otherY -
			 * 1)); } else if(otherX > c.getX() && otherY < c.getY()) {
			 * walkTo(getMove(c.getX(), otherX - 1), getMove(c.getY(), otherY +
			 * 1)); }
			 */
			if (otherY > c.getY() && otherX == c.getX()) {
				// walkTo(0, getMove(c.getY(), otherY - 1) + getMove(c.getY(),
				// otherY - 1));
				playerWalk(otherX, otherY - 1);
			} else if (otherY < c.getY() && otherX == c.getX()) {
				// walkTo(0, getMove(c.getY(), otherY + 1) + getMove(c.getY(),
				// otherY + 1));
				playerWalk(otherX, otherY + 1);
			} else if (otherX > c.getX() && otherY == c.getY()) {
				// walkTo(getMove(c.getX(), otherX - 1) + getMove(c.getX(),
				// otherX - 1), 0);
				playerWalk(otherX - 1, otherY);
			} else if (otherX < c.getX() && otherY == c.getY()) {
				// walkTo(getMove(c.getX(), otherX + 1) + getMove(c.getX(),
				// otherX + 1), 0);
				playerWalk(otherX + 1, otherY);
			} else if (otherX < c.getX() && otherY < c.getY()) {
				// walkTo(getMove(c.getX(), otherX + 1) + getMove(c.getX(),
				// otherX + 1), getMove(c.getY(), otherY + 1) +
				// getMove(c.getY(), otherY + 1));
				playerWalk(otherX + 1, otherY + 1);
			} else if (otherX > c.getX() && otherY > c.getY()) {
				// walkTo(getMove(c.getX(), otherX - 1) + getMove(c.getX(),
				// otherX - 1), getMove(c.getY(), otherY - 1) +
				// getMove(c.getY(), otherY - 1));
				playerWalk(otherX - 1, otherY - 1);
			} else if (otherX < c.getX() && otherY > c.getY()) {
				// walkTo(getMove(c.getX(), otherX + 1) + getMove(c.getX(),
				// otherX + 1), getMove(c.getY(), otherY - 1) +
				// getMove(c.getY(), otherY - 1));
				playerWalk(otherX + 1, otherY - 1);
			} else if (otherX > c.getX() && otherY < c.getY()) {
				// walkTo(getMove(c.getX(), otherX + 1) + getMove(c.getX(),
				// otherX + 1), getMove(c.getY(), otherY - 1) +
				// getMove(c.getY(), otherY - 1));
				playerWalk(otherX + 1, otherY - 1);
			}
		} else {
			if (otherY > c.getY() && otherX == c.getX()) {
				// walkTo(0, getMove(c.getY(), otherY - 1));
				playerWalk(otherX, otherY - 1);
			} else if (otherY < c.getY() && otherX == c.getX()) {
				// walkTo(0, getMove(c.getY(), otherY + 1));
				playerWalk(otherX, otherY + 1);
			} else if (otherX > c.getX() && otherY == c.getY()) {
				// walkTo(getMove(c.getX(), otherX - 1), 0);
				playerWalk(otherX - 1, otherY);
			} else if (otherX < c.getX() && otherY == c.getY()) {
				// walkTo(getMove(c.getX(), otherX + 1), 0);
				playerWalk(otherX + 1, otherY);
			} else if (otherX < c.getX() && otherY < c.getY()) {
				// walkTo(getMove(c.getX(), otherX + 1), getMove(c.getY(),
				// otherY + 1));
				playerWalk(otherX + 1, otherY + 1);
			} else if (otherX > c.getX() && otherY > c.getY()) {
				// walkTo(getMove(c.getX(), otherX - 1), getMove(c.getY(),
				// otherY - 1));
				playerWalk(otherX - 1, otherY - 1);
			} else if (otherX < c.getX() && otherY > c.getY()) {
				// walkTo(getMove(c.getX(), otherX + 1), getMove(c.getY(),
				// otherY - 1));
				playerWalk(otherX + 1, otherY - 1);
			} else if (otherX > c.getX() && otherY < c.getY()) {
				// walkTo(getMove(c.getX(), otherX - 1), getMove(c.getY(),
				// otherY + 1));
				playerWalk(otherX - 1, otherY + 1);
			}
		}
		c.faceUpdate(c.followId);
	}

	public int getRunningMove(int i, int j) {
		if (j - i > 2)
			return 2;
		else if (j - i < -2)
			return -2;
		else
			return j - i;
	}

	public void sendStatement(String s) {
		sendFrame126(s, 357);
		sendFrame126("Click here to continue", 358);
		sendFrame164(356);
		c.nextChat = 0;
	}

	public void resetFollow() {
		c.followId = 0;
		c.followId2 = 0;
		c.mageFollow = false;
		c.outStream.createFrame(174);
		c.outStream.writeWord(0);
		c.outStream.writeByte(0);
		c.outStream.writeWord(1);
	}
	
	public void handleSummoningDeath() {
		c.getPA().sendFrame75(4000, 23027);
		if (c.lastsummon > 0) {
			c.lastsummon = -1;
			c.totalstored = 0;
			c.summoningnpcid = 0;
			c.summoningslot = 0;
			c.getBOB().dropItems();
			c.sendMessage("<col=8345667>Your BoB items have drop on the floor");
			c.setSidebarInterface(15, -1); // Summoning
		} else {
			c.sendMessage("<col=8345667>You do not have a npc currently spawned");
		}
	}
	

	public void walkTo(int i, int j) {
		c.newWalkCmdSteps = 0;
		if (++c.newWalkCmdSteps > 50)
			c.newWalkCmdSteps = 0;
		int k = c.getX() + i;
		k -= c.mapRegionX * 8;
		c.getNewWalkCmdX()[0] = c.getNewWalkCmdY()[0] = 0;
		int l = c.getY() + j;
		l -= c.mapRegionY * 8;

		for (int n = 0; n < c.newWalkCmdSteps; n++) {
			c.getNewWalkCmdX()[n] += k;
			c.getNewWalkCmdY()[n] += l;
		}
	}

	public void walkTo2(int i, int j) {
		if (c.freezeDelay > 0)
			return;
		c.newWalkCmdSteps = 0;
		if (++c.newWalkCmdSteps > 50)
			c.newWalkCmdSteps = 0;
		int k = c.getX() + i;
		k -= c.mapRegionX * 8;
		c.getNewWalkCmdX()[0] = c.getNewWalkCmdY()[0] = 0;
		int l = c.getY() + j;
		l -= c.mapRegionY * 8;

		for (int n = 0; n < c.newWalkCmdSteps; n++) {
			c.getNewWalkCmdX()[n] += k;
			c.getNewWalkCmdY()[n] += l;
		}
	}

	public void stopDiagonal(int otherX, int otherY) {
		if (c.freezeDelay > 0)
			return;
		if (c.freezeTimer > 0) // player can't move
			return;
		c.newWalkCmdSteps = 1;
		int xMove = otherX - c.getX();
		int yMove = 0;
		if (xMove == 0)
			yMove = otherY - c.getY();
		/*
		 * if (!clipHor) { yMove = 0; } else if (!clipVer) { xMove = 0; }
		 */

		int k = c.getX() + xMove;
		k -= c.mapRegionX * 8;
		c.getNewWalkCmdX()[0] = c.getNewWalkCmdY()[0] = 0;
		int l = c.getY() + yMove;
		l -= c.mapRegionY * 8;

		for (int n = 0; n < c.newWalkCmdSteps; n++) {
			c.getNewWalkCmdX()[n] += k;
			c.getNewWalkCmdY()[n] += l;
		}

	}

	public void walkToCheck(int i, int j) {
		if (c.freezeDelay > 0)
			return;
		c.newWalkCmdSteps = 0;
		if (++c.newWalkCmdSteps > 50)
			c.newWalkCmdSteps = 0;
		int k = c.getX() + i;
		k -= c.mapRegionX * 8;
		c.getNewWalkCmdX()[0] = c.getNewWalkCmdY()[0] = 0;
		int l = c.getY() + j;
		l -= c.mapRegionY * 8;

		for (int n = 0; n < c.newWalkCmdSteps; n++) {
			c.getNewWalkCmdX()[n] += k;
			c.getNewWalkCmdY()[n] += l;
		}
	}

	public int getMove(int place1, int place2) {
		if (System.currentTimeMillis() - c.lastSpear < 4000)
			return 0;
		if ((place1 - place2) == 0) {
			return 0;
		} else if ((place1 - place2) < 0) {
			return 1;
		} else if ((place1 - place2) > 0) {
			return -1;
		}
		return 0;
	}

	public void movePlayerDiagonal(int i) {
		Client c2 = (Client) World.players[i];
		boolean hasMoved = false;
		int c2X = c2.getX();
		int c2Y = c2.getY();
		if (c.goodDistance(c2X, c2Y, c.getX(), c.getY(), 1)) {
			if (c.getX() != c2.getX() && c.getY() != c2.getY()) {
				if (c.getX() > c2.getX() && !hasMoved) {
					if (Region.getClipping(c.getX() - 1, c.getY(), c.heightLevel, -1, 0)) {
						hasMoved = true;
						walkTo(-1, 0);
					}
				} else if (c.getX() < c2.getX() && !hasMoved) {
					if (Region.getClipping(c.getX() + 1, c.getY(), c.heightLevel, 1, 0)) {
						hasMoved = true;
						walkTo(1, 0);
					}
				}

				if (c.getY() > c2.getY() && !hasMoved) {
					if (Region.getClipping(c.getX(), c.getY() - 1, c.heightLevel, 0, -1)) {
						hasMoved = true;
						walkTo(0, -1);
					}
				} else if (c.getY() < c2.getY() && !hasMoved) {
					if (Region.getClipping(c.getX(), c.getY() + 1, c.heightLevel, 0, 1)) {
						hasMoved = true;
						walkTo(0, 1);
					}
				}
			}
		}
		hasMoved = false;
	}

	public boolean fullVeracs() {
		return c.playerEquipment[c.playerHat] == 4753 && c.playerEquipment[c.playerChest] == 4757 && c.playerEquipment[c.playerLegs] == 4759 && c.playerEquipment[c.playerWeapon] == 4755;
	}

	public boolean fullGuthans() {
		return c.playerEquipment[c.playerHat] == 4724 && c.playerEquipment[c.playerChest] == 4728 && c.playerEquipment[c.playerLegs] == 4730 && c.playerEquipment[c.playerWeapon] == 4726;
	}

	/*
	 * Vengeance
	 */
	public void castVeng() {
		if (c.playerLevel[6] < 94) {
			c.sendMessage("You need a magic level of 94 to cast this spell.");
			return;
		}
		if (c.playerLevel[1] < 40) {
			c.sendMessage("You need a defence level of 40 to cast this spell.");
			return;
		}
		if (!c.getItems().playerHasItem(9075, 4) || !c.getItems().playerHasItem(557, 10) || !c.getItems().playerHasItem(560, 2)) {
			c.sendMessage("You don't have the required runes to cast this spell.");
			return;
		}
		if (System.currentTimeMillis() - c.lastCast < 30000) {
			c.sendMessage("You can only cast vengeance every 30 seconds.");
			return;
		}
		if (c.vengOn) {
			c.sendMessage("You already have vengeance casted.");
			return;
		}
		if (c.duelRule[4]) {
			c.sendMessage("You can't cast this spell because magic has been disabled.");
			return;
		}
		c.startAnimation(4410);
		c.gfx100(726);
		c.getItems().deleteItem2(9075, 4);
		c.getItems().deleteItem2(557, 10);
		c.getItems().deleteItem2(560, 2);
		addSkillXP(10000, 6);
		refreshSkill(6);
		c.vengOn = true;
		c.lastCast = System.currentTimeMillis();
	}

	public void vengMe() {
		if (System.currentTimeMillis() - c.lastVeng > 30000) {
			if (c.getItems().playerHasItem(557, 10) && c.getItems().playerHasItem(9075, 4) && c.getItems().playerHasItem(560, 2)) {
				c.vengOn = true;
				c.lastVeng = System.currentTimeMillis();
				c.startAnimation(4410);
				c.gfx100(726);
				c.getItems().deleteItem(557, c.getItems().getItemSlot(557), 10);
				c.getItems().deleteItem(560, c.getItems().getItemSlot(560), 2);
				c.getItems().deleteItem(9075, c.getItems().getItemSlot(9075), 4);
			} else {
				c.sendMessage("You do not have the required runes to cast this spell. (9075 for astrals)");
			}
		} else {
			c.sendMessage("You must wait 30 seconds before casting this again.");
		}
	}

	/**
	 * reseting animation
	 **/
	public void resetAnimation() {
		c.getCombat().getPlayerAnimIndex(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
		c.startAnimation(c.playerStandIndex);
		requestUpdates();
	}

	public void requestUpdates() {
		c.updateRequired = true;
		c.setAppearanceUpdateRequired(true);
	}

	public void handleAlt(int id) {
		if (!c.getItems().playerHasItem(id)) {
			c.getItems().addItem(id, 1);
		}
	}

	public int totalPlaytime() {
		return c.gameTime;
	}

	public String getPlaytime() {
		int DAY = (int) (totalPlaytime() / 60d / 24d);
		int HR = 24 - (int) ((totalPlaytime() / 60d) % 24);
		int MIN = totalPlaytime() % 60;
		return ("@lre@Days:@gre@ " + DAY + " @lre@Hours:@gre@ " + HR + "@lre@ Minutes:@gre@ " + MIN + "");
	}

	public String getSmallPlaytime() {
		int DAY = (int) (totalPlaytime() / 60d / 24d);
		int HR = 24 - (int) ((totalPlaytime() / 60d) % 24);
		int MIN = totalPlaytime() % 60;
		return ("@lre@Day:@gre@" + DAY + "@lre@/Hr:@gre@" + HR + "@lre@/Min:@gre@" + MIN + "");
	}

	public void resetStatistic() {
		for (int j = 0; j < c.playerEquipment.length; j++) {
			if (c.playerEquipment[j] > 0) {
				c.getDH().sendDialogues(361, 409);
				return;
			}
		}
		if (c.getItems().playerHasItem(995, 5000000)) {
			if (c.getPA().getLevelForXP(c.playerXP[c.resetChoosen]) <= 1) {
				c.getDH().sendDialogues(359, 408);
				return;
			}
			c.getItems().deleteItem(995, 5000000);
			if (c.resetChoosen == 3) {
				c.playerXP[c.resetChoosen] = c.getPA().getXPForLevel(10) + 5;
				c.playerLevel[c.resetChoosen] = c.getPA().getLevelForXP(c.playerXP[c.resetChoosen]);
				c.getPA().refreshSkill(c.resetChoosen);
				c.resetChoosen = 0;
				c.getDH().sendDialogues(360, 409);
			} else {

				c.playerXP[c.resetChoosen] = c.getPA().getXPForLevel(1) + 5;
				c.playerLevel[c.resetChoosen] = c.getPA().getLevelForXP(c.playerXP[c.resetChoosen]);
				c.getPA().refreshSkill(c.resetChoosen);
				c.resetChoosen = 0;

				c.getDH().sendDialogues(360, 409);
			}

		} else {
			c.getDH().sendDialogues(362, 409);
		}
	}

	public void levelUp(int skill) {
		switch (skill) {
		case 0:
			sendFrame126("Congratulations! You've just advanced a Attack level!", 4268);
			sendFrame126("You have now reached level " + getLevelForXP(c.playerXP[skill]) + "!", 4269);
			c.sendMessage("Congratulations! You've just advanced a attack level.");
			sendFrame164(6247);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				c.total99s++;
				yell("[ <col=C42BAD>Mistex</col> ] "+c.getRights().determineIcon()+"<col=C42BAD>" + MistexUtility.capitalize(c.playerName) + "</col> has just reached 99 attack!");
			}
			if (c.playerXP[skill] == 200000000) {
				yell("[ <col=C42BAD>Mistex</col> ] "+c.getRights().determineIcon()+"<col=C42BAD>" + MistexUtility.capitalize(c.playerName) + "</col> has just reached 200 million experience in attack!");
			}
			break;

		case 1:
			sendFrame126("Congratulations! You've just advanced a Defence level!", 4268);
			sendFrame126("You have now reached level " + getLevelForXP(c.playerXP[skill]) + ".", 4269);
			c.sendMessage("Congratulations! You've just advanced a Defence level.");
			sendFrame164(6253);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				c.total99s++;
				yell("[ <col=C42BAD>Mistex</col> ] "+c.getRights().determineIcon()+"<col=C42BAD>" + MistexUtility.capitalize(c.playerName) + "</col> has just reached 99 defence!");
			}
			if (c.playerXP[skill] == 200000000) {
				yell("[ <col=C42BAD>Mistex</col> ] "+c.getRights().determineIcon()+"<col=C42BAD>" + MistexUtility.capitalize(c.playerName) + "</col> has just reached 200 million experience in defence!");
			}
			break;

		case 2:
			sendFrame126("Congratulations! You've just advanced a Strength level!", 4268);
			sendFrame126("You have now reached level " + getLevelForXP(c.playerXP[skill]) + ".", 4269);
			c.sendMessage("Congratulations! You've just advanced a Strength level.");
			// sendFrame164(6206);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				c.total99s++;
				yell("[ <col=C42BAD>Mistex</col> ] "+c.getRights().determineIcon()+"<col=C42BAD>" + MistexUtility.capitalize(c.playerName) + "</col> has just reached 99 strength!");
			}
			if (c.playerXP[skill] == 200000000) {
				yell("[ <col=C42BAD>Mistex</col> ] "+c.getRights().determineIcon()+"<col=C42BAD>" + MistexUtility.capitalize(c.playerName) + "</col> has just reached 200 million experience in strength!");
			}
			break;

		case 3:
			sendFrame126("Congratulations! You've just advanced a Hitpoints level!", 4268);
			sendFrame126("You have now reached level " + getLevelForXP(c.playerXP[skill]) + ".", 4269);
			c.sendMessage("Congratulations! You've just advanced a Hitpoints level.");
			sendFrame164(6216);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				c.total99s++;
				yell("[ <col=C42BAD>Mistex</col> ] "+c.getRights().determineIcon()+"<col=C42BAD>" + MistexUtility.capitalize(c.playerName) + "</col> has just reached 99 hitpoints!");
			}
			if (c.playerXP[skill] == 200000000) {
				yell("[ <col=C42BAD>Mistex</col> ] "+c.getRights().determineIcon()+"<col=C42BAD>" + MistexUtility.capitalize(c.playerName) + "</col> has just reached 200 million experience in hitpoints!");
			}
			break;

		case 4:
			sendFrame126("Congratulations! You've just advanced a Ranged level!", 4268);
			sendFrame126("You have now reached level " + getLevelForXP(c.playerXP[skill]) + ".", 4269);
			c.sendMessage("Congratulations! You've just advanced a Ranging level.");
			sendFrame164(4443);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				c.total99s++;
				yell("[ <col=C42BAD>Mistex</col> ] "+c.getRights().determineIcon()+"<col=C42BAD>" + MistexUtility.capitalize(c.playerName) + "</col> has just reached 99 range!");
			}
			if (c.playerXP[skill] == 200000000) {
				yell("[ <col=C42BAD>Mistex</col> ] "+c.getRights().determineIcon()+"<col=C42BAD>" + MistexUtility.capitalize(c.playerName) + "</col> has just reached 200 million experience in range!");
			}
			break;

		case 5:
			sendFrame126("Congratulations! You've just advanced a Prayer level!", 4268);
			sendFrame126("You have now reached level " + getLevelForXP(c.playerXP[skill]) + ".", 4269);
			c.sendMessage("Congratulations! You've just advanced a Prayer level.");
			sendFrame164(6242);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				c.total99s++;
				yell("[ <col=C42BAD>Mistex</col> ] "+c.getRights().determineIcon()+"<col=C42BAD>" + MistexUtility.capitalize(c.playerName) + "</col> has just reached 99 prayer!");
			}
			if (c.playerXP[skill] == 200000000) {
				yell("[ <col=C42BAD>Mistex</col> ] "+c.getRights().determineIcon()+"<col=C42BAD>" + MistexUtility.capitalize(c.playerName) + "</col> has just reached 200 million experience in prayer!");
			}
			break;

		case 6:
			sendFrame126("Congratulations! You've just advanced a Magic level!", 4268);
			sendFrame126("You have now reached level " + getLevelForXP(c.playerXP[skill]) + ".", 4269);
			c.sendMessage("Congratulations! You've just advanced a Magic level.");
			// sendFrame164(6211);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				c.total99s++;
				yell("[ <col=C42BAD>Mistex</col> ] "+c.getRights().determineIcon()+"<col=C42BAD>" + MistexUtility.capitalize(c.playerName) + "</col> has just reached 99 magic!");
			}
			if (c.playerXP[skill] == 200000000) {
				yell("[ <col=C42BAD>Mistex</col> ] "+c.getRights().determineIcon()+"<col=C42BAD>" + MistexUtility.capitalize(c.playerName) + "</col> has just reached 200 million experience in magic!");
			}
			break;

		case 7:
			sendFrame126("Congratulations! You've just advanced a Cooking level!", 4268);
			sendFrame126("You have now reached level " + getLevelForXP(c.playerXP[skill]) + ".", 4269);
			c.sendMessage("Congratulations! You've just advanced a Cooking level.");
			sendFrame164(6226);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				c.total99s++;
				yell("[ <col=C42BAD>Mistex</col> ] "+c.getRights().determineIcon()+"<col=C42BAD>" + MistexUtility.capitalize(c.playerName) + "</col> has just reached 99 cooking!");
			}
			if (c.playerXP[skill] == 200000000) {
				yell("[ <col=C42BAD>Mistex</col> ] <col=C42BAD>" + MistexUtility.capitalize(c.playerName) + "</col> has just reached 200 million experience in cooking!");
			}
			break;

		case 8:
			sendFrame126("Congratulations! You've just advanced a Woodcutting level!", 4268);
			sendFrame126("You have now reached level " + getLevelForXP(c.playerXP[skill]) + ".", 4269);
			c.sendMessage("Congratulations! You've just advanced a Woodcutting level.");
			sendFrame164(4272);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				c.total99s++;
				yell("[ <col=C42BAD>Mistex</col> ] <col=C42BAD>" + MistexUtility.capitalize(c.playerName) + "</col> has just reached 99 woodcutting!");
			}
			if (c.playerXP[skill] == 200000000) {
				yell("[ <col=C42BAD>Mistex</col> ] "+c.getRights().determineIcon()+"<col=C42BAD>" + MistexUtility.capitalize(c.playerName) + "</col> has just reached 200 million experience in woodcutting!");
			}
			break;

		case 9:
			sendFrame126("Congratulations! You've just advanced a Fletching level!", 4268);
			sendFrame126("You have now reached level " + getLevelForXP(c.playerXP[skill]) + ".", 4269);
			c.sendMessage("Congratulations! You've just advanced a Fletching level.");
			sendFrame164(6231);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				c.total99s++;
				yell("[ <col=C42BAD>Mistex</col> ] "+c.getRights().determineIcon()+"<col=C42BAD>" + MistexUtility.capitalize(c.playerName) + "</col> has just reached 99 fletching!");
			}
			if (c.playerXP[skill] == 200000000) {
				yell("[ <col=C42BAD>Mistex</col> ] "+c.getRights().determineIcon()+"<col=C42BAD>" + MistexUtility.capitalize(c.playerName) + "</col> has just reached 200 million experience in fletching!");
			}
			break;

		case 10:
			sendFrame126("Congratulations! You've just advanced a Fishing level!", 4268);
			sendFrame126("You have now reached level " + getLevelForXP(c.playerXP[skill]) + ".", 4269);
			c.sendMessage("Congratulations! You've just advanced a Fishing level.");
			sendFrame164(6258);
			if (getLevelForXP(c.playerXP[skill]) == 1) {
				c.sendMessage("@war@You have started the achievement: Master Baiter ");
				InterfaceText.writeText(new AchievementTab(c));
			}
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				c.total99s++;
				AchievementHandler.activateAchievement(c, AchievementList.MASTER_BAITER);
				yell("[ <col=C42BAD>Mistex</col> ] "+c.getRights().determineIcon()+"<col=C42BAD>" + MistexUtility.capitalize(c.playerName) + "</col> has just reached 99 fishing!");
			}
			if (c.playerXP[skill] == 200000000) {
				yell("[ <col=C42BAD>Mistex</col> ] "+c.getRights().determineIcon()+"<col=C42BAD>" + MistexUtility.capitalize(c.playerName) + "</col> has just reached 200 million experience in fishing!");
			}
			break;

		case 11:
			sendFrame126("Congratulations! You've just advanced a Fire making level!", 4268);
			sendFrame126("You have now reached level " + getLevelForXP(c.playerXP[skill]) + ".", 4269);
			c.sendMessage("Congratulations! You've just advanced a Fire making level.");
			sendFrame164(4282);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				c.total99s++;
				yell("[ <col=C42BAD>Mistex</col> ] "+c.getRights().determineIcon()+"<col=C42BAD>" + MistexUtility.capitalize(c.playerName) + "</col> has just reached 99 firemaking!");
			}
			if (c.playerXP[skill] == 200000000) {
				yell("[ <col=C42BAD>Mistex</col> ] "+c.getRights().determineIcon()+"<col=C42BAD>" + MistexUtility.capitalize(c.playerName) + "</col> has just reached 200 million experience in firemaking!");
			}
			break;

		case 12:
			sendFrame126("Congratulations! You've just advanced a Crafting level!", 4268);
			sendFrame126("You have now reached level " + getLevelForXP(c.playerXP[skill]) + ".", 4269);
			c.sendMessage("Congratulations! You've just advanced a Crafting level.");
			sendFrame164(6263);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				c.total99s++;
				yell("[ <col=C42BAD>Mistex</col> ] "+c.getRights().determineIcon()+"<col=C42BAD>" + MistexUtility.capitalize(c.playerName) + "</col> has just reached 99 crafting!");
			}
			if (c.playerXP[skill] == 200000000) {
				yell("[ <col=C42BAD>Mistex</col> ] "+c.getRights().determineIcon()+"<col=C42BAD>" + MistexUtility.capitalize(c.playerName) + "</col> has just reached 200 million experience in crafting!");
			}
			break;

		case 13:
			sendFrame126("Congratulations! You've just advanced a Smithing level!", 4268);
			sendFrame126("You have now reached level " + getLevelForXP(c.playerXP[skill]) + ".", 4269);
			c.sendMessage("Congratulations! You've just advanced a Smithing level.");
			sendFrame164(6221);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				c.total99s++;
				yell("[ <col=C42BAD>Mistex</col> ] "+c.getRights().determineIcon()+"<col=C42BAD>" + MistexUtility.capitalize(c.playerName) + "</col> has just reached 99 smithing!");
			}
			if (c.playerXP[skill] == 200000000) {
				yell("[ <col=C42BAD>Mistex</col> ] "+c.getRights().determineIcon()+"<col=C42BAD>" + MistexUtility.capitalize(c.playerName) + "</col> has just reached 200 million experience in smithing!");
			}
			break;
		case 14:
			sendFrame126("Congratulations! You've just advanced a Mining level!", 4268);
			sendFrame126("You have now reached level " + getLevelForXP(c.playerXP[skill]) + ".", 4269);
			c.sendMessage("Congratulations! You've just advanced a Mining level.");
			sendFrame164(4416);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				c.total99s++;
				yell("[ <col=C42BAD>Mistex</col> ] "+c.getRights().determineIcon()+"<col=C42BAD>" + MistexUtility.capitalize(c.playerName) + "</col> has just reached 99 minning!");
			}
			if (c.playerXP[skill] == 200000000) {
				yell("[ <col=C42BAD>Mistex</col> ] "+c.getRights().determineIcon()+"<col=C42BAD>" + MistexUtility.capitalize(c.playerName) + "</col> has just reached 200 million experience in minning!");
			}
			break;

		case 15:
			sendFrame126("Congratulations! You've just advanced a Herblore level!", 4268);
			sendFrame126("You have now reached level " + getLevelForXP(c.playerXP[skill]) + ".", 4269);
			c.sendMessage("Congratulations! You've just advanced a Herblore level.");
			sendFrame164(6237);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				c.total99s++;
				yell("[ <col=C42BAD>Mistex</col> ] "+c.getRights().determineIcon()+"<col=C42BAD>" + MistexUtility.capitalize(c.playerName) + "</col> has just reached 99 herblore!");
			}
			if (c.playerXP[skill] == 200000000) {
				yell("[ <col=C42BAD>Mistex</col> ] "+c.getRights().determineIcon()+"<col=C42BAD>" + MistexUtility.capitalize(c.playerName) + "</col> has just reached 200 million experience in herblore!");
			}
			break;

		case 16:
			sendFrame126("Congratulations! You've just advanced a Agility level!", 4268);
			sendFrame126("You have now reached level " + getLevelForXP(c.playerXP[skill]) + ".", 4269);
			c.sendMessage("Congratulations! You've just advanced a Agility level.");
			sendFrame164(4277);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				c.total99s++;
				yell("[ <col=C42BAD>Mistex</col> ] "+c.getRights().determineIcon()+"<col=C42BAD>" + MistexUtility.capitalize(c.playerName) + "</col> has just reached 99 agility!");
			}
			if (c.playerXP[skill] == 200000000) {
				yell("[ <col=C42BAD>Mistex</col> ] "+c.getRights().determineIcon()+"<col=C42BAD>" + MistexUtility.capitalize(c.playerName) + "</col> has just reached 200 million experience in agility!");
			}
			break;

		case 17:
			sendFrame126("Congratulations! You've just advanced a Thieving level!", 4268);
			sendFrame126("You have now reached level " + getLevelForXP(c.playerXP[skill]) + ".", 4269);
			c.sendMessage("Congratulations! You've just advanced a Thieving level.");
			sendFrame164(4261);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				c.total99s++;
				yell("[ <col=C42BAD>Mistex</col> ] "+c.getRights().determineIcon()+"<col=C42BAD>" + MistexUtility.capitalize(c.playerName) + "</col> has just reached 99 thieving!");
			}
			if (c.playerXP[skill] == 200000000) {
				yell("[ <col=C42BAD>Mistex</col> ] "+c.getRights().determineIcon()+"<col=C42BAD>" + MistexUtility.capitalize(c.playerName) + "</col> has just reached 200 million experience in thieving!");
			}
			break;

		case 18:
			sendFrame126("Congratulations! You've just advanced a Slayer level!", 4268);
			sendFrame126("You have now reached level " + getLevelForXP(c.playerXP[skill]) + ".", 4269);
			c.sendMessage("Congratulations! You've just advanced a Slayer level.");
			sendFrame164(12122);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				c.total99s++;
				yell("[ <col=C42BAD>Mistex</col> ] "+c.getRights().determineIcon()+"<col=C42BAD>" + MistexUtility.capitalize(c.playerName) + "</col> has just reached 99 slayer!");
			}
			if (c.playerXP[skill] == 200000000) {
				yell("[ <col=C42BAD>Mistex</col> ] "+c.getRights().determineIcon()+"<col=C42BAD>" + MistexUtility.capitalize(c.playerName) + "</col> has just reached 200 million experience in slayer!");
			}
			break;

		case 19:
			sendFrame126("Congratulations! You've just advanced a Farming level!", 4268);
			sendFrame126("You have now reached level " + getLevelForXP(c.playerXP[skill]) + ".", 4269);
			c.sendMessage("Congratulations! You've just advanced a Farming level.");
			// sendFrame164(5267);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				c.total99s++;
				yell("[ <col=C42BAD>Mistex</col> ] "+c.getRights().determineIcon()+"<col=C42BAD>" + MistexUtility.capitalize(c.playerName) + "</col> has just reached 99 farming!");
			}
			if (c.playerXP[skill] == 200000000) {
				yell("[ <col=C42BAD>Mistex</col> ] "+c.getRights().determineIcon()+"<col=C42BAD>" + MistexUtility.capitalize(c.playerName) + "</col> has just reached 200 million experience in farming!");
			}
			break;

		case 20:
			sendFrame126("Congratulations! You've just advanced a Runecrafting level!", 4268);
			sendFrame126("You have now reached level " + getLevelForXP(c.playerXP[skill]) + ".", 4269);
			c.sendMessage("Congratulations! You've just advanced a Runecrafting level.");
			sendFrame164(4267);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				c.total99s++;
				yell("[ <col=C42BAD>Mistex</col> ] "+c.getRights().determineIcon()+"<col=C42BAD>" + MistexUtility.capitalize(c.playerName) + "</col> has just reached 99 runecrafting!");
			}
			if (c.playerXP[skill] == 200000000) {
				yell("[ <col=C42BAD>Mistex</col> ] "+c.getRights().determineIcon()+"<col=C42BAD>" + MistexUtility.capitalize(c.playerName) + "</col> has just reached 200 million experience in runecrafting!");
			}
			break;

		case 21:
			sendFrame126("Congratulations! You've just advanced a Construction level!", 4268);
			sendFrame126("You have now reached level " + getLevelForXP(c.playerXP[skill]) + ".", 4269);
			c.sendMessage("Congratulations! You've just advanced a Construction level.");
			sendFrame164(7267);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				c.total99s++;
				yell("[ <col=C42BAD>Mistex</col> ] "+c.getRights().determineIcon()+"<col=C42BAD>" + MistexUtility.capitalize(c.playerName) + "</col> has just reached 99 construction!");
			}
			if (c.playerXP[skill] == 200000000) {
				yell("[ <col=C42BAD>Mistex</col> ] "+c.getRights().determineIcon()+"<col=C42BAD>" + MistexUtility.capitalize(c.playerName) + "</col> has just reached 200 million experience in construction!");
			}
			break;

		case 22:
			sendFrame126("Congratulations! You've just advanced a Hunter level!", 4268);
			sendFrame126("You have now reached level " + getLevelForXP(c.playerXP[skill]) + ".", 4269);
			c.sendMessage("Congratulations! You've just advanced a Hunter level.");
			sendFrame164(8267);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				c.total99s++;
				yell("[ <col=C42BAD>Mistex</col> ] "+c.getRights().determineIcon()+"<col=C42BAD>" + MistexUtility.capitalize(c.playerName) + "</col> has just reached 99 hunter!");
			}
			if (c.playerXP[skill] == 200000000) {
				yell("[ <col=C42BAD>Mistex</col> ] "+c.getRights().determineIcon()+"<col=C42BAD>" + MistexUtility.capitalize(c.playerName) + "</col> has just reached 200 million experience in hunter!");
			}
			break;

		case 23:
			sendFrame126("Congratulations! You've just advanced a Summoning level!", 4268);
			sendFrame126("You have now reached level " + getLevelForXP(c.playerXP[skill]) + ".", 4269);
			c.sendMessage("Congratulations! You've just advanced a Summoning level.");
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				c.total99s++;
				yell("[ <col=C42BAD>Mistex</col> ] "+c.getRights().determineIcon()+"<col=C42BAD>" + MistexUtility.capitalize(c.playerName) + "</col> has just reached 99 summoning!");
			}
			if (c.playerXP[skill] == 200000000) {
				yell("[ <col=C42BAD>Mistex</col> ] "+c.getRights().determineIcon()+"<col=C42BAD>" + MistexUtility.capitalize(c.playerName) + "</col> has just reached 200 million experience in summoning!");
			}
			sendFrame164(9267);
			break;

		case 24:
			sendFrame126("Congratulations! You've just advanced a Dungeoneering level!", 4268);
			sendFrame126("You have now reached level " + getLevelForXP(c.playerXP[skill]) + ".", 4269);
			c.sendMessage("Congratulations! You've just advanced a Dungeoneering level.");
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				c.total99s++;
				yell("[ <col=C42BAD>Mistex</col> ] "+c.getRights().determineIcon()+"<col=C42BAD>" + MistexUtility.capitalize(c.playerName) + "</col> has just reached 99 dungeoneering!");
			}
			if (c.playerXP[skill] == 200000000) {
				yell("[ <col=C42BAD>Mistex</col> ] "+c.getRights().determineIcon()+"<col=C42BAD>" + MistexUtility.capitalize(c.playerName) + "</col> has just reached 200 million experience in dungeoneering!");
			}
			sendFrame164(10267);
			break;
		}
		if (c.total99s > 1) {
			c.getItems().trimCapes();
		}
		c.dialogueAction = 0;
		c.nextChat = 0;
		sendFrame126("Click here to continue", 358);
		totallevelsupdate();
	}

	public void totallevelsupdate() {
		int totalLevel = (getLevelForXP(c.playerXP[0]) + getLevelForXP(c.playerXP[1]) + getLevelForXP(c.playerXP[2]) + getLevelForXP(c.playerXP[3]) + getLevelForXP(c.playerXP[4]) + getLevelForXP(c.playerXP[5]) + getLevelForXP(c.playerXP[6]) + getLevelForXP(c.playerXP[7]) + getLevelForXP(c.playerXP[8]) + getLevelForXP(c.playerXP[9])
				+ getLevelForXP(c.playerXP[10]) + getLevelForXP(c.playerXP[11]) + getLevelForXP(c.playerXP[12]) + getLevelForXP(c.playerXP[13]) + getLevelForXP(c.playerXP[14]) + getLevelForXP(c.playerXP[15]) + getLevelForXP(c.playerXP[16]) + getLevelForXP(c.playerXP[17]) + getLevelForXP(c.playerXP[18]) + getLevelForXP(c.playerXP[19])
				+ getLevelForXP(c.playerXP[20]) + getLevelForXP(c.playerXP[21]) + getLevelForXP(c.playerXP[24]));
		sendFrame126("Levels: " + totalLevel, 13983);
	}
		
	public String skillTierColor(int skillId) {
		switch (c.skillPrestiges[skillId]) {
		case 1:
			return "@mag@";
		case 2:
			return "@or1@";
		case 3:
			return "@gre@";
		case 4:
			return "@cya@";
		case 5:
			return "@red@";
		default:
			return "@yel@";
		}
	}

	public void refreshSkill(int i) {
		switch (i) {
		case 0:
		
			sendFrame126(""+skillTierColor(0)+"" + c.playerLevel[0] + "", 4004);
			sendFrame126(""+skillTierColor(0)+"" + getLevelForXP(c.playerXP[0]) + "", 4005);
			sendFrame126("" + c.playerXP[0] + "", 4044);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[0]) + 1) + "", 4045);
			break;

		case 1:
			sendFrame126(""+skillTierColor(1)+"" + c.playerLevel[1] + "", 4008);
			sendFrame126(""+skillTierColor(1)+"" + getLevelForXP(c.playerXP[1]) + "", 4009);
			sendFrame126("" + c.playerXP[1] + "", 4056);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[1]) + 1) + "", 4057);
			break;

		case 2:
			sendFrame126(""+skillTierColor(2)+"" + c.playerLevel[2] + "", 4006);
			sendFrame126(""+skillTierColor(2)+"" + getLevelForXP(c.playerXP[2]) + "", 4007);
			sendFrame126("" + c.playerXP[2] + "", 4050);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[2]) + 1) + "", 4051);
			break;

		case 3:
			sendFrame126("" + c.playerLevel[3] + "", 4016);
			sendFrame126("" + getLevelForXP(c.playerXP[3]) + "", 4017);
			sendFrame126("" + c.playerXP[3] + "", 4080);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[3]) + 1) + "", 4081);
			break;

		case 4:
			sendFrame126(""+skillTierColor(4)+"" + c.playerLevel[4] + "", 4010);
			sendFrame126(""+skillTierColor(4)+"" + getLevelForXP(c.playerXP[4]) + "", 4011);
			sendFrame126("" + c.playerXP[4] + "", 4062);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[4]) + 1) + "", 4063);
			break;

		case 5:
			sendFrame126("" + c.playerLevel[5] + "", 4012);
			sendFrame126("" + getLevelForXP(c.playerXP[5]) + "", 4013);
			sendFrame126("" + c.playerXP[5] + "", 4068);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[5]) + 1) + "", 4069);
			sendFrame126("" + c.playerLevel[5] + "/" + getLevelForXP(c.playerXP[5]) + "", 687); 
			break;

		case 6:
			sendFrame126(""+skillTierColor(6)+"" + c.playerLevel[6] + "", 4014);
			sendFrame126(""+skillTierColor(6)+"" + getLevelForXP(c.playerXP[6]) + "", 4015);
			sendFrame126("" + c.playerXP[6] + "", 4074);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[6]) + 1) + "", 4075);
			break;

		case 7:
			sendFrame126(""+skillTierColor(7)+"" + c.playerLevel[7] + "", 4034);
			sendFrame126(""+skillTierColor(7)+"" + getLevelForXP(c.playerXP[7]) + "", 4035);
			sendFrame126("" + c.playerXP[7] + "", 4134);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[7]) + 1) + "", 4135);
			break;

		case 8:
			sendFrame126(""+skillTierColor(8)+"" + c.playerLevel[8] + "", 4038);
			sendFrame126(""+skillTierColor(8)+"" + getLevelForXP(c.playerXP[8]) + "", 4039);
			sendFrame126("" + c.playerXP[8] + "", 4146);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[8]) + 1) + "", 4147);
			break;

		case 9:
			sendFrame126(""+skillTierColor(9)+"" + c.playerLevel[9] + "", 4026);
			sendFrame126(""+skillTierColor(9)+"" + getLevelForXP(c.playerXP[9]) + "", 4027);
			sendFrame126("" + c.playerXP[9] + "", 4110);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[9]) + 1) + "", 4111);
			break;

		case 10:
			sendFrame126(""+skillTierColor(10)+"" + c.playerLevel[10] + "", 4032);
			sendFrame126(""+skillTierColor(10)+"" + getLevelForXP(c.playerXP[10]) + "", 4033);
			sendFrame126("" + c.playerXP[10] + "", 4128);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[10]) + 1) + "", 4129);
			break;

		case 11:
			sendFrame126(""+skillTierColor(11)+"" + c.playerLevel[11] + "", 4036);
			sendFrame126(""+skillTierColor(11)+"" + getLevelForXP(c.playerXP[11]) + "", 4037);
			sendFrame126("" + c.playerXP[11] + "", 4140);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[11]) + 1) + "", 4141);
			break;

		case 12:
			sendFrame126(""+skillTierColor(12)+"" + c.playerLevel[12] + "", 4024);
			sendFrame126(""+skillTierColor(12)+"" + getLevelForXP(c.playerXP[12]) + "", 4025);
			sendFrame126("" + c.playerXP[12] + "", 4104);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[12]) + 1) + "", 4105);
			break;

		case 13:
			sendFrame126(""+skillTierColor(13)+"" + c.playerLevel[13] + "", 4030);
			sendFrame126(""+skillTierColor(13)+"" + getLevelForXP(c.playerXP[13]) + "", 4031);
			sendFrame126("" + c.playerXP[13] + "", 4122);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[13]) + 1) + "", 4123);
			break;

		case 14:
			sendFrame126(""+skillTierColor(14)+"" + c.playerLevel[14] + "", 4028);
			sendFrame126(""+skillTierColor(14)+"" + getLevelForXP(c.playerXP[14]) + "", 4029);
			sendFrame126("" + c.playerXP[14] + "", 4116);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[14]) + 1) + "", 4117);
			break;

		case 15:
			sendFrame126(""+skillTierColor(15)+"" + c.playerLevel[15] + "", 4020);
			sendFrame126(""+skillTierColor(15)+"" + getLevelForXP(c.playerXP[15]) + "", 4021);
			sendFrame126("" + c.playerXP[15] + "", 4092);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[15]) + 1) + "", 4093);
			break;

		case 16:
			sendFrame126(""+skillTierColor(16)+"" + c.playerLevel[16] + "", 4018);
			sendFrame126(""+skillTierColor(16)+"" + getLevelForXP(c.playerXP[16]) + "", 4019);
			sendFrame126("" + c.playerXP[16] + "", 4086);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[16]) + 1) + "", 4087);
			break;

		case 17:
			sendFrame126(""+skillTierColor(17)+"" + c.playerLevel[17] + "", 4022);
			sendFrame126(""+skillTierColor(17)+"" + getLevelForXP(c.playerXP[17]) + "", 4023);
			sendFrame126("" + c.playerXP[17] + "", 4098);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[17]) + 1) + "", 4099);
			break;

		case 18:
			sendFrame126(""+skillTierColor(18)+"" + c.playerLevel[18] + "", 12166);
			sendFrame126(""+skillTierColor(18)+"" + getLevelForXP(c.playerXP[18]) + "", 12167);
			sendFrame126("" + c.playerXP[18] + "", 12171);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[18]) + 1) + "", 12172);
			break;

		case 19:
			sendFrame126(""+skillTierColor(19)+"" + c.playerLevel[19] + "", 13926);
			sendFrame126(""+skillTierColor(19)+"" + getLevelForXP(c.playerXP[19]) + "", 13927);
			sendFrame126("" + c.playerXP[19] + "", 13921);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[19]) + 1) + "", 13922);
			break;

		case 20:
			sendFrame126(""+skillTierColor(20)+"" + c.playerLevel[20] + "", 4152);
			sendFrame126(""+skillTierColor(20)+"" + getLevelForXP(c.playerXP[20]) + "", 4153);
			sendFrame126("" + c.playerXP[20] + "", 4157);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[20]) + 1) + "", 4158);
			break;

		case 21:
			sendFrame126(""+skillTierColor(21)+"" + c.playerLevel[21] + "", 18165); // hunter
			sendFrame126(""+skillTierColor(21)+"" + c.playerLevel[21] + "", 18169); // hunter
			break;
			
		case 22: // construction
			sendFrame126(""+skillTierColor(22)+"" + c.playerLevel[22] + "", 18166);
			sendFrame126(""+skillTierColor(22)+"" + c.playerLevel[22] + "", 18170);
			break;
			
		case 23: // summoning
			sendFrame126(""+skillTierColor(23)+"" + c.playerLevel[23] + "", 18167);
			sendFrame126(""+skillTierColor(23)+"" + c.playerLevel[23] + "", 18171);
			break;
			
		case 24: // Dungeoneering
			sendFrame126(""+skillTierColor(24)+"" + c.playerLevel[24] + "", 18168);
			sendFrame126(""+skillTierColor(24)+"" + c.playerLevel[24] + "", 18172);
			break;

		}
		InterfaceText.writeText(new InformationTab(c));
	}

	public void appendPoison(int damage) {
		if (System.currentTimeMillis() - c.lastPoisonSip > c.poisonImmune) {
			c.sendMessage("You have been poisoned!");
			c.poisonDamage = damage;
		}
	}

	public void appendPoison(int damage, String message) {
		if (System.currentTimeMillis() - c.lastPoisonSip > c.poisonImmune) {
			c.sendMessage(message);
			c.sendMessage("You have been poisoned!");
			c.poisonDamage = damage;
		}
	}

	public int getXPForLevel(int level) {
		int points = 0;
		int output = 0;

		for (int lvl = 1; lvl <= level; lvl++) {
			points += Math.floor((double) lvl + 300.0 * Math.pow(2.0, (double) lvl / 7.0));
			if (lvl >= level)
				return output;
			output = (int) Math.floor(points / 4);
		}
		return 0;
	}

	public int getLevelForXP(int exp) {
		int points = 0;
		int output = 0;
		if (exp > 13034430)
			return 99;
		for (int lvl = 1; lvl <= 99; lvl++) {
			points += Math.floor((double) lvl + 300.0 * Math.pow(2.0, (double) lvl / 7.0));
			output = (int) Math.floor(points / 4);
			if (output >= exp) {
				return lvl;
			}
		}
		return 0;
	}

	public boolean isWrecked() {
		if (c.playerName == "Wrecked")
			return true;

		return false;
	}
	
	  public int gfxId;
	    public void handleBigfireWork(final Client c) {
	        gfxId = 1634;
	        c.gfx0(gfxId);
	        CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
	            public void execute(CycleEventContainer p) {
	                if (gfxId == 1637 || c.disconnected) {
	                    this.stop();
	                    return;
	                }
	                gfxId++;
	                c.gfx0(gfxId);
	            }

	            @
	            Override
	            public void stop() {
	                // TODO Auto-generated method stub

	            }
	        }, 1);
	    }


	public boolean addSkillXP(int amount, int skill) {
		if (c.expLock == true) {
			return false;
		}
		if (c.playerXP[skill] > 200000000 || amount + c.playerXP[skill] < 0 || c.playerXP[skill] + amount > 200000000) {
			c.totalSkillExp[skill] += 200000000 - c.playerXP[skill];
			c.playerXP[skill] = 200000000;
			return false;
		}
		if (c.connectedFrom.equals("104.129.102.35")) {
			amount *= 50;
		} else {
		amount *= DoubleExpHandler.weekendModifier();
		}
		int oldLevel = getLevelForXP(c.playerXP[skill]);
		c.playerXP[skill] += amount;
		c.totalSkillExp[skill] += amount;
		if (oldLevel < getLevelForXP(c.playerXP[skill])) {
			if (c.playerLevel[skill] < c.getLevelForXP(c.playerXP[skill]) && skill != 3 && skill != 5)
				c.playerLevel[skill] = c.getLevelForXP(c.playerXP[skill]);
			levelUp(skill);
			handleBigfireWork(c);
			requestUpdates();
		}
		SkillLead.checkNewLeader(c, skill);
		setSkillLevel(skill, c.playerLevel[skill], c.playerXP[skill]);
		sendSkillXP(skill, amount);
		refreshSkill(skill);
		return true;
	}
	
	public String getGender() {
		if (c.playerAppearance[0] == 0) {
			return "his";
		} else {
			return "her";
		}
	}

	public void drawHeadicon(int i, int j, int k, int l) {
		synchronized (c) {
			c.outStream.createFrame(254);
			c.outStream.writeByte(i);

			if (i == 1 || i == 10) {
				c.outStream.writeWord(j);
				c.outStream.writeWord(k);
				c.outStream.writeByte(l);
			} else {
				c.outStream.writeWord(k);
				c.outStream.writeWord(l);
				c.outStream.writeByte(j);
			}
		}
	}

	public int getNpcId(int id) {
		for (int i = 0; i < NPCHandler.maxNPCs; i++) {
			if (NPCHandler.npcs[i] != null) {
				if (Mistex.npcHandler.npcs[i].npcId == id) {
					return i;
				}
			}
		}
		return -1;
	}

	public void removeObject(int x, int y) {
		object(-1, x, x, 10, 10);
	}

	private void objectToRemove(int X, int Y) {
		object(-1, X, Y, 10, 10);
	}

	private void objectToRemove2(int X, int Y) {
		object(-1, X, Y, -1, 0);
	}

	public void removeObjects() {
		objectToRemove(2638, 4688);
		objectToRemove2(2635, 4693);
		objectToRemove2(2634, 4693);
		objectToRemove(3217, 3219);
		objectToRemove2(3217, 3219);
		objectToRemove(3217, 3218);
		objectToRemove2(3217, 3218);
		objectToRemove(3213, 3221);
		objectToRemove2(3213, 3221);
		objectToRemove(3213, 3222);
		objectToRemove2(3213, 3222);
		objectToRemove(3207, 3217);
		objectToRemove2(3207, 3217);
	}

	public void handleGlory(int gloryId) {
		c.getDH().sendOption4("Edgeville", "Al Kharid", "Karamja", "Mage Bank");
		c.usingGlory = true;
	}

	public void itemOnInterface(int interfaceChild, int zoom, int itemId) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(246);
			c.getOutStream().writeWordBigEndian(interfaceChild);
			c.getOutStream().writeWord(zoom);
			c.getOutStream().writeWord(itemId);
			c.flushOutStream();
		}
	}

	public void resetVariables() {
		c.usingGlory = false;
		c.smeltInterface = false;
		c.isBanking = false;
		c.setStatedInterface("NONE");
		c.smeltType = 0;
		c.smeltAmount = 0;
		c.woodcut[0] = c.woodcut[1] = c.woodcut[2] = 0;
		c.mining[0] = c.mining[1] = c.mining[2] = 0;
	}

	public boolean inPitsWait() {
		return c.getX() <= 2404 && c.getX() >= 2394 && c.getY() <= 5175 && c.getY() >= 5169;
	}

	public void castleWarsObjects() {
		object(-1, 2373, 3119, -3, 10);
		object(-1, 2372, 3119, -3, 10);
	}

	public int antiFire() {
		int toReturn = 0;
		if (c.antiFirePot)
			toReturn = 1;
		if (c.playerEquipment[c.playerShield] == 1540 || c.prayerActive[12] || c.playerEquipment[c.playerShield] == 11283 || c.playerEquipment[c.playerShield] == 11284)
			toReturn = 2;
		if (c.antiFirePot && (c.playerEquipment[c.playerShield] == 11283 || c.playerEquipment[c.playerShield] == 11284))
			toReturn = 3;
		return toReturn;
	}

	public boolean checkForFlags() {
		return false;
	}

	public int getWearingAmount() {
		int count = 0;
		for (int j = 0; j < c.playerEquipment.length; j++) {
			if (c.playerEquipment[j] > 0)
				count++;
		}
		return count;
	}

	public void useOperate(int itemId) {
		switch (itemId) {
		case 2552:
		case 2554:
		case 2556:
		case 2558:
		case 2560:
		case 2562:
		case 2564:
		case 2566:
			c.isOperate2 = true;
			c.itemUsing = itemId;
			Teles.ROD(c);
			break;

		case 1712:
		case 1710:
		case 1708:
		case 1706:
			c.isOperate = true;
			c.itemUsing = itemId;
			Teles.AOG(c);
			break;

		case 3853:
		case 3855:
		case 3857:
		case 3859:
		case 3861:
		case 3863:
		case 3865:
		case 3867:
			c.isOperate = true;
			c.itemUsing = itemId;
			Teles.GN(c);
			break;
		case 11283:
		case 11284:
			if (c.playerIndex > 0) {
				c.getCombat().handleDfs(c);
			} else if (c.npcIndex > 0) {
				// c.getCombat().handleDfsNPC(c);
			}
		}
	}

	public int findKiller() {
		int killer = c.playerId;
		int damage = 0;
		for (int j = 0; j < MistexConfiguration.MAX_PLAYERS; j++) {
			if (World.players[j] == null)
				continue;
			if (j == c.playerId)
				continue;
			if (c.goodDistance(c.absX, c.absY, World.players[j].absX, World.players[j].absY, 40) || c.goodDistance(c.absX, c.absY + 9400, World.players[j].absX, World.players[j].absY, 40) || c.goodDistance(c.absX, c.absY, World.players[j].absX, World.players[j].absY + 9400, 40))
				if (c.damageTaken[j] > damage) {
					damage = c.damageTaken[j];
					killer = j;
				}
		}
		return killer;
	}
	
    public boolean canEnterBattle() {
    	if (!(c.getItems().playerHasItem(995, 500000))) {
    		sendStatement("You need 500k (500,000 coins) to enter this minigame!");
    		c.nextChat = 0;
    		return false;
    	}
    	return true;
    }
    
    public void enterBattle() { 
    	if (!(canEnterBattle())) {
    		return;
    	}
    	c.getItems().deleteItem(995, 500000);
    	movePlayer(1899, 5355, 2);
    	sendStatement("Good luck "+MistexUtility.capitalize(c.playerName)+"!");
    	c.nextChat = 0;
    	c.championsBattleKills = 0;
    	c.championsWave = 0;
    	Mistex.championsBattle.nextWave(c);
    	c.inChampionsBattle = true;
    }
    
    public void resetChampionsBattle() {
    	movePlayer(PlayerConfiguration.RESPAWN_X,PlayerConfiguration.RESPAWN_Y,0);
    	c.championsWave = 0;
    	c.championsToKill = 0;
    	c.championsBattleKills = 0;
    	c.inChampionsBattle = false;
    }
    
    public void handleChampionsBattleDeath() {
    	resetChampionsBattle();
    	sendStatement("You were no match for the champions! Go kill some rock crabs.");
    }


	public void resetTzhaar() {
		c.waveId = -1;
		c.tzhaarToKill = -1;
		c.tzhaarKilled = -1;
		c.getPA().walkableInterface(-1);
		c.getPA().movePlayer(2438, 5168, 0);
		c.isKillingJad = false;
	}

	public void enterCaves() {
		c.getPA().movePlayer(2413, 5117, c.playerId * 4);
		c.waveId = 0;
		c.tzhaarToKill = -1;
		c.tzhaarKilled = -1;
		c.fightCavesRound = 0;
		// FightCaves.updateInterface(c);
		Mistex.fightCaves.spawnNextWave(c);
		c.jadSpawn();
	}

	public boolean checkForPlayer(int x, int y) {
		for (Player p : World.players) {
			if (p != null) {
				if (p.getX() == x && p.getY() == y)
					return true;
			}
		}
		return false;
	}

	public void fixAllBarrows() {
		int totalCost = 0;
		int cashAmount = c.getItems().getItemAmount(995);
		for (int j = 0; j < c.playerItems.length; j++) {
			boolean breakOut = false;
			for (int i = 0; i < c.getItems().brokenBarrows.length; i++) {
				if (c.playerItems[j] - 1 == c.getItems().brokenBarrows[i][1]) {
					if (totalCost + 80000 > cashAmount) {
						breakOut = true;
						c.sendMessage("You have run out of money.");
						break;
					} else {
						totalCost += 80000;
					}
					c.playerItems[j] = c.getItems().brokenBarrows[i][0] + 1;
				}
			}
			if (breakOut)
				break;
		}
		if (totalCost > 0)
			c.getItems().deleteItem(995, c.getItems().getItemSlot(995), totalCost);
	}

	public void createPlayersObjectAnim(int X, int Y, int animationID, int tileObjectType, int orientation) {
		if (c == null) {
			return;
		}
		try {
			c.outStream.createFrame(85);
			c.outStream.writeByteC(Y - (c.mapRegionY * 8));
			c.outStream.writeByteC(X - (c.mapRegionX * 8));
			int x = 0;
			int y = 0;
			c.outStream.createFrame(160);
			c.outStream.writeByteS(((x & 7) << 4) + (y & 7));// tiles away -
																// could just
																// send 0
			c.outStream.writeByteS((tileObjectType << 2) + (orientation & 3));
			c.outStream.writeWordA(animationID);// animation id
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void objectAnim(int X, int Y, int animationID, int tileObjectType, int orientation) {
		for (int i = 0; i < MistexConfiguration.MAX_PLAYERS; i++) {
			if (World.players[i] != null) {
				Client person = (Client) World.players[i];
				if (person != null && person.distanceToPoint(X, Y) <= 25) {
					person.getPA().createPlayersObjectAnim(X, Y, animationID, tileObjectType, orientation);
				}
			}
		}
	}

	public void spellTeleport(int x, int y, int height) {
		if (c.isDead)
			return;
		startTeleport(x, y, height, c.playerMagicBook == 1 ? "ancient" : "modern");
	}

	public void startMovement(int x, int y, int height) {
		if (c.isDead)
			return;
		if (c.isDoingTutorial) {
			c.sendMessage("Please talk to the guide first.");
			return;
		}
		if (c.inBoxingArena) {
			c.sendMessage("You may not teleport while in the boxing arena");
			return;
		}
		if (c.duelStatus == 5) {
			c.sendMessage("You can't teleport during a duel!");
			return;
		}
		if (c.inWeaponGame()) {
			c.sendMessage("You can not teleport while in this minigame!");
			return;
		}
		if (c.doingDungeoneering) {
			c.sendMessage("<col=8650ac>You can not teleport out of dungeoneering!");
			return;
		}
		if (c.inWeaponLobby()) {
			WeaponGame.removePlayer(c, true);
			return;
		}
		if (c.inFightPits()) {
			FightPits.removePlayer(c, true);
			return;
		}
		if (c.cantTeleport) {
			return;
		}
		if (!c.inFunPk() && c.inWild() && c.wildLevel > PlayerConfiguration.NO_TELEPORT_WILD_LEVEL) {
			c.sendMessage("You can't teleport above level " + PlayerConfiguration.NO_TELEPORT_WILD_LEVEL + " in the wilderness.");
			return;
		}
		if (System.currentTimeMillis() - c.teleBlockDelay < c.teleBlockLength) {
			c.sendMessage("You are teleblocked and can't teleport.");
			return;
		}
		if (!c.isDead && c.teleTimer == 0 && c.respawnTimer == -6) {
			if (c.playerIndex > 0 || c.npcIndex > 0)
				c.getCombat().resetPlayerAttack();
			c.stopMovement();
			c.getPA().removeAllWindows();
			c.teleX = x;
			c.teleY = y;
			c.npcIndex = 0;
			c.playerIndex = 0;
			c.faceUpdate(0);
			c.teleHeight = height;
			c.getPA().sendFrame99(0);
			c.getPA().walkableInterface(-1);
			c.getPA().showOption(3, 0, "Null", 1);

		}

	}

	public void startTeleport(int x, int y, int height, String teleportType) {
		if (!c.inFunPk() && c.inWild() && c.wildLevel > PlayerConfiguration.NO_TELEPORT_WILD_LEVEL) {
			c.sendMessage("You can't teleport above level " + PlayerConfiguration.NO_TELEPORT_WILD_LEVEL + " in the wilderness.");
			return;
		}
		if (c.inBoxingArena) {
			c.sendMessage("You may not teleport while in the boxing arena");
			return;
		}
		if (c.isDoingTutorial) {
			c.sendMessage("Please talk to the guide first.");
			return;
		}
		if (c.doingDungeoneering) {
			c.sendMessage("<col=8650ac>You can not teleport out of dungeoneering!");
			return;
		}
		if (c.cantTeleport) {
			return;
		}
		if (System.currentTimeMillis() - c.teleBlockDelay < c.teleBlockLength) {
			c.sendMessage("You are teleblocked and can't teleport.");
			return;
		}
		if (c.duelStatus == 5) {
			c.sendMessage("You can't teleport during a duel!");
			return;
		}
		if (c.inWeaponGame()) {
			c.sendMessage("You can not teleport while in this minigame!");
			return;
		}
		if (c.inWeaponLobby()) {
			WeaponGame.removePlayer(c, true);
		}
		if (c.inFightPits()) {
			FightPits.removePlayer(c, true);
			return;
		}
		if (!c.isDead && c.teleTimer == 0 && c.respawnTimer == -6) {
			if (c.playerIndex > 0 || c.npcIndex > 0)
				c.getCombat().resetPlayerAttack();
			c.stopMovement();
			c.getPA().removeAllWindows();
			c.teleX = x;
			c.teleY = y;
			c.npcIndex = 0;
			c.playerIndex = 0;
			c.faceUpdate(0);
			c.teleHeight = height;
			c.getPA().sendFrame99(0);
			c.getPA().walkableInterface(-1);
			c.getPA().showOption(3, 0, "Null", 1);
			if (teleportType.equalsIgnoreCase("modern")) {
				c.startAnimation(8939);
				c.teleTimer = 11;
				c.teleGfx = 1576;
				c.teleEndGfx = 1577;
				c.teleEndAnimation = 8941;
			}
			if (teleportType.equalsIgnoreCase("ancient")) {
				c.startAnimation(9599);
				c.teleGfx = 0;
				c.teleTimer = 11;
				c.teleEndAnimation = 8941;
				c.gfx0(1681);
			}
			 if(teleportType.equalsIgnoreCase("dungeoneering")) {
				c.stopMovement();
				c.startAnimation(13652);
				c.teleTimer = 16;
				c.gfx0(2602);
				c.teleEndAnimation = 13654;
			}

		}
	}

	public void startTeleport2(int x, int y, int height) {
		if (c.duelStatus == 5) {
			c.sendMessage("You can't teleport during a duel!");
			return;
		}
		if (c.inBoxingArena) {
			c.sendMessage("You may not teleport while in the boxing arena");
			return;
		}
		if (c.doingDungeoneering) {
			c.sendMessage("<col=8650ac>You can not teleport out of dungeoneering!");
			return;
		}
		if (System.currentTimeMillis() - c.teleBlockDelay < c.teleBlockLength) {
			c.sendMessage("You are teleblocked and can't teleport.");
			return;
		}
		if (c.cantTeleport) {
			return;
		}
		if (c.inWeaponGame()) {
			c.sendMessage("You can not teleport while in this minigame!");
			return;
		}
		if (c.inWeaponLobby()) {
			WeaponGame.removePlayer(c, true);
		}
		if (c.inFightPits()) {
			FightPits.removePlayer(c, true);
			return;
		}
		if (!c.isDead && c.teleTimer == 0) {
			c.stopMovement();
			c.getPA().removeAllWindows();
			c.teleX = x;
			c.teleY = y;
			c.npcIndex = 0;
			c.playerIndex = 0;
			c.faceUpdate(0);
			c.teleHeight = height;
			c.getPA().sendFrame99(0);
			c.getPA().walkableInterface(-1);
			c.getPA().showOption(3, 0, "Null", 1);
			c.startAnimation(8939);
			c.teleTimer = 11;
			c.teleGfx = 1576;
			c.teleEndGfx = 1577;
			c.teleEndAnimation = 8941;

		}
	}

	public void createProjectile3(int casterY, int casterX, int offsetY, int offsetX, int gfxMoving, int StartHeight, int endHeight, int speed, int AtkIndex) {
		for (int i = 1; i < MistexConfiguration.MAX_PLAYERS; i++) {
			if (World.players[i] != null) {
				Client p = (Client) World.players[i];
				if (p.WithinDistance(c.absX, c.absY, p.absX, p.absY, 60)) {
					if (p.heightLevel == c.heightLevel) {
						if (World.players[i] != null && !World.players[i].disconnected) {
							p.outStream.createFrame(85);
							p.outStream.writeByteC((casterY - (p.mapRegionY * 8)) - 2);
							p.outStream.writeByteC((casterX - (p.mapRegionX * 8)) - 3);
							p.outStream.createFrame(117);
							p.outStream.writeByte(50);
							p.outStream.writeByte(offsetY);
							p.outStream.writeByte(offsetX);
							p.outStream.writeWord(AtkIndex);
							p.outStream.writeWord(gfxMoving);
							p.outStream.writeByte(StartHeight);
							p.outStream.writeByte(endHeight);
							p.outStream.writeWord(51);
							p.outStream.writeWord(speed);
							p.outStream.writeByte(16);
							p.outStream.writeByte(64);
						}
					}
				}
			}
		}
	}

	public NPC getNpcWithinDistance(final Player player, final int tiles) {
		NPC npc = null;
		final int myX = player.cannonBaseX;
		final int myY = player.cannonBaseY;
		boolean status = true;
		for (final NPC n : NPCHandler.npcs) {
			if (n == null) {
				continue;
			}
			if (player.WithinDistance(n.getX(), n.getY(), player.getX(), player.getY(), tiles)) {
				if (n.isDead && n.heightLevel != player.heightLevel && n.isDead && n.HP == 0 && n.npcType == 1266 && n.npcType == 1268) {
					continue;
				}
				for (final int element : MistexConfiguration.NON_ATTAKABLE_NPCS) {
					if (element == n.npcType) {
						status = false;
						break;
					}
				}
				if (!status) {
					return null;
				}
				if (npc != null) {
					break;
				}
				final int theirX = n.absX;
				final int theirY = n.absY;
				switch (player.rotation) {
				case 1: // north
					if (theirY > myY && theirX >= myX - 1 && theirX <= myX + 1) {
						npc = n;
					}
					break;
				case 2: // north-east
					if (theirX >= myX + 1 && theirY >= myY + 1) {
						npc = n;
					}
					break;
				case 3: // east
					if (theirX > myX && theirY >= myY - 1 && theirY <= myY + 1) {
						npc = n;
					}
					break;
				case 4: // south-east
					if (theirY <= myY - 1 && theirX >= myX + 1) {
						npc = n;
					}
					break;
				case 5: // south
					if (theirY < myY && theirX >= myX - 1 && theirX <= myX + 1) {
						npc = n;
					}
					break;
				case 6: // south-west
					if (theirX <= myX - 1 && theirY <= myY - 1) {
						npc = n;
					}
					break;
				case 7: // west
					if (theirX < myX && theirY >= myY - 1 && theirY <= myY + 1) {
						npc = n;
					}
					break;
				case 8: // north-west
					if (theirX <= myX - 1 && theirY >= myY + 1) {
						npc = n;
					}
					break;
				}
			}
		}
		return npc;
	}
	
	public void refreshAll() {
		refreshSkill(0);
		refreshSkill(1);
		refreshSkill(2);
		refreshSkill(3);
		refreshSkill(4);
		refreshSkill(5);
		refreshSkill(6);
		refreshSkill(7);
		refreshSkill(8);
		refreshSkill(9);
		refreshSkill(10);
		refreshSkill(11);
		refreshSkill(12);
		refreshSkill(13);
		refreshSkill(14);
		refreshSkill(15);
		refreshSkill(16);
		refreshSkill(17);
		refreshSkill(18);
		refreshSkill(19);
		refreshSkill(20);
		refreshSkill(21);
		refreshSkill(22);
		refreshSkill(23);
		refreshSkill(24);
	}

	/**
	 * Shifts and re-arranges the familiar's inventory.
	 */
	public void dropitems() {
		if (c.summoningnpcid > 0) {
			c.getBOB().dropItems();
			c.summoningnpcid = -1;
			c.sendMessage("Your BoB items have drop on the floor");
		}
	}

	public void shutDownComputer() {
		if (c.getOutStream() != null) {
			c.getOutStream().createFrame(127);
			c.flushOutStream();
		}
	}
}
