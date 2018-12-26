package org.mistex.game.world.content.clanchat;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class ClanHandler {
	public static final HashMap<String, Clan> clans = new HashMap<String, Clan>();
	
	private static long banTimer = System.currentTimeMillis();

	static {
		for (File file : new File("./data/newClans/").listFiles()) {
			if (file.exists()) {
				Clan clan = new Clan(file.getName().replace(".xml", ""));
				clans.put(clan.getOwner().toLowerCase(), clan);
			}
		}
		System.out.println("Clans loaded : " + clans.size());
	}

	public static void process() {
		for (Clan clan : clans.values()) {
			if (clan == null)
				continue;

			if (TimeUnit.MILLISECONDS.toHours(System.currentTimeMillis() - banTimer) <= 0) {
				clan.banList.clear();
			}

			clan.updateValues();
		}
	}

	public static Clan getClan(String name) {
		if (name == null) return null;
		return clans.get(name.toLowerCase());
	}
}