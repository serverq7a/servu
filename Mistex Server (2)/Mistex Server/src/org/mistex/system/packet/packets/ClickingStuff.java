package org.mistex.system.packet.packets;

import org.mistex.game.MistexUtility;
import org.mistex.game.world.World;
import org.mistex.game.world.content.skill.SkillHandler;
import org.mistex.game.world.player.Client;
import org.mistex.system.packet.PacketType;


public class ClickingStuff implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		if (c.inTrade) {
			if (!c.acceptedTrade) {
				MistexUtility.println("trade reset");
				c.getTrade().declineTrade();
			}
		}
        if (c.isBanking)
            c.isBanking = false;
        if(c.isShopping)
            c.isShopping = false;
        if(c.openDuel && c.duelStatus >= 1 && c.duelStatus <= 4) {
            Client o = (Client) World.players[c.duelingWith];
            if(o != null)
                o.getDuel().declineDuel();
            c.getDuel().declineDuel();
        }
        if(c.duelStatus == 6)
            c.getDuel().claimStakedItems();
        if (c.inTrade) {
            if(!c.acceptedTrade) {
                c.getTrade().declineTrade();
            }
        }

		if (c.isSkilling)
			SkillHandler.resetPlayerVariables(c);
		
		Client o = (Client) World.players[c.duelingWith];
		if (o != null) {
			if (c.duelStatus >= 1 && c.duelStatus <= 4) {
				c.getDuel().declineDuel();
				o.getDuel().declineDuel();
			}
		}

		if (c.duelStatus == 6) {
			c.getDuel().claimStakedItems();
		}

	}

}
