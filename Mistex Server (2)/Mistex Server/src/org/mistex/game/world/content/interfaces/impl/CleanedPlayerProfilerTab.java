package org.mistex.game.world.content.interfaces.impl;

import org.mistex.game.world.content.interfaces.InterfaceText;
import org.mistex.game.world.player.Client;

public class CleanedPlayerProfilerTab extends InterfaceText {
	
	public CleanedPlayerProfilerTab(Client player) {
		super(player);
	}

	private final String[] text = {
				player.canViewProfile == true ? "Disable Profile" : "Enable Profile",
				"Profile Settings",
				"View Your Profile",
				"Set Profile Colour",
				"",
				"",
				"To view a profile type:",
				"::viewprofile name",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",

			};

	@Override
	protected String[] text() {
		return text;
	}

	@Override
	protected int startingLine() {
		return 29004;
	}


}
