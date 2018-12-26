package org.mistex.game.world.content.dialogue;

import org.mistex.game.Mistex;
import org.mistex.game.MistexUtility;
import org.mistex.game.world.content.StarterPack;
import org.mistex.game.world.content.interfaces.InterfaceText;
import org.mistex.game.world.content.interfaces.impl.InformationTab;
import org.mistex.game.world.content.interfaces.impl.QuestInterface;
import org.mistex.game.world.content.minigame.barrows.Barrows;
import org.mistex.game.world.content.pvp.PvPArtifacts;
import org.mistex.game.world.content.quests.impl.AvoidingCatastrophe;
import org.mistex.game.world.content.quests.impl.ChessQuest;
import org.mistex.game.world.content.quests.impl.CooksAssistant;
import org.mistex.game.world.content.quests.impl.DoricsQuest;
import org.mistex.game.world.event.CycleEvent;
import org.mistex.game.world.event.CycleEventContainer;
import org.mistex.game.world.event.CycleEventHandler;
import org.mistex.game.world.npc.NPCHandler;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.PlayerConfiguration;
import org.mistex.game.world.shop.ShopExecutor;

@SuppressWarnings("unused")
public class DialogueHandler {

	private Client c;

	public DialogueHandler(Client client) {
		this.c = client;
	}

	@SuppressWarnings("static-access")
	public void sendDialogues(int dialogue, int npcId) {
		c.talkingNpc = npcId;
		switch (dialogue) {

		/*
		 * Zoo keeper dialogue
		 */
		case 2400:
			sendNpcChat3("Adventurer! I need your help!", "My exotic pets have escaped and have found a new home.", "Do you want to take a look?", 7571, "Zoo Keeper");
			c.nextChat = 2401;
			break;
		case 2401:
			sendOption2("Yes", "No");
			c.dialogueAction = 2401;
			break;
		case 2402:
			sendNpcChat2("Good luck! By the way,", "They drop different colored whips.", 7571, "Zoo Keeper");
			c.nextChat = 2403;
			break;
		case 2403:
			c.getPA().startTeleport(2168, 5297, 0, "modern");
			c.nextChat = 0;
			break;

		/*
		 * Teleporting dialogue
		 */
		case 2300:// Location Teleport
			sendOption5("Camelot", "Varrock", "Falador", "Dranyor", "Lumbridge");
			c.dialogueAction = 2300;
			break;
		/*
		 * Tzhaar shop dialogue
		 */
		case 2900:
			sendNpcChat1("Hello " + MistexUtility.capitalize(c.playerName) + "!", 2620, "TzHaar-Hur-Tel");
			c.nextChat = 2901;
			break;
		case 2901:
			ShopExecutor.open(c, 32);
			break;

		/*
		 * Veteran dialogue
		 */
		case 3000:
			sendNpcChat2("Hello " + MistexUtility.capitalize(c.playerName) + "!", "How may I help you today?", 7143, "Veteran");
			c.nextChat = 3001;
			break;
		case 3001:
			sendOption3("Can I get a veteran cape?", "Who are 'veterans'?", "Nothing");
			c.dialogueAction = 3001;
			break;
		case 3002:
			sendNpcChat1("You need to have a game time of 30 days to do this!", 7143, "Veteran");
			c.nextChat = 3001;
			break;
		case 3003:
			sendNpcChat1("You need to have 1,000,000 coins to do this!", 7143, "Veteran");
			c.nextChat = 3001;
			break;
		case 3004:
			sendNpcChat1("I want to thank you for staying with us! Enjoy.", 7143, "Veteran");
			c.nextChat = 0;
			break;
		case 3005:
			sendNpcChat2("A veteran is a person who has played Mistex", "for 30 or more days.", 7143, "Veteran");
			c.nextChat = 3001;
			break;

		/*
		 * Bank pin dialogue
		 */
		case 1270:
			sendNpcChat2("Hello " + MistexUtility.capitalize(c.playerName) + "!", "Would you like to delete your bank pin?", 494, "Banker");
			c.nextChat = 1272;
			break;
		case 1272:
			sendOption2("Yes", "No");
			c.dialogueAction = 1272;
			break;
		case 1273:
			sendNpcChat1("Your pin has been deleted.", 494, "Banker");
			c.nextChat = 1274;
			break;
		case 1274:
			c.getBank().openBank();
			break;
		case 1100:
			sendOption2("I have forgotten my bank pin!", "Nevermind");
			c.dialogueAction = 1100;
			break;

		/*
		 * Decanting dialogue
		 */
		case 1280:
			sendNpcChat1("Hello " + MistexUtility.capitalize(c.playerName) + "!", 300, "Dr. Decant");
			c.nextChat = 1281;
			break;
		case 1281:
			sendNpcChat1("I can decant your inventory for 100,000 coins.", 300, "Dr. Decant");
			c.nextChat = 1282;
			break;
		case 1282:
			sendOption3("Sure.", "No thanks.", "What is decanting?");
			c.dialogueAction = 1282;
			break;
		case 1285:
			sendNpcChat3("Decanting is the method in which a player combines", "partially full potions of the same kind to produce", "one full potion and one partially empty potion.", 300, "Dr. Decant");
			c.nextChat = 1286;
			break;
		case 1286:
			sendNpcChat3("For example, decanting a potion containing 3 doses", "and another containing 2 doses yields one full potion (4 doses)", "and one partially full potion (1 dose).", 300, "Dr. Decant");
			c.nextChat = 1282;
			break;
		case 1287:
			sendNpcChat1("The transaction was successful!", 300, "Dr. Decant");
			c.nextChat = 0;
			break;
		case 1288:
			sendNpcChat1("You need 100,000 coins to do this!", 300, "Dr. Decant");
			c.nextChat = 0;
			break;
		case 1289:
			sendNpcChat1("You need at least 1 free inventory slot.", 300, "Dr. Decant");
			c.nextChat = 0;
			break;
		case 1290:
			sendNpcChat1("You have nothing in your inventory!", 300, "Dr. Decant");
			c.nextChat = 0;
			break;

		/*
		 * Dungeoneering dialogue
		 */
		case 1120:
			sendNpcChat1("Hello " + MistexUtility.capitalize(c.playerName) + "!", 9713, "Thok, Master of Dungeoneering");
			c.nextChat = 1121;
			break;
		case 1121:
			ShopExecutor.open(c, 30);
			break;

		case 1125:
			sendNpcChat1("Hello " + MistexUtility.capitalize(c.playerName) + "!", 9712, "Dungeoneering tutor");
			c.nextChat = 1126;
			break;
		case 1126:
			sendNpcChat1("How may I help you today?", 9712, "Dungeoneering tutor");
			c.nextChat = 1127;
			break;
		case 1127:
			sendOption4("Explain solo dungeons.", "Explain co-op dungeons.", "What's up with the pig?", "Nothing.");
			c.dialogueAction = 1127;
			break;

		case 10000:
			Client opponent = (Client) Mistex.playerHandler.players[c.playerIndex];
			sendBanOption("Ban " + opponent.playerName + "", "Ip Ban " + opponent.playerName + "", "Mute " + opponent.playerName + "", "Ip Mute " + opponent.playerName + "", "Nothing");
			c.dialogueAction = 10000;
			c.nextChat = 0;
			break;

		case 1300:
			sendNpcChat1("Yeee haw!", c.summoningnpcid, MistexUtility.formatPlayerName(Mistex.npcHandler.getNpcName(c.summoningnpcid).replaceAll("_", " ")));
			c.nextChat = 0;
			break;
		case 1301:
			sendNpcChat1("Boom shakalaka!", c.summoningnpcid, MistexUtility.formatPlayerName(Mistex.npcHandler.getNpcName(c.summoningnpcid).replaceAll("_", " ")));
			c.nextChat = 0;
			break;

		case 22:
			sendOption2("Pick the flowers", "Leave the flowers");
			c.nextChat = 0;
			c.dialogueAction = 22;
			break;
		case 111:
			sendNpcChat3("Hello " + MistexUtility.capitalize(c.playerName) + "!", "My name is Max, I am the master of all skills in Mistex!", "How may i help you?", 15976, "Max");
			c.nextChat = 112;
			break;
		case 112:
			sendOption2("How do i obtain a Max cape?", "Nevermind");
			c.dialogueAction = 112;
			break;
		case 95:
			sendNpcChat3("Hello " + MistexUtility.capitalize(c.playerName) + "!", "I am the master of the skill woodcutting.", "How may I help you?", 5964, "Ed Wood");
			c.nextChat = 96;
			break;
		case 96:
			sendOption3("How do I get skilling points?", "I would like to trade.", "Skilling task.");
			c.dialogueAction = 96;
			break;
		case 97:
			sendNpcChat3("It's quite easy.", "While training any non-combat skills you will randomly be", "rewarded with an 'x' amount of points.", 5964, "Ed Wood");
			c.nextChat = 98;
			break;
		case 98:
			sendNpcChat2("Be advised that donators will have a better chance ", "to be rewarded with points.", 5964, "Ed Wood");
			c.nextChat = 96;
			break;

		case 99:
			sendOption5("Easy task", "Medium task", "Hard task", "Give items", "Cancel task");
			c.dialogueAction = 99;
			break;

		case 356:
			sendNpcChat1("You currently don't have a skilling task!", 5964, "Ed Wood");
			c.nextChat = 99;
			break;

		case 357:
			sendNpcChat1("You need 100,000 coins to delete your easy task!", 5964, "Ed Wood");
			c.nextChat = 99;
			break;

		case 358:
			sendNpcChat1("You need 250,000 coins to delete your medium task!", 5964, "Ed Wood");
			c.nextChat = 99;
			break;

		case 369:
			sendNpcChat1("You need 500,000 coins to delete your hard task!", 5964, "Ed Wood");
			c.nextChat = 99;
			break;

		case 70:
			sendOption2("Cape Shop 1", "Cape Shop 2");
			c.dialogueAction = 70;
			break;

		case 291:
			sendNpcChat2("Hello " + MistexUtility.capitalize(c.playerName) + "!", "I have the ability to change your loyalty title!", 10, "Schoolgirl");
			c.nextChat = 292;
			break;
		case 292:
			sendOption5("Title: Donator", "Title: Super", "Title: Extreme", "", "Nevermind");
			c.dialogueAction = 292;
			break;

		case 315:
			sendStatement("The minigame you are about to enter is extremely difficult.");
			c.nextChat = 316;
			break;
		case 316:
			sendStatement("You will have to face various difficult monsters.");
			c.nextChat = 317;
			break;
		case 317:
			sendStatement("However, completion of this minigame will reward you greatly.");
			c.nextChat = 318;
			break;
		case 318:
			sendStatement("The entry fee for this minigame is 500k (500,000 coins).");
			c.nextChat = 319;
			break;
		case 319:
			sendStatement("Would you like to play?");
			c.nextChat = 320;
			break;
		case 320:
			sendOption2("Hell yeah!", "No thanks.");
			c.dialogueAction = 320;
			break;

		case 340:
			sendNpcChat4("Hello " + MistexUtility.capitalize(c.playerName) + "!", "I am the richest person on Mistex.", "I can exchange 1 billion coins for a billion ticket.", "A billion ticket represents 1 billion coins.", 290, "Dr Orbon");
			c.nextChat = 341;
			break;

		case 341:
			sendOption2("Exchange ticket for coins", "Exchange coins for ticket");
			c.dialogueAction = 341;
			break;

		case 23:
			c.getDH().sendStartInfo("As you collect your reward, you notice an aweful smell.", "You look below the remaining debris to the bottom of the", "chest. You see a trapdoor. You open it and it leads to a ladder", "that goes down a long ways.", "Continue?");
			break;
		case 24:
			c.getDH().sendStatement("Would you like to continue?");
			c.nextChat = 25;
			break;
		case 25:
			c.dialogueAction = 25;
			c.getDH().sendOption2("Yes, I'm not afraid of anything!", "No way, the smell itself turns me away.");
			break;
		case 26:
			c.getDH().sendStatement("This is a very dangerous minigame, are you sure?");
			c.nextChat = 27;
			break;
		case 27:
			c.dialogueAction = 27;
			sendOption2("Yes, I'm a brave warrior!", "Maybe I shouldn't, I could lose my items!");
			break;
		case 28:
			c.getDH().sendStatement("Congratulations, " + c.playerName + ". You've completed the barrows challenge & your reward has been delivered.");
			c.nextChat = 0;
			break;
		case 29:
			sendStatement("Are you ready to visit the chest room?");
			c.nextChat = 30;
			c.dialogueAction = 29;
			break;
		case 30:
			sendOption2("Yes, I've killed all the other brothers!", "No, I still need to kill more brothers");
			c.nextChat = 0;
			break;

		/* Genie */

		case 363:
			sendNpcChat4("Hello " + MistexUtility.capitalize(c.playerName) + "!", "I am a magical genie that can reset your statistics.", "For a price of course!", "5 Million coins to be exact.", 3022, "Genie");
			c.nextChat = 364;
			break;
		case 364:
			sendOption5("Reset Attack", "Reset Strength", "Reset Defence", "Reset Hitpoints", "Next");
			c.dialogueAction = 364;
			break;
		case 365:
			sendOption5("Reset Range", "Reset Prayer", "Reset Magic", "Back", "Nevermind");
			c.dialogueAction = 365;
			break;

		case 362:
			sendNpcChat1("You need at least 5,000,000 coins to do this!", 3022, "Genie");
			c.nextChat = 364;
			break;

		case 361:
			sendNpcChat1("Please remove all your equipment first!", 3022, "Genie");
			c.nextChat = 364;
			break;

		case 360:
			sendNpcChat1("You successfully reset your skill!", 3022, "Genie");
			c.nextChat = 0;
			break;

		case 359:
			sendNpcChat1("What's the point of reseting a skill that's already been reset?", 3022, "Genie");
			c.nextChat = 0;
			break;

		/* Prestige */
		case 475:
			sendNpcChat3("Hello " + MistexUtility.capitalize(c.playerName) + "!", "My name is Scarlet,", "I am in charge of the Mistex prestiging system.", 2579, "Scarlet");
			c.nextChat = 476;
			break;
		case 476:
			sendOption4("I want to prestige!", "How does prestiging work?", "I want to see your shop", "Nevermind");
			c.dialogueAction = 476;
			break;

		/* Barrows Tunnel */
		case 155:
			sendOption2("Yeah I'm fearless", "No way that looks scary");
			c.dialogueAction = 982;
			break;

		case 445:
			sendNpcChat3("Hey mate, mind getting me", "some clay and a couple ores? You will be", "rewarded. Interessed?", c.talkingNpc, "Doric");
			c.nextChat = 446;
			break;
		case 446:
			sendOption2("Sure.", "Aw, I gotta go sorry.");
			c.dialogueAction = 118;
			break;
		case 447:
			sendPlayerChat1("Sure.");
			c.doricsQuest = 1;
			c.nextChat = 448;
			break;
		case 448:
			sendNpcChat3("Oh, that's great! Now, I", "need some clay, some copper, and", "a couple iron ores.", c.talkingNpc, "Doric");
			c.nextChat = 449;
			break;
		case 449:
			sendPlayerChat1("Alright, I'll get it for yoz.");

			c.nextChat = 0;
			break;
		case 500:
			sendPlayerChat1("Aw, I gotta go sorry.");
			c.nextChat = 449;
			break;
		case 501:
			sendNpcChat3("You'll have to go? Oh I see, it's 'cause I'm", "short, right? You fagbgots need to learn", "some respect for us dwarfs. Get out of my house!", c.talkingNpc, "Doric");
			c.nextChat = 0;
			break;
		case 502:
			sendNpcChat1("So, you got my items, mate?", c.talkingNpc, "Doric");
			c.nextChat = 503;
			break;
		case 503:
			if (c.getItems().playerHasItem(437, 4) && c.getItems().playerHasItem(441, 2) && c.getItems().playerHasItem(435, 6)) {
				sendPlayerChat1("Here's all the items!");
				c.nextChat = 504;
			} else {
				sendPlayerChat1("I don't have all the items yet.");
				c.nextChat = 506;
			}
			break;
		case 504:
			c.getItems().deleteItem(437, 4);
			c.getItems().deleteItem(435, 6);
			c.getItems().deleteItem(441, 2);
			c.doricsQuest = 2;
			sendNpcChat1("Thanks mate! You're the best!", c.talkingNpc, "Doric");
			c.nextChat = 505;
			InterfaceText.writeText(new InformationTab(c));
			break;
		case 505:
			DoricsQuest.doricFinish(c);
			break;
		case 506:
			sendNpcChat1("Well then go get them, I need it now!!", c.talkingNpc, "Doric");
			c.nextChat = 0;
			break;
		case 507:
			sendNpcChat1("Enjoy your experience, you can now use my anvil.", c.talkingNpc, "Doric");
			c.nextChat = 0;
			break;

		/* end of dorics quest */

		/*
		 * Cooks Assistant
		 * 
		 */

		case 800:
			sendNpcChat1("What am I to do?", c.talkingNpc, "Cook");
			c.nextChat = 801;
			break;
		case 801:
			sendOption4("What's wrong?", "Can you cook me a cake?", "You don't look very happy.", "Nice hat.");
			c.dialogueAction = 801;
			break;
		case 802:
			sendPlayerChat1("What's wrong?");
			c.nextChat = 803;
			break;
		case 803:
			sendNpcChat3("Oh dear, oh dear, oh dear, I'm in a terrible terrible", "mess! It's the Duke's birthday today, and I should be", "making him a lovely big birthday cake!", c.talkingNpc, "Cook");
			c.nextChat = 804;
			break;
		case 804:
			sendNpcChat4("I've forgotten to buy the ingredients. I'll never get", "them in time now. He'll sack me! What will I do? I have", "four children and a goat to look after. Would you help", "me? Please?", c.talkingNpc, "Cook");
			c.nextChat = 805;
			break;
		case 805:
			sendOption2("I'm always happy to help a cook in distress.", "I can't right now, Maybe later.");
			c.dialogueAction = 805;
			break;
		case 806:
			sendPlayerChat1("Yes, I'll help you.");
			c.nextChat = 809;
			InterfaceText.writeText(new InformationTab(c));
			break;
		case 807:
			sendPlayerChat1("I can't right now, Maybe later.");
			c.nextChat = 808;
			break;
		case 808:
			sendNpcChat1("Oh please! Hurry then!", c.talkingNpc, "Cook");
			c.nextChat = 0;
			break;
		case 809:
			sendNpcChat2("Oh thank you, thank you. I need milk, an egg, and", "flour. I'd be very grateful if you can get them for me.", c.talkingNpc, "Cook");
			c.cookAss = 1;
			c.nextChat = 810;
			break;
		case 810:
			sendPlayerChat1("So where do I find these ingredients then?");
			c.nextChat = 811;
			break;
		case 811:
			sendNpcChat3("You can find flour in any of the shops here.", "You can find eggs by killing chickens.", "You can find milk by using a bucket on a cow", c.talkingNpc, "Cook");
			c.nextChat = 0;
			break;
		case 812:
			sendNpcChat1("I don't have time for your jibber-jabber!", c.talkingNpc, "Cook");
			c.nextChat = 0;
			break;
		case 813:
			sendNpcChat1("Does it look like I have the time?", c.talkingNpc, "Cook");
			c.nextChat = 0;
			break;
		case 814:
			sendPlayerChat1("You don't look so happy.");
			c.nextChat = 803;
			break;
		case 815:
			sendNpcChat1("How are you getting on with finding the ingredients?", c.talkingNpc, "Cook");
			c.nextChat = 818;
			break;
		case 816:
			if (c.getItems().playerHasItem(1944, 1) && c.getItems().playerHasItem(1927, 1) && c.getItems().playerHasItem(1933, 1)) {
				sendPlayerChat1("Here's all the items!");
				c.nextChat = 818;
			} else {
				sendPlayerChat1("I don't have all the items yet.");
				c.nextChat = 808;
			}
			break;
		case 818:
			c.getItems().deleteItem(1944, 1);
			c.getItems().deleteItem(1927, 1);
			c.getItems().deleteItem(1933, 1);
			c.cookAss = 2;
			sendNpcChat2("You brought me everything I need! I'm saved!", "Thank you!", c.talkingNpc, "Cook");
			c.nextChat = 819;
			break;
		case 819:
			sendPlayerChat1("So do I get to go to the Duke's Party?");
			c.nextChat = 820;
			break;
		case 820:
			sendNpcChat2("I'm afraid not, only the big cheeses get to dine with the", "Duke.", c.talkingNpc, "Cook");
			c.nextChat = 821;
			break;
		case 821:
			sendPlayerChat2("Well, maybe one day I'll be important enough to sit on", "the Duke's table");
			c.nextChat = 822;
			break;
		case 822:
			sendNpcChat1("Maybe, but I won't be holding my breath.", c.talkingNpc, "Cook");
			c.nextChat = 823;
			break;
		case 823:
			CooksAssistant.rewards(c);
			break;
		case 824:
			sendNpcChat1("Thanks for helping me out friend!", c.talkingNpc, "Cook");
			c.nextChat = 0;
			break;

		/* Quest: Game of chess dialogue */

		case 600:
			sendPlayerChat2("Hello there!", "");
			c.nextChat = 601;
			break;
		case 601:
			sendNpcChat2("Those mucles, the chizzled cheeks...", "You are perfect!", 610, "Black Knight Captain");
			c.nextChat = 602;
			break;
		case 602:
			sendPlayerChat1("What...? Are you okay? What are you talking about?");
			c.nextChat = 603;
			break;
		case 603:
			sendNpcChat2("Nevermind...", "We need your help adventurer.", 610, "Black Knight Captain");
			c.nextChat = 604;
			break;
		case 604:
			sendPlayerChat1("Help for what?");
			c.nextChat = 605;
			break;
		case 605:
			sendNpcChat3("Those damn white knights!", "They came into our home and caputured our family members!", "But the worst part is that they stole our crystal key!", 610, "Black Knight Captain");
			c.nextChat = 606;
			break;
		case 606:
			sendPlayerChat1("How is a crystal key more important than family members?");
			c.nextChat = 607;
			break;
		case 607:
			sendNpcChat1("Do you know what the key opens?", 610, "Black Knight Captain");
			c.nextChat = 608;
			break;
		case 608:
			sendPlayerChat1("No obviously not. How would I?");
			c.nextChat = 609;
			break;
		case 609:
			sendNpcChat2("Exactly!", "Now without our key we won't be able to live another day.", 610, "Black Knight Captain");
			c.nextChat = 610;
			break;
		case 610:
			sendPlayerChat2("First of all who is 'we'? I only see you here.", " Secondly how the hell does a key effect your health?");
			c.nextChat = 611;
			break;
		case 611:
			sendNpcChat1("Can you just help us?", 610, "Black Knight Captain");
			c.nextChat = 612;
			break;
		case 612:
			sendOption2("Sure.", "No sorry.");
			c.dialogueAction = 612;
			break;

		/** If player says no **/
		case 613:
			sendNpcChat1("How dare you!", 610, "Black Knight Captain");
			c.nextChat = 0;
			ChessQuest.handleNoOption(c);
			break;

		/** If player says yes **/
		case 614:
			sendNpcChat3("I knew you were brave enough!", "I need you to go to the white knights' castle", "in Falador and tell me anything you can find.", 610, "Black Knight Captain");
			c.nextChat = 615;
			break;

		case 615:
			sendPlayerChat1("And what exactly am I supposed to be looking for?");
			c.nextChat = 616;
			break;

		case 616:
			sendNpcChat3("Listen!", "It's all about the key! Got it?", "Just find me the damn key!", 610, "Black Knight Captain");
			c.nextChat = 617;
			break;

		case 617:
			sendPlayerChat1("Alright, alright relax. I will see what I can find.");
			c.gameOfChess++;
			InterfaceText.writeText(new InformationTab(c));
			c.nextChat = 0;
			break;

		case 618:
			sendPlayerChat1("Hey, have you seen a key around here?");
			c.nextChat = 619;
			break;

		case 619:
			sendNpcChat1("Key? I don't know anything about a key...", 608, "Sir Amik Varze");
			c.nextChat = 0;
			break;

		case 620:
			sendNpcChat1("Hey " + MistexUtility.formatPlayerName(c.playerName) + "! Have you been...", 825, "Male Slave");
			c.nextChat = 621;
			break;

		case 621:
			sendNpcChat1("You there! Get back to work!", 608, "Sir Amik Varze");
			c.gameOfChess++;
			c.nextChat = 0;
			break;

		case 622:
			sendNpcChat1("*whispering* Have you been sent here by Frank?", 825, "Male Slave");
			c.nextChat = 623;
			break;

		case 623:
			sendPlayerChat1("Frank? Who the hell...");
			c.nextChat = 624;
			break;

		case 624:
			sendNpcChat4("SHHHH! Keep your voice down.", "Frank is the captain of all the black knights.", "I was a black night myself, but I was captured.", "Has Frank told you about the key?", 825, "Male Slave");
			c.nextChat = 625;
			break;

		case 625:
			sendPlayerChat2("Oh yeah, he mentioned it.", "Have any idea where I could find it?");
			c.nextChat = 626;
			break;

		case 626:
			sendNpcChat2("The last place I saw the key was in one of chests in the torture", "chambers in the castle. Please find it!", 825, "Male Slave");
			c.nextChat = 0;
			c.gameOfChess++;
			break;

		case 627:
			sendPlayerChat2("I have your key...", "...Frank...");
			c.nextChat = 628;
			break;

		case 628:
			sendNpcChat1("Erm, thanks. You don't know how much this helps me.", 610, "Black Knight Captain");
			c.nextChat = 629;
			c.gameOfChess++;
			break;

		case 629:
			c.getPA().movePlayer(2961, 3339, 3);
			sendNpcChat1("The commander of the white knights would like to talk with you.", 606, "Squire");
			c.nextChat = 0;
			c.gameOfChess++;
			break;

		case 630:
			sendNpcChat1("What the HELL have you done!?", 608, "Sir Amik Varze");
			c.nextChat = 631;
			break;

		case 631:
			sendPlayerChat1("What? What are you...uh-oh...");
			c.nextChat = 633;
			break;

		case 633:
			sendNpcChat3("YEAH! UH-OH IS RIGHT!", "What the hell were you thinking of when", "you gave that black knight the key?", 608, "Sir Amik Varze");
			c.nextChat = 634;
			break;

		case 634:
			sendPlayerChat2("Well, excuse me! I was just doing someone a favor!", "Why would you have a sinister key in a chest anyways?");
			c.nextChat = 635;
			break;

		case 635:
			sendNpcChat3("THAT key was to be kept away from that", "little rat! Do you know what that key", "even opens?", 608, "Sir Amik Varze");
			c.nextChat = 636;
			// c.gameOfChess++;
			break;

		/* Start of AC quest */
		case 700:
			sendNpcChat2("Hello child. I see you are experienced in Prayer.", "Yet there are 2 prayers you are not yet aware of.", 456, "Father Aereck");
			c.nextChat = 701;
			break;

		case 701:
			sendPlayerChat1("Really? What are they??");
			c.nextChat = 702;
			break;

		case 702:
			sendNpcChat3("Aha! I tell you what. I will enlighten you, but you", "must first do a favor for me..", "I wouldn't ask you, but people don't come around often.", 456, "Father Aereck");
			c.nextChat = 703;
			break;

		case 703:
			sendPlayerChat2("Well it's 2014.. Who still comes to lumbridge?", "Anyways what do you need?");
			c.nextChat = 704;
			break;

		case 704:
			sendNpcChat4("Zamorak mages' have been attacking the people of Lumbridge.", "They abducted the citizens and then returned them... brainwashed.", "The Duke of Lumbridge is the only one who they are not strong enough", "to manipulate, and that is in fact only because of an ancient spell cast...", 456, "Father Aereck");
			c.nextChat = 705;
			break;

		case 705:
			sendNpcChat1("Upon the Lumbridge Castle, which the Duke doesn't leave.", 456, "Father Aereck");
			c.nextChat = 706;
			break;

		case 706:
			sendPlayerChat3("What the hell man!?", "I came down here thinking this would be some easy errand quest.", "And now you tell me all of this?");
			c.nextChat = 707;
			break;

		case 707:
			sendNpcChat4("Their evil plot is to counter-activate that spell.", "We must stop this at all costs!", "I have an informant in Varrock. Aubury of the runecrafting shop in the south-eastern", "corner of the city. Go speak to him, and bring his information back to me.", 456, "Father Aereck");
			c.nextChat = 0;
			c.AcQuest = 1;
			InterfaceText.writeText(new InformationTab(c));
			break;

		case 708:
			sendPlayerChat1("Hello there!");
			c.nextChat = 709;
			break;

		case 709:
			sendNpcChat1("Whaat do you want? Leave me alone!", 553, "Aubury");
			c.nextChat = 710;
			break;

		case 710:
			sendPlayerChat1("But Father Aereck sent me!");
			c.nextChat = 711;
			break;

		case 711:
			sendNpcChat1("Why?", 553, "Aubury");
			c.nextChat = 712;
			break;

		case 712:
			sendPlayerChat1("He wants to know what's going on with the brainwashing and the such.");
			c.nextChat = 713;
			break;

		case 713:
			sendNpcChat1("I don't know what you are talking about. Go away.", 553, "Aubury");
			c.nextChat = 0;
			c.AcQuest = 2;
			InterfaceText.writeText(new InformationTab(c));
			break;

		case 714:
			sendPlayerChat1("Father, Aubury is not listening to me! He keeps pushing me away.");
			c.nextChat = 715;
			break;

		case 715:
			sendNpcChat1("He must not trust you then... We need to gain his trust.", 456, "Father Aereck");
			c.nextChat = 716;
			break;

		case 716:
			sendNpcChat1("I will give him a holy napkin, however I will need a few ingredients.", 456, "Father Aereck");
			c.nextChat = 717;
			break;

		case 717:
			sendNpcChat2("Get me the following:", "100 Normal Noted logs, 20 bones, 100 cosmic runes, and 1 rune dagger.", 456, "Father Aereck");
			c.nextChat = 718;
			c.AcQuest = 3;
			InterfaceText.writeText(new InformationTab(c));
			break;

		case 718:
			sendPlayerChat1("And that's going to make a holy napkin?");
			c.nextChat = 719;
			break;

		case 719:
			sendNpcChat1("How else would you make it?", 456, "Father Aereck");
			c.nextChat = 0;
			break;

		case 720:
			sendNpcChat1("Alright awesome! Give this to Aubury.", 456, "Father Aereck");
			c.nextChat = 0;
			c.AcQuest = 4;
			InterfaceText.writeText(new InformationTab(c));
			c.getItems().deleteItem(1512, 100);
			c.getItems().deleteItem(527, 20);
			c.getItems().deleteItem(564, 100);
			c.getItems().deleteItem(1213, 1);
			c.getItems().addItem(15, 1);
			break;

		case 721:
			sendPlayerChat1("Aubury look!");
			c.nextChat = 722;
			break;

		case 722:
			sendStatement("You hand Aubury the holy napkin");
			c.nextChat = 723;
			c.getItems().deleteItem(15, 1);
			c.AcQuest = 5;
			break;

		case 723:
			sendNpcChat2("Sorry " + MistexUtility.capitalize(c.playerName) + "", "I had to be careful of who I talk to.", 553, "Aubury");
			c.nextChat = 724;
			break;

		case 724:
			sendPlayerChat1("That's fine. So what's happening?");
			c.nextChat = 725;
			break;

		case 725:
			sendNpcChat2("Tell father aereck that the zamorakian forces are deciphering the counter-activation", "spell that protects the castle, and will likely be done in a matter of days.", 553, "Aubury");
			c.nextChat = 0;
			c.AcQuest = 5;
			InterfaceText.writeText(new InformationTab(c));
			break;

		case 726:
			sendStatement("You update the Father with the new information.");
			c.nextChat = 727;
			break;

		case 727:
			sendNpcChat2("Oh My GOD!", "That's not possible. No it can't be! It's too soon! They will kill us all!", 456, "Father Aereck");
			c.nextChat = 729;
			break;

		case 729:
			sendPlayerChat1("Relax bro! It's all just a bunch of pixels.");
			c.nextChat = 730;
			break;

		case 730:
			sendNpcChat2("You don't understand!", "We need to stop this! It has to be our number one priority.", 456, "Father Aereck");
			c.nextChat = 731;
			break;

		case 731:
			sendPlayerChat1("How can I help?");
			c.nextChat = 732;
			break;

		case 732:
			sendNpcChat1("You will need to talk to the Duke in lumbridge castle.", 456, "Father Aereck");
			c.nextChat = 0;
			c.AcQuest = 6;
			InterfaceText.writeText(new InformationTab(c));
			break;

		case 733:
			sendPlayerChat1("Duke! We don't have much time the-");
			c.nextChat = 734;
			break;

		case 734:
			sendNpcChat2("Calm down...", "Take a deep breathe and just take everything in.", 741, "Duke Horacio");
			c.nextChat = 735;
			break;

		case 735:
			sendPlayerChat1("What? No we don't have time the zamorak-");
			c.nextChat = 736;
			break;

		case 736:
			sendNpcChat3("Listen man you are killing my buzz.", "I know I know the mages are coming. So what?", "We all are going to die one day or another.", 741, "Duke Horacio");
			c.nextChat = 737;
			break;

		case 737:
			sendPlayerChat1("So we do what? Just wait and die?");
			c.nextChat = 738;
			break;

		case 738:
			sendNpcChat1("Well we could smoke some weed...", 741, "Duke Horacio");
			c.nextChat = 739;
			break;

		case 739:
			sendPlayerChat1("Are you serious? I don't have any... And I don't think this is the time...");
			c.nextChat = 740;
			break;

		case 740:
			sendNpcChat1("Don't worry about it! Take this...", 741, "Duke Horacio");
			c.nextChat = 741;
			break;

		case 741:
			sendStatement("*The Duke puts the joint in your mouth and lights it*");
			c.nextChat = 742;
			break;

		case 742:
			sendPlayerChat1("Ohh damnnn. That's good.");
			c.forcedChat("*Cough* *Cough*");
			c.nextChat = 743;
			break;

		case 743:
			sendPlayerChat1("Seriously what we going to do about the pending attack.");
			c.nextChat = 744;
			break;

		case 744:
			sendNpcChat1("I guess we can start by making a potion for the brainwashing. ", 741, "Duke Horacio");
			c.nextChat = 745;
			break;

		case 745:
			sendNpcChat2("Use a harpoon on a vial in the home bank ", "then with the finished potion use it on me.", 741, "Duke Horacio");
			c.nextChat = 746;
			c.AcQuest = 7;
			InterfaceText.writeText(new InformationTab(c));
			break;

		case 746:
			sendNpcChat1("Good luck.", 741, "Duke Horacio");
			c.nextChat = 0;
			AvoidingCatastrophe.handleWeedSmoking(c);

			break;

		case 747:
			sendNpcChat1("Great job! Now all you need to do now is go to the wizard tower.", 741, "Duke Horacio");
			c.nextChat = 748;
			break;

		case 748:
			sendPlayerChat1("Then what do I do?");
			c.nextChat = 749;
			break;

		case 749:
			sendNpcChat1("Nothing don't worry just go.", 741, "Duke Horacio");
			c.nextChat = 0;
			c.AcQuest = 8;
			InterfaceText.writeText(new InformationTab(c));
			break;

		case 750:
			sendPlayerChat1("Father I killed some zamorak mager and recived some kind of note!");
			c.nextChat = 752;
			break;

		case 752:
			sendNpcChat2("Let me see this...", "My god! You have done it!", 456, "Father Aereck");
			c.nextChat = 753;
			break;

		case 753:
			sendPlayerChat1("Done what?");
			c.nextChat = 754;
			break;

		case 754:
			sendNpcChat2("This note contains all the secrets to the zamorak mages.", "With this we can stop the attack!", 456, "Father Aereck");
			c.nextChat = 755;
			break;

		case 755:
			sendPlayerChat1("Sweet! I just saved a bunch of pixels!");
			c.nextChat = 756;
			break;

		case 756:
			sendNpcChat1("I wish there was some way to repay you...", 456, "Father Aereck");
			c.nextChat = 757;
			break;

		case 757:
			sendPlayerChat1("Teach me the new prayers!");
			c.nextChat = 758;
			break;

		case 758:
			sendNpcChat1("Oh right!", 456, "Father Aereck");
			c.nextChat = 759;
			break;

		case 759:
			c.AcQuest = 10;
			c.questCompleted += 1;
			c.getPA().addSkillXP(300000, 5);
			AvoidingCatastrophe.stageTen(c);
			c.getPA().showInterface(60600);
			break;

		/* End of quest dialogues */

		case 136:
			sendOption5("Armadyl", "Saradomin(SOON)", "Zamorak(SOON)", "Bandos", "Nevermind");
			c.dialogueAction = 136;
			break;

		case 182:
			sendNpcChat1("Hello adventurer, how may I help you?", 2024, "Weird old man");
			c.nextChat = 183;
			break;
		case 183:
			sendOption2("Can you repair my broken barrow pieces?", "Can you reset my barros.");
			c.dialogueAction = 183;
			break;

		case 74:
			sendOption2("General Skilling Supplies", "Talismans & Herblore");
			c.dialogueAction = 74;
			break;

		case 86:
			sendFarmingOptions("Falador", "Catherby", "Phasmatys", "Ardougne");
			c.dialogueAction = 86;
			break;

		case 94:
			sendOption3("General Supplies", "Talismans", "Nevermind");
			c.dialogueAction = 94;
			break;

		case 45:
			sendNpcChat2("Well, since you haven't shown me a defender to", "prove your prowess as a warrior.", 4289, "Kamfreena");
			c.nextChat = 46;
			break;
		case 46:
			sendNpcChat3("I'll release some Cyclopes which might drop bronze", "defenders for you to start off with, unless you show me", "another. Have fun in there.", 4289, "Kamfreena");
			break;
		case 47:
			sendNpcChat2("You have a defender, so I'll release some cyclopes", "which may drop " + Mistex.getWarriorsGuild().getCyclopsDrop126(c) + " defenders.", 4289, "Kamfreena");
			break;

		case 134:
			sendStatement("You found a hidden tunnel! Do you want to enter it?");
			c.dialogueAction = 104;
			c.nextChat = 135;
			break;
		case 135:
			sendOption2("Yea! I'm fearless!", "No way! That looks scary!");
			c.dialogueAction = 135;
			c.nextChat = 0;
			break;

		case 77:
			sendNpcChat4("" + MistexUtility.capitalize(c.playerName) + " you have Failed.", "You did participate enough to take down", "the portals. ", "Try Harder next time.", c.talkingNpc, "Void Knight");
			break;
		case 78:
			sendNpcChat4("All is Lost!", "You could not take down the portals in time.", " ", "Try Harder next time.", c.talkingNpc, "Void Knight");
			break;
		case 79:
			sendNpcChat4("Congratulations " + MistexUtility.capitalize(c.playerName) + "!", "You took down all the portals whilst keeping", "the void knight alive.", "You been awarded, well done.", c.talkingNpc, "Void Knight");
			break;

		case 12:
			sendNpcChat4("Hello!", "I am a master of the slayer skill.", "I can offer you a number of services.", "So how can I help you?", c.talkingNpc, "Vannaka");
			c.nextChat = 11;
			break;

		case 11:
			sendOption4("I wan't to cancel my current task.", "I need another assignment.", "Can I view your shop?", "Duo slayer!");
			c.dialogueAction = 12;
			break;

		case 127:
			c.nextChat = 128;
			sendNpcChat2("Duo slayer?", "Sure no problem!", c.talkingNpc, "Vannaka");
			break;
		case 128:
			c.dialogueAction = 8203;
			sendOption3("Assign us a duo slayer task, please!", "I want to cancel our slayer task.", "Nevermind.");
			break;

		case 13:
			sendNpcChat4("Hello!", "I am a master of the slayer skill.", "I see I have already assigned you a task to complete.", "You must finish the task before getting a new one!", c.talkingNpc, "Vannaka");
			break;
		case 14:
			sendOption2("Yes I would like an easier task.", "No I would like to keep my task.");
			c.dialogueAction = 6;
			break;
		case 15:

		case 0:
			c.getPA().closeAllWindows();
			break;

		/* PvP Dialogue */
		case 1:
			sendNpcChat1("Hello " + MistexUtility.capitalize(MistexUtility.capitalize(c.playerName)) + "!", 4297, "Sloane");
			c.nextChat = 2;
			break;

		case 2:
			sendOption4("I want to see your shops.", "I want to see my PvP statistics.", "I want to reset my PvP statistics.", "Nevermind");
			c.dialogueAction = 2;
			break;
		case 3:
			sendStatement("Kills: @red@" + c.KC + "@bla@ | Deaths: @red@" + c.DC + "@bla@ | PvP Points: @red@" + c.pkPoints + "");
			c.nextChat = 0;
			break;

		case 4:
			sendStatement("Reseting your KDR will cost 100 PvP Points");
			c.nextChat = 5;
			break;

		case 5:
			sendOption2("Reset KDR", "Nevermind");
			c.dialogueAction = 5;
			break;

		case 17:
			sendOption3("Shop 1", "Shop 2", "Shop 3");
			c.dialogueAction = 17;
			break;

		/* Vote Dialogue */
		case 6:
			sendOption2("I want to see your shop.", "Nevermind");
			c.dialogueAction = 4;
			break;

		/* Profile Dialogue */
		case 50:
			sendOption5("Blue", "Red", "Yellow", "Green", "Next");
			c.dialogueAction = 50;
			break;
		case 51:
			sendOption5("Orange", "White", "Cyan", "Purple", "Previous");
			c.dialogueAction = 51;
			break;

		/* Teleporting Dialogue */
		case 100:
			sendOption5("PvP Teleports", "Boss Teleports", "Minigame Teleports", "", "Nevermind");
			c.dialogueAction = 100;
			break;

		case 101:
			sendOption5("Edgeville", "Varrock", "Camelot", "Falador", "Nevermind");
			c.dialogueAction = 101;
			break;

		case 105:
			sendOption5("PvP Zones", "Easts", "Wests", "Mage Bank", "Nevermind");
			c.dialogueAction = 105;
			break;

		case 115:
			sendOption5("Coming soon", "Coming soon", "Coming soon", "Coming soon", "Nevermind");
			c.dialogueAction = 115;
			break;

		case 125:
			sendOption5("Duel Arena", "Barrows", "Pest Control", "Coming soon", "Nevermind");
			c.dialogueAction = 125;
			break;

		/* Healing Box Dialogue */
		case 200:
			sendOption4("Restore Prayer", "Restore Special", "Switch Magic Book", "Nevermind");
			c.dialogueAction = 200;
			break;

		case 201:
			sendOption4("Regular Prayer Book", "Ancient Prayer Book", "Lunar Prayer Book", "Nevermind");
			c.dialogueAction = 201;
			break;

		case 300:
			sendOption2("Play for 50 PvP Points", "No");
			c.dialogueAction = 300;
			break;

		/* Starter Dialogue */
		case 150:
			sendStatement("Welcome to Mistex, " + MistexUtility.capitalize(MistexUtility.capitalize(c.playerName)) + "!");
			c.nextChat = 151;
			break;
		case 151:
			sendStatement("Would you like a bank starter?");
			c.nextChat = 152;
			break;
		case 152:
			sendOption2("Yes", "No");
			c.dialogueAction = 152;
			break;

		/* Donation Dialogue */
		case 5000:
			sendOption4("Donation Shop 1", "Donation Shop 2", "Donation Shop 3", "Where do I donate?");
			c.dialogueAction = 5000;
			break;

		/* Tutorial Dialogues */
		case 250:
			sendNpcChat1("Welcome " + MistexUtility.capitalize(c.playerName) + "!", 945, "Mistex Guide");
			c.getPA().clearIcons();
			c.nextChat = 251;
			break;
		case 251:
			sendNpcChat1("Before you start your adventure would like a tutorial?", 945, "Mistex Guide");
			c.nextChat = 252;
			break;
		case 252:
			sendOption2("Yes please.", "No thank you.");
			c.dialogueAction = 252;
			break;
		case 253:
			sendNpcChat1("Well have have fun adventurer!", 945, "Mistex Guide");
			c.nextChat = 0;
			c.getPA().movePlayer(PlayerConfiguration.RESPAWN_X, PlayerConfiguration.RESPAWN_Y, 0);
			StarterPack.determineStarter(c);
			c.getPA().resetIcons();
			Mistex.clan.joinClan(c);
			break;
		case 254:
			sendNpcChat1("Alright then! Let's get started.", 945, "Mistex Guide");
			c.isDoingTutorial = true;
			c.nextChat = 255;
			break;
		case 255:
			sendNpcChat1("This is your home. The majority of your time played will be here.", 945, "Mistex Guide");
			c.getPA().movePlayer(3087, 3499, 0);
			c.nextChat = 256;
			break;
		case 256:
			sendNpcChat1("These are the shops. You can purchase items you need here.", 945, "Mistex Guide");
			c.getPA().movePlayer(3095, 3505, 0);
			c.nextChat = 257;
			break;
		case 257:
			sendNpcChat1("This is the home thieving area. You can make some easy money here.", 945, "Mistex Guide");
			c.getPA().movePlayer(3100, 3500, 0);
			c.nextChat = 258;
			break;
		case 258:
			sendNpcChat1("There are some of the most important NPCs in Mistex.", 945, "Mistex Guide");
			c.getPA().movePlayer(3080, 3504, 0);
			c.nextChat = 259;
			break;
		case 259:
			sendNpcChat1("This is Vannaka, he will assign you slayer tasks.", 945, "Mistex Guide");
			c.getPA().movePlayer(3093, 3479, 0);
			c.nextChat = 260;
			break;
		case 260:
			sendNpcChat1("Some other features you should know are that:", 945, "Mistex Guide");
			c.getPA().movePlayer(PlayerConfiguration.TUTORIAL_X, PlayerConfiguration.TUTORIAL_Y, 0);
			c.nextChat = 261;
			break;
		case 261:
			sendNpcChat1("The wrench icon is used to toggle certain gameplay options.", 945, "Mistex Guide");
			c.nextChat = 262;
			c.setSidebarInterface(16, 45500); // Settings
			break;
		case 262:
			sendNpcChat1("The note icon is used to view other player's profiles.", 945, "Mistex Guide");
			c.setSidebarInterface(13, 29000); // Player Profiler
			c.nextChat = 263;
			break;
		case 263:
			sendNpcChat1("The world map orb will open up the teleporting interface.", 945, "Mistex Guide");
			c.nextChat = 264;
			break;
		case 264:
			sendNpcChat1("And that's it! Good luck!", 945, "Mistex Guide");
			c.nextChat = 0;
			c.getPA().resetIcons();
			c.isDoingTutorial = false;
			c.getPA().movePlayer(PlayerConfiguration.RESPAWN_X, PlayerConfiguration.RESPAWN_Y, 0);
			StarterPack.determineStarter(c);
			break;

		case 5002:
			if (PvPArtifacts.hasArtifact(c)) {
				sendNpcChat3("Oh glorious warrior!", "I see you have found some ancient artifacts!", "Are you willing to sell them to me?", 1288, "Peer the Seer");
				c.nextChat = 5003;
			} else {
				sendNpcChat3("Hello, my name is Peer, I'm a collector.", "If you find any ancient artifacts,", "I am willing to buy them off you for a fine price!", 1288, "Peer the Seer");
				c.nextChat = 0;
			}
			break;

		case 5003:
			sendOption2("Yes.", "No, thanks.");
			c.dialogueAction = 5003;
			break;

		/**
		 * Prestige Dialogue 2581 for her id
		 */
		case 2000:
			sendNpcChat2("Hello young adventurer!", "My name is Ellamaria, how can I help you?", 2581, "Ellamaria");
			c.nextChat = 2001;
			break;
		case 2001:
			sendPlayerChat1("You tell me, how can YOU help me? *wink*");
			c.nextChat = 2002;
			break;
		case 2002:
			sendNpcChat4("Oh my..", "I don't offer those kinds of help...", "However, if you would like I may prestige", "your skills.", 2581, "Ellamaria");
			c.nextChat = 2003;
			break;
		case 2003:
			sendOption4("Tell me more about prestiges", "Open prestige panel", "Open prestige shop", "Nevermind");
			c.dialogueAction = 2003;
			break;

		case 2004:
			sendNpcChat3("When you have reached a level of 99 in any skill,", "you will have unlocked the ability to prestige it.", "By doing so your skill will be reset back to 1, along with all the xp.", 2581, "Ellamaria");
			c.nextChat = 2005;
			break;
		case 2005:
			sendNpcChat1("You may only prestige each skill a total of 5 times.", 2581, "Ellamaria");
			c.nextChat = 2006;
			break;
		case 2006:
			sendNpcChat3("However, it does come with it's rewards!", "You will be given a coin reward, your skill being a different color,", "you will recieve new titles, and will recieve prestige points.", 2581, "Ellamaria");
			c.nextChat = 2003;
			break;

		}
	}

	public void sendFarmingOptions(String s, String s1, String s2, String s3) {
		c.getPA().sendFrame126("Farming Locations", 2481);
		c.getPA().sendFrame126(s, 2482);
		c.getPA().sendFrame126(s1, 2483);
		c.getPA().sendFrame126(s2, 2484);
		c.getPA().sendFrame126(s3, 2485);
		c.getPA().sendFrame164(2480);
	}

	/*
	 * Information Box
	 */

	public void sendStartInfo(String text, String text1, String text2, String text3, String title) {
		c.getPA().sendFrame126(title, 6180);
		c.getPA().sendFrame126(text, 6181);
		c.getPA().sendFrame126(text1, 6182);
		c.getPA().sendFrame126(text2, 6183);
		c.getPA().sendFrame126(text3, 6184);
		c.getPA().sendFrame164(6179);
	}

	/*
	 * Options
	 */

	private void sendOption(String s, String s1) {
		c.getPA().sendFrame126("Select an Option", 2470);
		c.getPA().sendFrame126(s, 2471);
		c.getPA().sendFrame126(s1, 2472);
		c.getPA().sendFrame126("Click here to continue", 2473);
		c.getPA().sendFrame164(13758);
	}

	public void sendOption2(String s, String s1) {
		c.getPA().sendFrame126("Select an Option", 2460);
		c.getPA().sendFrame126(s, 2461);
		c.getPA().sendFrame126(s1, 2462);
		c.getPA().sendFrame164(2459);
	}

	public void sendBanOption(String s, String s1, String s2, String s3, String s4) {
		c.getPA().sendFrame126("Choose a punishment", 2493);
		c.getPA().sendFrame126(s, 2494);
		c.getPA().sendFrame126(s1, 2495);
		c.getPA().sendFrame126(s2, 2496);
		c.getPA().sendFrame126(s3, 2497);
		c.getPA().sendFrame126(s4, 2498);
		c.getPA().sendFrame164(2492);
	}

	public void sendOption3(String s, String s1, String s2) {
		c.getPA().sendFrame126("Select an Option", 2470);
		c.getPA().sendFrame126(s, 2471);
		c.getPA().sendFrame126(s1, 2472);
		c.getPA().sendFrame126(s2, 2473);
		c.getPA().sendFrame164(2469);
	}

	public void sendOption4(String s, String s1, String s2, String s3) {
		c.getPA().sendFrame126("Select an Option", 2481);
		c.getPA().sendFrame126(s, 2482);
		c.getPA().sendFrame126(s1, 2483);
		c.getPA().sendFrame126(s2, 2484);
		c.getPA().sendFrame126(s3, 2485);
		c.getPA().sendFrame164(2480);
	}

	public void sendOption5(String s, String s1, String s2, String s3, String s4) {
		c.getPA().sendFrame126("Select an Option", 2493);
		c.getPA().sendFrame126(s, 2494);
		c.getPA().sendFrame126(s1, 2495);
		c.getPA().sendFrame126(s2, 2496);
		c.getPA().sendFrame126(s3, 2497);
		c.getPA().sendFrame126(s4, 2498);
		c.getPA().sendFrame164(2492);
	}

	/*
	 * Statements
	 */

	public void sendStatement(String s) { // 1 line click here to continue chat
											// box interface
		c.getPA().sendFrame126(s, 357);
		c.getPA().sendFrame126("Click here to continue", 358);
		c.getPA().sendFrame164(356);
	}

	/*
	 * Npc Chatting
	 */

	private void sendNpcChat1(String s, int ChatNpc, String name) {
		c.getPA().sendFrame200(4883, 591);
		c.getPA().sendFrame126(name, 4884);
		c.getPA().sendFrame126(s, 4885);
		c.getPA().sendFrame75(ChatNpc, 4883);
		c.getPA().sendFrame164(4882);
	}

	public void sendNpcChat2(String s, String s1, int ChatNpc, String name) {
		c.getPA().sendFrame200(4888, 9847);
		c.getPA().sendFrame126(name, 4889);
		c.getPA().sendFrame126(s, 4890);
		c.getPA().sendFrame126(s1, 4891);
		c.getPA().sendFrame75(ChatNpc, 4888);
		c.getPA().sendFrame164(4887);
	}

	public void sendNpcChat3(String s, String s1, String s2, int ChatNpc, String name) {
		c.getPA().sendFrame200(4894, 9847); // Was 591
		c.getPA().sendFrame126(name, 4895);
		c.getPA().sendFrame126(s, 4896);
		c.getPA().sendFrame126(s1, 4897);
		c.getPA().sendFrame126(s2, 4898);
		c.getPA().sendFrame75(ChatNpc, 4894);
		c.getPA().sendFrame164(4893);
	}

	private void sendNpcChat4(String s, String s1, String s2, String s3, int ChatNpc, String name) {
		c.getPA().sendFrame200(4901, 9847);
		c.getPA().sendFrame126(name, 4902);
		c.getPA().sendFrame126(s, 4903);
		c.getPA().sendFrame126(s1, 4904);
		c.getPA().sendFrame126(s2, 4905);
		c.getPA().sendFrame126(s3, 4906);
		c.getPA().sendFrame75(ChatNpc, 4901);
		c.getPA().sendFrame164(4900);
	}

	/*
	 * Player Chating Back
	 */

	private void sendPlayerChat1(String s) {
		c.getPA().sendFrame200(969, 591);
		c.getPA().sendFrame126(c.playerName, 970);
		c.getPA().sendFrame126(s, 971);
		c.getPA().sendFrame185(969);
		c.getPA().sendFrame164(968);
	}

	private void sendPlayerChat2(String s, String s1) {
		c.getPA().sendFrame200(974, 591);
		c.getPA().sendFrame126(c.playerName, 975);
		c.getPA().sendFrame126(s, 976);
		c.getPA().sendFrame126(s1, 977);
		c.getPA().sendFrame185(974);
		c.getPA().sendFrame164(973);
	}

	private void sendPlayerChat3(String s, String s1, String s2) {
		c.getPA().sendFrame200(980, 591);
		c.getPA().sendFrame126(c.playerName, 981);
		c.getPA().sendFrame126(s, 982);
		c.getPA().sendFrame126(s1, 983);
		c.getPA().sendFrame126(s2, 984);
		c.getPA().sendFrame185(980);
		c.getPA().sendFrame164(979);
	}

	private void sendPlayerChat4(String s, String s1, String s2, String s3) {
		c.getPA().sendFrame200(987, 591);
		c.getPA().sendFrame126(c.playerName, 988);
		c.getPA().sendFrame126(s, 989);
		c.getPA().sendFrame126(s1, 990);
		c.getPA().sendFrame126(s2, 991);
		c.getPA().sendFrame126(s3, 992);
		c.getPA().sendFrame185(987);
		c.getPA().sendFrame164(986);
	}

	public void itemMessage(String title, String message, int itemid, int size) {
		c.getPA().sendFrame200(4883, 591);
		c.getPA().sendFrame126(title, 4884);
		c.getPA().sendFrame126(message, 4885);
		c.getPA().sendFrame126("Click here to continue.", 4886);
		c.getPA().sendFrame246(4883, size, itemid);
		c.getPA().sendFrame164(4882);
	}

	/** Item Dialogues **/

	public void OneItemDialogue(String text, int item) {
		c.getPA().sendFrame126(text, 308);
		c.getPA().sendFrame246(307, 200, item);
		c.getPA().sendFrame164(306);
	}

	public void TwoItemDialogue(String text1, String text2, int item1, int item2) {
		c.getPA().sendFrame126(text1, 6232);
		c.getPA().sendFrame126(text2, 6233);
		c.getPA().sendFrame246(6235, 170, item1);
		c.getPA().sendFrame246(6236, 170, item2);
		c.getPA().sendFrame164(6231);
	}

	public void sendStatement(String[] lines) {
		switch (lines.length) {
		case 1:
			sendStatement(lines[0]);
			break;
		case 2:
			sendStatement(lines[0], lines[1]);
			break;
		case 3:
			sendStatement(lines[0], lines[1], lines[2]);
			break;
		case 4:
			sendStatement(lines[0], lines[1], lines[2], lines[3]);
			break;
		case 5:
			sendStatement(lines[0], lines[1], lines[2], lines[3], lines[4]);
			break;
		}
	}

	public void sendStatement(String line1, String line2) {
		c.getPA().sendFrame126(line1, 360);
		c.getPA().sendFrame126(line2, 361);
		c.getPA().sendFrame164(359);
	}

	public void sendStatement(String line1, String line2, String line3) {
		c.getPA().sendFrame126(line1, 364);
		c.getPA().sendFrame126(line2, 365);
		c.getPA().sendFrame126(line3, 366);
		c.getPA().sendFrame164(363);
	}

	public void sendStatement(String line1, String line2, String line3, String line4) {
		c.getPA().sendFrame126(line1, 369);
		c.getPA().sendFrame126(line2, 370);
		c.getPA().sendFrame126(line3, 371);
		c.getPA().sendFrame126(line4, 372);
		c.getPA().sendFrame164(368);
	}

	public void sendStatement(String line1, String line2, String line3, String line4, String line5) {
		c.getPA().sendFrame126(line1, 375);
		c.getPA().sendFrame126(line2, 376);
		c.getPA().sendFrame126(line3, 377);
		c.getPA().sendFrame126(line4, 378);
		c.getPA().sendFrame126(line5, 379);
		c.getPA().sendFrame164(374);
	}

	public void sendSpecialOption2(String s, String s1, String title) {
		c.getPA().sendFrame126(title, 2460);
		c.getPA().sendFrame126(s, 2461);
		c.getPA().sendFrame126(s1, 2462);
		c.getPA().sendFrame164(2459);
	}

}