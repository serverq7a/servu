package org.mistex.game.world.content.quests.impl;

import org.mistex.game.world.content.interfaces.InterfaceText;
import org.mistex.game.world.content.interfaces.impl.QuestInterface;
import org.mistex.game.world.event.CycleEvent;
import org.mistex.game.world.event.CycleEventContainer;
import org.mistex.game.world.event.CycleEventHandler;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.PlayerConfiguration;

/**
 * 
 * Written by Omar & Chex
 * 
 */
public class ChessQuest {
	
	public static int noStage = 0;
	public static int StageOne = 1;
	public static int StageTwo = 2;
	public static int StageThree = 3;
	public static int StageFour = 4;
	public static int StageFive = 5;
	public static int StageSix = 6;
	public static int StageSeven = 7;
	
	public static void handleQuestInterface(Client c) {
		if (!(c.playerRights == 3)) {			
			c.sendMessage("Not currently released.");
			return;
		}
		if (c.gameOfChess == noStage) {
			notStarted(c);
		} else if (c.gameOfChess == StageOne) {
			stageOne(c);
		} else if (c.gameOfChess == StageTwo) {
			stageTwo(c);
		} else if (c.gameOfChess == StageThree) {
			stageThree(c);
		} else if (c.gameOfChess == StageFour) {
			stageFour(c);
		} else if (c.gameOfChess == StageFive) {
			stageFive(c);
		} else if (c.gameOfChess == StageSix) {
			stageSix(c);
		} else if (c.gameOfChess == StageSeven) {
			stageSeven(c);
		}				
	}
		
	public static void notStarted(final Client c) {
		 InterfaceText.writeText(new QuestInterface(c));
         c.getPA().showInterface(8134);
         c.getPA().sendFrame126("A Game of Chess", 8144);
         c.getPA().sendFrame126("I can start this quest by talking to", 8145);
         c.getPA().sendFrame126("The black knight at the black knight's fortress.", 8146);
         c.getPA().sendFrame126("", 8147);
         c.getPA().sendFrame126("It is recommended to have atleast 43 prayer.", 8148);
	}
	
	public static void stageOne(final Client c) {
		 InterfaceText.writeText(new QuestInterface(c));
        c.getPA().showInterface(8134);
        c.getPA().sendFrame126("A Game of Chess", 8144);
        c.getPA().sendFrame126("The black knight's captain said I should ", 8145);
        c.getPA().sendFrame126("Investigate the white castle. Something to do", 8146);
        c.getPA().sendFrame126("with a key.", 8147);
        c.getPA().sendFrame126("", 8148);
	}
	
	public static void stageTwo(final Client c) {
		InterfaceText.writeText(new QuestInterface(c));
       c.getPA().showInterface(8134);
       c.getPA().sendFrame126("A Game of Chess", 8144);
       c.getPA().sendFrame126("The slave said I should look around in the castle", 8145);
       c.getPA().sendFrame126("For the key.", 8146);
       c.getPA().sendFrame126("", 8147);
       c.getPA().sendFrame126("", 8148);
	}
	
	public static void stageThree(final Client c) {
		InterfaceText.writeText(new QuestInterface(c));
       c.getPA().showInterface(8134);
       c.getPA().sendFrame126("A Game of Chess", 8144);
       c.getPA().sendFrame126("I need to find the key the slave told me about.", 8145);
       c.getPA().sendFrame126("", 8146);
       c.getPA().sendFrame126("I should look in the torture chambers for the key.", 8147);
       c.getPA().sendFrame126("", 8148);
	}
	
	public static void stageFour(final Client c) {
		InterfaceText.writeText(new QuestInterface(c));
       c.getPA().showInterface(8134);
       c.getPA().sendFrame126("A Game of Chess", 8144);
       c.getPA().sendFrame126("I found the key! I should go visit Frank back", 8145);
       c.getPA().sendFrame126("at the Black Knights' Fortress and see what's next.", 8146);
       c.getPA().sendFrame126("", 8147);
       c.getPA().sendFrame126("", 8148);
	}
	
	public static void stageFive(final Client c) {
		InterfaceText.writeText(new QuestInterface(c));
       c.getPA().showInterface(8134);
       c.getPA().sendFrame126("A Game of Chess", 8144);
       c.getPA().sendFrame126("The squire from the White Knights' castle", 8145);
       c.getPA().sendFrame126("told me Sir Amik Varze wanted to talk to me.", 8146);
       c.getPA().sendFrame126("", 8147);
       c.getPA().sendFrame126("I should go talk to him.", 8148);
	}

	public static void stageSix(final Client c) {
		InterfaceText.writeText(new QuestInterface(c));
       c.getPA().showInterface(8134);
       c.getPA().sendFrame126("A Game of Chess", 8144);
       c.getPA().sendFrame126("", 8145);
       c.getPA().sendFrame126("", 8146);
       c.getPA().sendFrame126("", 8147);
       c.getPA().sendFrame126("", 8148);
	}
	public static void stageSeven(final Client c) {
		InterfaceText.writeText(new QuestInterface(c));
       c.getPA().showInterface(8134);
       c.getPA().sendFrame126("A Game of Chess", 8144);
       c.getPA().sendFrame126("", 8145);
       c.getPA().sendFrame126("", 8146);
       c.getPA().sendFrame126("", 8147);
       c.getPA().sendFrame126("", 8148);
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
