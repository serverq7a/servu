package org.mistex.game.world.content.skill.mining;

import org.mistex.game.MistexUtility;
import org.mistex.game.world.content.achievement.AchievementHandler;
import org.mistex.game.world.content.achievement.AchievementList;
import org.mistex.game.world.content.interfaces.InterfaceText;
import org.mistex.game.world.content.interfaces.impl.AchievementTab;
import org.mistex.game.world.content.skill.Skill;
import org.mistex.game.world.content.skill.SkillHandler;
import org.mistex.game.world.content.skill.smithing.Smelting;
import org.mistex.game.world.event.CycleEvent;
import org.mistex.game.world.event.CycleEventContainer;
import org.mistex.game.world.event.CycleEventHandler;
import org.mistex.game.world.gameobject.ObjectHandler;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.Player;
import org.mistex.game.world.player.item.ItemAssistant;

public class Mining extends Skill {

	public Mining() {
		super(Skills.MINING);
	}

	public static void mineEss(final Client c, final int object) {
		if (!noInventorySpace(c, "mining")) {
			resetMining(c);
			return;
		}
		
		if (System.currentTimeMillis() - c.miningDelay < 4500) {
			return;
		}
		
		if (c.underAttackBy > 0 || c.underAttackBy2 > 0) {
			c.sendMessage("You can't mine while in combat!");
			return;
		}
		
		if (!hasPickaxe(c)) {
			c.sendMessage("You need a Mining pickaxe which you need a Mining level to use.");
			return;
		}
		if (c.isSkilling || c.playerSkilling[14]) {
			return;
		}

		c.playerSkilling[14] = true;
		c.stopPlayerSkill = true;
		c.isSkilling = true;
		c.miningDelay = System.currentTimeMillis();
		c.startAnimation(getAnimation(c));
		c.turnPlayerTo(c.objectX, c.objectY);
		CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				c.turnPlayerTo(c.objectX, c.objectY);
				new Mining().giveItem(c, 1436, 1, "You manage to mine some "+ ItemAssistant.getItemName(1436).toLowerCase() + ".");
				new Mining().addExp(c, 5);
				c.startAnimation(getAnimation(c));
				if (!hasPickaxe(c)) {
					c.sendMessage("You need a pickaxe to mine this rock.");
					resetMining(c);
					container.stop();
				}
				if (!c.stopPlayerSkill) {
					resetMining(c);
					container.stop();
				}
				if (!c.isSkilling) {
					resetMining(c);
					container.stop();
				}
				if (!noInventorySpace(c, "mining")) {
					resetMining(c);
					container.stop();
				}
			}
			@Override
			public void stop() {

			}
		}, 2);
	}

	public static void attemptData(final Client c, final int object, final int obX, final int obY) {
		if (c.underAttackBy > 0 || c.underAttackBy2 > 0) {
			c.sendMessage("You can't mine while in combat!");
			return;
		}
		
		if (!noInventorySpace(c, "mining")) {
			resetMining(c);
			return;
		}
		
		final Ore ore = Ore.forId(object);
		final Pickaxe pickaxe = Pickaxe.getPickaxe(c);
		
		if (ore == null || pickaxe == null || Skill.unavailableObjects.contains((Integer) (obX << 16 | obY << 8 | object)))
			return;
		
		if (!hasRequiredLevel(c, 14, ore.getLevel(), "mining","mine here")) {
			return;
		}
		
		c.sendMessage("You swing your pick at the rock.");
		
		if (c.playerSkilling[14] || c.isSkilling) {
			return;
		}
		
		c.isSkilling = true;
		c.playerSkilling[14] = true;
		c.startAnimation(getAnimation(c));
		c.turnPlayerTo(obX, obY);
		
		CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				if (!canMine(c)) {
					container.stop();
					return;
				}
				
				c.turnPlayerTo(obX, obY);
				new Mining().addExp(c, ore.getExp());
				
				if (Pickaxe.getPickaxe(c).equals(Pickaxe.INFERNO_ADZ) && Math.random() > 0.75 && Smelting.BarData.forId(ore.getProduct()) != null) {
					int exp = Smelting.BarData.forId(ore.getProduct()).getXp() * Skills.SMITHING.getMultiplier();
					new Smelting().addExp(c, Smelting.BarData.forId(ore.getProduct()).getXp());
					new Mining().giveItem(c, Smelting.BarData.forId(ore.getProduct()).getProduct(), 1, "The adze's heat instantly refines the ore, granting " + MistexUtility.format(exp) + " smithing EXP");
				} else {
					giveGems(c);
					new Mining().giveItem(c, ore.getProduct(), 1, "You manage to mine some "+ ItemAssistant.getItemName(ore.getProduct()).toLowerCase() + ".");
				}
				
				c.oresRecieved += 1;
				
				if (c.oresRecieved == 1) {
					c.sendMessage("@war@You have started the achievement: Oreo ");
					InterfaceText.writeText(new AchievementTab(c));
				}
				
				if (c.oresRecieved == 1500)
					AchievementHandler.activateAchievement(c, AchievementList.OREO);
				
				Mining.resetMining(c);
				container.stop();
			}

			@Override
			public void stop() {
				Skill.unavailableObjects.add((Integer) (obX << 16 | obY << 8 | object));										
				ObjectHandler.createAnObject(c, 451, obX, obY);
				
				c.playerSkilling[14] = false;
				c.isSkilling = false;
				
				CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						Skill.unavailableObjects.remove((Integer) (obX << 16 | obY << 8 | object));
						ObjectHandler.createAnObject(c, object, obX, obY);
						container.stop();
					}

					@Override
					public void stop() {
					}
				}, ore.getRespawn());
			}
		}, getTimer(c, ore, pickaxe));

		CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				if(c.playerSkilling[Player.playerMining]) {
					c.startAnimation(pickaxe.getAnimation());
				}
				if(!c.isSkilling) {
					resetMining(c);
					container.stop();
				}
			}
			@Override
			public void stop() {

			}
		}, pickaxe.equals(Pickaxe.INFERNO_ADZ) ? 7 : 9);
	}
	
	public static int getTimer(Client c, Ore ore, Pickaxe pickaxe) {
		if (c.skillPrestiges[Player.playerMining] == 5)
			return 4;
		int init = (ore.getLevel() << (4 - (pickaxe.getLevel() >> 4)) | c.playerLevel[Player.playerWoodcutting] >> 4) >> 3;
		if (init <= 1)
			return 4;
		return init;
	}
	
	public static boolean canMine(Client c) {
		if (!c.playerSkilling[14]) {
			Mining.resetMining(c);
			return false;
		}
		
		if (!Mining.hasPickaxe(c)) {
			Mining.resetMining(c);
			return false;
		}
		
		if (!Mining.noInventorySpace(c, "mining")) {
			Mining.resetMining(c);
			return false;
		}
		return true;
	}
	
	public static void resetMining(Client c) {
		c.startAnimation(65535);
		SkillHandler.resetPlayerVariables(c);
	}

	public static boolean miningRocks(Client c, int object) {
		return Ore.forId(object) != null;
	}

	public static boolean hasPickaxe(Client c) {
		return Pickaxe.getPickaxe(c) != null;
	}

	public static int getAnimation(Client c) {
		Pickaxe pickaxe = Pickaxe.getPickaxe(c);
		
		if (pickaxe == null)
			return 65535;
		
		return pickaxe.getAnimation();
	}
	
	private static final int[] RANDOM_GEMS = {1623,1621,1619,1617};
	
	private static void giveGems(Client c) {
		if (MistexUtility.random(10) == 1) {
			c.getItems().addItem(RANDOM_GEMS[(int)(RANDOM_GEMS.length * Math.random())], 1);
			c.sendMessage("You find a gem!");
		}
	}

	public static void prospectRock(final Client c, final String itemName) {
		c.sendMessage("You examine the rock for ores...");
        CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
        	@Override
            public void execute(CycleEventContainer container) {
				c.sendMessage("This rock contains " + itemName + ".");
				container.stop();
			}

            @Override
            public void stop() {

            }
        }, 3);
	}

	public static void prospectNothing(final Client c) {
		c.sendMessage("You examine the rock for ores...");
        CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
        	@Override
            public void execute(CycleEventContainer container) {
				c.sendMessage("There is no ore left in this rock.");
				container.stop();
			}
            @Override
            public void stop() {

            }
        }, 3);
	}
}