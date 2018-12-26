package org.mistex.system.packet.packets;

import org.mistex.game.world.content.skill.crafting.JewelryMaking;
import org.mistex.game.world.content.skill.smithing.Smithing;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.shop.Shop;
import org.mistex.game.world.shop.ShopExecutor;
import org.mistex.system.packet.PacketType;

public class Bank10 implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int interfaceId = c.getInStream().readUnsignedWordBigEndian();
		int removeId = c.getInStream().readUnsignedWordA();
		int removeSlot = c.getInStream().readUnsignedWordA();

		switch (interfaceId) {

		case 4233:
			JewelryMaking.mouldRing(c, removeId, 10);
			break;
		case 4239:
			JewelryMaking.mouldNecklace(c, removeId, 10);
			break;
		case 4245:
			JewelryMaking.mouldAmulet(c, removeId, 10);
			break;

		case 43933:
			c.getPriceChecker().withdrawItem(removeId, removeSlot, 10);
			break;
		case 2700:
			if (c.usingBoB) {
				c.getBOB().withdraw(removeSlot, removeId, 10);
			}
			break;
		case 7423:
			if (!c.usingBoB) {
			if (c.inTrade) {
				c.getTrade().declineTrade(true);
			}
			if(c.storing){
				return;
			}
			c.getBank().deposit(removeId, removeSlot, 10);
			c.getItems().resetItems(7423);
			} else {
				c.getBOB().deposit(removeId, 10);
			}
			break;

		case 3900:
			if (c.inTrade) {
				c.getTrade().declineTrade(true);
			}
			
			if (c.shopIndex == 13) {
				boolean flag = false;
				for (int i = 0; i < Shop.skillCapes.length; i++) {
					if (removeId == Shop.skillCapes[i] || removeId - 1 == Shop.skillCapes[i]) {
						flag = true;
					}
				}
				if (!flag) return;
				Shop.buySkillCape(c, removeId, 5);
				return;
			}

			ShopExecutor.buy(c, c.shopIndex, removeSlot, 5);
			break;

		case 3823:
            if(!c.getItems().playerHasItem(removeId))
                return;
			if (c.inTrade) {
				c.getTrade().declineTrade(true);
			}
			ShopExecutor.sell(c, c.shopIndex, removeSlot, 5);
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
			// if (c.isChecking) {
			// PriceChecker.depositItem(removeId, 10);
			// break;
			// }
			c.getBank().deposit(removeId, 10, removeSlot);
			} else {
				c.getBOB().deposit(removeId, 10);
			}
			break;

		case 50063:
			c.getBank().withdraw(removeId, 10);
			break;

		case 3322:
			if (c.duelStatus <= 0) {
				c.getTrade().tradeItem(removeId, removeSlot, 10);
			} else {
				c.getDuel().stakeItem(removeId, removeSlot, 10);
			}
			break;

		case 3415:
			if (c.duelStatus <= 0) {
				c.getTrade().fromTrade(removeId, removeSlot, 10);
			}
			break;

		case 6669:
			c.getDuel().fromDuel(removeId, removeSlot, 10);
			break;

		case 1119:
		case 1120:
		case 1121:
		case 1122:
		case 1123:
			Smithing.readInput(c, removeId, 10);
			break;
		}
	}

}