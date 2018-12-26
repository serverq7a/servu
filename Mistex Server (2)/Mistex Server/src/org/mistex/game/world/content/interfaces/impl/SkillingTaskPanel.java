package org.mistex.game.world.content.interfaces.impl;

import org.mistex.game.world.content.interfaces.InterfaceText;
import org.mistex.game.world.player.Client;

public class SkillingTaskPanel extends InterfaceText {
	
	public SkillingTaskPanel(Client player) {
		super(player);
	}
	
	public static void handlePanel(Client player) {

	}
	

	private final String[] text = {
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
		return 8145;
	}


}
