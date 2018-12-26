package org.mistex.system.packet.packets;

import org.mistex.game.world.player.Client;
import org.mistex.system.packet.PacketType;

public class IdleLogout implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		// if (!c.playerName.equalsIgnoreCase("End Game))
		// c.logout();
	}
}