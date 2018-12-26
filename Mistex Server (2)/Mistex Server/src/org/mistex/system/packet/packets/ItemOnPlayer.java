package org.mistex.system.packet.packets;

import org.mistex.game.MistexUtility;
import org.mistex.game.world.World;
import org.mistex.game.world.content.skill.slayer.DuoSlayer;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.item.Item;
import org.mistex.system.packet.PacketType;

public class ItemOnPlayer implements PacketType {

    private static final int[] CRACKER_ITEMS = {
        1038, 1040, 1042, 1044, 1046, 1048
    };

    @Override
    public void processPacket(Client c, int packetType, int packetSize) {
        c.getInStream().readUnsignedWordBigEndianA();
        int playerIndex = c.getInStream().readUnsignedWord();
        int item = c.getInStream().readUnsignedWord();
        int slot = c.getInStream().readUnsignedWordBigEndian();

        if (playerIndex > World.players.length) {
            return;
        }

        if (World.players[playerIndex] == null) {
            return;
        }

        if (!c.getItems().playerHasItem(item, 1, slot)) {
            return;
        }

        Client other = (Client) World.players[playerIndex];
        c.turnPlayerTo(other.absX, other.absY);
        switch (item) {

        case 962:
            if (other.getItems().freeSlots() <= 0) {
                c.sendMessage("Other player does not have enough free inventory space.");
                return;
            }
            if (c.getItems().playerHasItem(962)) {
            	c.getItems().deleteItem2(962, 1);
            	int player = MistexUtility.random(1);
            	int prize = CRACKER_ITEMS[(int)(Math.random() * CRACKER_ITEMS.length)];
            	(player == 0 ? c : other).getItems().addItem(prize, 1);
            	(player == 0 ? c : other).sendMessage("You pull the cracker and win a " + Item.getItemName(prize) + ".");
            	(player == 0 ? other : c).sendMessage("You pull the cracker and win nothing. The other player won a " + Item.getItemName(prize) + ".");
            }
            break;

        case 4155:
            if (c.getItems().playerHasItem(4155)) {
                c.lastSlayerInvite = System.currentTimeMillis();
                    //if (other.dialogueAction > 0) {
                       // c.sendMessage("This player is currently busy.");
                       // return;
                  //  }
                    if (c == null || other == null) {
                        return;
                    }
                    if (other.duoPartner == null)
                        DuoSlayer.getInstance().invite(c, other);
                    else if (other.getDuoPartner() == c)
                        c.sendMessage("You are already doing a slayer duo with " + other.playerName + ".");
                    else
                        c.sendMessage("Your partner already has a slayer dual partner.");
            }
            break;
            
            
        default:
            c.sendMessage("Nothing interesting happens.");
            break;
        }
    }

}