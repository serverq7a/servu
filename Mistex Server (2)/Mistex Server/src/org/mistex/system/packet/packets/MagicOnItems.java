package org.mistex.system.packet.packets;

import org.mistex.game.world.player.Client;
import org.mistex.system.packet.PacketType;

public class MagicOnItems implements PacketType {

	@SuppressWarnings("unused")
	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int slot = c.getInStream().readSignedWord();
		int itemId = c.getInStream().readSignedWordA();
		int junk = c.getInStream().readSignedWord();
		int spellId = c.getInStream().readSignedWordA();
		if (c.playerRights == 3) {
			c.sendMessage("Slot: "+slot+" | itemId: "+itemId+" | spellId "+spellId);
		}
        if(!c.getItems().playerHasItem(itemId, 1, slot))
            return;
		c.usingMagic = true;
		c.getPA().magicOnItems(slot, itemId, spellId);
		c.usingMagic = false;

	}

}
