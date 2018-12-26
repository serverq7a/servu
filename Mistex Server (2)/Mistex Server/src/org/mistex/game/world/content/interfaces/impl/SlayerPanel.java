package org.mistex.game.world.content.interfaces.impl;

import org.mistex.game.MistexUtility;
import org.mistex.game.world.content.interfaces.InterfaceText;
import org.mistex.game.world.npc.NPCHandler;
import org.mistex.game.world.player.Client;

public class SlayerPanel extends InterfaceText {
	
	public SlayerPanel(Client player) {
		super(player);
	}

	private final String[] text = {
			"Solo:",
			"@bla@Task: @blu@"+ (player.slayerTask == 0 ? "None" : NPCHandler.getNpcListName(player.slayerTask).replaceAll("_", " ")) + "",
			"@bla@Amount: @blu@"+player.taskAmount,
			"@bla@Points:  @blu@"+MistexUtility.format(player.slayerPoints),
			"",
			"Duo:",
			"@bla@Partner: @blu@"+((player.duoPartner != null && player.duoPartner.get() != null) ? player.duoPartner.get().playerName : "No one"),
			"@bla@Task: @blu@"+ (player.duoTask == 0 ? "None" : NPCHandler.getNpcListName(player.duoTask).replaceAll("_", " ")) + "",
			"@bla@Amount: @blu@"+(player.duoTaskAmount > 0 ? player.duoTaskAmount : 0),
			"@bla@Points: @blu@"+MistexUtility.format(player.duoPoints),
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
