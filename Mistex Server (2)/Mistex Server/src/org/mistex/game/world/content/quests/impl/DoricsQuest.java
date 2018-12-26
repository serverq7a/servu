package org.mistex.game.world.content.quests.impl;

import org.mistex.game.world.content.interfaces.InterfaceText;
import org.mistex.game.world.content.interfaces.impl.InformationTab;
import org.mistex.game.world.player.Client;

public class DoricsQuest {

    public static void showInformation(Client c) {
        for (int i = 8144; i < 8195; i++) {
            c.getPA().sendFrame126("", i);
        }
        c.getPA().sendFrame126("@dre@Dorics Quest", 8144);
        c.getPA().sendFrame126("", 8145);
        if (c.doricsQuest == 0) {
            c.getPA().sendFrame126("Dorics Quest", 8144);
            c.getPA().sendFrame126("I can start this quest by speaking to Doric, the dwarf, near", 8147);
            c.getPA().sendFrame126("the Goblin Village.", 8148);
            c.getPA().sendFrame126("", 8149);
            c.getPA().sendFrame126("Minimum requirements are:", 8150);
            c.getPA().sendFrame126("Atleast level 15 mining.", 8151);
        } else if (c.doricsQuest == 1) {
            c.getPA().sendFrame126("Dorics Quest", 8144);
            c.getPA().sendFrame126("<str>I've talked to Doric.", 8147);
            c.getPA().sendFrame126("He wants me to gather the following materials:", 8148);
            if (c.getItems().playerHasItem(435, 6)) {
                c.getPA().sendFrame126("<str>6 Clay (noted)", 8149);
            } else {
                c.getPA().sendFrame126("@red@6 Clay (noted)", 8149);
            }
            if (c.getItems().playerHasItem(437, 4)) {
                c.getPA().sendFrame126("<str>4 Copper ores (noted)", 8150);
            } else {
                c.getPA().sendFrame126("@red@4 Copper ores (noted)", 8150);
            }
            if (c.getItems().playerHasItem(441, 2)) {
                c.getPA().sendFrame126("<str>2 Iron ores (noted)", 8151);
            } else {
                c.getPA().sendFrame126("@red@2 Iron ores (noted)", 8151);
            }
        } else if (c.doricsQuest == 2) {
            c.getPA().sendFrame126("Dorics Quest", 8144);
            c.getPA().sendFrame126("<str>I've talked to Doric.", 8147);
            c.getPA().sendFrame126("He wants me to gather the following materials:", 8148);
            if (c.getItems().playerHasItem(435, 6)) {
                c.getPA().sendFrame126("<str>6 Clay (noted)", 8149);
            } else {
                c.getPA().sendFrame126("@red@6 Clay (noted)", 8149);
            }
            if (c.getItems().playerHasItem(437, 4)) {
                c.getPA().sendFrame126("<str>4 Copper ores (noted)", 8150);
            } else {
                c.getPA().sendFrame126("@red@4 Copper ores (noted)", 8150);
            }
            if (c.getItems().playerHasItem(441, 2)) {
                c.getPA().sendFrame126("<str>2 Iron ores (noted)", 8151);
            } else {
                c.getPA().sendFrame126("@red@2 Iron ores (noted)", 8151);
            }
        } else if (c.doricsQuest == 3) {
            c.getPA().sendFrame126("Dorics Quest", 8144);
            c.getPA().sendFrame126("<str>I talked to Doric.", 8147);
            c.getPA().sendFrame126("<str>I gave him his items.", 8148);
            c.getPA().sendFrame126("@red@     QUEST COMPLETE", 8150);
            c.getPA().sendFrame126("As a reward, I gained 30.000 Mining Experience.", 8151);
        }
        c.getPA().showInterface(8134);
    }

    public static void doricFinish(Client c) {
        c.doricsQuest = 3;
        c.questCompleted += 1;
        c.getPA().addSkillXP(75000, 7);
        c.getPA().showInterface(297);
        c.getPA().showInterface(12140);
        c.getPA().sendFrame126("You have completed: Dorics Quest", 12144);
        c.getPA().sendFrame126("75,000 Mining Experience", 12150);
        c.getPA().sendFrame126("", 12151);
        c.getPA().sendFrame126("", 12152);
        c.getPA().sendFrame126("", 12153);
        c.getPA().sendFrame126("", 12154);
        c.getPA().sendFrame126("", 12155);
        c.getPA().sendFrame126("@gre@Dorics Quest!", 7332);
    	InterfaceText.writeText(new InformationTab(c));
    }

}