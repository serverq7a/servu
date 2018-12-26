package org.mistex.system.packet.packets;

import org.mistex.game.Mistex;
import org.mistex.game.MistexConfiguration;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.PlayerSave;
import org.mistex.system.packet.PacketType;

public class DropItem implements PacketType {

	@Override
	public void processPacket(final Client c, final int packetType, final int packetSize) {
		final int itemId = c.getInStream().readUnsignedWordA();
		c.getInStream().readUnsignedByte();
		c.getInStream().readUnsignedByte();
		final int slot = c.getInStream().readUnsignedWordA();

		if (slot < c.playerItems.length && itemId != c.playerItems[slot] - 1) {
			return;
		}

		if (System.currentTimeMillis() - c.alchDelay < 1800) {
			return;
		}
		if (itemId == 4045) {
			c.startAnimation(827);
			c.getItems().deleteItem(itemId, slot, c.playerItemsN[slot]);
			c.handleHitMask(15);
			c.dealDamage(15);
			c.getPA().refreshSkill(3);
			c.forcedText = "Ow! That really hurt!";
			c.forcedChatUpdateRequired = true;
			c.updateRequired = true;
			return;
		}
		if (System.currentTimeMillis() - c.itemMakingDelay < 0) {
			c.sendMessage("Sorry that will not work!");
			return;
		}
		if (c.gameTime < 15) {
			c.sendMessage("You can not do this yet! You are still a new player.");
			return;
		}
		if (c.getRights().isAdminstrator() && !MistexConfiguration.ADMIN_DROP_ITEMS) {
			c.sendMessage("Dropping has been disabled for adminstrators.");
			return;
		}
		if (c.doingDungeoneering) {
			c.sendMessage("<col=8650ac>You can't drop items while dungeoneering!");
			return;
		}
		if (c.isDead) {
			c.sendMessage("You can't drop items while dead!");
			return;
		}
		if (c.arenas()) {
			c.sendMessage("You can't drop items inside the arena!");
			return;
		}
		if (c.inTrade) {
			c.sendMessage("You can't drop items while trading!");
			return;
		}
		for (final int i : MistexConfiguration.UNDROPPABLE_ITEMS) {
			if (itemId == i) {
				c.sendMessage("That item may not be dropped!");
				return;

			}
		}
		if (c.underAttackBy > 0 && c.inWild()) {
			if (c.getItems().getItemShopValue(itemId) > 5000) {
				c.sendMessage("You may not drop items worth more than 5,000 while in combat.");
				return;
			}
		}
		if (!c.getItems().playerHasItem(itemId)) {
			return;
		}
		if (c.playerRights == 3) {
			c.sendMessage("The item Id you dropped is: " + itemId);
		}
		Mistex.itemHandler.createGroundItem(c, itemId, c.getX(), c.getY(), c.playerItemsN[slot], c.getId());
		c.getItems().deleteItem(itemId, slot, c.playerItemsN[slot]);
		PlayerSave.saveGame(c);
		c.getPA().closeAllWindows();
		c.getPA().resetVariables();
		c.getCombat().resetPlayerAttack();
	}
}