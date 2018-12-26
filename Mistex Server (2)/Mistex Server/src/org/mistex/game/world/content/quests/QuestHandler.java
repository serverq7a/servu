package org.mistex.game.world.content.quests;

import org.mistex.game.world.content.quests.impl.AvoidingCatastrophe;
import org.mistex.game.world.content.quests.impl.CooksAssistant;
import org.mistex.game.world.content.quests.impl.DoricsQuest;
import org.mistex.game.world.player.Client;

public class QuestHandler {
	
	public static void QuestButtons(Client c, int actionButtonsId) {
		switch (actionButtonsId) {
		
		case 136242://A Game of Chess
			//ChessQuest.handleQuestInterface(c);
			break;
		case 136245://Avoiding Catastrophe
			AvoidingCatastrophe.handleQuestInterface(c);
			break;
		case 136246://Cooking Assistant
			CooksAssistant.showInformation(c);
			break;
		case 136247://Dorics Quest
			DoricsQuest.showInformation(c);
			break;
			
		}
		
	}

}
