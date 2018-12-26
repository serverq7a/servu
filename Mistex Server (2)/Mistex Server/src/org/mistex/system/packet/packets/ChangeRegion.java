package org.mistex.system.packet.packets;

import org.mistex.game.Mistex;
import org.mistex.game.world.player.Client;
import org.mistex.system.packet.PacketType;

public class ChangeRegion implements PacketType {

    @Override
    public void processPacket(Client c, int packetType, int packetSize) {
        c.getPA().removeObjects();
        Mistex.objectManager.loadObjects(c);
     
    }
}