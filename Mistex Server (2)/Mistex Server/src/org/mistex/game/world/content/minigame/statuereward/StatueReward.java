package org.mistex.game.world.content.minigame.statuereward;

import org.mistex.game.MistexUtility;
import org.mistex.game.world.content.achievement.AchievementHandler;
import org.mistex.game.world.content.achievement.AchievementList;
import org.mistex.game.world.content.interfaces.InterfaceText;
import org.mistex.game.world.content.interfaces.impl.AchievementTab;
import org.mistex.game.world.event.CycleEvent;
import org.mistex.game.world.event.CycleEventContainer;
import org.mistex.game.world.event.CycleEventHandler;
import org.mistex.game.world.player.Client;

/**
 * StatueReward 
 * @author Omar | Play Boy
 */

@SuppressWarnings("static-access")
public class StatueReward {

		private Client c;
		
		public StatueReward(Client c) {
			this.c = c;
		}
		
		/**
		 * Winning item variable
		 */
		int winningItem;

		/**
		 * Expensive amount
		 */
		public int expensiveAmount = 10000000; //10 Million
		
		/**
		 * Items rewarded 
		 */
		int[] rewwardItems = { 
				/* Common Items */
		4151,4151,11732,11732,10828,10828,7462,7462,6585,6575,6737,6737,
		1725,1725,10551,1215,1215,4587,4587,1079,1079,4153,4153,6920,6920,
		6922,6922,6918,6918,544,544,542,542,1201,1201,2653,2653,2655,2655,
		3478,3478,391,391,2440,2440,6685,6685,1249,1249,1434,1434,1305,1305,
		4087,4087,4585,4585,995,995,1187,1187,1073,1073,1123,1123,4119,4119,
		1333,1333,1319,1319,2639,2639,2641,2641,2643,2643,4049,4049,989,989,
		1333, 9005, 9006,2591,2593,2595,2597,2607,2609,2611,2613,2615,2617,2619,2621,3473,3475,3476,7362,7366,7370,7374,7378,7382,7386,7390,7394,
						
				/* Rare Items*/
		11694,11724,11726,11283,11696,11698,11700,11730,6570, 11283,
		};
		
		/**
		 * Randomizes the items
		 * @return
		 */
		public int handleRewardItems() {
			return rewwardItems[(int) (Math.random() * rewwardItems.length)];
		}		
		
		/**
		 * Checks if player can play
		 */
		public void canPlay() {
			if(System.currentTimeMillis() - c.pkStatueDelay >= 5000) {
				if (c.pkPoints <= 49) {
					c.sendMessage("@blu@It cost 50 Pk points to play this!");
					return;
				}
				if (c.inWild()) {
					c.sendMessage("@blu@You can not open this in the wilderness!");
					return;
				}
				if (c.getItems().freeSlots() < 5) {
					c.sendMessage("Y@blu@ou need at least 5 inventory slots to open this box!");
					return;
				}
				if (c.disconnected) {
					c.sendMessage("@blu@Um what?");
					return;
				}
				if (c.isDead) {
					c.sendMessage("@blu@You can not do this while you are dead!");
					return;
				}
				governmentTime();
				} else {
	        		c.sendMessage("@blu@Please wait a couple of seconds before doing this again!");
	        	}
		}

		/**
		 * It's government time!
		 */
		public void governmentTime() {
			c.pkStatueDelay = System.currentTimeMillis();
			c.pkPoints -= 50;
			startStatue();
			c.pkStatuePlays +=1;
			c.sendMessage("@red@PLEASE DO NOT EXAIME THE ITEM, CLIENT WILL CRASH!");
			if (c.pkStatuePlays == 1)
				c.sendMessage("@war@You have started the achievement: The Gamer ");
				InterfaceText.writeText(new AchievementTab(c));
			if (c.pkStatuePlays == 100)
				AchievementHandler.activateAchievement(c, AchievementList.THE_GAMER);
		}
		
		/**
		 * Starts the statue
		 */
		public void startStatue() {
			CycleEventHandler.getSingleton().addEvent(winningItem, new CycleEvent() {
				public void execute(CycleEventContainer p) {
						c.getPA().sendFrame34a(16002, handleRewardItems(), 0, 1);
						if (MistexUtility.random(20) == 1) {
							winningItem = handleRewardItems();
							c.getPA().sendFrame34a(15002, winningItem, 0, 1);
							c.getItems().addItem(winningItem, 1);
							c.getPA().showInterface(15000);
							sendWinningMessage();
						p.stop();
						}
					}
				@Override
				public void stop() {
					CloseInterface();					
				}
			}, 1);
			c.getPA().sendFrame126("", 16003);
			c.getPA().showInterface(16000);
		}		
		
		/**
		 * Sends winning message
		 */
		private void sendWinningMessage() {
			if(c.getItems().getItemName(winningItem).endsWith("s")) {
				c.getPA().sendFrame126("You've won " + c.getItems().getItemName(winningItem) + "!", 15003);
				c.sendMessage("[ <col=2784FF>PvP Statue </col>] Congratulations! You have won <col=2784FF>" + c.getItems().getItemName(winningItem) + "</col>!");
			} else {
				c.getPA().sendFrame126("You've won a " + c.getItems().getItemName(winningItem) + "!", 15003);
				c.sendMessage("[ <col=2784FF>PvP Statue </col>] Congratulations! You have won a <col=2784FF>" + c.getItems().getItemName(winningItem) + "</col>!");
			}
			ExpensiveReward();
		}
		
		private void ExpensiveReward() {
			if (c.getItems().getItemPrice(winningItem) > expensiveAmount) {
				if(c.getItems().getItemName(winningItem).endsWith("s")) {
					c.getPA().yell("[ <col=2784FF>PvP Statue </col>] "+c.getRights().determineIcon()+"<col=2784FF>" + MistexUtility.capitalize(c.playerName) + " </col>has just won <col=2784FF>" + c.getItems().getItemName(winningItem) + "</col>!");
				} else {
					c.getPA().yell("[ <col=2784FF>PvP Statue </col>] "+c.getRights().determineIcon()+"<col=2784FF>" + MistexUtility.capitalize(c.playerName) + " </col>has just won a <col=2784FF>" + c.getItems().getItemName(winningItem) + "</col>!");
				}
				c.startAnimation(2109);	
			}
		}
		
		/**
		 * Closes the interface
		 */
		private void CloseInterface() {
			CycleEventHandler.getSingleton().addEvent(winningItem, new CycleEvent() {
				public void execute(CycleEventContainer p) {
					c.getPA().removeAllWindows();
					p.stop();
				}
				@Override
				public void stop() {
					
				}
			},5);	
		}		
		
	}