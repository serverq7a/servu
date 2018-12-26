package org.mistex.game.world.content;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.mistex.game.Mistex;
import org.mistex.game.MistexUtility;
import org.mistex.game.world.PunishmentHandler;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.PlayerSave;

@SuppressWarnings("static-access")
public class StarterPack {

    public static String INVENTORYITEMSLOCATION = "./Data/starterkit/Inventory.txt";
    public static String MESSAGECOLOR = "<col=DE1B24>";
    
    public static void addInventoryStarter(Client player){
        BufferedReader reader;
        try {
                reader = new BufferedReader(new FileReader(INVENTORYITEMSLOCATION));
                String line = null;
                while ((line = reader.readLine()) != null) {
                        if (line.contains("#")) {
                                continue;
                        }
                        final String[] split = line.split(":");
                        final int itemIdentity = Integer.valueOf(split[0]);
                        final int amount = Integer.valueOf(split[1]);
                        //player.getItems().addItemToBank(itemIdentity, amount);
                        player.getItems().addItem(itemIdentity, amount);
                        player.specAmount = 10;
                        PlayerSave.saveGame(player);
                        player.isDoingTutorial = false;
                        player.getPA().showInterface(3559);
                        player.canChangeAppearance = true;
                }
                reader.close();
        } catch (IOException e) {
                e.printStackTrace();
        }
}

	public static void determineStarter(Client c) {
        if (!PunishmentHandler.hasRecieved1stStarter(Mistex.playerHandler.players[c.playerId].connectedFrom)) {
        	addInventoryStarter(c);
            PunishmentHandler.addIpToStarterList1(Mistex.playerHandler.players[c.playerId].connectedFrom);
            PunishmentHandler.addIpToStarter1(Mistex.playerHandler.players[c.playerId].connectedFrom);
            firstLoginMessage(c);
        } else if (PunishmentHandler.hasRecieved1stStarter(Mistex.playerHandler.players[c.playerId].connectedFrom) && !PunishmentHandler.hasRecieved2ndStarter(Mistex.playerHandler.players[c.playerId].connectedFrom)) {
        	addInventoryStarter(c);
        	secondLoginMessage(c);
            PunishmentHandler.addIpToStarterList2(Mistex.playerHandler.players[c.playerId].connectedFrom);
            PunishmentHandler.addIpToStarter2(Mistex.playerHandler.players[c.playerId].connectedFrom);
        } else if (PunishmentHandler.hasRecieved1stStarter(Mistex.playerHandler.players[c.playerId].connectedFrom) && PunishmentHandler.hasRecieved2ndStarter(Mistex.playerHandler.players[c.playerId].connectedFrom)) {
        	thirdLoginMessage(c);
        }
    }
	
	public static void firstLoginMessage(Client c) {
		c.sendMessage(MESSAGECOLOR+"Welcome to Mistex, "+MistexUtility.capitalize(c.playerName)+"!");
		c.sendMessage(MESSAGECOLOR+"It appears this is your first time playing Mistex, we have given you a starter kit.");
		c.sendMessage(MESSAGECOLOR+"We will only give you 2 starter kits, you have used the first one.");
	}
	
	public static void secondLoginMessage(Client c) {
		c.sendMessage(MESSAGECOLOR+"Welcome to Mistex, "+MistexUtility.capitalize(c.playerName)+"!");
		c.sendMessage(MESSAGECOLOR+"It appears this is your second time playing Mistex, we have given you another starter.");
		c.sendMessage(MESSAGECOLOR+"We will only give you 2 starter kits, you have now used both starters.");
	}
	
	public static void thirdLoginMessage(Client c) {
		c.sendMessage(MESSAGECOLOR+"Welcome to Mistex, "+MistexUtility.capitalize(c.playerName)+"!");
		c.sendMessage(MESSAGECOLOR+"It appears you have already recieved both your starterkits!");
    	c.isDoingTutorial = false;
        c.getPA().showInterface(3559);
        c.canChangeAppearance = true;
	}
	
	


}