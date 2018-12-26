package org.mistex.system.packet.packets;

import org.mistex.game.Mistex;
import org.mistex.game.world.event.CycleEvent;
import org.mistex.game.world.event.CycleEventContainer;
import org.mistex.game.world.event.CycleEventHandler;
import org.mistex.game.world.player.Client;
import org.mistex.system.packet.PacketType;

public class MagicOnFloorItems implements PacketType {

	@SuppressWarnings("unused")
	@Override
	public void processPacket(final Client c, int packetType, int packetSize) {
		final int itemY = c.getInStream().readSignedWordBigEndian();
		final int itemId = c.getInStream().readUnsignedWord();
		final int itemX = c.getInStream().readSignedWordBigEndian();
		final int spellId = c.getInStream().readUnsignedWordA();

		if (!Mistex.itemHandler.itemExists(itemId, itemX, itemY)) {
			c.stopMovement();
			return;
		}
		
		c.usingMagic = true;
		if (!c.getCombat().checkMagicReqs(51)) {
			c.stopMovement();
			return;
		}

		if (c.goodDistance(c.getX(), c.getY(), itemX, itemY, 12)) {
			int offY = (c.getX() - itemX) * -1;
			int offX = (c.getY() - itemY) * -1;
			c.turnPlayerTo(itemX, itemY);
			
			c.startAnimation(c.MAGIC_SPELLS[51][2]);
			c.gfx100(c.MAGIC_SPELLS[51][3]);
			c.getPA().createPlayersStillGfx(144, itemX, itemY, 0, 72);
			c.getPA().createPlayersProjectile(c.getX(), c.getY(), offX, offY,50, 70, c.MAGIC_SPELLS[51][4], 50, 10, 0, 50);
			c.getPA().addSkillXP(c.MAGIC_SPELLS[51][7], 6);
			c.getPA().refreshSkill(6);
			c.stopMovement();
			
			CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
				@Override
				public void execute(CycleEventContainer container) {
					container.stop();
				}

				@Override
				public void stop() {
					if (c.usingMagic) {
			            c.usingMagic = false;
			            if (Mistex.itemHandler.itemExists(itemId, itemX, itemY)) {
			                Mistex.itemHandler.removeGroundItem(c, itemId, itemX, itemY, true);
			            }
			        }
				}
			}, 2);
		}
	}
}
