package org.mistex.game.world.content.consumables;

import org.mistex.game.world.content.achievement.AchievementHandler;
import org.mistex.game.world.content.achievement.AchievementList;
import org.mistex.game.world.content.interfaces.InterfaceText;
import org.mistex.game.world.content.interfaces.impl.AchievementTab;
import org.mistex.game.world.event.CycleEvent;
import org.mistex.game.world.event.CycleEventContainer;
import org.mistex.game.world.event.CycleEventHandler;
import org.mistex.game.world.player.Client;

@SuppressWarnings({"static-access", "unused"})
public class Potions {

	private Client c;

	public Potions(Client c) {
		this.c = c;
	}

	public void handlePotion(int itemId, int slot) {
		if (c.duelRule[5]) {
			c.sendMessage("You may not drink potions in this duel.");
			return;
		}
		if (System.currentTimeMillis() - c.potDelay >= 900) {
			c.potDelay = System.currentTimeMillis();
			c.getCombat().resetPlayerAttack();
			c.attackTimer++;
			c.potionsDrank += 1;
			potionMessages(itemId);
			if (c.potionsDrank == 1)
				c.sendMessage("@war@You have started the achievement: Mr Thirsty ");
				InterfaceText.writeText(new AchievementTab(c));
			if (c.potionsDrank == 1000)
				AchievementHandler.activateAchievement(c, AchievementList.MR_THIRSTY);
			switch (itemId) {
			case 6685: // brews
				doTheBrew(itemId, 6687, slot);
				break;
			case 6687:
				doTheBrew(itemId, 6689, slot);
				break;
			case 6689:
				doTheBrew(itemId, 6691, slot);
				break;
			case 6691:
				doTheBrew(itemId, 229, slot);
				break;
			case 2436:
				drinkStatPotion(itemId, 145, slot, 0, true);
				break;
			case 145:
				drinkStatPotion(itemId, 147, slot, 0, true);
				break;
			case 147:
				drinkStatPotion(itemId, 149, slot, 0, true);
				break;
			case 149:
				drinkStatPotion(itemId, 229, slot, 0, true);
				break;
			case 3040:
				drinkMagicPotion(itemId, 3042, slot, 6, false);
				break;
			case 3042:
				drinkMagicPotion(itemId, 3044, slot, 6, false);
				break;
			case 3044:
				drinkMagicPotion(itemId, 3046, slot, 6, false);
				break;
			case 3046:
				drinkMagicPotion(itemId, 229, slot, 6, false);
				break;
			case 2440:
				drinkStatPotion(itemId, 157, slot, 2, true); 
				break;
			case 157:
				drinkStatPotion(itemId, 159, slot, 2, true);
				break;
			case 159:
				drinkStatPotion(itemId, 161, slot, 2, true);
				break;
			case 161:
				drinkStatPotion(itemId, 229, slot, 2, true);
				break;
			case 2444:
				drinkStatPotion(itemId, 169, slot, 4, false); 
				break;
			case 169:
				drinkStatPotion(itemId, 171, slot, 4, false);
				break;
			case 171:
				drinkStatPotion(itemId, 173, slot, 4, false);
				break;
			case 173:
				drinkStatPotion(itemId, 229, slot, 4, false);
				break;
			case 2432:
				drinkStatPotion(itemId, 133, slot, 1, false); 
				break;
			case 133:
				drinkStatPotion(itemId, 135, slot, 1, false);
				break;
			case 135:
				drinkStatPotion(itemId, 137, slot, 1, false);
				break;
			case 137:
				drinkStatPotion(itemId, 229, slot, 1, false);
				break;
			case 113:
				drinkStatPotion(itemId, 115, slot, 2, false); 
				break;
			case 115:
				drinkStatPotion(itemId, 117, slot, 2, false);
				break;
			case 117:
				drinkStatPotion(itemId, 119, slot, 2, false);
				break;
			case 119:
				drinkStatPotion(itemId, 229, slot, 2, false);
				break;
			case 2428:
				drinkStatPotion(itemId, 121, slot, 0, false); 
				break;
			case 121:
				drinkStatPotion(itemId, 123, slot, 0, false);
				break;
			case 123:
				drinkStatPotion(itemId, 125, slot, 0, false);
				break;
			case 125:
				drinkStatPotion(itemId, 229, slot, 0, false);
				break;
			case 2442:
				drinkStatPotion(itemId, 163, slot, 1, true);
				break;
			case 163:
				drinkStatPotion(itemId, 165, slot, 1, true);
				break;
			case 165:
				drinkStatPotion(itemId, 167, slot, 1, true);
				break;
			case 167:
				drinkStatPotion(itemId, 229, slot, 1, true);
				break;
			case 3024:
				drinkPrayerPot(itemId, 3026, slot, true); 
				break;
			case 3026:
				drinkPrayerPot(itemId, 3028, slot, true);
				break;
			case 3028:
				drinkPrayerPot(itemId, 3030, slot, true);
				break;
			case 3030:
				drinkPrayerPot(itemId, 229, slot, true);
				break;
			case 2434:
				drinkPrayerPot(itemId, 139, slot, false);
				break;
			case 139:
				drinkPrayerPot(itemId, 141, slot, false);
				break;
			case 141:
				drinkPrayerPot(itemId, 143, slot, false);
				break;
			case 143:
				drinkPrayerPot(itemId, 229, slot, false);
				break;
			case 2446:
				drinkAntiPoison(itemId, 175, slot, 30000); 
				break;
			case 175:
				drinkAntiPoison(itemId, 177, slot, 30000);
				break;
			case 177:
				drinkAntiPoison(itemId, 179, slot, 30000);
				break;
			case 179:
				drinkAntiPoison(itemId, 229, slot, 30000);
				break;
			case 2448:
				drinkAntiPoison(itemId, 181, slot, 300000); 
				break;
			case 181:
				drinkAntiPoison(itemId, 183, slot, 300000);
				break;
			case 183:
				drinkAntiPoison(itemId, 185, slot, 300000);
				break;
			case 185:
				drinkAntiPoison(itemId, 229, slot, 300000);
				break;
			case 15332:
				doOverload(itemId, 15333, slot);
				break;
			case 15333:
				doOverload(itemId, 15334, slot);
				break;
			case 15334:
				doOverload(itemId, 15335, slot);
				break;
			case 15335:
				doOverload(itemId, 229, slot);
				break;
			case 2452:
				antifirePot(itemId,2454,slot);
			break;
			case 2454:
				antifirePot(itemId,2456,slot);
			break;
			case 2456:
				antifirePot(itemId,2458,slot);
			break;
			case 2458:
				antifirePot(itemId,229,slot);
			break;
			}
		}
	}
	
	public void antifirePot(int itemId, int replaceItem, int slot){
		c.startAnimation(829);
		c.playerItems[slot] = replaceItem + 1;
		c.antiFirePot = true;
		antiFirePotion();
		c.sendMessage("<col=191,62,255>Your immunity against dragon fire has been increased.");
		c.getItems().resetItems(3214);
		
	}
	
	public void antiFirePotion(){
		CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
            public void execute(CycleEventContainer p) {
            	c.antiFirePot = false;	
				c.sendMessage("Your resistance to dragon fire has worn off.");
                p.stop();
            }
            	@Override
                public void stop() {
                    //CloseInterface();					
            	}
            }, 100);
		
	}

	public void potionMessages(int itemId) {
        c.sendMessage("You drink some of your "+ c.getItems().getItemName(itemId) +".");
        String item =  c.getItems().getItemName(itemId);
        if(item.endsWith("(4)")) {
        	c.sendMessage("You have 3 doses of potion left.");
        } else if(item.endsWith("(3)")) {
        	c.sendMessage("You have 2 doses of potion left.");
        } else if(item.endsWith("(2)")) {
        	c.sendMessage("You have 1 dose of potion left.");
        } else if(item.endsWith("(1)")) {
        	c.sendMessage("You have finished your potion.");
        }
	}
	
	public void doOverload(int itemId, int replaceItem, int slot) {
	if (System.currentTimeMillis() - c.overloadDelay >= 1000) {
		if (c.inWild()) {
			c.sendMessage("You can not do this in the wilderness!");
			return;
		}
		
		c.overloadDelay = System.currentTimeMillis();
		int health = c.playerLevel[3];
		int herbLevel = c.playerLevel[15];
		if (health < 60) {
			c.sendMessage("I should get some more lifepoints before using this!");
			return;
		}
		c.startAnimation(829);
		c.playerItems[slot] = replaceItem + 1;
		c.getItems().resetItems(3214);
		c.hasOverloadBoost = true;
		doOverloadBoost();
		handleOverloadTimers();
		   } else {
               c.sendMessage("@blu@Chill man!");
           }
	}
	
	public void handleOverloadTimers() {
		CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer b) {
				if (c == null)
					b.stop();
				c.hasOverloadBoost = false;
			}
			@Override
			public void stop() { }
		}, 500); 
		CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer b) {
				if (c != null){
					if (c.hasOverloadBoost)
						doOverloadBoost();
					else {
						b.stop();
						int[] toNormalise = {0,1,2,4,6};
						for (int i = 0; i < toNormalise.length; i++) {
							c.playerLevel[toNormalise[i]] = c.getLevelForXP(c.playerXP[toNormalise[i]]);
						}
					}
				} else
					b.stop();

			}
			@Override
			public void stop() {}
		}, 25); 
		CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
			int counter2 = 0;
			@Override
			public void execute(CycleEventContainer b) {
				if (c == null)
					b.stop();
				if (counter2 < 5) {
					c.startAnimation(3170);
					c.dealDamage(10);
					c.handleHitMask(10); 
					c.getPA().refreshSkill(3);
					counter2++;
				} else
					b.stop();
			}

			@Override
			public void stop() {}
		}, 3); 
	}

	public void doOverloadBoost() {
		int[] toIncrease = {0,1,2,4,6};
		int boost;
		for (int i = 0; i < toIncrease.length; i++) {
			boost = (int)(getOverloadBoost(toIncrease[i]));
			c.playerLevel[toIncrease[i]] += boost;
			if (c.playerLevel[toIncrease[i]] > (c.getLevelForXP(c.playerXP[toIncrease[i]]) + boost))
				c.playerLevel[toIncrease[i]] = (c.getLevelForXP(c.playerXP[toIncrease[i]]) + boost);
			c.getPA().refreshSkill(toIncrease[i]);
		}
	}

	public double getOverloadBoost(int skill) {
		double boost = 1;
		switch(skill) {
		case 0:
		case 1:
		case 2:
			boost = 5+ (c.getLevelForXP(c.playerXP[skill]) * .22);
			break;
		case 4:
			boost = 3+ (c.getLevelForXP(c.playerXP[skill]) * .22);
			break;
		case 6:
			boost = 7;
			break;
		}
		return boost;
	}

	public void drinkAntiPoison(int itemId, int replaceItem, int slot, long delay) {
		c.startAnimation(829);
		c.playerItems[slot] = replaceItem + 1;
		c.getItems().resetItems(3214);
		curePoison(delay);
	}

	public void curePoison(long delay) {
		c.poisonDamage = 0;
		c.poisonImmune = delay;
		c.lastPoisonSip = System.currentTimeMillis();
	}

	public void drinkStatPotion(int itemId, int replaceItem, int slot, int stat, boolean sup) {
		c.startAnimation(829);
		c.playerItems[slot] = replaceItem + 1;
		c.getItems().resetItems(3214);
		enchanceStat(stat, sup);
	}

	public void drinkMagicPotion(int itemId, int replaceItem, int slot, int stat, boolean sup) {
		c.startAnimation(829);
		c.playerItems[slot] = replaceItem + 1;
		c.getItems().resetItems(3214);
		enchanceMagic(stat, sup);

	}

	public void enchanceMagic(int skillID, boolean sup) {
		c.playerLevel[skillID] += getBoostedMagic(skillID, sup);
		c.getPA().refreshSkill(skillID);
	}

	public int getBoostedMagic(int skill, boolean sup) {
		int increaseBy = 0;
		if (sup)
			increaseBy = (int) (c.getLevelForXP(c.playerXP[skill]) * .06);
		else
			increaseBy = (int) (c.getLevelForXP(c.playerXP[skill]) * .06);
		if (c.playerLevel[skill] + increaseBy > c
				.getLevelForXP(c.playerXP[skill]) + increaseBy + 1) {
			return c.getLevelForXP(c.playerXP[skill]) + increaseBy
					- c.playerLevel[skill];
		}
		return increaseBy;
	}

	public void drinkPrayerPot(int itemId, int replaceItem, int slot, boolean rest) {
		c.startAnimation(829);
		c.playerItems[slot] = replaceItem + 1;
		c.getItems().resetItems(3214);
		c.playerLevel[5] += (c.getLevelForXP(c.playerXP[5]) * .33);
		if (rest)
			c.playerLevel[5] += 1;
		if (c.playerLevel[5] > c.getLevelForXP(c.playerXP[5]))
			c.playerLevel[5] = c.getLevelForXP(c.playerXP[5]);
		c.getPA().refreshSkill(5);
		if (rest)
			restoreStats();
	}

	public void restoreStats() {
		for (int j = 0; j <= 6; j++) {
			if (j == 5 || j == 3)
				continue;
			if (c.playerLevel[j] < c.getLevelForXP(c.playerXP[j])) {
				c.playerLevel[j] += (c.getLevelForXP(c.playerXP[j]) * .33);
				if (c.playerLevel[j] > c.getLevelForXP(c.playerXP[j])) {
					c.playerLevel[j] = c.getLevelForXP(c.playerXP[j]);
				}
				c.getPA().refreshSkill(j);
				c.getPA().setSkillLevel(j, c.playerLevel[j], c.playerXP[j]);
			}
		}
	}

	public void doTheBrew(int itemId, int replaceItem, int slot) {
	if (c.duelRule[6]) {
		c.sendMessage("You may not eat in this duel.");
		return;
	}
	c.startAnimation(829);
	c.playerItems[slot] = replaceItem + 1;
	c.getItems().resetItems(3214);
	int[] toDecrease = {0,2,4,6};
	
	int[] toIncrease = {1,3};
	for (int tD : toDecrease) {
		c.playerLevel[tD] -= getBrewStat(tD, .10);
		if (c.playerLevel[tD] < 0)
			c.playerLevel[tD] = 1;
		c.getPA().refreshSkill(tD);
		c.getPA().setSkillLevel(tD, c.playerLevel[tD], c.playerXP[tD]);
	}
	c.playerLevel[1] += getBrewStat(1, .20);		
	if (c.playerLevel[1] > (c.getLevelForXP(c.playerXP[1])*1.2 + 1)) {
		c.playerLevel[1] = (int)(c.getLevelForXP(c.playerXP[1])*1.2);
	}
	c.getPA().refreshSkill(1);
	double ammount = c.torva() ? 1.22 : 1.17;
	c.playerLevel[3] += c.torva() ? getBrewStat(3, .21) : getBrewStat(3, .15);
	if (c.playerLevel[3] > (c.getLevelForXP(c.playerXP[3])* ammount + 1)) {
		c.playerLevel[3] = (int)(c.getLevelForXP(c.playerXP[3])*ammount);
	}
	c.getPA().refreshSkill(3);
	message(itemId);
}
	
	public void message(int itemId) {
		String name = c.getItems().getItemName(itemId);
		c.sendMessage("You drink 1 dose of "+ name);
		c.sendMessage(name.contains("(4)") ? "You now have 3 doses left" :name.contains("(3)") ? "You now have 2 doses left" : name.contains("(2)") ? "You now have 1 doses left" : "You now have 0 doses left");
	}

	public void enchanceStat(int skillID, boolean sup) {
		c.playerLevel[skillID] += getBoostedStat(skillID, sup);
		c.getPA().refreshSkill(skillID);
	}

	public int getBrewStat(int skill, double amount) {
		return (int) (c.getLevelForXP(c.playerXP[skill]) * amount);
	}

	public int getBoostedStat(int skill, boolean sup) {
		int increaseBy = 0;
		if (sup)
			increaseBy = (int) (c.getLevelForXP(c.playerXP[skill]) * .20);
		else
			increaseBy = (int) (c.getLevelForXP(c.playerXP[skill]) * .13) + 1;
		if (c.playerLevel[skill] + increaseBy > c
				.getLevelForXP(c.playerXP[skill]) + increaseBy + 1) {
			return c.getLevelForXP(c.playerXP[skill]) + increaseBy
					- c.playerLevel[skill];
		}
		return increaseBy;
	}

	public boolean isPotion(int itemId) {
		String name = c.getItems().getItemName(itemId);
		return name.contains("(4)") || name.contains("(3)")|| name.contains("(2)") || name.contains("(1)");
	}
	
}