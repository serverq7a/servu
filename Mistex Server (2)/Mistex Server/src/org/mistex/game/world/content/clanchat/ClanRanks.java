package org.mistex.game.world.content.clanchat;

import org.mistex.game.MistexUtility;

public enum ClanRanks {
	NOT_RANKED,
	OWNER,
	ADMINISTRATOR,
	FRIEND,
	RECRUIT,
	CORPORAL,
	SERGEANT,
	LIEUTENANT,
	CAPTAIN,
	GENERAL;
	
	public String toString() {
		return MistexUtility.capitalize((name().trim().toLowerCase().replaceAll("_", " ")));
	};
	
	public static ClanRanks forName(String name) {
		switch (name) {
		case "Anyone":
			return NOT_RANKED;
		case "Any friends":
			return FRIEND;
		case "Recruit+":
			return RECRUIT;
		case "Corporal+":
			return CORPORAL;
		case "Sergeant+":
			return SERGEANT;
		case "Lieutenant+":
			return LIEUTENANT;
		case "Captain+":
			return CAPTAIN;
		case "General+":
			return ClanRanks.GENERAL;
		case "Only me":
			return OWNER;
		default:
			return NOT_RANKED;
		}
	}
	
	public static ClanRanks forIndex(int index) {
		for (ClanRanks rank : values()) {
			if (rank.ordinal() == index) {
				return rank;
			}
		}
		return NOT_RANKED;
	}
}