package org.mistex.game.world.player;

import java.util.ArrayList;

import org.mistex.game.MistexUtility;
import org.mistex.game.world.World;

public class PlayerRight {
	
	private Client c;

	public PlayerRight(Client Client) {
		this.c = Client;
	}
	
	/**
	 * Player rank
	 */
	public int PLAYER = 0;
	
	/**
	 * Moderator rank
	 */
	public int MODERATOR = 1;
	
	/**
	 * Adminstrator rank
	 */
	public int ADMINISTRATOR = 2;
	
	/**
	 * Developer rank
	 */
	public int DEVELOPER = 3;
	
	/**
	 * Normal donator rank
	 */
	public int NOMRAL_DONATOR = 4;
	
	/**
	 * Super donator rank
	 */
	public int SUPER_DONATOR = 5;
	
	/**
	 * Extreme donator rank
	 */
	public int EXTREME_DONATOR = 6;
	
	/**
	 * Elite donator rank
	 */
	public int ELITE_DONATOR = 7;
	
	/**
	 * Server supporter rank
	 */
	public int SERVER_SUPPORTER = 8;
	
	/**
	 * Server veteran rank
	 */
	public int VETERAN = 9;
	
	/**
	 * Checks if player has player rights
	 * @return
	 */
	public boolean isPlayer() {
		return c.playerRights == PLAYER;
	}
	
	/**
	 * Checks if player has moderator rights
	 * @return
	 */
	public boolean isModerator() {
		return c.playerRights == MODERATOR;
	}
	
	/**
	 * Checks if player has adminstrator rights
	 * @return
	 */
	public boolean isAdminstrator() {
		return c.playerRights == ADMINISTRATOR;
	}
	
	/**
	 * Checks if player has developer rights
	 * @return
	 */
	public boolean isDeveloper() {
		return c.playerRights == DEVELOPER;
	}
	
	/**
	 * Checks if player has normal donator rights
	 * @return
	 */
	public boolean isNormalDonator() {
		return c.playerRights == NOMRAL_DONATOR;
	}
	
	/**
	 * Checks if player has super donator rights
	 * @return
	 */
	public boolean isSuperDonator() {
		return c.playerRights == SUPER_DONATOR;
	}
	
	/**
	 * Chckes if player has extreme donator rights
	 * @return
	 */
	public boolean isExtremeDonator() {
		return c.playerRights == EXTREME_DONATOR;
	}
	
	/**
	 * Checks if player has elite donator rights
	 * @return
	 */
	public boolean isEliteDonator() {
		return c.playerRights == ELITE_DONATOR;
	}
	
	/**
	 * Checks if player has server supporter rights
	 */
	public boolean isSupporter() {
		return c.playerRights == SERVER_SUPPORTER;
	}
	
	/**
	 * Checks if player has veteran rights
	 */
	public boolean isVeteran() {
		return c.playerRights == VETERAN;
	}
	
	/**
	 * Checks if player is a donator
	 * @return
	 */
	public boolean isDonator() {
		return c.playerRights >= 1 && c.playerRights <= 7;
	}
	
	/**
	 * Detemermines staff
	 */
	public void determineStaff() {
		switch (c.playerName) {
		case "play boy":
			c.playerRights = 3;
			break;

		case "chex":
			c.playerRights = 3;
			break;
		}
	}
	
	/**
	 * Determines rights icons
	 * @return
	 */
	public String determineIcon() {
		switch (c.playerRights) {
		case 1:
			return "<img=0>";
		case 2:
			return "<img=1>";
		case 3:
			return "<img=2>";
		case 4:
			return "<img=3>";
		case 5:
			return "<img=4>";
		case 6:
			return "<img=5>";
		case 7:
			return "<img=6>";
		case 8:
			return "<img=8>";
		case 9:
			return "<img=9>";
		}
		return "";
	}

	/**
	 * Detemines the rank
	 * @return
	 */
	public String determineRank() {
		switch (c.playerRights) {
		case 1:
			return "@gre@Normal Player";
		case 2:
			return "@yel@Adminstrator";
		case 3:
			return "@ceo@Developer";
		case 4:
			return "@red@Normal Donator";
		case 5:
			return "@cya@Super Donator";
		case 6:
			return "@mag@Extreme Donator";
		case 7:
			return "@mag@Elite Donator";
		case 8:
			return "@mag@Server Supporter";
		case 9:
			return "@mag@Veteran";
		}
		return "@gre@Player";
	}
	

	/**
	 * Online moderator array list
	 */
	public final ArrayList<String> onlineModerators = new ArrayList<String>();
	
	/**
	 * Online adminstrator array list
	 */
	public final ArrayList<String> onlineAdminstrators = new ArrayList<String>();

	/**
	 * Online developer array list
	 */
	public final ArrayList<String> onlineDevelopers = new ArrayList<String>();

	/**
	 * Gets all online staff members
	 */
	public void getOnlineStaff() {
		onlineModerators.clear();
		onlineAdminstrators.clear();
		onlineDevelopers.clear();
		for (Player p : World.players) {
			if (p != null) {
				Client staff = (Client) p;
				if (staff.playerRights == 1 && staff.privateChat == 0) {
					onlineModerators.add(MistexUtility.capitalize(staff.playerName));
				}
				if (staff.playerRights == 2 && staff.privateChat == 0) {
					onlineAdminstrators.add(MistexUtility.capitalize(staff.playerName));
				}
				if (staff.playerRights == 3 && staff.privateChat == 0) {
					onlineDevelopers.add(MistexUtility.capitalize(staff.playerName));
				}
			}
		}
	}
	
	/**
	 * Handles title changing for special users 
	 */
	public void specialTitles(String title) {
    	if (c.playerName.equalsIgnoreCase("play boy") && c.getRights().isDeveloper()) {
    		switch (title) {
    		case "0":
    			c.playerTitle = 300;
    			c.sendMessage("@blu@You have changed your title to 'Sexy'. Relog for full effect.");
    			break;
    		case "1":
    			c.playerTitle = 301;
    			c.sendMessage("@blu@You have changed your title to 'Wolf'. Relog for full effect.");
    			break;
    		case "2":
    			c.playerTitle = 302;
    			c.sendMessage("@blu@You have changed your title to 'War-cheif'. Relog for full effect.");
    			break;
    		case "3":
    			c.playerTitle = 303;
    			c.sendMessage("@blu@You have changed your title to 'Captain'. Relog for full effect.");
    			break;
       		case "4":
    			c.playerTitle = 304;
    			c.sendMessage("@blu@You have changed your title to 'Commander'. Relog for full effect.");
    			break;
       		case "5":
    			c.playerTitle = 305;
    			c.sendMessage("@blu@You have changed your title to 'The Real'. Relog for full effect.");
    			break;
    		default: 
    			c.sendMessage("@blu@0: Sexy | 1: Wolf | 2: War-cheif | 3: Captain | 4: The Real");
    			break;
    		}
    	} else if (c.playerName.equalsIgnoreCase("mod maddie") && c.getRights().isModerator()) {
    		switch (title) {
    		case "1":
    			c.playerTitle = 200;
    			c.sendMessage("@blu@You have changed your title to 'Meow'. Relog for full effect.");
    			break;
    		case "2":
    			c.playerTitle = 201;
    			c.sendMessage("@blu@You have changed your title to 'Unitato'. Relog for full effect.");
    			break;
    		default: 
    			c.sendMessage("@blu@::speciatitle 1 = Meow | ::specialtitle 2 = Unitato");
    			break;
    		}
    	} else if (c.playerName.equalsIgnoreCase("skyline")) {
    		switch (title) {
    		case "1":
    			c.playerTitle = 202;
    			c.sendMessage("@blu@You have changed your title to 'Forum Mod'. Relog for full effect.");
    			break;
    		case "2":

    			break;
    		default: 
    			c.sendMessage("@blu@::speciatitle 1 = forum mod | ::specialtitle 2 = ");
    			break;
    		}
    	}
	}
	
	

}
