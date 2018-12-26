package org.mistex.system.packet.packets;

import org.mistex.game.world.player.Client;
import org.mistex.system.packet.PacketType;

public class SkillGoals implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		c.sendMessage("Saving skill goals is being reworked.");
	}
}