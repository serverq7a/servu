package org.mistex.system.packet.packets;

import org.mistex.game.world.content.gambling.DiceHandler;
import org.mistex.game.world.content.skill.runecrafting.Pouches;
import org.mistex.game.world.player.Client;
import org.mistex.system.packet.PacketType;


public class WearItem implements PacketType {

	@SuppressWarnings("unused")
	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		c.wearId = c.getInStream().readUnsignedWord();
		c.wearSlot = c.getInStream().readUnsignedWordA();
		c.interfaceId = c.getInStream().readUnsignedWordA();

        if(!c.getItems().playerHasItem(c.wearId, 1, c.wearSlot)) {
        	return;
        }
        
		int oldCombatTimer = c.attackTimer;
		if (c.playerIndex > 0 || c.npcIndex > 0)
			c.getCombat().resetPlayerAttack();
		switch (c.wearId ) {
		case 18346:
			c.getPA().startTeleport(1787, 5342, 0, "modern");
			c.sendMessage("You were teleported to the frost dragon area.");
			c.wildLevel = 50;
			return;
		case 20764:
		case 20763:
			if (c.gameTime < 41353) {
				c.sendMessage("You must be a veteran to wear this!");
				return;
			}
			break;
		case 2653:
		case 2655:
		case 2657:
		case 2659:
		case 3478:
			c.sendMessage("Due to a bug, this item is currently unable to equipt.");
			return;
		}
		if (c.wearId == 7478) {
			c.sendMessage("Shine bright like a diamond.");
			return;
		}
		if (c.wearId == 4155) {
			return;
		}
		for (int i = 0; i < Pouches.pouchData.length; i++) {
			if (c.wearId == Pouches.pouchData[i][0]) {
				Pouches.emptyPouch(c, c.wearId);
				return;
			}
		}
		

		if (c.wearId > DiceHandler.DICE_BAG && c.wearId <= 15100) {
			DiceHandler.rollDice(c, c.wearId, true);
			return;
		}
		if (c.inTrade) {
			c.sendMessage("You can not wear items while in trade!");
			return;
		}
		c.getItems().wearItem(c.wearId, c.wearSlot);
	}

}
