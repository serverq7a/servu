package org.mistex.system.packet.packets;

import org.mistex.game.MistexConfiguration;
import org.mistex.game.MistexUtility;
import org.mistex.game.world.PunishmentHandler;
import org.mistex.game.world.content.ReportHandler;
import org.mistex.game.world.player.Client;
import org.mistex.system.packet.PacketType;

public class Chat implements PacketType {

    @Override
    public void processPacket(Client c, int packetType, int packetSize) {
        c.setChatTextEffects(c.getInStream().readUnsignedByteS());
        c.setChatTextColor(c.getInStream().readUnsignedByteS());
        c.setChatTextSize((byte)(c.packetSize - 2));
        c.inStream.readBytes_reverseA(c.getChatText(), c.getChatTextSize(), 0);
        ReportHandler.addText(c.playerName, c.getChatText(), packetSize - 2);
        String term = MistexUtility.textUnpack(c.getChatText(), c.packetSize - 2).toLowerCase();
        
        if (c.advertiseDetection >= 3) {
            c.sendMessage("You have been muted!");
            PunishmentHandler.addNameToMuteList(c.playerName);
        }
        for (int i = 0; i < MistexConfiguration.illegalWords.length; i++) {
            if (term.contains(MistexConfiguration.illegalWords[i])) {
                c.advertiseDetection += 1;
               	c.getChatlog().logger("Regular", MistexUtility.textUnpack(c.getChatText(), packetSize-2));
                if (!PunishmentHandler.isMuted(c))
                	c.sendMessage("@red@[ Mistex ] Auto detected avertisement! "+c.advertiseDetection+" of 3 chances. Will result in mute.");
                return;
            }
        }
        if (!PunishmentHandler.isMuted(c)) {
            c.setChatTextUpdateRequired(true);
        } else if (PunishmentHandler.isMuted(c)) {
            c.sendMessage("You are muted! No one can hear you. Please apply on forums.");
            return;
        }
    }
}