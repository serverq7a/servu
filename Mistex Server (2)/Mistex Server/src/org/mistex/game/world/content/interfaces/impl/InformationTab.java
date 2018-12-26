package org.mistex.game.world.content.interfaces.impl;

import java.text.DecimalFormat;

import org.mistex.game.Mistex;
import org.mistex.game.MistexUtility;
import org.mistex.game.world.content.interfaces.InterfaceText;
import org.mistex.game.world.player.Client;

public class InformationTab extends InterfaceText {
	
	public InformationTab(Client player) {
		super(player);
	}
	
	DecimalFormat df = new DecimalFormat("#.##");
	double ratio = ((double) player.KC) / ((double) player.DC);

	private final String[] text = {
			Mistex.vpsTime(),
			"@lre@Server Votes: @gre@"+Mistex.voteCount,
			(Mistex.LastVoter == "" ? "@lre@Last Voter: @gre@None" : "@lre@Last Voter: @gre@"+Mistex.LastVoter+"") ,
			"",
			"@or2@User Information:",
			"",
			"@lre@Name: @gre@" + MistexUtility.capitalize(player.playerName),
			"@lre@Rank: "+player.getRights().determineIcon()+""+player.getRights().determineRank(),
			"@lre@Date Joined: @gre@"+player.joinDate,
			"@lre@Total Play Time:",
			player.getPA().getSmallPlaytime(),
			"",
			"<currentY>@or2@Point Statistics:",
			"",
			"@lre@[@gre@" + MistexUtility.format(player.KC) + "@lre@] Kills",
			"@lre@[@gre@" + MistexUtility.format(player.DC) + "@lre@] Deaths",
			"@lre@[@gre@" + df.format(ratio) + "@lre@] KDR",
			"@lre@[@gre@" + player.killStreak + "@lre@] Killstreak",
			"@lre@[@gre@" + MistexUtility.format(player.pkPoints) + "@lre@] PvP Points",
			"@lre@[@gre@" + MistexUtility.format(player.donatorPoints) + "@lre@] Donator Points",
			"@lre@[@gre@" + MistexUtility.format(player.dungTokens) + "@lre@] Dungeoneering Points",
			"@lre@[@gre@" + MistexUtility.format(player.votingPoints)+ "@lre@] Vote Points",
			"@lre@[@gre@" + MistexUtility.format(player.pcPoints) +"@lre@] Pest Control Points",
			"@lre@[@gre@" + MistexUtility.format(player.prestigePoints) +"@lre@] Prestige Points",
			"@lre@[@gre@" + MistexUtility.format(player.slayerPoints) +"@lre@] Slayer Points",
			"@lre@[@gre@" + MistexUtility.format(player.skillingPoints) +"@lre@] Skilling Points",
			"",
			"<currentY>@or2@Panels",
			"",
			"Slayer",
			"Skilling Task",
			"",
			"<currentY>@or2@Quests:",
			"@lre@Completed: @gre@"+player.questCompleted+"@lre@/@red@4",
			"",
			//(player.gameOfChess >= 10 ? "@gre@" : player.gameOfChess >= 1 ? "@yel@" : "") + "A Game of Chess",
			(player.AcQuest >= 10 ? "@gre@" : player.AcQuest >= 1 ? "@yel@" : "") + "Avoiding Catastrophe",
			(player.cookAss >= 3 ? "@gre@" : player.cookAss >= 1 ? "@yel@" : "") + "Cook's Assistant",
			(player.doricsQuest >= 3 ? "@gre@" : player.doricsQuest >= 1 ? "@yel@" : "") + "Dorics Quest",
			};

	@Override
	protected String[] text() {
		return text;
	}

	@Override
	protected int startingLine() {
		return 35026;
	}


}
