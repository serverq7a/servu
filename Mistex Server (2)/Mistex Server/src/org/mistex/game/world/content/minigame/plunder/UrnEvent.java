package org.mistex.game.world.content.minigame.plunder;

import org.mistex.game.Mistex;
import org.mistex.game.world.event.CycleEvent;
import org.mistex.game.world.event.CycleEventContainer;
import org.mistex.game.world.gameobject.ObjectHandler;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.Player;

public class UrnEvent extends CycleEvent {
	
	private Client c;
	private int id, obX, obY;
	private boolean success;
	
	public UrnEvent(Client c, int id, int obX, int obY, boolean success) {
		this.c = c;
		this.id = id;
		this.obX = obX;
		this.obY = obY;
		this.success = success;
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
			if (success) {
				c.sendMessage("You successfully loot the urn!");
				addLoot(c);
				ObjectHandler.createAnObject(c, PlunderData.lootedUrn(id), obX, obY, 0);
				c.getPA().addSkillXP(PlunderData.EXP_MULTIPLIR * PlunderData.LOOT_EXP.getData()[c.plunderFloor & 7], Player.playerThieving);
				if (PlunderData.hasSnake(id) && 0.5 > Math.random()) {
					if (c.poisonDamage > 0) {
						c.forcedChat("Ow!");
						c.getPA().appendPoison(4, "You've been poisoned by the snake bite.");
						c.handleHitMask(1);
						c.dealDamage(1);
					} else
						c.getPA().appendPoison(4, "You've been bitten by a snake.");
				}
			} else {
				c.forcedChat("Ow!");
				c.handleHitMask(1);
				c.dealDamage(1);
			}
		}
	}
	
	public static void addLoot(Client c) {
		int[] possibleLoot = PlunderData.getLoot(c.plunderFloor);
		int loot = possibleLoot[(int) (Math.random() * possibleLoot.length)];
		if (!c.getItems().addItem(loot, 1)) {
			Mistex.itemHandler.createGroundItem(c, loot, c.absX, c.absY, 1, c.playerId);
		}
	}
}