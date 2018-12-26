package org.mistex.game.world.content.dialogue;

import org.mistex.game.world.player.Client;
import org.mistex.system.packet.PacketType;

/**
 * Dialogue
 **/
public class Dialogue implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {

		if (c.nextChat > 0) {
			c.getDH().sendDialogues(c.nextChat, c.talkingNpc);
		} else {
			c.getDH().sendDialogues(0, -1);
		}

	}

}
