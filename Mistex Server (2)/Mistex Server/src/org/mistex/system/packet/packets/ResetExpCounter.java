package org.mistex.system.packet.packets;

import org.mistex.game.world.player.Client;
import org.mistex.system.packet.PacketType;

public class ResetExpCounter implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		c.counterxp = 0;
		c.getPA().sendSkillXP(0, 0);
	}
}