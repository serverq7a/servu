package org.mistex.system.packet.packets;

import org.mistex.game.world.content.BookHandler;
import org.mistex.game.world.content.MysteryBox;
import org.mistex.game.world.content.TeletabHandler;
import org.mistex.game.world.content.TreasureTrails;
import org.mistex.game.world.content.cannon.DwarfCannon;
import org.mistex.game.world.content.gambling.DiceHandler;
import org.mistex.game.world.content.gambling.MithrilSeeds;
import org.mistex.game.world.content.skill.herblore.Herblore;
import org.mistex.game.world.content.skill.prayer.Bury;
import org.mistex.game.world.content.skill.prayer.Constants;
import org.mistex.game.world.content.skill.runecrafting.Pouches;
import org.mistex.game.world.content.skill.woodcutting.BirdsNests;
import org.mistex.game.world.event.CycleEvent;
import org.mistex.game.world.event.CycleEventContainer;
import org.mistex.game.world.event.CycleEventHandler;
import org.mistex.game.world.player.Client;
import org.mistex.system.packet.PacketType;

public class ClickItem implements PacketType {

	@Override
	public void processPacket(final Client c, int packetType, int packetSize) {
		c.getInStream().readSignedWordBigEndianA();
		int itemSlot = c.getInStream().readUnsignedWordA();
		int itemId = c.getInStream().readUnsignedWordBigEndian();
		if (itemSlot < c.playerItems.length && itemId != c.playerItems[itemSlot] - 1) {
			return;
		}
		if (BirdsNests.isNest(itemId)) {
			BirdsNests.searchNest(c, itemId);
		}
		if (c.doingDungeoneering == false) {
			TeletabHandler.handleItem(c, itemId);
		}

		switch (itemId) {

		case 299:
			MithrilSeeds.plantMithrilSeed(c);
			break;

		case 6:
			c.setCannon(new DwarfCannon(c));
			c.getCannon().setup();
			break;

		case 2528:
			c.getPA().showInterface(2808);
			break;

		case 6199:
			MysteryBox.openBox(c);
			break;

		case 4155:
			c.getSlayer().showTask();
			break;

		case 2677:
			c.getItems().deleteItem(itemId, 1);
			TreasureTrails.addClueReward(c, 0);
			break;

		case 2678:
			c.getItems().deleteItem(itemId, 1);
			TreasureTrails.addClueReward(c, 1);
			break;

		case 2679:
			c.getItems().deleteItem(itemId, 1);
			TreasureTrails.addClueReward(c, 2);
			break;

		case 757:
			BookHandler.openVoteBook(c, 757);
			break;

		case 7464:
			BookHandler.openChickenBook(c, 7464);
			break;

		case 10025:
			BookHandler.openMagicBox(c, 10025);
			break;

		case 15272:
			if (System.currentTimeMillis() - c.foodDelay >= 1800) {
				if (c.playerLevel[3] >= c.getPA().getLevelForXP(c.playerXP[3] + 10)) {
					c.playerLevel[3] = c.getPA().getLevelForXP(c.playerXP[3]) + 10;
					c.getItems().deleteItem(15272, itemSlot, 1);
					c.startAnimation(829);
					c.sendMessage("You eat the Rocktail.");
					c.getPA().refreshSkill(3);
					c.foodDelay = System.currentTimeMillis();
				}
				if (c.playerLevel[3] == c.getPA().getLevelForXP(c.playerXP[3])) {
					c.getItems().deleteItem(15272, itemSlot, 1);
					c.startAnimation(829);
					c.playerLevel[3] += 10;
					c.sendMessage("You eat the Rocktail.");
					c.getPA().refreshSkill(3);
					c.foodDelay = System.currentTimeMillis();
				}
				if (c.playerLevel[3] < c.getPA().getLevelForXP(c.playerXP[3])) {
					c.getItems().deleteItem(15272, itemSlot, 1);
					c.startAnimation(829);
					c.playerLevel[3] += 23;
					c.sendMessage("You eat the Rocktail.");
					if (c.playerLevel[3] > c.getPA().getLevelForXP(c.playerXP[3]) + 10)
						c.playerLevel[3] = c.getPA().getLevelForXP(c.playerXP[3]) + 10;
					c.getPA().refreshSkill(3);
					c.foodDelay = System.currentTimeMillis();
				}
			}
			break;

		}
		if (c.getFood().isFood(itemId)) {
			c.getFood().eat(itemId, itemSlot);
		}

		if (c.getPotions().isPotion(itemId)) {
			c.getPotions().handlePotion(itemId, itemSlot);
		}

		if (itemId == DiceHandler.DICE_BAG) {
			DiceHandler.selectDice(c, itemId);
		}

		if (itemId > DiceHandler.DICE_BAG && itemId <= 15100) {
			DiceHandler.rollDice(c, itemId, false);
		}

		if (Herblore.isHerb(c, itemId)) {
			Herblore.cleanTheHerb(c, itemId);
		}

		if (Constants.playerBones(c, itemId)) {
			Bury.buryBones(c, itemId, itemSlot);
		}

		for (int i = 0; i < Pouches.pouchData.length; i++) {
			if (itemId == Pouches.pouchData[i][0]) {
				Pouches.fillPouch(c, itemId);
			}
		}

		if (itemId == 952) {
			if (System.currentTimeMillis() - c.spadeDelay >= 1000) {
				c.spadeDelay = System.currentTimeMillis();
				c.startAnimation(831);
				c.sendMessage("You start digging...");
				CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
					public void execute(CycleEventContainer p) {
						c.startAnimation(65535);
						c.getBarrows().spadeDigging();
						p.stop();
					}

					@Override
					public void stop() {
					}
				}, 1);
			} else {
			}
		}

	}

}