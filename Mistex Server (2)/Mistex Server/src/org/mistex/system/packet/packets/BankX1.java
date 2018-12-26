package org.mistex.system.packet.packets;

import org.mistex.game.world.player.Client;
import org.mistex.game.world.shop.Shop;
import org.mistex.game.world.shop.ShopExecutor;
import org.mistex.system.packet.PacketType;

/**
 * Bank X Items
 **/
public class BankX1 implements PacketType {

	public static final int PART1 = 135;
	public static final int PART2 = 208;
	public int XremoveSlot, XinterfaceID, XremoveID, Xamount;

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		if (packetType == 135) {
			c.xRemoveSlot = c.getInStream().readSignedWordBigEndian();
			c.xInterfaceId = c.getInStream().readUnsignedWordA();
			c.xRemoveId = c.getInStream().readSignedWordBigEndian();
		}
		
		if (c.xInterfaceId == 3900) {
			if (c.shopIndex == 13) {
				boolean flag = false;
				for (int i = 0; i < Shop.skillCapes.length; i++) {
					if (c.xRemoveId == Shop.skillCapes[i] || c.xRemoveId - 1 == Shop.skillCapes[i]) {
						flag = true;
					}
				}
				if (!flag) return;
				Shop.buySkillCape(c, c.xRemoveId, 500);
				return;
			}
			ShopExecutor.buy(c, c.shopIndex, c.xRemoveSlot, 500);
			c.xRemoveSlot = 0;
			c.xInterfaceId = 0;
			c.xRemoveId = 0;
			return;
		}
		
		if (c.xInterfaceId == 3823) {
			ShopExecutor.sell(c, c.shopIndex, c.xRemoveSlot, 500);
			c.xRemoveSlot = 0;
			c.xInterfaceId = 0;
			c.xRemoveId = 0;
			return;
		}
		
		if (packetType == PART1) {
			synchronized (c) {
				c.getOutStream().createFrame(27);
			}
		}

	}
}
