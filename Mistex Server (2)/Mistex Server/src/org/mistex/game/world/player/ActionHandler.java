package org.mistex.game.world.player;

import org.mistex.game.Mistex;
import org.mistex.game.MistexConfiguration;
import org.mistex.game.world.clip.region.ObjectDef;
import org.mistex.game.world.content.Boxing;
import org.mistex.game.world.content.Miscellaneous;
import org.mistex.game.world.content.WebsHandler;
import org.mistex.game.world.content.achievement.AchievementHandler;
import org.mistex.game.world.content.achievement.AchievementList;
import org.mistex.game.world.content.highscores.HighScoresBoard;
import org.mistex.game.world.content.interfaces.InterfaceText;
import org.mistex.game.world.content.interfaces.impl.AchievementTab;
import org.mistex.game.world.content.interfaces.impl.InformationTab;
import org.mistex.game.world.content.minigame.fightpits.FightPits;
import org.mistex.game.world.content.minigame.pestcontrol.PestControl;
import org.mistex.game.world.content.quests.impl.AvoidingCatastrophe;
import org.mistex.game.world.content.skill.SkillingTasks;
import org.mistex.game.world.content.skill.agility.BarbarianCourse;
import org.mistex.game.world.content.skill.agility.GnomeCourse;
import org.mistex.game.world.content.skill.agility.WildernessCourse;
import org.mistex.game.world.content.skill.farming.Farming;
import org.mistex.game.world.content.skill.firemaking.Bonfire;
import org.mistex.game.world.content.skill.fishing.Fishing;
import org.mistex.game.world.content.skill.mining.Mining;
import org.mistex.game.world.content.skill.smithing.Smelting;
import org.mistex.game.world.content.skill.thieving.Thieving;
import org.mistex.game.world.content.skill.woodcutting.Woodcutting;
import org.mistex.game.world.npc.NPCHandler;
import org.mistex.game.world.shop.Shop;
import org.mistex.game.world.shop.ShopExecutor;

public class ActionHandler {

	private Client c;

	public ActionHandler(Client Client) {
		this.c = Client;
	}

	@SuppressWarnings("static-access")
	public void firstClickObject(int objectType, int obX, int obY) {
		c.clickObjectType = 0;
		c.isBanking = false;
		c.turnPlayerTo(obX, obY);
		if (Farming.harvest(c, obX, obY)) {
			return;
		}
		if (c.agilityEmote || c.doingAgility)
			return;
		final String ObjectName = ObjectDef.getObjectDef(objectType).name;
		if (ObjectName != null && (ObjectName.equalsIgnoreCase("bank booth") || ObjectName.equalsIgnoreCase("bank chest"))) {
			c.isBanking = true;
			c.getBank().openBank();
			return;
		}
		if (Mining.miningRocks(c, objectType) && c.doingDungeoneering == false) {
			Mining.attemptData(c, objectType, obX, obY);
			return;
		}
		if (Woodcutting.Tree.forId(objectType) != null) {
			Woodcutting.attemptData(c, objectType, obX, obY);
			return;
		}
		// if (c.playerRights == 3)
		// PyramidPlunder.handleObjectClick(c, objectType, obX, obY);
		GnomeCourse.handleObject(objectType, c);
		BarbarianCourse.handleObject(objectType, c);
		WildernessCourse.handleObject(objectType, c);
		if (c.playerRights == 3 /*
								 * && MistexConfiguration.SERVER_DEBUG == true
								 */)
			c.sendMessage("[ <col=255>Mistex</col> ] Objectclick 1: " + objectType);
		switch (objectType) {
		case 4389:
			c.getPA().startTeleport2(PlayerConfiguration.START_LOCATION_X, PlayerConfiguration.START_LOCATION_Y, 0);
			break;
		case 12351:
			Boxing.barrierMoving(c);
			break;
		case 733:
			c.turnPlayerTo(obX, obY);
			WebsHandler.handleWebs(c, objectType, obX, obY, 0, 1);
			break;
		case 3193:
			c.isBanking = true;
			c.getBank().openBank();
			break;
		case 5959:
			c.getPA().startTeleport2(2539, 4712, 0);
			break;
		case 6702:
		case 6703:
		case 6704:
		case 6705:
		case 6706:
		case 6707:
			c.getBarrows().useStairs();
			break;
		case 10284:
			c.getBarrows().useChest();
			break;

		case 6823:
			if (c.barrowsNpcs[0][1] == 0) {
				Mistex.npcHandler.spawnNpc(c, 2030, c.getX(), c.getY() - 1, 3, 0, 120, 25, 200, 200, true, true);
				c.barrowsNpcs[0][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
			break;

		case 6772:
			if (c.barrowsNpcs[1][1] == 0) {
				Mistex.npcHandler.spawnNpc(c, 2029, c.getX() + 1, c.getY(), 3, 0, 120, 20, 200, 200, true, true);
				c.barrowsNpcs[1][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
			break;

		case 6822:
			if (c.barrowsNpcs[2][1] == 0) {
				Mistex.npcHandler.spawnNpc(c, 2028, c.getX(), c.getY() - 1, 3, 0, 90, 17, 200, 200, true, true);
				c.barrowsNpcs[2][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
			break;

		case 6773:
			if (c.barrowsNpcs[3][1] == 0) {
				Mistex.npcHandler.spawnNpc(c, 2027, c.getX(), c.getY() - 1, 3, 0, 120, 23, 200, 200, true, true);
				c.barrowsNpcs[3][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
			break;

		case 6771:
			c.getDH().sendDialogues(29, 2026);
			break;

		case 6821:
			if (c.barrowsNpcs[5][1] == 0) {
				Mistex.npcHandler.spawnNpc(c, 2025, c.getX() - 1, c.getY(), 3, 0, 90, 19, 200, 200, true, true);
				c.barrowsNpcs[5][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
			break;
		case 8689:
			if (System.currentTimeMillis() - c.buryDelay > 1500) {
				if (c.getItems().playerHasItem(1925, 1)) {
					c.turnPlayerTo(c.objectX, c.objectY);
					c.startAnimation(2292);
					c.getItems().addItem(1927, 1);
					c.getItems().deleteItem(1925, 1);
					c.buryDelay = System.currentTimeMillis();
				} else {
					c.sendMessage("You need a bucket to milk a cow!");
				}
			}
			break;

		case 412:
			if (c.playerRights == 3) {
				c.setSidebarInterface(5, 22500);
			} else {
				c.getPA().sendStatement("Currently not released!");
			}
			break;

		case 2147:
			if (c.AcQuest == 8) {
				c.getPA().movePlayer(2068, 4450, 0);
				NPCHandler.spawnNpc2(6367, 2069, 4454, 0, 1, 800, 20, 800, 25);
			}
			break;
		case 5099:
			c.getPA().movePlayer(obX, obY - (c.absY > 9498 ? 7 : -7), 0);
			break;
		case 5110:
			c.getPA().movePlayer(c.absX - 2, c.absY - 5, 0);
			break;
		case 5111:
			c.getPA().movePlayer(c.absX + 2, c.absY + 5, 0);
			break;
		case 11721:
			c.getPA().movePlayer(c.absX - (c.absX >= 2965 ? 1 : -1), obY, 0);
			break;
		case 11716:
			c.getPA().movePlayer(c.absX - (c.absX >= 2965 ? 1 : -1), obY, 0);
			break;
		case 170:
			c.startAnimation(881);
			if (c.gameOfChess == 3) {
				c.getItems().addItem(993, 1);
				c.gameOfChess++;
				c.sendMessage("I find the key!");
				c.sendMessage("I should go talk to Frank.");
			} else {
				c.sendMessage("You dont find anything of interest.");
			}
			break;
		case 11731:
			c.getPA().movePlayer(c.absX, c.absY, c.heightLevel == 3 ? 2 : c.heightLevel == 2 ? 1 : 0);
			break;

		case 11729:
			c.getPA().movePlayer(c.absX, c.absY, c.heightLevel == 2 ? 3 : c.heightLevel == 1 ? 2 : 1);
			break;

		/* Taverly Pipe */
		case 9293:
			c.getPA().movePlayer(obX + (c.absX == 2886 ? 5 : -5), obY, 0);
			break;

		/* Brimhaven Vines */
		case 5106:
			c.getPA().movePlayer(obX - (c.absX < 2675 ? -2 : 2), obY, 0);
			break;

		case 5107:
			c.getPA().movePlayer(obX - (c.absX < 2694 ? -2 : 2), obY, 0);
			break;

		// Warriors Guild
		case 15644:
		case 15641:
			if (c.heightLevel == 2)
				Mistex.getWarriorsGuild().handleKamfreena(c, true);
			break;

		case 7129:
			if (c.objectX == 3029 && c.objectY == 4830) {
				c.getPA().movePlayer(2584, 4836, 0);
			}
			break;
		case 7132:
			if (c.objectX == 3028 && c.objectY == 4837) {
				c.getPA().movePlayer(2162, 4833, 0);
			}
			break;

		case 6552:
			if (c.playerMagicBook == 0) { // modern
				c.startAnimation(645);
				c.playerMagicBook = 1; // ancient
				c.setSidebarInterface(6, 12855); // ancient
				c.sendMessage("Ancient wisdom fills your mind.");
				c.getPA().resetAutocast();
			} else {
				c.setSidebarInterface(6, 1151); // modern
				c.startAnimation(645);
				c.playerMagicBook = 0; // modern
				c.sendMessage("You feel a drain on your memory.");
				c.autocastId = -1;
				c.getPA().resetAutocast();
			}
			break;

		case 172:
			c.sendMessage("The chest is locked, if only you had some sort of key.");
			break;

		case 410: // LUNAR ALTAR like runecrafting altars
			if (c.playerMagicBook == 0) { // lunar
				c.startAnimation(645);
				c.playerMagicBook = 2;
				c.setSidebarInterface(6, 29999);
				c.sendMessage("Lunar wisdom fills your mind.");
				c.getPA().resetAutocast();
			} else {
				c.setSidebarInterface(6, 1151); // modern
				c.startAnimation(645);
				c.playerMagicBook = 0;
				c.sendMessage("You feel a drain on your memory.");
				c.autocastId = -1;
				c.getPA().resetAutocast();
			}
			break;

		case 9356:
			if (System.currentTimeMillis() - c.jadDelay < 2500) {

			} else {
				c.getPA().enterCaves();
				c.jadDelay = System.currentTimeMillis();
			}
			break;
		case 9357:
			c.getPA().resetTzhaar();
			break;

		case 3044:
		case 14921:
			Smelting.startSmelting(c);
			c.turnPlayerTo(obX, obY);
			break;

		case 2491:
			Mining.mineEss(c, objectType);
			break;

		case 9319:
			if (c.heightLevel == 0) {
				c.getPA().movePlayer(c.absX, c.absY, 1);
			} else if (c.heightLevel == 1) {
				c.getPA().movePlayer(c.absX, c.absY, 2);
			}
			break;

		case 9320:
			if (c.heightLevel == 1) {
				c.getPA().movePlayer(c.absX, c.absY, 0);
			} else if (c.heightLevel == 2) {
				c.getPA().movePlayer(c.absX, c.absY, 1);
			}
			break;

		case 4496:
		case 4494:
			if (c.heightLevel == 2) {
				c.getPA().movePlayer(c.absX - 5, c.absY, 1);
			} else if (c.heightLevel == 1) {
				c.getPA().movePlayer(3411, 3541, 0);
			}
			break;

		case 4493:
			if (c.heightLevel == 0) {
				c.getPA().movePlayer(c.absX - 5, c.absY, 1);
			} else if (c.heightLevel == 1) {
				c.getPA().movePlayer(c.absX + 5, c.absY, 2);
			}
			break;

		case 4495:
			if (c.heightLevel == 1) {
				c.getPA().movePlayer(c.absX + 5, c.absY, 2);
			}
			break;

		case 5126:
			if (c.absY == 3554) {
				c.getPA().walkTo(0, 1);
			} else {
				c.getPA().walkTo(0, -1);
			}
			break;

		case 1755:
			if (c.objectX == 2884 && c.objectY == 9797) {
				c.getPA().movePlayer(c.absX, c.absY - 6400, 0);
			} else {
				c.sendMessage("This ladder leads to an unimportant place.");
			}
			break;
		case 1759:
			if (c.objectX == 2884 && c.objectY == 3397) {
				c.getPA().movePlayer(c.absX, c.absY + 6400, 0);
			}
			break;

		case 2006:
			Miscellaneous.clearHighscores(c);
			HighScoresBoard.getLeaderBoard().displayLeaderBoard(c);
			break;

		case 5960:
			c.getPA().startTeleport(3090, 3956, 0, "modern");
			break;

		// case 5959:
		// c.getPA().magebankteleport(2539, 4712, 0, "modern");
		// break;

		case 563:
			c.getDH().sendDialogues(300, -1);
			break;
		case 13620:
			c.getDH().sendDialogues(100, 1);
			break;
		case 13618:
			c.getDH().sendDialogues(110, 1);
			break;
		case 13617:
			c.getDH().sendDialogues(120, 1);
			break;
		case 13623:
			c.getDH().sendDialogues(130, 1);
			break;
		case 13619:
			c.getDH().sendDialogues(140, 1);
			break;

		case 14315:
			if (c.absX == 2657 && c.absY == 2639 && c.teleTimer <= 0)
				PestControl.addToWaitRoom(c);
			break;

		case 14314:
			if (c.inPcBoat())
				PestControl.leaveWaitingBoat(c);
			c.getPA().walkableInterface(-1);
			break;

		case 409:
		case 4859:
			if (c.playerLevel[5] < c.getPA().getLevelForXP(c.playerXP[5])) {
				c.startAnimation(645);
				c.playerLevel[5] = c.getPA().getLevelForXP(c.playerXP[5]);
				c.sendMessage("You recharge your prayer points.");
				c.getPA().refreshSkill(5);
				c.altarPrayers += 1;
				if (c.altarPrayers == 1) {
					c.sendMessage("@war@You have started the achievement: Holy Monk ");
				}
				InterfaceText.writeText(new AchievementTab(c));
				if (c.altarPrayers == 1000)
					AchievementHandler.activateAchievement(c, AchievementList.HOLY_MONK);
			} else {
				c.sendMessage("You already have full prayer points!");
			}
			break;

		case 13291:
			c.getDH().sendDialogues(200, -1);
			break;

		case 9369:
			if (c.teleTimer > 0) {
				return;
			}
			if (c.getY() > 5175) {
				FightPits.addPlayer(c);
			} else {
				FightPits.removePlayer(c, false);
			}
			break;

		case 9368:
			if (c.getY() < 5169) {
				FightPits.removePlayer(c, false);
			}
			break;

		case 6742:
		case 6723:
		case 6750:
		case 6731:
		case 6717:
		case 6736:
		case 6716:
		case 6735:
			c.sendMessage("The door has been sealed shut by rust.");
			break;

		case 6747:
		case 6728:
		case 6741:
		case 6722:
		case 6740:
		case 6721:
		case 6738:
		case 6719:
		case 6737:
		case 6718:
		case 6745:
		case 6726:
		case 6748:
		case 6729:
		case 6749:
		case 6730:
			// Dungeon.openDungeonDoor(c, objectType, obX, obY);
			break;

		case 6746:
		case 6727:
		case 6739:
		case 6720:
		case 6724:
		case 6743:
		case 6744:
		case 6725:
			// Dungeon.puzzleDoors(c, objectType, obX, obY);
			break;

		case 411:
			if (System.currentTimeMillis() - c.prayerBookDelay >= 3000) {
				c.prayerBookDelay = System.currentTimeMillis();
				if (c.altarPrayed == 0) {
					c.altarPrayed = 1;
					c.setSidebarInterface(5, 22500);
					c.startAnimation(645);
					c.sendMessage("You sense a surge of power flow through your body!");
					c.getCombat().resetPrayers();
				} else {
					c.altarPrayed = 0;
					c.setSidebarInterface(5, 5608);
					c.startAnimation(645);
					c.sendMessage("You sense a surge of purity flow through your body!");
					c.getCombat().resetPrayers();
				}
			} else {
				c.sendMessage("@blu@Please wait a couple of seconds!");
			}
			break;

		case 14829:
		case 14830:
		case 14827:
		case 14828:
		case 14826:
		case 14831:
			Mistex.objectManager.startObelisk(objectType);
			break;

		default:

			break;

		}
	}

	public void secondClickObject(int objectType, int obX, int obY) {
		c.clickObjectType = 0;
		c.turnPlayerTo(obX, obY);
		c.isBanking = false;
		if (Farming.inspectObject(c, obX, obY)) {
			return;
		}
		Bonfire.AddBonFireLogs(c, objectType);
		final String ObjectName = ObjectDef.getObjectDef(objectType).name;
		if (ObjectName != null && (ObjectName.equalsIgnoreCase("bank booth") || ObjectName.equalsIgnoreCase("bank chest"))) {
			c.isBanking = true;
			c.getBank().openBank();
			return;
		}

		if (c.playerRights == 3 && MistexConfiguration.SERVER_DEBUG)
			c.sendMessage("[ <col=255>Mistex</col> ] Objectclick 2: " + objectType);
		switch (objectType) {
		case 2090:
		case 2091:
		case 3042:
		case 15505:
		case 15503:
			Mining.prospectRock(c, "copper ore");
			break;
		case 2094:
		case 2095:
		case 3043:
		case 11959:
		case 11958:
		case 11957:
			Mining.prospectRock(c, "tin ore");
			break;
		case 2110:
			Mining.prospectRock(c, "blurite ore");
			break;
		case 2092:
		case 2093:
		case 11954:
		case 11955:
		case 11956:
			Mining.prospectRock(c, "iron ore");
			break;
		case 2100:
		case 2101:
			Mining.prospectRock(c, "silver ore");
			break;
		case 2098:
		case 2099:
			Mining.prospectRock(c, "gold ore");
			break;
		case 2096:
		case 2097:
			Mining.prospectRock(c, "coal");
			break;
		case 2102:
		case 2103:
			Mining.prospectRock(c, "mithril ore");
			break;
		case 2104:
		case 2105:
			Mining.prospectRock(c, "adamantite ore");
			break;
		case 2106:
		case 2107:
		case 14859:
			Mining.prospectRock(c, "runite ore");
			break;
		case 15504:
		case 11950:
		case 11949:
		case 11948:
			Mining.prospectRock(c, "clay");
			break;
		case 450:
		case 451:
			Mining.prospectNothing(c);
			break;
		default:
			break;
		}
	}

	public void thirdClickObject(int objectType, int obX, int obY) {
		c.clickObjectType = 0;
		c.isBanking = false;
		c.turnPlayerTo(obX, obY);
		final String ObjectName = ObjectDef.getObjectDef(objectType).name;
		if (ObjectName != null && (ObjectName.equalsIgnoreCase("bank booth") || ObjectName.equalsIgnoreCase("bank chest"))) {
			c.isBanking = true;
			c.getBank().openBank();
			return;
		}
		if (c.playerRights == 3 && MistexConfiguration.SERVER_DEBUG == true)
			c.sendMessage("[ <col=255>Mistex</col> ] Objectclick 2: " + objectType);
		switch (objectType) {

		default:
			break;
		}
	}

	public void firstClickNpc(int i) {
		c.clickNpcType = 0;
		c.npcClickIndex = 0;
		c.isBanking = false;
		if (c.playerRights == 3 && MistexConfiguration.SERVER_DEBUG == true) {
			c.sendMessage("[ <col=255>Mistex</col> ] Npc click 1: " + i);
		}
		// if (Mistex.npcHandler.isSummoningNpc(i)) {
		// int randomChat = MistexUtility.random(2);
		// if (randomChat == 1) {
		// c.getDH().sendDialogues(1300, c.summoningnpcid);
		// } else if (randomChat == 2) {
		// c.getDH().sendDialogues(1301, c.summoningnpcid);
		// }
		// }

		// if (c.fishTimer == 0) {
		if (Fishing.fishingNPC(i)) {
			c.fishTimer = 3;
			Fishing.fishingNPC(c, 1, i);
			return;
		}
		// }
		switch (i) {
		case 7571:
			c.getDH().sendDialogues(2400, 7571);
			break;
		case 2620:
			c.getDH().sendDialogues(2900, 2620);
			break;
		case 7143:
			c.getDH().sendDialogues(3000, 7143);
			break;
		case 300:
			c.getDH().sendDialogues(1280, 300);
			break;
		case 9711:
			ShopExecutor.open(c, 31);
			break;
		case 9713:
			c.getDH().sendDialogues(1120, 9713);
			break;
		case 9712:
			c.getDH().sendDialogues(1125, 9712);
			break;
		case 6971:
			if (c.getRights().isDonator() && c.inDonatorZone()) {
				ShopExecutor.open(c, 29);
			} else {
				ShopExecutor.open(c, 28);
			}
			break;
		case 2581:
			c.getDH().sendDialogues(2000, 2581);
			break;
		case 15976:
			if (!c.getPA().isMaxed()) {
				c.getPA().sendStatement("You must objtain all 99 stats for Max to be interested in talking!");
				return;
			}
			c.getDH().sendDialogues(111, 15976);
			break;
		case 5964:
			c.getDH().sendDialogues(95, 5964);
			break;
		case 1778:
			c.getDH().sendDialogues(70, -1);
			break;
		case 10:
			c.getDH().sendDialogues(291, 10);
			break;
		case 290:
			c.getDH().sendDialogues(340, 290);
			break;
		case 3022:
			c.getDH().sendDialogues(363, 3022);
			break;
		case 1288:
			c.getDH().sendDialogues(5002, 1288);
			break;
		case 2579:
			c.getDH().sendDialogues(475, 2579);
			break;
		case 610:
			if (c.gameOfChess == 0)
				c.getDH().sendDialogues(600, 610);
			else if (c.gameOfChess == 4)
				c.getDH().sendDialogues(627, 610);
			else {
				c.sendMessage("The cap'n notices you don't have the key and ignores you.");
			}
			break;
		case 825:
			if (c.gameOfChess == 1)
				c.getDH().sendDialogues(620, 610);
			else if (c.gameOfChess == 2)
				c.getDH().sendDialogues(622, 610);
			else {
				c.sendMessage("The slave is not interested to talk at the moment.");
			}
			break;
		case 608:
			c.getDH().sendDialogues(c.gameOfChess == 7 ? 630 : 618, 608);
			break;
		case 494:
			c.isBanking = true;
			c.getBank().openBank();
			break;
		case 2024:
			c.getDH().sendDialogues(182, -1);
			break;
		case 945:
			c.getDH().sendDialogues(250, 945);
			break;
		case 278:
			if (c.cookAss == 0) {
				c.getDH().sendDialogues(800, 278);
			} else if (c.cookAss == 1) {
				c.getDH().sendDialogues(816, 278);
			} else if (c.cookAss == 2) {
				c.getDH().sendDialogues(819, 278);
			} else if (c.cookAss == 3) {
				c.getDH().sendDialogues(824, 278);
			}
			InterfaceText.writeText(new InformationTab(c));
			break;

		/* Shops */
		case 5113:
			ShopExecutor.open(c, 20);
			break;
		case 516:
			if (!c.isDoingTutorial)
				ShopExecutor.open(c, 7);
			break;
		case 554:
			ShopExecutor.open(c, 16);
			break;
		case 520:
			ShopExecutor.open(c, 0);
			break;
		case 461:
			ShopExecutor.open(c, 8);
			break;
		case 2537:
			if (!c.isDoingTutorial)
				ShopExecutor.open(c, 9);
			break;
		case 682:
			ShopExecutor.open(c, 10);
			break;
		case 2536:
			ShopExecutor.open(c, 11);
			break;
		case 2538:
			if (!c.isDoingTutorial)
				ShopExecutor.open(c, 12);
			break;
		case 2253:
			// ShopExecutor.open(c, 13);
			Shop.openSkillcapeShop(c);
			break;
		case 2590:
			ShopExecutor.open(c, 3);
			break;
		case 3299:
			ShopExecutor.open(c, 1);
			break;
		case 519:
			// ShopExecutor.open(c, 4);
			c.getDH().sendDialogues(74, -1);
			break;

		case 4289:
			Mistex.getWarriorsGuild().handleKamfreena(c, false);
			break;

		case 1597:
			if (!c.isDoingTutorial)
				c.getDH().sendDialogues(12, i);
			break;
		case 599:
			c.getPA().showInterface(3559);
			c.canChangeAppearance = true;
			break;
		case 4297:
			c.getDH().sendDialogues(1, 4297);
			break;
		case 782:
			c.getDH().sendDialogues(20, 782);
			break;

		case 1304:
			c.getDH().sendDialogues(100, -1);
			break;

		case 3788:
			ShopExecutor.open(c, 6);
			break;

		case 251:
			ShopExecutor.open(c, 2);
			break;

		case 659:
			c.getDH().sendDialogues(5000, -1);
			break;

		case 456:
			if (c.AcQuest == 0) {
				c.getDH().sendDialogues(700, 456);
			} else if (c.AcQuest == 2) {
				c.getDH().sendDialogues(714, 456);
			} else if (c.AcQuest == 3 && AvoidingCatastrophe.hasHolyIngredients(c)) {
				c.getDH().sendDialogues(720, 456);
			} else if (c.AcQuest == 5) {
				c.getDH().sendDialogues(726, 456);
			} else if (c.AcQuest == 9) {
				c.getDH().sendDialogues(750, 456);
			} else {

			}
			break;

		case 553:
			if (c.AcQuest == 1) {
				c.getDH().sendDialogues(708, 456);
			} else if (c.AcQuest == 4) {
				c.getDH().sendDialogues(721, 456);
			} else {

			}

			break;

		case 284:
			if (c.doricsQuest == 0) {
				c.getDH().sendDialogues(445, 284);
			} else if (c.doricsQuest == 1) {
				c.getDH().sendDialogues(502, 284);
			} else if (c.doricsQuest == 2) {
				c.getDH().sendDialogues(503, 284);
			} else if (c.doricsQuest == 3) {
				c.getDH().sendDialogues(507, 284);
			}

			break;

		case 741:
			if (c.AcQuest == 6) {
				c.getDH().sendDialogues(733, 2088);
			} else {

			}

			break;

		default:

			break;
		}
	}

	public void secondClickNpc(int i) {
		c.clickNpcType = 0;
		c.npcClickIndex = 0;
		c.isBanking = false;
		if (c.playerRights == 3 && MistexConfiguration.SERVER_DEBUG == true) {
			c.sendMessage("[ <col=255>Mistex</col> ] Npc click 2: " + i);
		}
		if (Thieving.pickpocketNPC(c, i)) {
			Thieving.attemptToPickpocket(c, i);
			return;
		}
		if (c.fishTimer == 0) {
			if (Fishing.fishingNPC(i)) {
				c.fishTimer = 3;
				Fishing.fishingNPC(c, 2, i);
				return;
			}
		}
		switch (i) {
		case 2620:
			ShopExecutor.open(c, 32);
			break;
		case 5964:
			SkillingTasks.checkComplete(c);
			break;
		case 2581:
			ShopExecutor.open(c, 27);
			break;
		case 5113:
			ShopExecutor.open(c, 20);
			break;
		case 4297:
			ShopExecutor.open(c, 5);
			break;
		case 3788:
			ShopExecutor.open(c, 6);
			break;

		case 1597:
			if (!c.isDoingTutorial)
				c.getSlayer().generateTask();
			break;

		case 599:
			c.getPA().showInterface(3559);
			c.canChangeAppearance = true;
			break;

		case 251:
			ShopExecutor.open(c, 2);
			break;

		case 659:
			c.getDH().sendDialogues(5000, -1);
			break;

		default:

			break;
		}
	}

	public void thirdClickNpc(int npcType) {
		c.clickNpcType = 0;
		c.npcClickIndex = 0;
		if (c.playerRights == 3 && MistexConfiguration.SERVER_DEBUG == true)
			c.sendMessage("[ <col=255>Mistex</col> ] Npc click 3: " + npcType);
		switch (npcType) {
		case 2581:
			c.sendMessage("Sorry this feature has been removed");
			break;
		case 5113:
			ShopExecutor.open(c, 20);
			break;
		case 4297:
			ShopExecutor.open(c, 5);
			break;
		case 1597:
			if (!c.isDoingTutorial)
				ShopExecutor.open(c, 15);
			break;
		}
	}

}