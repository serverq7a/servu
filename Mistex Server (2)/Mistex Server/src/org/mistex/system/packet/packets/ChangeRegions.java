package org.mistex.system.packet.packets;

import org.mistex.game.Mistex;
import org.mistex.game.world.player.Client;
import org.mistex.system.packet.PacketType;

public class ChangeRegions implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		Mistex.objectHandler.updateObjects(c);
		Mistex.itemHandler.reloadItems(c);
		Mistex.objectManager.loadObjects(c);
		c.getPA().removeObjects();
		c.getPA().castleWarsObjects();

		c.saveFile = true;

		if (c.skullTimer > 0) {
			c.isSkulled = true;
			c.headIconPk = 0;
			c.getPA().requestUpdates();
		}
		
      
        

	}

}
