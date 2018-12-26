package org.mistex.game.world.player.item;

import org.mistex.game.Mistex;
import org.mistex.game.MistexUtility;
import org.mistex.game.world.content.skill.cooking.Cooking;
import org.mistex.game.world.content.skill.crafting.GemCutting;
import org.mistex.game.world.content.skill.farming.Farming;
import org.mistex.game.world.content.skill.firemaking.Bonfire;
import org.mistex.game.world.content.skill.fletching.BowHandler;
import org.mistex.game.world.content.skill.fletching.StringingHandler;
import org.mistex.game.world.content.skill.herblore.Grinding;
import org.mistex.game.world.content.skill.smithing.SmithingInterface;
import org.mistex.game.world.player.Client;


public class UseItem {

	public static void ItemonObject(Client c, int objectID, int objectX,int objectY, int itemId) {
		if (!c.getItems().playerHasItem(itemId, 1))
			return;		
		if (Cooking.handleInterface(c, itemId, objectID, objectX, objectY)) {
            return;
		}
		if (c.getCompost().handleItemOnObject(itemId, objectID, objectX, objectY)) {
			return;
		}		
		if (Farming.prepareCrop(c, itemId, objectID, objectX, objectY)) {
			return;
		}
		switch (objectID) {
		case 2732:
			Bonfire.AddBonFireLogs(c, itemId);
			break;
		case 8689:
			if(c.getItems().playerHasItem(1925, 1) && c.objectId == 8689) {
					c.turnPlayerTo(c.objectX, c.objectY);
					c.startAnimation(2292);
					c.getItems().addItem(1927 ,1);
					c.getItems().deleteItem(1925, 1);
					
				} else {
					   c.sendMessage("You need a bucket to milk a cow!");
					}
				break;
		case 15621:
			Mistex.getWarriorsGuild().handleArmor(c, itemId, objectX, objectY);
		break;
		case 2782:
		case 2783:
			SmithingInterface.showSmithInterface(c, itemId);
			break;

		default:
			if (c.playerRights == 3)
				MistexUtility.println("Player At Object id: " + objectID+ " with Item id: " + itemId);
			break;
		}

	}

	public static void ItemonItem(Client c, int itemUsed, int useWith) {
		if (ItemAssistant.getItemName(itemUsed).contains("(")&& ItemAssistant.getItemName(useWith).contains("(")) {
			c.getPotMixing().mixPotion2(itemUsed, useWith);
		}
		StringingHandler.useItemInterface(c, useWith, itemUsed);
		BowHandler.handleInterface(c, useWith, itemUsed);
		Grinding.init(c, itemUsed, useWith);
		if (itemUsed == 1755 || useWith == 1755) {
			GemCutting.cutGem(c, itemUsed, useWith);
		}

		switch (itemUsed) {
		default:
			if (c.playerRights == 3)
				MistexUtility.println("Player used Item id: " + itemUsed+ " with Item id: " + useWith);
			break;
		}
	}

	public static void ItemonNpc(Client c, int itemId, int npcId, int slot) {
		if (itemId == 3265 && npcId == 741) {			
			c.getItems().deleteItem(3265, 1);
			c.getDH().sendDialogues(747, 741);
		}
		switch (itemId) {

		default:
			if (c.playerRights == 3)
				MistexUtility.println("Player used Item id: " + itemId+ " with Npc id: " + npcId + " With Slot : " + slot);
			break;
		}

	}

}
