package org.mistex.system.packet.packets;

import org.mistex.game.world.player.Client;
import org.mistex.system.packet.PacketType;

public class BankX2 implements PacketType {
	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int Xamount = c.getInStream().readDWord();
        if (Xamount < 0) {
            Xamount = c.getItems().getItemAmount(c.xRemoveId);
        }
		if (Xamount == 0) {
			Xamount = 1;
		}	

		switch (c.xInterfaceId) {
		
		case 43933:
			//PriceChecker.withdrawItem(c, c.price[c.xRemoveSlot], c.xRemoveSlot,Xamount);
			break;
			
		case 2700:
			if (c.usingBoB) {
				c.getBOB().withdraw(c.xRemoveSlot, c.xRemoveId, Xamount);
			}
			break;
		
		case 5064:
	        if(!c.getItems().playerHasItem(c.xRemoveId, Xamount))
                return;
			if (!c.usingBoB) {
//			if (c.isChecking) {
//				PriceChecker.depositItem(c, Xamount, 10);
//	        }
			if(c.storing) {
				return;
			}
			c.getBank().deposit(c.playerItems[c.xRemoveSlot] - 1, Xamount, c.xRemoveSlot);
			} else {
				c.getBOB().deposit(c.xRemoveId, Xamount);
			}
			break;
			
		case 50063:
			c.getBank().withdraw(c.xRemoveId, Xamount);
			break;

		case 3322:
	        if(!c.getItems().playerHasItem(c.xRemoveId, Xamount))
                return;
			if (c.duelStatus <= 0) {
				c.getTrade().tradeItem(c.xRemoveId, c.xRemoveSlot, Xamount);
			} else {
				c.getDuel().stakeItem(c.xRemoveId, c.xRemoveSlot, Xamount);
			}
			break;

		case 3415:
			if (c.duelStatus <= 0) {
				c.getTrade().fromTrade(c.xRemoveId, c.xRemoveSlot,Xamount);
			}
			break;

		case 6669:
			c.getDuel().fromDuel(c.xRemoveId, c.xRemoveSlot, Xamount);
			break;
		}
	}
}