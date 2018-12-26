package org.mistex.game.world.player.command;

import org.mistex.game.Mistex;
import org.mistex.game.MistexConfiguration;
import org.mistex.game.world.PunishmentHandler;
import org.mistex.game.world.World;
import org.mistex.game.world.player.Client;

public class ModeratorCommands {

	public static void moderatorCommands(Client c, String playerCommand) {
        
        if (playerCommand.startsWith("unipmute")) {
            try {
                String playerToBan = playerCommand.substring(9);
                for (int i = 0; i < MistexConfiguration.MAX_PLAYERS; i++) {
                    if (World.players[i] != null) {
                        if (World.players[i].playerName.equalsIgnoreCase(playerToBan)) {
                            PunishmentHandler.unIPMuteUser(World.players[i].connectedFrom);
                            c.sendMessage("You have Un Ip-Muted the user: " + World.players[i].playerName);
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                c.sendMessage("Player Must Be Offline.");
            }
        }
        
        if (playerCommand.startsWith("ipban")) {
            try {
                String playerToBan = playerCommand.substring(6);
                for (int i = 0; i < MistexConfiguration.CANTHARM.length; i++){
    				if (playerToBan.startsWith(MistexConfiguration.CANTHARM[i])) {
    					c.sendMessage("<col=124123>Error processing your crap.");
  
    					return;
    				}
    			} 
                for (int i = 0; i < MistexConfiguration.MAX_PLAYERS; i++) {
                    if (World.players[i] != null) {
                        if (World.players[i].playerName.equalsIgnoreCase(playerToBan)) {
                            PunishmentHandler.addIpToBanList(World.players[i].connectedFrom);
                            PunishmentHandler.addIpToFile(World.players[i].connectedFrom);
                            c.sendMessage("You have IP banned the user: " + World.players[i].playerName + " with the host: " + World.players[i].connectedFrom);
                            Client c2 = (Client) World.players[i];
                            World.players[i].disconnected = true;
                            c2.sendMessage(" " + c2.playerName + " Got IpBanned By " + c.playerName + ".");
                        }
                    }
                }
            } catch (Exception e) {
                c.sendMessage("Player Must Be Offline.");
            }
        }
        
        if (playerCommand.startsWith("unban")) {
            try {
                String playerToBan = playerCommand.substring(6);
                PunishmentHandler.removeNameFromBanList(playerToBan);
                c.sendMessage(playerToBan + " has been unbanned.");
            } catch (Exception e) {
                c.sendMessage("Player Must Be Offline.");
            }
        }

        if (playerCommand.startsWith("movehome")) {
            try {
                String playerToBan = playerCommand.substring(9);  
                for (int i = 0; i < MistexConfiguration.CANTHARM.length; i++){
    				if (playerToBan.startsWith(MistexConfiguration.CANTHARM[i])) {
    					c.sendMessage("<col=124123>Error processing your crap.");
    					return;
    				}
    			}
                for (int i = 0; i < MistexConfiguration.MAX_PLAYERS; i++) {
                    if (World.players[i] != null) {
                        if (World.players[i].playerName.equalsIgnoreCase(playerToBan)) {
                            Client c2 = (Client) World.players[i];
                            c2.teleportToX = 3086;
                            c2.teleportToY = 3493;
                            c2.heightLevel = c.heightLevel;
                            c.sendMessage("You have teleported " + c2.playerName + " to Home");
                            c2.sendMessage("You have been teleported to home");
                        }
                    }
                }
            } catch (Exception e) {
                c.sendMessage("Player Must Be Offline.");
            }
        }
        
        if (playerCommand.startsWith("afk")) {
            String Message = "<shad=6081134>[" + c.playerName + "] is now AFK, don't message me; I won't reply";
            for (int j = 0; j < World.players.length; j++) {
                if (World.players[j] != null) {
                    Client c2 = (Client) World.players[j];
                    c2.sendMessage(Message);
                }
            }
        }
        
        if (playerCommand.startsWith("getid")) {
            String a[] = playerCommand.split(" ");
            String name = "";
            int results = 0;
            for (int i = 1; i < a.length; i++)
                name = name + a[i] + " ";
            name = name.substring(0, name.length() - 1);
            c.sendMessage("Searching: " + name);
            for (int j = 0; j < Mistex.itemHandler.ItemList.length; j++) {
                if (Mistex.itemHandler.ItemList[j] != null)
                    if (Mistex.itemHandler.ItemList[j].itemName.replace("_", " ").toLowerCase().contains(name.toLowerCase())) {
                        c.sendMessage("<col=255>" + Mistex.itemHandler.ItemList[j].itemName.replace("_", " ") + " - " + Mistex.itemHandler.ItemList[j].itemId);
                        results++;
                    }
            }
            c.sendMessage(results + " results found...");
        }
        
        if (playerCommand.startsWith("jail")) {
            try {
                String playerToBan = playerCommand.substring(5);
                for (int i = 0; i < MistexConfiguration.CANTHARM.length; i++){
    				if (playerToBan.startsWith(MistexConfiguration.CANTHARM[i])) {
    					c.sendMessage("<col=124123>Error processing your crap.");
    					return;
    				}
    			}
                for (int i = 0; i < MistexConfiguration.MAX_PLAYERS; i++) {
                    if (World.players[i] != null) {
                        if (World.players[i].playerName.equalsIgnoreCase(playerToBan)) {
                            Client c2 = (Client) World.players[i];
                            c2.teleportToX = 3102;
                            c2.teleportToY = 9516;
                            //c2.Jail = true;
                            c2.sendMessage("You have been jailed by " + c.playerName + "");
                            c.sendMessage("Successfully Jailed " + c2.playerName + ".");
                        }
                    }
                }
            } catch (Exception e) {
                c.sendMessage("Player Must Be Offline.");
            }
        }
        
        if (playerCommand.startsWith("mute")) {
            try {
                String playerToBan = playerCommand.substring(5);
                for (int i = 0; i < MistexConfiguration.CANTHARM.length; i++){
    				if (playerToBan.startsWith(MistexConfiguration.CANTHARM[i])) {
    					c.sendMessage("<col=124123>Error processing your crap.");
    					return;
    				}
    			}  
                PunishmentHandler.addNameToMuteList(playerToBan);
                for (int i = 0; i < MistexConfiguration.MAX_PLAYERS; i++) {
                    if (World.players[i] != null) {
                        if (World.players[i].playerName.equalsIgnoreCase(playerToBan)) {
                            Client c2 = (Client) World.players[i];
                            c2.sendMessage("You have been muted by: " + c.playerName);
                            c2.sendMessage(" " + c2.playerName + " Got Muted By " + c.playerName + ".");
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                c.sendMessage("Player Must Be Offline.");
            }
        }
        
        if (playerCommand.startsWith("unmute")) {
            try {
                String playerToBan = playerCommand.substring(7);
                PunishmentHandler.unMuteUser(playerToBan);
                PunishmentHandler.removeNameFromMuteList(playerToBan);
                c.sendMessage("You have unmuted " + playerToBan + ".");
            } catch (Exception e) {
                c.sendMessage("Player Must Be Offline.");

            }
        }

        if (playerCommand.startsWith("xteleto")) {
            String name = playerCommand.substring(8);
            for (int i = 0; i < MistexConfiguration.MAX_PLAYERS; i++) {
                if (World.players[i] != null) {
                    if (World.players[i].playerName.equalsIgnoreCase(name)) {
                        c.getPA().movePlayer(World.players[i].getX(), World.players[i].getY(), World.players[i].heightLevel);
                    }
                }
            }
        }
        
        if (playerCommand.startsWith("xteletome")) {
            try {
                String playerToTele = playerCommand.substring(10);
                for (int i = 0; i < MistexConfiguration.MAX_PLAYERS; i++) {
                    if (World.players[i] != null) {
                        if (World.players[i].playerName.equalsIgnoreCase(playerToTele)) {
                            Client c2 = (Client) World.players[i];
                            c2.sendMessage("You have been teleported to " + c.playerName);
                            c2.getPA().movePlayer(c.getX(), c.getY(), c.heightLevel);
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                c.sendMessage("Player Must Be Offline.");
            }
        }
        
        if (playerCommand.startsWith("kick") && playerCommand.charAt(4) == ' ') {
            try {
                String playerToBan = playerCommand.split("kick ")[1];
                for (int i = 0; i < MistexConfiguration.CANTHARM.length; i++){
    				if (playerToBan.equalsIgnoreCase(MistexConfiguration.CANTHARM[i])) {
    					c.sendMessage("<col=124123>Error processing your crap.");
    					return;
    				}
    			}
                for (int i = 0; i < MistexConfiguration.MAX_PLAYERS; i++) {
                    if (World.players[i] != null) {
                        if (World.players[i].playerName.equalsIgnoreCase(playerToBan)) {
                            World.players[i].disconnected = true;
                        }
                    }
                }
            } catch (Exception e) {
                c.sendMessage("Player Must Be Offline.");
            }
        }
        
        if (playerCommand.startsWith("ban") && playerCommand.charAt(3) == ' ') {
            try {
                String playerToBan = playerCommand.substring(4);
                for (int i = 0; i < MistexConfiguration.CANTHARM.length; i++){
    				if (playerToBan.startsWith(MistexConfiguration.CANTHARM[i])) {
    					c.sendMessage("<col=124123>Error processing your crap.");
    					return;
    				}
    			}
                PunishmentHandler.addNameToBanList(playerToBan);
                PunishmentHandler.addNameToFile(playerToBan);
                for (int i = 0; i < MistexConfiguration.MAX_PLAYERS; i++) {
                    if (World.players[i] != null) {
                        if (World.players[i].playerName.equalsIgnoreCase(playerToBan)) {
                            World.players[i].disconnected = true;
                            Client c2 = (Client) World.players[i];
                            c2.sendMessage(" " + c2.playerName + " Got Banned By " + c.playerName + ".");
                        }
                    }
                }
            } catch (Exception e) {
                c.sendMessage("Player Must Be Offline.");
            }
        }
        
        if (playerCommand.startsWith("unban")) {
            try {
                String playerToBan = playerCommand.substring(6);
                PunishmentHandler.removeNameFromBanList(playerToBan);
                c.sendMessage(playerToBan + " has been unbanned.");
            } catch (Exception e) {
                c.sendMessage("Player Must Be Offline.");
            }
        }
        
        if (playerCommand.startsWith("ipmute")) {
            try {
                String playerToBan = playerCommand.substring(7);
                for (int i = 0; i < MistexConfiguration.CANTHARM.length; i++){
    				if (playerToBan.startsWith(MistexConfiguration.CANTHARM[i])) {
    					c.sendMessage("<col=124123>Error processing your crap.");
    					return;
    				}
    			}
                for (int i = 0; i < MistexConfiguration.MAX_PLAYERS; i++) {
                    if (World.players[i] != null) {
                        if (World.players[i].playerName.equalsIgnoreCase(playerToBan)) {
                            PunishmentHandler.addIpToMuteList(World.players[i].connectedFrom);
                            c.sendMessage("You have IP Muted the user: " + World.players[i].playerName);
                            Client c2 = (Client) World.players[i];
                            c2.sendMessage("You have been muted by: " + c.playerName);
                            c2.sendMessage(" " + c2.playerName + " Got IpMuted By " + c.playerName + ".");
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                c.sendMessage("Player Must Be Offline.");
            }
        }
        
        if (playerCommand.startsWith("unipmute")) {
            try {
                String playerToBan = playerCommand.substring(9);
                for (int i = 0; i < MistexConfiguration.CANTHARM.length; i++){
    				if (playerToBan.startsWith(MistexConfiguration.CANTHARM[i])) {
    					c.sendMessage("<col=124123>Error processing your crap.");
    					return;
    				}
    			} 
                for (int i = 0; i < MistexConfiguration.MAX_PLAYERS; i++) {
                    if (World.players[i] != null) {
                        if (World.players[i].playerName.equalsIgnoreCase(playerToBan)) {
                            PunishmentHandler.unIPMuteUser(World.players[i].connectedFrom);
                            c.sendMessage("You have Un Ip-Muted the user: " + World.players[i].playerName);
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                c.sendMessage("Player Must Be Offline.");
            }
        }
        
        if (playerCommand.startsWith("unjail")) {
            try {
                String playerToBan = playerCommand.substring(7);
                for (int i = 0; i < MistexConfiguration.MAX_PLAYERS; i++) {
                    if (World.players[i] != null) {
                        if (World.players[i].playerName.equalsIgnoreCase(playerToBan)) {
                            Client c2 = (Client) World.players[i];
                            c2.teleportToX = 3086;
                            c2.teleportToY = 3493;
                           // c2.monkeyk0ed = 0;
                          //  c2.Jail = false;
                            c2.sendMessage("You have been unjailed by " + c.playerName + ".");
                            c.sendMessage("Successfully unjailed " + c2.playerName + ".");
                        }
                    }
                }
            } catch (Exception e) {
                c.sendMessage("Player Must Be Offline.");
            }
        }

    }

	
}
