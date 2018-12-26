package org.mistex.game.world.content.clanchat;

import org.mistex.game.MistexUtility;
import org.mistex.game.world.player.Client;

public class ClanButtons {

	public static void handleClanButton(Client c, int button) {
		Clan clan = ClanHandler.getClan(c.playerName);
		if (button == 70212) {
			if (clan == null) {
				clan = new Clan(c.playerName);
				ClanHandler.clans.put(c.playerName.toLowerCase(), clan);
			}
        	c.getPA().sendFrame126(clan.getStringSetting("name"), 47814);
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
	        c.getPA().showInterface(40172);
	        return;
		}
		
		if (clan == null) return;
		
		String[] ranks = new String[] { "Anyone", "Any friends", "Recruit+", "Corporal+", "Sergeant+", "Lieutenant+", "Captain+", "General+", "Only me" };
		int[] enterButtons = new int[] { 184151, 187135, 187134, 187133, 187132, 187131, 187130, 187129, 187128 };
		int[] talkButtons = new int[] { 184154, 187145, 187144, 187143, 187142, 187141, 187140, 187139, 187138 };
		int[] kickButtons = new int[] { 184157, 187149, 187150, 187151, 187152, 187153, 187154 };
		
		for (int index = 0; index < enterButtons.length; index++) {
			if (button == enterButtons[index]) {
				int rank = ClanRanks.forName(ranks[index]).ordinal();
				int whoCanEnter = clan.getSetting("whoCanEnter");
				if (whoCanEnter == rank || clan.whoCanEnter == rank) {
					clan.removeFlag(Clan.ENTER_FLAG);
					if (clan.whoCanEnter != rank)
						return;
				}
				clan.changeElement("ClanData", "whoCanEnter", ""+whoCanEnter+"", true);
				clan.changeElement("ClanData", "whoCanEnter", ""+ClanRanks.forName(ranks[index]).ordinal()+"", false);
				c.getPA().sendFrame126(ranks[index], 47815);
				clan.addFlag(Clan.ENTER_FLAG);
				return;
			}
		}
		
		for (int index = 0; index < talkButtons.length; index++) {
			if (button == talkButtons[index]) {
				int rank = ClanRanks.forName(ranks[index]).ordinal();
				int whoCanTalk = clan.getSetting("whoCanTalk");
				if (whoCanTalk == rank || clan.whoCanTalk == rank) {
					clan.removeFlag(Clan.TALK_FLAG);
					if (clan.whoCanTalk != rank)
						return;
				}
				clan.changeElement("ClanData", "whoCanTalk", ""+whoCanTalk+"", true);
				clan.changeElement("ClanData", "whoCanTalk", ""+rank+"", false);
				c.getPA().sendFrame126(ranks[index], 47816);
				clan.addFlag(Clan.TALK_FLAG);
				return;
			}
		}
		
		for (int index = 0; index < kickButtons.length; index++) {
			if (button == kickButtons[index]) {
				int rank = ClanRanks.forName(ranks[index == 0 ? 8 : (7 - index + 1)]).ordinal();
				int whoCanKick = clan.getSetting("whoCanKick");
				if (whoCanKick == rank || clan.whoCanKick == rank) {
					clan.removeFlag(Clan.KICK_FLAG);
					if (clan.whoCanKick != rank)
						return;
				}
				clan.changeElement("ClanData", "whoCanKick", ""+whoCanKick+"", true);
				clan.changeElement("ClanData", "whoCanKick", ""+rank+"", false);
				c.getPA().sendFrame126(ranks[index == 0 ? 8 : (7 - index + 1)], 47817);
				clan.addFlag(Clan.KICK_FLAG);
				return;
			}
		}
		switch (button) {
		case 193219:
			if (c.savedClan != null) {
				clan = ClanHandler.getClan(c.savedClan);
				if (clan != null)
					clan.leaveClan(c, false);
			} else {
				c.sendMessage("You are not in a clan.");
				c.savedClan = null;
				c.getPA().sendFrame126("Join chat", 18135);
			}
			break;

		case 62137:
			if (c.savedClan != null) {
				c.sendMessage("You are already in a clan.");
				break;
			}
			if (c.getOutStream() != null) {
				c.getOutStream().createFrame(187);
				c.flushOutStream();
			}
			break;

		case 71074:
			c.sendMessage("Lootshare coming soon.");
//			clan.addFlag(Clan.LOOTSHARE_FLAG);
//			handleShareButton(c);
			break;
		}
	}
}