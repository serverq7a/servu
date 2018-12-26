package org.mistex.system.packet.packets;

import org.mistex.game.world.content.Teles;
import org.mistex.game.world.content.gambling.DiceHandler;
import org.mistex.game.world.content.skill.hunter.ButterflyCatching;
import org.mistex.game.world.content.skill.runecrafting.TalismanHandler;
import org.mistex.game.world.content.skill.runecrafting.TalismanHandler.talismanData;
import org.mistex.game.world.player.Client;
import org.mistex.system.packet.PacketType;

public class ItemClick3 implements PacketType {

	public void summon(Client c, int npcid) {
		if (c.lastsummon < 1) {
			c.Summoning().SummonNewNPC(npcid, true);
		} else {
			c.sendMessage("<col=8345667>You already have a NPC summoned");
			c.sendMessage("<col=8345667>To dismiss it you need to click on the summoning Stat icon");
		}
	}

	@SuppressWarnings("static-access")
	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int itemId11 = c.getInStream().readSignedWordBigEndianA();
		int itemId1 = c.getInStream().readSignedWordA();
		int itemId = c.getInStream().readSignedWordA();
		if (!c.getItems().playerHasItem(itemId, 1)) {
			return;
		}
		if (itemId >= DiceHandler.DICE_BAG && itemId <= 15100) {
			DiceHandler.putupDice(c, itemId);
		}
		for (talismanData t : talismanData.values()) {
			if (itemId == t.getTalisman()) {
				TalismanHandler.handleTalisman(c, itemId);
			}
		}
		final String name = c.getItems().getItemName(itemId);
		if (c.getPotions().isPotion(itemId) && System.currentTimeMillis() - c.lastEmpty >= 1500) {
			if (name.contains("potion")) {
				c.sendMessage("You empty your " + name + ".");
				c.getItems().deleteItem(itemId, 1);
				c.getItems().addItem(229, 1);
				c.lastEmpty = System.currentTimeMillis();
			}
		}
		switch (itemId) {
		case 1704:
			c.sendMessage("Your glory has no more charges left.");
			break;
		case 2552:
		case 2554:
		case 2556:
		case 2558:
		case 2560:
		case 2562:
		case 2564:
		case 2566:
			c.itemUsing = itemId;
			Teles.ROD(c);
			break;

		case 1712:
		case 1710:
		case 1708:
		case 1706:
			c.itemUsing = itemId;
			Teles.AOG(c);
			break;

		case 3853:
		case 3855:
		case 3857:
		case 3859:
		case 3861:
		case 3863:
		case 3865:
		case 3867:
			c.itemUsing = itemId;
			Teles.GN(c);
			break;

		case 10014:
		case 10016:
		case 10018:
		case 10020:
			ButterflyCatching.releaseButterfly(c, itemId);
			break;

		case 12007:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 6795);
			break;

		case 12009:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 6797);
			break;

		case 12011:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 6799);
			break;

		case 12013:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 6801);
			break;

		case 12015:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 6803);
			break;

		case 12017:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 6805);
			break;

		case 12019:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 6807);
			break;

		case 12021:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 6808);
			break;

		case 12023:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 6810);
			break;

		case 12025:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 6812);
			break;

		case 12027:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 6993);
			break;

		case 12029:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 6814);
			break;

		case 12031:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 6816);
			break;

		case 12033:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 6817);
			break;

		case 12035:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 6819);
			break;

		case 12037:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 6821);
			break;

		case 12039:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 6823);
			break;

		case 12041:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 6824);
			break;

		case 12043:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 6826);
			break;

		case 12045:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 6828);
			break;

		case 12047:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 6830);
			break;

		case 12049:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 6832);
			break;

		case 12051:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 6834);
			break;

		case 12053:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 6836);
			break;

		case 12055:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 6838);
			break;

		case 12057:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 6840);
			break;

		case 12059:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 6842);
			break;

		case 12061:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 6844);
			break;

		case 12063:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 6995);
			break;

		case 12065:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 6846);
			break;

		case 12067:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 6848);
			break;

		case 12069:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 6850);
			break;

		case 12071:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 6852);
			break;

		case 12073:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 6854);
			break;

		case 12075:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 6856);
			break;

		case 12077:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 6858);
			break;

		case 12079:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 6860);
			break;

		case 12081:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 6862);
			break;

		case 12083:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 6864);
			break;

		case 12085:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 6866);
			break;

		case 12087:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 6868);
			break;

		case 12089:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 6870);
			break;

		case 12091:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 6872);
			break;

		case 12093:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 6874);
			break;

		case 12123:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 6890);
			break;

		case 12776:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 7330);
			break;

		case 12778:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 7332);
			break;

		case 12780:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 7334);
			break;

		case 12782:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 7336);
			break;

		case 12784:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 7338);
			break;

		case 12786:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 7340);
			break;

		case 12788:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 7342);
			break;

		case 12790:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 7344);
			break;

		case 12792:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 7346);
			break;

		case 12794:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 7348);
			break;

		case 12796:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 7350);
			break;

		case 12798:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 7352);
			break;

		case 12800:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 7354);
			break;

		case 12802:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 7356);
			break;

		case 12804:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 7358);
			break;

		case 12806:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 7360);
			break;

		case 12808:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 7362);
			break;

		case 12810:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 7364);
			break;

		case 12812:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 7366);
			break;

		case 12814:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 7368);
			break;

		case 12816:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 7378);
			break;

		case 12818:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 7370);
			break;

		case 12820:
			if (!(c.lastsummon <= 0)) {
				c.sendMessage("<col=8345667>You already have a follower!");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			summon(c, 7371);
			break;

		default:
			if (c.playerRights == 3)
				c.sendMessage(c.playerName + " - Item3rdOption: " + itemId + " : " + itemId11 + " : " + itemId1);
			break;
		}

	}

}