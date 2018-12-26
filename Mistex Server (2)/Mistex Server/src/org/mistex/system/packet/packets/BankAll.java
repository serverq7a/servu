package org.mistex.system.packet.packets;

import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.bank.BankItem;
import org.mistex.game.world.player.item.GameItem;
import org.mistex.game.world.player.item.Item;
import org.mistex.game.world.shop.Shop;
import org.mistex.game.world.shop.ShopExecutor;
import org.mistex.system.packet.PacketType;

public class BankAll implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int removeSlot = c.getInStream().readUnsignedWordA();
		int interfaceId = c.getInStream().readUnsignedWord();
		int removeId = c.getInStream().readUnsignedWordA();

		switch (interfaceId) {	
		case 43933:

			c.getPriceChecker().withdrawItem(removeId, removeSlot, c.getPriceChecker().getItems().get(removeSlot).getAmount());
			break;

		
		case 3900:
			if (c.shopIndex == 13) {
				boolean flag = false;
				for (int i = 0; i < Shop.skillCapes.length; i++) {
					if (removeId == Shop.skillCapes[i] || removeId - 1 == Shop.skillCapes[i]) {
						flag = true;
					}
				}
				if (!flag) return;
				Shop.buySkillCape(c, removeId, 10);
				return;
			}
			
			ShopExecutor.buy(c, c.shopIndex, removeSlot, 10);
		break;
		
		case 3823:
            if(!c.getItems().playerHasItem(removeId))
                return;
		if(c.inTrade) {
			c.sendMessage("You can't sell items while trading!");
			return;
		}
		ShopExecutor.sell(c, c.shopIndex, removeSlot, 10);
		break;
		case 2700:
			if (c.usingBoB) {
				c.getBOB().withdraw(removeSlot, removeId, 28);
			}
			break;

			
		case 5064:
            if(!c.getItems().playerHasItem(removeId))
                return;
			if (!c.usingBoB) {
				if (c.inTrade) {
					c.sendMessage("You can't store items while trading!");
					return;
				}
				if(c.storing) {
					return;
				}
				if (Item.itemStackable[removeId]) {
					c.getBank().deposit(removeId, c.playerItemsN[removeSlot], removeSlot);
				} else {
					c.getBank().deposit(removeId, c.getItems().itemAmount(c.playerItems[removeSlot]), removeSlot);
				}
			} else {
				c.getBOB().deposit(removeId, 28);
			}
			break;

		case 50063:
			if(c.inTrade) {
				c.sendMessage("You can't store items while trading!");
				return;
			}
			if(c.storing){
				return;
			}
			BankItem bankItem = c.getBank().getBankItem(removeId + 1);
			c.getBank().withdraw(bankItem.getID() - 1, bankItem.getAmount());
			break;
			
			case 3322:
			if(c.duelStatus <= 0) { 
				if(Item.itemStackable[removeId]){
					c.getTrade().tradeItem(removeId, removeSlot, c.playerItemsN[removeSlot]);
		    	} else {
					c.getTrade().tradeItem(removeId, removeSlot, 28);  
				}
			} else {
				if(Item.itemStackable[removeId] || Item.itemIsNote[removeId]) {
					c.getDuel().stakeItem(removeId, removeSlot, c.playerItemsN[removeSlot]);
				} else {
					c.getDuel().stakeItem(removeId, removeSlot, 28);
				}
			}
			break;
			
			case 3415: 
			if(c.duelStatus <= 0) { 
				if(Item.itemStackable[removeId]) {
					for (GameItem item : c.getTrade().offeredItems) {
						if(item.id == removeId) {
							c.getTrade().fromTrade(removeId, removeSlot, c.getTrade().offeredItems.get(removeSlot).amount);
						}
					}
				} else {
					for (GameItem item : c.getTrade().offeredItems) {
						if(item.id == removeId) {
							c.getTrade().fromTrade(removeId, removeSlot, 28);
						}
					}
				}
            } 
			break;
			
			case 6669:
			if(Item.itemStackable[removeId] || Item.itemIsNote[removeId]) {
				for (GameItem item : c.getDuel().stakedItems) {
					if(item.id == removeId) {
						c.getDuel().fromDuel(removeId, removeSlot, c.getDuel().stakedItems.get(removeSlot).amount);
					}
				}
						
			} else {
				c.getDuel().fromDuel(removeId, removeSlot, 28);
			}
			break;

		}
	}

}
