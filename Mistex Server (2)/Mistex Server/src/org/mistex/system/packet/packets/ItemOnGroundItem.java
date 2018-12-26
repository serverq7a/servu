package org.mistex.system.packet.packets;

import org.mistex.game.Mistex;
import org.mistex.game.MistexUtility;
import org.mistex.game.world.player.Client;
import org.mistex.system.packet.PacketType;

public class ItemOnGroundItem implements PacketType {

	@SuppressWarnings("unused")
	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int a1 = c.getInStream().readSignedWordBigEndian();
		int itemUsed = c.getInStream().readSignedWordA();
		int groundItem = c.getInStream().readUnsignedWord();
		int gItemY = c.getInStream().readSignedWordA();
		int itemUsedSlot = c.getInStream().readSignedWordBigEndianA();
		int gItemX = c.getInStream().readUnsignedWord();
        if(!c.getItems().playerHasItem(itemUsed, 1, itemUsedSlot)) {
            return;
        }
        if(!Mistex.itemHandler.itemExists(groundItem, gItemX, gItemY)) {
            return;
        }
		switch (itemUsed) {

		default:
			if (c.playerRights == 3)
				MistexUtility.println("ItemUsed " + itemUsed + " on Ground Item "
						+ groundItem);
			break;
		}
	}

}
