package org.mistex.system.packet.packets;

import org.mistex.game.Mistex;
import org.mistex.game.MistexConfiguration;
import org.mistex.game.MistexUtility;
import org.mistex.game.world.PunishmentHandler;
import org.mistex.game.world.content.clanchat.Clan;
import org.mistex.game.world.content.clanchat.ClanHandler;
import org.mistex.game.world.content.clanchat.ClanRanks;
import org.mistex.game.world.player.Client;
import org.mistex.system.packet.PacketType;

public class PrivateMessaging implements PacketType {

	public final int ADD_FRIEND = 188, SEND_PM = 126, REMOVE_FRIEND = 215,
			CHANGE_PM_STATUS = 95, REMOVE_IGNORE = 59, ADD_IGNORE = 133;

	@SuppressWarnings({ "static-access", "unused" })
	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		switch (packetType) {

		case ADD_FRIEND:
			c.friendUpdate = true;
			long friendToAdd = c.getInStream().readQWord();
			boolean canAdd = true;

			for (int i1 = 0; i1 < c.friends.length; i1++) {
				if (c.friends[i1] != 0 && c.friends[i1] == friendToAdd) {
					canAdd = false;
					c.sendMessage(friendToAdd+ " is already on your friends list.");
				}
			}
			if (canAdd == true) {
				for (int i1 = 0; i1 < c.friends.length; i1++) {
					if (c.friends[i1] == 0) {
						c.friends[i1] = friendToAdd;
						Clan clan = ClanHandler.getClan(c.playerName);
						if (clan != null)
							clan.addRank(MistexUtility.longToPlayerName2(c.friends[i1]).replaceAll("_", " ").toLowerCase(), ClanRanks.NOT_RANKED);
						for (int i2 = 1; i2 < MistexConfiguration.MAX_PLAYERS; i2++) {
							if (Mistex.playerHandler.players[i2] != null && Mistex.playerHandler.players[i2].isActive && MistexUtility.playerNameToInt64(Mistex.playerHandler.players[i2].playerName) == friendToAdd) {
								Client o = (Client) Mistex.playerHandler.players[i2];
								if (o != null) {
									if (Mistex.playerHandler.players[i2].privateChat == 0 || (Mistex.playerHandler.players[i2].privateChat == 1 && o.getPA().isInPM(MistexUtility.playerNameToInt64(c.playerName)))) {
										c.getPA().loadPM(friendToAdd, 1);
										break;
									}
								}
							}
						}
						break;
					}
				}
			}
			break;

		case SEND_PM:
			long sendMessageToFriendId = c.getInStream().readQWord();
			byte pmchatText[] = new byte[100];
			int pmchatTextSize = (byte) (packetSize - 8);
			c.getInStream().readBytes(pmchatText, pmchatTextSize, 0);
			if (PunishmentHandler.isMuted(c))
				break;
			for (int i1 = 0; i1 < c.friends.length; i1++) {
				if (c.friends[i1] == sendMessageToFriendId) {
					boolean pmSent = false;

					for (int i2 = 1; i2 < MistexConfiguration.MAX_PLAYERS; i2++) {
						if (Mistex.playerHandler.players[i2] != null
								&& Mistex.playerHandler.players[i2].isActive
								&& MistexUtility.playerNameToInt64(Mistex.playerHandler.players[i2].playerName) == sendMessageToFriendId) {
							Client o = (Client) Mistex.playerHandler.players[i2];
							if (o != null) {
								if (Mistex.playerHandler.players[i2].privateChat == 0 || (Mistex.playerHandler.players[i2].privateChat == 1 && o .getPA() .isInPM(MistexUtility .playerNameToInt64(c.playerName)))) {
										c.getPMLog().Logger("Private", MistexUtility.textUnpack(pmchatText, pmchatTextSize));
									if (PunishmentHandler.isMuted(c)) {
							            c.sendMessage("You are muted! No one can hear you. Please apply on forums.");
							            return;
									}
									o.getPA().sendPM(MistexUtility.playerNameToInt64(c.playerName),c.playerRights, pmchatText,pmchatTextSize);
									pmSent = true;
								}
							}
							break;
						}
					}
					if (!pmSent) {
						c.sendMessage("That player is currently offline.");
						break;
					}
				}
			}
			break;

		case REMOVE_FRIEND:
			c.friendUpdate = true;
			long friendToRemove = c.getInStream().readQWord();

			for (int i1 = 0; i1 < c.friends.length; i1++) {
				if (c.friends[i1] == friendToRemove) {
					for (int i2 = 1; i2 < MistexConfiguration.MAX_PLAYERS; i2++) {
						Client o = (Client) Mistex.playerHandler.players[i2];
						if (o != null) {
							if (c.friends[i1] == MistexUtility.playerNameToInt64(Mistex.playerHandler.players[i2].playerName)) {
								o.getPA().updatePM(c.playerId, 0);
								break;
							}
						}
					}
					Clan clan = ClanHandler.getClan(c.playerName);
					if (clan != null)
						clan.removeRank(MistexUtility.longToPlayerName2(c.friends[i1]).replaceAll("_", " ").toLowerCase());
					c.friends[i1] = 0;
					break;
				}
			}
			break;

		case REMOVE_IGNORE:
			int i = c.getInStream().readDWord();
			int i2 = c.getInStream().readDWord();
			int i3 = c.getInStream().readDWord();
			// for other status changing
			// c.getPA().handleStatus(i,i2,i3);
			break;

		case CHANGE_PM_STATUS:
			int tradeAndCompete = c.getInStream().readUnsignedByte();
			c.privateChat = c.getInStream().readUnsignedByte();
			int publicChat = c.getInStream().readUnsignedByte();
			for (int i1 = 1; i1 < MistexConfiguration.MAX_PLAYERS; i1++) {
				if (Mistex.playerHandler.players[i1] != null && Mistex.playerHandler.players[i1].isActive == true) {
					Client o = (Client) Mistex.playerHandler.players[i1];
					if (o != null) {
						o.getPA().updatePM(c.playerId, 1);
					}
				}
			}
			break;

		case ADD_IGNORE:
			// TODO: fix this so it works :)
			break;

		}

	}
}
