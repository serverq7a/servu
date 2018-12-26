package org.mistex.system.packet.packets;

import org.mistex.game.world.content.skill.crafting.JewelryMaking;
import org.mistex.game.world.content.skill.smithing.Smithing;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.shop.ShopExecutor;
import org.mistex.system.packet.PacketType;

public class RemoveItem implements PacketType {

    @Override
    public void processPacket(Client c, int packetType, int packetSize) {
        int interfaceId = c.getInStream().readUnsignedWordA();
        int removeSlot = c.getInStream().readUnsignedWordA();
        int removeId = c.getInStream().readUnsignedWordA();
        if (c.playerRights == 3) {
            c.sendMessage("Interface: " + interfaceId + ", " + removeId + ", " + removeSlot);
        }
        
        switch (interfaceId) {
        case 7423:
            if (c.occupied[removeSlot] == true && c.storeditems[removeSlot] == removeId) {
                if (c.amount[removeSlot] <= 1) {
                    c.getPA().Frame34(7423, -1, removeSlot, 1);
                }
                if (c.amount[removeSlot] > 1) {
                    c.getPA().Frame34(7423, removeId, removeSlot, c.amount[removeSlot] - 1);
                }
                c.getItems().addItem(removeId, 1);
                c.amount[removeSlot] -= 1;
                if (c.amount[removeSlot] < 1) {
                    c.occupied[removeSlot] = false;
                    c.storeditems[removeSlot] = 0;
                    c.totalstored -= 1;
                }
                c.getItems().resetTempItems();
                c.getBank().refreshBank();
            }
            break;

        case 4233:
            JewelryMaking.mouldRing(c, removeId, 1);
            break;
        case 4239:
            JewelryMaking.mouldNecklace(c, removeId, 1);
            break;
        case 4245:
            JewelryMaking.mouldAmulet(c, removeId, 1);
            break;

        case 1688:
            if (c.inTrade) {
                c.getTrade().declineTrade(true);
                return;
            }
            if (c.inBoxingArena) {
            	c.sendMessage("You can not do that!");
            	return;
            }
            if (c.inWeaponGame()) {
                c.sendMessage("<col=0079AD>You may not remove your weapon in this minigame!");
                return;
            }
            c.getItems().removeItem(removeId, removeSlot);

            break;

        case 5064:
        	if (!c.usingBoB) {
            if (c.getPriceChecker().isActive()) {
                c.getPriceChecker().depositItem(removeId, 1);
                break;
            }
            c.getBank().deposit(removeId, 1, removeSlot);
			} else {
				c.getBOB().deposit(removeId, 1);
			}
            break;
    	case 2700: // Bob Interface
			if (c.inWild() || c.inFightCaves() || c.inFightPits()
					|| c.inPcGame() || c.duelStatus > 3) {
				return;
			}
			if (c.usingBoB) {
				c.getBOB().withdraw(removeSlot, removeId, 1);
			}
			break;


        case 50063:
            c.getBank().withdraw(removeId, 1);
            break;

        case 3900:
            if (c.inTrade) {
                c.getTrade().declineTrade(true);
                return;
            }
            ShopExecutor.buyValue(c, c.shopIndex, removeSlot);
            break;

        case 3823:
            if (c.inTrade) {
                c.getTrade().declineTrade(true);
                return;
            }
            ShopExecutor.sellValue(c, c.shopIndex, removeSlot);
            break;

        case 3322:
            //if (c.inTrade) {
            //c.getTradeAndDuel().declineTrade(true);
            //return;
            //}
            if (!c.canOffer) {
                return;
            }
            if (c.duelStatus <= 0) {
                c.getTrade().tradeItem(removeId, removeSlot, 1);
            } else {
                c.getDuel().stakeItem(removeId, removeSlot, 1);
            }
            break;

        case 3415:
            /*if (c.inTrade) {
                c.getTradeAndDuel().declineTrade(true);
                return;
            }*/
            if (!c.canOffer) {
                return;
            }
            if (c.duelStatus <= 0) {
                c.getTrade().fromTrade(removeId, removeSlot, 1);
            }
            break;

        case 6669:
            if (c.inTrade) {
                c.getTrade().declineTrade(true);
                return;
            }
            c.getDuel().fromDuel(removeId, removeSlot, 1);
            break;

        case 1119:
        case 1120:
        case 1121:
        case 1122:
        case 1123:
            Smithing.readInput(c, removeId, 1);
            break;
        }
    }

}