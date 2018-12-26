package org.mistex.system.packet.packets;

import org.mistex.game.MistexConfiguration;
import org.mistex.game.world.player.Client;
import org.mistex.system.packet.PacketType;

public class Trade implements PacketType {

	public void processPacket(Client c, int packetType, int packetSize) {
		int tradeId = c.getInStream().readSignedWordBigEndian();
		c.getPA().resetFollow();
		
        if (c.getRights().isAdminstrator() && !MistexConfiguration.ADMIN_CAN_TRADE) {
        	c.sendMessage("Trading has been disabled for adminstrators.");
        	return;
        }
		if (c.craftingTeletabs){
			return;
		}
		if (c.inBoxingArena) {
			c.sendMessage("You may not trade while in the boxing arena");
			return;
		}
		
		if (c.isDoingTutorial){
			return;
		}
		
		if (c.gameTime < 15) {
			c.sendMessage("You can not do this yet! You are still a new player.");
			return;
		}
		
		if (c.arenas()) {
			c.sendMessage("You can\'t trade inside the arena!");
		} else {
			if (tradeId != c.playerId) {
				if (!c.inTrade) {
					c.getTrade().requestTrade(tradeId);
				}
			}
		}
	}
}
