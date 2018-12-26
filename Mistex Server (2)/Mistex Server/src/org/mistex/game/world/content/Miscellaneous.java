package org.mistex.game.world.content;

import org.mistex.game.Mistex;
import org.mistex.game.MistexConfiguration;
import org.mistex.game.MistexUtility;
import org.mistex.game.world.PunishmentHandler;
import org.mistex.game.world.World;
import org.mistex.game.world.content.achievement.AchievementHandler;
import org.mistex.game.world.content.achievement.AchievementList;
import org.mistex.game.world.player.Client;


public class Miscellaneous {
	
	public static void givePlayerTitle(Client c) {
		switch (c.viewingAchievement) {
			case 1:
				if (c.playerReported) {
					c.playerTitle = 7;
					c.sendMessage("<col=FC0000>Congratulations! You have redeemed the title 'Law Men'.");
					c.sendMessage("<col=FC0000>Please re-log for this to take effect.");
				} else {
					c.sendMessage("No no, not for you :p");
				}
			break;
			case 2:
				if (c.foodEaten >= 1000) {
					c.playerTitle = 8;
					c.sendMessage("<col=FC0000>Congratulations! You have redeemed the title 'Munchy'.");
					c.sendMessage("<col=FC0000>Please re-log for this to take effect.");
				} else {
					c.sendMessage("No no, not for you :p");
				}
			break;
			case 3:
				if (c.potionsDrank >= 1000) {
					c.playerTitle = 9;
					c.sendMessage("<col=FC0000>Congratulations! You have redeemed the title 'Thirsty'.");	
					c.sendMessage("<col=FC0000>Please re-log for this to take effect.");
				} else {
					c.sendMessage("No no, not for you :p");
				}
			break;
			case 4:
				if (c.KC >= 1000) {
					c.playerTitle = 10;
					c.sendMessage("<col=FC0000>Congratulations! You have redeemed the title 'Beast'.");	
					c.sendMessage("<col=FC0000>Please re-log for this to take effect.");
				} else {
					c.sendMessage("No no, not for you :p");
				}
			break;
			case 5:
				if (c.DC >= 100) {
					c.playerTitle = 11;
					c.sendMessage("<col=FC0000>Congratulations! You have redeemed the title 'Weak'.");		
					c.sendMessage("<col=FC0000>Please re-log for this to take effect.");
				} else {
					c.sendMessage("No no, not for you :p");
				}
			break;
			case 6:
				if (c.pkStatuePlays >= 100) {
					c.playerTitle = 12;
					c.sendMessage("<col=FC0000>Congratulations! You have redeemed the title 'Gamer'.");		
					c.sendMessage("<col=FC0000>Please re-log for this to take effect.");
				} else {
					c.sendMessage("No no, not for you :p");
				}
			break;
			case 7:
				if (c.duelsWon >= 100) {
					c.playerTitle = 13;
					c.sendMessage("<col=FC0000>Congratulations! You have redeemed the title 'Duelist'.");	
					c.sendMessage("<col=FC0000>Please re-log for this to take effect.");
				} else {
					c.sendMessage("No no, not for you :p");
				}
			break;
			case 8:
				if (c.teleportedHome >= 500) {
					c.playerTitle = 21;
					c.sendMessage("<col=FC0000>Congratulations! You have redeemed the title 'Home'.");	
					c.sendMessage("<col=FC0000>Please re-log for this to take effect.");
				} else {
					c.sendMessage("No no, not for you :p");
				}
			break;
			case 9:
				if (c.profileViews >= 250) {
					c.playerTitle = 14;
					c.sendMessage("<col=FC0000>Congratulations! You have redeemed the title 'Viewer'.");	
					c.sendMessage("<col=FC0000>Please re-log for this to take effect.");
				} else {
					c.sendMessage("No no, not for you :p");
				}
			break;
			case 10:
				if (c.tradesCompleted >= 500) {
					c.playerTitle = 15;
					c.sendMessage("<col=FC0000>Congratulations! You have redeemed the title 'Trader'.");	
					c.sendMessage("<col=FC0000>Please re-log for this to take effect.");
				} else {
					c.sendMessage("No no, not for you :p");
				}
			break;
			case 11:
				if (c.appearancesChanged >= 100) {
					c.playerTitle = 16;
					c.sendMessage("<col=FC0000>Congratulations! You have redeemed the title 'Selfie'.");	
					c.sendMessage("<col=FC0000>Please re-log for this to take effect.");
				} else {
					c.sendMessage("No no, not for you :p");
				}
			break;
			case 12:
				if (c.corpKills >= 100) {
					c.playerTitle = 22;
					c.sendMessage("<col=FC0000>Congratulations! You have redeemed the title 'Hunter'.");	
					c.sendMessage("<col=FC0000>Please re-log for this to take effect.");
				} else {
					c.sendMessage("No no, not for you :p");
				}
			break;
			case 13:
				if (c.hit900) {
					c.playerTitle = 17;
					c.sendMessage("<col=FC0000>Congratulations! You have redeemed the title '900'.");	
					c.sendMessage("<col=FC0000>Please re-log for this to take effect.");
				} else {
					c.sendMessage("No no, not for you :p");
				}
			break;
			case 14:
				if (c.triviaWon >= 100) {
					c.playerTitle = 18;
					c.sendMessage("<col=FC0000>Congratulations! You have redeemed the title 'Brainiac'.");	
					c.sendMessage("<col=FC0000>Please re-log for this to take effect.");
				} else {
					c.sendMessage("No no, not for you :p");
				}
			break;
			case 15:
				if (c.altarPrayed >= 1000) {
					c.playerTitle = 19;
					c.sendMessage("<col=FC0000>Congratulations! You have redeemed the title 'Holy'.");	
					c.sendMessage("<col=FC0000>Please re-log for this to take effect.");
				} else {
					c.sendMessage("No no, not for you :p");
				}
			break;
			case 100:
				if (c.currentlyCompletedAll) {
				c.playerTitle = 30;
				c.sendMessage("<col=FC0000>Congratulations! You have redeemed the title 'Mystic'.");	
				c.sendMessage("<col=FC0000>Please re-log for this to take effect.");
				} else {
					c.sendMessage("No no, not for you :p");
				}
			break;
			case 16:
				if (c.getLevelForXP(c.playerXP[10]) >= 99) {
				c.playerTitle = 23;
				c.sendMessage("<col=FC0000>Congratulations! You have redeemed the title 'Master Baiter'.");	
				c.sendMessage("<col=FC0000>Please re-log for this to take effect.");
				} else {
					c.sendMessage("No no, not for you :p");
				}
			break;
			case 17:
				if (c.rockCrabKills >= 100) {
				c.playerTitle = 24;
				c.sendMessage("<col=FC0000>Congratulations! You have redeemed the title 'Crabs'.");	
				c.sendMessage("<col=FC0000>Please re-log for this to take effect.");
				} else {
					c.sendMessage("No no, not for you :p");
				}
			break;
			case 18:
				if (c.jadKills >= 15) {
				c.playerTitle = 25;
				c.sendMessage("<col=FC0000>Congratulations! You have redeemed the title 'Tztok'.");	
				c.sendMessage("<col=FC0000>Please re-log for this to take effect.");
				} else {
					c.sendMessage("No no, not for you :p");
				}
			break;
			case 19:
				if (c.yewsCut >= 1000) {
				c.playerTitle = 26;
				c.sendMessage("<col=FC0000>Congratulations! You have redeemed the title 'Nazi'.");	
				c.sendMessage("<col=FC0000>Please re-log for this to take effect.");
				} else {
					c.sendMessage("No no, not for you :p");
				}
			break;
			case 20:
				if (c.foodCooked >= 10000) {
				c.playerTitle = 27;
				c.sendMessage("<col=FC0000>Congratulations! You have redeemed the title 'Iron Chef'.");	
				c.sendMessage("<col=FC0000>Please re-log for this to take effect.");
				} else {
					c.sendMessage("No no, not for you :p");
				}
			break;
			case 21:
				if (c.logsBurned >= 4200) {
				c.playerTitle = 28;
				c.sendMessage("<col=FC0000>Congratulations! You have redeemed the title 'Blazed'.");	
				c.sendMessage("<col=FC0000>Please re-log for this to take effect.");
				} else {
					c.sendMessage("No no, not for you :p");
				}
			break;
			case 22:
				if (c.logsBurned >= 1500) {
				c.playerTitle = 29;
				c.sendMessage("<col=FC0000>Congratulations! You have redeemed the title 'Oreo'.");	
				c.sendMessage("<col=FC0000>Please re-log for this to take effect.");
				} else {
					c.sendMessage("No no, not for you :p");
				}
			break;
			case 23:
				if (c.totalPrestiges >= 10) {
				c.playerTitle = 31;
				c.sendMessage("<col=FC0000>Congratulations! You have redeemed the title 'Wowza'.");	
				c.sendMessage("<col=FC0000>Please re-log for this to take effect.");
				} else {
					c.sendMessage("No no, not for you :p");
				}
			break;
			
		}
	}
	
	public static boolean noInventorySpace(Client c) {
		if (c.getItems().freeSlots() == 0) {
			c.sendMessage("You haven't got enough inventory space to do this!");
			c.getPA().sendStatement("You haven't got enough inventory space to do this!");
			return false;
		}
		return true;
	}
	
	public static void exchangeTicket4Coins(Client c) {
		if (!noInventorySpace(c)) {
			return;
		}
		if (!(c.getItems().playerHasItem(1464, 1))) {
			c.getPA().sendStatement("You haven't got any billion ticket to do this!");
			return;
		}
		if (c.getItems().playerHasItem(995)) {
			c.getPA().sendStatement("Please bank all your coins before doing this.");
			return;
		}
		if (c.getItems().playerHasItem(1464, 1)) {
			c.getPA().closeAllWindows();
			c.getItems().deleteItem(1464, 1);
			c.getItems().addItem(995, 1000000000);
			c.sendMessage("You successfully exchange your billion ticket for coins!");
		}
	}
	
	public static void exchangeCoins4Tickets(Client c) {
		if (!noInventorySpace(c)) {
			return;
		}
		if (!(c.getItems().playerHasItem(995, 1000000000))) {
			c.getPA().sendStatement("You haven't got enough coins to do this!");
		}
		if (c.getItems().playerHasItem(995, 1000000000)) {
			c.getPA().closeAllWindows();
			c.getItems().deleteItem(995, 1000000000);
			c.getItems().addItem(1464, 1);
			c.sendMessage("You successfully exchange your coins for a billion ticket!");
		}
	}
	
	public static void refillPrayer(Client c) {
		 c.getPA().closeAllWindows();
         if(c.playerLevel[5] < c.getPA().getLevelForXP(c.playerXP[5])) {
				c.startAnimation(645);
				c.playerLevel[5] = c.getPA().getLevelForXP(c.playerXP[5]);
				c.sendMessage("You recharge your prayer points.");
				c.getPA().refreshSkill(5);
				c.altarPrayed += 1;
				if (c.altarPrayed == 1)
					c.sendMessage("@war@You have started the achievement: Holy Monk ");
				if (c.altarPrayed == 300)
					AchievementHandler.activateAchievement(c, AchievementList.HOLY_MONK);
			} else {
				c.sendMessage("You already have full prayer points!");
			}            
	}
	
	public static void determineWhoToRestore(Client c) {
		if (c.getRights().isDonator()) {
			restoreSpecialDonator(c);
		} else {
			restoreSpecialPlayer(c);
		}
		 c.getPA().closeAllWindows();
	}
	
	public static void restoreSpecialPlayer(Client c) {
		if (System.currentTimeMillis() - c.playerRestoreDelay >= 300000) {
			c.playerRestoreDelay = System.currentTimeMillis();
			restoreSpecial(c);
		 } else {
             c.sendMessage("<col=255>You can only restore your special every 3 minutes!");
             c.sendMessage("<img=4><col=255>Donators only have to wait 1 minute!");
         }
	}
	
	public static void restoreSpecialDonator(Client c) {
		if (System.currentTimeMillis() - c.donatorRestoreDelay >= 100000) {
			c.donatorRestoreDelay = System.currentTimeMillis();
			restoreSpecial(c);
		 } else {
             c.sendMessage("<img=4><col=255>You can only do this once every minute!");
         }
	}
	
	public static void restoreSpecial(Client c) {
        //c.startAnimation(8961);
        c.sendMessage("<col=255>Your special attack has been restored!");
        c.specAmount = 10.0;
        c.getItems().updateSpecialBar();
	}
	
	public static void togglesTab(Client c) {
		c.getPA().sendFrame126(c.wantTriviaBot == true ? "Toggle TriviaBot: @red@False" : "Toggle TriviaBot: @gre@True", 47526);
		c.getPA().sendFrame126(c.wantYellChannel == true ? "Toggle Yell Channel: @gre@ON" : "Toggle Yell Channel: @red@OFF", 47528);
	}
	
	public static void prestigeInterface(Client c) {
		c.getPA().sendFrame126("Combat Level: @lre@"+c.combatLevel, 27805);
		c.getPA().sendFrame126("Coming soon!", 27806);
		c.getPA().sendFrame126(c.getRights().determineIcon()+""+MistexUtility.capitalize(c.playerName), 27831);
	}
	
	public static void clearHighscores(Client c) {
		c.getPA().sendFrame126("Server Name Top 10", 6399);
		c.getPA().sendFrame126("Close Window", 6401);
		c.getPA().sendFrame126(" ", 6402);
		c.getPA().sendFrame126(" ", 6403);
		c.getPA().sendFrame126(" ", 6404);
		c.getPA().sendFrame126(" ", 6405);
		c.getPA().sendFrame126("Mistex", 640);
		c.getPA().sendFrame126(" ", 6406);
		c.getPA().sendFrame126(" ", 6407);
		c.getPA().sendFrame126(" ", 6408);
		c.getPA().sendFrame126(" ", 6409);
		c.getPA().sendFrame126(" ", 6410);
		c.getPA().sendFrame126(" ", 6411);
		c.getPA().sendFrame126(" ", 8578);
		c.getPA().sendFrame126(" ", 8579);
		c.getPA().sendFrame126(" ", 8580);
		c.getPA().sendFrame126(" ", 8581);
		c.getPA().sendFrame126(" ", 8582);
		c.getPA().sendFrame126(" ", 8583);
		c.getPA().sendFrame126(" ", 8584);
		c.getPA().sendFrame126(" ", 8585);
		c.getPA().sendFrame126(" ", 8586);
		c.getPA().sendFrame126(" ", 8587);
		c.getPA().sendFrame126(" ", 8588);
		c.getPA().sendFrame126(" ", 8589);
		c.getPA().sendFrame126(" ", 8590);
		c.getPA().sendFrame126(" ", 8591);
		c.getPA().sendFrame126(" ", 8592);
		c.getPA().sendFrame126(" ", 8593);
		c.getPA().sendFrame126(" ", 8594);
		c.getPA().sendFrame126(" ", 8595);
		c.getPA().sendFrame126(" ", 8596);
		c.getPA().sendFrame126(" ", 8597);
		c.getPA().sendFrame126(" ", 8598);
		c.getPA().sendFrame126(" ", 8599);
		c.getPA().sendFrame126(" ", 8600);
		c.getPA().sendFrame126(" ", 8601);
		c.getPA().sendFrame126(" ", 8602);
		c.getPA().sendFrame126(" ", 8603);
		c.getPA().sendFrame126(" ", 8604);
		c.getPA().sendFrame126(" ", 8605);
		c.getPA().sendFrame126(" ", 8606);
		c.getPA().sendFrame126(" ", 8607);
		c.getPA().sendFrame126(" ", 8608);
		c.getPA().sendFrame126(" ", 8609);
		c.getPA().sendFrame126(" ", 8610);
		c.getPA().sendFrame126(" ", 8611);
		c.getPA().sendFrame126(" ", 8612);
		c.getPA().sendFrame126(" ", 8613);
		c.getPA().sendFrame126(" ", 8614);
		c.getPA().sendFrame126(" ", 8615);
		c.getPA().sendFrame126(" ", 8616);
		c.getPA().sendFrame126(" ", 8617);
	}
	
	public static void handleMessages(Client player) {	
		if (player.specAmount == 1.0) {
			player.sendMessage("Your special attack is at 10%.");
		} else if (player.specAmount == 2.0) {
			player.sendMessage("Your special attack is at 20%.");
		} else if (player.specAmount == 3.0) {
			player.sendMessage("Your special attack is at 30%.");
		} else if (player.specAmount == 4.0) {
			player.sendMessage("Your special attack is at 40%.");
		} else if (player.specAmount == 5.0) {
			player.sendMessage("Your special attack is at 50%.");
		} else if (player.specAmount == 6.0) {
			player.sendMessage("Your special attack is at 60%.");
		} else if (player.specAmount == 7.0) {
			player.sendMessage("Your special attack is at 70%.");
		} else if (player.specAmount == 8.0) {
			player.sendMessage("Your special attack is at 80%.");
		} else if (player.specAmount == 9.0) {
			player.sendMessage("Your special attack is at 90%.");
		} else if (player.specAmount == 10.0) {
			player.sendMessage("Your special attack is at 100%.");
		}
	}
	
	@SuppressWarnings("static-access")
	public static void banUser(Client player) {
		Client opponent = (Client) Mistex.playerHandler.players[player.playerIndex];
        for (int i = 0; i < MistexConfiguration.CANTHARM.length; i++){
			if (opponent.playerName.equalsIgnoreCase(MistexConfiguration.CANTHARM[i])) {
				player.getPA().closeAllWindows();
				player.sendMessage("You can't punish "+MistexUtility.capitalize(player.playerName)+"! They fucking own you.");
				opponent.getPA().sendStatement(""+MistexUtility.capitalize(opponent.playerName)+" tried to punish you.");
				return;
			}
		}
        PunishmentHandler.addNameToBanList(opponent.playerName);
        PunishmentHandler.addNameToFile(opponent.playerName);
        for (int i = 0; i < MistexConfiguration.MAX_PLAYERS; i++) {
            if (World.players[i] != null) {
                if (World.players[i].playerName.equalsIgnoreCase(opponent.playerName)) {
                    World.players[i].disconnected = true;
                    Client c2 = (Client) World.players[i];
                    c2.sendMessage(" " + c2.playerName + " Got Banned By " + player.playerName + ".");                
                }
            }
            player.getPA().sendStatement("<col=445878>You have banned the user: " + opponent.playerName + " with the host: " + opponent.connectedFrom);
        }
	}
	
	@SuppressWarnings("static-access")
	public static void IpbanUser(Client player) {
		Client opponent = (Client) Mistex.playerHandler.players[player.playerIndex];
        for (int i = 0; i < MistexConfiguration.CANTHARM.length; i++){
			if (opponent.playerName.equalsIgnoreCase(MistexConfiguration.CANTHARM[i])) {
				player.getPA().closeAllWindows();
				player.sendMessage("You can't punish "+MistexUtility.capitalize(player.playerName)+"! They fucking own you.");
				opponent.getPA().sendStatement(""+MistexUtility.capitalize(opponent.playerName)+" tried to punish you.");
				return;
			}
		}
        PunishmentHandler.addIpToBanList(opponent.playerName);
        PunishmentHandler.addIpToFile(opponent.playerName);
        for (int i = 0; i < MistexConfiguration.MAX_PLAYERS; i++) {
            if (World.players[i] != null) {
                if (World.players[i].playerName.equalsIgnoreCase(opponent.playerName)) {
                    World.players[i].disconnected = true;
                    Client c2 = (Client) World.players[i];
                    c2.sendMessage(" " + c2.playerName + " Got Banned By " + player.playerName + ".");                   
                }
            }
            player.getPA().sendStatement("<col=445878>You have Ip-banned the user: " + opponent.playerName + " with the host: " + opponent.connectedFrom);
        }
	}
	
	@SuppressWarnings("static-access")
	public static void muteUser(Client player) {
		Client opponent = (Client) Mistex.playerHandler.players[player.playerIndex];
        for (int i = 0; i < MistexConfiguration.CANTHARM.length; i++){
			if (opponent.playerName.equalsIgnoreCase(MistexConfiguration.CANTHARM[i])) {
				player.sendMessage("You can't punish "+MistexUtility.capitalize(player.playerName)+"! They fucking own you.");
				player.getPA().closeAllWindows();
				opponent.getPA().sendStatement(""+MistexUtility.capitalize(opponent.playerName)+" tried to punish you.");
				return;
			}
		}
		PunishmentHandler.addNameToMuteList(opponent.playerName);
        for (int i = 0; i < MistexConfiguration.MAX_PLAYERS; i++) {
            if (World.players[i] != null) {
                if (World.players[i].playerName.equalsIgnoreCase(opponent.playerName)) {
                    Client c2 = (Client) World.players[i];
                    c2.getPA().sendStatement("You have been muted by: "+MistexUtility.capitalize(player.playerName));
                }
            }
            player.getPA().sendStatement("<col=445878>You have muted the user: " + opponent.playerName + " with the host: " + opponent.connectedFrom);
        }
	}
	
	@SuppressWarnings("static-access")
	public static void ipMuteUser(Client player) {
		Client opponent = (Client) Mistex.playerHandler.players[player.playerIndex];
        for (int i = 0; i < MistexConfiguration.CANTHARM.length; i++){
			if (opponent.playerName.equalsIgnoreCase(MistexConfiguration.CANTHARM[i])) {
				player.getPA().closeAllWindows();
				player.sendMessage("You can't punish "+MistexUtility.capitalize(opponent.playerName)+"! They fucking own you.");
				opponent.getPA().sendStatement(""+MistexUtility.capitalize(player.playerName)+" tried to punish you.");
				return;
			}
		}
		PunishmentHandler.addIpToMuteList(opponent.playerName);
        for (int i = 0; i < MistexConfiguration.MAX_PLAYERS; i++) {
            if (World.players[i] != null) {
                if (World.players[i].playerName.equalsIgnoreCase(opponent.playerName)) {
                    Client c2 = (Client) World.players[i];
                    c2.getPA().sendStatement("You have been IP-muted by: "+MistexUtility.capitalize(player.playerName));         
                }
            }
        }
        player.getPA().sendStatement("<col=445878>You have IP-muted the user: " + opponent.playerName + " with the host: " + opponent.connectedFrom);
	}

}
