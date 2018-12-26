package org.mistex.game.world.content.dialogue;

import org.mistex.game.world.content.Miscellaneous;
import org.mistex.game.world.content.Teles;
import org.mistex.game.world.content.Veteran;
import org.mistex.game.world.content.decanting.Decanting;
import org.mistex.game.world.content.gambling.DiceHandler;
import org.mistex.game.world.content.gambling.MithrilSeeds;
import org.mistex.game.world.content.pvp.PvPArtifacts;
import org.mistex.game.world.content.skill.SkillingTasks;
import org.mistex.game.world.content.skill.SkillingTasks.TaskType;
import org.mistex.game.world.content.skill.slayer.DuoSlayer;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.Player;
import org.mistex.game.world.player.PlayerConfiguration;
import org.mistex.game.world.shop.ShopExecutor;


public class DialogueButtons {

	@SuppressWarnings("static-access")
	public static void handleClickButton(Client c, int buttonId) {
		switch (buttonId) {

		case 9157:
			if (c.dialogueAction == 805 && c.cookAss == 0) {
				c.getDH().sendDialogues(806, 278);
			} else if (c.dialogueAction == 118 && c.doricsQuest == 0) {
				c.getDH().sendDialogues(447, 284);
			} else if (c.dialogueAction == 612) {
				c.getDH().sendDialogues(614, 610);
			} else if (c.dialogueAction == 22) {
				c.getPA().removeAllWindows();
			} else if (c.dialogueAction == 300) {
				c.getPA().removeAllWindows();
				// c.getStatue().handleMain();
			} else if (c.dialogueAction == 22) {
				MithrilSeeds.pickupFlowers(c);
				c.getPA().removeAllWindows();
			} else if (c.dialogueAction == 70) {
				ShopExecutor.open(c, 24);
			} else if (c.dialogueAction == 12) {
				// Slayer.giveTask(c);
				c.getPA().removeAllWindows();
			} else if (c.dialogueAction == 2401) {
				c.getDH().sendDialogues(2402, 7571);
			} else if (c.dialogueAction == 29) {
				c.getBarrows().checkCoffins();
				c.getPA().removeAllWindows();
				return;
			} else if (c.dialogueAction == 27) {
				c.getBarrows().cantWalk = false;
				c.getPA().removeAllWindows();
				c.getBarrowsChallenge().start();
				return;
			} else if (c.dialogueAction == 25) {
				c.getDH().sendDialogues(26, 0);
				return;
			} else if (c.dialogueAction == 252) {
				c.getDH().sendDialogues(254, 945);
			} else if (c.dialogueAction == 341) {
				Miscellaneous.exchangeTicket4Coins(c);
			} else if (c.dialogueAction == 74) {
				ShopExecutor.open(c, 4);
			} else if (c.dialogueAction == 183) {
				c.getPA().fixAllBarrows();
				c.getPA().closeAllWindows();
			} else if (c.usingROD) {
				c.getPA().startTeleport(3367, 3267, 0, "modern");
				Teles.rings(c);
			} else if (c.dialogueAction == 50) {
				c.getPA().startTeleport(2898, 3562, 0, "modern");
				Teles.necklaces(c);
			} else if (c.dialogueAction == 5003) {
				PvPArtifacts.handleExchange(c);
			} else if (c.dialogueAction == 320) {
				c.getPA().enterBattle();
			} else if (c.dialogueAction == 879) {
				if (c.getPotentialPartner() != null)
					DuoSlayer.getInstance().accept(c, c.getPotentialPartner());
				else
					c.sendMessage("You do not have a open request.");
			}
			switch (c.dialogueAction) {
			case 1272:
				c.getBankPins().deletePin();
				break;

			}
			break;

		case 9158:
			if (c.dialogueAction == 5) {
				c.getPA().closeAllWindows();
			} else if (c.dialogueAction == 320) {
				c.getPA().removeAllWindows();
			} else if (c.dialogueAction == 805 && c.cookAss == 0) {
				c.getDH().sendDialogues(807, 278);
			} else if (c.dialogueAction == 612) {
				c.getDH().sendDialogues(613, 610);
			} else if (c.dialogueAction == 2401) {
				c.getPA().closeAllWindows();
			} else if (c.dialogueAction == 10000) {
				c.getPA().removeAllWindows();
			} else if (c.dialogueAction == 22) {
				MithrilSeeds.processFlower(c);
				c.getPA().removeAllWindows();
			} else if (c.dialogueAction == 879) {
				DuoSlayer.getInstance().decline(c, c.getPotentialPartner());
			} else if (c.dialogueAction == 341) {
				Miscellaneous.exchangeCoins4Tickets(c);
			} else if (c.dialogueAction == 252) {
				c.getDH().sendDialogues(253, 945);
			} else if (c.dialogueAction == 22) {
				c.getPA().removeAllWindows();
			} else if (c.dialogueAction == 70) {
				ShopExecutor.open(c, 25);
			} else if (c.dialogueAction == 22) {
				c.getPA().removeAllWindows();
			} else if (c.dialogueAction == 300) {
				c.getPA().removeAllWindows();
			} else if (c.dialogueAction == 135) {
				c.getPA().removeAllWindows();
			} else if (c.dialogueAction == 12) {
				c.getPA().removeAllWindows();
			} else if (c.dialogueAction == 74) {
				ShopExecutor.open(c, 19);
			} else if (c.dialogueAction == 5003) {
				c.getPA().removeAllWindows();
			} else if (c.dialogueAction == 183) {
				c.lastBrother = 0;
				c.brotherKills = 0;
				c.getPA().sendStatement("The old man has reset your barrows.");
				c.nextChat = 0;
			} else if (c.usingROD) {
				c.getPA().startTeleport(2441, 3090, 0, "modern");
				Teles.rings(c);
			} else if (c.dialogueAction == 50) {
				c.getPA().startTeleport(2552, 3558, 0, "modern");
				Teles.necklaces(c);
			}
			switch (c.dialogueAction) {
			case 1272:
				c.getPA().closeAllWindows();
				break;
			}
			break;

		case 9167:
			if (c.dialogueAction == 22) {
				c.outStream.createFrame(27);
				c.ExchangingTokensForPoints = true;
			} else if (c.dialogueAction == 17) {
				ShopExecutor.open(c, 1);
			} else if (c.dialogueAction == 96) {
				c.getDH().sendDialogues(97, 5964);
			} else if (c.dialogueAction == 8203) {
				DuoSlayer.getInstance().assignDuo(c);
			} else if (c.dialogueAction == 94) {
				ShopExecutor.open(c, 13);
				c.sendMessage("You will not be able to wear the cape without the requirement!");
			}
			switch (c.dialogueAction) {
			case 1282:
				Decanting.startDecanting(c);
				break;
			case 3001:
				Veteran.claimVeteran(c);
				break;
			}
			break;

		case 9168:
			if (c.dialogueAction == 22) {
				c.outStream.createFrame(27);
				c.ExchangingPointsForTokens = true;
			} else if (c.dialogueAction == 96) {
				ShopExecutor.open(c, 26);

			} else if (c.dialogueAction == 3001) {
				c.getDH().sendDialogues(3005, 7143);

			} else if (c.dialogueAction == 17) {
				ShopExecutor.open(c, 4);
			} else if (c.dialogueAction == 8203) {
				DuoSlayer.getInstance().cancelTask(c);
			} else if (c.dialogueAction == 94) {
				ShopExecutor.open(c, 14);
				c.sendMessage("You will not be able to wear the cape without the requirement!");
			}
			switch (c.dialogueAction) {
			case 1282:
				c.getPA().closeAllWindows();
				break;
			case 3001:
				c.getDH().sendDialogues(3005, 7143);
				break;
			}
			break;

		case 9169:
			switch (c.dialogueAction) {
			case 17:
				ShopExecutor.open(c, 5);
				break;
			case 1282:
				c.getDH().sendDialogues(1285, 300);
				break;
			case 96:
				c.getDH().sendDialogues(99, -1);
				break;
			case 3001:
			case 94:
			case 22:
				c.getPA().closeAllWindows();
				break;
			}
			break;

		case 9178:
			if (c.dialogueAction == 1) {
				ShopExecutor.open(c, 5);
			} else if (c.dialogueAction == 801) {
				c.getDH().sendDialogues(802, 278);
			} else if (c.dialogueAction == 5000) {
				ShopExecutor.open(c, 3);
			} else if (c.dialogueAction == 200) {
				Miscellaneous.refillPrayer(c);
			} else if (c.dialogueAction == 12) {
				c.getSlayer().cancelTask();
			} else if (c.dialogueAction == 86) {
				c.getPA().closeAllWindows();
				c.getPA().startTeleport(3056, 3311, 0, "modern");
				c.sendMessage("<col=482CB8>You have teleported to the Falador location.");
			} else if (c.dialogueAction == 201) {
				c.setSidebarInterface(6, 1151);
				c.playerMagicBook = 0;
				c.sendMessage("You feel a drain on your memory.");
				c.autocastId = -1;
				c.getPA().resetAutocast();
				c.getPA().closeAllWindows();
			} else if (c.dialogueAction == 2) {
				ShopExecutor.open(c, 5);
			} else if (c.dialogueAction == 51) {
				c.getPA().startTeleport(3088, 3500, 0, "modern");
				Teles.necklaces(c);
			}
			switch (c.dialogueAction) {
			case 2003:
				c.getDH().sendDialogues(2004, 2581);
				break;
			}
			break;

		case 9179:
			if (c.dialogueAction == 1) {
				c.getDH().sendDialogues(2, -1);
			} else if (c.dialogueAction == 801) {
				c.getDH().sendDialogues(813, 278);
			} else if (c.dialogueAction == 12) {
				c.getSlayer().generateTask();
			} else if (c.dialogueAction == 5000) {
				ShopExecutor.open(c, 33);
			} else if (c.dialogueAction == 200) {
				Miscellaneous.determineWhoToRestore(c);
			} else if (c.dialogueAction == 2) {
				c.getDH().sendDialogues(3, -1);
			} else if (c.dialogueAction == 86) {
				c.getPA().closeAllWindows();
				c.getPA().startTeleport(2805, 3464, 0, "modern");
				c.sendMessage("<col=482CB8>You have teleported to the Catherby location.");
			} else if (c.dialogueAction == 201) {
				c.getPA().closeAllWindows();
				c.playerMagicBook = 1;
				c.setSidebarInterface(6, 12855);
				c.sendMessage("An ancient wisdomin fills your mind.");
				c.getPA().resetAutocast();
			} else if (c.dialogueAction == 51) {
				c.getPA().startTeleport(3293, 3174, 0, "modern");
				Teles.necklaces(c);
			}
			switch (c.dialogueAction) {
			case 2003:
				c.sendMessage("Sorry this feature has been removed.");
				break;
			}
			break;

		case 9180:
			if (c.dialogueAction == 1) {
				c.sendMessage("Coming soon...");
				c.getPA().closeAllWindows();
			} else if (c.dialogueAction == 801) {
				c.getDH().sendDialogues(814, 278);
			} else if (c.dialogueAction == 12) {
				ShopExecutor.open(c, 15);
			} else if (c.dialogueAction == 5000) {
				ShopExecutor.open(c, 23);
			} else if (c.dialogueAction == 2) {
				c.getDH().sendDialogues(4, -1);
			} else if (c.dialogueAction == 200) {
				c.getDH().sendDialogues(201, -1);
			} else if (c.dialogueAction == 201) {
				c.getPA().closeAllWindows();
				c.playerMagicBook = 2;
				c.setSidebarInterface(6, 29999);
				c.sendMessage("Your mind becomes stirred with thoughs of dreams.");
				c.getPA().resetAutocast();
			} else if (c.dialogueAction == 51) {
				c.getPA().startTeleport(2911, 3152, 0, "modern");
				Teles.necklaces(c);
			} else if (c.dialogueAction == 86) {
				c.getPA().closeAllWindows();
				c.getPA().startTeleport(3600, 3524, 0, "modern");
				c.sendMessage("<col=482CB8>You have teleported to the Phasmatys location.");
			}
			switch (c.dialogueAction) {
			case 2003:
				ShopExecutor.open(c, 27);
				break;

			}
			break;

		case 9181:
			switch (c.dialogueAction) {
			case 1:
			case 200:
			case 2:
			case 2003:
				c.getPA().closeAllWindows();
				break;
			case 5000:
				c.getPA().closeAllWindows();
				c.getPA().sendFrame126("www.mistex.org/donate", 12000);
				c.sendMessage("Thank you for considering donating!");
				break;

			case 12:
				c.getDH().sendDialogues(127, 1597);
				break;

			case 801:
				c.getDH().sendDialogues(812, 278);
				break;

			case 51:
				c.getPA().startTeleport(3103, 3249, 0, "modern");
				Teles.necklaces(c);
				break;

			case 86:
				c.getPA().closeAllWindows();
				c.getPA().startTeleport(2662, 3375, 0, "modern");
				c.sendMessage("<col=482CB8>You have teleported to the Ardougne location.");
				break;
			}
			break;

		case 9190:
			switch (c.dialogueAction) {
			case 99:
				SkillingTasks.giveTask(c, TaskType.Easy);
				break;
			case 2300:
				c.getPA().closeAllWindows();
				c.getPA().startTeleport(2757, 3477, 0, "modern");
				c.sendMessage("<col=482CB8>You have teleported to the Camelot location.");
				break;
			}
			if (c.dialogueAction == 50) {
				c.profileColour = "blu";
				c.sendMessage("@ceo@Your profile colour is now: @blu@Blue");
				c.getPA().closeAllWindows();
			} else if (c.dialogueAction == 51) {
				c.profileColour = "or1";
				c.sendMessage("@ceo@Your profile colour is now: @or1@Orange");
				c.getPA().closeAllWindows();
			} else if (c.dialogueAction == 100) {
				c.getDH().sendDialogues(105, 1);
			} else if (c.dialogueAction == 86) {
				c.getPA().closeAllWindows();
				c.getPA().startTeleport(2804, 3463, 0, "modern");
				c.sendMessage("<col=482CB8>You have teleported to the Falador location.");
			} else if (c.dialogueAction == 10000) {
				Miscellaneous.banUser(c);
			} else if (c.dialogueAction == 105) {
				c.getDH().sendDialogues(101, 1);
			} else if (c.dialogueAction == 101) {
				c.getPA().closeAllWindows();
				c.getPA().startTeleport(PlayerConfiguration.START_LOCATION_X, PlayerConfiguration.START_LOCATION_Y, 0, "modern");
				c.sendMessage("@ceo@You have been teleported to Edgeville!");
			} else if (c.dialogueAction == 125) {
				c.getPA().closeAllWindows();
				c.getPA().startTeleport(3362, 3263, 0, "modern");
				c.sendMessage("@ceo@You have been teleported to the Duel Arena!");
			} else if (c.dialogueAction == 136) {
				c.getPA().closeAllWindows();
				c.getPA().startTeleport(2840, 5296, 2, "modern");
				c.sendMessage("<col=482CB8>You have been teleported to the armadyl boss!");

			} else if (c.dialogueAction == 364 || c.dialogueAction == 365) {
				c.resetChoosen = c.dialogueAction == 364 ? Player.playerAttack : Player.playerRanged;
				c.getPA().resetStatistic();
			} else if (c.dialogueAction == 292) {
				if (c.playerRights >= 4) {
					c.playerTitle = 50;
					c.getPA().sendStatement("Re-log for full effect.");
				} else {
					c.getPA().sendStatement("You have to be a <img=3>regular donator to do this!");
				}
			} else {
				DiceHandler.handleClick(c, buttonId);
			}
			break;

		case 9191:
			switch (c.dialogueAction) {
			case 99:
				SkillingTasks.giveTask(c, TaskType.Medium);
				break;
			case 2300:
				c.getPA().closeAllWindows();
				c.getPA().startTeleport(3210, 3424, 0, "modern");
				c.sendMessage("<col=482CB8>You have teleported to the Varrock location.");
				break;
			}
			if (c.dialogueAction == 50) {
				c.profileColour = "red";
				c.sendMessage("@ceo@Your profile colour is now: @red@Red");
				c.getPA().closeAllWindows();
			} else if (c.dialogueAction == 101) {
				c.getPA().closeAllWindows();
				c.getPA().startTeleport(PlayerConfiguration.VARROCK_X, PlayerConfiguration.VARROCK_Y, 0, "modern");
				c.sendMessage("@ceo@You have been teleported to Varrock!");
			} else if (c.dialogueAction == 51) {
				c.profileColour = "whi";
				c.sendMessage("@ceo@Your profile colour is now: @whi@White");
				c.getPA().closeAllWindows();
			} else if (c.dialogueAction == 10000) {
				Miscellaneous.IpbanUser(c);
			} else if (c.dialogueAction == 100) {
				c.getDH().sendDialogues(115, 1);
			} else if (c.dialogueAction == 364 || c.dialogueAction == 365) {
				c.resetChoosen = c.dialogueAction == 364 ? Player.playerStrength : Player.playerPrayer;
				c.getPA().resetStatistic();
			} else if (c.dialogueAction == 136) {
				// c.getPA().closeAllWindows();
				// c.getPA().startTeleport(2906, 5260, 0, "modern");
				// c.sendMessage("<col=482CB8>You have been teleported to the
				// saradomin boss!");
			} else if (c.dialogueAction == 292) {
				if (c.playerRights >= 5) {
					c.playerTitle = 51;
					c.getPA().sendStatement("Re-log for full effect.");
				} else {
					c.getPA().sendStatement("You have to be a <img=4>Super donator to do this!");
				}
			} else {
				DiceHandler.handleClick(c, buttonId);
			}
			break;
		case 9192:
			switch (c.dialogueAction) {
			case 99:
				SkillingTasks.giveTask(c, TaskType.Hard);
				break;
			case 2300:
				c.getPA().closeAllWindows();
				c.getPA().startTeleport(2964, 3378, 0, "modern");
				c.sendMessage("<col=482CB8>You have teleported to the Falador location.");
				break;
			}
			if (c.dialogueAction == 50) {
				c.profileColour = "yel";
				c.sendMessage("@ceo@Your profile colour is now: @yel@Yellow");
				c.getPA().closeAllWindows();
			} else if (c.dialogueAction == 51) {
				c.profileColour = "cya";
				c.sendMessage("@ceo@Your profile colour is now: @cya@Cyan");
				c.getPA().closeAllWindows();

			} else if (c.dialogueAction == 10000) {
				Miscellaneous.muteUser(c);

			} else if (c.dialogueAction == 101) {
				c.getPA().closeAllWindows();
				c.getPA().startTeleport(PlayerConfiguration.CAMELOT_X, PlayerConfiguration.CAMELOT_Y, 0, "modern");
				c.sendMessage("@ceo@You have been teleported to Camelot!");
			} else if (c.dialogueAction == 100) {
				c.getDH().sendDialogues(125, 1);
			} else if (c.dialogueAction == 364 || c.dialogueAction == 365) {
				c.resetChoosen = c.dialogueAction == 364 ? Player.playerDefence : Player.playerMagic;
				c.getPA().resetStatistic();
			} else if (c.dialogueAction == 136) {
				// c.getPA().closeAllWindows();
				// c.getPA().startTeleport(2925, 5530, 2, "modern");
				// c.sendMessage("<col=482CB8>You have been teleported to the
				// zamorak boss!");
			} else if (c.dialogueAction == 292) {
				if (c.playerRights >= 6) {
					c.playerTitle = 53;
					c.getPA().sendStatement("Re-log for full effect.");
				} else {
					c.getPA().sendStatement("You have to be an <img=5>Extreme donator to do this!");
				}
			} else {
				DiceHandler.handleClick(c, buttonId);
			}
			break;

		case 9193:
			switch (c.dialogueAction) {
			case 99:
				SkillingTasks.checkComplete(c);
				break;
			case 2300:
				c.getPA().closeAllWindows();
				c.getPA().startTeleport(3093, 3244, 0, "modern");
				c.sendMessage("<col=482CB8>You have teleported to the Dranyor location.");
				break;
			}
			if (c.dialogueAction == 50) {
				c.profileColour = "gre";
				c.sendMessage("@ceo@Your profile colour is now: @gre@Green");
				c.getPA().closeAllWindows();
			} else if (c.dialogueAction == 51) {
				c.profileColour = "mag";
				c.sendMessage("@ceo@Your profile colour is now: @mag@Purple");
				c.getPA().closeAllWindows();
			} else if (c.dialogueAction == 10000) {
				Miscellaneous.ipMuteUser(c);
			} else if (c.dialogueAction == 105) {
				c.getPA().closeAllWindows();
				c.getPA().startTeleport(2538, 4716, 0, "modern");
				c.sendMessage("@ceo@You have been teleported to Mage Bank!");
			} else if (c.dialogueAction == 101) {
				c.getPA().closeAllWindows();
				c.getPA().startTeleport(PlayerConfiguration.FALADOR_X, PlayerConfiguration.FALADOR_Y, 0, "modern");
				c.sendMessage("@ceo@You have been teleported to Falador!");
			} else if (c.dialogueAction == 364) {
				c.resetChoosen = Player.playerHitpoints;
				c.getPA().resetStatistic();
			} else if (c.dialogueAction == 136) {
				c.getPA().closeAllWindows();
				c.getPA().startTeleport(2864, 5354, 2, "modern");
				c.sendMessage("<col=482CB8>You have been teleported to the bandos boss!");
			} else if (c.dialogueAction == 365) {
				c.getDH().sendDialogues(364, 409);
			} else {
				DiceHandler.handleClick(c, buttonId);
			}
			break;

		case 9194:
			if (c.dialogueAction == 50) {
				c.getDH().sendDialogues(51, 1);
			} else if (c.dialogueAction == 51) {
				c.getDH().sendDialogues(50, 1);
			} else if (c.dialogueAction == 2300) {
				c.getPA().closeAllWindows();
				c.getPA().startTeleport(3222, 3218, 0, "modern");
				c.sendMessage("<col=482CB8>You have teleported to the Lumbridge location.");
			} else if (c.dialogueAction == 100) {
				c.getPA().closeAllWindows();
			} else if (c.dialogueAction == 101) {
				c.getPA().closeAllWindows();
			} else if (c.dialogueAction == 105) {
				c.getPA().closeAllWindows();
			} else if (c.dialogueAction == 115) {
				c.getPA().closeAllWindows();
			} else if (c.dialogueAction == 125) {
				c.getPA().closeAllWindows();
			} else if (c.dialogueAction == 364) {
				c.getDH().sendDialogues(365, 409);

			} else if (c.dialogueAction == 99) {
				SkillingTasks.cancelTask(c);

			} else if (c.dialogueAction == 365) {
				c.getPA().closeAllWindows();
			} else if (c.dialogueAction == 292) {
				c.getPA().closeAllWindows();
			} else if (c.dialogueAction == 10000) {
				c.getPA().closeAllWindows();
			} else {
				DiceHandler.handleClick(c, buttonId);
			}
			break;

		}
	}
}