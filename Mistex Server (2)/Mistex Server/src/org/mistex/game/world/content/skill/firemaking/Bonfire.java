package org.mistex.game.world.content.skill.firemaking;

import org.mistex.game.world.content.skill.Skill;
import org.mistex.game.world.content.skill.SkillHandler;
import org.mistex.game.world.event.CycleEvent;
import org.mistex.game.world.event.CycleEventContainer;
import org.mistex.game.world.event.CycleEventHandler;
import org.mistex.game.world.player.Client;

public class Bonfire extends Skill {

	public Bonfire() {
		super(Skills.FIREMAKING);
	}

	public static int FireID = 2732;
	public static int RING_OF_FIRE = 13659;
	public static int FLAME_GLOVES = 13660;

	public static enum BonfireData {
		REGULAR(1511, 1, 40, 6),
		OAK(1521, 15, 60, 4),
		WILLOW(1519, 30, 90, 4),
		MAPLE(1517, 45, 135, 3),
		YEW(1515, 60, 203, 3),
		MAGIC(1513, 75, 304, 3);

		int logId, levelReq, exp, timer;

		private BonfireData(int logId, int levelReq, int exp, int timer) {
			this.logId = logId;
			this.exp = exp;
			this.levelReq = levelReq;
			this.timer = timer;
		}

		public int getExp() {
			return exp;
		}

		public int getLevelReq() {
			return levelReq;
		}

		public int getLogId() {
			return logId;
		}

		public int getTimer() {
			return timer;
		}

		public static BonfireData forId(int log) {
			for (BonfireData data : values())
				if (data.getLogId() == log)
					return data;
			return null;
		}
	}

	public static double AddBonfireExp(Client c, BonfireData data) {
		double EXP = data.getExp();
		double Bonus = 1;
		if (c.playerEquipment[c.playerRing] != RING_OF_FIRE && c.playerEquipment[c.playerHands] != FLAME_GLOVES) {
			Bonus = 1;
		}
		if (c.playerEquipment[c.playerRing] == RING_OF_FIRE || c.playerEquipment[c.playerHands] == FLAME_GLOVES) {
			Bonus = 1.02;
		} else if (c.playerEquipment[c.playerRing] == RING_OF_FIRE && c.playerEquipment[c.playerHands] == FLAME_GLOVES) {
			Bonus = 1.05;
		}
		EXP *= Bonus;
		return EXP;
	}

	public static void AddBonFireLogs(final Client c, int log) {
		final BonfireData data = BonfireData.forId(log);

		if (data == null || c.isSkilling || c.playerSkilling[new Bonfire().skillId()])
			return;
		
		c.isSkilling = true;
		c.playerSkilling[new Bonfire().skillId()] = true;
		burnLogs(c, data);
		
		CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer bonfire) {
				if (!burnLogs(c, data)) {
					bonfire.stop();
				}
			}

			@Override
			public void stop() {
				SkillHandler.resetPlayerVariables(c);
			}
			
		}, data.getTimer());
	}
	
	public static boolean burnLogs(Client c, BonfireData data) {
		int skillId = new Bonfire().skillId();
		if (!c.playerSkilling[skillId]) {
			return false;
		}
		
		if (c.playerLevel[11] < data.getLevelReq()) {
			c.sendMessage("You need a firemaking level of " + data.getLevelReq() + " to burn these logs. ");
			c.playerSkilling[skillId] = false;
			return false;
		}
		
		if (c.getItems().playerHasItem(data.getLogId(), 1)) {
			c.startAnimation(883);
			new Bonfire().addExp(c, AddBonfireExp(c, data));
			c.getItems().deleteItem(data.getLogId(), 1);
			c.sendMessage("You add a log to the bonfire.");
			c.stopMovement();
			return true;
		} else {
			c.sendMessage("You run out of logs to add to the bonfire.");
			c.playerSkilling[skillId] = false;
			return false;
		}
	}
}