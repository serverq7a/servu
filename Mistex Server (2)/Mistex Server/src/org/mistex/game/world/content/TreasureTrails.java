package org.mistex.game.world.content;

import org.mistex.game.MistexUtility;
import org.mistex.game.world.player.Client;


public class TreasureTrails {
	
	public static int lowLevelReward[] = {
		1077, 1125, 1165, 1195,
		1297, 1367, 853, 7390,
		7392, 7394, 7396, 7386, 
		7388, 1099, 1135, 1065,
		851
	};
	public static int mediemLevelReward[] = {
		1073, 1123, 1161, 1199,
		1301, 1371, 857, 2577,
		2579, 2487, 2493, 2499, 
		2631, 855
	};
	public static int highLevelReward[] = {
		1079, 1093, 1113, 1127,
		1147, 1163, 1185, 1201,
		1275, 1303, 1319, 1333,
		1359, 1373, 2491, 2497,
		2503, 861, 859, 2581,
		2651, 1079, 1093, 1113, 
		1127, 1147, 1163, 1185, 
		1201, 1275, 1303, 1319, 
		1333, 1359, 1373, 2491, 
		2497, 2503, 861, 859, 
		2581, 2651,
	};
	
	public static int lowLevelStacks[] = {
		995, 380, 561, 886, 
	};
	public static int mediumLevelStacks[] = {
		995, 374, 561, 563, 890,
	};
	public static int highLevelStacks[] = {
		995, 386, 561, 563, 560, 892
	};
	public static int allStacks[] = {
		995, 380, 561, 886,
		374, 561, 563, 890,
		386, 561, 563, 560, 
		892
	};
	
	public static void addClueReward(Client c, int clueLevel) {
		int chanceReward = MistexUtility.random(2);
		if(clueLevel == 0) {
			switch (chanceReward) {
				case 0: 
				displayReward(c, lowLevelReward[MistexUtility.random(lowLevelReward.length - 1)], 1, lowLevelReward[MistexUtility.random(lowLevelReward.length - 1)], 1, lowLevelStacks[MistexUtility.random(lowLevelStacks.length - 1)], 1 + MistexUtility.random(150)); 
				break;
				case 1: 
				displayReward(c, lowLevelReward[MistexUtility.random(lowLevelReward.length - 1)], 1, lowLevelStacks[MistexUtility.random(lowLevelStacks.length - 1)], 1 + MistexUtility.random(150), -1, 1); 
				break;
				case 2: 
				displayReward(c, lowLevelReward[MistexUtility.random(lowLevelReward.length - 1)], 1, -1, 1, -1, 1); 
				break;
			}
		} else if(clueLevel == 1) {
			switch (chanceReward) {
				case 0: 
				displayReward(c, mediemLevelReward[MistexUtility.random(mediemLevelReward.length - 1)], 1, mediemLevelReward[MistexUtility.random(mediemLevelReward.length - 1)], 1, mediumLevelStacks[MistexUtility.random(mediumLevelStacks.length - 1)], 1 + MistexUtility.random(200));
				break;
				case 1: 
				displayReward(c, mediemLevelReward[MistexUtility.random(mediemLevelReward.length - 1)], 1, mediumLevelStacks[MistexUtility.random(mediumLevelStacks.length - 1)], 1 + MistexUtility.random(200), -1, 1); 
				break;
				case 2: 
				displayReward(c, mediemLevelReward[MistexUtility.random(mediemLevelReward.length - 1)], 1, -1, 1, -1, 1);
				break;
			}
		} else if(clueLevel == 2) {
			switch (chanceReward) {
				case 0: 
				displayReward(c, highLevelReward[MistexUtility.random(highLevelReward.length - 1)], 1, highLevelReward[MistexUtility.random(highLevelReward.length - 1)], 1, highLevelStacks[MistexUtility.random(highLevelStacks.length - 1)], 1 + MistexUtility.random(350)); 
				break;
				case 1: 
				displayReward(c, highLevelReward[MistexUtility.random(highLevelReward.length - 1)], 1, highLevelStacks[MistexUtility.random(highLevelStacks.length - 1)], 1 + MistexUtility.random(350), -1, 1); 
				break;
				case 2: 
				displayReward(c, highLevelReward[MistexUtility.random(highLevelReward.length - 1)], 1, -1, 1, -1, 1); 
				break;
			}
		}
	}

	public static void displayReward(Client c, int item, int amount, int item2, int amount2, int item3, int amount3) {
		int[] items = {
			item, item2, item3
		};
		int[] amounts = {
			amount, amount2, amount3
		};
		c.outStream.createFrameVarSizeWord(53);
		c.outStream.writeWord(6963);
		c.outStream.writeWord(items.length);
		for(int i = 0; i < items.length; i++) {
			if(c.playerItemsN[i] > 254) {
				c.outStream.writeByte(255);
				c.outStream.writeDWord_v2(amounts[i]);
			} else {
				c.outStream.writeByte(amounts[i]);
			}
			if(items[i] > 0) {
				c.outStream.writeWordBigEndianA(items[i] + 1);
			} else {
				c.outStream.writeWordBigEndianA(0);
			}
		}
		c.outStream.endFrameVarSizeWord();
		c.flushOutStream();
		c.getItems().addItem(item, amount);
		c.getItems().addItem(item2, amount2);
		c.getItems().addItem(item3, amount3);
		c.getPA().showInterface(6960);
	}
	
}