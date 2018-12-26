package org.mistex.system.packet.packets;

import org.mistex.game.world.World;
import org.mistex.game.world.player.Client;
import org.mistex.system.packet.PacketType;

public class FollowPlayer implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int followPlayer = c.getInStream().readUnsignedWordBigEndian();
		if (World.players[followPlayer] == null) {
			return;
		}
		if (c.craftingTeletabs) {
			return;
		}
		if (c.isDoingTutorial) {
			return;
		}
		if (c.inBoxingArena) {
			c.sendMessage("You may not follow while in the boxing arena");
			return;
		}
		c.playerIndex = 0;
		c.npcIndex = 0;
		c.mageFollow = false;
		c.usingBow = false;
		c.usingRangeWeapon = false;
		c.followDistance = 1;
		c.followId = followPlayer;
	}
}