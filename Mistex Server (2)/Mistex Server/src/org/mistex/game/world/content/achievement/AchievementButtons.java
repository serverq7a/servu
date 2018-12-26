package org.mistex.game.world.content.achievement;

import org.mistex.game.world.content.Miscellaneous;
import org.mistex.game.world.content.interfaces.InterfaceText;
import org.mistex.game.world.content.interfaces.impl.QuestInterface;
import org.mistex.game.world.player.Client;


public class AchievementButtons {

    public static void handleClickButton(Client c, int buttonId) {
        switch (buttonId) {
        
        case 31219:
        	Miscellaneous.givePlayerTitle(c);
        	break;
        
        case 121031: //Mystic
        	InterfaceText.writeText(new QuestInterface(c));
        	c.viewingAchievement = 100;
            c.getPA().showInterface(8134);
            c.getPA().sendFrame126("Mystic", 8144);
            c.getPA().sendFrame126("@or1@How to complete:", 8145);
            if (c.achievementsCompleted == c.totalAchievements) {
                c.getPA().sendFrame126("@blu@<str>Get a Completionist cape", 8146);
            } else {
                c.getPA().sendFrame126("@blu@Get a Completionist cape", 8146);
            }
            c.getPA().sendFrame126("", 8147);
            c.getPA().sendFrame126("@or1@Reward:", 8148);
            c.getPA().sendFrame126("", 8149);
            c.getPA().sendFrame126("@blu@1,000,000 coins", 8150);
            c.getPA().sendFrame126("@blu@Title: Mystic", 8151);
            c.getPA().sendFrame126("", 8152);
            c.getPA().sendFrame126("@or1@Progress:", 8153);
            if (c.getPA().isMaxed()) {
                c.getPA().sendFrame126("@ceo@COMPLETED", 8154);
                c.getPA().sendFrame126("Change title", 8155);
            } else {
                c.getPA().sendFrame126("@red@NOT COMPLETED", 8154);
            }
            break;

        case 121032: //REPORT 1
        	InterfaceText.writeText(new QuestInterface(c));
        	c.viewingAchievement = 1;
            c.getPA().showInterface(8134);
            c.getPA().sendFrame126("Law Abiding Citizen", 8144);
            c.getPA().sendFrame126("@or1@How to complete:", 8145);
            if (c.playerReported) {
                c.getPA().sendFrame126("@blu@<str>Report a player once", 8146);
            } else {
                c.getPA().sendFrame126("@blu@Report a player once", 8146);
            }
            c.getPA().sendFrame126("", 8147);
            c.getPA().sendFrame126("@or1@Reward:", 8148);
            c.getPA().sendFrame126("", 8149);
            c.getPA().sendFrame126("@blu@100,000 coins", 8150);
            c.getPA().sendFrame126("@blu@Title: Law Men", 8151);
            c.getPA().sendFrame126("", 8152);
            c.getPA().sendFrame126("@or1@Progress:", 8153);
            if (c.playerReported) {
                c.getPA().sendFrame126("@ceo@COMPLETED", 8154);
                c.getPA().sendFrame126("Change title", 8155);
            } else {
                c.getPA().sendFrame126("@red@NOT STARTED", 8154);
            }
            break;

        case 121033: //MUNCHIES 2
        	InterfaceText.writeText(new QuestInterface(c));
        	c.viewingAchievement = 2;
            c.getPA().showInterface(8134);
            c.getPA().sendFrame126("Munchies", 8144);
            c.getPA().sendFrame126("@or1@How to complete:", 8145);
            if (c.foodEaten >= 1000) {
                c.getPA().sendFrame126("@blu@<str>Eat 1000 food", 8146);
            } else {
                c.getPA().sendFrame126("@blu@Eat 1000 food", 8146);
            }
            c.getPA().sendFrame126("", 8147);
            c.getPA().sendFrame126("@or1@Reward:", 8148);
            c.getPA().sendFrame126("", 8149);
            c.getPA().sendFrame126("@blu@100,000 coins", 8150);
            c.getPA().sendFrame126("@blu@Title: Munchy", 8151);
            c.getPA().sendFrame126("", 8152);
            c.getPA().sendFrame126("@or1@Progress:", 8153);
            if (c.foodEaten >= 1000) {
                c.getPA().sendFrame126("@ceo@COMPLETED", 8154);
                c.getPA().sendFrame126("Change title", 8155);
            } else if (c.foodEaten >= 1) {
                c.getPA().sendFrame126("@yel@" + c.foodEaten + "@or1@/1000", 8154);
            } else {
                c.getPA().sendFrame126("@red@NOT STARTED", 8154);
            }
            break;

        case 121034: //THIRST 3
        	InterfaceText.writeText(new QuestInterface(c));
        	c.viewingAchievement = 3;
            c.getPA().showInterface(8134);
            c.getPA().sendFrame126("Mr Thirsty", 8144);
            c.getPA().sendFrame126("@or1@How to complete:", 8145);
            if (c.potionsDrank >= 1000) {
                c.getPA().sendFrame126("@blu@<str>Drink 1000 potions", 8146);
            } else {
                c.getPA().sendFrame126("@blu@Drink 1000 potions", 8146);
            }
            c.getPA().sendFrame126("", 8147);
            c.getPA().sendFrame126("@or1@Reward:", 8148);
            c.getPA().sendFrame126("", 8149);
            c.getPA().sendFrame126("@blu@100,000 coins", 8150);
            c.getPA().sendFrame126("@blu@Title: Thirsty", 8151);
            c.getPA().sendFrame126("", 8152);
            c.getPA().sendFrame126("@or1@Progress:", 8153);
            if (c.potionsDrank >= 1000) {
                c.getPA().sendFrame126("@ceo@COMPLETED", 8154);
                c.getPA().sendFrame126("Change title", 8155);
            } else if (c.potionsDrank >= 1) {
                c.getPA().sendFrame126("@yel@" + c.potionsDrank + "@or1@/1000", 8154);
            } else {
                c.getPA().sendFrame126("@red@NOT STARTED", 8154);
            }
            break;

        case 121035: //BEAST 4
        	InterfaceText.writeText(new QuestInterface(c));
        	c.viewingAchievement = 4;
            c.getPA().showInterface(8134);
            c.getPA().sendFrame126("Beast", 8144);
            c.getPA().sendFrame126("@or1@How to complete:", 8145);
            if (c.KC >= 1000) {
                c.getPA().sendFrame126("@blu@<str>Kill 1000 players", 8146);
            } else {
                c.getPA().sendFrame126("@blu@Kill 1000 players", 8146);
            }
            c.getPA().sendFrame126("", 8147);
            c.getPA().sendFrame126("@or1@Reward:", 8148);
            c.getPA().sendFrame126("", 8149);
            c.getPA().sendFrame126("@blu@1,000,000 coins", 8150);
            c.getPA().sendFrame126("@blu@Title: Beast", 8151);
            c.getPA().sendFrame126("", 8152);
            c.getPA().sendFrame126("@or1@Progress:", 8153);
            if (c.KC >= 1000) {
                c.getPA().sendFrame126("@ceo@COMPLETED", 8154);
                c.getPA().sendFrame126("Change title", 8155);
            } else if (c.KC >= 1) {
                c.getPA().sendFrame126("@yel@" + c.KC + "@or1@/1000", 8154);
            } else {
                c.getPA().sendFrame126("@red@NOT STARTED", 8154);
            }
            break;

        case 121036: //WEAKLING 5
        	InterfaceText.writeText(new QuestInterface(c));
        	c.viewingAchievement = 5;
            c.getPA().showInterface(8134);
            c.getPA().sendFrame126("Weakling", 8144);
            c.getPA().sendFrame126("@or1@How to complete:", 8145);
            if (c.DC >= 100) {
                c.getPA().sendFrame126("@blu@<str>Die 100 times", 8146);
            } else {
                c.getPA().sendFrame126("@blu@Die 100 times", 8146);
            }
            c.getPA().sendFrame126("", 8147);
            c.getPA().sendFrame126("@or1@Reward:", 8148);
            c.getPA().sendFrame126("", 8149);
            c.getPA().sendFrame126("@blu@100,000 coins", 8150);
            c.getPA().sendFrame126("@blu@Title: Weak", 8151);
            c.getPA().sendFrame126("", 8152);
            c.getPA().sendFrame126("@or1@Progress:", 8153);
            if (c.DC >= 100) {
                c.getPA().sendFrame126("@ceo@COMPLETED", 8154);
                c.getPA().sendFrame126("Change title", 8155);
            } else if (c.DC >= 1) {
                c.getPA().sendFrame126("@yel@" + c.DC + "@or1@/100", 8154);
            } else {
                c.getPA().sendFrame126("@red@NOT STARTED", 8154);
            }
            break;

        case 121037: //The Gamer 6
        	InterfaceText.writeText(new QuestInterface(c));
        	c.viewingAchievement = 6;
            c.getPA().showInterface(8134);
            c.getPA().sendFrame126("The Gamer", 8144);
            c.getPA().sendFrame126("@or1@How to complete:", 8145);
            if (c.pkStatuePlays >= 100) {
                c.getPA().sendFrame126("@blu@<str>Play the Pk Statue minigame 100 times", 8146);
            } else {
                c.getPA().sendFrame126("@blu@Play the Pk Statue minigame 100 times", 8146);
            }
            c.getPA().sendFrame126("", 8147);
            c.getPA().sendFrame126("@or1@Reward:", 8148);
            c.getPA().sendFrame126("", 8149);
            c.getPA().sendFrame126("@blu@1,000,000 coins", 8150);
            c.getPA().sendFrame126("@blu@Title: Gamer", 8151);
            c.getPA().sendFrame126("", 8152);
            c.getPA().sendFrame126("@or1@Progress:", 8153);
            if (c.pkStatuePlays >= 100) {
                c.getPA().sendFrame126("@ceo@COMPLETED", 8154);
                c.getPA().sendFrame126("Change title", 8155);
            } else if (c.pkStatuePlays >= 1) {
                c.getPA().sendFrame126("@yel@" + c.pkStatuePlays + "@or1@/100", 8154);
            } else {
                c.getPA().sendFrame126("@red@NOT STARTED", 8154);
            }
            break;

        case 121038: //DUELIST 7
        	InterfaceText.writeText(new QuestInterface(c));
        	c.viewingAchievement = 7;
            c.getPA().showInterface(8134);
            c.getPA().sendFrame126("The Duelist", 8144);
            c.getPA().sendFrame126("@or1@How to complete:", 8145);
            if (c.duelsWon >= 100) {
                c.getPA().sendFrame126("@blu@<str>Win 100 duels", 8146);
            } else {
                c.getPA().sendFrame126("@blu@Win 100 duels", 8146);
            }
            c.getPA().sendFrame126("", 8147);
            c.getPA().sendFrame126("@or1@Reward:", 8148);
            c.getPA().sendFrame126("", 8149);
            c.getPA().sendFrame126("@blu@500,000 coins", 8150);
            c.getPA().sendFrame126("@blu@Title: Duelist", 8151);
            c.getPA().sendFrame126("", 8152);
            c.getPA().sendFrame126("@or1@Progress:", 8153);
            if (c.duelsWon >= 100) {
                c.getPA().sendFrame126("@ceo@COMPLETED", 8154);
                c.getPA().sendFrame126("Change title", 8155);
            } else if (c.duelsWon >= 1) {
                c.getPA().sendFrame126("@yel@" + c.duelsWon + "@or1@/100", 8154);
            } else {
                c.getPA().sendFrame126("@red@NOT STARTED", 8154);
            }
            break;

        case 121039: //HOME SICK 8
        	InterfaceText.writeText(new QuestInterface(c));
        	c.viewingAchievement = 8;
            c.getPA().showInterface(8134);
            c.getPA().sendFrame126("Home Sick", 8144);
            c.getPA().sendFrame126("@or1@How to complete:", 8145);
            if (c.teleportedHome >= 500) {
                c.getPA().sendFrame126("@blu@<str>Teleport Home 500 Times", 8146);
            } else {
                c.getPA().sendFrame126("@blu@Teleport Home 500 Times", 8146);
            }
            c.getPA().sendFrame126("", 8147);
            c.getPA().sendFrame126("@or1@Reward:", 8148);
            c.getPA().sendFrame126("", 8149);
            c.getPA().sendFrame126("@blu@500,000 coins", 8150);
            c.getPA().sendFrame126("@blu@Title: Home", 8151);
            c.getPA().sendFrame126("", 8152);
            c.getPA().sendFrame126("@or1@Progress:", 8153);
            if (c.teleportedHome >= 500) {
                c.getPA().sendFrame126("@ceo@COMPLETED", 8154);
                c.getPA().sendFrame126("Change title", 8155);
            } else if (c.teleportedHome >= 1) {
                c.getPA().sendFrame126("@yel@" + c.teleportedHome + "@or1@/500", 8154);
            } else {
                c.getPA().sendFrame126("@red@NOT STARTED", 8154);
            }
            break;

        case 121040: //THE VIEWER 9
        	InterfaceText.writeText(new QuestInterface(c));
        	c.viewingAchievement = 9;
            c.getPA().showInterface(8134);
            c.getPA().sendFrame126("The Viewer", 8144);
            c.getPA().sendFrame126("@or1@How to complete:", 8145);
            if (c.profileViews >= 250) {
                c.getPA().sendFrame126("@blu@<str>View 250 Profiles", 8146);
            } else {
                c.getPA().sendFrame126("@blu@View 250 Profiles", 8146);
            }
            c.getPA().sendFrame126("", 8147);
            c.getPA().sendFrame126("@or1@Reward:", 8148);
            c.getPA().sendFrame126("", 8149);
            c.getPA().sendFrame126("@blu@500,000 coins", 8150);
            c.getPA().sendFrame126("@blu@Title: Viewer", 8151);
            c.getPA().sendFrame126("", 8152);
            c.getPA().sendFrame126("@or1@Progress:", 8153);
            if (c.profileViews >= 250) {
                c.getPA().sendFrame126("@ceo@COMPLETED", 8154);
                c.getPA().sendFrame126("Change title", 8155);
            } else if (c.profileViews >= 1) {
                c.getPA().sendFrame126("@yel@" + c.profileViews + "@or1@/250", 8154);
            } else {
                c.getPA().sendFrame126("@red@NOT STARTED", 8154);
            }
            break;

        case 121041: //THE TRADER 10
        	InterfaceText.writeText(new QuestInterface(c));
        	c.viewingAchievement = 10;
            c.getPA().showInterface(8134);
            c.getPA().sendFrame126("The Trader", 8144);
            c.getPA().sendFrame126("@or1@How to complete:", 8145);
            if (c.tradesCompleted >= 500) {
                c.getPA().sendFrame126("@blu@<str>Trade 500 players", 8146);
            } else {
                c.getPA().sendFrame126("@blu@Trade 500 players", 8146);
            }
            c.getPA().sendFrame126("", 8147);
            c.getPA().sendFrame126("@or1@Reward:", 8148);
            c.getPA().sendFrame126("", 8149);
            c.getPA().sendFrame126("@blu@500,000 coins", 8150);
            c.getPA().sendFrame126("@blu@Title: Trader", 8151);
            c.getPA().sendFrame126("", 8152);
            c.getPA().sendFrame126("@or1@Progress:", 8153);
            if (c.tradesCompleted >= 500) {
                c.getPA().sendFrame126("@ceo@COMPLETED", 8154);
                c.getPA().sendFrame126("Change title", 8155);
            } else if (c.tradesCompleted >= 1) {
                c.getPA().sendFrame126("@yel@" + c.tradesCompleted + "@or1@/500", 8154);
            } else {
                c.getPA().sendFrame126("@red@NOT STARTED", 8154);
            }
            break;

        case 121042: //SELF-CONSCIOUS 11
        	InterfaceText.writeText(new QuestInterface(c));
        	c.viewingAchievement = 11;
            c.getPA().showInterface(8134);
            c.getPA().sendFrame126("Self-Conscious", 8144);
            c.getPA().sendFrame126("@or1@How to complete:", 8145);
            if (c.appearancesChanged >= 100) {
                c.getPA().sendFrame126("@blu@<str>Change your appearance 100 times", 8146);
            } else {
                c.getPA().sendFrame126("@blu@Change your appearance 100 times", 8146);
            }
            c.getPA().sendFrame126("", 8147);
            c.getPA().sendFrame126("@or1@Reward:", 8148);
            c.getPA().sendFrame126("", 8149);
            c.getPA().sendFrame126("@blu@100,000 coins", 8150);
            c.getPA().sendFrame126("@blu@Title: Selfie", 8151);
            c.getPA().sendFrame126("", 8152);
            c.getPA().sendFrame126("@or1@Progress:", 8153);
            if (c.appearancesChanged >= 100) {
                c.getPA().sendFrame126("@ceo@COMPLETED", 8154);
                c.getPA().sendFrame126("Change title", 8155);
            } else if (c.appearancesChanged >= 1) {
                c.getPA().sendFrame126("@yel@" + c.appearancesChanged + "@or1@/100", 8154);
            } else {
                c.getPA().sendFrame126("@red@NOT STARTED", 8154);
            }
            break;

        case 121043: //CORPOREAL HUNTER 12
        	InterfaceText.writeText(new QuestInterface(c));
        	c.viewingAchievement = 12;
            c.getPA().showInterface(8134);
            c.getPA().sendFrame126("Corporeal Hunter", 8144);
            c.getPA().sendFrame126("@or1@How to complete:", 8145);
            if (c.corpKills >= 100) {
                c.getPA().sendFrame126("@blu@<str>Kill corporeal beast 100 times", 8146);
            } else {
                c.getPA().sendFrame126("@blu@Kill corporeal beast 100 times", 8146);
            }
            c.getPA().sendFrame126("", 8147);
            c.getPA().sendFrame126("@or1@Reward:", 8148);
            c.getPA().sendFrame126("", 8149);
            c.getPA().sendFrame126("@blu@1,000,000 coins", 8150);
            c.getPA().sendFrame126("@blu@Title: Corp", 8151);
            c.getPA().sendFrame126("", 8152);
            c.getPA().sendFrame126("@or1@Progress:", 8153);
            if (c.corpKills >= 100) {
                c.getPA().sendFrame126("@ceo@COMPLETED", 8154);
                c.getPA().sendFrame126("Change title", 8155);
            } else if (c.corpKills >= 1) {
                c.getPA().sendFrame126("@yel@" + c.corpKills + "@or1@/100", 8154);
            } else {
                c.getPA().sendFrame126("@red@NOT STARTED", 8154);
            }
            break;

        case 121044: //OVER 900 13
        	InterfaceText.writeText(new QuestInterface(c));
        	c.viewingAchievement = 13;
            c.getPA().showInterface(8134);
            c.getPA().sendFrame126("Over 900", 8144);
            c.getPA().sendFrame126("@or1@How to complete:", 8145);
            if (c.hit900) {
                c.getPA().sendFrame126("@blu@<str>Hit a 900 in PvP", 8146);
            } else {
                c.getPA().sendFrame126("@blu@Hit a 900 in PvP", 8146);
            }
            c.getPA().sendFrame126("", 8147);
            c.getPA().sendFrame126("@or1@Reward:", 8148);
            c.getPA().sendFrame126("", 8149);
            c.getPA().sendFrame126("@blu@1,000,000 coins", 8150);
            c.getPA().sendFrame126("@blu@Title: 900", 8151);
            c.getPA().sendFrame126("", 8152);
            c.getPA().sendFrame126("@or1@Progress:", 8153);
            if (c.hit900) {
                c.getPA().sendFrame126("@ceo@COMPLETED", 8154);
                c.getPA().sendFrame126("Change title", 8155);
            } else {
                c.getPA().sendFrame126("@red@NOT STARTED", 8154);
            }
            break;

        case 121045: //BRANIAC 14
        	InterfaceText.writeText(new QuestInterface(c));
        	c.viewingAchievement = 14;
            c.getPA().showInterface(8134);
            c.getPA().sendFrame126("Brainiac", 8144);
            c.getPA().sendFrame126("@or1@How to complete:", 8145);
            if (c.triviaWon >= 250) {
                c.getPA().sendFrame126("@blu@<str>Win The Trivia Bot 100 Times", 8146);
            } else {
                c.getPA().sendFrame126("@blu@Win The Trivia Bot 100 Times", 8146);
            }
            c.getPA().sendFrame126("", 8147);
            c.getPA().sendFrame126("@or1@Reward:", 8148);
            c.getPA().sendFrame126("", 8149);
            c.getPA().sendFrame126("@blu@1,000,000 coins", 8150);
            c.getPA().sendFrame126("@blu@Title: Brainiac", 8151);
            c.getPA().sendFrame126("", 8152);
            c.getPA().sendFrame126("@or1@Progress:", 8153);
            if (c.triviaWon >= 250) {
                c.getPA().sendFrame126("@ceo@COMPLETED", 8154);
                c.getPA().sendFrame126("Change title", 8155);
            } else if (c.triviaWon >= 1) {
                c.getPA().sendFrame126("@yel@" + c.triviaWon + "@or1@/100", 8154);
            } else {
                c.getPA().sendFrame126("@red@NOT STARTED", 8154);
            }
            break;

        case 121046: //HOLY MONK 15
        	InterfaceText.writeText(new QuestInterface(c));
        	c.viewingAchievement = 15;
            c.getPA().showInterface(8134);
            c.getPA().sendFrame126("Holy Monk", 8144);
            c.getPA().sendFrame126("@or1@How to complete:", 8145);
            if (c.altarPrayed >= 1000) {
                c.getPA().sendFrame126("@blu@<str>Pray at the altar 1,000 times", 8146);
            } else {
                c.getPA().sendFrame126("@blu@Pray at the altar 1,000 times", 8146);
            }
            c.getPA().sendFrame126("", 8147);
            c.getPA().sendFrame126("@or1@Reward:", 8148);
            c.getPA().sendFrame126("", 8149);
            c.getPA().sendFrame126("@blu@1,000,000 coins", 8150);
            c.getPA().sendFrame126("@blu@Title: Holy", 8151);
            c.getPA().sendFrame126("", 8152);
            c.getPA().sendFrame126("@or1@Progress:", 8153);
            if (c.altarPrayed >= 1000) {
                c.getPA().sendFrame126("@ceo@COMPLETED", 8154);
                c.getPA().sendFrame126("Change title", 8155);
            } else if (c.altarPrayed >= 1) {
                c.getPA().sendFrame126("@yel@" + c.altarPrayed + "@or1@/1000", 8154);
            } else {
                c.getPA().sendFrame126("@red@NOT STARTED", 8154);
            }
            break;

        case 121047: //MASTER BAITER 16
        	InterfaceText.writeText(new QuestInterface(c));
        	c.viewingAchievement = 16;
            c.getPA().showInterface(8134);
            c.getPA().sendFrame126("Master Baiter", 8144);
            c.getPA().sendFrame126("@or1@How to complete:", 8145);
            if (c.getLevelForXP(c.playerXP[10]) >= 99) {
                c.getPA().sendFrame126("@blu@<str>Achieve 99 fishing", 8146);
            } else {
                c.getPA().sendFrame126("@blu@Achieve 99 fishing", 8146);
            }
            c.getPA().sendFrame126("", 8147);
            c.getPA().sendFrame126("@or1@Reward:", 8148);
            c.getPA().sendFrame126("", 8149);
            c.getPA().sendFrame126("@blu@1,000,000 coins", 8150);
            c.getPA().sendFrame126("@blu@Title: Baiter", 8151);
            c.getPA().sendFrame126("", 8152);
            c.getPA().sendFrame126("@or1@Progress:", 8153);
            if (c.getLevelForXP(c.playerXP[10]) >= 99) {
                c.getPA().sendFrame126("@ceo@COMPLETED", 8154);
                c.getPA().sendFrame126("Change title", 8155);
            } else if (c.getLevelForXP(c.playerXP[10]) >= 1) {
                c.getPA().sendFrame126("@yel@" + c.playerLevel[10] + "@or1@/99", 8154);
            } else {
                c.getPA().sendFrame126("@red@NOT STARTED", 8154);
            }
            break;
            
        case 121048: //CRABS 17
        	InterfaceText.writeText(new QuestInterface(c));
        	c.viewingAchievement = 17;
            c.getPA().showInterface(8134);
            c.getPA().sendFrame126("Crabs", 8144);
            c.getPA().sendFrame126("@or1@How to complete:", 8145);
            if (c.rockCrabKills >= 100) {
                c.getPA().sendFrame126("@blu@<str>Kill 100 rock crabs", 8146);
            } else {
                c.getPA().sendFrame126("@blu@Kill 100 rock crabs", 8146);
            }
            c.getPA().sendFrame126("", 8147);
            c.getPA().sendFrame126("@or1@Reward:", 8148);
            c.getPA().sendFrame126("", 8149);
            c.getPA().sendFrame126("@blu@100,000 coins", 8150);
            c.getPA().sendFrame126("@blu@Title: Crabs", 8151);
            c.getPA().sendFrame126("", 8152);
            c.getPA().sendFrame126("@or1@Progress:", 8153);
            if (c.rockCrabKills >= 100) {
                c.getPA().sendFrame126("@ceo@COMPLETED", 8154);
                c.getPA().sendFrame126("Change title", 8155);
            } else if (c.rockCrabKills >= 1) {
                c.getPA().sendFrame126("@yel@" + c.rockCrabKills + "@or1@/100", 8154);
            } else {
                c.getPA().sendFrame126("@red@NOT STARTED", 8154);
            }
            break;
            
        case 121049: //JAD 18
        	InterfaceText.writeText(new QuestInterface(c));
        	c.viewingAchievement = 18;
            c.getPA().showInterface(8134);
            c.getPA().sendFrame126("Jad Killer", 8144);
            c.getPA().sendFrame126("@or1@How to complete:", 8145);
            if (c.jadKills >= 15) {
                c.getPA().sendFrame126("@blu@<str>Kill TzTok Jad 15 times.", 8146);
            } else {
                c.getPA().sendFrame126("@blu@Kill TzTok Jad 15 times.", 8146);
            }
            c.getPA().sendFrame126("", 8147);
            c.getPA().sendFrame126("@or1@Reward:", 8148);
            c.getPA().sendFrame126("", 8149);
            c.getPA().sendFrame126("@blu@500,000 coins", 8150);
            c.getPA().sendFrame126("@blu@Title: TzTok", 8151);
            c.getPA().sendFrame126("", 8152);
            c.getPA().sendFrame126("@or1@Progress:", 8153);
            if (c.jadKills >= 15) {
                c.getPA().sendFrame126("@ceo@COMPLETED", 8154);
                c.getPA().sendFrame126("Change title", 8155);
            } else if (c.jadKills >= 1) {
                c.getPA().sendFrame126("@yel@" + c.jadKills + "@or1@/15", 8154);
            } else {
                c.getPA().sendFrame126("@red@NOT STARTED", 8154);
            }
            break;
            
        case 121050: //Nazi 19
        	InterfaceText.writeText(new QuestInterface(c));
        	c.viewingAchievement = 19;
            c.getPA().showInterface(8134);
            c.getPA().sendFrame126("Nazi", 8144);
            c.getPA().sendFrame126("@or1@How to complete:", 8145);
            if (c.jadKills >= 15) {
                c.getPA().sendFrame126("@blu@<str>Chop down 1,000 yew trees.", 8146);
            } else {
                c.getPA().sendFrame126("@blu@Chop down 1,000 yew trees.", 8146);
            }
            c.getPA().sendFrame126("", 8147);
            c.getPA().sendFrame126("@or1@Reward:", 8148);
            c.getPA().sendFrame126("", 8149);
            c.getPA().sendFrame126("@blu@100,000 coins", 8150);
            c.getPA().sendFrame126("@blu@Title: Nazi", 8151);
            c.getPA().sendFrame126("", 8152);
            c.getPA().sendFrame126("@or1@Progress:", 8153);
            if (c.yewsCut >= 1000) {
                c.getPA().sendFrame126("@ceo@COMPLETED", 8154);
                c.getPA().sendFrame126("Change title", 8155);
            } else if (c.yewsCut >= 1) {
                c.getPA().sendFrame126("@yel@" + c.yewsCut + "@or1@/1000", 8154);
            } else {
                c.getPA().sendFrame126("@red@NOT STARTED", 8154);
            }
            break;
            
        case 121051: //Iron Chef 20
        	InterfaceText.writeText(new QuestInterface(c));
        	c.viewingAchievement = 20;
            c.getPA().showInterface(8134);
            c.getPA().sendFrame126("Iron Chef", 8144);
            c.getPA().sendFrame126("@or1@How to complete:", 8145);
            if (c.foodCooked >= 10000) {
                c.getPA().sendFrame126("@blu@<str>Successfully cook 10,000 foods.", 8146);
            } else {
                c.getPA().sendFrame126("@blu@Successfully cook 10,000 foods.", 8146);
            }
            c.getPA().sendFrame126("", 8147);
            c.getPA().sendFrame126("@or1@Reward:", 8148);
            c.getPA().sendFrame126("", 8149);
            c.getPA().sendFrame126("@blu@1,000,000 coins", 8150);
            c.getPA().sendFrame126("@blu@Title: Iron Chef", 8151);
            c.getPA().sendFrame126("", 8152);
            c.getPA().sendFrame126("@or1@Progress:", 8153);
            if (c.foodCooked >= 10000) {
                c.getPA().sendFrame126("@ceo@COMPLETED", 8154);
                c.getPA().sendFrame126("Change title", 8155);
            } else if (c.foodCooked >= 1) {
                c.getPA().sendFrame126("@yel@" + c.foodCooked + "@or1@/10000", 8154);
            } else {
                c.getPA().sendFrame126("@red@NOT STARTED", 8154);
            }
            break;
            
        case 121052: //Blazed 21
        	InterfaceText.writeText(new QuestInterface(c));
        	c.viewingAchievement = 21;
            c.getPA().showInterface(8134);
            c.getPA().sendFrame126("Blazed", 8144);
            c.getPA().sendFrame126("@or1@How to complete:", 8145);
            if (c.logsBurned >= 10000) {
                c.getPA().sendFrame126("@blu@<str>Burn 4,200 logs.", 8146);
            } else {
                c.getPA().sendFrame126("@blu@Burn 4,200 logs.", 8146);
            }
            c.getPA().sendFrame126("", 8147);
            c.getPA().sendFrame126("@or1@Reward:", 8148);
            c.getPA().sendFrame126("", 8149);
            c.getPA().sendFrame126("@blu@1,000,000 coins", 8150);
            c.getPA().sendFrame126("@blu@Title: Blazed", 8151);
            c.getPA().sendFrame126("", 8152);
            c.getPA().sendFrame126("@or1@Progress:", 8153);
            if (c.logsBurned >= 4200) {
                c.getPA().sendFrame126("@ceo@COMPLETED", 8154);
                c.getPA().sendFrame126("Change title", 8155);
            } else if (c.logsBurned >= 1) {
                c.getPA().sendFrame126("@yel@" + c.logsBurned + "@or1@/4200", 8154);
            } else {
                c.getPA().sendFrame126("@red@NOT STARTED", 8154);
            }
            break;
            
        case 121053: //Oreo 22
        	InterfaceText.writeText(new QuestInterface(c));
        	c.viewingAchievement = 22;
            c.getPA().showInterface(8134);
            c.getPA().sendFrame126("Oreo", 8144);
            c.getPA().sendFrame126("@or1@How to complete:", 8145);
            if (c.oresRecieved >= 1500) {
                c.getPA().sendFrame126("@blu@<str>Mine 1,500 ores.", 8146);
            } else {
                c.getPA().sendFrame126("@blu@Mine 1,500 ores.", 8146);
            }
            c.getPA().sendFrame126("", 8147);
            c.getPA().sendFrame126("@or1@Reward:", 8148);
            c.getPA().sendFrame126("", 8149);
            c.getPA().sendFrame126("@blu@1,000,000 coins", 8150);
            c.getPA().sendFrame126("@blu@Title: Oreo", 8151);
            c.getPA().sendFrame126("", 8152);
            c.getPA().sendFrame126("@or1@Progress:", 8153);
            if (c.oresRecieved >= 1500) {
                c.getPA().sendFrame126("@ceo@COMPLETED", 8154);
                c.getPA().sendFrame126("Change title", 8155);
            } else if (c.oresRecieved >= 1) {
                c.getPA().sendFrame126("@yel@" + c.oresRecieved + "@or1@/1500", 8154);
            } else {
                c.getPA().sendFrame126("@red@NOT STARTED", 8154);
            }
            break;
            
        case 121054: //Wowza 23
        	InterfaceText.writeText(new QuestInterface(c));
        	c.viewingAchievement = 23;
            c.getPA().showInterface(8134);
            c.getPA().sendFrame126("Wowza", 8144);
            c.getPA().sendFrame126("@or1@How to complete:", 8145);
            if (c.totalPrestiges >= 10) {
                c.getPA().sendFrame126("@blu@<str>Prestige a total of 10 times.", 8146);
            } else {
                c.getPA().sendFrame126("@blu@Prestige a total of 10 times.", 8146);
            }
            c.getPA().sendFrame126("", 8147);
            c.getPA().sendFrame126("@or1@Reward:", 8148);
            c.getPA().sendFrame126("", 8149);
            c.getPA().sendFrame126("@blu@1,000,000 coins", 8150);
            c.getPA().sendFrame126("@blu@Title: Wowza", 8151);
            c.getPA().sendFrame126("", 8152);
            c.getPA().sendFrame126("@or1@Progress:", 8153);
            if (c.totalPrestiges >= 10) {
                c.getPA().sendFrame126("@ceo@COMPLETED", 8154);
                c.getPA().sendFrame126("Change title", 8155);
            } else if (c.totalPrestiges >= 1) {
                c.getPA().sendFrame126("@yel@" + c.totalPrestiges + "@or1@/10", 8154);
            } else {
                c.getPA().sendFrame126("@red@NOT STARTED", 8154);
            }
            break;
        }
    }

}