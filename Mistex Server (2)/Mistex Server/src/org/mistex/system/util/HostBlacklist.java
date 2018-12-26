package org.mistex.system.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.mistex.game.Mistex;

public class HostBlacklist {

	private static final String BLACKLIST_DIR = "./Data/Blacklisted Host/blacklist.txt";

	private static List<String> blockedHostnames = new ArrayList<String>();
	
	private static final Logger logger = Logger.getLogger(Mistex.class.getName());

	public static List<String> getBlockedHostnames() {
		return blockedHostnames;
	}

	public static boolean isBlocked(String host) {
		return blockedHostnames.contains(host.toLowerCase());
	}

	public static void loadBlacklist() {
		String word = null;
		try {
			BufferedReader in = new BufferedReader(
					new FileReader(BLACKLIST_DIR));
			while ((word = in.readLine()) != null)
				blockedHostnames.add(word.toLowerCase());
				logger.info("Black lists have been have initialized.");
			in.close();
			in = null;
		} catch (final Exception e) {
			System.out.println("Could not load blacklisted hosts.");
		}
	}
}