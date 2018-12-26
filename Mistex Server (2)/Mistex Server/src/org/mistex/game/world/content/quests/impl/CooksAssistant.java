package org.mistex.game.world.content.quests.impl;

import org.mistex.game.world.content.interfaces.InterfaceText;
import org.mistex.game.world.content.interfaces.impl.InformationTab;
import org.mistex.game.world.player.Client;

public class CooksAssistant {

    public static void showInformation(Client c) {
        for (int i = 8144; i < 8195; i++) {
            c.getPA().sendFrame126("", i);
        }
        c.getPA().sendFrame126("@dre@Cook's Assistant", 8144);
        c.getPA().sendFrame126("", 8145);
        if (c.cookAss == 0) {
            c.getPA().sendFrame126("Cook's Assistant", 8144);
            c.getPA().sendFrame126("I can start this quest by speaking to the Cook in the", 8147);
            c.getPA().sendFrame126("Lumbridge Castle kitchen.", 8148);
            c.getPA().sendFrame126("", 8149);
            c.getPA().sendFrame126("There are no minimum requirments.", 8150);
        } else if (c.cookAss == 1) {
            c.getPA().sendFrame126("Cook's Assistant", 8144);
            c.getPA().sendFrame126("<str>I've talked to the cook.", 8147);
            c.getPA().sendFrame126("He wants me to gather the following materials:", 8148);
            if (c.getItems().playerHasItem(1944, 1)) {
                c.getPA().sendFrame126("<str>1 egg", 8149);
            } else {
                c.getPA().sendFrame126("@red@1 egg", 8149);
            }
            if (c.getItems().playerHasItem(1927, 1)) {
                c.getPA().sendFrame126("<str>1 bucket of milk", 8150);
            } else {
                c.getPA().sendFrame126("@red@1 bucket of milk", 8150);
            }
            if (c.getItems().playerHasItem(1933, 1)) {
                c.getPA().sendFrame126("<str>1 heap of flour", 8151);
            } else {
                c.getPA().sendFrame126("@red@1 pot of flour", 8151);
            }
        } else if (c.cookAss == 2) {
            c.getPA().sendFrame126("Cook's Assistant", 8144);
            c.getPA().sendFrame126("<str>I talked to the cook.", 8147);
            c.getPA().sendFrame126("<str>I gave the cook his items.", 8148);
            c.getPA().sendFrame126("I should go speak to the cook.", 8149);
        } else if (c.cookAss == 3) {
            c.getPA().sendFrame126("Cook's Assistant", 8144);
            c.getPA().sendFrame126("<str>I talked to the cook.", 8147);
            c.getPA().sendFrame126("<str>I gave him his items.", 8148);
            c.getPA().sendFrame126("@red@     QUEST COMPLETE", 8150);
            c.getPA().sendFrame126("As a reward, I gained 15,000 Cooking Experience.", 8151);
        }
        c.getPA().showInterface(8134);
    }

    public static void rewards(Client c) {
        c.cookAss = 3;
        c.questCompleted += 1;
        c.getPA().addSkillXP(150000, 7);
        c.getPA().showInterface(297);
        c.getPA().showInterface(12140);
        c.getPA().sendFrame126("You have completed: Cook's Assistant", 12144);
        c.getPA().sendFrame126("15,0000 Cooking Experience", 12150);
        c.getPA().sendFrame126("", 12151);
        c.getPA().sendFrame126("", 12152);
        c.getPA().sendFrame126("", 12153);
        c.getPA().sendFrame126("", 12154);
        c.getPA().sendFrame126("", 12155);
        c.getPA().sendFrame126("@gre@Cook's Assistant", 7332);
    	InterfaceText.writeText(new InformationTab(c));
    }


}