package org.mistex.game.world.content.interfaces.impl;

import java.text.DecimalFormat;

import org.mistex.game.MistexUtility;
import org.mistex.game.world.content.interfaces.InterfaceText;
import org.mistex.game.world.player.Client;

public class PlayerProfileTab extends InterfaceText {
	
	public PlayerProfileTab(Client player) {
		super(player);
	}
	
	DecimalFormat df = new DecimalFormat("#.##");
	double ratio = ((double) player.KC) / ((double) player.DC);

	private final String[] text = {
	   		"Enable Profile",
	   		"Profile Settings", 
	        "Clear", 
	   		"", 
	   		"@lre@Profile: @"+player.profileColour+"@"+MistexUtility.capitalize(player.playerName)+"", 
	   		"", 
	   		"<currentY>@or2@Statistics:", 
	   		"@lre@Pk Points: @"+player.profileColour+"@"+MistexUtility.format(player.pkPoints)+"", 
	   		"@lre@Kills: @"+player.profileColour+"@"+MistexUtility.format(player.KC)+"", 
	   		"@lre@Deaths: @"+player.profileColour+"@"+MistexUtility.format(player.DC)+"", 
	   		"@lre@KDR: @"+player.profileColour+"@" + df.format(ratio) ,
	   		"@lre@Killstreak: @"+player.profileColour+"@"+MistexUtility.format(player.killStreak)+"",
	   		"@lre@Voting Points: @"+player.profileColour+"@"+MistexUtility.format(player.votingPoints)+"", 
	   		"@lre@Donation Points: @"+player.profileColour+"@"+MistexUtility.format(player.donatorPoints)+"",
	   		"@lre@Achievements Completed: @"+player.profileColour+"@"+MistexUtility.format(player.achievementsCompleted)+"", 
	   		"@lre@Achievement Points: @"+player.profileColour+"@"+MistexUtility.format(player.achievementPoints)+"", 
	   		"", 
	   		"<currentY>@or2@Details:", 
	   		"@lre@Name: @"+player.profileColour+"@"+player.name, 
	   		"@lre@Age: @"+player.profileColour+"@"+player.ageProfile, 
	   		"@lre@Birthplace: @"+player.profileColour+"@"+player.bplaceProfile+"",
	   		"@lre@About Me:", 
	   		"@"+player.profileColour+"@"+player.aboutMe+"", 
	   		"", 
	   		"<currentY>@or2@Other:", 
	   		"@lre@Food Eaten: @"+player.profileColour+"@"+MistexUtility.format(player.foodEaten)+"", 
	   		"@lre@Potions Drank: @"+player.profileColour+"@"+MistexUtility.format(player.potionsDrank)+"",
	   		"@lre@Duels Won: @"+player.profileColour+"@"+MistexUtility.format(player.duelsWon)+"", 
	   		"@lre@Home Teleports: @"+player.profileColour+"@"+MistexUtility.format(player.teleportedHome)+"",
	   		"@lre@Trivia Won: @"+player.profileColour+"@"+MistexUtility.format(player.triviaWon)+"", 
	   		"@lre@Altar Prays: @"+player.profileColour+"@"+MistexUtility.format(player.altarPrayers)+"",
	   		"@lre@Profile Views: @"+player.profileColour+"@"+MistexUtility.format(player.profileViews)+"", 
	   		"@lre@Statue Plays: @"+player.profileColour+"@"+MistexUtility.format(player.pkStatuePlays)+"", 
	   		"@lre@Trades: @"+player.profileColour+"@"+MistexUtility.format(player.tradesCompleted)+"", 
	   		"@lre@Appearance Change: @"+player.profileColour+"@"+MistexUtility.format(player.appearancesChanged)+"",
	   		"@lre@Corp Kills @"+player.profileColour+"@"+MistexUtility.format(player.corpKills)+"", 
	   		"@lre@Rock Crab Kills @"+player.profileColour+"@"+MistexUtility.format(player.rockCrabKills)+"",
	   		"@lre@Tztok Jad Kills @"+player.profileColour+"@"+MistexUtility.format(player.jadKills)+"",
            "@lre@Tztok Jad Kills: @" + player.profileColour + "@" + MistexUtility.format(player.jadKills) + "",
            "@lre@Food Cooked: @" + player.profileColour + "@" + MistexUtility.format(player.foodCooked) + "",
            "@lre@Logs Burned: @" + player.profileColour + "@" + MistexUtility.format(player.logsBurned) + "",
            "@lre@Ores Mined: @" + player.profileColour + "@" + MistexUtility.format(player.oresRecieved) + "",
            "@lre@Total Prestiges: @" + player.profileColour + "@" + MistexUtility.format(player.totalPrestiges) + "",
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
