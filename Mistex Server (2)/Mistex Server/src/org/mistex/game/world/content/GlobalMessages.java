package org.mistex.game.world.content;

import org.mistex.game.MistexUtility;
import org.mistex.game.world.World;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.task.Task;

public class GlobalMessages extends Task {
	
	/**
	 * The news color text
	 */
	private String newsColor = "<col=910D0D>";
	
	/**
	 * The news icon
	 */
	private String iconNumber = "<img=7>";
	
	/**
	 * The news title
	 */
	private String messageTitle = "News";

	/**
	 * The random messages that news will send
	 */
	private static final String[] ANNOUNCEMENTS = {
			"Did you know Mistex was released on May 9th 2014!",
			"Have an interesting idea for Mistex? Suggest it on the forums!",
			"Please report all bugs/exploits/glitches to a staff member immediately!",
			"Check out our forums at www.mistex.org or type ::forums!",
			"Voting will help the server populate!",
			"Please be sure to follow all the rules, ::rules",
			"You can add protection to your account by setting a bank pin!",
			"You can check all recent updates on forums or type ::updates",
			"Did you know the original name for Mistex was Contrast?",
			"Donators get double slayer, thieving, and pvp points/rewards!",
			"Play Boy loves everyone<3!",
			"Did you know completing achievements unlocks player titles? ",
	};

	/**
	 * The timer for the message
	 */
	public GlobalMessages() {
		super(175, false);
	}
	
	/**
	 * Executes the actual message
	 */
	@Override
	public void execute() {
		final String announcement = ANNOUNCEMENTS[MistexUtility.random(ANNOUNCEMENTS.length - 1)];
		yell(iconNumber+""+newsColor+""+messageTitle+": " + announcement);
	}
	
	/**
	 * Yells the message to all players in server
	 * @param string
	 */
    public void yell(String string) {
		for (int j = 0; j < World.players.length; j++) {
			if (World.players[j] != null) {
				Client c2 = (Client) World.players[j];
				c2.sendMessage(string);
			}
		}
	}
}
