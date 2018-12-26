package org.mistex.system.packet.packets;

import org.mistex.game.Mistex;
import org.mistex.game.world.event.CycleEvent;
import org.mistex.game.world.event.CycleEventContainer;
import org.mistex.game.world.event.CycleEventHandler;
import org.mistex.game.world.player.Client;
import org.mistex.system.packet.PacketType;


public class PickupItem implements PacketType {

	@Override
	public void processPacket(final Client c, int packetType, int packetSize) {
		c.pItemY = c.getInStream().readSignedWordBigEndian();
		c.pItemId = c.getInStream().readUnsignedWord();
		c.pItemX = c.getInStream().readSignedWordBigEndian();
		if (Math.abs(c.getX() - c.pItemX) > 25|| Math.abs(c.getY() - c.pItemY) > 25) {
			c.resetWalkingQueue();
			return;
		}
		if (c.playerIsFiremaking) {
			return;
		}
	    if(!Mistex.itemHandler.itemExists(c.pItemId, c.pItemX, c.pItemY)) {
	        c.stopMovement();
	        return;
	    }
		if (c.inBoxingArena) {
			c.sendMessage("You can not do that!");
			return;
		}
		c.getCombat().resetPlayerAttack();
		if (c.getX() == c.pItemX && c.getY() == c.pItemY) {
			Mistex.itemHandler.removeGroundItem(c, c.pItemId, c.pItemX,c.pItemY, true);
		} else {
			c.walkingToItem = true;
			CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
				@Override
				public void execute(CycleEventContainer container) {
					if (!c.walkingToItem)
						container.stop();
					if (c.getX() == c.pItemX && c.getY() == c.pItemY) {
						Mistex.itemHandler.removeGroundItem(c, c.pItemId,c.pItemX, c.pItemY, true);
						container.stop();
					}
				}

				@Override
				public void stop() {
					c.walkingToItem = false;
				}
			}, 1);
		}

	}

}
