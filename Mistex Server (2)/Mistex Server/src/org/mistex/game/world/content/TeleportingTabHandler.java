package org.mistex.game.world.content;

import org.mistex.game.MistexUtility;
import org.mistex.game.world.content.interfaces.InterfaceText;
import org.mistex.game.world.content.interfaces.impl.teleports.BossInterface;
import org.mistex.game.world.content.interfaces.impl.teleports.DonatorInterface;
import org.mistex.game.world.content.interfaces.impl.teleports.MinigameInterface;
import org.mistex.game.world.content.interfaces.impl.teleports.PlayerKillingInterface;
import org.mistex.game.world.content.interfaces.impl.teleports.SkillingInterface;
import org.mistex.game.world.content.interfaces.impl.teleports.TrainingInterface;
import org.mistex.game.world.content.minigame.weapongame.WeaponGame;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.PlayerConfiguration;

/**
 * Handles all the clicking buttons for teleport interface
 * @author Omar | Play Boy
 */

public class TeleportingTabHandler {
	
	/**
	 * The variable for nothing
	 */
	public static int None = 0;
	
	/**
	 * The variable for training
	 */
	public static int Training = 1;
	
	/**
	 * The variable for minigame
	 */
	public static int Minigame = 2;
	
	/**
	 * The variable for boss
	 */
	public static int Boss = 3;
	
	/**
	 * The variable for pk
	 */
	public static int PK = 4;
	
	/**
	 * The variable for skilling
	 */
	public static int Skilling = 5;
	
	/**
	 * The variable for donator
	 */
	public static int Donator = 6;

	/**
	 * Handles the action buttons for teleports
	 * @param player
	 * @param buttonId
	 */
    public static void handleActionsButtons(Client player, int buttonId) {
        switch (buttonId) {
        
        	/*
        	 * Location Teleport
        	 */
        case 4143:
        case 50245:
        	player.getDH().sendDialogues(2300, -1);
        	break;
        	
            /*
             * Training Teleport
             */
        case 236186:
        case 4146:
        case 50243:
        	player.viewingTeleportingInterface = Training;
            player.getPA().showInterface(60600);
            InterfaceText.writeText(new TrainingInterface(player));
            break;

            /*
             * Minigame Teleport
             */
        case 236189:
        case 6005:
        case 51023:
        	player.viewingTeleportingInterface = Minigame;
            player.getPA().showInterface(60700);
            InterfaceText.writeText(new MinigameInterface(player));
            break;

            /*
             * Bosses Teleport
             */
        case 236192:
        case 4150:
        case 51005:
        	player.viewingTeleportingInterface = Boss;
            player.getPA().showInterface(60800);
            InterfaceText.writeText(new BossInterface(player));
            break;

            /*
             * Playerkilling Teleport
             */
        case 236195:
        case 50235:
        case 4140:
        	player.viewingTeleportingInterface = PK;
            player.getPA().showInterface(60900);
            InterfaceText.writeText(new PlayerKillingInterface(player));
            break;

            /*
             * Skilling Teleport
             */
        case 236198:
        case 6004:
        case 51013:
        	player.viewingTeleportingInterface = Skilling;
            player.getPA().showInterface(61000);
            InterfaceText.writeText(new SkillingInterface(player));
            break;
            
            /*
             * Donator Teleport
             */
        case 236201:
        	if (player.getRights().isPlayer()) {
        		player.sendMessage("<col=482CB8>You need to be a donator to have access to this!");
        	} else {
        		player.viewingTeleportingInterface = Donator;
        		player.getPA().showInterface(61100);
        		InterfaceText.writeText(new DonatorInterface(player));
        	}
            break;
            
            
           /** Beginning of actual teleports **/ 
            
        case 236206://OPTION 1
        	if (player.viewingTeleportingInterface == Training) {//Rock Crabs
        		player.getPA().closeAllWindows();
        		player.getPA().startTeleport(2674, 3710, 0, "modern");
        		player.sendMessage("<col=482CB8>You have teleported to the rock crab Training area.");
        	} else if (player.viewingTeleportingInterface == Minigame) {//Duel Arena
        		if (player.hasActivePerk) {
        			player.sendMessage("You can not go to duel arena with an active perk.");
        			return;
        		}
        		player.getPA().closeAllWindows();
        		player.getPA().startTeleport(3362, 3266, 0, "modern");
        		player.sendMessage("<col=482CB8>You have teleported to the duel arena minigame.");
        	} else if (player.viewingTeleportingInterface == Boss) {//GWD       		
        		player.getDH().sendDialogues(136, -1);
        	} else if (player.viewingTeleportingInterface == PK) {//MageBank
        		player.getPA().closeAllWindows();
        		player.getPA().startTeleport(2540, 4717, 0, "modern");
        		player.sendMessage("<col=482CB8>You have teleported to the mage bank pvp location.");  
        	} else if (player.viewingTeleportingInterface == Skilling) {//Skilling Zone
        		player.getPA().closeAllWindows();
        		player.getPA().startTeleport(2338, 3695, 0, "modern");
        		player.sendMessage("<col=482CB8>You have teleported to the skilling zone.");        		
        	} else if (player.viewingTeleportingInterface == Donator) {//Donator Zone
        		player.getPA().closeAllWindows();
        		player.getPA().startTeleport(PlayerConfiguration.DONATORZONE_X + MistexUtility.random(5), PlayerConfiguration.DONATORZONE_Y + MistexUtility.random(5), 0, "modern");
        		player.sendMessage("<col=E319BB>Welcome "+player.getRights().determineIcon()+""+MistexUtility.capitalize(player.playerName)+", to the donator zone.");
        		player.sendMessage("<col=E319BB>We would like to thank you for your kind donation!");
        		
        	}     	     
     	break;
     	
        case 236209://OPTION 2
        	if (player.viewingTeleportingInterface == Training) {//Bandits
        		player.getPA().closeAllWindows();
        		player.getPA().startTeleport(3172, 2981, 0, "modern");
        		player.sendMessage("<col=482CB8>You have teleported to the bandits Training area.");
        	} else if (player.viewingTeleportingInterface == Minigame) {//Fight Pits
        		player.getPA().closeAllWindows();
        		player.getPA().startTeleport(2400, 5178, 0, "modern");
        		player.sendMessage("<col=482CB8>You have teleported to the fight pits minigame.");
        	} else if (player.viewingTeleportingInterface == Boss) {//kBD
        		player.getPA().closeAllWindows();
        		player.getPA().startTeleport(2271, 4681, 0, "modern");
        		player.sendMessage("<col=482CB8>You have teleported to king back dragon boss.");
        	} else if (player.viewingTeleportingInterface == PK) {//EASTS    		
        		player.getPA().closeAllWindows();
        		player.getPA().startTeleport(3333, 3666, 0, "modern");
        		player.sendMessage("<col=482CB8>You have teleported to the easts pvp location.");    		
        	} else if (player.viewingTeleportingInterface == Skilling) {       		
        		player.getDH().sendDialogues(86, -1);
        		
        	} else if (player.viewingTeleportingInterface == Donator) {
        		
        	}  
     	break;
     	
        case 236212://OPTION 3
        	if (player.viewingTeleportingInterface == Training) {//Al kahid
        		player.getPA().closeAllWindows();
        		player.getPA().startTeleport(3293, 3179, 0, "modern");
        		player.sendMessage("<col=482CB8>You have teleported to the al-kahid training area.");
        	} else if (player.viewingTeleportingInterface == Minigame) {//Champions Battle
        		if (player.playerRights == 3) 
        		player.getDH().sendDialogues(315, -1);
        		else 
        			player.sendMessage("Currently under development");
        	} else if (player.viewingTeleportingInterface == Boss) {//CORPOREAL
        		player.getPA().closeAllWindows();
        		player.getPA().startTeleport(2898, 3618, 0, "modern");
        		player.sendMessage("<col=482CB8>You have teleported to the corporeal beast.");
        	} else if (player.viewingTeleportingInterface == PK) {
        		
        	} else if (player.viewingTeleportingInterface == Skilling) {//Woodcutting
        		player.getPA().closeAllWindows();
        		player.getPA().startTeleport(2725, 3484, 0, "modern");
        		player.sendMessage("<col=482CB8>You have teleported to the woodcutting skilling area.");
        		
        	} else if (player.viewingTeleportingInterface == Donator) {
        		
        		      	
        	}  
     	break;
     	
        case 236215://OPTION 4
        	if (player.viewingTeleportingInterface == Training) {//Hill giants
        		player.getPA().closeAllWindows();
        		player.getPA().startTeleport(3117, 9852, 0, "modern");
        		player.sendMessage("<col=482CB8>You have teleported to the hill giants training area.");
        	} else if (player.viewingTeleportingInterface == Minigame) {//Barrows
        		player.getPA().closeAllWindows();
        		player.getPA().startTeleport(3565, 3316, 0, "modern");
        		player.sendMessage("<col=482CB8>You have teleported to the barrows minigame.");
        	} else if (player.viewingTeleportingInterface == Boss) {//Nomad
        		player.getPA().closeAllWindows();
        		player.getPA().startTeleport(3504, 3562, 0, "modern");
        		player.sendMessage("<col=482CB8>You have teleported to the nomad boss.");
        	} else if (player.viewingTeleportingInterface == PK) {//FUNPK
        		player.getPA().closeAllWindows();
        		player.getPA().startTeleport(2525, 4776, 0, "modern");
        		player.sendMessage("<col=482CB8>You have teleported to fun pk.");
        	} else if (player.viewingTeleportingInterface == Skilling) {//Fishing
        		player.getPA().closeAllWindows();
        		player.getPA().startTeleport(2853, 3432, 0, "modern");
        		player.sendMessage("<col=482CB8>You have teleported to the fishing training zone.");
        	} else if (player.viewingTeleportingInterface == Donator) {
        		
        	
        	}  
     	break;
     	
        case 236218://OPTION 5
        	if (player.viewingTeleportingInterface == Training) {//Apes
        		player.getPA().closeAllWindows();
        		player.getPA().startTeleport(2786, 2786, 0, "modern");
        		player.sendMessage("<col=482CB8>You have teleported to the ape toll training area.");
        	} else if (player.viewingTeleportingInterface == Minigame) {//Fight Caves
        		player.getPA().closeAllWindows();
        		player.getPA().startTeleport(2445, 5178, 0, "modern");
        		player.sendMessage("<col=482CB8>You have teleported to the fight caves minigame.");
        	} else if (player.viewingTeleportingInterface == Boss) {//TDM
        		player.getPA().closeAllWindows();
        		player.getPA().startTeleport(2907, 10034, 0, "modern");
        		player.sendMessage("<col=482CB8>You have teleported to the tormented demons.");
        	} else if (player.viewingTeleportingInterface == PK) {
        		
        	} else if (player.viewingTeleportingInterface == Skilling) {//Mining guild
        		player.getPA().closeAllWindows();
        		player.getPA().startTeleport(3044, 9785, 0, "modern");
        		player.sendMessage("<col=482CB8>You have teleported to the mining skilling area.");
        	} else if (player.viewingTeleportingInterface == Donator) {
        		
        	
        	}  
     	break;
     	
        case 236221://OPTION 6
        	if (player.viewingTeleportingInterface == Training) {//Slayer Tower
        		player.getPA().closeAllWindows();
        		player.getPA().startTeleport(3428, 3537, 0, "modern");
        		player.sendMessage("<col=482CB8>You have teleported to the slayer tower.");
        	} else if (player.viewingTeleportingInterface == Minigame) {//Pest Control
        		player.getPA().closeAllWindows();
        		player.getPA().startTeleport(2662, 2655, 0, "modern");
        		player.sendMessage("<col=482CB8>You have teleported to the pest control minigame.");

        	} else if (player.viewingTeleportingInterface == Boss) {
        		
        	} else if (player.viewingTeleportingInterface == PK) {
        		
        	} else if (player.viewingTeleportingInterface == Skilling) {//Hunter
        		player.getPA().closeAllWindows();
        		player.getPA().startTeleport(3000, 3413, 0, "modern");
        		player.sendMessage("<col=482CB8>You have teleported to the hunter training zone.");
        	} else if (player.viewingTeleportingInterface == Donator) {
        		

        	}  
     	break;
     	
        case 236224://OPTION 7
        	if (player.viewingTeleportingInterface == Training) {
        		player.getPA().closeAllWindows();
        		player.getPA().startTeleport(2710, 9466, 0, "modern");
        		player.sendMessage("<col=482CB8>You have teleported to brimhaven dungeon.");
        	} else if (player.viewingTeleportingInterface == Minigame) {     		
        		
        		
        	} else if (player.viewingTeleportingInterface == Boss) {
        		
        	} else if (player.viewingTeleportingInterface == PK) {
        		
        	} else if (player.viewingTeleportingInterface == Skilling) {//Thieving            	
            	player.getPA().closeAllWindows();
            	player.getPA().startTeleport(3044, 4976, 1, "modern");
            	player.sendMessage("<col=482CB8>You have teleported to to the thieving area.");
        		
        	} else if (player.viewingTeleportingInterface == Donator) {
        		
        	}  
        	break;
        	
        case 236227://OPTION 8
        	if (player.viewingTeleportingInterface == Training) {
        		player.getPA().closeAllWindows();
        		player.getPA().startTeleport(2884, 9798, 0, "modern");
        		player.sendMessage("<col=482CB8>You have teleported to taverly dungeon.");
        	} else if (player.viewingTeleportingInterface == Minigame) {//Weapon Game      		
        		if (WeaponGame.canEnter(player)) {     		
        		player.getPA().closeAllWindows();
        		if (!player.inWeaponGame() || !player.inWeaponLobby()) {
        			WeaponGame.addPlayer(player);
        		} else {
        			player.sendMessage("<col=482CB8>You already are involved in the weapon game.");
        		}
        		player.sendMessage("<col=482CB8>You have teleported to the weapon game minigame");
        		} else {
        			player.sendMessage("<col=482CB8>Please remove all your items from inventory and equipment.");
        		}	
        	} else if (player.viewingTeleportingInterface == Boss) {
        		
        	} else if (player.viewingTeleportingInterface == PK) {
        		
        	} else if (player.viewingTeleportingInterface == Skilling) {//Slayer Tower
        		player.getPA().closeAllWindows();
        		player.getPA().startTeleport(3428, 3525, 0, "modern");
        		player.sendMessage("<col=482CB8>You have teleported to the slayer tower");
        		
        	} else if (player.viewingTeleportingInterface == Donator) {
        		
        	}  
     	break;
     	
        case 236230://OPTION 9
        	if (player.viewingTeleportingInterface == Training) {

        	} else if (player.viewingTeleportingInterface == Minigame) {     		
        		
        		
        	} else if (player.viewingTeleportingInterface == Boss) {
        		
        	} else if (player.viewingTeleportingInterface == PK) {
        		
        	} else if (player.viewingTeleportingInterface == Skilling) {//Agility          	
            	player.getPA().closeAllWindows();
            	player.getPA().startTeleport(2480, 3437, 0, "modern");
            	player.sendMessage("<col=482CB8>You have teleported to to the agility area.");
        		
        	} else if (player.viewingTeleportingInterface == Donator) {
        		
        	}  
        	break;
     	
    }
        }

}