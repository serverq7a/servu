package org.mistex.game.world.content.minigame.warriorsguild;

import org.mistex.game.world.event.CycleEvent;
import org.mistex.game.world.event.CycleEventContainer;
import org.mistex.game.world.event.CycleEventHandler;
import org.mistex.game.world.npc.NPCHandler;
import org.mistex.game.world.player.Client;

public class WarriorsGuild {
	
	public WarriorsGuild() {
		//TO DO
	}
	
	private int xCoord, yCoord;
	
	public void setSpawned(Client c, boolean spawned) {
		c.spawned = spawned;
	}

	private final int[][] ARMORREQUIRED = {{1075, 1117, 1155, 4278, 20, 5, 30, 100}, {1067, 1115, 1153, 4279, 30, 7, 40, 100}, {1069, 1119, 1157, 4280, 40, 10, 60, 100}, {1077, 1125, 1165, 4281, 50, 15, 70, 100},
									{1071, 1121, 1159, 4282, 60, 15, 80, 100}, {1091, 1123, 1161, 4283, 80, 17, 90, 10}, {1079, 1127, 1163, 4284, 100, 20, 100, 100}};
	

	public void handleArmor(Client c, int itemId, int X, int Y) {
		if(!c.spawned) {
			this.xCoord = X;
			this.yCoord = Y;
			for(int i = 0; i < ARMORREQUIRED.length; i++) {
				for(int f = 0; f < ARMORREQUIRED[0].length; f++) {
					if(itemId == ARMORREQUIRED[i][f]) {
						if(hasItem(c, ARMORREQUIRED[i][0], true) && hasItem(c, ARMORREQUIRED[i][1], true) && hasItem(c, ARMORREQUIRED[i][2], true)) {
							animateArmor(c, new int[] {ARMORREQUIRED[i][0], ARMORREQUIRED[i][1], ARMORREQUIRED[i][2]}, ARMORREQUIRED[i][3], ARMORREQUIRED[i][4], ARMORREQUIRED[i][5], ARMORREQUIRED[i][6], ARMORREQUIRED[i][7]);
						}
					}
				}
			}
		} else {
			c.sendMessage("You already have armour spawned!");
		}
	}
	
	private boolean hasItem(Client c, int itemId, boolean inventory) {
		return inventory ? c.getItems().playerHasItem(itemId) : c.getItems().playerHasItem(itemId) || c.playerEquipment[5] == itemId;
	}
	
	private void animateArmor(Client c, int[] usedArmor, int npcId, int hitPoints, int maxHit, int attack, int defence) {
		c.getPA().walkTo(0, 3);
		for(int i = 0; i < 3; i++) {
			c.getItems().deleteItem(usedArmor[i], 1);
		}
		c.spawned = true;
		NPCHandler.spawnNpc(c, npcId, xCoord, yCoord, 0, 0, hitPoints, maxHit, attack, defence, true, true);
	}
	
	private final String[][] DEFENDER = {{"8850", "Rune"}, {"8849", "Adamant"}, {"8848", "Mithril"}, {"8847", "Black"}, {"8846", "Steel"}, {"8845", "Iron"}, {"8844", "Bronze"}};
	

	public int getCyclopsDrop(Client c) {
		if(!c.kamfreenaDone)
			return Integer.parseInt(DEFENDER[6][0]);
		for(int i = 0; i < DEFENDER.length; i++) {
			try {
				if(hasItem(c, Integer.parseInt(DEFENDER[i][0]), false))
					return Integer.parseInt(DEFENDER[i - 1][0]);
			} catch(Exception e) {
				return Integer.parseInt(DEFENDER[0][0]);
			}
		}
		return Integer.parseInt(DEFENDER[6][0]);
	}
	
	public String getCyclopsDrop126(Client c) {
		for(int i = 0; i < DEFENDER.length; i++) {
			if(Integer.parseInt(DEFENDER[i][0]) == getCyclopsDrop(c)) {
				return DEFENDER[i][1];
			}
		}
		return DEFENDER[0][1];
	}
	
	public void handleKamfreena(Client c, boolean door) {
		if(door) {
			if(!c.getItems().playerHasItem(8851, 100)) {
				c.sendMessage("You need 100 tokens to go in!");
				return;
			}
			if(c.absX < 2848) {
				if(!c.kamfreenaDone)
					c.getDH().sendDialogues(45, 4289);
				c.getPA().movePlayer(2848, 3541, 2);
				executeCyclopsTimer(c);
			} else {
				c.inCyclops = false;
				c.getPA().movePlayer(2847, 3541, 2);
				c.kamfreenaDone = false;
			}
		} else {
			c.kamfreenaDone = true;
			c.getDH().sendDialogues(47, 4289);
		}
	}
	
	private void executeCyclopsTimer(final Client c) {
		c.inCyclops = true;
		CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer e) {
				if(c.getItems().playerHasItem(8851, 20) && c.inCyclops) {
					c.sendMessage("You lose 20 tokens.");
					c.getItems().deleteItem2(8851, 20);
				} 
				if(!c.getItems().playerHasItem(8851, 20)) {
					c.inCyclops = false;
					c.sendMessage("You have ran out of tokens!");
					c.getPA().movePlayer(2846, 3541, 2);
					e.stop();
				}
			}

			@Override
			public void stop() {
			}
		}, 60000);
	}
	
	//Original:
	
	/*private Event cyclopsTimer(final Player c) {
		return new Event() {
			public void execute(EventContainer e) {
				if(c.getItems().playerHasItem(8851, 20) && c.inCyclops) {
					c.sendMessage("You lose 20 tokens.");
					c.getItems().deleteItem2(8851, 20);
				} 
				if(!c.getItems().playerHasItem(8851, 20)) {
					c.inCyclops = false;
					c.sendMessage("You have ran out of tokens!");
					c.getPA().movePlayer(2846, 3541, 2);
					e.stop();
				}
			}
		};
	}

	private void executeCyclopsTimer(Player c) {
		c.inCyclops = true;
		EventManager.getSingleton().addEvent(cyclopsTimer(c), 60000);
	}*/
}