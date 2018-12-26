package org.mistex.system.packet.packets;

import org.mistex.game.world.content.CrystalChest;
import org.mistex.game.world.content.skill.crafting.JewelryMaking;
import org.mistex.game.world.content.skill.crafting.LeatherMaking;
import org.mistex.game.world.content.skill.crafting.TipMaker;
import org.mistex.game.world.content.skill.firemaking.Firemaking;
import org.mistex.game.world.content.skill.herblore.Herblore;
import org.mistex.game.world.event.CycleEvent;
import org.mistex.game.world.event.CycleEventContainer;
import org.mistex.game.world.event.CycleEventHandler;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.item.UseItem;
import org.mistex.system.packet.PacketType;

public class ItemOnItem implements PacketType {

    @Override
    public void processPacket(final Client c, int packetType, int packetSize) {
        int usedWithSlot = c.getInStream().readUnsignedWord();
        int itemUsedSlot = c.getInStream().readUnsignedWordA();
        int useWith = c.playerItems[usedWithSlot] - 1;
        int itemUsed = c.playerItems[itemUsedSlot] - 1;
        if(!c.getItems().playerHasItem(useWith, 1, usedWithSlot) || !c.getItems().playerHasItem(itemUsed, 1, itemUsedSlot)) {
            return;
        }
        UseItem.ItemonItem(c, itemUsed, useWith);
        /* Crystal Key */
		if (itemUsed == CrystalChest.toothHalf() && useWith == CrystalChest.loopHalf() || itemUsed == CrystalChest.loopHalf() && useWith == CrystalChest.toothHalf()) {
			CrystalChest.makeKey(c);
		}
        
        /* Firemaking */
        if (Firemaking.playerLogs(c, itemUsed, useWith)) {
            Firemaking.grabData(c, itemUsed, useWith);
        }
        /* Herblore */
        if (Herblore.mixPotion(c, itemUsed, useWith)) {
            Herblore.setUpUnfinished(c, itemUsed, useWith);
        } else if (Herblore.mixPotionNew(c, itemUsed, useWith)) {
            Herblore.setUpPotion(c, itemUsed, useWith);
        }

        /* Crafting */
        TipMaker.makeTip(c, useWith, itemUsed);
        if (itemUsed == 1759 && useWith == 1759) {
            JewelryMaking.stringAmulet(c, itemUsed, useWith);
        } else if (itemUsed == 1733 && useWith == 1733) {
            LeatherMaking.craftLeatherDialogue(c, itemUsed, useWith);
        }
        
        /* Onyx Making */
        if (itemUsed == 1759 && useWith == 6573 || itemUsed == 6573 && useWith == 1759) {      	
        	if (c.getItems().playerHasItem(1759, 1) && c.getItems().playerHasItem(6573, 1)) {
	        	c.getItems().deleteItem(1759, 1);
	        	c.getItems().deleteItem(6573, 1);
	        	c.getItems().addItem(6581, 1);
	        	c.getDH().OneItemDialogue("You successfully create an onyx amulet!", 6581);	
        	} else {
        		//c.sendMessage("Fuck you.");
        	}
        }
        
        /* Troll Making */
        if ((itemUsed == 229 && useWith == 311) || (itemUsed == 311 && useWith == 229)) { 
        	if (c.inBankZone() && c.AcQuest == 7) {
        	c.getItems().deleteItem(229, 1);
        	c.getItems().deleteItem(311, 1);
        	c.getItems().addItem(3265, 1);
        	 c.getDH().OneItemDialogue("You successfully make a troll potion", 3265);
        	}       	
        }

        /* Godsword Making */
		if (itemUsed >= 11710 && itemUsed <= 11714 && useWith >= 11710 && useWith <= 11714) {
			if (c.getItems().hasAllShards()) {
				c.getItems().makeBlade();
			}		
		}
		if (c.getItems().isHilt(itemUsed) || c.getItems().isHilt(useWith)) {
			int hilt = c.getItems().isHilt(itemUsed) ? itemUsed : useWith;
			int blade = c.getItems().isHilt(itemUsed) ? useWith : itemUsed;
			if (blade == 11690) {
				c.getItems().makeGodsword(hilt);
			}
		}
        
        /* DFS Making */
        if ((itemUsed == 11286 && useWith == 1540) || (itemUsed == 1540 && useWith == 11286)) {
            if (System.currentTimeMillis() - c.itemMakingDelay >= 5000) {
                c.sendMessage("You attempt to put the two items together...");
                CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
                    public void execute(CycleEventContainer p) {
                        if (System.currentTimeMillis() - c.itemMakingDelay >= 5000) {
                            if (c.getItems().playerHasItem(11286) && c.getItems().playerHasItem(1540)) {
                                c.itemMakingDelay = System.currentTimeMillis();
                                c.getItems().deleteItem(11286, 1);
                                c.getItems().deleteItem(1540, 1);
                                c.getItems().addItem(11283, 1);
                                c.sendMessage("You successfully combine the draconic visage with the anti-dragon shield.");
                                c.getDH().OneItemDialogue("You successfully create a dragon fire shield!", 11283);
                                p.stop();
                            } else {
                                c.sendMessage("Something went wrong!");
                                p.stop();
                            }
                        }
                    }@
                    Override
                    public void stop() {

                    }
                }, 5);
            } else {}
        }

    }

}