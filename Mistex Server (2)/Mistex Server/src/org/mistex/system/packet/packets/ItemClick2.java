package org.mistex.system.packet.packets;

import org.mistex.game.world.content.gambling.DiceHandler;
import org.mistex.game.world.content.skill.hunter.Implings;
import org.mistex.game.world.content.skill.runecrafting.Pouches;
import org.mistex.game.world.player.Client;
import org.mistex.system.packet.PacketType;

public class ItemClick2 implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int itemId = c.getInStream().readSignedWordA();

		if (!c.getItems().playerHasItem(itemId, 1))
			return;

		if (itemId > DiceHandler.DICE_BAG && itemId <= 15100) {
			DiceHandler.selectDice(c, itemId);
		}
		for (int i = 0; i < Pouches.pouchData.length; i++) {
			if (itemId == Pouches.pouchData[i][0]) {
				Pouches.checkPouch(c, itemId);
				return;
			}
		}

		switch (itemId) {
		case 11238:
		case 11240:
		case 11242:
		case 11244:
		case 11246:
		case 11248:
		case 11250:
		case 11252:
		case 11254:
		case 11256:
			Implings.lootJar(c, itemId);
			break;

		case 11694:
			if (c.getItems().playerHasItem(11694)) {
				if (canDismantle(c)) {
					c.getItems().deleteItem(11694, 1);
					c.getItems().addItem(11702, 1);
					c.getItems().addItem(11690, 1);
					c.getDH().OneItemDialogue("You successfully dismantled an armadyl godsword!", 11702);
				}
			} else {
				c.getPA().sendStatement("If you want to dismantle a godsword you first need a godsword!");
			}
			break;

		case 11696:
			if (c.getItems().playerHasItem(11696)) {
				if (canDismantle(c)) {
					c.getItems().deleteItem(11696, 1);
					c.getItems().addItem(11704, 1);
					c.getItems().addItem(11690, 1);
					c.getDH().OneItemDialogue("You successfully dismantled a bandos godsword!", 11704);
				}
			} else {
				c.getPA().sendStatement("If you want to dismantle a godsword you first need a godsword!");
			}
			break;
		case 11698:
			if (c.getItems().playerHasItem(11698)) {
				if (canDismantle(c)) {
					c.getItems().deleteItem(11698, 1);
					c.getItems().addItem(11706, 1);
					c.getItems().addItem(11690, 1);
					c.getDH().OneItemDialogue("You successfully dismantled a saradomin godsword!", 11706);
				}
			} else {
				c.getPA().sendStatement("If you want to dismantle a godsword you first need a godsword!");
			}
			break;
		case 11700:
			if (c.getItems().playerHasItem(11700)) {
				if (canDismantle(c)) {
					c.getItems().deleteItem(11700, 1);
					c.getItems().addItem(11708, 1);
					c.getItems().addItem(11690, 1);
					c.getDH().OneItemDialogue("You successfully dismantled a zamorak godsword!", 11708);
				}
			} else {
				c.getPA().sendStatement("If you want to dismantle a godsword you first need a godsword!");
			}
			break;
		default:
			if (c.playerRights == 3)
				c.sendMessage(c.playerName + " - Item2rdOption: " + itemId);
			break;
		}
	}

	public boolean canDismantle(Client c) {
		if (c.getItems().freeSlots() < 3) {
			c.sendMessage("You need atleast 3 free inventory spaces");
			c.getPA().closeAllWindows();
			return false;
		}
		return true;
	}

}