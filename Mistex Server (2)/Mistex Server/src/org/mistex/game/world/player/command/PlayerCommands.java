package org.mistex.game.world.player.command;

import java.text.DecimalFormat;

import org.mistex.game.Mistex;
import org.mistex.game.MistexConfiguration;
import org.mistex.game.MistexUtility;
import org.mistex.game.world.PunishmentHandler;
import org.mistex.game.world.World;
import org.mistex.game.world.content.achievement.AchievementHandler;
import org.mistex.game.world.content.achievement.AchievementList;
import org.mistex.game.world.content.clanchat.ClanHandler;
import org.mistex.game.world.content.interfaces.InterfaceText;
import org.mistex.game.world.content.interfaces.impl.AchievementTab;
import org.mistex.game.world.content.interfaces.impl.QuestInterface;
import org.mistex.game.world.content.interfaces.impl.teleports.TrainingInterface;
import org.mistex.game.world.content.minigame.triviabot.TriviaBot;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.Player;
import org.mistex.game.world.player.PlayerConfiguration;
import org.mistex.game.world.player.PlayerSave;
import org.mistex.game.world.player.bank.BankItem;
import org.mistex.game.world.player.bank.BankTab;

public class PlayerCommands {

	@SuppressWarnings("static-access")
	public static void commands(Client c, String playerCommand) {
		if (playerCommand.startsWith("lockxp")) {
			c.expLock = true;
			c.getPA().sendStatement("Your XP is now locked! You will gain no experience.");
		}

		if (playerCommand.startsWith("unlockxp")) {
			c.expLock = false;
			c.getPA().sendStatement("Your XP is now unlocked! You will gain experience.");
		}

		if (playerCommand.equalsIgnoreCase("totalworth") || playerCommand.equalsIgnoreCase("networth") || playerCommand.equalsIgnoreCase("totalwealth")) {
			long netWorth = 0;
			for (int i = 0; i < c.playerEquipment.length; i++) {
				if (c.playerEquipment[i] > 0)
					netWorth += c.getItems().getItemShopValue(c.playerEquipment[i]) * c.playerEquipmentN[i];
			}
			for (int i = 0; i < c.playerItems.length; i++) {
				if (c.playerItems[i] > 0)
					netWorth += c.getItems().getItemShopValue(c.playerItems[i]) * c.playerItemsN[i];
			}
			for (BankTab tab : c.getBank().getBankTabs()) {
				for (BankItem item : tab.getItems()) {
					int price = c.getItems().getItemShopValue(item.getID() - 1);
					if (item.getID() == 996) {
						price = 1;
					}
					netWorth += price * item.getAmount();
				}
			}

			c.sendMessage("Your net worth is " + String.format("%, d", netWorth) + " gp. " + ((netWorth / 1000000000.0) > 1 ? "(~" + (netWorth / 1000000000) + "B)" : (netWorth / 1000000) > 1 ? "(~" + (netWorth / 1000000) + "M)" : (netWorth / 1000.0) > 1 ? "(~" + (netWorth / 1000) + "K)" : ""));
			c.forcedChat("My net worth is " + String.format("%, d", netWorth) + " gp. " + ((netWorth / 1000000000.0) > 1 ? "(~" + (netWorth / 1000000000) + "B)" : (netWorth / 1000000) > 1 ? "(~" + (netWorth / 1000000) + "M)" : (netWorth / 1000.0) > 1 ? "(~" + (netWorth / 1000) + "K)" : ""));
		}

		if (playerCommand.startsWith("/") && playerCommand.length() > 1) {
			if (c.savedClan != null) {
				playerCommand = playerCommand.substring(1);
				ClanHandler.getClan(c.savedClan).messageClan(c, playerCommand);
			} else {
				c.sendMessage("You are not in a clan.");
				c.savedClan = null;
			}
			return;
		}

		if (playerCommand.equalsIgnoreCase("train") || playerCommand.equalsIgnoreCase("skill")) {
			c.sendMessage("Alternatively, you may click on the world map.");
			c.viewingTeleportingInterface = 1;
			c.getPA().showInterface(60600);
			InterfaceText.writeText(new TrainingInterface(c));
		}

		if (playerCommand.startsWith("kdr")) {
			DecimalFormat df = new DecimalFormat("#.##");
			double ratio = ((double) c.KC) / ((double) c.DC);
			c.forcedChat("My KDR is: " + df.format(ratio));
		}

		if (playerCommand.startsWith("skipe123")) {
			// c.getPA().addSkillXP(100, 23);
		}

		if (playerCommand.startsWith("onlinestaff") || playerCommand.startsWith("staff") || playerCommand.startsWith("staffon")) {
			c.getRights().getOnlineStaff();
			c.sendMessage("[ <img=1> Online @red@Owners</col>]: " + c.getRights().onlineDevelopers);
			c.sendMessage("[ <img=1> Online @yel@Administrators</col> ]: " + c.getRights().onlineAdminstrators);
			c.sendMessage("[ <img=0> Online @blu@Moderators</col> ]: " + c.getRights().onlineModerators);

			InterfaceText.writeText(new QuestInterface(c));
			c.getPA().showInterface(8134);
			c.getPA().sendFrame126("Online Staff Members", 8144);
			c.getPA().sendFrame126("<img=2><CurrentY>Owners:", 8145);
			c.getPA().sendFrame126("", 8146);
			c.getPA().sendFrame126("" + c.getRights().onlineDevelopers + "", 8147);
			c.getPA().sendFrame126("", 8148);
			c.getPA().sendFrame126("<img=1><CurrentY>Adminstrators:", 8149);
			c.getPA().sendFrame126("", 8150);
			c.getPA().sendFrame126("" + c.getRights().onlineAdminstrators + "", 8151);
			c.getPA().sendFrame126("", 8152);
			c.getPA().sendFrame126("<img=0><CurrentY>Moderators:", 8153);
			c.getPA().sendFrame126("", 8154);
			c.getPA().sendFrame126("" + c.getRights().onlineModerators + "", 8155);
			c.getPA().sendFrame126("", 8156);
		}

		if (playerCommand.startsWith("skull")) {
			c.isSkulled = true;
			c.skullTimer = PlayerConfiguration.SKULL_TIMER;
			c.headIconPk = 0;
			c.getPA().requestUpdates();
		}

		if (playerCommand.startsWith("empty")) {
			if (c.inWild()) {
				c.sendMessage("You can not do that right now.");
				return;
			}
			if (c.emptyAgreed) {
				c.getItems().removeAllItems();
				c.sendMessage("<col=CB7E09>You empty your inventory.");
			} else {
				c.sendMessage("<col=CB7E09>Be avised, typing this command again will delete your inventory!");
				c.emptyAgreed = true;
			}
		}

		if (playerCommand.equalsIgnoreCase("players")) {
			InterfaceText.writeText(new QuestInterface(c));
			c.getPA().showInterface(8134);
			int frame = 8144;
			c.getPA().sendFrame126("Mistex Players", frame++);
			for (int i = 0; i < World.players.length; i++) {
				Player p = World.players[i];
				String name = "";
				c.getPA().sendFrame126(name, 8145 + i);
				if (p != null) {
					Client client = (Client) p;
					String rankPrefix = "<col=0000FF>";
					if (client.playerRights != 0)
						rankPrefix += "<img=" + (client.playerRights - 1) + "> ";
					name = rankPrefix + client.playerName;
					c.getPA().sendFrame126(name, frame++);
				}
			}
			c.sendMessage("<col=EB7E09>There are currently " + World.getPlayerCount() + " players online.");
		}

		if (playerCommand.equalsIgnoreCase("commands") || playerCommand.equalsIgnoreCase("command") || playerCommand.equalsIgnoreCase("COMMANDS") || playerCommand.equalsIgnoreCase("COMMAND")) {
			InterfaceText.writeText(new QuestInterface(c));
			c.getPA().showInterface(8134);
			c.getPA().sendFrame126("Mistex Commands", 8144);
			c.getPA().sendFrame126("", 8145);
			c.getPA().sendFrame126("::Home (Teleports Home)", 8146);
			c.getPA().sendFrame126("::Changepassword (Changes Password)", 8147);
			c.getPA().sendFrame126("::Kdr (Shouts KDR)", 8148);
			c.getPA().sendFrame126("::Forums (Opens Forums)", 8149);
			c.getPA().sendFrame126("::Donate (Opens Donation Page)", 8150);
			c.getPA().sendFrame126("::Vote (Opens Vote Page)", 8151);
			c.getPA().sendFrame126("::Viewprofile (View's Players Profile)", 8152);
			c.getPA().sendFrame126("::Onlinestaff (Shows All Online Staff)", 8153);
			c.getPA().sendFrame126("::Voted (Gives Voting Reward)", 8154);
			c.getPA().sendFrame126("::Players (Shows List Of Online Players)", 8155);
			c.getPA().sendFrame126("::Empty (Deletes Inventory)", 8156);
			c.getPA().sendFrame126("::Skull (Gives Skull)", 8157);
		}

		if (playerCommand.equalsIgnoreCase("rule") || playerCommand.equalsIgnoreCase("rules")) {
			InterfaceText.writeText(new QuestInterface(c));
			c.getPA().showInterface(8134);
			c.getPA().sendFrame126("Rules", 8144);
			c.getPA().sendFrame126("", 8145);
			c.getPA().sendFrame126("No advertising", 8146);
			c.getPA().sendFrame126("Auto typers minimum of 5 seconds", 8147);
			c.getPA().sendFrame126("No using cheat clients, we will catch you", 8148);
			c.getPA().sendFrame126("No abusing of any bug/glitch/exploit", 8149);
			c.getPA().sendFrame126("No staff impersonating", 8150);
			c.getPA().sendFrame126("No PvP point farming", 8151);
			c.getPA().sendFrame126("No excessive cursing", 8152);
			c.getPA().sendFrame126("No ddosing or threatening to", 8153);
			c.getPA().sendFrame126("No luring over yell", 8154);
			c.getPA().sendFrame126("No convicing others to rule break", 8155);
			c.getPA().sendFrame126("No scamming", 8156);
			c.getPA().sendFrame126("No abusing nulled accounts", 8157);
			c.getPA().sendFrame126("No disrespecting staff members", 8158);
			c.getPA().sendFrame126("No disrespecting community members", 8159);
			c.getPA().sendFrame126("No offensive language over yell", 8160);
			c.getPA().sendFrame126("No sexual harassment", 8161);
			c.getPA().sendFrame126("No spamming", 8162);
			c.getPA().sendFrame126("No asking for staff or items to staff members, especially owners", 8163);
			c.getPA().sendFrame126("No spamming staff members private messages", 8164);
			c.getPA().sendFrame126("", 8165);
		}

		if (playerCommand.equals("vote")) {
			c.getPA().sendFrame126("www.mistex.org/vote", 12000);
		}

		if (playerCommand.equals("donate")) {
			c.getPA().sendFrame126("www.mistex.org/donate", 12000);
			c.sendMessage("Thank you for considering donating!");
		}

		if (playerCommand.equals("forums") || playerCommand.equals("forum")) {
			c.getPA().sendFrame126("www.mistex.org", 12000);
		}

		if (playerCommand.startsWith("changepassword") && playerCommand.length() > 15) {
			c.playerPass = playerCommand.substring(15);
			c.sendMessage("Your password is now: " + c.playerPass);
		}

		if (playerCommand.startsWith("answer")) {
			if (System.currentTimeMillis() - c.triviaBotDelay >= 5000) {
				String answer = playerCommand.substring(7);
				TriviaBot.answerQuestion(c, answer);
				c.triviaBotDelay = System.currentTimeMillis();
			} else {
				c.sendMessage("[<col=D4021B>TriviaBot</col>]: You may only submit an answer every 5 seconds!");
			}
		}

		if (playerCommand.equalsIgnoreCase("save")) {
			PlayerSave.saveGame(c);
			c.sendMessage("<col=B35232>Your account has been saved.");
		}

		if (playerCommand.equalsIgnoreCase("home")) {
			if (System.currentTimeMillis() - c.teleportingDelay >= 7500) {
				String type = c.playerMagicBook == 0 ? "modern" : "ancient";
				c.getPA().startTeleport(PlayerConfiguration.START_LOCATION_X, PlayerConfiguration.START_LOCATION_Y, 0, type);
				c.teleportedHome += 1;
				c.teleportingDelay = System.currentTimeMillis();
				if (c.teleportedHome == 1)
					c.sendMessage("@war@You have started the achievement: Home Sick ");
				InterfaceText.writeText(new AchievementTab(c));
				if (c.teleportedHome == 500)
					AchievementHandler.activateAchievement(c, AchievementList.HOME_SICK);
			} else {
				c.sendMessage("Please wait a couple of seconds before doing this again!");
			}
		}

		if (playerCommand.equalsIgnoreCase("levels")) {
			c.forcedChat("My Levels: Attack: " + c.playerLevel[0] + ", Defence: " + c.playerLevel[1] + ", Strength: " + c.playerLevel[2] + ", Constitution: " + c.playerLevel[3] + ", Ranged: " + c.playerLevel[4] + ", Prayer: " + c.playerLevel[5] + ", Magic: " + c.playerLevel[6] + ".");
			c.forcedChatUpdateRequired = true;
		}

		if (playerCommand.startsWith("viewprofile")) {
			try {
				String playerToView = playerCommand.substring(12);
				for (int i = 0; i < MistexConfiguration.MAX_PLAYERS; i++) {
					if (World.players[i] != null) {
						if (World.players[i].playerName.equalsIgnoreCase(playerToView)) {
							Client c2 = (Client) World.players[i];
							if (playerToView.equalsIgnoreCase(c.playerName)) {
								c.sendMessage("You can't view your own profile!");
								break;
							}
							if (c.isViewingProfile) {
								c.sendMessage("@ceo@Please clear your viewer first!");
								break;
							}
							if (c2.canViewProfile == false) {
								c.sendMessage("@ceo@" + MistexUtility.capitalize(c2.playerName) + " has disabled profile viewing!");
							} else {
								c2.sendMessage("@ceo@" + MistexUtility.capitalize(c.playerName) + " </col>is viewing your profile!");
								c.isViewingProfile = true;
								c.profileViews += 1;
								c.sendMessage("You are now viewing @ceo@" + MistexUtility.capitalize(c2.playerName) + " </col>'s profile!");
								DecimalFormat df = new DecimalFormat("#.##");
								double ratio = ((double) c.KC) / ((double) c.DC);
								c.getPA().sendFrame126("Enable Profile", 29004);
								c.getPA().sendFrame126("Profile Settings", 29005);
								c.getPA().sendFrame126("Clear", 29006);
								c.getPA().sendFrame126("", 29007);
								c.getPA().sendFrame126("@lre@Profile: @" + c2.profileColour + "@" + MistexUtility.capitalize(c2.playerName) + "", 29008);
								c.getPA().sendFrame126("@lre@Date Joined: @gre@" + c2.joinDate, 29009);
								c.getPA().sendFrame126("<currentY>@or2@Statistics:", 29010);
								c.getPA().sendFrame126("@lre@Pk Points: @" + c2.profileColour + "@" + MistexUtility.format(c2.pkPoints) + "", 29011);
								c.getPA().sendFrame126("@lre@Kills: @" + c2.profileColour + "@" + MistexUtility.format(c2.KC) + "", 29012);
								c.getPA().sendFrame126("@lre@Deaths: @" + c2.profileColour + "@" + MistexUtility.format(c2.DC) + "", 29013);
								c.getPA().sendFrame126("@lre@KDR: @" + c2.profileColour + "@" + df.format(ratio), 29014);
								c.getPA().sendFrame126("@lre@Killstreak: @" + c2.profileColour + "@" + MistexUtility.format(c2.killStreak) + "", 29015);
								c.getPA().sendFrame126("@lre@Voting Points: @" + c2.profileColour + "@" + MistexUtility.format(c2.votingPoints) + "", 29016);
								c.getPA().sendFrame126("@lre@Donation Points: @" + c2.profileColour + "@" + MistexUtility.format(c2.donatorPoints) + "", 29017);
								c.getPA().sendFrame126("@lre@Achievements Completed: @" + c2.profileColour + "@" + MistexUtility.format(c2.achievementsCompleted) + "", 29018);
								c.getPA().sendFrame126("@lre@Achievement Points: @" + c2.profileColour + "@" + MistexUtility.format(c2.achievementPoints) + "", 29019);
								c.getPA().sendFrame126("", 29020);
								c.getPA().sendFrame126("<currentY>@or2@Details:", 29021);

								if (c2.playerName.contains("Play Boy")) {
									c.getPA().sendFrame126("@lre@Name: @red@" + MistexUtility.capitalize(c.playerName) + " ( +  Y  + )" + c2.name, 29022);
								} else {
									c.getPA().sendFrame126("@lre@Name: @" + c2.profileColour + "@" + c2.name, 29022);
								}
								c.getPA().sendFrame126("@lre@Age: @" + c2.profileColour + "@" + c2.ageProfile, 29023);
								c.getPA().sendFrame126("@lre@Birthplace: @" + c2.profileColour + "@" + c2.bplaceProfile + "", 29024);
								c.getPA().sendFrame126("@lre@About Me:", 29025);
								c.getPA().sendFrame126("@" + c2.profileColour + "@" + c2.aboutMe + "", 29026);
								c.getPA().sendFrame126("", 29027);
								c.getPA().sendFrame126("<currentY>@or2@Other:", 29028);
								c.getPA().sendFrame126("@lre@Food Eaten: @" + c2.profileColour + "@" + MistexUtility.format(c2.foodEaten) + "", 29029);
								c.getPA().sendFrame126("@lre@Potions Drank: @" + c2.profileColour + "@" + MistexUtility.format(c2.potionsDrank) + "", 29030);
								c.getPA().sendFrame126("@lre@Duels Won: @" + c2.profileColour + "@" + MistexUtility.format(c2.duelsWon) + "", 29031);
								c.getPA().sendFrame126("@lre@Home Teleports: @" + c2.profileColour + "@" + MistexUtility.format(c2.teleportedHome) + "", 29032);
								c.getPA().sendFrame126("@lre@Trivia Won: @" + c2.profileColour + "@" + MistexUtility.format(c2.triviaWon) + "", 29033);
								c.getPA().sendFrame126("@lre@Altar Prays: @" + c2.profileColour + "@" + MistexUtility.format(c2.altarPrayers) + "", 29034);
								c.getPA().sendFrame126("@lre@Profile Views: @" + c2.profileColour + "@" + MistexUtility.format(c2.profileViews) + "", 29035);
								c.getPA().sendFrame126("@lre@Statue Plays: @" + c2.profileColour + "@" + MistexUtility.format(c2.pkStatuePlays) + "", 29036);
								c.getPA().sendFrame126("@lre@Trades: @" + c2.profileColour + "@" + MistexUtility.format(c2.tradesCompleted) + "", 29037);
								c.getPA().sendFrame126("@lre@Appearance Change: @" + c2.profileColour + "@" + MistexUtility.format(c2.appearancesChanged) + "", 29038);
								c.getPA().sendFrame126("@lre@Corp Kills: @" + c2.profileColour + "@" + MistexUtility.format(c2.corpKills) + "", 29039);
								c.getPA().sendFrame126("@lre@Crab Kills: @" + c2.profileColour + "@" + MistexUtility.format(c2.rockCrabKills) + "", 29039);
								c.getPA().sendFrame126("@lre@Tztok Jad Kills: @" + c2.profileColour + "@" + MistexUtility.format(c2.jadKills) + "", 29040);
								c.getPA().sendFrame126("@lre@Food Cooked: @" + c2.profileColour + "@" + MistexUtility.format(c2.foodCooked) + "", 29041);
								c.getPA().sendFrame126("@lre@Logs Burned: @" + c2.profileColour + "@" + MistexUtility.format(c2.logsBurned) + "", 29042);
								c.getPA().sendFrame126("@lre@Ores Mined: @" + c2.profileColour + "@" + MistexUtility.format(c2.oresRecieved) + "", 29043);
								c.getPA().sendFrame126("@lre@Total Prestiges: @" + c2.profileColour + "@" + MistexUtility.format(c2.totalPrestiges) + "", 29044);
								if (c.profileViews == 1)
									c.sendMessage("@war@You have started the achievement: The Viewer ");
								InterfaceText.writeText(new AchievementTab(c));
								if (c.profileViews == 250)
									AchievementHandler.activateAchievement(c, AchievementList.THE_VIEWER);
							}
							break;
						}
					}
				}
			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}

		if (playerCommand.startsWith("setname")) {
			c.name = playerCommand.substring(8);
			if (playerCommand.length() >= 24) {
				c.sendMessage("@ceo@You may only have 15 characters in your name!");
				return;
			}
			c.sendMessage("@ceo@Your name is now: " + c.name);
		}

		if (playerCommand.startsWith("setage")) {
			c.ageProfile = playerCommand.substring(7);
			if (playerCommand.length() >= 10) {
				c.sendMessage("@ceo@You may only have 2 digits in your age!");
				return;
			}
			c.sendMessage("@ceo@Your age is now: " + c.ageProfile);
		}

		if (playerCommand.startsWith("setbplace")) {
			c.bplaceProfile = playerCommand.substring(10);
			if (playerCommand.length() >= 26) {
				c.sendMessage("@ceo@You may only have 15 characters in your birthplace!");
				return;
			}
			c.sendMessage("@ceo@Your birthplace is now: " + c.bplaceProfile);
		}

		if (playerCommand.startsWith("setaboutme")) {
			c.aboutMe = playerCommand.substring(11);
			if (playerCommand.length() >= 37) {
				c.sendMessage("@ceo@You may only use 25 characters");
				return;
			}
			c.sendMessage("@ceo@Your about me is now: " + c.aboutMe);
		}

		if (playerCommand.startsWith("specialtitle")) {
			String title = playerCommand.substring(13);
			c.getRights().specialTitles(title);
		}

		if (playerCommand.startsWith("yell") && c.getRights().isPlayer()) {
			c.sendMessage("The yell command has changed by using '>>' characters instead of ::yell.");
			c.sendMessage("If you wish to yell, use this as a framework: ' >> Hello Mistex! '");
		}

		if (playerCommand.startsWith(">>") && c.getRights().isPlayer()) {
			// c.sendMessage("Only donators can do this!");
			if (!PunishmentHandler.isMuted(c)) {
				String string = playerCommand.substring(2);
				string = MistexUtility.formatSentence(string.trim());
				if (string.contains("@") || string.contains("<img=") || string.contains("<col=") || string.contains("<shade=")) {
					c.sendMessage("You may not use that symbol!");
					return;
				}
				if (PunishmentHandler.isMuted(c)) {
					c.sendMessage("You are muted! You may not yell.");
					return;
				}
				if (System.currentTimeMillis() - c.yellTimer >= 10000) {
					c.yellTimer += System.currentTimeMillis();
				} else {
					c.sendMessage("Please wait to yell again! Donate to remove this timer.");
					return;
				}
				// if (c.isDonator == 0) {
				// c.sendMessage("This is a donator only feature!");
				// return;
				// }
				for (int j = 0; j < Mistex.playerHandler.players.length; j++) {
					if (Mistex.playerHandler.players[j] != null) {
						Client c2 = (Client) Mistex.playerHandler.players[j];
						if (c2.wantYellChannel) {
							c.yellTimer = System.currentTimeMillis();
							c2.sendMessage(c.getPA().getYellRank() + " " + MistexUtility.capitalize(c.playerName) + ": " + string);
						}
					}
				}
			}
		}

	}

}