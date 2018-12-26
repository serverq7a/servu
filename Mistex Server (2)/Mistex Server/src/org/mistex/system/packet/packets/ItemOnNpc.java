package org.mistex.system.packet.packets;

import org.mistex.game.Mistex;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.item.UseItem;
import org.mistex.system.packet.PacketType;

public class ItemOnNpc implements PacketType {

	@SuppressWarnings("static-access")
	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int itemId = c.getInStream().readSignedWordA();
		int i = c.getInStream().readSignedWordA();
		int slot = c.getInStream().readSignedWordBigEndian();
		int npcId = Mistex.npcHandler.npcs[i].npcType;
        if(!c.getItems().playerHasItem(itemId, 1, slot)) {
            return;
        }
		UseItem.ItemonNpc(c, itemId, npcId, slot);
	}
}