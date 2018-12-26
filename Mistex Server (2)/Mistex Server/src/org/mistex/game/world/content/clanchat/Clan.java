package org.mistex.game.world.content.clanchat;

import java.util.ArrayList;
import java.util.List;

import org.mistex.game.MistexUtility;
import org.mistex.game.world.PunishmentHandler;
import org.mistex.game.world.World;
import org.mistex.game.world.player.Client;

/**
 * @author Michael | Chex
 */
public class Clan extends ClanFile {

	public Client[] members = new Client[100];
	public List<String> banList = new ArrayList<String>();
	
	public List<String> friends = new ArrayList<String>();
	public List<String> recruits = new ArrayList<String>();
	public List<String> corporals = new ArrayList<String>();
	public List<String> sergeants = new ArrayList<String>();
	public List<String> lieutenants = new ArrayList<String>();
	public List<String> captains = new ArrayList<String>();
	public List<String> generals = new ArrayList<String>();
	
	public String owner, name;
	public int lootshare, whoCanEnter, whoCanTalk, whoCanKick, updateMask;
	
	/* Update Flags */
	public static final short ENTER_FLAG = 0x1;
	public static final short TALK_FLAG = 0x2;
	public static final short KICK_FLAG = 0x4;
	public static final short CLAN_NAME_FLAG = 0x8;
	public static final short RANK_CHANGE_FLAG = 0x10;
	public static final short LOOTSHARE_FLAG = 0x20;
	public static final short MASTER_FLAG = ENTER_FLAG | TALK_FLAG | KICK_FLAG | CLAN_NAME_FLAG | RANK_CHANGE_FLAG | LOOTSHARE_FLAG;
	
	public Clan(String owner) {
		super(owner);
		this.owner = MistexUtility.capitalize(owner);
		addFlag(MASTER_FLAG);
		updateValues();
	}
	
	public void updateValues() {
		if ((updateMask & ENTER_FLAG) == ENTER_FLAG)
			whoCanEnter = ClanRanks.forIndex(getSetting("whoCanEnter")).ordinal();
		
		if ((updateMask & TALK_FLAG) == TALK_FLAG)
			whoCanTalk = ClanRanks.forIndex(getSetting("whoCanTalk")).ordinal();

		if ((updateMask & KICK_FLAG) == KICK_FLAG)
			whoCanKick = ClanRanks.forIndex(getSetting("whoCanKick")).ordinal();
		
		if ((updateMask & LOOTSHARE_FLAG) == LOOTSHARE_FLAG)
			lootshare = ClanRanks.forIndex(getSetting("lootshare")).ordinal();
		
		if ((updateMask & RANK_CHANGE_FLAG) == RANK_CHANGE_FLAG) {
			friends = retrieveRanks("Friends");
			recruits = retrieveRanks("Recruits");
			corporals = retrieveRanks("Corporals");
			sergeants = retrieveRanks("Sergeants");
			lieutenants = retrieveRanks("Lieutenants");
			captains = retrieveRanks("Captains");
			generals = retrieveRanks("Generals");
			updateChatInterface();
		}
		
		if ((updateMask & CLAN_NAME_FLAG) == CLAN_NAME_FLAG) {
			name = getStringSetting("name");
			for (Client member : members) {
				if (member != null) {
					member.getPA().sendFrame126("Talking in: " + name, 18139);
				}
			}
		}
		
		updateMask = 0;
	}
	
	public boolean canEnter(String name) {
		return getRank(name).ordinal() >= whoCanEnter || owner.equals(name);
	}
	
	public boolean canTalk(String name) {
		return getRank(name).ordinal() >= whoCanTalk || owner.equals(name);
	}
	
	public boolean canKick(String name) {
		return getRank(name).ordinal() >= whoCanKick || owner.equals(name);
	}
	
	public boolean isBanned(String name) {
		return banList.contains(name);
	}
	
	public void changeName(String name) {
		name = name.trim();
		if (name.length() > 12)
			name = name.substring(0, 12);

		changeElement("ClanData", "name", getStringSetting("name"), true);
		changeElement("ClanData", "name", name, false);
		
		addFlag(CLAN_NAME_FLAG);
	}
	
	public boolean attemptJoin(Client c) {
		c.sendMessage("Attempting to join channel...");
		if (c.savedClan != null) {
			c.sendMessage("You are already in a clan!");
			c.getPA().sendFrame126("Leave chat", 18135);
			return false;
		}
		
		if (!canEnter(c.playerName)) {
			c.sendMessage("You don't have high enough rank to enter this channel!");
			return false;
		}

		if (isBanned(c.playerName)) {
			c.sendMessage("You have been banned from this clan chat.");
			c.sendMessage("The ban will be removed once the next hour start.");
			return false;
		}
		return true;
	}
	
	public void joinClan(Client c) {
		if (!attemptJoin(c)) {
			return;
		}
		
		for (int index = 0; index < members.length; index++) {
			if (members[index] == null) {
				c.sendMessage("Now talking in clan channel: " + getStringSetting("name"));
				c.sendMessage("To talk, start each line of chat with the / symbol.");
				members[index] = c;
				c.savedClan = owner.toLowerCase();
				alertClan(MistexUtility.capitalize(members[index].playerName) + " has joined the channel.");
				c.getPA().sendFrame126("Leave chat", 18135);
				updateChatInterface();
				return;
			}
		}
	}
	
	public void leaveClan(Client c, boolean logout) {
		boolean left = false;
		for (int index = 0; index < members.length; index++) {
			if (members[index] != null && members[index].equals(c)) {
				if (!logout) {
					c.savedClan = null;
					members[index].getPA().sendFrame126("Talking in: None", 18139);
					members[index].getPA().sendFrame126("Owner: None", 18140);
					for (int line = 18144; line < 18244; line++) {
						members[index].getPA().updateClanRank("", line, 0);
					}
					c.sendMessage("You have left the channel.");
				}
				c.getPA().sendFrame126("Join chat", 18135);
				String name = MistexUtility.capitalize(members[index].playerName);
				members[index] = null;
				alertClan(name + " has left the channel.");
				updateChatInterface();
				left = true;
			}
		}
		
		if (!left) {
			c.savedClan = null;
			c.getPA().sendFrame126("Join chat", 18135);
			c.sendMessage("You are not in a clan.");
		}
	}
	
	public void updateChatInterface() {
		for (Client member : members) {
			if (member != null) {
				member.getPA().sendFrame126("Talking in: " + MistexUtility.capitalize(name), 18139);
				member.getPA().sendFrame126("Owner: " + owner, 18140);
				int nextMember = 18144;
				for (Client otherMember : members) {
					if (otherMember != null) {
						String name = MistexUtility.capitalize(otherMember.playerName);
						ClanRanks rank = getRank(name);
						member.getPA().updateClanRank(name, nextMember++, rank.ordinal());
					}
				}
				for (int index = nextMember; index < 18244; index++) {
					member.getPA().updateClanRank("", index, 0);
				}
			}
		}
	}
	
	public void alertClan(String message) {
		for (Client member : members) {
			if (member != null) {
				member.sendMessage("@red@" + message);
			}
		}
	}
	
	public void messageClan(Client c, String message) {
		if (!canTalk(c.playerName)) {
			c.sendMessage("You don't have high enough rank to speak in this channel!");
			return;
		}
		
		if (PunishmentHandler.isMuted(c)) {
			c.sendMessage("You are muted and are not permitted to speak!");
			return;
		}
		
		for (Client member : members) {
			if (member != null) {
				String name = MistexUtility.capitalize(c.playerName);
				member.sendClan(name, message, this.name, c.playerRights);
			}
		}
	}
	
	public ClanRanks getRank(String name) {
		name = name.toLowerCase();
		if (owner.equalsIgnoreCase(name))
			return ClanRanks.OWNER;
		Client member = (Client) World.getPlayer(name);
		if (member != null && (member.playerRights == 2 || member.playerRights == 3))
			return ClanRanks.ADMINISTRATOR;
		if (recruits.contains(name))
			return ClanRanks.RECRUIT;
		if (corporals.contains(name))
			return ClanRanks.CORPORAL;
		if (sergeants.contains(name))
			return ClanRanks.SERGEANT;
		if (lieutenants.contains(name))
			return ClanRanks.LIEUTENANT;
		if (captains.contains(name))
			return ClanRanks.CAPTAIN;
		if (generals.contains(name))
			return ClanRanks.GENERAL;
		if (friends.contains(name))
			return ClanRanks.FRIEND;
		return ClanRanks.NOT_RANKED;
	}
	
	public ClanRanks getEditedRank(String name) {
		if (retrieveRanks("Recruits").contains(name))
			return ClanRanks.RECRUIT;
		if (retrieveRanks("Corperals").contains(name))
			return ClanRanks.CORPORAL;
		if (retrieveRanks("Sergeants").contains(name))
			return ClanRanks.SERGEANT;
		if (retrieveRanks("Lieutenants").contains(name))
			return ClanRanks.LIEUTENANT;
		if (retrieveRanks("Captains").contains(name))
			return ClanRanks.CAPTAIN;
		if (retrieveRanks("Generals").contains(name))
			return ClanRanks.GENERAL;
		return ClanRanks.NOT_RANKED;
	}
	
	public void addRank(String name, ClanRanks rank) {
		removeRank(name);
		if (rank.equals(ClanRanks.NOT_RANKED) && ((Client) World.getPlayer(owner)).getPA().isInPM(MistexUtility.playerNameToInt64(name)))
			rank = ClanRanks.FRIEND;
		String rawRank = rank.name().toLowerCase();
		changeElement(MistexUtility.capitalize(rawRank + "s"), "name", name, false);
		addFlag(RANK_CHANGE_FLAG);
	}
	
	public void removeRank(String name) {
		changeElement("Friends", "name", name, true);
		changeElement("Recruits", "name", name, true);
		changeElement("Corporals", "name", name, true);
		changeElement("Sergeants", "name", name, true);
		changeElement("Lieutenants", "name", name, true);
		changeElement("Captains", "name", name, true);
		changeElement("Generals", "name", name, true);
		addFlag(RANK_CHANGE_FLAG);
	}

	public String getOwner() {
		return owner;
	}

	public void addFlag(int updateMask) {
		Client c = (Client) World.getPlayer(owner);
		if (c != null && (this.updateMask & MASTER_FLAG) == 0)
			c.sendMessage("Changes will take effect on your clan chat in the next 60 seconds.");
		this.updateMask |= updateMask;
	}

	public void removeFlag(int updateMask) {
		this.updateMask &= ~updateMask;
		System.out.println("Flag removed.");
	}
}