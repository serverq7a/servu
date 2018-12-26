package org.mistex.game.world.content.interfaces.impl;

import org.mistex.game.world.content.interfaces.InterfaceText;
import org.mistex.game.world.player.Client;

public class AchievementTab extends InterfaceText {

    public AchievementTab(Client player) {
        super(player);
    }

    private final String[] text = {
    		"@lre@Completed: @gre@" + player.achievementsCompleted + "@lre@/@red@" + player.totalAchievements,
            "",
            "",
            // player.achievementsCompleted == player.totalAchievements ? "@gre@Mystic" : "Mystic",
            "",
            (player.playerReported == true ? "@gre@" : "") + "Law Abiding Citizen",
        	(player.foodEaten >= 1000 ? "@gre@" : player.foodEaten >= 1 ? "@yel@" : "") + "Munchies",
        	(player.potionsDrank >= 1000 ? "@gre@" : player.potionsDrank >= 1 ? "@yel@" : "") + "Mr Thirsty",
        	(player.KC >= 1000 ? "@gre@" : player.KC >= 1 ? "@yel@" : "") + "Beast",
        	(player.DC >= 100 ? "@gre@" : player.DC >= 1 ? "@yel@" : "") + "Weakling",
            (player.pkStatuePlays >= 100 ? "@gre@" : player.pkStatuePlays >= 1 ? "@yel@" : "") + "The Gamer",
        	(player.duelsWon >= 100 ? "@gre@" : player.duelsWon >= 1 ? "@yel@" : "") + "The Duelist",
        	(player.teleportedHome >= 500 ? "@gre@" : player.teleportedHome >= 1 ? "@yel@" : "") + "Home Sick",
        	(player.profileViews >= 250 ? "@gre@" : player.profileViews >= 1 ? "@yel@" : "") + "The Viewer",		
        	(player.tradesCompleted >= 500 ? "@gre@" : player.tradesCompleted >= 1 ? "@yel@" : "") + "Trader",		
        	(player.appearancesChanged >= 100 ? "@gre@" : player.appearancesChanged >= 1 ? "@yel@" : "") + "Self-Conscious",			
        	(player.corpKills >= 100 ? "@gre@" : player.corpKills >= 1 ? "@yel@" : "") + "Corporeal Hunter",			
        	 player.hit900 == true ? "@gre@Over 900" : "Over 900",		
        	(player.triviaWon >= 100 ? "@gre@" : player.triviaWon >= 1 ? "@yel@" : "") + "Brainiac",		       	
        	(player.altarPrayed >= 1000 ? "@gre@" : player.altarPrayed >= 1 ? "@yel@" : "") + "Holy Monk",	      	
        	(player.getLevelForXP(player.playerXP[19]) >= 99 ? "@gre@" : player.getLevelForXP(player.playerXP[19]) >= 2 ? "@yel@" : "") + "Master Baiter",  		
        	(player.rockCrabKills >= 100 ? "@gre@" : player.rockCrabKills >= 1 ? "@yel@" : "") + "Crabs",        			      			
        	(player.jadKills >= 15 ? "@gre@" : player.jadKills >= 1 ? "@yel@" : "") + "Jad Killer",     
        	(player.yewsCut >= 1000 ? "@gre@" : player.yewsCut >= 1 ? "@yel@" : "") + "Nazi",   
        	(player.foodCooked >= 10000 ? "@gre@" : player.foodCooked >= 1 ? "@yel@" : "") + "Iron Chef",   
        	(player.logsBurned >= 4200 ? "@gre@" : player.logsBurned >= 1 ? "@yel@" : "") + "Blazed",
        	(player.oresRecieved >= 1500 ? "@gre@" : player.oresRecieved >= 1 ? "@yel@" : "") + "Oreo",
        	(player.totalPrestiges >= 10 ? "@gre@" : player.totalPrestiges >= 1 ? "@yel@" : "") + "Wowza",
    };

    @Override
    protected String[] text() {
        return text;
    }

    @Override
    protected int startingLine() {
        return 31004;
    }


}