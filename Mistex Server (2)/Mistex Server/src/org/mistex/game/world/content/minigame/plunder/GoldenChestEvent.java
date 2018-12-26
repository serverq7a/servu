package org.mistex.game.world.content.minigame.plunder;

import org.mistex.game.Mistex;
import org.mistex.game.MistexUtility;
import org.mistex.game.world.event.CycleEvent;
import org.mistex.game.world.event.CycleEventContainer;
import org.mistex.game.world.gameobject.ObjectHandler;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.item.ItemAssistant;

public class GoldenChestEvent extends CycleEvent {
	
	private Client c;
	private int obX, obY;
	
	public GoldenChestEvent(Client c, int obX, int obY) {
		this.c = c;
		this.obX = obX;
		this.obY = obY;
	}

	@Override
	public void execute(CycleEventContainer container) {
		container.stop();
	}

	@Override
	public void stop() {
		c.canPlayerMove = true;
		if (c.isSkilling) {
			c.isSkilling = false;
			addLoot(c);
			c.getPA().addSkillXP(PlunderData.EXP_MULTIPLIR * PlunderData.GOLD_CHEST_EXP.getData()[(++c.plunderFloor) & 7], 17);
			ObjectHandler.createAnObject(c, PlunderData.GRAND_GOLD_CHEST.getData()[1], obX, obY);
		}
	}
	
	public static void addLoot(Client c) {
		int[] possibleLoot = PlunderData.CHEST_REWARDS.getData();
		int loot = possibleLoot[(int) (Math.random() * possibleLoot.length)];
		if (Math.random() > 1 - 1/10.0) {
			possibleLoot = new int[] { 9044 };
			loot = possibleLoot[(int) (Math.random() * possibleLoot.length)];
			if (!c.getItems().addItem(loot, 1)) {
				c.getBank().addItem(loot + 1, 1);
				c.sendMessage("<col=FF0000>Your <col=C42BAD>" + MistexUtility.capitalize(ItemAssistant.getItemName(loot).replaceAll("_", " ")) + "<col=FF0000> has been added to your bank.");
			}
		} else {
			if (!c.getItems().addItem(loot, 1)) {
				Mistex.itemHandler.createGroundItem(c, loot, c.absX, c.absY, 1, c.playerId);
			}
		}
	}
}
