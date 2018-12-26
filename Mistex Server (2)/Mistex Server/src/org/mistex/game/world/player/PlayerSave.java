package org.mistex.game.world.player;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.mistex.game.Mistex;
import org.mistex.game.MistexUtility;
import org.mistex.game.world.player.bank.BankItem;

@SuppressWarnings("static-access")
public class PlayerSave {

	public static int loadGame(Client p, String playerName, String playerPass) {
		String line = "";
		String token = "";
		String token2 = "";
		String[] token3 = new String[3];
		boolean EndOfFile = false;
		int ReadMode = 0;
		BufferedReader characterfile = null;
		boolean File1 = false;
		try {
			characterfile = new BufferedReader(new FileReader("./Data/characters/" + playerName + ".txt"));
			File1 = true;
		} catch (FileNotFoundException fileex1) {
		}

		if (File1) {
		} else {
			MistexUtility.println(playerName + ": character file not found.");
			p.newPlayer = false;
			return 0;
		}
		try {
			line = characterfile.readLine();
		} catch (IOException ioexception) {
			MistexUtility.println(playerName + ": error loading file.");
			return 3;
		}
		while (EndOfFile == false && line != null) {
			line = line.trim();
			int spot = line.indexOf("=");
			if (spot > -1) {
				token = line.substring(0, spot);
				token = token.trim();
				token2 = line.substring(spot + 1);
				token2 = token2.trim();
				token3 = token2.split("\t");
				switch (ReadMode) {
				case 1:
					if (token.equals("character-password")) {
						if (playerPass.equalsIgnoreCase(token2) || MistexUtility.basicEncrypt(playerPass).equals(token2)) {
							playerPass = token2;
						} else {
							return 3;
						}
					}
					break;
				case 2:
					if (token.equals("brothersKilled")) {
						for (int j = 0; j < token3.length; j++) {
							p.brotherKilled[j] = Boolean.parseBoolean(token3[j]);
						}
					} else if (token.equals("skillTaskName")) {
						p.skillTaskName = token2;
					} else if (token.equals("skillTaskAmount")) {
						p.skillTaskAmount = Integer.parseInt(token2);
					} else if (token.equals("brothers")) {
						p.brotherKills = Integer.parseInt(token2);
					} else if (token.equals("savedClan")) {
						p.savedClan = token2;
					} else if (token.equals("lastBrother")) {
						p.lastBrother = Integer.parseInt(token2);
					} else if (token.equals("counterxp")) {
						p.counterxp = Integer.parseInt(token2);
					} else if (token.equals("totalTasks")) {
						p.totalTasks = Integer.parseInt(token2);
					} else if (token.equals("doricsQuest")) {
						p.doricsQuest = Integer.parseInt(token2);
					} else if (token.equals("slayerTask")) {
						p.slayerTask = Integer.parseInt(token2);
					} else if (token.equals("character-height")) {
						p.heightLevel = Integer.parseInt(token2);
					} else if (token.equals("character-posx")) {
						p.teleportToX = (Integer.parseInt(token2) <= 0 ? 3210 : Integer.parseInt(token2));
					} else if (token.equals("character-posy")) {
						p.teleportToY = (Integer.parseInt(token2) <= 0 ? 3424 : Integer.parseInt(token2));
					} else if (token.equals("character-rights")) {
						p.playerRights = Integer.parseInt(token2);
					} else if (token.equals("skull-timer")) {
						p.skullTimer = Integer.parseInt(token2);
					} else if (token.equals("last-vote")) {
						p.vote = Long.parseLong(token2);
					} else if (token.equals("gameTime")) {
						p.gameTime = Integer.parseInt(token2);
					} else if (token.equals("magic-book")) {
						p.playerMagicBook = Integer.parseInt(token2);
					} else if (token.equals("summonId")) {
						p.summonId = Integer.parseInt(token2);
					}
                                        else if (token.equals("has-npc")) {
						p.hasNpc = Boolean.parseBoolean(token2);
					} else if (token.equals("bankPin1")) {
						p.bankPin1 = Integer.parseInt(token2);
					} else if (token.equals("bankPin2")) {
						p.bankPin2 = Integer.parseInt(token2);
					} else if (token.equals("bankPin3")) {
						p.bankPin3 = Integer.parseInt(token2);
					} else if (token.equals("bankPin4")) {
						p.bankPin4 = Integer.parseInt(token2);
					} else if (token.equals("hasBankpin")) {
						p.hasBankpin = Boolean.parseBoolean(token2);
					} else if (token.equals("pinRegisteredDeleteDay")) {
						p.pinDeleteDateRequested = Integer.parseInt(token2);
					} else if (token.equals("requestPinDelete")) {
						p.requestPinDelete = Boolean.parseBoolean(token2);
					} else if (token.equals("removeBankMessage")) {
						p.removeBankMessage = Boolean.parseBoolean(token2);
					} else if (token.equals("cookAss")) {
						p.cookAss = Integer.parseInt(token2);
					} else if (token.equals("special-amount")) {
						p.specAmount = Double.parseDouble(token2);
					} else if (token.equals("gameOfChess")) {
						p.gameOfChess = Integer.parseInt(token2);
					} else if (token.equals("AcQuest")) {
						p.AcQuest = Integer.parseInt(token2);
					} else if (token.equals("selected-coffin")) {
						p.randomCoffin = Integer.parseInt(token2);
					} else if (token.equals("targetIndex")) {
						p.targetIndex = Integer.parseInt(token2);
					} else if (token.equals("EPMINUTES")) {
						p.EP_MINUTES = Integer.parseInt(token2);
					} else if (token.equals("EP")) {
						p.EP = Integer.parseInt(token2);
					} else if (token.equals("safeTimer")) {
						p.safeTimer = Integer.parseInt(token2);
					} else if (token.equals("TargetPercentage")) {
						p.targetPercentage = Integer.parseInt(token2);
					} else if (token.equals("lastLoginDate")) {
						p.lastLoginDate = Integer.parseInt(token2);
					} else if (token.equals("skillingPoints")) {
						p.skillingPoints = Integer.parseInt(token2);
					} else if (token.equals("slayerPoints")) {
						p.slayerPoints = Integer.parseInt(token2);
					} else if (token.equals("votePoints")) {
						p.votingPoints = Integer.parseInt(token2);
					} else if (token.equals("pkPoints")) {
						p.pkPoints = Integer.parseInt(token2);
					} else if (token.equals("slayerTask")) {
						p.slayerTask = Integer.parseInt(token2);
					} else if (token.equals("TaskType")) {
						p.taskType = Integer.parseInt(token2);
					} else if (token.equals("join-date")) {
						p.joinDate = token2;
					} else if (token.equals("teleblock-length")) {
						p.teleBlockDelay = System.currentTimeMillis();
						p.teleBlockLength = Integer.parseInt(token2);
					} else if (token.equals("pc-points")) {
						p.pcPoints = Integer.parseInt(token2);
					} else if (token.equals("killStreak")) {
						p.killStreak = Integer.parseInt(token2);
					} else if (token.equals("earningPotential")) {
						p.earningPotential = Integer.parseInt(token2);
					} else if (token.equals("isDonator")) {
						p.isDonator = Integer.parseInt(token2);
					} else if (token.equals("donarPoints")) {
						p.donatorPoints = Integer.parseInt(token2);
					} else if (token.equals("totalDonations")) {
						p.totalDonations = Integer.parseInt(token2);
					} else if (token.equals("character-title")) {
						p.playerTitle = Integer.parseInt(token2);
					} else if (line.startsWith("KC")) {
						p.KC = Integer.parseInt(token2);
					} else if (line.startsWith("DC")) {
						p.DC = Integer.parseInt(token2);
					} else if (token.equals("character-expLock")) {
						p.expLock = Boolean.parseBoolean(token2);
					} else if (token.equals("autoRet")) {
						p.autoRet = Integer.parseInt(token2);
					} else if (token.equals("flagged")) {
						p.accountFlagged = Boolean.parseBoolean(token2);
					} else if (token.equals("mute-end")) {
						p.muteEnd = Long.parseLong(token2);
					} else if (token.equals("ban-start")) {
						p.banStart = Long.parseLong(token2);
					} else if (token.equals("ban-end")) {
						p.banEnd = Long.parseLong(token2);
					} else if (token.equals("taskAmount")) {
						p.taskAmount = Integer.parseInt(token2);
					} else if (token.equals("void")) {
						for (int j = 0; j < token3.length; j++) {
							p.voidStatus[j] = Integer.parseInt(token3[j]);
						}
					} else if (token.equals("removedTask0")) {
						p.removedTasks[0] = Integer.parseInt(token2);
					} else if (token.equals("removedTask1")) {
						p.removedTasks[1] = Integer.parseInt(token2);
					} else if (token.equals("removedTask2")) {
						p.removedTasks[2] = Integer.parseInt(token2);
					} else if (token.equals("removedTask3")) {
						p.removedTasks[3] = Integer.parseInt(token2);
					} else if (token.equals("fightMode")) {
						p.fightMode = Integer.parseInt(token2);
					} else if (token.equals("yellTag")) {
						p.yellTag = token2;
					} else if (token.equals("setTheAppearance")) {
						p.setTheAppearance = Boolean.parseBoolean(token2);
					} else if (token.equals("totalSkillExp")) {
						for (int i = 0; i < 25; i++)
							p.totalSkillExp[i] = Integer.parseInt(token3[i]);
					} else if (token.equals("skillPrestiges")) {
						for (int i = 0; i < 25; i++)
							p.skillPrestiges[i] = Integer.parseInt(token3[i]);
					} else if (token.equals("hasActivePerk")) {
						p.hasActivePerk = Boolean.parseBoolean(token2);
					} else if (token.equals("activeCycles")) {
						p.activeCycles = Integer.parseInt(token2);
					} else if (token.equals("coolDown")) {
						p.coolDown = Integer.parseInt(token2);

						/** Toggles **/
					} else if (token.equals("wantTrivia")) {
						p.wantTriviaBot = Boolean.parseBoolean(token2);
						/** Player Profile **/
					} else if (token.equals("canViewProfile")) {
						p.canViewProfile = Boolean.parseBoolean(token2);
					} else if (token.equals("profileColour")) {
						p.profileColour = token2;
					} else if (token.equals("name")) {
						p.name = token2;
					} else if (token.equals("ageProfile")) {
						p.ageProfile = token2;
					} else if (token.equals("bplaceProfile")) {
						p.bplaceProfile = token2;
					} else if (token.equals("aboutMe")) {
						p.aboutMe = token2;
						/** Prestiges **/
					} else if (token.equals("totalPrestiges")) {
						p.totalPrestiges = Integer.parseInt(token2);
					} else if (token.equals("prestigePoints")) {
						p.prestigePoints = Integer.parseInt(token2);
					} else if (token.equals("perksActivated")) {
						for (int i = 0; i < token3.length; i++)
							p.perksActivated[i] = Boolean.parseBoolean(token3[i]);
						/** Achievements **/
					} else if (token.equals("achievementsCompleted")) {
						p.achievementsCompleted = Integer.parseInt(token2);
					} else if (token.equals("achievementPoints")) {
						p.achievementPoints = Integer.parseInt(token2);
					} else if (token.equals("currentlyComepletedAll")) {
						p.currentlyCompletedAll = Boolean.parseBoolean(token2);
					} else if (token.equals("foodEaten")) {
						p.foodEaten = Integer.parseInt(token2);
					} else if (token.equals("potionsDrank")) {
						p.potionsDrank = Integer.parseInt(token2);
					} else if (token.equals("duelsWon")) {
						p.duelsWon = Integer.parseInt(token2);
					} else if (token.equals("teleportedHome")) {
						p.teleportedHome = Integer.parseInt(token2);
					} else if (token.equals("triviaWon")) {
						p.triviaWon = Integer.parseInt(token2);
					} else if (token.equals("altarPrayed")) {
						p.altarPrayed = Integer.parseInt(token2);
					} else if (token.equals("altarPrayers")) {
						p.altarPrayers = Integer.parseInt(token2);
					} else if (token.equals("profileViews")) {
						p.profileViews = Integer.parseInt(token2);
					} else if (token.equals("pkStatuePlays")) {
						p.pkStatuePlays = Integer.parseInt(token2);
					} else if (token.equals("tradesCompleted")) {
						p.tradesCompleted = Integer.parseInt(token2);
					} else if (token.equals("appearancesChanged")) {
						p.appearancesChanged = Integer.parseInt(token2);
					} else if (token.equals("corpKills")) {
						p.corpKills = Integer.parseInt(token2);
					} else if (token.equals("rockCrabKills")) {
						p.rockCrabKills = Integer.parseInt(token2);
					} else if (token.equals("jadKills")) {
						p.jadKills = Integer.parseInt(token2);
					} else if (token.equals("yewsCut")) {
						p.yewsCut = Integer.parseInt(token2);
					} else if (token.equals("foodCooked")) {
						p.foodCooked = Integer.parseInt(token2);
					} else if (token.equals("logsBurned")) {
						p.logsBurned = Integer.parseInt(token2);
					} else if (token.equals("oresRecieved")) {
						p.oresRecieved = Integer.parseInt(token2);
					} else if (token.equals("playerReported")) {
						p.playerReported = Boolean.parseBoolean(token2);
					} else if (token.equals("killedF2p")) {
						p.killedF2p = Boolean.parseBoolean(token2);
					} else if (token.equals("hit900")) {
						p.hit900 = Boolean.parseBoolean(token2);
					} else if (token.equals("gotCompletionist")) {
						p.gotCompletionist = Boolean.parseBoolean(token2);
					} else if (token.equals("privateChat")) {
						p.privateChat = Integer.parseInt(token2);
					} else if (token.equals("questCompleted")) {
						p.questCompleted = Integer.parseInt(token2);

					}
					break;
				case 3:
					if (token.equals("character-equip")) {
						p.playerEquipment[Integer.parseInt(token3[0])] = Integer.parseInt(token3[1]);
						p.playerEquipmentN[Integer.parseInt(token3[0])] = Integer.parseInt(token3[2]);
					}
					break;
				case 4:
					if (token.equals("character-look")) {
						p.playerAppearance[Integer.parseInt(token3[0])] = Integer.parseInt(token3[1]);
					}
					break;
				case 5:
					if (token.equals("character-skill")) {
						p.playerLevel[Integer.parseInt(token3[0])] = Integer.parseInt(token3[1]);
						p.playerXP[Integer.parseInt(token3[0])] = Integer.parseInt(token3[2]);
					}
					break;
				case 6:
					if (token.equals("character-item")) {
						p.playerItems[Integer.parseInt(token3[0])] = Integer.parseInt(token3[1]);
						p.playerItemsN[Integer.parseInt(token3[0])] = Integer.parseInt(token3[2]);
					}
					break;
				case 7:
					if (token.equals("character-bank")) {
						p.getBank().getTab(0).addItem(Integer.parseInt(token3[1]), Integer.parseInt(token3[2]));
					}
					break;
				case 8:
					if (token.equals("character-friend")) {
						p.friends[Integer.parseInt(token3[0])] = Long.parseLong(token3[1]);
					}
					break;
				case 9:
					/*
					 * if (token.equals("character-ignore")) {
					 * ignores[Integer.parseInt(token3[0])] =
					 * Long.parseLong(token3[1]); }
					 */
					break;
				case 10:
					if (token.startsWith("allotment")) {
						int index = Integer.parseInt(token.split("allotment")[1]);
						p.getAllotment().allotmentHarvest[index] = Integer.parseInt(token3[0]);
						p.getAllotment().allotmentSeeds[index] = Integer.parseInt(token3[1]);
						p.getAllotment().allotmentStages[index] = Integer.parseInt(token3[2]);
						p.getAllotment().allotmentState[index] = Integer.parseInt(token3[3]);
						p.getAllotment().allotmentTimer[index] = Long.parseLong(token3[4]);
						p.getAllotment().diseaseChance[index] = Double.parseDouble(token3[5]);
						p.getAllotment().hasFullyGrown[index] = Boolean.parseBoolean(token3[6]);
					} else if (token.startsWith("bush")) {
						int i = Integer.parseInt(token.split("bush")[1]);
						p.getBushes().bushesSeeds[i] = Integer.parseInt(token3[0]);
						p.getBushes().bushesStages[i] = Integer.parseInt(token3[1]);
						p.getBushes().bushesState[i] = Integer.parseInt(token3[2]);
						p.getBushes().bushesTimer[i] = Long.parseLong(token3[3]);
						p.getBushes().diseaseChance[i] = Double.parseDouble(token3[4]);
						p.getBushes().hasFullyGrown[i] = Boolean.parseBoolean(token3[5]);
					} else if (token.startsWith("herb")) {
						int i = Integer.parseInt(token.split("herb")[1]);
						p.getHerbs().herbHarvest[i] = Integer.parseInt(token3[0]);
						p.getHerbs().herbSeeds[i] = Integer.parseInt(token3[1]);
						p.getHerbs().herbStages[i] = Integer.parseInt(token3[2]);
						p.getHerbs().herbState[i] = Integer.parseInt(token3[3]);
						p.getHerbs().herbTimer[i] = Long.parseLong(token3[4]);
						p.getHerbs().diseaseChance[i] = Double.parseDouble(token3[5]);
					} else if (token.startsWith("tree")) {
						int i = Integer.parseInt(token.split("tree")[1]);
						p.getTrees().treeHarvest[i] = Integer.parseInt(token3[0]);
						p.getTrees().treeSaplings[i] = Integer.parseInt(token3[1]);
						p.getTrees().treeStages[i] = Integer.parseInt(token3[2]);
						p.getTrees().treeState[i] = Integer.parseInt(token3[3]);
						p.getTrees().treeTimer[i] = Long.parseLong(token3[4]);
						p.getTrees().diseaseChance[i] = Double.parseDouble(token3[5]);
						p.getTrees().hasFullyGrown[i] = Boolean.parseBoolean(token3[6]);
					} else if (token.startsWith("fruitTree")) {
						int i = Integer.parseInt(token.split("fruitTree")[1]);
						p.getFruitTrees().fruitTreeSaplings[i] = Integer.parseInt(token3[0]);
						p.getFruitTrees().fruitTreeStages[i] = Integer.parseInt(token3[1]);
						p.getFruitTrees().fruitTreeState[i] = Integer.parseInt(token3[2]);
						p.getFruitTrees().fruitTreeTimer[i] = Long.parseLong(token3[3]);
						p.getFruitTrees().diseaseChance[i] = Double.parseDouble(token3[4]);
						p.getFruitTrees().hasFullyGrown[i] = Boolean.parseBoolean(token3[5]);
					} else if (token.startsWith("hop")) {
						int i = Integer.parseInt(token.split("hop")[1]);
						p.getHops().hopsHarvest[i] = Integer.parseInt(token3[0]);
						p.getHops().hopsSeeds[i] = Integer.parseInt(token3[1]);
						p.getHops().hopsStages[i] = Integer.parseInt(token3[2]);
						p.getHops().hopsState[i] = Integer.parseInt(token3[3]);
						p.getHops().hopsTimer[i] = Long.parseLong(token3[4]);
						p.getHops().diseaseChance[i] = Double.parseDouble(token3[5]);
						p.getHops().hasFullyGrown[i] = Boolean.parseBoolean(token3[6]);
					} else if (token.startsWith("flower")) {
						int i = Integer.parseInt(token.split("flower")[1]);
						p.getFlowers().flowerSeeds[i] = Integer.parseInt(token3[0]);
						p.getFlowers().flowerStages[i] = Integer.parseInt(token3[1]);
						p.getFlowers().flowerState[i] = Integer.parseInt(token3[2]);
						p.getFlowers().flowerTimer[i] = Long.parseLong(token3[3]);
						p.getFlowers().diseaseChance[i] = Double.parseDouble(token3[4]);
						p.getFlowers().hasFullyGrown[i] = Boolean.parseBoolean(token3[5]);
					}
					break;
				}
			} else {
				if (line.equals("[ACCOUNT]")) {
					ReadMode = 1;
				} else if (line.equals("[CHARACTER]")) {
					ReadMode = 2;
				} else if (line.equals("[EQUIPMENT]")) {
					ReadMode = 3;
				} else if (line.equals("[LOOK]")) {
					ReadMode = 4;
				} else if (line.equals("[SKILLS]")) {
					ReadMode = 5;
				} else if (line.equals("[ITEMS]")) {
					ReadMode = 6;
				} else if (line.equals("[BANK]")) {
					ReadMode = 7;
				} else if (line.equals("[FRIENDS]")) {
					ReadMode = 8;
				} else if (line.equals("[IGNORES]")) {
					ReadMode = 9;
				} else if (line.equals("[FARMING]")) {
					ReadMode = 10;
				} else if (line.equals("[EOF]")) {
					try {
						characterfile.close();
					} catch (IOException ioexception) {
					}
					return 1;
				}
			}
			try {
				line = characterfile.readLine();
			} catch (IOException ioexception1) {
				EndOfFile = true;
			}
		}
		try {
			characterfile.close();
		} catch (IOException ioexception) {
		}
		return 13;
	}

	/**
	 * Saving
	 **/
	public static boolean saveGame(Client p) {
		if (!p.saveFile || p.newPlayer || !p.saveCharacter) {
			// System.out.println("first");
			return false;
		}
		if (p.playerName == null || Mistex.playerHandler.players[p.playerId] == null) {
			// System.out.println("second");
			return false;
		}
		p.playerName = p.playerName2;
		int tbTime = (int) (p.teleBlockDelay - System.currentTimeMillis() + p.teleBlockLength);
		if (tbTime > 300000 || tbTime < 0) {
			tbTime = 0;
		}

		BufferedWriter characterfile = null;
		try {
			characterfile = new BufferedWriter(new FileWriter("./Data/characters/" + p.playerName + ".txt"));

			/* ACCOUNT */
			characterfile.write("[ACCOUNT]", 0, 9);
			characterfile.newLine();
			characterfile.write("character-username = ", 0, 21);
			characterfile.write(p.playerName, 0, p.playerName.length());
			characterfile.newLine();
			characterfile.write("character-password = ", 0, 21);
			characterfile.write(p.playerPass, 0, p.playerPass.length());
			characterfile.newLine();
			characterfile.newLine();

			/* CHARACTER */
			characterfile.write("[CHARACTER]", 0, 11);
			characterfile.newLine();
			characterfile.write("character-height = ", 0, 19);
			characterfile.write(Integer.toString(p.heightLevel), 0, Integer.toString(p.heightLevel).length());
			characterfile.newLine();
			characterfile.write("character-posx = ", 0, 17);
			characterfile.write(Integer.toString(p.absX), 0, Integer.toString(p.absX).length());
			characterfile.newLine();
			characterfile.write("character-posy = ", 0, 17);
			characterfile.write(Integer.toString(p.absY), 0, Integer.toString(p.absY).length());
			characterfile.newLine();
			characterfile.write("character-rights = ", 0, 19);
			characterfile.write(Integer.toString(p.playerRights), 0, Integer.toString(p.playerRights).length());
			characterfile.newLine();
			characterfile.write("lastLoginDate = ", 0, 16);
			characterfile.write(Integer.toString(p.lastLoginDate), 0, Integer.toString(p.lastLoginDate).length());
			characterfile.newLine();
			characterfile.write("UUID = ", 0, 7);
			characterfile.write(p.UUID, 0, p.UUID.length());
			characterfile.newLine();
			characterfile.write("hasBankpin = ", 0, 13);
			characterfile.write(Boolean.toString(p.hasBankpin), 0, Boolean.toString(p.hasBankpin).length());
			characterfile.newLine();
			characterfile.write("pinRegisteredDeleteDay = ", 0, 25);
			characterfile.write(Integer.toString(p.pinDeleteDateRequested), 0, Integer.toString(p.pinDeleteDateRequested).length());
			characterfile.newLine();
			characterfile.write("requestPinDelete = ", 0, 19);
			characterfile.write(Boolean.toString(p.requestPinDelete), 0, Boolean.toString(p.requestPinDelete).length());
			characterfile.newLine();
			characterfile.write("removeBankMessage = ", 0, 20);
			characterfile.write(Boolean.toString(p.removeBankMessage), 0, Boolean.toString(p.removeBankMessage).length());
			characterfile.newLine();
			characterfile.write("bankPin1 = ", 0, 11);
			characterfile.write(Integer.toString(p.bankPin1), 0, Integer.toString(p.bankPin1).length());
			characterfile.newLine();
			characterfile.write("bankPin2 = ", 0, 11);
			characterfile.write(Integer.toString(p.bankPin2), 0, Integer.toString(p.bankPin2).length());
			characterfile.newLine();
			characterfile.write("bankPin3 = ", 0, 11);
			characterfile.write(Integer.toString(p.bankPin3), 0, Integer.toString(p.bankPin3).length());
			characterfile.newLine();
			characterfile.write("bankPin4 = ", 0, 11);
			characterfile.write(Integer.toString(p.bankPin4), 0, Integer.toString(p.bankPin4).length());
			characterfile.newLine();
			characterfile.write("gameTime = ", 0, 11);
			characterfile.write(Integer.toString(p.gameTime), 0, Integer.toString(p.gameTime).length());
			characterfile.newLine();
			characterfile.write("yellTag = ", 0, 10);
			characterfile.write(p.yellTag, 0, p.yellTag.length());
			characterfile.newLine();
			characterfile.write("totalTasks = ", 0, 13);
			characterfile.write(Integer.toString(p.totalTasks), 0, Integer.toString(p.totalTasks).length());
			characterfile.newLine();
			characterfile.write("counterxp = ", 0, 12);
			characterfile.write(Integer.toString(p.counterxp), 0, Integer.toString(p.counterxp).length());
			characterfile.newLine();
			characterfile.write("has-npc = ", 0, 10);
			characterfile.write(Boolean.toString(p.hasNpc), 0, Boolean.toString(p.hasNpc).length());
			characterfile.newLine();
			characterfile.write("summonId = ", 0, 11);
			characterfile.write(Integer.toString(p.summonId), 0, Integer.toString(p.summonId).length());
			characterfile.newLine();
			characterfile.write("EP = ", 0, 5);
			characterfile.write(Integer.toString(p.EP), 0, Integer.toString(p.EP).length());
			characterfile.newLine();
			characterfile.write("EPMINUTES = ", 0, 12);
			characterfile.write(Integer.toString(p.EP_MINUTES), 0, Integer.toString(p.EP_MINUTES).length());
			characterfile.newLine();
			characterfile.write("safeTimer = ", 0, 12);
			characterfile.write(Integer.toString(p.safeTimer), 0, Integer.toString(p.safeTimer).length());
			characterfile.newLine();
			characterfile.write("TargetPercentage = ", 0, 19);
			characterfile.write(Integer.toString(p.targetPercentage), 0, Integer.toString(p.targetPercentage).length());
			characterfile.newLine();
			characterfile.write("targetIndex = ", 0, 14);
			characterfile.write(Integer.toString(p.targetIndex), 0, Integer.toString(p.targetIndex).length());
			characterfile.newLine();
			if (p.targetName != null) {
				characterfile.write("targetName = ", 0, 13);
				characterfile.write(p.targetName, 0, p.targetName.length());
				characterfile.newLine();
			}
			if (p.skillTaskName != null) {
				characterfile.write("skillTaskName = ", 0, 16);
				characterfile.write(p.skillTaskName, 0, p.skillTaskName.length());
				characterfile.newLine();
				characterfile.write("skillTaskAmount = ", 0, 18);
				characterfile.write(Integer.toString(p.skillTaskAmount), 0, Integer.toString(p.skillTaskAmount).length());
				characterfile.newLine();
			}
			characterfile.write("brothers = ", 0, 11);
			characterfile.write(Integer.toString(p.brotherKills), 0, Integer.toString(p.brotherKills).length());
			characterfile.newLine();
			characterfile.write("lastBrother = ", 0, 14);
			characterfile.write(Integer.toString(p.lastBrother), 0, Integer.toString(p.lastBrother).length());
			characterfile.newLine();
			characterfile.write("brothersKilled = ", 0, 17);
			for (int index = 0; index < p.brotherKilled.length; index++) {
				characterfile.write("	", 0, 1);
				characterfile.write(Boolean.toString(p.brotherKilled[index]), 0, Boolean.toString(p.brotherKilled[index]).length());
			}
			characterfile.newLine();
			characterfile.write("join-date = ", 0, 12);
			characterfile.write(p.joinDate, 0, p.joinDate.length());
			characterfile.newLine();
			for (int i = 0; i < p.removedTasks.length; i++) {
				characterfile.write("removedTask" + i + " = ", 0, 15);
				characterfile.write(Integer.toString(p.removedTasks[i]), 0, Integer.toString(p.removedTasks[i]).length());
				characterfile.newLine();
			}
			characterfile.write("character-title = ", 0, 18);
			characterfile.write(Integer.toString(p.playerTitle), 0, Integer.toString(p.playerTitle).length());
			characterfile.newLine();
			characterfile.write("last-vote = ", 0, 12);
			characterfile.write(Long.toString(p.vote), 0, Long.toString(p.vote).length());
			characterfile.newLine();
			characterfile.write("skull-timer = ", 0, 14);
			characterfile.write(Integer.toString(p.skullTimer), 0, Integer.toString(p.skullTimer).length());
			characterfile.newLine();
			characterfile.write("magic-book = ", 0, 13);
			characterfile.write(Integer.toString(p.playerMagicBook), 0, Integer.toString(p.playerMagicBook).length());
			characterfile.newLine();
			characterfile.write("character-expLock = ", 0, 20);
			characterfile.write(Boolean.toString(p.expLock), 0, Boolean.toString(p.expLock).length());
			characterfile.newLine();
			if (p.savedClan != null) {
				characterfile.write("savedClan = ", 0, 12);
				characterfile.write(p.savedClan, 0, p.savedClan.length());
				characterfile.newLine();
			}
			characterfile.write("KC = ");
			characterfile.write(Integer.toString(p.KC));
			characterfile.newLine();
			characterfile.write("DC = ");
			characterfile.write(Integer.toString(p.DC));
			characterfile.newLine();
			characterfile.write("ban-start = ", 0, 12);
			characterfile.write(Long.toString(p.banStart), 0, Long.toString(p.banStart).length());
			characterfile.newLine();
			characterfile.write("ban-end = ", 0, 10);
			characterfile.write(Long.toString(p.banEnd), 0, Long.toString(p.banEnd).length());
			characterfile.newLine();
			characterfile.write("special-amount = ", 0, 17);
			characterfile.write(Double.toString(p.specAmount), 0, Double.toString(p.specAmount).length());
			characterfile.newLine();
			characterfile.write("selected-coffin = ", 0, 18);
			characterfile.write(Integer.toString(p.randomCoffin), 0, Integer.toString(p.randomCoffin).length());
			characterfile.newLine();
			characterfile.write("votePoints = ", 0, ("votePoints = ").length());
			characterfile.write(Integer.toString(p.votingPoints), 0, Integer.toString(p.votingPoints).length());
			characterfile.newLine();
			characterfile.write("pkPoints = ", 0, ("pkPoints = ").length());
			characterfile.write(Integer.toString(p.pkPoints), 0, Integer.toString(p.pkPoints).length());
			characterfile.newLine();
			characterfile.write("teleblock-length = ", 0, 19);
			characterfile.write(Integer.toString(tbTime), 0, Integer.toString(tbTime).length());
			characterfile.newLine();
			characterfile.write("pc-points = ", 0, 12);
			characterfile.write(Integer.toString(p.pcPoints), 0, Integer.toString(p.pcPoints).length());
			characterfile.newLine();
			characterfile.write("killStreak = ", 0, 13);
			characterfile.write(Integer.toString(p.killStreak), 0, Integer.toString(p.killStreak).length());
			characterfile.newLine();
			characterfile.write("earningPotential = ", 0, 19);
			characterfile.write(Integer.toString(p.earningPotential), 0, Integer.toString(p.earningPotential).length());
			characterfile.newLine();
			characterfile.write("isDonator = ", 0, 12);
			characterfile.write(Integer.toString(p.isDonator), 0, Integer.toString(p.isDonator).length());
			characterfile.newLine();
			characterfile.write("donarPoints = ", 0, 14);
			characterfile.write(Integer.toString(p.donatorPoints), 0, Integer.toString(p.donatorPoints).length());
			characterfile.newLine();
			characterfile.write("totalDonations = ", 0, 17);
			characterfile.write(Integer.toString(p.totalDonations), 0, Integer.toString(p.totalDonations).length());
			characterfile.newLine();
			characterfile.write("slayerPoints = ", 0, 15);
			characterfile.write(Integer.toString(p.slayerPoints), 0, Integer.toString(p.slayerPoints).length());
			characterfile.newLine();
			characterfile.write("magePoints = ", 0, 13);
			characterfile.write(Integer.toString(p.magePoints), 0, Integer.toString(p.magePoints).length());
			characterfile.newLine();
			characterfile.write("autoRet = ", 0, 10);
			characterfile.write(Integer.toString(p.autoRet), 0, Integer.toString(p.autoRet).length());
			characterfile.newLine();
			characterfile.write("flagged = ", 0, 10);
			characterfile.write(Boolean.toString(p.accountFlagged), 0, Boolean.toString(p.accountFlagged).length());
			characterfile.newLine();
			characterfile.write("mute-end = ", 0, 11);
			characterfile.write(Long.toString(p.muteEnd), 0, Long.toString(p.muteEnd).length());
			characterfile.newLine();
			characterfile.write("fightMode = ", 0, 12);
			characterfile.write(Integer.toString(p.fightMode), 0, Integer.toString(p.fightMode).length());
			characterfile.newLine();
			characterfile.write("altarPrayed = ", 0, 14);
			characterfile.write(Integer.toString(p.altarPrayed), 0, Integer.toString(p.altarPrayed).length());
			characterfile.newLine();
			characterfile.write("skillingPoints = ", 0, 17);
			characterfile.write(Integer.toString(p.skillingPoints), 0, Integer.toString(p.skillingPoints).length());
			characterfile.newLine();
			characterfile.write("slayerPoints = ", 0, 15);
			characterfile.write(Integer.toString(p.slayerPoints), 0, Integer.toString(p.slayerPoints).length());
			characterfile.newLine();
			characterfile.write("slayerTask = ", 0, 13);
			characterfile.write(Integer.toString(p.slayerTask), 0, Integer.toString(p.slayerTask).length());
			characterfile.newLine();
			characterfile.write("taskType = ", 0, 11);
			characterfile.write(Integer.toString(p.taskType), 0, Integer.toString(p.taskType).length());
			characterfile.newLine();
			characterfile.write("taskAmount = ", 0, 13);
			characterfile.write(Integer.toString(p.taskAmount), 0, Integer.toString(p.taskAmount).length());
			characterfile.newLine();
			characterfile.write("setTheAppearance = ", 0, 19);
			characterfile.write(Boolean.toString(p.setTheAppearance), 0, Boolean.toString(p.setTheAppearance).length());
			characterfile.newLine();
			characterfile.write("void = ", 0, 7);
			String toWrite = p.voidStatus[0] + "\t" + p.voidStatus[1] + "\t" + p.voidStatus[2] + "\t" + p.voidStatus[3] + "\t" + p.voidStatus[4];
			characterfile.write(toWrite);
			characterfile.newLine();
			characterfile.write("totalPrestiges = ", 0, 17);
			characterfile.write(Integer.toString(p.totalPrestiges), 0, Integer.toString(p.totalPrestiges).length());
			characterfile.newLine();
			characterfile.write("prestigePoints = ", 0, 17);
			characterfile.write(Integer.toString(p.prestigePoints), 0, Integer.toString(p.prestigePoints).length());
			characterfile.newLine();
			characterfile.write("skillPrestiges = ", 0, 17);
			for (int i = 0; i < 25; i++) {
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(p.skillPrestiges[i]), 0, Integer.toString(p.skillPrestiges[i]).length());
			}
			characterfile.newLine();
			characterfile.write("totalSkillExp = ", 0, 16);
			for (int i = 0; i < 25; i++) {
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(p.totalSkillExp[i]), 0, Integer.toString(p.totalSkillExp[i]).length());
			}
			characterfile.newLine();
              
                        characterfile.write("hasActivePerk = ", 0, 16);
			characterfile.write(Boolean.toString(p.hasActivePerk), 0, Boolean.toString(p.hasActivePerk).length());
			characterfile.newLine();
			characterfile.write("activeCycles = ", 0, 15);
			characterfile.write(Integer.toString(p.activeCycles), 0, Integer.toString(p.activeCycles).length());
			characterfile.newLine();
			characterfile.write("coolDown = ", 0, 11);
			characterfile.write(Integer.toString(p.coolDown), 0, Integer.toString(p.coolDown).length());
			characterfile.newLine();
			characterfile.write("perksActivated = ", 0, 17);
			for (int i = 0; i < p.perksActivated.length; i++) {
				characterfile.write("	", 0, 1);
				characterfile.write(Boolean.toString(p.perksActivated[i]), 0, Boolean.toString(p.perksActivated[i]).length());
			}
			characterfile.newLine();
			characterfile.write("questCompleted = ", 0, 17);
			characterfile.write(Integer.toString(p.questCompleted), 0, Integer.toString(p.questCompleted).length());
			characterfile.newLine();
			characterfile.write("gameOfChess = ", 0, 14);
			characterfile.write(Integer.toString(p.gameOfChess), 0, Integer.toString(p.gameOfChess).length());
			characterfile.newLine();
			characterfile.write("AcQuest = ", 0, 10);
			characterfile.write(Integer.toString(p.AcQuest), 0, Integer.toString(p.AcQuest).length());
			characterfile.newLine();
			characterfile.write("cookAss = ", 0, 10);
			characterfile.write(Integer.toString(p.cookAss), 0, Integer.toString(p.cookAss).length());
			characterfile.newLine();
			characterfile.write("doricsQuest = ", 0, 14);
			characterfile.write(Integer.toString(p.doricsQuest), 0, Integer.toString(p.doricsQuest).length());
			characterfile.newLine();
			characterfile.newLine();
			characterfile.write("[TOGGLES]", 0, 9);
			characterfile.newLine();
			characterfile.write("privateChat = ", 0, 14);
			characterfile.write(Integer.toString(p.privateChat), 0, Integer.toString(p.privateChat).length());
			characterfile.newLine();
			characterfile.write("wantTrivia = ", 0, 13);
			characterfile.write(Boolean.toString(p.wantTriviaBot), 0, Boolean.toString(p.wantTriviaBot).length());
			characterfile.newLine();
			characterfile.newLine();
			characterfile.write("[PROFILER]", 0, 10);
			characterfile.newLine();
			characterfile.write("canViewProfile = ", 0, 17);
			characterfile.write(Boolean.toString(p.canViewProfile), 0, Boolean.toString(p.canViewProfile).length());
			characterfile.newLine();
			characterfile.write("profileColour = ", 0, 16);
			characterfile.write(p.profileColour, 0, p.profileColour.length());
			characterfile.newLine();
			characterfile.write("name = ", 0, 7);
			characterfile.write(p.name, 0, p.name.length());
			characterfile.newLine();
			characterfile.write("ageProfile = ", 0, 13);
			characterfile.write(p.ageProfile, 0, p.ageProfile.length());
			characterfile.newLine();
			characterfile.write("bplaceProfile = ", 0, 16);
			characterfile.write(p.bplaceProfile, 0, p.bplaceProfile.length());
			characterfile.newLine();
			characterfile.write("aboutMe = ", 0, 10);
			characterfile.write(p.aboutMe, 0, p.aboutMe.length());
			characterfile.newLine();
			characterfile.newLine();
			characterfile.write("[ACHIEVEMENTS]", 0, 14);
			characterfile.newLine();
			characterfile.newLine();
			characterfile.write("achievementsCompleted = ", 0, 24);
			characterfile.write(Integer.toString(p.achievementsCompleted), 0, Integer.toString(p.achievementsCompleted).length());
			characterfile.newLine();
			characterfile.write("achievementPoints = ", 0, 20);
			characterfile.write(Integer.toString(p.achievementPoints), 0, Integer.toString(p.achievementPoints).length());
			characterfile.newLine();
			characterfile.write("currentlyCompletedAll = ", 0, 24);
			characterfile.write(Boolean.toString(p.currentlyCompletedAll), 0, Boolean.toString(p.currentlyCompletedAll).length());
			characterfile.newLine();
			characterfile.write("foodEaten = ", 0, 12);
			characterfile.write(Integer.toString(p.foodEaten), 0, Integer.toString(p.foodEaten).length());
			characterfile.newLine();
			characterfile.write("potionsDrank = ", 0, 15);
			characterfile.write(Integer.toString(p.potionsDrank), 0, Integer.toString(p.potionsDrank).length());
			characterfile.newLine();
			characterfile.write("duelsWon = ", 0, 11);
			characterfile.write(Integer.toString(p.duelsWon), 0, Integer.toString(p.duelsWon).length());
			characterfile.newLine();
			characterfile.write("teleportedHome = ", 0, 17);
			characterfile.write(Integer.toString(p.teleportedHome), 0, Integer.toString(p.teleportedHome).length());
			characterfile.newLine();
			characterfile.write("triviaWon = ", 0, 12);
			characterfile.write(Integer.toString(p.triviaWon), 0, Integer.toString(p.triviaWon).length());
			characterfile.newLine();
			characterfile.write("altarPrayers = ", 0, 15);
			characterfile.write(Integer.toString(p.altarPrayers), 0, Integer.toString(p.altarPrayers).length());
			characterfile.newLine();
			characterfile.write("profileViews = ", 0, 15);
			characterfile.write(Integer.toString(p.profileViews), 0, Integer.toString(p.profileViews).length());
			characterfile.newLine();
			characterfile.write("pkStatuePlays = ", 0, 16);
			characterfile.write(Integer.toString(p.pkStatuePlays), 0, Integer.toString(p.pkStatuePlays).length());
			characterfile.newLine();
			characterfile.write("tradesCompleted = ", 0, 18);
			characterfile.write(Integer.toString(p.tradesCompleted), 0, Integer.toString(p.tradesCompleted).length());
			characterfile.newLine();
			characterfile.write("appearancesChanged = ", 0, 21);
			characterfile.write(Integer.toString(p.appearancesChanged), 0, Integer.toString(p.appearancesChanged).length());
			characterfile.newLine();
			characterfile.write("corpKills = ", 0, 12);
			characterfile.write(Integer.toString(p.corpKills), 0, Integer.toString(p.corpKills).length());
			characterfile.newLine();
			characterfile.write("rockCrabKills = ", 0, 16);
			characterfile.write(Integer.toString(p.rockCrabKills), 0, Integer.toString(p.rockCrabKills).length());
			characterfile.newLine();
			characterfile.write("jadKills = ", 0, 11);
			characterfile.write(Integer.toString(p.jadKills), 0, Integer.toString(p.jadKills).length());
			characterfile.newLine();
			characterfile.write("yewsCut = ", 0, 10);
			characterfile.write(Integer.toString(p.yewsCut), 0, Integer.toString(p.yewsCut).length());
			characterfile.newLine();
			characterfile.write("foodCooked = ", 0, 13);
			characterfile.write(Integer.toString(p.foodCooked), 0, Integer.toString(p.foodCooked).length());
			characterfile.newLine();
			characterfile.write("logsBurned = ", 0, 13);
			characterfile.write(Integer.toString(p.logsBurned), 0, Integer.toString(p.logsBurned).length());
			characterfile.newLine();
			characterfile.write("oresRecieved = ", 0, 15);
			characterfile.write(Integer.toString(p.oresRecieved), 0, Integer.toString(p.oresRecieved).length());
			characterfile.newLine();
			characterfile.write("playerReported = ", 0, 17);
			characterfile.write(Boolean.toString(p.playerReported), 0, Boolean.toString(p.playerReported).length());
			characterfile.newLine();
			characterfile.write("killedF2p = ", 0, 12);
			characterfile.write(Boolean.toString(p.killedF2p), 0, Boolean.toString(p.killedF2p).length());
			characterfile.newLine();
			characterfile.write("hit900 = ", 0, 9);
			characterfile.write(Boolean.toString(p.hit900), 0, Boolean.toString(p.hit900).length());
			characterfile.newLine();
			characterfile.write("gotCompletionist = ", 0, 19);
			characterfile.write(Boolean.toString(p.gotCompletionist), 0, Boolean.toString(p.gotCompletionist).length());
			characterfile.newLine();
			characterfile.newLine();

			/* EQUIPMENT */
			characterfile.write("[EQUIPMENT]", 0, 11);
			characterfile.newLine();
			for (int i = 0; i < p.playerEquipment.length; i++) {
				characterfile.write("character-equip = ", 0, 18);
				characterfile.write(Integer.toString(i), 0, Integer.toString(i).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(p.playerEquipment[i]), 0, Integer.toString(p.playerEquipment[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(p.playerEquipmentN[i]), 0, Integer.toString(p.playerEquipmentN[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.newLine();
			}
			characterfile.newLine();

			/* LOOK */
			characterfile.write("[LOOK]", 0, 6);
			characterfile.newLine();
			for (int i = 0; i < p.playerAppearance.length; i++) {
				characterfile.write("character-look = ", 0, 17);
				characterfile.write(Integer.toString(i), 0, Integer.toString(i).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(p.playerAppearance[i]), 0, Integer.toString(p.playerAppearance[i]).length());
				characterfile.newLine();
			}
			characterfile.newLine();

			/* SKILLS */
			characterfile.write("[SKILLS]", 0, 8);
			characterfile.newLine();
			for (int i = 0; i < p.playerLevel.length; i++) {
				characterfile.write("character-skill = ", 0, 18);
				characterfile.write(Integer.toString(i), 0, Integer.toString(i).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(p.playerLevel[i]), 0, Integer.toString(p.playerLevel[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(p.playerXP[i]), 0, Integer.toString(p.playerXP[i]).length());
				characterfile.newLine();
			}
			characterfile.newLine();

			/* ITEMS */
			characterfile.write("[ITEMS]", 0, 7);
			characterfile.newLine();
			for (int i = 0; i < p.playerItems.length; i++) {
				if (p.playerItems[i] > 0) {
					characterfile.write("character-item = ", 0, 17);
					characterfile.write(Integer.toString(i), 0, Integer.toString(i).length());
					characterfile.write("	", 0, 1);
					characterfile.write(Integer.toString(p.playerItems[i]), 0, Integer.toString(p.playerItems[i]).length());
					characterfile.write("	", 0, 1);
					characterfile.write(Integer.toString(p.playerItemsN[i]), 0, Integer.toString(p.playerItemsN[i]).length());
					characterfile.newLine();
				}
			}
			characterfile.newLine();

			/* BANK */
			characterfile.write("[BANK]", 0, 6);
			characterfile.newLine();

				List<BankItem> bankItems = p.getBank().getTab(0).getItems();

				for (int itemIndex = 0; itemIndex < bankItems.size(); itemIndex++) {

					BankItem bankItem = bankItems.get(itemIndex);

					if (bankItem.getID() > 0) {
						String bankToken = "character-bank" + ("") + " = ";
						characterfile.write(bankToken, 0, bankToken.length());
						characterfile.write(Integer.toString(itemIndex), 0, Integer.toString(itemIndex).length());
						characterfile.write("	", 0, 1);
						characterfile.write(Integer.toString(bankItem.getID()), 0, Integer.toString(bankItem.getID()).length());
						characterfile.write("	", 0, 1);
						characterfile.write(Integer.toString(bankItem.getAmount()), 0, Integer.toString(bankItem.getAmount()).length());
						characterfile.newLine();
					}
				}
			

			characterfile.newLine();

			/* Storeditems */
			// characterfile.write("[STORED]", 0, 8);
			// characterfile.newLine();
			// for (int i = 0; i < p.bobItems.length; i++) {
			// characterfile.write("stored = ", 0, 9);
			// characterfile.write(Integer.toString(i), 0, Integer.toString(i)
			// .length());
			// characterfile.write(" ", 0, 1);
			// characterfile.write(Integer.toString(p.bobItems[i]), 0, Integer
			// .toString(p.bobItems[i]).length());
			// characterfile.newLine();
			// }
			// characterfile.newLine();

			/* FRIENDS */
			characterfile.write("[FRIENDS]", 0, 9);
			characterfile.newLine();
			for (int i = 0; i < p.friends.length; i++) {
				if (p.friends[i] > 0) {
					characterfile.write("character-friend = ", 0, 19);
					characterfile.write(Integer.toString(i), 0, Integer.toString(i).length());
					characterfile.write("	", 0, 1);
					characterfile.write("" + p.friends[i]);
					characterfile.newLine();
				}
			}
			characterfile.newLine();

			/* IGNORES */
			/*
			 * characterfile.write("[IGNORES]", 0, 9); characterfile.newLine();
			 * for (int i = 0; i < ignores.length; i++) { if (ignores[i] > 0) {
			 * characterfile.write("character-ignore = ", 0, 19);
			 * characterfile.write(Integer.toString(i), 0,
			 * Integer.toString(i).length()); characterfile.write("	", 0, 1);
			 * characterfile.write(Long.toString(ignores[i]), 0,
			 * Long.toString(ignores[i]).length()); characterfile.newLine(); } }
			 * characterfile.newLine();
			 */
			/* EOF */
			characterfile.write("[FARMING]", 0, 9);
			characterfile.newLine();

			for (int i = 0; i < p.getAllotment().allotmentStages.length; i++) {
				if (p.getAllotment().allotmentStages[i] <= 0)
					continue;
				characterfile.write("allotment" + i + " = ", 0, 13);
				characterfile.write(Integer.toString(p.getAllotment().allotmentHarvest[i]), 0, Integer.toString(p.getAllotment().allotmentHarvest[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(p.getAllotment().allotmentSeeds[i]), 0, Integer.toString(p.getAllotment().allotmentSeeds[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(p.getAllotment().allotmentStages[i]), 0, Integer.toString(p.getAllotment().allotmentStages[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(p.getAllotment().allotmentState[i]), 0, Integer.toString(p.getAllotment().allotmentState[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Long.toString(p.getAllotment().allotmentTimer[i]), 0, Long.toString(p.getAllotment().allotmentTimer[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Double.toString(p.getAllotment().diseaseChance[i]), 0, Double.toString(p.getAllotment().diseaseChance[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Boolean.toString(p.getAllotment().hasFullyGrown[i]), 0, Boolean.toString(p.getAllotment().hasFullyGrown[i]).length());
				characterfile.newLine();
			}

			for (int i = 0; i < p.getBushes().bushesStages.length; i++) {
				if (p.getBushes().bushesStages[i] <= 0)
					continue;
				characterfile.write("bush" + i + " = ", 0, 8);
				characterfile.write(Integer.toString(p.getBushes().bushesSeeds[i]), 0, Integer.toString(p.getBushes().bushesSeeds[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(p.getBushes().bushesStages[i]), 0, Integer.toString(p.getBushes().bushesStages[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(p.getBushes().bushesState[i]), 0, Integer.toString(p.getBushes().bushesState[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Long.toString(p.getBushes().bushesTimer[i]), 0, Long.toString(p.getBushes().bushesTimer[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Double.toString(p.getBushes().diseaseChance[i]), 0, Double.toString(p.getBushes().diseaseChance[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Boolean.toString(p.getBushes().hasFullyGrown[i]), 0, Boolean.toString(p.getBushes().hasFullyGrown[i]).length());
				characterfile.newLine();
			}

			for (int i = 0; i < p.getTrees().treeStages.length; i++) {
				if (p.getTrees().treeStages[i] <= 0)
					continue;
				characterfile.write("tree" + i + " = ", 0, 8);
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(p.getTrees().treeHarvest[i]), 0, Integer.toString(p.getTrees().treeHarvest[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(p.getTrees().treeSaplings[i]), 0, Integer.toString(p.getTrees().treeSaplings[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(p.getTrees().treeStages[i]), 0, Integer.toString(p.getTrees().treeStages[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(p.getTrees().treeState[i]), 0, Integer.toString(p.getTrees().treeState[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Long.toString(p.getTrees().treeTimer[i]), 0, Long.toString(p.getTrees().treeTimer[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Double.toString(p.getTrees().diseaseChance[i]), 0, Double.toString(p.getTrees().diseaseChance[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Boolean.toString(p.getTrees().hasFullyGrown[i]), 0, Boolean.toString(p.getTrees().hasFullyGrown[i]).length());
				characterfile.newLine();
			}

			for (int i = 0; i < p.getFlowers().flowerStages.length; i++) {
				if (p.getFlowers().flowerStages[i] <= 0)
					continue;
				characterfile.write("flower" + i + " = ", 0, 10);
				characterfile.write(Integer.toString(p.getFlowers().flowerSeeds[i]), 0, Integer.toString(p.getFlowers().flowerSeeds[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(p.getFlowers().flowerStages[i]), 0, Integer.toString(p.getFlowers().flowerStages[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(p.getFlowers().flowerState[i]), 0, Integer.toString(p.getFlowers().flowerState[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Long.toString(p.getFlowers().flowerTimer[i]), 0, Long.toString(p.getFlowers().flowerTimer[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Double.toString(p.getFlowers().diseaseChance[i]), 0, Double.toString(p.getFlowers().diseaseChance[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Boolean.toString(p.getFlowers().hasFullyGrown[i]), 0, Boolean.toString(p.getFlowers().hasFullyGrown[i]).length());
				characterfile.newLine();
			}

			for (int i = 0; i < p.getFruitTrees().fruitTreeStages.length; i++) {
				if (p.getFruitTrees().fruitTreeStages[i] <= 0)
					continue;
				characterfile.write("fruitTree" + i + " = ", 0, 13);
				characterfile.write(Integer.toString(p.getFruitTrees().fruitTreeSaplings[i]), 0, Integer.toString(p.getFruitTrees().fruitTreeSaplings[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(p.getFruitTrees().fruitTreeStages[i]), 0, Integer.toString(p.getFruitTrees().fruitTreeStages[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(p.getFruitTrees().fruitTreeState[i]), 0, Integer.toString(p.getFruitTrees().fruitTreeState[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Long.toString(p.getFruitTrees().fruitTreeTimer[i]), 0, Long.toString(p.getFruitTrees().fruitTreeTimer[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Double.toString(p.getFruitTrees().diseaseChance[i]), 0, Double.toString(p.getFruitTrees().diseaseChance[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Boolean.toString(p.getFruitTrees().hasFullyGrown[i]), 0, Boolean.toString(p.getFruitTrees().hasFullyGrown[i]).length());
				characterfile.newLine();
			}

			for (int i = 0; i < p.getHerbs().herbStages.length; i++) {
				if (p.getHerbs().herbStages[i] <= 0)
					continue;
				characterfile.write("herb" + i + " = ", 0, 8);
				characterfile.write(Integer.toString(p.getHerbs().herbHarvest[i]), 0, Integer.toString(p.getHerbs().herbHarvest[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(p.getHerbs().herbSeeds[i]), 0, Integer.toString(p.getHerbs().herbSeeds[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(p.getHerbs().herbStages[i]), 0, Integer.toString(p.getHerbs().herbStages[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(p.getHerbs().herbState[i]), 0, Integer.toString(p.getHerbs().herbState[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Long.toString(p.getHerbs().herbTimer[i]), 0, Long.toString(p.getHerbs().herbTimer[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Double.toString(p.getHerbs().diseaseChance[i]), 0, Double.toString(p.getHerbs().diseaseChance[i]).length());
				characterfile.newLine();
			}

			// for (int i = 0; i < p.getHops().hopsStages.length; i++) {
			// characterfile.write("hop" + i + " = ", 0, 7);
			// characterfile.write("" + p.getHops().hopsHarvest[i]);
			// characterfile.write(" ", 0, 1);
			// characterfile.write("" + p.getHops().hopsSeeds[i]);
			// characterfile.write(" ", 0, 1);
			// characterfile.write("" + p.getHops().hopsStages[i]);
			// characterfile.write(" ", 0, 1);
			// characterfile.write("" + p.getHops().hopsState[i]);
			// characterfile.write(" ", 0, 1);
			// characterfile.write("" + p.getHops().hopsTimer[i]);
			// characterfile.write(" ", 0, 1);
			// characterfile.write("" + p.getHops().diseaseChance[i]);
			// characterfile.write(" ", 0, 1);
			// characterfile.write("" + p.getHops().hasFullyGrown[i]);
			// characterfile.newLine();
			// }

			// for (int i = 0; i <
			// p.getSpecialPlantOne().specialPlantStages.length; i++) {
			// characterfile.write("specialPlantOne" + i + " = ", 0, 19);
			// characterfile.write("" +
			// p.getSpecialPlantOne().specialPlantSaplings[i]);
			// characterfile.write(" ", 0, 1);
			// characterfile.write("" +
			// p.getSpecialPlantOne().specialPlantStages[i]);
			// characterfile.write(" ", 0, 1);
			// characterfile.write("" +
			// p.getSpecialPlantOne().specialPlantState[i]);
			// characterfile.write(" ", 0, 1);
			// characterfile.write("" +
			// p.getSpecialPlantOne().specialPlantTimer[i]);
			// characterfile.write(" ", 0, 1);
			// characterfile.write("" +
			// p.getSpecialPlantOne().diseaseChance[i]);
			// characterfile.write(" ", 0, 1);
			// characterfile.write("" +
			// p.getSpecialPlantOne().hasFullyGrown[i]);
			// characterfile.newLine();
			// }

			// for (int i = 0; i <
			// p.getSpecialPlantTwo().specialPlantStages.length; i++) {
			// characterfile.write("specialPlantTwo" + i + " = ", 0, 19);
			// characterfile.write("" +
			// p.getSpecialPlantTwo().specialPlantSeeds[i]);
			// characterfile.write(" ", 0, 1);
			// characterfile.write("" +
			// p.getSpecialPlantTwo().specialPlantStages[i]);
			// characterfile.write(" ", 0, 1);
			// characterfile.write("" +
			// p.getSpecialPlantTwo().specialPlantState[i]);
			// characterfile.write(" ", 0, 1);
			// characterfile.write("" +
			// p.getSpecialPlantTwo().specialPlantTimer[i]);
			// characterfile.write(" ", 0, 1);
			// characterfile.write("" +
			// p.getSpecialPlantTwo().diseaseChance[i]);
			// characterfile.write(" ", 0, 1);
			// characterfile.write("" +
			// p.getSpecialPlantTwo().hasFullyGrown[i]);
			// characterfile.newLine();
			// }
			//
			characterfile.newLine();

			characterfile.write("[EOF]", 0, 5);
			characterfile.newLine();

			characterfile.newLine();
			characterfile.close();
		} catch (IOException ioexception) {
			MistexUtility.println(p.playerName + ": error writing file.");
			return false;
		}
		return true;
	}

}