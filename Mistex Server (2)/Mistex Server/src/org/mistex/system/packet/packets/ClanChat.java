package org.mistex.system.packet.packets;

import org.mistex.game.MistexUtility;
import org.mistex.game.world.content.clanchat.Clan;
import org.mistex.game.world.content.clanchat.ClanHandler;
import org.mistex.game.world.content.clanchat.ClanRanks;
import org.mistex.game.world.player.Client;
import org.mistex.system.packet.PacketType;

/**
 * @author Michael | Chex
 */
public class ClanChat implements PacketType {
	
	public static final int CHANGE_NAME = 60,
							JOIN_CLAN = 63,
							KICK = 64,
							CHANGE_RANK = 65;

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		byte text[] = new byte[100];
		Clan clan;

		switch (packetType) {
		case CHANGE_NAME:
			c.getInStream().readBytes(text, packetSize, 0);
			clan = ClanHandler.getClan(c.playerName);
			clan.changeName(MistexUtility.textUnpack(text, packetSize));
			c.getPA().sendFrame126(ClanHandler.getClan(c.playerName).getStringSetting("name"), 47814);
			break;
		case JOIN_CLAN:
			c.getInStream().readBytes(text, packetSize, 0);
			clan = ClanHandler.getClan(MistexUtility.textUnpack(text, packetSize).toLowerCase());
			if (clan == null) {
				c.sendMessage("That clan does not exist.");
				break;
			}
			clan.joinClan(c);
			break;
		case KICK:c.getInStream().readBytes(text, packetSize, 0);
			if (c.savedClan == null) {
				c.sendMessage("You are not in a clan.");
				break;
			}
			clan = ClanHandler.getClan(c.savedClan);

			c.sendMessage("Kicking coming soon.");
//			clan.kick(MistexUtility.textUnpack(text, packetSize).toLowerCase());
			break;

		case CHANGE_RANK:
			int rank = c.getInStream().readUnsignedByte();
			c.getInStream().readBytes(text, packetSize - 1, 0);
			clan = ClanHandler.getClan(c.playerName);
			clan.addRank(MistexUtility.textUnpack(text, packetSize - 1).toLowerCase(), ClanRanks.forIndex(rank + (rank == 0 ? 0 : 3)));
			int slot = 44001;
			for (long asLong : c.friends) {
				if (asLong == 0) {
					c.getPA().sendFrame126("", slot + 800);
					c.getPA().sendFrame126("", slot);
				} else {
					String name = MistexUtility.longToPlayerName2(asLong).replace("_", " ");
					c.getPA().sendFrame126(MistexUtility.capitalize(clan.getEditedRank(name).name().replace("_", " ").toLowerCase()), slot + 800);
					c.getPA().sendFrame126(MistexUtility.capitalize(name.replace("_", " ").toLowerCase()), slot);
					slot++;
				}
			}
			break;
		}
	}
}
