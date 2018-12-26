package org.mistex.system.packet.packets;

import org.mistex.game.world.player.Client;
import org.mistex.system.packet.PacketType;

/**
 * Move Items
 **/
public class MoveItems implements PacketType {

    @
    Override
    public void processPacket(Client c, int packetType, int packetSize) {
        int somejunk = c.getInStream().readUnsignedWordA(); // junk
        int itemFrom = c.getInStream().readUnsignedWordA(); // slot1
        int itemTo = (c.getInStream().readUnsignedWordA() - 128); // slot2
//        c.sendMessage("junk: " + somejunk);
        
        //if (c.playerItems[itemFrom] != -1) {
        	//if (c.playerItems[itemFrom] <= 0 && c.playerItems[itemTo] <= 0)
        	//	return;        	
     //   }
        
        if (c.isBanking) {
        	c.sendMessage("Bank tabs are currently disabled.");
        	return;
        }

        /*if (somejunk >= Bank.BASE_ID + 50 && somejunk <= Bank.BASE_ID + 58) {
            BankItem item = c.getBank().getCurrentBankTab().itemForIndex(itemFrom);
            if (item == null) {
            	return;
            }
            c.getBank().itemToTab(item, somejunk - Bank.BASE_ID + 5);
        }*/
       
        if (c.inTrade) {
            c.getTrade().declineTrade();
            return;
        }
        if (c.tradeStatus == 1) {
            c.getTrade().declineTrade();
            return;
        }
        if (c.duelStatus == 1) {
            c.getDuel().declineDuel();
            return;
        }
        
        c.getItems().moveItems(itemFrom, itemTo, somejunk);
    }
}