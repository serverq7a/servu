package org.mistex.system.packet.packets;

import org.mistex.game.world.content.ReportHandler;
import org.mistex.game.world.player.Client;
import org.mistex.system.packet.PacketType;

public class ReportPacket implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		try {
			ReportHandler.handleReport(c);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}