package org.mistex.system.packet.packets;

import org.mistex.game.world.Position;
import org.mistex.game.world.content.CrystalChest;
import org.mistex.game.world.content.skill.crafting.JewelryMaking;
import org.mistex.game.world.content.skill.prayer.Altar;
import org.mistex.game.world.content.skill.prayer.Constants;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.item.UseItem;
import org.mistex.system.packet.PacketType;

public class ItemOnObject implements PacketType {

	@Override
	public void processPacket(final Client c, final int packetType,final int packetSize) {

		c.getInStream().readUnsignedWord();
		final int objectId = c.getInStream().readSignedWordBigEndian();
		final int objectY = c.getInStream().readSignedWordBigEndianA();
		c.getInStream().readUnsignedWord();
		final int objectX = c.getInStream().readSignedWordBigEndianA();
		final int itemId = c.getInStream().readUnsignedWord();
		if (!c.getItems().playerHasItem(itemId, 1)) {
			return;
		}
		c.objectX = objectX;
		c.objectY = objectY;
		UseItem.ItemonObject(c, objectId, objectX, objectY, itemId);
		c.turnPlayerTo(objectX, objectY);

		switch (objectId) {
        case 6:
            if (itemId == 2) {
                if (c.getCannon() != null) {
                    c.getCannon().giveBalls(new Position(objectX, objectY, c.heightLevel));
                }
            }
            break;
		case 172:
			if(itemId == CrystalChest.KEY)
				CrystalChest.searchChest(c, objectId, objectX, objectY);
			break;
			
		case 409:
			if (Constants.playerBones(c, itemId)) {
				Altar.bonesOnAltar(c, itemId);
			}
			break;

		case 3044:
			if (itemId == JewelryMaking.GOLD_BAR) {
				JewelryMaking.jewelryInterface(c);
			}
			break;

		}
	}

}
