package org.mistex.game.world.event.events;

import org.mistex.game.world.event.CycleEvent;
import org.mistex.game.world.event.CycleEventContainer;
import org.mistex.game.world.npc.NPCHandler;
import org.mistex.game.world.player.Client;

public class PlayerActionEvent extends CycleEvent {
	
	private final Client player;

	public PlayerActionEvent(final Client player) {
		this.player = player;
	}

	@Override
	public void execute(CycleEventContainer container) {
		if (player.clickObjectType > 0
				&& player.goodDistance(player.objectX + player.objectXOffset,
						player.objectY + player.objectYOffset, player.getX(),
						player.getY(), player.objectDistance)) {
			if (player.clickObjectType == 1) {
				player.getActions().firstClickObject(player.objectId,
						player.objectX, player.objectY);
			}
			if (player.clickObjectType == 2) {
				player.getActions().secondClickObject(player.objectId,
						player.objectX, player.objectY);
			}
			if (player.clickObjectType == 3) {
				player.getActions().thirdClickObject(player.objectId,
						player.objectX, player.objectY);
			}
		}

		if ((player.clickNpcType > 0)
				&& NPCHandler.npcs[player.npcClickIndex] != null) {
			if (player.goodDistance(player.getX(), player.getY(),
					NPCHandler.npcs[player.npcClickIndex].getX(),
					NPCHandler.npcs[player.npcClickIndex].getY(), 1)) {
				if (player.clickNpcType == 1) {
					player.turnPlayerTo(
							NPCHandler.npcs[player.npcClickIndex].getX(),
							NPCHandler.npcs[player.npcClickIndex].getY());
					NPCHandler.npcs[player.npcClickIndex]
							.facePlayer(player.playerId);
					player.getActions().firstClickNpc(player.npcType);
				}
				if (player.clickNpcType == 2) {
					player.turnPlayerTo(
							NPCHandler.npcs[player.npcClickIndex].getX(),
							NPCHandler.npcs[player.npcClickIndex].getY());
					NPCHandler.npcs[player.npcClickIndex]
							.facePlayer(player.playerId);
					player.getActions().secondClickNpc(player.npcType);
				}
				if (player.clickNpcType == 3) {
					player.turnPlayerTo(
							NPCHandler.npcs[player.npcClickIndex].getX(),
							NPCHandler.npcs[player.npcClickIndex].getY());
					NPCHandler.npcs[player.npcClickIndex]
							.facePlayer(player.playerId);
					player.getActions().thirdClickNpc(player.npcType);
				}
			}
		}
	}

	@Override
	public void stop() {}

}
