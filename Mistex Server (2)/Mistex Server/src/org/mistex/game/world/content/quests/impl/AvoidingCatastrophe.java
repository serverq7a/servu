package org.mistex.game.world.content.quests.impl;

import org.mistex.game.world.content.interfaces.InterfaceText;
import org.mistex.game.world.content.interfaces.impl.InformationTab;
import org.mistex.game.world.content.interfaces.impl.QuestInterface;
import org.mistex.game.world.event.CycleEvent;
import org.mistex.game.world.event.CycleEventContainer;
import org.mistex.game.world.event.CycleEventHandler;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.PlayerConfiguration;

/**
 * 
 * Written by Omar & Chex
 * Current part - go visit duke
 */
public class AvoidingCatastrophe {
	
	public static int noStage = 0;
	public static int StageOne = 1;
	public static int StageTwo = 2;
	public static int StageThree = 3;
	public static int StageFour = 4;
	public static int StageFive = 5;
	public static int StageSix = 6;
	public static int StageSeven = 7;
	public static int StageEight = 8;
	public static int StageNine = 9;
	public static int StageTen = 10;
	
	public static void handleQuestInterface(Client c) {
		if (c.AcQuest == noStage) {
			notStarted(c);
		} else if (c.AcQuest == StageOne) {
			stageOne(c);
		} else if (c.AcQuest == StageTwo) {
			stageTwo(c);
		} else if (c.AcQuest == StageThree) {
			stageThree(c);
		} else if (c.AcQuest == StageFour) {
			stageFour(c);
		} else if (c.AcQuest == StageFive) {
			stageFive(c);
		} else if (c.AcQuest == StageSix) {
			stageSix(c);		
		} else if (c.AcQuest == StageSeven) {
			stageSeven(c);
		} else if (c.AcQuest == StageEight) {
			stageEight(c);
		} else if (c.AcQuest == StageNine) {
			stageNine(c);
		} else if (c.AcQuest == StageTen) {
			stageTen(c);
		}				
	}
	
	public static boolean hasHolyIngredients(Client c) {
		if (c.getItems().playerHasItem(1512, 100) && c.getItems().playerHasItem(527, 20) && c.getItems().playerHasItem(564, 100) && c.getItems().playerHasItem(1213, 1)) {
			return true;
		}
		return false;
	}	
		
	public static void notStarted(final Client c) {
		 InterfaceText.writeText(new QuestInterface(c));
         c.getPA().showInterface(8134);
         c.getPA().sendFrame126("Avoiding Catastrophe", 8144);
         c.getPA().sendFrame126("Speak to Father Aereck at the lumbridge church.", 8145);
         c.getPA().sendFrame126("", 8146);
         c.getPA().sendFrame126("", 8147);
         c.getPA().sendFrame126("", 8148);
	}
	
	public static void stageOne(final Client c) {
		 InterfaceText.writeText(new QuestInterface(c));
        c.getPA().showInterface(8134);
        c.getPA().sendFrame126("Avoiding Catastrophe", 8144);
        c.getPA().sendFrame126("I should visit the Father's informant whom is located", 8145);
        c.getPA().sendFrame126("in the southern-east shop of varrock.", 8146);
        c.getPA().sendFrame126("", 8147);
        c.getPA().sendFrame126("", 8148);
	}
	
	public static void stageTwo(final Client c) {
		InterfaceText.writeText(new QuestInterface(c));
       c.getPA().showInterface(8134);
       c.getPA().sendFrame126("Avoiding Catastrophe", 8144);
       c.getPA().sendFrame126("Aubury is being a brat. I should go back to the Father.", 8145);
       c.getPA().sendFrame126("", 8146);
       c.getPA().sendFrame126("", 8147);
       c.getPA().sendFrame126("", 8148);
	}
	
	public static void stageThree(final Client c) {
		InterfaceText.writeText(new QuestInterface(c));
       c.getPA().showInterface(8134);
       c.getPA().sendFrame126("Avoiding Catastrophe", 8144);
       c.getPA().sendFrame126("I need to gain the trust of Aubury. To do so I will need a", 8145);
       c.getPA().sendFrame126("holy napkin, but to make it the Father needs the following", 8146);
       c.getPA().sendFrame126("ingredients:", 8147);
       c.getPA().sendFrame126("", 8148);
       c.getPA().sendFrame126("100 Normal Noted Logs", 8149);
       c.getPA().sendFrame126("20 Normal Noted Bones", 8150);
       c.getPA().sendFrame126("100 Cosmic Runes", 8151);
       c.getPA().sendFrame126("1 Rune Dagger", 8152);
	}
	
	public static void stageFour(final Client c) {
		InterfaceText.writeText(new QuestInterface(c));
       c.getPA().showInterface(8134);
       c.getPA().sendFrame126("Avoiding Catastrophe", 8144);
       c.getPA().sendFrame126("I should bring the holy napkin to Aubury.", 8145);
       c.getPA().sendFrame126("", 8146);
       c.getPA().sendFrame126("", 8147);
       c.getPA().sendFrame126("", 8148);
	}
	
	public static void stageFive(final Client c) {
		InterfaceText.writeText(new QuestInterface(c));
       c.getPA().showInterface(8134);
       c.getPA().sendFrame126("Avoiding Catastrophe", 8144);
       c.getPA().sendFrame126("I should go back to Aubury with the new information.", 8145);
       c.getPA().sendFrame126("", 8146);
       c.getPA().sendFrame126("", 8147);
       c.getPA().sendFrame126("", 8148);
	}
	
	public static void stageSix(final Client c) {
		InterfaceText.writeText(new QuestInterface(c));
       c.getPA().showInterface(8134);
       c.getPA().sendFrame126("Avoiding Catastrophe", 8144);
       c.getPA().sendFrame126("You should visit the Duke of Lumbridge.", 8145);
       c.getPA().sendFrame126("He should be located around the lumbridge castle.", 8146);
       c.getPA().sendFrame126("", 8147);
       c.getPA().sendFrame126("", 8148);
	}
	
	public static void stageSeven(final Client c) {
		InterfaceText.writeText(new QuestInterface(c));
       c.getPA().showInterface(8134);
       c.getPA().sendFrame126("Avoiding Catastrophe", 8144);
       c.getPA().sendFrame126("", 8145);
       c.getPA().sendFrame126("", 8146);
       c.getPA().sendFrame126("Use a harpoon on a vial in the home bank. ", 8147);
       c.getPA().sendFrame126("Once you have the potion use it on the Duke.", 8148);
	}	
	
	public static void stageEight(final Client c) {
		InterfaceText.writeText(new QuestInterface(c));
       c.getPA().showInterface(8134);
       c.getPA().sendFrame126("Avoiding Catastrophe", 8144);
       c.getPA().sendFrame126("I should go to the wizard tower.", 8145);
       c.getPA().sendFrame126("", 8146);
       c.getPA().sendFrame126("", 8147);
       c.getPA().sendFrame126("", 8148);
	}
	
	public static void stageNine(final Client c) {
		InterfaceText.writeText(new QuestInterface(c));
       c.getPA().showInterface(8134);
       c.getPA().sendFrame126("Avoiding Catastrophe", 8144);
       c.getPA().sendFrame126("I killed some random zamorak mage and got a note.", 8145);
       c.getPA().sendFrame126("Maybe I should give it to the Father.", 8146);
       c.getPA().sendFrame126("", 8147);
       c.getPA().sendFrame126("", 8148);
	}     
	
	public static void stageTen(final Client c) {
        c.getPA().showInterface(297);
        c.getPA().showInterface(12140);
        c.getPA().sendFrame126("You have completed: Avoiding Catastrophe", 12144);
        c.getPA().sendFrame126("You recieved 500k prayer xp.", 12150);
        c.getPA().sendFrame126("", 12151);
        c.getPA().sendFrame126("", 12152);
        c.getPA().sendFrame126("", 12153);
        c.getPA().sendFrame126("", 12154);
        c.getPA().sendFrame126("", 12155);
    	InterfaceText.writeText(new InformationTab(c));
	}
	
	public static void handleWeedSmoking(final Client c) {
		c.startAnimation(884);
		c.sendMessage("The weed starts to take effect...");
    	c.getPA().walkableInterface(18460);
		CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
            public void execute(CycleEventContainer p) {

                p.stop();
            }
            	@Override
                public void stop() {
            		c.getPA().closeAllWindows();
            	}
            }, 80);
	}
	
	public static void handleNoOption(final Client c) {
		c.startAnimation(836);
		CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
            public void execute(CycleEventContainer p) {
                p.stop();
            }
            	@Override
                public void stop() {
            		c.getPA().resetAnimation();
            		c.startAnimation(65535);	
            		c.getPA().movePlayer(PlayerConfiguration.RESPAWN_X, PlayerConfiguration.RESPAWN_Y, 0);
            	}
            }, 5);
	}

}
