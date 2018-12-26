package org.mistex.game.world.player.command;

import org.mistex.game.Mistex;
import org.mistex.game.MistexUtility;
import org.mistex.game.world.PunishmentHandler;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.PlayerConfiguration;

public class DonatorCommands {
	
    @SuppressWarnings("static-access")
	public static void donatorCommands(Client c, String playerCommand) {
    	
        if (playerCommand.startsWith("dzone") || playerCommand.startsWith("donatorzone") || playerCommand.startsWith("dplace")) {
 			c.getPA().startTeleport(PlayerConfiguration.DONATORZONE_X + MistexUtility.random(5), PlayerConfiguration.DONATORZONE_Y + MistexUtility.random(5), 0, "modern");
 			c.sendMessage("<col=E319BB>Welcome "+c.getRights().determineIcon()+""+MistexUtility.capitalize(c.playerName)+", to the donator zone.");
 			c.sendMessage("<col=E319BB>We would like to thank you for your kind donation!");
        }
        
        if (playerCommand.startsWith("yell")) {
        	c.sendMessage("The yell command has changed by using '>>' characters instead of ::yell.");
        	c.sendMessage("If you wish to yell, use this as a framework: ' >> Hello Mistex! '");
        }
        
        if (playerCommand.startsWith(">>")) {
     			if (!PunishmentHandler.isMuted(c)) {
     				String string = playerCommand.substring(2);
     				string = MistexUtility.formatSentence(string.trim());
     				if (string.contains("@") || string.contains("<img=") || string.contains("<col=") && !(c.playerRights == 3)) {
     					c.sendMessage("You may not use that symbol!");
     					return;
     				}
     				if (PunishmentHandler.isMuted(c)) {
     		            c.sendMessage("You are muted! You may not yell.");
     		            return;
     				}
     				for (int j = 0; j < Mistex.playerHandler.players.length; j++) {
     					if (Mistex.playerHandler.players[j] != null) {
     						Client c2 = (Client)Mistex.playerHandler.players[j];
     						if (c2.wantYellChannel)
     						c2.sendMessage(c.getPA().getYellRank() + " " + MistexUtility.capitalize(c.playerName) + ": " + string);
     					}
     				}
     			}
     		}
     		
     		if (playerCommand.startsWith("settag")) {
				try {
					String newYellTag = MistexUtility.optimizeText(playerCommand.substring(7));
						if (newYellTag.length() > 8 && !(c.playerRights == 3)){
							c.sendMessage("Max length for yell tags is 8 characters!");
							return;
						}
						if (newYellTag.contains("@") || newYellTag.contains("<") || newYellTag.contains(">") && !(c.playerRights == 3)){
							c.sendMessage("Color codes not supported");
							return;
						}
						if (newYellTag.equalsIgnoreCase("ceo") || newYellTag.equalsIgnoreCase("owner") || newYellTag.equalsIgnoreCase("admin") ||
							newYellTag.equalsIgnoreCase("administrator") || newYellTag.equalsIgnoreCase("developer") || newYellTag.equalsIgnoreCase("mod") ||
							newYellTag.equalsIgnoreCase("moderator") || newYellTag.equalsIgnoreCase(".com") || newYellTag.equalsIgnoreCase("nigger") ||
							newYellTag.equalsIgnoreCase("bitch") || newYellTag.equalsIgnoreCase("fuck") || newYellTag.equalsIgnoreCase("nigger") ||
							newYellTag.equalsIgnoreCase("whore") || newYellTag.equalsIgnoreCase("cunt") || newYellTag.equalsIgnoreCase("motherfucker") ||
							newYellTag.equalsIgnoreCase("support") || newYellTag.equalsIgnoreCase("helper") || newYellTag.equalsIgnoreCase("sponsor") ||
							newYellTag.equalsIgnoreCase("producer") || newYellTag.equalsIgnoreCase("manager") || newYellTag.equalsIgnoreCase("cock") && !(c.playerRights == 3)){
							c.sendMessage("You may not set your tag to that!");
							return;
						}
						if (newYellTag.startsWith("reset") || newYellTag.startsWith("default")) {
							c.yellTag = "Mistex";
							c.sendMessage("Successfully changed your yell tag to: "+c.yellTag);
							return;
						}
						c.yellTag = newYellTag;
						c.sendMessage("Successfully changed your yell tag to: "+c.yellTag);
						
				} catch(Exception e) {
					c.sendMessage("Wrong syntax use as ::yelltag youryelltag");
				}
			}

    }

}
