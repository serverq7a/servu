package org.mistex.system.packet;

import org.mistex.game.world.player.Client;

public interface PacketType {
	
	public void processPacket(Client c, int packetType, int packetSize);
}
