package org.mistex.system.packet.packets;

import org.mistex.game.world.World;
import org.mistex.game.world.player.Client;
import org.mistex.system.packet.PacketType;

/**
 * Challenge Player
 **/
public class ChallengePlayer implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		switch (packetType) {
		case 128:
			int answerPlayer = c.getInStream().readUnsignedWord();
			if (World.players[answerPlayer] == null) {
				return;
			}
			
			if (c.getRights().isAdminstrator()) {
				c.sendMessage("Dueling has been disabled for Administrators.");
				return;
			}

			if (c.inDuelArena()) {
				if (c.arenas() || c.duelStatus == 5) {
					c.sendMessage("You can't challenge inside the arena!");
					return;
				}

				c.getDuel().requestDuel(answerPlayer);
				break;
			}
		}
	}
}
