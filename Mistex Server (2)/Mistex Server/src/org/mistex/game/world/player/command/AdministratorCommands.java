package org.mistex.game.world.player.command;

import org.mistex.game.Mistex;
import org.mistex.game.MistexConfiguration;
import org.mistex.game.MistexUtility;
import org.mistex.game.world.World;
import org.mistex.game.world.event.CycleEvent;
import org.mistex.game.world.event.CycleEventContainer;
import org.mistex.game.world.event.CycleEventHandler;
import org.mistex.game.world.npc.NPCHandler;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.shop.ShopExecutor;
import org.mistex.game.world.shop.Shops;

public class AdministratorCommands {

	@SuppressWarnings("static-access")
	public static void administratorCommands(final Client c, String playerCommand) {
	    	if (playerCommand.toLowerCase().startsWith("shop")) {
				try {
					ShopExecutor.open(c, Integer.parseInt(playerCommand.substring(5)));
				} catch(Exception e) {
					c.sendMessage("Use as ::shop [id]!");
				}
	    	}
	    	
	    	if(playerCommand.startsWith("specme")) {
	    		try {
	    			
	    			/** Apply initial * 2 hit */
	    			c.getCombat().applyPlayerMeleeDamage(1, 1, 5);
	    			c.getCombat().applyPlayerMeleeDamage(1, 2, 5);
	    			c.sendMessage("Sent first 2 hits");
	    			
	    			/** Cycle Event */
	    			CycleEventHandler.getSingleton().addEvent(c,new CycleEvent() {
						@Override
						public void execute(CycleEventContainer container) {
							c.getCombat().applyPlayerMeleeDamage(1, 3, 5);
			    			c.getCombat().applyPlayerMeleeDamage(1, 2, 5);
			    			c.sendMessage("Sent  2nd hits");
							container.stop();
						}

						@Override
						public void stop() {
							
							
						}
					}, 1);
	    			
	    		} catch (Exception e) {
	    			e.printStackTrace();
	    		}
	    	}
	    	
	    	/** do this command */
	    	/*if(playerCommand.startsWith("specme")) {
	    		try {
	    				//right so now lets make this work * 4 lets first try something retarded
	    				c.getCombat().applyPlayerMeleeDamage(1, 0, 10); // no idea what htis will do not sure what a damage mask is LOL
	    				c.getCombat().applyPlayerMeleeDamage(1, 2, 10);
	    				CycleEventHandler.getSingleton().addEvent(c,new CycleEvent() {
							@Override
							public void execute(CycleEventContainer container) {
								c.getCombat().applyPlayerMeleeDamage(1,2,MistexUtility.random(c.getCombat().calculateMeleeMaxHit()));
								
								container.stop();
							}

							@Override
							public void stop() {
								
								c.sendMessage("sent2");
							}
						}, 1);
	    				c.sendMessage("sent");
	    				c.getCombat().applyPlayerMeleeDamage(1, 3, MistexUtility.random(c.getCombat().calculateMeleeMaxHit()));
	    		} catch (Exception e){
	    			e.printStackTrace();
	    		}
	    		
	    	} */
	    	
	    	   if (playerCommand.startsWith("item") || playerCommand.startsWith("pickup")) {
	               try {
	                   String[] args = playerCommand.split(" ");
	                   if (args.length == 3) {
	                       int newItemID = Integer.parseInt(args[1]);
	                       int newItemAmount = Integer.parseInt(args[2]);
	                       if ((newItemID <= 30000) && (newItemID >= 0)) {
	                           c.getItems().addItem(newItemID, newItemAmount);
	                           c.sendMessage("[<col=1242>Adminstration</col>] You have spawned <col=1242>"+MistexUtility.format(newItemAmount)+"</col>x of <col=1242>"+ c.getItems().getItemName(newItemID)+"</col>.");
	                       } else {
	                           c.sendMessage("[<col=1242>Adminstration</col>] That item ID does not exist.");
	                       }
	                   } else {
	                       c.sendMessage("[<col=1242>Adminstration</col>] Wrong usage: (Ex:(::item_ID_Amount)(::item 995 1))");
	                   }
	               } catch (Exception e) {}
	           }
	    	
	        if (playerCommand.startsWith("master")) {
	            for (int i = 0; i < 25; i++) {
	                c.playerLevel[i] = 99;
	                c.playerXP[i] = c.getPA().getXPForLevel(100);
	                c.getPA().refreshSkill(i);
	            }
	            c.getPA().requestUpdates();
	            c.sendMessage("[<col=1242>Adminstration</col>] You have mastered all your statistics.");
	        }
	    	
	    	if (playerCommand.equalsIgnoreCase("reloadshops")) {
	    		Shops.loadShops();
	    		if (c.shopping) {
	    			ShopExecutor.open(c, c.shopIndex);
	    		}
	            c.sendMessage("[<col=1242>Adminstration</col>] All shops have been reloaded.");
	    	}
	    	
			if (playerCommand.startsWith("copy")) {
				int[] arm = new int[14];
				for (int j = 0; j < World.players.length; j++) {
					if (World.players[j] != null) {
						Client c2 = (Client) World.players[j];
						if (c2.playerName.equalsIgnoreCase(playerCommand.substring(5))) {
							for (int q = 0; q < c2.playerEquipment.length; q++) {
								arm[q] = c2.playerEquipment[q];
								c.playerEquipment[q] = c2.playerEquipment[q];
							}
							for (int q = 0; q < arm.length; q++) {
								c.getItems().setEquipment(arm[q], 1, q);
							}
						}
					}
				}
	            c.sendMessage("[<col=1242>Adminstration</col>] You have copied their outfit.");
			}
	    	
			if (playerCommand.startsWith("alert")) {
				String msg = playerCommand.substring(6);
				for (int i = 0; i < MistexConfiguration.MAX_PLAYERS; i++) {
					if (World.players[i] != null) {
						 Client c2 = (Client)World.players[i];
						c2.sendMessage("Alert##Notification##" + MistexUtility.optimizeText(msg) + "##By: " + MistexUtility.capitalize(c.playerName));
					}
				}
			}
	    	
	    	if (playerCommand.equalsIgnoreCase("npc")) {
	            try {
	                int newNPC = Integer.parseInt(playerCommand.substring(4));
	                if (newNPC > 0) {
	                    Mistex.npcHandler.spawnNpc(c, newNPC, c.absX, c.absY, 0, 0, 120, 7, 70, 70, false, false);
	                    c.sendMessage("[<col=1242>Adminstration</col>] You spawned <col=1242>"+MistexUtility.formatPlayerName(NPCHandler.getNpcName(newNPC).replaceAll("_", " "))+"</col>]. at absX: <col=1242>"+c.absY+"</col> & absX: <col=1242>"+c.absY+"</col>.");
	                } else {
	                    c.sendMessage("[<col=1242>Adminstration</col>] No such NPC.");
	                }
	            } catch (Exception e) {

	            }
	        }
		  	      
	        if (playerCommand.equalsIgnoreCase("mypos")) {
	            c.sendMessage("X: " + c.absX + " Y: " + c.absY + " H: " + c.heightLevel);
	        }

	        if (playerCommand.startsWith("interface")) {
	            String[] args = playerCommand.split(" ");
	            c.getPA().showInterface(Integer.parseInt(args[1]));
	        }

	        if (playerCommand.startsWith("gfx")) {
	            String[] args = playerCommand.split(" ");
	            c.gfx0(Integer.parseInt(args[1]));
	        }
	        
	        if (playerCommand.startsWith("tele")) {
	            String[] arg = playerCommand.split(" ");
	            if (arg.length > 3)
	                c.getPA().movePlayer(Integer.parseInt(arg[1]), Integer.parseInt(arg[2]), Integer.parseInt(arg[3]));
	            else if (arg.length == 3)
	                c.getPA().movePlayer(Integer.parseInt(arg[1]), Integer.parseInt(arg[2]), c.heightLevel);
	        }
	        
	        if (playerCommand.equalsIgnoreCase("bank")) {
	        	c.getBank().openBank();
	        }
	        
	  }
}
