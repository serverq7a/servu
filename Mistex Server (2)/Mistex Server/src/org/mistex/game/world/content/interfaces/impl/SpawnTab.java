package org.mistex.game.world.content.interfaces.impl;

import org.mistex.game.world.content.interfaces.InterfaceText;
import org.mistex.game.world.player.Client;

public class SpawnTab extends InterfaceText {
	
	public SpawnTab(Client player) {
		super(player);
	}

	private final String[] text = {
			"<col=0DAFD4>Spawn Tab:",
			"",
			"<col=EDDC1A><currentY>Melee:",
			"",
			"Classic",
			"Zerker",
			"Pure",
			"F2P",
			"",
			"<col=EDDC1A><currentY>Range:",
			"",
			"Classic",
			"Tank",
			"Pure",
			"F2P",
			"",
			"<col=EDDC1A><currentY>Magic:",
			"",
			"Classic",
			"Hybrid",
			"Pure",
			"F2P",
			"",
			"<col=EDDC1A><currentY>Miscellaneous:",
			"",
			"Food",
			"Potions",
			"Barrage Runes",
			"Vengeance Runes",
			"All Runes",
			};

	@Override
	protected String[] text() {
		return text;
	}

	@Override
	protected int startingLine() {
		return 46404;
	}


}
